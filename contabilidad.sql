-- object: eboscont."CuentaCentro" | type: TABLE -- 
CREATE TABLE eboscont."CuentaCentro"(
	id bigint NOT NULL,
	id_Empresa bigint not null,
	"id_CuentaContable" bigint NOT NULL,
	"id_TipoCentroCosto" bigint,
	"id_CentroCosto" bigint,
	id_creador bigint NOT NULL,
	creado timestamp NOT NULL,
	id_modificador bigint,
	modificado timestamp,
	estado varchar NOT NULL DEFAULT 'A',
	CONSTRAINT "ctbPkCtaCentro" PRIMARY KEY (id)
	WITH (FILLFACTOR = 10)
	USING INDEX TABLESPACE ebos_index
)
WITH (OIDS=TRUE)
TABLESPACE ebos_data;
-- ddl-end --

COMMENT ON TABLE eboscont."CuentaCentro" IS 'Asiento Detalle';
COMMENT ON COLUMN eboscont."CuentaCentro".id IS 'id de la estructra organizacional';
COMMENT ON COLUMN eboscont."CuentaCentro"."cuentaContable_id" IS 'id de la cuenta contable';
COMMENT ON COLUMN eboscont."CuentaCentro"."centroCosto_id" IS 'id del centro de costo';
COMMENT ON COLUMN eboscont."CuentaCentro".estado IS 'estado de la relacion';
-- ddl-end --


