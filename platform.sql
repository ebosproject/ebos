/*
 INITIAL DATA PLATFORM PROJECT'S
 
 @author Eduardo Plua Alay 
 @since 2013-02-10
 @update 2013-19-03
*/

/*
 PLTFAPPL.APPTCATEGORIA
*/
INSERT INTO pltfappl.apptcategoria(
            id, codigo, descripcion, estado, padre_id)
    VALUES 
(1, 'ACT', 'ACTIVO FIJO', 0, null),   
(2, 'VEHI', 'ACTIVO VEHÍCULO', 0, 1),
(3, 'CAMI', 'VEHÍCULO CAMIÓN', 0, 2);


/*
 PLTFAPPL.APPTPERSONA
*/
INSERT INTO pltfappl.apptpersona(
            id, apellidos, fechacreacion, fechamodificacion, cliente, empleado, 
            estado, identificacion, mail, nombres, proveedor, tipoidentificacion, 
            usuario, usuariocreacion_id, usuariomodificacion_id)
VALUES (1, 'Plúa Alay', '2013-03-19 00:00:00', '2013-03-19 00:00:00', FALSE, FALSE, 0, '0926212515', 'eduardo.plua@gmail.com', 'Eduardo Plúa', FALSE, 0, TRUE, null, null);

/*
 PLTFAPPL.APPTEMPRESA
*/
INSERT INTO pltfappl.apptempresa(
            id, fechacreacion, fechamodificacion, descripcion, estado, imagen, 
            usuariocreacion_id, usuariomodificacion_id, persona_id)
    VALUES (1, '2013-03-19 00:00:00', '2013-03-19 00:00:00', 'Eduardo Plúa Alay', 0, 'images/logo_empresa', null, null, 1);

/*
 PLTFAPPL.APPTEMPRESA_PERSONA
*/
INSERT INTO pltfappl.apptempresa_persona(
            id, fechacreacion, fechamodificacion, estado, usuariocreacion_id, 
            usuariomodificacion_id, empresa_id, persona_id)
    VALUES (1, '2013-03-19 00:00:00', '2013-03-19 00:00:00', 0, null, null, 1, 1);


/*
 PLTFSEGU.SEGTUSUARIO
*/
INSERT INTO pltfsegu.segtusuario(
            id, fechacreacion, fechamodificacion, estado, "password", tema, 
            username, usuariocreacion_id, usuariomodificacion_id, empresapersona_id)
    VALUES (1, '2013-03-19 00:00:00', '2013-03-19 00:00:00', 0, 'eplua', 'aristo', 'eplua',null ,null , 1);

select usr.* from pltfsegu.segtusuario usr;
--update pltfsegu.segtusuario set usuariocreacion_id = 1, usuariomodificacion_id = 1 where id = 1;

/*
 PLTFAPPL.APPTACTIVO
*/
INSERT INTO pltfappl.apptactivo(
            id, fechacreacion, fechamodificacion, estado, usuariocreacion_id, 
            usuariomodificacion_id, categoria_id, empresa_id)
    VALUES (1, '2013-03-19 00:00:00', '2013-03-19 00:00:00', 0, 1, 1, 1, 1);


/*
 PLTFAPPL.APPTPROPIEDAD
*/
INSERT INTO pltfappl.apptpropiedad(
            id, estado, lista, longitud, requerido, tipodato, valor, valordefecto, 
            activo_id, categoria_id)
    VALUES 
(1, 0, FALSE, 5, TRUE, 0, 'GAU-589', NULL, 1, 3);


select act.*, cat.descripcion ,pro.valor from pltfappl.apptactivo act 
join pltfappl.apptcategoria cat on act.categoria_id = cat.id
join pltfappl.apptpropiedad pro on cat.id = pro.categoria_id;

select act.* from pltfappl.apptactivo act;
select cat.* from pltfappl.apptcategoria cat;
select pro.* from pltfappl.apptpropiedad pro;


/*
 PLTFSEGU.SEGTOBJETO
*/
INSERT INTO pltfsegu.segtobjeto(
            id, fechacreacion, fechamodificacion, codigo, descripcion, estado, 
            tipo, usuariocreacion_id)
values (1, '2012-08-09 05:16:36', '2012-08-09 05:16:36', 'usuarioMB', 'Bean de usuario', 0, 
            0, 1);

/*
 PLTFSEGU.SEGTOPCION
*/
INSERT INTO pltfsegu.segtopcion(id,descripcion,estado,etiqueta,fechaCreacion,fechaModificacion,nombre,target,padre_id,icono,usuariocreacion_id,objeto_id) 
VALUES (1,'MÃ³dulo de seguridad',0,'Seguridad','2012-08-05 22:19:31','2012-10-31 10:27:27','seguridad','/seguridad/usuario/index.jsf',NULL,'',1,1),
(2,'Mantenimiento de Usuarios',0,'Usuario','2012-08-08 20:54:01','2012-11-13 15:19:16','usuario','seguridad/usuario/index.jsf',1,'user.png',1,1),
(3,'Mantenimiento de Opciones',0,'Opción','2012-08-09 01:26:45','2012-10-04 23:00:39','Opcion','seguridad/opcion/index.jsf',1,'menu_item.png',1,1),
(4,'Mantenimiento de Roles',0,'Rol','2012-08-09 01:27:15','2012-10-04 23:01:30','rol','seguridad/rol/index.jsf',1,'role.png',1,1),
(5,'MensajerÃ­a',1,'MensajerÃ­a','2012-08-09 16:50:09','2012-08-10 09:14:45','mensajeria','/mensajeria',NULL,NULL,1,1),
(6,'Enviados',1,'Enviados','2012-08-09 16:51:19','2012-08-09 16:51:19','enviados','mensajeria/enviados/index.jsf',5,NULL,1,1),
(7,'Pantalla de recibidos',0,'Recibidos','2012-08-10 09:16:38','2012-08-10 09:16:38','recibidos','mensajeria/recibidos/index.jsf',5,NULL,1,1),
(8,'MÃ³dulo SincronizaciÃ³n',0,'SincronizaciÃ³n','2012-08-10 10:42:34','2012-08-10 10:42:34','sincronizacion','/sincronizacion',NULL,NULL,1,1),
(9,'Pantalla de conexiÃ³n inicial para sincronizacion',0,'ConexiÃ³n Inicial','2012-08-10 10:43:24','2012-10-09 17:26:34','conexion inicial','sincronizacion/conexion/index.jsf',8,'connection.png',1,1),
(10,'MÃ³dulo de Mantenimiento',0,'Mantenimiento','2012-09-02 12:33:52','2012-09-02 12:34:20','mantenimiento','/mantenimiento/*',NULL,NULL,1,1),
(11,'Mantenimiento de Alumnos',0,'Alumno','2012-09-02 12:35:14','2012-10-04 23:47:04','alumno','mantenimiento/alumno/index.jsf',10,'user_student.png',1,1),
(12,'Mantenimiento de Materia',0,'Materia','2012-09-02 14:42:04','2012-10-04 23:47:35','materia','mantenimiento/materia/index.jsf',10,'book.png',1,1),
(13,'Mantenimiento de Paralelos',0,'Paralelo','2012-09-02 15:57:36','2012-10-04 23:50:54','paralelo','mantenimiento/paralelo/index.jsf',10,'blackboard.png',1,1),
(14,'Mantenimiento de MatrÃ­culas',0,'MatrÃ­cula','2012-09-02 18:11:15','2012-10-04 23:59:17','matricula','mantenimiento/matricula/index.jsf',10,'book_reg.png',1,1),
(15,'Mantenimiento de Niveles',0,'Nivel','2012-09-02 19:43:54','2012-10-05 00:00:56','Nivel','mantenimiento/nivel/index.jsf',10,'level.png',1,1),
(16,'Mantenimiento de Horario',0,'Horario','2012-09-02 20:19:59','2012-10-05 00:04:28','horario','mantenimiento/horario/index.jsf',10,'clock.png',1,1),
(17,'Reporte de EnvÃ­o de Sms',0,'Rep. EnvÃ­o SMS','2012-09-02 22:34:17','2012-10-05 00:29:00','enviohSms','reportes/enviohSms/index.jsf',20,'report_go.png',1,1),
(18,'Reporte de RecepciÃ³n de Sms',0,'Rep. RecepcÃ­on SMS','2012-09-02 22:38:36','2012-10-05 00:29:13','recibehSms','reportes/recibehSms/index.jsf',20,'report_disk.png',1,1),
(19,'Reporte de E-Mails',0,'Rep. E-Mails','2012-09-02 22:40:05','2012-10-05 00:29:40','emailhSms','reportes/emailhSms/index.jsf',20,'email_send.png',1,1),
(20,'Modulo de reportes',0,'Reportes','2012-09-04 11:56:20','2012-09-04 11:56:20','reportes','/reportes',NULL,NULL,1,1),
(21,'Procesos de sincronizacion de tablas',0,'Procesos','2012-09-04 12:49:53','2012-10-04 23:43:02','procesos conexion','sincronizacion/procesos/index.jsf',8,'proceso_sync.png',1,1),
(22,'Ejecucion de procesos de sincronizacion',0,'Ejecucion','2012-09-04 12:50:42','2012-10-04 23:43:18','ejecucion','sincronizacion/ejecucion/index.jsf',8,'ejecucion_sync.png',1,1),
(24,'Modulo de Procesos',0,'Procesos','2012-09-04 23:19:10','2012-09-04 23:20:14','procesos','/procesos',NULL,NULL,1,1),
(25,'Definiciones de consultas Sms',0,'Definicion Consultas','2012-09-04 23:21:07','2012-10-05 00:34:00','definicion_sms','procesos/definicion/index.jsf',24,'dialog.png',1,1),
(26,'Mantenimiento de Consultas SMS',0,'Consulta','2012-09-05 22:33:32','2012-10-05 00:22:29','mant_consultas','mantenimiento/consulta/index.jsf',10,'question.png',1,1),
(27,'Envio masivo de Sms / Emails',0,'Envio Masivo','2012-09-06 01:31:09','2012-10-05 00:34:16','envio masivo','procesos/masivo/index.jsf',24,'masivo.png',1,1),
(28,'Mantenimiento de Deudas',0,'Deuda','2012-09-18 16:23:55','2012-10-05 00:23:42','deudas','mantenimiento/deuda/index.jsf',10,'money.png',1,1),
(29,'Modulo de administracion de la pagina',0,'Administracion','2012-09-21 17:04:37','2012-09-21 17:04:37','Administracion','/administracion/',NULL,NULL,1,1),
(30,'Parametros de Sms Server',0,'Parametros','2012-09-21 17:07:12','2012-10-05 00:37:42','Parametros','administracion/parametros/index.jsf',29,'setting.png',1,1),
(31,'Mantenimiento de personal administrativo',0,'Personal Adm.','2012-09-30 01:16:51','2012-10-05 00:25:59','personal','mantenimiento/personal/index.jsf',10,'user_suit.png',1,1),
(32,'Envio masivo de Sms / Emails a personal administrativo',0,'Envios personal Ad.','2012-09-30 21:56:37','2012-10-05 00:35:57','envio masivo personal','procesos/personal/index.jsf',24,'masivo_pers.png',1,1),
(33,'Parametros de conexion local para sincronizacion',0,'ConexiÃ³n Local','2012-10-09 17:25:55','2012-10-09 17:39:34','conexion local','sincronizacion/conLocal/index.jsf',8,'database.png',1,1),
(34,'ConfiguraciÃ³n',0,'ConfiguraciÃ³n','2012-10-31 10:21:52','2012-10-31 10:22:15','configuracion','administracion/configuracion/contenedor.jsf',29,'setting.png',1,1);

/*
 PLTFSEGU.SEGTROL
*/
INSERT INTO pltfsegu.segtrol(id,descripcion,estado,fechaCreacion,fechaModificacion,nombre,usuariocreacion_id) 
VALUES (1,'Admin',0,'2012-08-05 19:21:00','2012-10-16 21:06:27','Admin',1);

/*
 PLTFSEGU.SEGTROL_OPCION
*/
INSERT INTO pltfsegu.segtrol_opcion(id,crear,editar,eliminar,estado,exportar,fechaCreacion,fechaModificacion,opcion_id,rol_id,usuariocreacion_id) 
values (100,TRUE,TRUE,TRUE,0,TRUE,'2012-08-08 01:33:20','2012-09-04 12:11:10',1,1,1),
(2,TRUE,TRUE,TRUE,0,TRUE,'2012-08-08 20:56:31','2012-09-04 12:11:10',2,1,1),
(3,TRUE,TRUE,TRUE,0,TRUE,'2012-08-09 01:32:20','2012-09-04 12:11:10',3,1,1),
(4,TRUE,TRUE,TRUE,0,TRUE,'2012-08-09 01:32:36','2012-09-04 12:11:10',4,1,1),
(9,TRUE,TRUE,TRUE,1,TRUE,'2012-08-09 16:59:05','2012-08-17 23:17:58',5,1,1),
(10,TRUE,TRUE,TRUE,0,TRUE,'2012-08-09 17:00:01','2012-09-04 12:11:10',6,1,1),
(13,TRUE,TRUE,TRUE,0,TRUE,'2012-08-10 09:20:36','2012-08-10 09:20:36',7,1,1),
(17,TRUE,TRUE,TRUE,0,TRUE,'2012-08-10 10:43:39','2012-09-04 12:11:10',8,1,1),
(18,TRUE,TRUE,TRUE,0,TRUE,'2012-08-10 10:43:41','2012-09-04 12:11:10',9,1,1),
(19,TRUE,TRUE,TRUE,0,TRUE,'2012-09-02 12:36:03','2012-09-04 12:11:10',10,1,1),
(20,TRUE,TRUE,TRUE,0,TRUE,'2012-09-02 12:36:14','2012-09-04 12:11:10',11,1,1),
(21,TRUE,TRUE,TRUE,0,TRUE,'2012-09-02 14:43:08','2012-09-04 12:11:10',12,1,1),
(22,TRUE,TRUE,TRUE,0,TRUE,'2012-09-02 15:58:15','2012-09-04 12:11:10',13,1,1),
(23,TRUE,TRUE,TRUE,0,TRUE,'2012-09-02 18:23:19','2012-09-04 12:11:10',14,1,1),
(24,TRUE,TRUE,TRUE,0,TRUE,'2012-09-02 19:44:31','2012-09-04 12:11:10',15,1,1),
(25,TRUE,TRUE,TRUE,0,TRUE,'2012-09-02 20:20:25','2012-09-04 12:11:10',16,1,1),
(26,TRUE,TRUE,TRUE,0,TRUE,'2012-09-02 22:40:18','2012-09-04 12:11:10',17,1,1),
(27,TRUE,TRUE,TRUE,0,TRUE,'2012-09-02 22:40:20','2012-09-04 12:11:10',18,1,1),
(28,TRUE,TRUE,TRUE,0,TRUE,'2012-09-02 22:40:22','2012-09-04 12:11:10',19,1,1),
(29,TRUE,TRUE,TRUE,0,TRUE,'2012-09-04 11:58:43','2012-09-04 12:11:10',20,1,1),
(32,TRUE,TRUE,TRUE,0,TRUE,'2012-09-04 12:51:08','2012-09-04 12:51:27',21,1,1),
(33,TRUE,TRUE,TRUE,0,TRUE,'2012-09-04 12:51:11','2012-09-04 12:51:27',22,1,1),
(34,TRUE,TRUE,TRUE,0,TRUE,'2012-09-04 23:21:50','2012-09-04 23:22:20',24,1,1),
(35,TRUE,TRUE,TRUE,0,TRUE,'2012-09-04 23:21:53','2012-09-04 23:22:20',25,1,1),
(36,TRUE,TRUE,TRUE,0,TRUE,'2012-09-05 22:33:59','2012-09-05 22:34:09',26,1,1),
(37,TRUE,TRUE,TRUE,0,TRUE,'2012-09-06 01:31:33','2012-09-06 01:32:10',27,1,1),
(38,TRUE,TRUE,TRUE,0,TRUE,'2012-09-18 16:24:28','2012-09-18 16:24:40',28,1,1),
(39,TRUE,TRUE,TRUE,0,TRUE,'2012-09-21 17:07:45','2012-09-21 17:08:02',29,1,1),
(40,TRUE,TRUE,TRUE,0,TRUE,'2012-09-21 17:07:47','2012-09-21 17:08:02',30,1,1),
(41,TRUE,TRUE,TRUE,0,TRUE,'2012-09-30 01:17:13','2012-09-30 14:13:11',31,1,1),
(42,TRUE,TRUE,TRUE,0,TRUE,'2012-09-30 21:57:05','2012-09-30 21:57:16',32,1,1),
(44,TRUE,TRUE,TRUE,0,TRUE,'2012-10-09 17:27:01','2012-10-09 17:27:13',33,1,1),
(45,TRUE,TRUE,TRUE,0,TRUE,'2012-11-26 16:25:32','2012-11-26 16:25:50',34,1,1);

/*
 PLTFSEGU.SEGTUSUARIO_ROL
*/
INSERT INTO pltfsegu.segtusuario_rol(id,estado,fechaCreacion,fechaModificacion,rol_id,usuario_id,usuariocreacion_id) 
VALUES (1,0,'2012-08-09 06:19:53','2012-08-09 06:19:53',1,1,1);


/*
 PLTFAPPL.APPTMESSAGE_RESOURCE
*/
INSERT INTO pltfappl.apptmessage_resource(
            id, fechacreacion, fechamodificacion, code, locale, "value", 
            usuariocreacion_id, usuariomodificacion_id)
VALUES (1, '2013-02-15 00:00:00', '2013-02-15 00:00:00', 'systemAbout', 'es','Platform Project...', 1, 1),
    (2, '2013-02-15 00:00:00', '2013-02-15 00:00:00', 'systemAuthor', 'es','Eduardo Andrés Plúa Alay', 1, 1),
    (3, '2013-02-15 00:00:00', '2013-02-15 00:00:00', 'systemVersion', 'es','1.0.0', 1, 1),
    (4, '2013-02-15 00:00:00', '2013-02-15 00:00:00', 'systemCopyright', 'es','Platform Project 2013. Todos los Derechos Reservados.', 1, 1),
    (5, '2013-02-15 00:00:00', '2013-02-15 00:00:00', 'systemIngreso', 'es','Ingreso al sistema', 1, 1),
    (6, '2013-02-15 00:00:00', '2013-02-15 00:00:00', 'systemName', 'es','Platform Project', 1, 1),
    (7, '2013-02-15 00:00:00', '2013-02-15 00:00:00', 'systemYear', 'es','2013', 1, 1),
    (8, '2013-02-15 00:00:00', '2013-02-15 00:00:00', 'plActualizar', 'es','Actualizar', 1, 1),
    (9, '2013-02-15 00:00:00', '2013-02-15 00:00:00', 'plBuscar', 'es','Buscar', 1, 1),
    (10, '2013-02-15 00:00:00', '2013-02-15 00:00:00', 'plCambiar', 'es','Cambiar', 1, 1),
    (11, '2013-02-15 00:00:00', '2013-02-15 00:00:00', 'plCambiarPassword', 'es','Cambiar Contraseña', 1, 1),
    (12, '2013-02-15 00:00:00', '2013-02-15 00:00:00', 'plCancelar', 'es','Cancelar', 1, 1),
    (13, '2013-02-15 00:00:00', '2013-02-15 00:00:00', 'plCerrar', 'es','Cerrar', 1, 1),
    (14, '2013-02-15 00:00:00', '2013-02-15 00:00:00', 'plClave', 'es','Clave', 1, 1),
    (15, '2013-02-15 00:00:00', '2013-02-15 00:00:00', 'plConfiguracion', 'es','Configuración', 1, 1),
    (16, '2013-02-15 00:00:00', '2013-02-15 00:00:00', 'plConfirmarPassword', 'es','Confirmar Contraseña', 1, 1),
    (17, '2013-02-15 00:00:00', '2013-02-15 00:00:00', 'plCreado', 'es','Creado', 1, 1),
    (18, '2013-02-15 00:00:00', '2013-02-15 00:00:00', 'plCreadoPor', 'es','Creado Por', 1, 1),
    (19, '2013-02-15 00:00:00', '2013-02-15 00:00:00', 'plCuenta', 'es','Cuenta', 1, 1),
    (20, '2013-02-15 00:00:00', '2013-02-15 00:00:00', 'plDescripcion', 'es','Descripción', 1, 1),
    (21, '2013-02-15 00:00:00', '2013-02-15 00:00:00', 'plEditar', 'es','Editar', 1, 1),
    (22, '2013-02-15 00:00:00', '2013-02-15 00:00:00', 'plEliminar', 'es','Eliminar', 1, 1),
    (23, '2013-02-15 00:00:00', '2013-02-15 00:00:00', 'plEmptyDataTable', 'es','No existen registros', 1, 1),
    (24, '2013-02-15 00:00:00', '2013-02-15 00:00:00', 'plEtiqueta', 'es','Etiqueta', 1, 1),
    (25, '2013-02-15 00:00:00', '2013-02-15 00:00:00', 'plEstado', 'es','Estado', 1, 1),
    (26, '2013-02-15 00:00:00', '2013-02-15 00:00:00', 'plExportar', 'es','Exportar', 1, 1),
    (27, '2013-02-15 00:00:00', '2013-02-15 00:00:00', 'plGuardar', 'es','Guardar', 1, 1),
    (28, '2013-02-15 00:00:00', '2013-02-15 00:00:00', 'plIcono', 'es','Ícono', 1, 1),
    (29, '2013-02-15 00:00:00', '2013-02-15 00:00:00', 'plIngresar', 'es','Ingresar', 1, 1),
    (30, '2013-02-15 00:00:00', '2013-02-15 00:00:00', 'plIngreseClave', 'es','Ingrese Clave', 1, 1),
    (31, '2013-02-15 00:00:00', '2013-02-15 00:00:00', 'plIngreseUsuario', 'es','Ingrese Usuario', 1, 1),
    (32, '2013-02-15 00:00:00', '2013-02-15 00:00:00', 'plModificado', 'es','Modificado', 1, 1),
    (33, '2013-02-15 00:00:00', '2013-02-15 00:00:00', 'plModificadoPor', 'es','Modificado Por', 1, 1),
    (34, '2013-02-15 00:00:00', '2013-02-15 00:00:00', 'plNombre', 'es','Nombre', 1, 1),
    (35, '2013-02-15 00:00:00', '2013-02-15 00:00:00', 'plNombres', 'es','Nombres', 1, 1),
    (36, '2013-02-15 00:00:00', '2013-02-15 00:00:00', 'plNuevo', 'es','Nuevo', 1, 1),
    (37, '2013-02-15 00:00:00', '2013-02-15 00:00:00', 'plNuevoPassword', 'es','Nueva Contraseña', 1, 1),
    (38, '2013-02-15 00:00:00', '2013-02-15 00:00:00', 'plOpcion', 'es','Opción', 1, 1),
    (39, '2013-02-15 00:00:00', '2013-02-15 00:00:00', 'plPadre', 'es','Padre', 1, 1),
    (40, '2013-02-15 00:00:00', '2013-02-15 00:00:00', 'plPasswordActual', 'es','Contraseña Actual', 1, 1),
    (41, '2013-02-15 00:00:00', '2013-02-15 00:00:00', 'plParametros', 'es','Parámetros', 1, 1),
    (42, '2013-02-15 00:00:00', '2013-02-15 00:00:00', 'plPor', 'es','Por', 1, 1),
    (43, '2013-02-15 00:00:00', '2013-02-15 00:00:00', 'plRol', 'es','Rol', 1, 1),
    (44, '2013-02-15 00:00:00', '2013-02-15 00:00:00', 'plSalir', 'es','Salir', 1, 1),
    (45, '2013-02-15 00:00:00', '2013-02-15 00:00:00', 'plTarget', 'es','Target', 1, 1),
    (46, '2013-02-15 00:00:00', '2013-02-15 00:00:00', 'plUsername', 'es','Username', 1, 1),
    (47, '2013-02-15 00:00:00', '2013-02-15 00:00:00', 'plUsuario', 'es','Usuario', 1, 1);