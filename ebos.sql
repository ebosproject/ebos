/*
Datos iniciales eBos Project //TODO (epa): Terminar script para ejecució total de migración de datos iniciales

@author Eduardo Plúa Alay
@since 2013/04/28
*/

--
--MASTER
--
/*
PERSONA
*/
ALTER TABLE ebosmast.persona ALTER COLUMN id_creador DROP NOT NULL;  
INSERT INTO ebosmast.persona (id, apellidos, creacion, modificacion, cliente, empleado, identificacion, mail, nombres, proveedor, usuario, id_creador, id_modificador, estado, tipoidentificacion, tipopersona) 
VALUES (1, 'Plúa Alay', '2013-03-19 00:00:00.0', '2013-03-19 00:00:00.0', false, false, '0926212515', 'eduardo.plua@gmail.com', 'Eduardo Plúa', false, true, null, null, 'A', 'C', 'N');

/*
EMPRESA
*/
ALTER TABLE ebosmast.organizacion ALTER COLUMN id_creador DROP NOT NULL;
INSERT INTO ebosmast.organizacion (id, creacion, modificacion, descripcion, imagen, id_creador, id_modificador, id_persona, estado) 
VALUES (1, '2013-03-19 00:00:00.0', '2013-03-19 00:00:00.0', 'Eduardo Plúa Alay', 'images/logo_empresa', null, null, 1, 'A');

/*
EMPRESA_PERSONA
*/
ALTER TABLE ebosmast.empresa_persona ALTER COLUMN id_creador DROP NOT NULL;
INSERT INTO ebosmast.empresa_persona (id, creacion, modificacion, id_creador, id_modificador, id_empresa, id_persona, estado) 
VALUES (1, '2013-03-19 00:00:00.0', '2013-03-19 00:00:00.0', null, null, 1, 1, 'A');

/*
BUNDLE
*/
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (2, 'systemAuthor', 'es', 'Eduardo Andrés Plúa Alay');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (54, 'ebViewExpired', 'es', 'Vista expiró');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (55, 'ebSessionTimedout', 'es', 'Sesión cerrada por inactividad');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (3, 'systemVersion', 'es', '1.0.0');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (4, 'systemCopyright', 'es', 'Platform Project 2013. Todos los Derechos Reservados.');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (5, 'systemIngreso', 'es', 'Ingreso al sistema');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (6, 'systemName', 'es', 'Platform Project');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (7, 'systemYear', 'es', '2013');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (8, 'ebActualizar', 'es', 'Actualizar');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (9, 'ebBuscar', 'es', 'Buscar');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (10, 'ebCambiar', 'es', 'Cambiar');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (11, 'ebCambiarPassword', 'es', 'Cambiar Contraseña');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (12, 'ebCancelar', 'es', 'Cancelar');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (13, 'ebCerrar', 'es', 'Cerrar');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (14, 'ebClave', 'es', 'Clave');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (15, 'ebConfiguracion', 'es', 'Configuración');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (16, 'ebConfirmarPassword', 'es', 'Confirmar Contraseña');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (17, 'ebCreado', 'es', 'Creado');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (18, 'ebCreadoPor', 'es', 'Creado Por');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (19, 'ebCuenta', 'es', 'Cuenta');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (20, 'ebDescripcion', 'es', 'Descripción');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (21, 'ebEditar', 'es', 'Editar');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (22, 'ebEliminar', 'es', 'Eliminar');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (23, 'ebEmptyDataTable', 'es', 'No existen registros');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (24, 'ebEtiqueta', 'es', 'Etiqueta');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (25, 'ebEstado', 'es', 'Estado');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (26, 'ebExportar', 'es', 'Exportar');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (27, 'ebGuardar', 'es', 'Guardar');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (28, 'ebIcono', 'es', 'Ícono');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (29, 'ebIngresar', 'es', 'Ingresar');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (30, 'ebIngreseClave', 'es', 'Ingrese Clave');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (31, 'ebIngreseUsuario', 'es', 'Ingrese Usuario');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (32, 'ebModificado', 'es', 'Modificado');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (33, 'ebModificadoPor', 'es', 'Modificado Por');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (34, 'ebNombre', 'es', 'Nombre');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (35, 'ebNombres', 'es', 'Nombres');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (36, 'ebNuevo', 'es', 'Nuevo');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (37, 'ebNuevoPassword', 'es', 'Nueva Contraseña');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (38, 'ebOpcion', 'es', 'Opción');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (39, 'ebPadre', 'es', 'Padre');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (40, 'ebPasswordActual', 'es', 'Contraseña Actual');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (41, 'ebParametros', 'es', 'Parámetros');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (42, 'ebPor', 'es', 'Por');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (43, 'ebRol', 'es', 'Rol');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (44, 'ebSalir', 'es', 'Salir');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (45, 'ebTarget', 'es', 'Target');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (46, 'ebUsername', 'es', 'Username');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (47, 'ebUsuario', 'es', 'Usuario');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (48, 'ebApellidos', 'es', 'Apellidos');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (1, 'systemAbout', 'es', 'Platform Project...');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (50, 'ebCodigo', 'es', 'Código');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (51, 'ebValor', 'es', 'Valor');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (52, 'ebId', 'es', 'Id');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (53, 'ebLocalidad', 'es', 'Localidad');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (56, 'ebVer', 'es', 'Ver');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (57, 'ebEmail', 'es', 'Email');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (58, 'ebAgregar', 'es', 'Agregar');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (59, 'ebSel', 'es', 'Sel.');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (60, 'ebEscojaTema', 'es', 'Seleccione tema');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (61, 'ebPassword', 'es', 'Contraseña');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (62, 'ebDebil', 'es', 'Débil');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (63, 'ebFuerte', 'es', 'Fuerte');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (64, 'ebBuena', 'es', 'Buena');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (65, 'ebMala', 'es', 'Mala');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (66, 'ebIngresePassword', 'es', 'Por favor ingrese una contraseña');
INSERT INTO ebosmast.bundle (id, codigo, localidad, valor) VALUES (67, 'ebCrear', 'es', 'Crear');

--
-- SECU
--

/*
OBJETO
*/
ALTER TABLE ebossecu.objeto ALTER COLUMN id_creador DROP NOT NULL;
INSERT INTO ebossecu.objeto (id, creacion, modificacion, codigo, descripcion, id_creador, id_modificador, estado, tipo) 
VALUES (1, '2013-03-24 00:00:00.0', '2013-03-24 00:00:00.0', 'sesionUsuario', 'Bean de sesion de usuario', null, NULL, 'A', 'B');
INSERT INTO ebossecu.objeto (id, creacion, modificacion, codigo, descripcion, id_creador, id_modificador, estado, tipo) 
VALUES (2, '2013-03-24 00:00:00.0', '2013-03-24 00:00:00.0', 'bundleBean', 'Bean de bundle', null, NULL, 'A', 'B');
INSERT INTO ebossecu.objeto (id, creacion, modificacion, codigo, descripcion, id_creador, id_modificador, estado, tipo) 
VALUES (3, '2013-03-24 00:00:00.0', '2013-03-24 00:00:00.0', 'usuarioBean', 'Bean de usuario', null, NULL, 'A', 'B');


/*
USUARIO
*/
INSERT INTO ebossecu.usuario (id, creacion, modificacion, password, tema, username, id_creador, id_modificador, id_empresapersona, estado, maxoptions) 
VALUES (1, '2013-03-19 00:00:00.0', '2013-03-31 23:06:34.238', '8C6976E5B5410415BDE908BD4DEE15DFB167A9C873FC4BB8A81F6F2AB448A918', 'rocket', 'admin', 1, 1, 1, 'A', 10);

/*
Actualización de creador en todas las tablas anteriores
*/
--MASTER
UPDATE ebosmast.persona set id_creador = 1;
ALTER TABLE ebosmast.persona ALTER COLUMN id_creador SET NOT NULL;
UPDATE ebosmast.organizacion set id_creador = 1;
ALTER TABLE ebosmast.organizacion ALTER COLUMN id_creador SET NOT NULL;
UPDATE ebosmast.empresa_persona set id_creador = 1;
ALTER TABLE ebosmast.empresa_persona ALTER COLUMN id_creador SET NOT NULL;

--SECU
UPDATE ebossecu.objeto set id_creador = 1;
ALTER TABLE ebossecu.objeto ALTER COLUMN id_creador SET NOT NULL;


/*
OPCION
*/
--Módulos
INSERT INTO ebossecu.opcion (id, creacion, modificacion, descripcion, etiqueta, icono, nombre, target, id_creador, id_modificador, id_objeto, id_padre, estado) VALUES (10, '2012-09-02 12:33:52.0', '2012-09-02 12:34:20.0', 'MÃ³dulo de Mantenimiento', 'Mantenimiento', NULL, 'mantenimiento', '/mantenimiento/*', 1, NULL, 1, NULL, 'A');
INSERT INTO ebossecu.opcion (id, creacion, modificacion, descripcion, etiqueta, icono, nombre, target, id_creador, id_modificador, id_objeto, id_padre, estado) VALUES (36, '2013-03-24 17:49:00.731', NULL, 'Módulo Aplicación', 'Aplicación', 'user.png', 'Aplicacion', '/master/*', 1, NULL, 1, NULL, 'A');
INSERT INTO ebossecu.opcion (id, creacion, modificacion, descripcion, etiqueta, icono, nombre, target, id_creador, id_modificador, id_objeto, id_padre, estado) VALUES (1, '2012-08-05 22:19:31.0', '2012-10-31 10:27:27.0', 'MÃ³dulo de seguridad', 'Seguridad', '', 'seguridad', '/security/*', 1, NULL, 1, NULL, 'A');
INSERT INTO ebossecu.opcion (id, creacion, modificacion, descripcion, etiqueta, icono, nombre, target, id_creador, id_modificador, id_objeto, id_padre, estado) VALUES (8, '2012-08-10 10:42:34.0', '2013-03-30 01:14:24.727', 'Módulo Sincronización', 'Sincronización', '', 'sincronizacion', '/sincronizacion/*', 1, 1, 1, NULL, 'A');
INSERT INTO ebossecu.opcion (id, creacion, modificacion, descripcion, etiqueta, icono, nombre, target, id_creador, id_modificador, id_objeto, id_padre, estado) VALUES (20, '2012-09-04 11:56:20.0', '2012-09-04 11:56:20.0', 'Modulo de reportes', 'Reportes', NULL, 'reportes', '/reportes/*', 1, NULL, 1, NULL, 'A');
INSERT INTO ebossecu.opcion (id, creacion, modificacion, descripcion, etiqueta, icono, nombre, target, id_creador, id_modificador, id_objeto, id_padre, estado) VALUES (24, '2012-09-04 23:19:10.0', '2012-09-04 23:20:14.0', 'Modulo de Procesos', 'Procesos', NULL, 'procesos', '/procesos/*', 1, NULL, 1, NULL, 'A');
INSERT INTO ebossecu.opcion (id, creacion, modificacion, descripcion, etiqueta, icono, nombre, target, id_creador, id_modificador, id_objeto, id_padre, estado) VALUES (29, '2012-09-21 17:04:37.0', '2012-09-21 17:04:37.0', 'Modulo de administracion de la pagina', 'Administración', NULL, 'Administracion', '/admin/*', 1, NULL, 1, NULL, 'A');
INSERT INTO ebossecu.opcion (id, creacion, modificacion, descripcion, etiqueta, icono, nombre, target, id_creador, id_modificador, id_objeto, id_padre, estado) VALUES (39, '2013-04-01 00:14:46.236', NULL, 'Módulo Bitácora', 'Bitácora', 'user.png', 'bitacora', '/bitacora/*', 1, NULL, NULL, NULL, 'A');
INSERT INTO ebossecu.opcion (id, creacion, modificacion, descripcion, etiqueta, icono, nombre, target, id_creador, id_modificador, id_objeto, id_padre, estado) VALUES (5, '2012-08-09 16:50:09.0', '2012-08-10 09:14:45.0', 'Modulo MensajerÃ­a', 'Mensajería', NULL, 'mensajeria', '/mensajeria/*', 1, NULL, 1, NULL, 'A');

--Opciones
INSERT INTO ebossecu.opcion (id, creacion, modificacion, descripcion, etiqueta, icono, nombre, target, id_creador, id_modificador, id_objeto, id_padre, estado) VALUES (4, '2012-08-09 01:27:15.0', '2012-10-04 23:01:30.0', 'Mantenimiento de Roles', 'Rol', 'role.png', 'rol', '/security/rol/index.xhtml', 1, NULL, 1, 1, 'A');
INSERT INTO ebossecu.opcion (id, creacion, modificacion, descripcion, etiqueta, icono, nombre, target, id_creador, id_modificador, id_objeto, id_padre, estado) VALUES (6, '2012-08-09 16:51:19.0', '2012-08-09 16:51:19.0', 'Enviados', 'Enviados', NULL, 'enviados', '/mensajeria/enviados/index.xhtml', 1, NULL, 1, 5, 'A');
INSERT INTO ebossecu.opcion (id, creacion, modificacion, descripcion, etiqueta, icono, nombre, target, id_creador, id_modificador, id_objeto, id_padre, estado) VALUES (7, '2012-08-10 09:16:38.0', '2012-08-10 09:16:38.0', 'Pantalla de recibidos', 'Recibidos', NULL, 'recibidos', '/mensajeria/recibidos/index.xhtml', 1, NULL, 1, 5, 'A');
INSERT INTO ebossecu.opcion (id, creacion, modificacion, descripcion, etiqueta, icono, nombre, target, id_creador, id_modificador, id_objeto, id_padre, estado) VALUES (11, '2012-09-02 12:35:14.0', '2012-10-04 23:47:04.0', 'Mantenimiento de Alumnos', 'Alumno', 'user_student.png', 'alumno', '/mantenimiento/alumno/index.xhtml', 1, NULL, 1, 10, 'A');
INSERT INTO ebossecu.opcion (id, creacion, modificacion, descripcion, etiqueta, icono, nombre, target, id_creador, id_modificador, id_objeto, id_padre, estado) VALUES (12, '2012-09-02 14:42:04.0', '2012-10-04 23:47:35.0', 'Mantenimiento de Materia', 'Materia', 'book.png', 'materia', '/mantenimiento/materia/index.xhtml', 1, NULL, 1, 10, 'A');
INSERT INTO ebossecu.opcion (id, creacion, modificacion, descripcion, etiqueta, icono, nombre, target, id_creador, id_modificador, id_objeto, id_padre, estado) VALUES (13, '2012-09-02 15:57:36.0', '2012-10-04 23:50:54.0', 'Mantenimiento de Paralelos', 'Paralelo', 'blackboard.png', 'paralelo', '/mantenimiento/paralelo/index.xhtml', 1, NULL, 1, 10, 'A');
INSERT INTO ebossecu.opcion (id, creacion, modificacion, descripcion, etiqueta, icono, nombre, target, id_creador, id_modificador, id_objeto, id_padre, estado) VALUES (16, '2012-09-02 20:19:59.0', '2012-10-05 00:04:28.0', 'Mantenimiento de Horario', 'Horario', 'clock.png', 'horario', '/mantenimiento/horario/index.xhtml', 1, NULL, 1, 10, 'A');
INSERT INTO ebossecu.opcion (id, creacion, modificacion, descripcion, etiqueta, icono, nombre, target, id_creador, id_modificador, id_objeto, id_padre, estado) VALUES (19, '2012-09-02 22:40:05.0', '2012-10-05 00:29:40.0', 'Reporte de E-Mails', 'Rep. E-Mails', 'email_send.png', 'emailhSms', '/reportes/emailhSms/index.xhtml', 1, NULL, 1, 20, 'A');
INSERT INTO ebossecu.opcion (id, creacion, modificacion, descripcion, etiqueta, icono, nombre, target, id_creador, id_modificador, id_objeto, id_padre, estado) VALUES (21, '2012-09-04 12:49:53.0', '2012-10-04 23:43:02.0', 'Procesos de sincronizacion de tablas', 'Procesos', 'proceso_sync.png', 'procesos conexion', '/sincronizacion/procesos/index.xhtml', 1, NULL, 1, 8, 'A');
INSERT INTO ebossecu.opcion (id, creacion, modificacion, descripcion, etiqueta, icono, nombre, target, id_creador, id_modificador, id_objeto, id_padre, estado) VALUES (26, '2012-09-05 22:33:32.0', '2012-10-05 00:22:29.0', 'Mantenimiento de Consultas SMS', 'Consulta', 'question.png', 'mant_consultas', '/mantenimiento/consulta/index.xhtml', 1, NULL, 1, 10, 'A');
INSERT INTO ebossecu.opcion (id, creacion, modificacion, descripcion, etiqueta, icono, nombre, target, id_creador, id_modificador, id_objeto, id_padre, estado) VALUES (28, '2012-09-18 16:23:55.0', '2012-10-05 00:23:42.0', 'Mantenimiento de Deudas', 'Deuda', 'money.png', 'deudas', '/mantenimiento/deuda/index.xhtml', 1, NULL, 1, 10, 'A');
INSERT INTO ebossecu.opcion (id, creacion, modificacion, descripcion, etiqueta, icono, nombre, target, id_creador, id_modificador, id_objeto, id_padre, estado) VALUES (31, '2012-09-30 01:16:51.0', '2012-10-05 00:25:59.0', 'Mantenimiento de personal administrativo', 'Personal Adm.', 'user_suit.png', 'personal', '/mantenimiento/personal/index.xhtml', 1, NULL, 1, 10, 'A');
INSERT INTO ebossecu.opcion (id, creacion, modificacion, descripcion, etiqueta, icono, nombre, target, id_creador, id_modificador, id_objeto, id_padre, estado) VALUES (25, '2012-09-04 23:21:07.0', '2012-10-05 00:34:00.0', 'Definiciones de consultas Sms', 'Definición Consultas', 'dialog.png', 'definicion_sms', '/procesos/definicion/index.xhtml', 1, NULL, 1, 24, 'A');
INSERT INTO ebossecu.opcion (id, creacion, modificacion, descripcion, etiqueta, icono, nombre, target, id_creador, id_modificador, id_objeto, id_padre, estado) VALUES (3, '2012-08-09 01:26:45.0', '2013-03-20 23:47:17.329', 'Mantenimiento de Opciones', 'Opción', 'menu_item.png', 'Opcion', '/security/opcion/index.xhtml', 1, 1, 1, 1, 'A');
INSERT INTO ebossecu.opcion (id, creacion, modificacion, descripcion, etiqueta, icono, nombre, target, id_creador, id_modificador, id_objeto, id_padre, estado) VALUES (9, '2012-08-10 10:43:24.0', '2012-10-09 17:26:34.0', 'Pantalla de conexiÃ³n inicial para sincronizacion', 'Conexión Inicial', 'connection.png', 'conexion inicial', '/sincronizacion/conexion/index.xhtml', 1, NULL, 1, 8, 'A');
INSERT INTO ebossecu.opcion (id, creacion, modificacion, descripcion, etiqueta, icono, nombre, target, id_creador, id_modificador, id_objeto, id_padre, estado) VALUES (14, '2012-09-02 18:11:15.0', '2012-10-04 23:59:17.0', 'Mantenimiento de MatrÃ­culas', 'Matrícula', 'book_reg.png', 'matricula', '/mantenimiento/matricula/index.xhtml', 1, NULL, 1, 10, 'A');
INSERT INTO ebossecu.opcion (id, creacion, modificacion, descripcion, etiqueta, icono, nombre, target, id_creador, id_modificador, id_objeto, id_padre, estado) VALUES (17, '2012-09-02 22:34:17.0', '2012-10-05 00:29:00.0', 'Reporte de EnvÃ­o de Sms', 'Rep. Envío SMS', 'report_go.png', 'enviohSms', '/reportes/enviohSms/index.xhtml', 1, NULL, 1, 20, 'A');
INSERT INTO ebossecu.opcion (id, creacion, modificacion, descripcion, etiqueta, icono, nombre, target, id_creador, id_modificador, id_objeto, id_padre, estado) VALUES (18, '2012-09-02 22:38:36.0', '2012-10-05 00:29:13.0', 'Reporte de RecepciÃ³n de Sms', 'Rep. Recepción SMS', 'report_disk.png', 'recibehSms', '/reportes/recibehSms/index.xhtml', 1, NULL, 1, 20, 'A');
INSERT INTO ebossecu.opcion (id, creacion, modificacion, descripcion, etiqueta, icono, nombre, target, id_creador, id_modificador, id_objeto, id_padre, estado) VALUES (22, '2012-09-04 12:50:42.0', '2012-10-04 23:43:18.0', 'Ejecucion de procesos de sincronizacion', 'Ejecución', 'ejecucion_sync.png', 'ejecucion', '/sincronizacion/ejecucion/index.xhtml', 1, NULL, 1, 8, 'A');
INSERT INTO ebossecu.opcion (id, creacion, modificacion, descripcion, etiqueta, icono, nombre, target, id_creador, id_modificador, id_objeto, id_padre, estado) VALUES (27, '2012-09-06 01:31:09.0', '2012-10-05 00:34:16.0', 'Envio masivo de Sms / Emails', 'Envío Masivo', 'masivo.png', 'envio masivo', '/procesos/masivo/index.xhtml', 1, NULL, 1, 24, 'A');
INSERT INTO ebossecu.opcion (id, creacion, modificacion, descripcion, etiqueta, icono, nombre, target, id_creador, id_modificador, id_objeto, id_padre, estado) VALUES (35, '2013-03-24 15:54:01.258', NULL, 'Objeto', 'Objeto', 'user.png', 'Objeto', '/security/objeto/index.xhtml', 1, NULL, 1, 1, 'A');
INSERT INTO ebossecu.opcion (id, creacion, modificacion, descripcion, etiqueta, icono, nombre, target, id_creador, id_modificador, id_objeto, id_padre, estado) VALUES (30, '2012-09-21 17:07:12.0', '2012-10-05 00:37:42.0', 'Parametros de Sms Server', 'Parámetros', 'setting.png', 'Parametros', '/admin/parametros/index.xhtml', 1, NULL, 1, 29, 'A');
INSERT INTO ebossecu.opcion (id, creacion, modificacion, descripcion, etiqueta, icono, nombre, target, id_creador, id_modificador, id_objeto, id_padre, estado) VALUES (32, '2012-09-30 21:56:37.0', '2012-10-05 00:35:57.0', 'Envio masivo de Sms / Emails a personal administrativo', 'Envíos personal Ad.', 'masivo_pers.png', 'envio masivo personal', '/procesos/personal/index.xhtml', 1, NULL, 1, 24, 'A');
INSERT INTO ebossecu.opcion (id, creacion, modificacion, descripcion, etiqueta, icono, nombre, target, id_creador, id_modificador, id_objeto, id_padre, estado) VALUES (33, '2012-10-09 17:25:55.0', '2012-10-09 17:39:34.0', 'Parametros de conexion local para sincronizacion', 'Conexión Local', 'database.png', 'conexion local', '/sincronizacion/conLocal/index.xhtml', 1, NULL, 1, 8, 'A');
INSERT INTO ebossecu.opcion (id, creacion, modificacion, descripcion, etiqueta, icono, nombre, target, id_creador, id_modificador, id_objeto, id_padre, estado) VALUES (34, '2012-10-31 10:21:52.0', '2012-10-31 10:22:15.0', 'ConfiguraciÃ³n', 'Configuración', 'setting.png', 'configuracion', '/admin/configuracion/contenedor.xhtml', 1, NULL, 1, 29, 'A');
INSERT INTO ebossecu.opcion (id, creacion, modificacion, descripcion, etiqueta, icono, nombre, target, id_creador, id_modificador, id_objeto, id_padre, estado) VALUES (15, '2012-09-02 19:43:54.0', '2012-10-05 00:00:56.0', 'Mantenimiento de Niveles', 'Nivel', 'level.png', 'Nivel', '/mantenimiento/nivel/index.xhtml', 1, NULL, 1, 10, 'A');
INSERT INTO ebossecu.opcion (id, creacion, modificacion, descripcion, etiqueta, icono, nombre, target, id_creador, id_modificador, id_objeto, id_padre, estado) VALUES (2, '2012-08-08 20:54:01.0', '2012-11-13 15:19:16.0', 'Mantenimiento de Usuarios', 'Usuario', 'user.png', 'usuario', '/security/usuario/index.xhtml', 1, NULL, 3, 1, 'A');
INSERT INTO ebossecu.opcion (id, creacion, modificacion, descripcion, etiqueta, icono, nombre, target, id_creador, id_modificador, id_objeto, id_padre, estado) VALUES (38, '2013-03-31 11:37:02.258', NULL, 'Propiedad', 'Propiedad', 'question.png', 'propiedad', '/master/propiedad/index.xhtml', 1, NULL, 1, 36, 'A');
INSERT INTO ebossecu.opcion (id, creacion, modificacion, descripcion, etiqueta, icono, nombre, target, id_creador, id_modificador, id_objeto, id_padre, estado) VALUES (37, '2013-03-24 17:49:39.96', NULL, 'Bundle', 'Bundle', 'user.png', 'Bundle', '/master/bundle/index.xhtml', 1, NULL, 2, 36, 'A');

/*
ROL
*/
INSERT INTO ebossecu.rol (id, creacion, modificacion, descripcion, nombre, id_creador, id_modificador, estado) VALUES (1, '2012-08-05 19:21:00.0', '2012-10-16 21:06:27.0', 'Admin', 'Admin', 1, NULL, 'A');
INSERT INTO ebossecu.rol (id, creacion, modificacion, descripcion, nombre, id_creador, id_modificador, estado) VALUES (2, '2013-04-01 00:05:05.98', '2013-04-01 00:05:05.98', 'Rol para guardias de seguridad de garitas', 'Guardía garita', 1, NULL, 'A');

/*
ROL_OPCION
*/
INSERT INTO ebossecu.rol_opcion (id, creacion, modificacion, crear, editar, eliminar, exportar, id_creador, id_modificador, id_opcion, id_rol, estado) VALUES (100, '2012-08-08 01:33:20.0', '2012-09-04 12:11:10.0', true, true, true, true, 1, NULL, 1, 1, 'A');
INSERT INTO ebossecu.rol_opcion (id, creacion, modificacion, crear, editar, eliminar, exportar, id_creador, id_modificador, id_opcion, id_rol, estado) VALUES (2, '2012-08-08 20:56:31.0', '2012-09-04 12:11:10.0', true, true, true, true, 1, NULL, 2, 1, 'A');
INSERT INTO ebossecu.rol_opcion (id, creacion, modificacion, crear, editar, eliminar, exportar, id_creador, id_modificador, id_opcion, id_rol, estado) VALUES (3, '2012-08-09 01:32:20.0', '2012-09-04 12:11:10.0', true, true, true, true, 1, NULL, 3, 1, 'A');
INSERT INTO ebossecu.rol_opcion (id, creacion, modificacion, crear, editar, eliminar, exportar, id_creador, id_modificador, id_opcion, id_rol, estado) VALUES (4, '2012-08-09 01:32:36.0', '2012-09-04 12:11:10.0', true, true, true, true, 1, NULL, 4, 1, 'A');
INSERT INTO ebossecu.rol_opcion (id, creacion, modificacion, crear, editar, eliminar, exportar, id_creador, id_modificador, id_opcion, id_rol, estado) VALUES (9, '2012-08-09 16:59:05.0', '2012-08-17 23:17:58.0', true, true, true, true, 1, NULL, 5, 1, 'A');
INSERT INTO ebossecu.rol_opcion (id, creacion, modificacion, crear, editar, eliminar, exportar, id_creador, id_modificador, id_opcion, id_rol, estado) VALUES (10, '2012-08-09 17:00:01.0', '2012-09-04 12:11:10.0', true, true, true, true, 1, NULL, 6, 1, 'A');
INSERT INTO ebossecu.rol_opcion (id, creacion, modificacion, crear, editar, eliminar, exportar, id_creador, id_modificador, id_opcion, id_rol, estado) VALUES (13, '2012-08-10 09:20:36.0', '2012-08-10 09:20:36.0', true, true, true, true, 1, NULL, 7, 1, 'A');
INSERT INTO ebossecu.rol_opcion (id, creacion, modificacion, crear, editar, eliminar, exportar, id_creador, id_modificador, id_opcion, id_rol, estado) VALUES (17, '2012-08-10 10:43:39.0', '2012-09-04 12:11:10.0', true, true, true, true, 1, NULL, 8, 1, 'A');
INSERT INTO ebossecu.rol_opcion (id, creacion, modificacion, crear, editar, eliminar, exportar, id_creador, id_modificador, id_opcion, id_rol, estado) VALUES (18, '2012-08-10 10:43:41.0', '2012-09-04 12:11:10.0', true, true, true, true, 1, NULL, 9, 1, 'A');
INSERT INTO ebossecu.rol_opcion (id, creacion, modificacion, crear, editar, eliminar, exportar, id_creador, id_modificador, id_opcion, id_rol, estado) VALUES (19, '2012-09-02 12:36:03.0', '2012-09-04 12:11:10.0', true, true, true, true, 1, NULL, 10, 1, 'A');
INSERT INTO ebossecu.rol_opcion (id, creacion, modificacion, crear, editar, eliminar, exportar, id_creador, id_modificador, id_opcion, id_rol, estado) VALUES (20, '2012-09-02 12:36:14.0', '2012-09-04 12:11:10.0', true, true, true, true, 1, NULL, 11, 1, 'A');
INSERT INTO ebossecu.rol_opcion (id, creacion, modificacion, crear, editar, eliminar, exportar, id_creador, id_modificador, id_opcion, id_rol, estado) VALUES (21, '2012-09-02 14:43:08.0', '2012-09-04 12:11:10.0', true, true, true, true, 1, NULL, 12, 1, 'A');
INSERT INTO ebossecu.rol_opcion (id, creacion, modificacion, crear, editar, eliminar, exportar, id_creador, id_modificador, id_opcion, id_rol, estado) VALUES (22, '2012-09-02 15:58:15.0', '2012-09-04 12:11:10.0', true, true, true, true, 1, NULL, 13, 1, 'A');
INSERT INTO ebossecu.rol_opcion (id, creacion, modificacion, crear, editar, eliminar, exportar, id_creador, id_modificador, id_opcion, id_rol, estado) VALUES (23, '2012-09-02 18:23:19.0', '2012-09-04 12:11:10.0', true, true, true, true, 1, NULL, 14, 1, 'A');
INSERT INTO ebossecu.rol_opcion (id, creacion, modificacion, crear, editar, eliminar, exportar, id_creador, id_modificador, id_opcion, id_rol, estado) VALUES (24, '2012-09-02 19:44:31.0', '2012-09-04 12:11:10.0', true, true, true, true, 1, NULL, 15, 1, 'A');
INSERT INTO ebossecu.rol_opcion (id, creacion, modificacion, crear, editar, eliminar, exportar, id_creador, id_modificador, id_opcion, id_rol, estado) VALUES (25, '2012-09-02 20:20:25.0', '2012-09-04 12:11:10.0', true, true, true, true, 1, NULL, 16, 1, 'A');
INSERT INTO ebossecu.rol_opcion (id, creacion, modificacion, crear, editar, eliminar, exportar, id_creador, id_modificador, id_opcion, id_rol, estado) VALUES (26, '2012-09-02 22:40:18.0', '2012-09-04 12:11:10.0', true, true, true, true, 1, NULL, 17, 1, 'A');
INSERT INTO ebossecu.rol_opcion (id, creacion, modificacion, crear, editar, eliminar, exportar, id_creador, id_modificador, id_opcion, id_rol, estado) VALUES (27, '2012-09-02 22:40:20.0', '2012-09-04 12:11:10.0', true, true, true, true, 1, NULL, 18, 1, 'A');
INSERT INTO ebossecu.rol_opcion (id, creacion, modificacion, crear, editar, eliminar, exportar, id_creador, id_modificador, id_opcion, id_rol, estado) VALUES (28, '2012-09-02 22:40:22.0', '2012-09-04 12:11:10.0', true, true, true, true, 1, NULL, 19, 1, 'A');
INSERT INTO ebossecu.rol_opcion (id, creacion, modificacion, crear, editar, eliminar, exportar, id_creador, id_modificador, id_opcion, id_rol, estado) VALUES (29, '2012-09-04 11:58:43.0', '2012-09-04 12:11:10.0', true, true, true, true, 1, NULL, 20, 1, 'A');
INSERT INTO ebossecu.rol_opcion (id, creacion, modificacion, crear, editar, eliminar, exportar, id_creador, id_modificador, id_opcion, id_rol, estado) VALUES (32, '2012-09-04 12:51:08.0', '2012-09-04 12:51:27.0', true, true, true, true, 1, NULL, 21, 1, 'A');
INSERT INTO ebossecu.rol_opcion (id, creacion, modificacion, crear, editar, eliminar, exportar, id_creador, id_modificador, id_opcion, id_rol, estado) VALUES (33, '2012-09-04 12:51:11.0', '2012-09-04 12:51:27.0', true, true, true, true, 1, NULL, 22, 1, 'A');
INSERT INTO ebossecu.rol_opcion (id, creacion, modificacion, crear, editar, eliminar, exportar, id_creador, id_modificador, id_opcion, id_rol, estado) VALUES (34, '2012-09-04 23:21:50.0', '2012-09-04 23:22:20.0', true, true, true, true, 1, NULL, 24, 1, 'A');
INSERT INTO ebossecu.rol_opcion (id, creacion, modificacion, crear, editar, eliminar, exportar, id_creador, id_modificador, id_opcion, id_rol, estado) VALUES (35, '2012-09-04 23:21:53.0', '2012-09-04 23:22:20.0', true, true, true, true, 1, NULL, 25, 1, 'A');
INSERT INTO ebossecu.rol_opcion (id, creacion, modificacion, crear, editar, eliminar, exportar, id_creador, id_modificador, id_opcion, id_rol, estado) VALUES (36, '2012-09-05 22:33:59.0', '2012-09-05 22:34:09.0', true, true, true, true, 1, NULL, 26, 1, 'A');
INSERT INTO ebossecu.rol_opcion (id, creacion, modificacion, crear, editar, eliminar, exportar, id_creador, id_modificador, id_opcion, id_rol, estado) VALUES (37, '2012-09-06 01:31:33.0', '2012-09-06 01:32:10.0', true, true, true, true, 1, NULL, 27, 1, 'A');
INSERT INTO ebossecu.rol_opcion (id, creacion, modificacion, crear, editar, eliminar, exportar, id_creador, id_modificador, id_opcion, id_rol, estado) VALUES (38, '2012-09-18 16:24:28.0', '2012-09-18 16:24:40.0', true, true, true, true, 1, NULL, 28, 1, 'A');
INSERT INTO ebossecu.rol_opcion (id, creacion, modificacion, crear, editar, eliminar, exportar, id_creador, id_modificador, id_opcion, id_rol, estado) VALUES (39, '2012-09-21 17:07:45.0', '2012-09-21 17:08:02.0', true, true, true, true, 1, NULL, 29, 1, 'A');
INSERT INTO ebossecu.rol_opcion (id, creacion, modificacion, crear, editar, eliminar, exportar, id_creador, id_modificador, id_opcion, id_rol, estado) VALUES (40, '2012-09-21 17:07:47.0', '2012-09-21 17:08:02.0', true, true, true, true, 1, NULL, 30, 1, 'A');
INSERT INTO ebossecu.rol_opcion (id, creacion, modificacion, crear, editar, eliminar, exportar, id_creador, id_modificador, id_opcion, id_rol, estado) VALUES (41, '2012-09-30 01:17:13.0', '2012-09-30 14:13:11.0', true, true, true, true, 1, NULL, 31, 1, 'A');
INSERT INTO ebossecu.rol_opcion (id, creacion, modificacion, crear, editar, eliminar, exportar, id_creador, id_modificador, id_opcion, id_rol, estado) VALUES (42, '2012-09-30 21:57:05.0', '2012-09-30 21:57:16.0', true, true, true, true, 1, NULL, 32, 1, 'A');
INSERT INTO ebossecu.rol_opcion (id, creacion, modificacion, crear, editar, eliminar, exportar, id_creador, id_modificador, id_opcion, id_rol, estado) VALUES (44, '2012-10-09 17:27:01.0', '2012-10-09 17:27:13.0', true, true, true, true, 1, NULL, 33, 1, 'A');
INSERT INTO ebossecu.rol_opcion (id, creacion, modificacion, crear, editar, eliminar, exportar, id_creador, id_modificador, id_opcion, id_rol, estado) VALUES (45, '2012-11-26 16:25:32.0', '2012-11-26 16:25:50.0', true, true, true, true, 1, NULL, 34, 1, 'A');
INSERT INTO ebossecu.rol_opcion (id, creacion, modificacion, crear, editar, eliminar, exportar, id_creador, id_modificador, id_opcion, id_rol, estado) VALUES (1, '2013-03-24 15:56:32.946', '2013-03-24 15:57:33.305', true, true, true, false, 1, 1, 35, 1, 'A');
INSERT INTO ebossecu.rol_opcion (id, creacion, modificacion, crear, editar, eliminar, exportar, id_creador, id_modificador, id_opcion, id_rol, estado) VALUES (101, '2013-03-24 00:00:00.0', '2013-03-24 00:00:00.0', true, true, true, false, 1, NULL, 36, 1, 'A');
INSERT INTO ebossecu.rol_opcion (id, creacion, modificacion, crear, editar, eliminar, exportar, id_creador, id_modificador, id_opcion, id_rol, estado) VALUES (102, '2013-03-24 00:00:00.0', '2013-03-24 00:00:00.0', true, true, true, false, 1, NULL, 37, 1, 'A');
INSERT INTO ebossecu.rol_opcion (id, creacion, modificacion, crear, editar, eliminar, exportar, id_creador, id_modificador, id_opcion, id_rol, estado) VALUES (103, '2013-03-24 00:00:00.0', '2013-03-24 00:00:00.0', true, true, true, false, 1, NULL, 38, 1, 'A');



/*
USUARIO_ROL
*/
INSERT INTO ebossecu.usuario_rol (id, creacion, modificacion, id_creador, id_modificador, id_rol, id_usuario, estado) VALUES (1, '2012-08-09 06:19:53.0', '2012-08-09 06:19:53.0', 1, NULL, 1, 1, 'A');
