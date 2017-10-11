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
CREATE DATABASE IF NOT EXISTS `webtopics`  DEFAULT CHARACTER SET utf8;
USE `webtopics`;

-- Volcando estructura para tabla webtopics.amigos
CREATE TABLE IF NOT EXISTS `amigos` (
  `idUsuario` int(11) NOT NULL,
  `idAmigo` int(11) NOT NULL,
  `estado` set('p','a') NOT NULL DEFAULT 'p',
  PRIMARY KEY (`idUsuario`,`idAmigo`),
  KEY `FK_amigo-amigo` (`idAmigo`),
  CONSTRAINT `FK_amigo-amigo` FOREIGN KEY (`idAmigo`) REFERENCES `usuarios` (`idUsuario`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_amigo-usuario` FOREIGN KEY (`idUsuario`) REFERENCES `usuarios` (`idUsuario`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla webtopics.amigos: ~0 rows (aproximadamente)
DELETE FROM `amigos`;
/*!40000 ALTER TABLE `amigos` DISABLE KEYS */;
/*!40000 ALTER TABLE `amigos` ENABLE KEYS */;

-- Volcando estructura para tabla webtopics.categorias
CREATE TABLE IF NOT EXISTS `categorias` (
  `idCategoria` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) DEFAULT NULL,
  `descripcion` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`idCategoria`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla webtopics.categorias: ~5 rows (aproximadamente)
DELETE FROM `categorias`;
/*!40000 ALTER TABLE `categorias` DISABLE KEYS */;
INSERT INTO `categorias` (`idCategoria`, `nombre`, `descripcion`) VALUES
	(1, 'Actualidad', 'El dia a dia del mundo'),
	(2, 'Deporte', 'El ultimo partido de la liga, el nuevo fichaje de tu equipo favorito, toda la actualidad deportiva'),
	(3, 'Tecnologia', 'Desde los aparatos mas retro hasta la tecnologia mas puntera'),
	(4, 'Entretenimiento', '¿Quien ganará los Oscar? ¿Habeis escuchado el ultimo disco de Iron Maiden?'),
	(5, 'Miscelanea', 'Cajon desastre, todo vale');
/*!40000 ALTER TABLE `categorias` ENABLE KEYS */;

-- Volcando estructura para tabla webtopics.mensajes
CREATE TABLE IF NOT EXISTS `mensajes` (
  `idMensaje` int(11) NOT NULL AUTO_INCREMENT,
  `idTema` int(11) NOT NULL,
  `idUsuario` int(11) NOT NULL,
  `fechaCreacion` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `contenido` varchar(250) NOT NULL,
  PRIMARY KEY (`idMensaje`),
  KEY `FK_mensaje-tema` (`idTema`),
  KEY `FK_mensaje-usuario` (`idUsuario`),
  CONSTRAINT `FK_mensaje-tema` FOREIGN KEY (`idTema`) REFERENCES `temas` (`idTema`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_mensaje-usuario` FOREIGN KEY (`idUsuario`) REFERENCES `usuarios` (`idUsuario`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla webtopics.mensajes: ~0 rows (aproximadamente)
DELETE FROM `mensajes`;
/*!40000 ALTER TABLE `mensajes` DISABLE KEYS */;
/*!40000 ALTER TABLE `mensajes` ENABLE KEYS */;

-- Volcando estructura para tabla webtopics.privados
CREATE TABLE IF NOT EXISTS `privados` (
  `idPrivado` int(11) NOT NULL AUTO_INCREMENT,
  `idCreador` int(11) NOT NULL,
  `idReceptor` int(11) NOT NULL,
  `titulo` varchar(50) NOT NULL DEFAULT 'Mensaje Privado',
  `contenido` varchar(250) NOT NULL,
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
  `mensajeInicial` varchar(250) NOT NULL,
  `estado` set('b','a') NOT NULL DEFAULT 'a',
  PRIMARY KEY (`idTema`),
  KEY `FK_tema-usuario` (`idUsuario`),
  KEY `FK_tema-categoria` (`idCategoria`),
  CONSTRAINT `FK_tema-categoria` FOREIGN KEY (`idCategoria`) REFERENCES `categorias` (`idCategoria`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_tema-usuario` FOREIGN KEY (`idUsuario`) REFERENCES `usuarios` (`idUsuario`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla webtopics.temas: ~4 rows (aproximadamente)
DELETE FROM `temas`;
/*!40000 ALTER TABLE `temas` DISABLE KEYS */;
INSERT INTO `temas` (`idTema`, `idUsuario`, `idCategoria`, `titulo`, `fechaCreacion`, `mensajeInicial`, `estado`) VALUES
	(1, 3, 1, 'prueba Actualidad', '2017-10-09 23:25:59', 'prueba de tema', 'a'),
	(2, 3, 2, ' prueba Deporte', '2017-10-09 23:26:20', 'prueba de tema', 'a'),
	(3, 3, 3, 'prueba Tecnologia', '2017-10-09 23:26:36', 'prueba de tema', 'a'),
	(4, 3, 4, 'prueba Entretenimiento', '2017-10-09 23:26:51', 'prueba de tema', 'a'),
	(5, 3, 5, 'prueba Miscelanea', '2017-10-09 23:27:06', 'prueba de tema', 'a');
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla webtopics.usuarios: ~0 rows (aproximadamente)
DELETE FROM `usuarios`;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` (`idUsuario`, `nombre`, `apellidos`, `apodo`, `email`, `tipo`, `clave`, `avatar`, `fechaAlta`) VALUES
	(3, 'Prueba', 'Prueba', 'Prueba', 'prueba@prueba.com', 'e', 'yJO61okntFfb7TlGDmr9Yg==', '3.jpg', '2017-10-01 19:00:12');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
