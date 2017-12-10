-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         5.7.16-log - MySQL Community Server (GPL)
-- SO del servidor:              Win64
-- HeidiSQL Versión:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Volcando estructura de base de datos para webtopics
CREATE DATABASE IF NOT EXISTS `webtopics` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `webtopics`;

-- Volcando estructura para tabla webtopics.categorias
CREATE TABLE IF NOT EXISTS `categorias` (
  `idCategoria` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) DEFAULT NULL,
  `descripcion` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`idCategoria`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla webtopics.categorias: ~5 rows (aproximadamente)
DELETE FROM `categorias`;
/*!40000 ALTER TABLE `categorias` DISABLE KEYS */;
INSERT INTO `categorias` (`idCategoria`, `nombre`, `descripcion`) VALUES
	(1, 'Actualidad', 'El dia a dia del mundo'),
	(2, 'Deporte', 'El ultimo partido de la liga, el nuevo fichaje de tu equipo favorito, toda la actualidad deportiva'),
	(3, 'Tecnologia', 'Desde los aparatos mas retro hasta la tecnologia mas puntera'),
	(4, 'Entretenimiento', '¿Quien ganará los Oscar? ¿Habeis escuchado el ultimo disco de Iron Maiden?'),
	(5, 'Miscelanea', 'Cajon desastre, todo vale'),
	(6, 'Foro Moderadores', 'Foro privado para los moderadores');
/*!40000 ALTER TABLE `categorias` ENABLE KEYS */;

-- Volcando estructura para tabla webtopics.mensajes
CREATE TABLE IF NOT EXISTS `mensajes` (
  `idMensaje` int(11) NOT NULL AUTO_INCREMENT,
  `idTema` int(11) NOT NULL,
  `idUsuario` int(11) NOT NULL,
  `fechaCreacion` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `contenido` varchar(250) NOT NULL,
  `estado` set('n','b') NOT NULL DEFAULT 'n',
  PRIMARY KEY (`idMensaje`),
  KEY `FK_mensaje-tema` (`idTema`),
  KEY `FK_mensaje-usuario` (`idUsuario`),
  CONSTRAINT `FK_mensaje-tema` FOREIGN KEY (`idTema`) REFERENCES `temas` (`idTema`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_mensaje-usuario` FOREIGN KEY (`idUsuario`) REFERENCES `usuarios` (`idUsuario`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla webtopics.mensajes: ~8 rows (aproximadamente)
DELETE FROM `mensajes`;
/*!40000 ALTER TABLE `mensajes` DISABLE KEYS */;
INSERT INTO `mensajes` (`idMensaje`, `idTema`, `idUsuario`, `fechaCreacion`, `contenido`, `estado`) VALUES
	(13, 12, 14, '2017-12-10 15:22:15', 'Este mensaje ha sido eliminado por un moderador', 'b'),
	(14, 13, 11, '2017-12-10 15:40:55', 'Este mensaje ha sido eliminado por un moderador', 'b'),
	(15, 14, 11, '2017-12-10 15:41:14', 'Este mensaje ha sido eliminado por un moderador', 'b'),
	(16, 18, 8, '2017-12-10 15:42:26', 'Entendido', 'n'),
	(17, 16, 8, '2017-12-10 15:43:57', 'Este foro es para discutir noticias sobre tecnologia real, no ciencia ficcion. La proxima vez crea el tema en Miscelanea, que para algo esta', 'n'),
	(18, 18, 7, '2017-12-10 15:45:05', 'Recibido', 'n'),
	(19, 12, 7, '2017-12-10 15:51:10', 'Aunque este sea un foro general aun debes poner algo de esfuerzo en el tema. Tema cerrado', 'n'),
	(20, 17, 14, '2017-12-10 15:56:12', 'Seguro que ese iba como una cuba', 'n'),
	(21, 19, 7, '2017-12-10 18:41:33', 'Un recordatorio de las normas de este foro', 'n');
/*!40000 ALTER TABLE `mensajes` ENABLE KEYS */;

-- Volcando estructura para tabla webtopics.privados
CREATE TABLE IF NOT EXISTS `privados` (
  `idPrivado` int(11) NOT NULL AUTO_INCREMENT,
  `idCreador` int(11) NOT NULL,
  `idReceptor` int(11) NOT NULL,
  `titulo` varchar(50) NOT NULL DEFAULT 'Mensaje Privado',
  `contenido` varchar(250) NOT NULL,
  `fechaCreacion` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `leido` set('s','n') NOT NULL DEFAULT 's',
  PRIMARY KEY (`idPrivado`),
  KEY `FK_privado-creador` (`idCreador`),
  KEY `FK_privado-receptor` (`idReceptor`),
  CONSTRAINT `FK_privado-creador` FOREIGN KEY (`idCreador`) REFERENCES `usuarios` (`idUsuario`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_privado-receptor` FOREIGN KEY (`idReceptor`) REFERENCES `usuarios` (`idUsuario`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla webtopics.privados: ~0 rows (aproximadamente)
DELETE FROM `privados`;
/*!40000 ALTER TABLE `privados` DISABLE KEYS */;
/*!40000 ALTER TABLE `privados` ENABLE KEYS */;

-- Volcando estructura para tabla webtopics.temas
CREATE TABLE IF NOT EXISTS `temas` (
  `idTema` int(11) NOT NULL AUTO_INCREMENT,
  `idUsuario` int(11) NOT NULL,
  `idCategoria` int(11) NOT NULL,
  `titulo` varchar(50) NOT NULL,
  `fechaCreacion` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `mensajeInicial` varchar(500) NOT NULL,
  `estado` set('b','a') NOT NULL DEFAULT 'a',
  PRIMARY KEY (`idTema`),
  KEY `FK_tema-usuario` (`idUsuario`),
  KEY `FK_tema-categoria` (`idCategoria`),
  CONSTRAINT `FK_tema-categoria` FOREIGN KEY (`idCategoria`) REFERENCES `categorias` (`idCategoria`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_tema-usuario` FOREIGN KEY (`idUsuario`) REFERENCES `usuarios` (`idUsuario`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla webtopics.temas: ~8 rows (aproximadamente)
DELETE FROM `temas`;
/*!40000 ALTER TABLE `temas` DISABLE KEYS */;
INSERT INTO `temas` (`idTema`, `idUsuario`, `idCategoria`, `titulo`, `fechaCreacion`, `mensajeInicial`, `estado`) VALUES
	(12, 14, 5, 'WAAAGH', '2017-12-10 00:00:00', 'WAAAAAAAAAAAAAAGH!', 'b'),
	(13, 12, 2, 'Clasificacion Tour de Francia 2017', '2017-12-10 15:24:27', 'Chris Froome- Rigoberto Urán - Romain Bardet- Mikel Landa- Fabio Aru- Dan Martin- Simon Yates- Louis Meintjes - Alberto Contador - Warren Barguil', 'a'),
	(14, 13, 4, 'El ultimo Jedi', '2017-12-10 15:26:32', 'Quedan 4 dias para el estreno! Alguien mas ira a verla?', 'a'),
	(15, 13, 3, 'Coches Flotantes', '2017-12-10 15:29:29', 'Resulta que Renaul esta trabajando en coches flotantes', 'a'),
	(16, 10, 3, 'T-800 vs T-1000', '2017-12-10 00:00:00', 'Venga, hora de discusiones. Quien ganaria en 1 contra 1?', 'b'),
	(17, 12, 1, 'Detenido en Burgos por bajar escaleras en coche', '2017-12-10 15:33:12', 'Han detenido a un tipo que ha intentado bajar las escaleras de la catedral de burgos con su coche', 'a'),
	(18, 9, 6, 'Reglas de los Moderadores', '2017-12-10 15:38:39', 'El cometido principal de los moderadores es vigilar que se cumplan las reglas del foro, cerrar temas inactivos e informar a los usuarios de cualquier cambio en los foros.\r\nNo abuseis de vuestros privilegios, el estado de moderador se quita mas rapido que se consigue', 'a'),
	(19, 7, 3, 'Foro de tecnologia', '2017-12-10 18:41:14', 'El foro de tecnologia existe para discutir los ultimos avances tecnologicos', 'a');
/*!40000 ALTER TABLE `temas` ENABLE KEYS */;

-- Volcando estructura para tabla webtopics.usuarios
CREATE TABLE IF NOT EXISTS `usuarios` (
  `idUsuario` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(40) DEFAULT 'Anonimo',
  `apellidos` varchar(40) DEFAULT 'Anonimo',
  `apodo` varchar(40) NOT NULL,
  `email` varchar(60) NOT NULL,
  `tipo` set('e','m','a','b') NOT NULL,
  `clave` varchar(45) NOT NULL,
  `avatar` varchar(50) DEFAULT NULL,
  `fechaAlta` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`idUsuario`),
  UNIQUE KEY `apodo` (`apodo`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla webtopics.usuarios: ~9 rows (aproximadamente)
DELETE FROM `usuarios`;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` (`idUsuario`, `nombre`, `apellidos`, `apodo`, `email`, `tipo`, `clave`, `avatar`, `fechaAlta`) VALUES
	(5, 'Chuck', 'Norris', 'TheRealNorris', 'cnorris@email.com', 'm', 'RAQeZ6SOdikUgABmY5f/Uw==', '5.jpg', '2017-12-08 00:29:47'),
	(7, 'Bruce', 'Lee', 'FuriaOriental', 'blee@email.com', 'm', 'sPi0nyLHGOmST1sRZREaZw==', '7.jpg', '2017-12-10 14:12:34'),
	(8, 'Clint', 'Eastwood', '44Magnum', 'ceastwood@mail.com', 'm', 'i3GLh9cUXsyUD8IFq1dsBA==', '8.jpg', '2017-12-10 14:13:43'),
	(9, 'Conan', 'El Cimmerio', 'ReyConan', 'conan@email.com', 'a', 'ydwAT8PQOa1/tJRW5ZArAQ==', '9.jpg', '2017-12-10 14:16:21'),
	(10, 'Terminator', 'mk800', 'T-800', 't800@email.com', 'e', 'U43BG0tQdP6tKNGZEjvDfA==', '10.jpg', '2017-12-10 15:13:29'),
	(11, 'El gremlin', 'mojado', 'WetGremlin', 'grem@email.com', 'b', '4hLTqybEvZ5tZRCeyEySRQ==', '11.jpg', '2017-12-10 15:14:39'),
	(12, 'Amadís', 'de Gaula', 'Cabaclista', 'amadis@email.com', 'e', 'vaZ175y1a710aSg3cZWFCg==', '12.jpg', '2017-12-10 15:17:06'),
	(13, 'Morador', 'de las Arenas', 'SarlaccBait', 'tusken@email.com', 'e', 'uJ76M/99xajkNOzrq12iWg==', '13.jpg', '2017-12-10 15:18:50'),
	(14, 'Grimgor', 'IronHide', 'WAAAGH', 'grimgor@email.com', 'e', 'ccKTB7NHBSS/c3T8Td13wg==', '14.jpg', '2017-12-10 15:20:27'),
	(15, 'Paul', 'McCartney', 'BassBeatle', 'paul@mail.com', 'e', 'bGMhKrSOhAHq9rWbldgWqQ==', '15.jpg', '2017-12-10 18:38:00');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
