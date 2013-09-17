-- Function: eboscont.p_mayorizacion(bigint, bigint)

-- DROP FUNCTION eboscont.p_mayorizacion(bigint, bigint);

CREATE OR REPLACE FUNCTION eboscont.p_mayorizacion(IN "idDocumento" bigint, IN "IdUsuario" bigint, OUT type_message "char", OUT key_message "char")
  RETURNS record AS
$BODY$
DECLARE
  LCur_Documento CURSOR  FOR
    select doc.*
    from ebosadmi.documento doc
    where doc.id = idDocumento;
  --
  LCur_Asiento CURSOR  FOR
    select asi.*
    from eboscont.asiento asi
    where id_documento = idDocumento
    for update;
  LReg_Asiento eboscont.asiento%ROWTYPE;
  --
  LCur_AsientoDetalle CURSOR  FOR
    select ctas.id, sum(valor_debe) debe, sum(valor_haber) haber
    from eboscont.asiento_detalle det
	    join eboscont.cuenta_contable_empresa ctaEmp on det.id_cuenta_contable = ctaEmp.id
	    join eboscont.cuenta_contable cta on ctaEmp.id_cuenta_contable = cta.id
	    join eboscont.cuenta_contable ctas on cta.formato like ctas.foramto || '%'
    where id_documento = idDocumento;
    _REGISTRADO varchar(1) := 'R';
    _AUTORIZADO varchar(1) := 'A';
    _PROCESADO varchar(1)  := 'P';
    --
    ERROR varchar(1)  := 'E';
BEGIN
	open LCur_Documento;
	fetch LCur_Documento into LReg_Asiento;
	if(LCur_Documento.estado = _REGISTRADO)then
		type_message = ERROR;
		key_message = 'mayorizacion.error.estado.Registrado';
		return;
	end if;
	if(LCur_Documento.estado = _PROCESADO)then
		type_message = ERROR;
		key_message = 'mayorizacion.error.estado.YaProcesado';
		return;
	end if;
	--
	/*open LCur_Asiento;
	fetch LCur_Asiento into LReg_Asiento;
	--
	update eboscont.asiento
	set estado = _PROCESADO
	where current of LCur_Asiento;
	close LCur_Asiento;*/
	--
	open LCur_Documento;
	update ebosadmi.documento
	set estado = _PROCESADO, procesado= sysdate,
		procesador = idUsuario
	where current of LCur_Documento;
	close LCur_Documento;
	--
	commit;
Exception
	when others then
		if Lcur_Asiento%isopen then close Lcur_Asiento; end if;
		rollback;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION eboscont.p_mayorizacion(bigint, bigint)
  OWNER TO postgres;
