-- phpMyAdmin SQL Dump
-- version 4.5.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Erstellungszeit: 25. Sep 2016 um 18:04
-- Server-Version: 10.1.16-MariaDB
-- PHP-Version: 7.0.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `Sensoren`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `Abteilung`
--

CREATE TABLE `Abteilung` (
  `ID` int(10) NOT NULL,
  `Abteilungstyp_ID` int(11) NOT NULL,
  `Markt_ID` int(20) NOT NULL,
  `Warenbereichsleiter_id` int(20) NOT NULL,
  `Server` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `Abteilung`
--

INSERT INTO `Abteilung` (`ID`, `Abteilungstyp_ID`, `Markt_ID`, `Warenbereichsleiter_id`, `Server`) VALUES
(14, 0, 7, 0, ''),
(15, 0, 3, 0, ''),
(16, 0, 10, 3, ''),
(19, 20, 13, 6, 'Serveradresse 2');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `Abteilungstyp`
--

CREATE TABLE `Abteilungstyp` (
  `ID` int(10) NOT NULL,
  `Land` varchar(20) NOT NULL,
  `Bezeichnung` varchar(100) NOT NULL,
  `Grenzwert` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `Abteilungstyp`
--

INSERT INTO `Abteilungstyp` (`ID`, `Land`, `Bezeichnung`, `Grenzwert`) VALUES
(17, 'RO', 'MoPro', 4),
(20, 'CZ', 'Fleischabteilung', 5),
(21, 'CZ', 'Dry_Aged_Fleisch', 8),
(22, 'HR', 'Fleisch_HR', 4);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `Markt`
--

CREATE TABLE `Markt` (
  `ID` int(10) NOT NULL,
  `Laenderkuerzel` varchar(50) NOT NULL,
  `Marktnummer` int(50) NOT NULL,
  `Oeffnungszeiten_von` time NOT NULL,
  `Oeffnungszeiten_bis` time NOT NULL,
  `Hausleiter_id` int(20) NOT NULL,
  `Verkaufsleiter_id` int(20) NOT NULL,
  `Leitstand_id` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `Markt`
--

INSERT INTO `Markt` (`ID`, `Laenderkuerzel`, `Marktnummer`, `Oeffnungszeiten_von`, `Oeffnungszeiten_bis`, `Hausleiter_id`, `Verkaufsleiter_id`, `Leitstand_id`) VALUES
(4, 'DE', 1000, '00:00:00', '00:00:00', 0, 0, 0),
(5, 'DE', 1900, '00:00:00', '00:00:00', 0, 0, 0),
(6, 'HR', 1000, '00:00:00', '00:00:00', 0, 0, 0),
(7, 'DE', 3000, '00:00:00', '00:00:00', 0, 0, 0),
(8, 'PL', 1000, '00:00:00', '00:00:00', 0, 0, 0),
(9, 'DE', 3000, '00:00:00', '00:00:00', 0, 0, 0),
(10, 'SK', 1900, '02:00:00', '21:00:00', 9, 2, 7),
(11, 'BG', 1000, '11:31:00', '17:40:00', 11, 5, 7),
(13, 'CZ', 4100, '02:30:00', '14:30:00', 4, 5, 7);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `Sensordaten`
--

CREATE TABLE `Sensordaten` (
  `ID` int(10) NOT NULL,
  `LogischeSensor_ID` varchar(100) NOT NULL,
  `Wert` varchar(50) NOT NULL,
  `Timestamp` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `Sensordaten`
--

INSERT INTO `Sensordaten` (`ID`, `LogischeSensor_ID`, `Wert`, `Timestamp`) VALUES
(1, '12345', '605.0', '2016-09-18 17:32:59'),
(2, '12345', '750.0', '2016-09-18 17:33:01'),
(3, '4711', '42', '2016-09-18 17:56:42'),
(4, '12345', '332.0', '2016-09-18 20:16:45');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `Sensoren`
--

CREATE TABLE `Sensoren` (
  `ID` int(10) NOT NULL,
  `Markt_ID` int(12) NOT NULL,
  `Abteilung_ID` int(20) NOT NULL,
  `Hersteller` varchar(200) NOT NULL,
  `Sensortyp` varchar(100) NOT NULL,
  `Hardware_ID` varchar(100) NOT NULL,
  `Logische_ID` varchar(100) NOT NULL,
  `Status` varchar(20) NOT NULL,
  `MqttGateway` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `Sensoren`
--

INSERT INTO `Sensoren` (`ID`, `Markt_ID`, `Abteilung_ID`, `Hersteller`, `Sensortyp`, `Hardware_ID`, `Logische_ID`, `Status`, `MqttGateway`) VALUES
(1, 2, 3, 'EnOcean', 'Temperatursensor', 'HARDWARE_ID', 'DE_3000_MoPro_sensor1', 'passiv', 'yowhanpi.ddns.net:1883'),
(2, 2, 3, 'Eclipse', 'Softwaretest', 'HARDWARE_ID', 'kmunda_sens1', 'aktiv', 'iot.eclipse.org:1883'),
(3, 10, 16, 'EnOcean', 'Temperatursensor', 'HARDWAREID 1213131', 'SK_1900_Kuehlhaus_Fleischbedienung_1', 'aktiv', 'iot.eclipse.org:1883'),
(4, 13, 19, 'EnOcean', 'Temperatursensor', '//new ID lalala', 'CZ_4100 _Fleischabteilung_1', 'aktiv', 'iot.eclipse.org:1883'),
(5, 13, 19, 'EnOcean', 'Temperatursensor', '//neue Hardware ID', 'CZ_4100 _Fleischabteilung_2', 'aktiv', 'yowhanpi.ddns.net:1883');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `User`
--

CREATE TABLE `User` (
  `ID` int(11) NOT NULL,
  `Land` varchar(10) NOT NULL,
  `Anrede` varchar(20) NOT NULL,
  `Vorname` varchar(100) NOT NULL,
  `Nachname` varchar(100) NOT NULL,
  `Email` varchar(100) NOT NULL,
  `Telefon` varchar(20) NOT NULL,
  `Rolle` varchar(100) NOT NULL,
  `M_registration_token` varchar(100) DEFAULT NULL,
  `M_os` int(11) DEFAULT NULL,
  `M_is_available` tinyint(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `User`
--

INSERT INTO `User` (`ID`, `Land`, `Anrede`, `Vorname`, `Nachname`, `Email`, `Telefon`, `Rolle`, `M_registration_token`, `M_os`, `M_is_available`) VALUES
(1, 'CZ', 'Herr', 'Testname (WBL)', 'Hallouser 1', 'mail@mail.de', '123456', 'WBL', '', 0, 0),
(2, 'CZ', 'Frau', 'VKL123', 'lalaa', 'test@vkl.de', '4512622', 'VKL', '', 0, 0),
(3, 'CZ', 'Herr', 'WBL123', 'Nummer 2', 'mail@wbl.de', '986069', 'WBL', '', 0, 0),
(4, 'CZ', 'Frau', 'HL 2', 'Hausleiterin', 'HL@kfland.com', '93813784014', 'HL', '', 0, 0),
(5, 'CZ', 'Herr', 'WKL12', 'Hallo123', 'test@test.de', '626262652', 'VKL', '', 0, 0),
(6, 'CZ', 'Herr', 'WBL3', 'ICH bins', 'hallo@test.de', '5551514313', 'WBL', '', 0, 0),
(7, 'CZ', 'Herr', 'Störung', 'Leitstand 1', 'test@laitstand.de', '51325224', 'LS', '', 0, 0),
(8, 'CZ', 'Frau', 'Incident', 'Leitstand 2', 'leit@stand.de', '625244421', 'LS', '', 0, 0),
(9, 'CZ', 'Frau', 'HL1', 'Hausleiterin Nummer 1', 'test@hl.de', '14113556143', 'HL', '', 0, 0),
(10, 'CZ', 'Frau', 'WBL Frau 212', 'TEst', 'mail@mail.de', '1441113123', 'WBL', '', 0, 0),
(11, 'HR', 'Frau', 'adadwdad', '3qadaaada', 'mail@test.de', '1123131112', 'HL', NULL, NULL, NULL),
(13, 'HR', 'Frau', 'WBL HR', 'adaddwda', 'test@test.de', '1113131145134', 'WBL', NULL, NULL, NULL);

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `Abteilung`
--
ALTER TABLE `Abteilung`
  ADD PRIMARY KEY (`ID`);

--
-- Indizes für die Tabelle `Abteilungstyp`
--
ALTER TABLE `Abteilungstyp`
  ADD PRIMARY KEY (`ID`);

--
-- Indizes für die Tabelle `Markt`
--
ALTER TABLE `Markt`
  ADD PRIMARY KEY (`ID`);

--
-- Indizes für die Tabelle `Sensordaten`
--
ALTER TABLE `Sensordaten`
  ADD PRIMARY KEY (`ID`);

--
-- Indizes für die Tabelle `Sensoren`
--
ALTER TABLE `Sensoren`
  ADD PRIMARY KEY (`ID`);

--
-- Indizes für die Tabelle `User`
--
ALTER TABLE `User`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `Abteilung`
--
ALTER TABLE `Abteilung`
  MODIFY `ID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;
--
-- AUTO_INCREMENT für Tabelle `Abteilungstyp`
--
ALTER TABLE `Abteilungstyp`
  MODIFY `ID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;
--
-- AUTO_INCREMENT für Tabelle `Markt`
--
ALTER TABLE `Markt`
  MODIFY `ID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;
--
-- AUTO_INCREMENT für Tabelle `Sensordaten`
--
ALTER TABLE `Sensordaten`
  MODIFY `ID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT für Tabelle `Sensoren`
--
ALTER TABLE `Sensoren`
  MODIFY `ID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT für Tabelle `User`
--
ALTER TABLE `User`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
