CREATE DATABASE  IF NOT EXISTS `proyecto` ;
USE proyecto;
--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;

CREATE TABLE `usuarios` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `token` varchar(50) NULL DEFAULT NUll,
  `rol` varchar (10) not null default "usuario",
  PRIMARY KEY (`id`),
  UNIQUE (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `proyectos`
--

DROP TABLE IF EXISTS `proyectos`;

CREATE TABLE `proyectos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `descripcion` text,
  `propietario_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_proyectos_usuarios` (`propietario_id`),
  CONSTRAINT `fk_proyectos_usuarios` FOREIGN KEY (`propietario_id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `columnas`
--

DROP TABLE IF EXISTS `columnas`;

CREATE TABLE `columnas` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `proyecto_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_columnas_proyectos` (`proyecto_id`),
  CONSTRAINT `fk_columnas_proyectos` FOREIGN KEY (`proyecto_id`) REFERENCES `proyectos` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `tareas`
--

DROP TABLE IF EXISTS `tareas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tareas` (
  `id` int NOT NULL AUTO_INCREMENT,
  `titulo` varchar(50) NOT NULL,
  `descripcion` text,
  `proyecto_id` int NOT NULL,
  `columnas_id` int NOT NULL,
  `creador_id` int NOT NULL,
  `responsable_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tareas_proyectos` (`proyecto_id`),
  KEY `fk_tareas_columnas` (`columnas_id`),
  KEY `fk_tareas_usuarioscreador` (`creador_id`),
  KEY `fk_tareas_usuariosresponsable` (`responsable_id`),
  CONSTRAINT `fk_tareas_columnas` FOREIGN KEY (`columnas_id`) REFERENCES `columnas` (`id`),
  CONSTRAINT `fk_tareas_proyectos` FOREIGN KEY (`proyecto_id`) REFERENCES `proyectos` (`id`),
  CONSTRAINT `fk_tareas_usuarioscreador` FOREIGN KEY (`creador_id`) REFERENCES `usuarios` (`id`),
  CONSTRAINT `fk_tareas_usuariosresponsable` FOREIGN KEY (`responsable_id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `proyectos_usuarios`
-- Esta tabla relaciona a los participantes con su proyecto NO PROPIETARIOS ESO ES EN TBL PROYECTO
--

DROP TABLE IF EXISTS `proyectos_usuarios`;

CREATE TABLE `proyectos_usuarios` (
  `proyecto_id` int NOT NULL,
  `usuario_id` int NOT NULL,
  PRIMARY KEY (`proyecto_id`,`usuario_id`),
  KEY `fk_proyectos_usuarios_usuarios` (`usuario_id`),
  CONSTRAINT `fk_proyectos_usuarios_proyectos` FOREIGN KEY (`proyecto_id`) REFERENCES `proyectos` (`id`),
  CONSTRAINT `fk_proyectos_usuarios_usuarios` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `comentarios`
--

DROP TABLE IF EXISTS `comentarios`;

CREATE TABLE `comentarios` (
  `id` int NOT NULL AUTO_INCREMENT,
  `comentario` text NOT NULL,
  `tarea_id` int NOT NULL,
  `usuario_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_comentarios_tareas` (`tarea_id`),
  KEY `fk_comentarios_usuarios` (`usuario_id`),
  CONSTRAINT `fk_comentarios_tareas` FOREIGN KEY (`tarea_id`) REFERENCES `tareas` (`id`),
  CONSTRAINT `fk_comentarios_usuarios` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
