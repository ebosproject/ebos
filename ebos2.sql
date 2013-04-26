/* Database generated with pgModeler (PostgreSQL Database Modeler).
  Project Site: pgmodeler.com.br
  Model Author: --- */

SET check_function_bodies = false;
-- ddl-end --

-- object: ebos | type: ROLE -- 
/*CREATE ROLE ebos WITH 
	NOSUPERUSER
	NOCREATEDB
	NOCREATEROLE
	NOINHERIT
	NOLOGIN
;*/
-- ddl-end --

/*  Tablespaces creation must be done outside an multicommand file.
  These commands were put in this file only for convenience.

-- object: ebos_data | type: TABLESPACE -- 
CREATE TABLESPACE ebos_data
	OWNERebos
	LOCATION 'c: \ebos \db';
-- ddl-end --

-- object: ebos_index | type: TABLESPACE -- 
CREATE TABLESPACE ebos_index
	OWNERebos
	LOCATION 'c:\ebos\db';
-- ddl-end --


*/


/* Database creation must be done outside an multicommand file.
   These commands were put in this file only for convenience.

-- object: seguridad | type: DATABASE -- 
CREATE DATABASE seguridad
	ENCODING = 'UTF8'
;
-- ddl-end --


*/
/*
-- object: ebossegu | type: SCHEMA -- 
CREATE SCHEMA ebossegu;
ALTER SCHEMA ebossegu OWNER TO ebos;
COMMENT ON SCHEMA ebossegu IS 'esquema ebos de seguridad y accesos';
-- ddl-end --

-- object: ebosconf | type: SCHEMA -- 
CREATE SCHEMA ebosconf;
ALTER SCHEMA ebosconf OWNER TO ebos;
COMMENT ON SCHEMA ebosconf IS 'esquema ebos de configuracion';
-- ddl-end --

-- object: ebosconf | type: SCHEMA -- 
CREATE SCHEMA ebosadmi;
ALTER SCHEMA ebosadmi OWNER TO ebos;
COMMENT ON SCHEMA ebosconf IS 'esquema ebos de administracion, entidades que puede modificar el usario';
-- ddl-end --

-- object: ebosinve | type: SCHEMA -- 
CREATE SCHEMA ebosinve;
ALTER SCHEMA ebosinve OWNER TO ebos;
COMMENT ON SCHEMA ebosinve IS 'esquema ebos de inventario';
-- ddl-end --

-- object: ebosprov | type: SCHEMA -- 
CREATE SCHEMA ebosprov;
ALTER SCHEMA ebosprov OWNER TO ebos;
COMMENT ON SCHEMA ebosprov IS 'esquema ebos de proveedores';
-- ddl-end --

-- object: ebosclie | type: SCHEMA -- 
CREATE SCHEMA ebosclie;
ALTER SCHEMA ebosclie OWNER TO ebos;
COMMENT ON SCHEMA ebosclie IS 'esquema ebos de proveedores';
-- ddl-end --
*/
CREATE TABLE ebosconf.TipoIdentificacion(
	id bigint,
	codigo varchar(25) NOT NULL,
	descripcion varchar(100) NOT NULL,
	codigoFiscal varchar(2) NOT NULL,
	consumidorFinal smallint NOT NULL DEFAULT 0,
	creador_id bigint NOT NULL,
	creado timestamp NOT NULL,
	modificador_id bigint NOT NULL,
	modificado timestamp,
	CONSTRAINT pkTipoIdentificacion PRIMARY KEY (id)
	WITH (FILLFACTOR = 10)
	USING INDEX TABLESPACE ebos_index
)
WITH (OIDS=TRUE);
ALTER TABLE ebosconf.TipoIdentificacion OWNER TO ebos;

-- object: ebosadmin.persona | type: TABLE -- 
CREATE TABLE ebosadmi.Persona(
	id bigint,
	codigo varchar(25) NOT NULL,
	descripcion varchar(100) NOT NULL,
	id_TipoIdentificacion bigint NOT NULL,
	identificacion varchar(15) NOT NULL,
	contribuyenteEspecial smallint NOT NULL DEFAULT 0,
	llevaContablidad smallint NOT NULL DEFAULT 0,
	juridica smallint NOT NULL DEFAULT 0,
	retieneFuente smallint NOT NULL DEFAULT 0,
	retieneIva smallint NOT NULL DEFAULT 0,
	razonSocial varchar(200) NOT NULL,
	nombreComercial varchar(100) NOT NULL,
	nombres varchar(100) NOT NULL,
	apellidos varchar(100) NOT NULL,
	id_padre bigint ,
	creador_id bigint NOT NULL,
	creado timestamp NOT NULL,
	modificador_id bigint NOT NULL,
	modificado timestamp,
	CONSTRAINT Persona_pk PRIMARY KEY (id)
	WITH (FILLFACTOR = 10)
	USING INDEX TABLESPACE ebos_index
)
WITH (OIDS=TRUE)
TABLESPACE ebos_data;
-- ddl-end --

COMMENT ON COLUMN ebosadmi.Persona.identificacion IS 'identificacion de la persona';
COMMENT ON COLUMN ebosadmi.Persona.contribuyenteEspecial IS 'indica si es o no contribuyente especial';
COMMENT ON COLUMN ebosadmi.Persona.id_padre IS 'id del Representante Legal';
ALTER TABLE ebosadmi.Persona OWNER TO ebos;
-- ddl-end --

-- object: persona_fk | type: CONSTRAINT -- 
ALTER TABLE ebosadmi.Persona ADD CONSTRAINT TipoIdentificacion_fk_Persona FOREIGN KEY (id_TipoIdentificacion)
REFERENCES ebosconf.TipoIdentificacion (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE NOT DEFERRABLE;
-- ddl-end --
-- object: persona_fk_persona | type: CONSTRAINT -- 
ALTER TABLE ebosadmi.Persona ADD CONSTRAINT Persona_fk_Persona FOREIGN KEY (id_Padre)
REFERENCES ebosadmi.Persona (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE NOT DEFERRABLE;
-- ddl-end --

-- object: ebossegu.Usuario | type: TABLE -- 
CREATE TABLE ebossegu.Usuario(
	id bigint NOT NULL,
	codigo varchar(20) NOT NULL,
	clave varchar(32) NOT NULL,
	tipoUsuario varchar(1) not null,
	id_persona bigint,
	creador_id bigint NOT NULL,
	creado timestamp NOT NULL,
	modificador_id bigint ,
	modificado timestamp,
	estado varchar(1) default 'A',
	CONSTRAINT Usuario_pk PRIMARY KEY (id)
	WITH (FILLFACTOR = 10)
	USING INDEX TABLESPACE ebos_index
)
WITH (OIDS=TRUE)
TABLESPACE ebos_data;
-- ddl-end --

COMMENT ON CONSTRAINT Usuario_pk ON ebossegu.Usuario IS 'Contiene los usuarios que acceden al sistema';
-- ddl-end --

-- object: persona_fk | type: CONSTRAINT -- 
ALTER TABLE ebossegu.Usuario ADD CONSTRAINT persona_fk_Usuario FOREIGN KEY (id_persona)
REFERENCES ebosadmi.Persona (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE NOT DEFERRABLE;
-- ddl-end --

-- object: persona_usuario_uq | type: CONSTRAINT -- 
ALTER TABLE ebossegu.Usuario ADD CONSTRAINT persona_uq_usuario UNIQUE (id_persona);
-- ddl-end --
ALTER TABLE ebossegu.Usuario OWNER TO ebos;


-- object: ebossegu.Modulo | type: TABLE -- 
CREATE TABLE ebossegu.Modulo(
	id bigint NOT NULL,
	codigo varchar(25) NOT NULL,
	descripcion varchar(100) NOT NULL,
	audita smallint NOT NULL DEFAULT 0,
	contabiliza smallint NOT NULL DEFAULT 0,
	creador_id bigint NOT NULL,
	creado timestamp NOT NULL,
	modificador_id bigint NOT NULL,
	modificado timestamp,
	CONSTRAINT Modulo_pk PRIMARY KEY (id)
	WITH (FILLFACTOR = 10)
	USING INDEX TABLESPACE ebos_index
)
WITH (OIDS=TRUE)
TABLESPACE ebos_data;
COMMENT ON TABLE ebossegu.Modulo IS 'Contiene las palicacion o plugins con los que cuenta el eBos';
COMMENT ON COLUMN ebossegu.Modulo.audita IS 'indica si el modulo es auditable';
COMMENT ON COLUMN ebossegu.Modulo.contabiliza IS 'indica si el modulo genera asientos contbables';
ALTER TABLE ebossegu.Modulo OWNER TO ebos;
-- ddl-end --

-- object: ebossegu.Aplicacion | type: TABLE -- 
CREATE TABLE ebosconf.Aplicacion(
	id bigint NOT NULL,
	codigo varchar(25) NOT NULL,
	descripcion varchar(100) NOT NULL,
	autor varchar(100) not null,
	version varchar(50) NOT NULL,
	liberado date NOT NULL,
	licencia varchar,
	creador_id bigint NOT NULL,
	creado timestamp NOT NULL,
	modificador_id bigint,
	modificado timestamp,
	estado varchar(1) default 'L', -- 
	CONSTRAINT aplicacion_pk PRIMARY KEY (id)
	WITH (FILLFACTOR = 10)
	USING INDEX TABLESPACE ebos_index
)
WITH (OIDS=TRUE)
TABLESPACE ebos_data;
-- ddl-end --

COMMENT ON TABLE ebosconf.Aplicacion IS 'Contiene las palicacion o plugins con los que cuenta el eBos';
COMMENT ON COLUMN ebosconf.Aplicacion.version IS 'version de la aplicacion';
COMMENT ON COLUMN ebosconf.Aplicacion.liberado IS 'fecha de libreacion dela ultima version de la Aplicacion';
COMMENT ON COLUMN ebosconf.Aplicacion.licencia IS 'licencia valida para el usao de la aplicacion y sus opciones';
COMMENT ON COLUMN ebosconf.Aplicacion.estado IS 'L: Licenciado, T:Trial, I:Inactivo';
ALTER TABLE ebosconf.Aplicacion OWNER TO ebos;
-- ddl-end --

-- object: ebossegu.Objeto | type: TABLE -- 
CREATE TABLE ebosconf.Objeto(
	id bigint NOT NULL,
	codigo varchar(25) NOT NULL,
	descripcion varchar(100) NOT NULL,
	tipo varchar(4) NOT NULL,
	nombre varchar(100) NOT NULL,
	creador_id bigint NOT NULL,
	creado timestamp NOT NULL,
	modificador_id bigint,
	modificado timestamp,
	estado varchar NOT NULL DEFAULT 'A',
	id_Modulo bigint,
	CONSTRAINT pk_Objeto PRIMARY KEY (id)
	WITH (FILLFACTOR = 10)
	USING INDEX TABLESPACE ebos_index
)
WITH (OIDS=TRUE)
TABLESPACE ebos_data;
-- ddl-end --

COMMENT ON COLUMN ebosconf.Objeto.id IS 'id del objeto';
COMMENT ON COLUMN ebosconf.Objeto.codigo IS 'codigo del objeto';
COMMENT ON COLUMN ebosconf.Objeto.descripcion IS 'descripcion del objeto';
COMMENT ON COLUMN ebosconf.Objeto.tipo IS 'BEAN:Clase, TABL:Tabla';
COMMENT ON COLUMN ebosconf.Objeto.estado IS 'A:Activo I:Inactivo';
-- ddl-end --

-- object: Modulo_fk | type: CONSTRAINT -- 
ALTER TABLE ebosconf.Objeto ADD CONSTRAINT Modulo_fk FOREIGN KEY (id_Modulo)
REFERENCES ebossegu.Modulo (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE NOT DEFERRABLE;
ALTER TABLE ebosconf.Objeto OWNER TO ebos;
-- ddl-end --

-- object: ebossegu.Opcion | type: TABLE -- 
CREATE TABLE ebossegu.Opcion(
	id bigint NOT NULL,
	codigo varchar(20) NOT NULL,
	descripcion varchar(200) NOT NULL,
	tipoAplicacion varchar(3) NOT NULL DEFAULT 'OPC',
	id_padre bigint,
	terminante smallint NOT NULL DEFAULT 0,
	nivel smallint NOT NULL DEFAULT 1,
	orden varchar(24) NOT NULL,
	crear smallint NOT NULL DEFAULT 1,
	editar smallint NOT NULL DEFAULT 1,
	eliminar smallint NOT NULL DEFAULT 1,
	listar smallint NOT NULL DEFAULT 1,
	generar smallint NOT NULL DEFAULT 1,
	autorizar smallint NOT NULL DEFAULT 1,
	rechazar smallint NOT NULL DEFAULT 1,
	procesar smallint NOT NULL DEFAULT 1,
	reversar smallint NOT NULL DEFAULT 1,
	enviar smallint NOT NULL DEFAULT 1,
	recuperar smallint NOT NULL DEFAULT 1,
	contabilizar smallint NOT NULL DEFAULT 1,
	descontabilizar smallint NOT NULL DEFAULT 1,
	permiso1 smallint NOT NULL DEFAULT 0,
	permiso2 smallint NOT NULL DEFAULT 0,
	permiso3 smallint NOT NULL DEFAULT 0,
	permiso4 smallint NOT NULL DEFAULT 0,
	permiso5 smallint NOT NULL DEFAULT 0,
	permiso6 smallint NOT NULL DEFAULT 0,
	permiso7 smallint NOT NULL DEFAULT 0,
	permiso8 smallint NOT NULL DEFAULT 0,
	descipcionPermiso varchar(200) NOT NULL DEFAULT 0,
	id_Objeto bigint,
	id_Aplicacion bigint,
	id_Modulo bigint,
	creador_id bigint NOT NULL,
	creado timestamp NOT NULL,
	modificador_id bigint NOT NULL,
	modificado timestamp,
	estado varchar(1) default 'A',
	CONSTRAINT Opcion_pk PRIMARY KEY (id)
	WITH (FILLFACTOR = 10)
	USING INDEX TABLESPACE ebos_index
)
WITH (OIDS=TRUE)
TABLESPACE ebos_data;
-- ddl-end --

COMMENT ON COLUMN ebossegu.Opcion.tipoAplicacion IS 'EXE: Ejecutable, REP: Reporte, OPC: Opcion Normal';
COMMENT ON COLUMN ebossegu.Opcion.terminante IS 'si es ultima opcion';
COMMENT ON COLUMN ebossegu.Opcion.nivel IS 'nivel de la opcion dentro de la jerarquia';
COMMENT ON COLUMN ebossegu.Opcion.orden IS 'orden para almar el menu 01-01-01-03';
COMMENT ON COLUMN ebossegu.Opcion.crear IS 'permiso crear';
COMMENT ON COLUMN ebossegu.Opcion.id_Objeto IS 'id del objeto';
ALTER TABLE ebossegu.Opcion OWNER TO ebos;
-- ddl-end --

-- object: Modulo_fk | type: CONSTRAINT -- 
ALTER TABLE ebossegu.Opcion ADD CONSTRAINT Modulo_fk FOREIGN KEY (id_Modulo)
REFERENCES ebossegu.Modulo (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE NOT DEFERRABLE;
-- ddl-end --

-- object: Objeto_fk | type: CONSTRAINT -- 
ALTER TABLE ebossegu.Opcion ADD CONSTRAINT Objeto_fk FOREIGN KEY (id_Objeto)
REFERENCES ebosconf.Objeto (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE NOT DEFERRABLE;
-- ddl-end --

-- object: Aplicacion_fk | type: CONSTRAINT -- 
ALTER TABLE ebossegu.Opcion ADD CONSTRAINT Aplicacion_fk FOREIGN KEY (id_Aplicacion)
REFERENCES ebosconf.Aplicacion (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE NOT DEFERRABLE;
-- ddl-end --


-- object: ebosconf.TipoOrganizacion | type: TABLE -- 
CREATE TABLE ebosconf.TipoOrganizacion(
	id bigint NOT NULL,
	codigo varchar(25) NOT NULL,
	descripcion varchar(100) NOT NULL,
	nivel smallint NOT NULL DEFAULT 1,
	CONSTRAINT PkTipoOrganizacion PRIMARY KEY (id)
	WITH (FILLFACTOR = 10)
	USING INDEX TABLESPACE ebos_index
)
WITH (OIDS=TRUE)
TABLESPACE ebos_data;
-- ddl-end --
ALTER TABLE ebosconf.TipoOrganizacion OWNER TO ebos;

-- object: ebosconf.Organizacion | type: TABLE -- 
CREATE TABLE ebosconf.Organizacion(
	id bigint NOT NULL,
	codigo varchar(20) NOT NULL,
	descripcion varchar(100) NOT NULL,
	persona_id bigint NOT NULL,
	id_tipoOrganizacion bigint NOT NULL DEFAULT 1,
	nivel smallint NOT NULL DEFAULT 1,
	rama_id bigint,
	creador_id bigint NOT NULL,
	creado timestamp NOT NULL,
	modificador_id bigint NOT NULL,
	modificado timestamp,
	manejaCentroCosto smallint default 1,
	estado varchar NOT NULL DEFAULT 'A',
	id_DivisionGeografica bigint,
	CONSTRAINT PkOrganizacion PRIMARY KEY (id)
	WITH (FILLFACTOR = 10)
	USING INDEX TABLESPACE ebos_index
)
WITH (OIDS=TRUE)
TABLESPACE ebos_data;
-- ddl-end --

COMMENT ON TABLE ebosconf.Organizacion IS 'Contiene la estructura organizacional de la empresa, region, oficina, sucursal, agencia';
COMMENT ON COLUMN ebosconf.Organizacion.id IS 'id de la estructra organizacional';
COMMENT ON COLUMN ebosconf.Organizacion.codigo IS 'codigo de la estructura organizacional';
COMMENT ON COLUMN ebosconf.Organizacion.descripcion IS 'descripcion o nombre de la estrucutra organizacional';
COMMENT ON COLUMN ebosconf.Organizacion.persona_id IS 'bajo el contexto, es la empresa o representante del registro de la estructura';
COMMENT ON COLUMN ebosconf.Organizacion.id_tipoOrganizacion IS 'E:Empresa, R:Region, S:Sucursal, A:Agencia, B: Bodega';
COMMENT ON COLUMN ebosconf.Organizacion.nivel IS 'nivel dentro de la estructura';
COMMENT ON COLUMN ebosconf.Organizacion.rama_id IS 'organizaion padre';
COMMENT ON COLUMN ebosconf.Organizacion.modificado IS 'fecha modificacion del registro actual';
COMMENT ON COLUMN ebosconf.Organizacion.estado IS 'indica si eta A:activo o I:inactivo';
COMMENT ON COLUMN ebosconf.Organizacion.id_DivisionGeografica IS 'id division geografica';
COMMENT ON CONSTRAINT PkOrganizacion ON ebosconf.Organizacion IS 'clave primariaria de organizaciones';
ALTER TABLE ebosconf.Organizacion OWNER TO ebos;
-- ddl-end --

-- object: TipoOrganizacion_fk | type: CONSTRAINT -- 
ALTER TABLE ebosconf.Organizacion ADD CONSTRAINT TipoOrganizacion_fk_Ornanizacion FOREIGN KEY (id_TipoOrganizacion)
REFERENCES ebosconf.TipoOrganizacion (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE NOT DEFERRABLE;
-- ddl-end --

-- object: ebossegu.UsuarioOrganizacion | type: TABLE -- 
CREATE TABLE ebossegu.UsuarioOrganizacion(
	id bigint NOT NULL,
	id_Usuario bigint,
	id_Empresa bigint,
	id_Agencia bigint,
	CONSTRAINT pk_UsuarioOrganizacion PRIMARY KEY (id)
	WITH (FILLFACTOR = 10)
	USING INDEX TABLESPACE ebos_index
)
WITH (OIDS=TRUE)
TABLESPACE ebos_data;
-- ddl-end --

COMMENT ON TABLE ebossegu.UsuarioOrganizacion IS 'Permisos sobre la organizacion que tiene un usario';
ALTER TABLE ebossegu.UsuarioOrganizacion OWNER TO ebos;
-- ddl-end --

-- object: Usuario_fk | type: CONSTRAINT -- 
ALTER TABLE ebossegu.UsuarioOrganizacion ADD CONSTRAINT Usuario_fk FOREIGN KEY (id_Usuario)
REFERENCES ebossegu.Usuario (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE NOT DEFERRABLE;
-- ddl-end --
-- object: Organizacion_fk | type: CONSTRAINT -- 
ALTER TABLE ebossegu.UsuarioOrganizacion ADD CONSTRAINT Organizacion_fk FOREIGN KEY (id_Empresa)
REFERENCES ebosconf.Organizacion (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE NOT DEFERRABLE;
-- ddl-end --

-- object: ebosconf.TipoDocumento | type: TABLE -- 
CREATE TABLE ebosconf.TipoDocumento(
	id bigint NOT NULL,
	tipoFiscal smallint NOT NULL DEFAULT 99,
	id_tipoAsociacion bigint not null,
	id_Bean bigint,
	id_Modulo bigint,
	CONSTRAINT TipoDocumento PRIMARY KEY (id)
	WITH (FILLFACTOR = 10)
	USING INDEX TABLESPACE ebos_index
)
WITH (OIDS=TRUE)
TABLESPACE ebos_data;
-- ddl-end --

COMMENT ON COLUMN ebosconf.TipoDocumento.tipo IS '1 Factaura, 4 Nota de Credito, 5 Nota de debito (ver codigos del SRI)';
COMMENT ON COLUMN ebosconf.TipoDocumento.id_Objeto IS 'id del Bean que administra este tipo de documento';
COMMENT ON COLUMN ebosconf.TipoDocumento.id_tipoAsociacion IS 'indica si es documento de empleado, proveedor, cliente, etc';
ALTER TABLE ebosconf.TipoDocumento OWNER TO ebos;
-- ddl-end --


-- object: Objeto_fk | type: CONSTRAINT -- 
ALTER TABLE ebossegu.TipoDocumentoUsuario ADD CONSTRAINT Objeto_fk FOREIGN KEY (id_Objeto)
REFERENCES ebossegu.Objeto (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE NOT DEFERRABLE;
-- ddl-end --

-- object: Objeto_fk | type: CONSTRAINT -- 
ALTER TABLE ebosconf.TipoDocumento ADD CONSTRAINT Objeto_fk FOREIGN KEY (id_Bean)
REFERENCES ebossegu.Objeto (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE NOT DEFERRABLE;
-- ddl-end --

-- object: Modulo_fk | type: CONSTRAINT -- 
ALTER TABLE ebosconf.TipoDocumento ADD CONSTRAINT Modulo_fk FOREIGN KEY (id_Modulo)
REFERENCES ebossegu.Modulo (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE NOT DEFERRABLE;
-- ddl-end --


-- object: ebosconf.TipoDocumentoEmpresa | type: TABLE -- 
CREATE TABLE ebosconf.TipoDocumentoEmpresa(
	id bigint NOT NULL,
	numeroLineas smallint NOT NULL DEFAULT 0,
	formatoReferenciaFiscal varchar(20) DEFAULT '###-###-#########',
	exigeMotivo smallint not null default 0,
	id_Reporte bigint,
	id_Empresa bigint,
	id_TipoDocumento bigint,
	CONSTRAINT PkTipoDocumentoEmpresa PRIMARY KEY (id)
)
WITH (OIDS=TRUE)
TABLESPACE ebos_data;
-- ddl-end --

-- ddl-end --
ALTER TABLE ebosconf.TipoDocumentoEmpresa OWNER TO ebos;

-- object: Organizacion_fk | type: CONSTRAINT -- 
ALTER TABLE ebosconf.TipoDocumentoEmpresa ADD CONSTRAINT Organizacion_fk_tde FOREIGN KEY (id_Empresa)
REFERENCES ebossegu.Organizacion (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE NOT DEFERRABLE;
-- ddl-end --

-- object: TipoDocumento_fk | type: CONSTRAINT -- 
ALTER TABLE ebosconf.TipoDocumentoEmpresa ADD CONSTRAINT TipoDocumento_fk_tde FOREIGN KEY (id_TipoDocumento)
REFERENCES ebosconf.TipoDocumento (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE NOT DEFERRABLE;
-- ddl-end --

-- object: ebossegu.TipoDocumentoUsuario | type: TABLE -- 
CREATE TABLE ebossegu.TipoDocumentoBodegaUsuario(
	id bigint NOT NULL,
	registrar smallint NOT NULL DEFAULT 1,
	procesar smallint NOT NULL DEFAULT 0,
	aprobar smallint NOT NULL DEFAULT 0,
	contabilizar smallint NOT NULL DEFAULT 0,
	digitar smallint NOT NULL DEFAULT 0,
	coordinar smallint NOT NULL,
	id_TipoDocumento bigint,
	id_Usuario bigint,
	id_Empresa bigint,
	id_Agencia bigint,
	id_TipoBodega bigint,
	id_Bodega bigint,
	CONSTRAINT TipoDocumentoUsuario_pk PRIMARY KEY (id)
)
WITH (OIDS=TRUE)
TABLESPACE ebos_data;
-- ddl-end --

COMMENT ON COLUMN ebossegu.TipoDocumentoBodegaUsuario.id IS 'id del tipo de documento permitidos al usuario';
COMMENT ON COLUMN ebossegu.TipoDocumentoBodegaUsuario.digitar IS 'indica si puede o no digitar tomas ficisas';
COMMENT ON COLUMN ebossegu.TipoDocumentoBodegaUsuario.id_Objeto IS 'id del objeto';
-- ddl-end --

-- object: Usuario_fk | type: CONSTRAINT -- 
ALTER TABLE ebossegu.TipoDocumentoBodegaUsuario ADD CONSTRAINT Usuario_fk FOREIGN KEY (id_Usuario)
REFERENCES ebossegu.Usuario (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE NOT DEFERRABLE;
-- ddl-end --

-- object: TipoDocumento_fk | type: CONSTRAINT -- 
ALTER TABLE ebossegu.TipoDocumentoBodegaUsuario ADD CONSTRAINT TipoDocumento_fk FOREIGN KEY (id_TipoDocumento)
REFERENCES ebosconf.TipoDocumento (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE NOT DEFERRABLE;
-- ddl-end --

-- object: Organizacion_fk | type: CONSTRAINT -- 
ALTER TABLE ebossegu.TipoDocumentoBodegaUsuario ADD CONSTRAINT Organizacion_fk FOREIGN KEY (id_Empresa)
REFERENCES ebossegu.Organizacion (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE NOT DEFERRABLE;
-- ddl-end --

-- object: ebossegu.Perfil | type: TABLE -- 
CREATE TABLE ebossegu.Perfil(
	id bigint NOT NULL,
	codigo varchar(25) NOT NULL,
	descripcion varchar(100) NOT NULL,
	estado varchar NOT NULL DEFAULT 'A',
	CONSTRAINT Perfil_Pk PRIMARY KEY (id)
)
WITH (OIDS=TRUE)
TABLESPACE ebos_data;
-- ddl-end --

COMMENT ON COLUMN ebossegu.Perfil.estado IS 'A Activo, I Inactivo';
-- ddl-end --

-- object: ebossegu.PerfilOpcion | type: TABLE -- 
CREATE TABLE ebossegu.PerfilOpcion(
	id smallint NOT NULL,
	crear smallint NOT NULL DEFAULT 1,
	editar smallint NOT NULL DEFAULT 1,
	eliminar smallint NOT NULL DEFAULT 1,
	listar smallint NOT NULL DEFAULT 1,
	generar smallint NOT NULL DEFAULT 1,
	autorizar smallint NOT NULL DEFAULT 1,
	rechazar smallint NOT NULL DEFAULT 1,
	procesar smallint NOT NULL DEFAULT 1,
	reversar smallint NOT NULL DEFAULT 1,
	enviar smallint NOT NULL DEFAULT 1,
	recuperar smallint NOT NULL DEFAULT 1,
	contabilizar smallint NOT NULL DEFAULT 1,
	descontabilizar smallint NOT NULL DEFAULT 1,
	permiso1 smallint NOT NULL DEFAULT 0,
	permiso2 smallint NOT NULL DEFAULT 0,
	permiso3 smallint NOT NULL DEFAULT 0,
	permiso4 smallint NOT NULL DEFAULT 0,
	permiso5 smallint NOT NULL DEFAULT 0,
	permiso6 smallint NOT NULL DEFAULT 0,
	permiso7 smallint NOT NULL DEFAULT 0,
	permiso8 smallint NOT NULL DEFAULT 0,
	id_Perfil bigint,
	id_Opcion bigint,
	CONSTRAINT PerfilOpcion_pk PRIMARY KEY (id)
)
WITH (OIDS=TRUE)
TABLESPACE ebos_data;
-- ddl-end --

COMMENT ON COLUMN ebossegu.PerfilOpcion.crear IS 'permiso crear';
-- ddl-end --

-- object: Perfil_fk | type: CONSTRAINT -- 
ALTER TABLE ebossegu.PerfilOpcion ADD CONSTRAINT Perfil_fk FOREIGN KEY (id_Perfil)
REFERENCES ebossegu.Perfil (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE NOT DEFERRABLE;
-- ddl-end --


-- object: Opcion_fk | type: CONSTRAINT -- 
ALTER TABLE ebossegu.PerfilOpcion ADD CONSTRAINT Opcion_fk FOREIGN KEY (id_Opcion)
REFERENCES ebossegu.Opcion (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE NOT DEFERRABLE;
-- ddl-end --


-- object: ebossegu.UsuarioOpcion | type: TABLE -- 
CREATE TABLE ebossegu.UsuarioOpcion(
	id bigint NOT NULL,
	crear smallint NOT NULL DEFAULT 1,
	editar smallint NOT NULL DEFAULT 1,
	eliminar smallint NOT NULL DEFAULT 1,
	listar smallint NOT NULL DEFAULT 1,
	generar smallint NOT NULL DEFAULT 1,
	autorizar smallint NOT NULL DEFAULT 1,
	rechazar smallint NOT NULL DEFAULT 1,
	procesar smallint NOT NULL DEFAULT 1,
	reversar smallint NOT NULL DEFAULT 1,
	enviar smallint NOT NULL DEFAULT 1,
	recuperar smallint NOT NULL DEFAULT 1,
	contabilizar smallint NOT NULL DEFAULT 1,
	descontabilizar smallint NOT NULL DEFAULT 1,
	permiso1 smallint NOT NULL DEFAULT 0,
	permiso2 smallint NOT NULL DEFAULT 0,
	permiso3 smallint NOT NULL DEFAULT 0,
	permiso4 smallint NOT NULL DEFAULT 0,
	permiso5 smallint NOT NULL DEFAULT 0,
	permiso6 smallint NOT NULL DEFAULT 0,
	permiso7 smallint NOT NULL DEFAULT 0,
	permiso8 smallint NOT NULL DEFAULT 0,
	tipo     smallint not null default 1,
	id_Usuario bigint,
	id_Opcion bigint,
	id_Empresa bigint,
	id_Agencia bigint,
	id_Perfil bigint,
	CONSTRAINT UsuarioOpcion_Pk PRIMARY KEY (id)
)
WITH (OIDS=TRUE)
TABLESPACE ebos_data;
-- ddl-end --

COMMENT ON COLUMN ebossegu.UsuarioOpcion.crear IS 'permiso crear';
-- ddl-end --

-- object: Usuario_fk | type: CONSTRAINT -- 
ALTER TABLE ebossegu.UsuarioOpcion ADD CONSTRAINT Usuario_fk FOREIGN KEY (id_Usuario)
REFERENCES ebossegu.Usuario (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE NOT DEFERRABLE;
-- ddl-end --


-- object: Opcion_fk | type: CONSTRAINT -- 
ALTER TABLE ebossegu.UsuarioOpcion ADD CONSTRAINT Opcion_fk FOREIGN KEY (id_Opcion)
REFERENCES ebossegu.Opcion (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE NOT DEFERRABLE;
-- ddl-end --


-- object: Organizacion_fk | type: CONSTRAINT -- 
ALTER TABLE ebossegu.UsuarioOpcion ADD CONSTRAINT Empresa_fk_UsuarioOpcion FOREIGN KEY (id_Empresa)
REFERENCES ebossegu.Organizacion (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE NOT DEFERRABLE;
-- ddl-end --

-- object: Organizacion_fk | type: CONSTRAINT -- 
ALTER TABLE ebossegu.UsuarioOpcion ADD CONSTRAINT Agencia_fk_UsuarioOpcion FOREIGN KEY (id_Agencia)
REFERENCES ebossegu.Organizacion (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE NOT DEFERRABLE;
-- ddl-end --

-- object: Perfil_fk | type: CONSTRAINT -- 
ALTER TABLE ebossegu.UsuarioOpcion ADD CONSTRAINT Perfil_fk FOREIGN KEY (id_Perfil)
REFERENCES ebossegu.Perfil (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE NOT DEFERRABLE;
-- ddl-end --


-- object: persona_fk | type: CONSTRAINT -- 
ALTER TABLE ebossegu.Organizacion ADD CONSTRAINT persona_fk FOREIGN KEY (id_persona)
REFERENCES ebossegu.persona (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE NOT DEFERRABLE;
-- ddl-end --

-- object: fkOpcion_OpcionPadre | type: CONSTRAINT -- 
ALTER TABLE ebossegu.Opcion ADD CONSTRAINT fkOpcion_OpcionPadre FOREIGN KEY (id_padre)
REFERENCES ebossegu.Opcion (id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE;
-- ddl-end --



