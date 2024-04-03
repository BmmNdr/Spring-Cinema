-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Apr 03, 2024 alle 18:16
-- Versione del server: 10.4.32-MariaDB
-- Versione PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cinema`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `movies`
--

CREATE TABLE `movies` (
  `id` int(11) NOT NULL,
  `title` varchar(64) NOT NULL,
  `release_year` int(4) NOT NULL,
  `description` text NOT NULL,
  `imagePath` varchar(32) DEFAULT NULL,
  `genre` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `movies`
--

INSERT INTO `movies` (`id`, `title`, `release_year`, `description`, `imagePath`, `genre`) VALUES
(1, 'The Shawshank Redemption', 1994, 'Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.', 'ShawshankRedemption.jpg', 'Thriller/Crime'),
(3, 'The Dark Knight', 2008, 'When the menace known as The Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.', 'DarkKnight.jpg', 'Action/Crime'),
(4, 'Pulp Fiction', 1994, 'The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption.', 'PulpFiction.jpg', 'Crime/Thriller'),
(5, 'Schindler\'s List', 1993, 'In German-occupied Poland during World War II, industrialist Oskar Schindler gradually becomes concerned for his Jewish workforce after witnessing their persecution by the Nazis.', 'Schindlers.jpg', 'War/Documentary'),
(6, 'The Lord of the Rings: The Return of the King', 2003, 'Gandalf and Aragorn lead the World of Men against Sauron\'s army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring.', 'LOTR.jpg', 'Fantasy/Adventure'),
(7, 'Forrest Gump', 1994, 'The presidencies of Kennedy and Johnson, the events of Vietnam, Watergate, and other historical events unfold from the perspective of an Alabama man with an IQ of 75, whose only desire is to be reunited with his childhood sweetheart.', 'ForrestGump.jpg', 'Comedy/Romance'),
(8, 'Inception', 2010, 'A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.', 'Inception.jpg', 'Sci-fi/Action'),
(9, 'The Matrix', 1999, 'A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.', 'Matrix.jpg', 'Action/Sci-fi'),
(10, 'Interstellar', 2014, 'A team of explorers travel through a wormhole in space in an attempt to ensure humanity\'s survival.', 'Interstellar.jpg', 'Sci-fi/Adventure'),
(11, 'The Silence of the Lambs', 1991, 'A young F.B.I. cadet must receive the help of an incarcerated and manipulative cannibal killer to help catch another serial killer, a madman who skins his victims.', 'SilenceLambs.jpg', 'Horror/Crime'),
(12, 'The Green Mile', 1999, 'The lives of guards on Death Row are affected by one of their charges: a black man accused of child murder and rape, yet who has a mysterious gift.', 'GreenMile.jpg', 'Crime/Fantasy'),
(13, 'The Lion King', 1994, 'Lion cub and future king Simba searches for his identity. His eagerness to please others and penchant for testing his boundaries sometimes gets him into trouble.', 'LionKing.jpg', 'Family/Musica'),
(14, 'Fight Club', 1999, 'An insomniac office worker and a devil-may-care soapmaker form an underground fight club that evolves into something much, much more.', 'FC.jpg', 'Thriller/Crime'),
(15, 'Goodfellas', 1990, 'The story of Henry Hill and his life in the mob, covering his relationship with his wife Karen Hill and his mob partners Jimmy Conway and Tommy DeVito in the Italian-American crime syndicate.', 'GoodFellas.jpg', 'Crime/Thriller'),
(16, 'The Godfather: Part II', 1974, 'The early life and career of Vito Corleone in 1920s New York City is portrayed, while his son, Michael, expands and tightens his grip on the family crime syndicate.', 'GodfatherII.jpg', 'Crime/Drama'),
(17, 'The Departed', 2006, 'An undercover cop and a mole in the police attempt to identify each other while infiltrating an Irish gang in South Boston.', 'Departed.jpg', 'Crime/Thriller'),
(18, 'The Prestige', 2006, 'After a tragic accident, two stage magicians engage in a battle to create the ultimate illusion while sacrificing everything they have to outwit each other.', 'Prestige.jpg', 'Thriller/Sci-fi'),
(19, 'Gladiator', 2000, 'A former Roman General sets out to exact vengeance against the corrupt emperor who murdered his family and sent him into slavery.', 'Gladiator.jpg', ' Action/Adventure'),
(20, 'The Usual Suspects', 1995, 'A sole survivor tells of the twisty events leading up to a horrific gun battle on a boat, which began when five criminals met at a seemingly random police lineup.', 'UnusualSuspect.jpg', 'Thriller/Crime'),
(22, 'The Godfather', 1972, 'The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.', 'Godfather.jpg', 'Crime');

-- --------------------------------------------------------

--
-- Struttura della tabella `utenti`
--

CREATE TABLE `utenti` (
  `username` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `ID` int(16) NOT NULL,
  `email` varchar(32) NOT NULL,
  `isAdmin` tinyint(1) NOT NULL,
  `token` varchar(64) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `utenti`
--

INSERT INTO `utenti` (`username`, `password`, `ID`, `email`, `isAdmin`, `token`) VALUES
('admin', '21232f297a57a5a743894a0e4a801fc3', 3, 'admin@admin.admin', 1, '5b0918521cffe85c7ec79821eb661435');

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `movies`
--
ALTER TABLE `movies`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `utenti`
--
ALTER TABLE `utenti`
  ADD PRIMARY KEY (`ID`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `movies`
--
ALTER TABLE `movies`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT per la tabella `utenti`
--
ALTER TABLE `utenti`
  MODIFY `ID` int(16) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
