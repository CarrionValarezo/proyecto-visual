-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 30-07-2021 a las 03:21:17
-- Versión del servidor: 10.4.16-MariaDB
-- Versión de PHP: 7.4.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `sistema_facturacion`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categorias`
--

CREATE TABLE `categorias` (
  `ID_CAT` int(11) NOT NULL,
  `NOM_CAT` varchar(50) COLLATE utf8mb4_spanish_ci NOT NULL,
  `DES_CAT` varchar(300) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `EST_CAT` varchar(15) COLLATE utf8mb4_spanish_ci NOT NULL DEFAULT 'ACTIVO'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
  `ID_CLI` varchar(13) COLLATE utf8mb4_spanish_ci NOT NULL,
  `TIP_ID_CLI` varchar(6) COLLATE utf8mb4_spanish_ci NOT NULL,
  `NOM_CLI` varchar(45) COLLATE utf8mb4_spanish_ci NOT NULL,
  `APE_CLI` varchar(45) COLLATE utf8mb4_spanish_ci NOT NULL,
  `DIR_CLI` varchar(300) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `TEL_CLI` varchar(10) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `COR_ELE_CLI` varchar(100) COLLATE utf8mb4_spanish_ci NOT NULL,
  `EST_CLI` varchar(15) COLLATE utf8mb4_spanish_ci NOT NULL DEFAULT 'ACTIVO'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_ventas`
--

CREATE TABLE `detalle_ventas` (
  `NUM_FAC_PER` int(11) NOT NULL,
  `ID_PRO_PER` int(11) NOT NULL,
  `CAN_PRO` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

CREATE TABLE `productos` (
  `ID_PRO` int(11) NOT NULL,
  `COD_BAR_PRO` varchar(50) COLLATE utf8mb4_spanish_ci NOT NULL,
  `NOM_PRO` varchar(100) COLLATE utf8mb4_spanish_ci NOT NULL,
  `PRE_PRO` decimal(11,2) NOT NULL,
  `CAN_PRO` int(11) NOT NULL,
  `DES_PRO` varchar(300) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `IMA_PRO` varchar(50) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `EST_PRO` varchar(15) COLLATE utf8mb4_spanish_ci NOT NULL DEFAULT 'ACTIVO',
  `ID_CAT_PER` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `roles`
--

CREATE TABLE `roles` (
  `ID_ROL` int(11) NOT NULL,
  `NOM_ROL` varchar(25) COLLATE utf8mb4_spanish_ci NOT NULL,
  `DES_ROL` varchar(300) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `EST_ROL` varchar(15) COLLATE utf8mb4_spanish_ci NOT NULL DEFAULT 'ACTIVO'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `CED_USU` varchar(10) COLLATE utf8mb4_spanish_ci NOT NULL,
  `NOM_USU` varchar(45) COLLATE utf8mb4_spanish_ci NOT NULL,
  `APE_USU` varchar(45) COLLATE utf8mb4_spanish_ci NOT NULL,
  `DIR_USU` varchar(300) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `TEL_USU` varchar(10) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `COR_ELE_USU` varchar(100) COLLATE utf8mb4_spanish_ci NOT NULL,
  `CON_USU` varchar(100) COLLATE utf8mb4_spanish_ci NOT NULL,
  `EST_USU` varchar(15) COLLATE utf8mb4_spanish_ci NOT NULL DEFAULT 'ACTIVO',
  `ID_ROL_PER` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ventas`
--

CREATE TABLE `ventas` (
  `NUM_FAC` int(11) NOT NULL,
  `CED_USU_PER` varchar(10) COLLATE utf8mb4_spanish_ci NOT NULL,
  `ID_CLI_PER` varchar(10) COLLATE utf8mb4_spanish_ci NOT NULL,
  `FEC_VEN` date NOT NULL DEFAULT current_timestamp(),
  `MET_PAG_VEN` varchar(25) COLLATE utf8mb4_spanish_ci NOT NULL,
  `TOT_VEN` decimal(11,2) NOT NULL,
  `EST_VEN` varchar(25) COLLATE utf8mb4_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `categorias`
--
ALTER TABLE `categorias`
  ADD PRIMARY KEY (`ID_CAT`);

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`ID_CLI`),
  ADD UNIQUE KEY `COR_ELE_CLI_UNIQUE` (`COR_ELE_CLI`);

--
-- Indices de la tabla `detalle_ventas`
--
ALTER TABLE `detalle_ventas`
  ADD KEY `FK_DETALLE_VENTAS_VENTAS_idx` (`NUM_FAC_PER`),
  ADD KEY `FK_DETALLE_VENTAS_PRODUCTOS_idx` (`ID_PRO_PER`);

--
-- Indices de la tabla `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`ID_PRO`),
  ADD UNIQUE KEY `NOM_PRO_UNIQUE` (`NOM_PRO`),
  ADD KEY `FK_PRODUCTOS_CATEGORIAS_idx` (`ID_CAT_PER`);

--
-- Indices de la tabla `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`ID_ROL`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`CED_USU`),
  ADD UNIQUE KEY `COR_ELE_USU_UNIQUE` (`COR_ELE_USU`),
  ADD KEY `FK_USUARIO_ROL_idx` (`ID_ROL_PER`);

--
-- Indices de la tabla `ventas`
--
ALTER TABLE `ventas`
  ADD PRIMARY KEY (`NUM_FAC`),
  ADD KEY `FK_VENTAS_CLIENTES_idx` (`ID_CLI_PER`),
  ADD KEY `FK_VENTAS_USUARIOS_idx` (`CED_USU_PER`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `categorias`
--
ALTER TABLE `categorias`
  MODIFY `ID_CAT` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `productos`
--
ALTER TABLE `productos`
  MODIFY `ID_PRO` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `roles`
--
ALTER TABLE `roles`
  MODIFY `ID_ROL` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `ventas`
--
ALTER TABLE `ventas`
  MODIFY `NUM_FAC` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `detalle_ventas`
--
ALTER TABLE `detalle_ventas`
  ADD CONSTRAINT `FK_DETALLE_VENTAS_PRODUCTOS` FOREIGN KEY (`ID_PRO_PER`) REFERENCES `productos` (`ID_PRO`) ON DELETE CASCADE ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_DETALLE_VENTAS_VENTAS` FOREIGN KEY (`NUM_FAC_PER`) REFERENCES `ventas` (`NUM_FAC`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Filtros para la tabla `productos`
--
ALTER TABLE `productos`
  ADD CONSTRAINT `FK_PRODUCTOS_CATEGORIAS` FOREIGN KEY (`ID_CAT_PER`) REFERENCES `categorias` (`ID_CAT`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Filtros para la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD CONSTRAINT `FK_USUARIO_ROL` FOREIGN KEY (`ID_ROL_PER`) REFERENCES `roles` (`ID_ROL`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Filtros para la tabla `ventas`
--
ALTER TABLE `ventas`
  ADD CONSTRAINT `FK_VENTAS_CLIENTES` FOREIGN KEY (`ID_CLI_PER`) REFERENCES `clientes` (`ID_CLI`) ON DELETE CASCADE ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_VENTAS_USUARIOS` FOREIGN KEY (`CED_USU_PER`) REFERENCES `usuarios` (`CED_USU`) ON DELETE CASCADE ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
