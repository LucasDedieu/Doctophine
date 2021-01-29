-- phpMyAdmin SQL Dump
-- version 5.0.3
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : sam. 30 jan. 2021 à 00:35
-- Version du serveur :  10.4.14-MariaDB
-- Version de PHP : 7.2.34

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `doctophine`
--

-- --------------------------------------------------------

--
-- Structure de la table `activity`
--

CREATE TABLE `activity` (
  `id` int(100) NOT NULL,
  `doctor_ID` int(100) NOT NULL,
  `medical_center_ID` int(100) NOT NULL,
  `speciality_ID` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `activity`
--

INSERT INTO `activity` (`id`, `doctor_ID`, `medical_center_ID`, `speciality_ID`) VALUES
(1, 1, 1, 1),
(2, 2, 3, 2);

-- --------------------------------------------------------

--
-- Structure de la table `appointment`
--

CREATE TABLE `appointment` (
  `id` int(100) NOT NULL,
  `activity_ID` int(100) NOT NULL,
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  `patient_ID` int(100) NOT NULL,
  `description` text NOT NULL,
  `is_video` tinyint(4) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `appointment`
--

INSERT INTO `appointment` (`id`, `activity_ID`, `start_date`, `end_date`, `patient_ID`, `description`, `is_video`) VALUES
(1, 1, '2021-01-20 15:00:00', '2021-01-20 15:30:00', 1, 'Description', 0),
(2, 1, '2021-01-13 18:00:00', '2021-01-13 18:30:00', 1, 'Description', 0);

-- --------------------------------------------------------

--
-- Structure de la table `availability`
--

CREATE TABLE `availability` (
  `id` int(100) NOT NULL,
  `activity_ID` int(100) NOT NULL,
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `availability`
--

INSERT INTO `availability` (`id`, `activity_ID`, `start_date`, `end_date`) VALUES
(1, 1, '2021-01-20 15:30:00', '2021-01-20 16:00:00');

-- --------------------------------------------------------

--
-- Structure de la table `doctor`
--

CREATE TABLE `doctor` (
  `id` int(100) NOT NULL,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  `address` text DEFAULT NULL,
  `creation_date` date DEFAULT NULL,
  `birth_date` date DEFAULT NULL,
  `is_admin` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `doctor`
--

INSERT INTO `doctor` (`id`, `first_name`, `last_name`, `email`, `password`, `phone`, `status`, `address`, `creation_date`, `birth_date`, `is_admin`) VALUES
(1, 'Yves', 'Claude', 'yves.claude@example.org', 'coucou', '0123456789', NULL, NULL, NULL, NULL, 0),
(2, 'Paul', 'Molaire', 'paul.molaire@example.org', 'coucou', NULL, NULL, NULL, NULL, NULL, 0),
(3, 'Alice', 'Ventricule', 'alice.ventricule@example.org\r\n', 'coucou', NULL, NULL, NULL, NULL, NULL, 0);

-- --------------------------------------------------------

--
-- Structure de la table `medical_center`
--

CREATE TABLE `medical_center` (
  `id` int(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `address` text DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `medical_center`
--

INSERT INTO `medical_center` (`id`, `name`, `address`, `phone`) VALUES
(1, 'Hopital Necker', 'Paris', '0123456789'),
(3, 'Hopital Hotel-Dieu', 'Paris', '0123456789'),
(4, 'Hopital Saint-Antoine', 'Paris', '0123456789'),
(5, 'Hopital Test', '3 rue test', '0123456789');

-- --------------------------------------------------------

--
-- Structure de la table `patient`
--

CREATE TABLE `patient` (
  `id` int(100) NOT NULL,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  `address` text DEFAULT NULL,
  `creation_date` date DEFAULT NULL,
  `birth_date` date DEFAULT NULL,
  `is_admin` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `patient`
--

INSERT INTO `patient` (`id`, `first_name`, `last_name`, `email`, `password`, `phone`, `status`, `address`, `creation_date`, `birth_date`, `is_admin`) VALUES
(1, 'Lucas', 'Dedieu', 'lucas.dedieu7@gmail.com', 'coucou', NULL, NULL, NULL, NULL, NULL, 0),
(2, 'Anis', 'SI YOUCEF\r\n   ', 'anis@gmail.com', 'gzerge', NULL, NULL, NULL, NULL, NULL, 0),
(3, 'Samy', 'HAOUCHINE', 'samy', 'grege', NULL, NULL, NULL, NULL, NULL, 0),
(4, 'Yves-Fredricq', 'ADLER', 'bgrzeg', 'eggre', NULL, NULL, NULL, NULL, NULL, 0);

-- --------------------------------------------------------

--
-- Structure de la table `speciality`
--

CREATE TABLE `speciality` (
  `id` int(100) NOT NULL,
  `name` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `speciality`
--

INSERT INTO `speciality` (`id`, `name`) VALUES
(1, 'Généraliste'),
(2, 'Dentiste'),
(3, 'ORL'),
(4, 'Cardiologue');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `activity`
--
ALTER TABLE `activity`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_doctor_id` (`doctor_ID`),
  ADD KEY `fk_medical_center_id` (`medical_center_ID`),
  ADD KEY `fk_speciality_id` (`speciality_ID`);

--
-- Index pour la table `appointment`
--
ALTER TABLE `appointment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_appointment_activity_id` (`activity_ID`),
  ADD KEY `fk_appointment_patient_id` (`patient_ID`);

--
-- Index pour la table `availability`
--
ALTER TABLE `availability`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_availability_activity_id` (`activity_ID`);

--
-- Index pour la table `doctor`
--
ALTER TABLE `doctor`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `medical_center`
--
ALTER TABLE `medical_center`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `patient`
--
ALTER TABLE `patient`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `speciality`
--
ALTER TABLE `speciality`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `activity`
--
ALTER TABLE `activity`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `appointment`
--
ALTER TABLE `appointment`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `availability`
--
ALTER TABLE `availability`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `doctor`
--
ALTER TABLE `doctor`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pour la table `medical_center`
--
ALTER TABLE `medical_center`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT pour la table `patient`
--
ALTER TABLE `patient`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pour la table `speciality`
--
ALTER TABLE `speciality`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `activity`
--
ALTER TABLE `activity`
  ADD CONSTRAINT `fk_doctor_id` FOREIGN KEY (`doctor_ID`) REFERENCES `doctor` (`id`),
  ADD CONSTRAINT `fk_medical_center_id` FOREIGN KEY (`medical_center_ID`) REFERENCES `medical_center` (`id`),
  ADD CONSTRAINT `fk_speciality_id` FOREIGN KEY (`speciality_ID`) REFERENCES `speciality` (`id`);

--
-- Contraintes pour la table `appointment`
--
ALTER TABLE `appointment`
  ADD CONSTRAINT `fk_appointment_activity_id` FOREIGN KEY (`activity_ID`) REFERENCES `activity` (`id`),
  ADD CONSTRAINT `fk_appointment_patient_id` FOREIGN KEY (`patient_ID`) REFERENCES `patient` (`id`);

--
-- Contraintes pour la table `availability`
--
ALTER TABLE `availability`
  ADD CONSTRAINT `fk_availability_activity_id` FOREIGN KEY (`activity_ID`) REFERENCES `activity` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
