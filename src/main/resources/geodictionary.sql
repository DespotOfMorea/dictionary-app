-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 27, 2018 at 11:19 AM
-- Server version: 5.7.14
-- PHP Version: 5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `geodictionary`
--

-- --------------------------------------------------------

--
-- Table structure for table `languages`
--

CREATE TABLE `languages` (
  `ID` int(11) NOT NULL,
  `EnglishName` varchar(255) NOT NULL,
  `NativeName` varchar(255) DEFAULT NULL,
  `IsoCode` char(3) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `languages`
--

INSERT INTO `languages` (`ID`, `EnglishName`, `NativeName`, `IsoCode`) VALUES
(1, 'english', 'english', 'eng'),
(2, 'serbian', 'srpski', 'srp');

-- --------------------------------------------------------

--
-- Table structure for table `terms`
--

CREATE TABLE `terms` (
  `ID` int(11) NOT NULL,
  `Term` varchar(255) NOT NULL,
  `Meaning` text,
  `LanguageID` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `terms`
--

INSERT INTO `terms` (`ID`, `Term`, `Meaning`, `LanguageID`) VALUES
(1, 'Fault', 'A fault is a fracture, or break, in the Earth\'s crust (lithosphere). Some faults are active. Here, sections of rock move past each other. This sometimes makes earthquakes.', 1),
(2, 'Fold', 'The term fold is used in geology when one or a stack of originally flat, level surfaces, such as sedimentary strata, are bent or curved as a result of pressure and high temperature. The basic cause is likely to be some aspect of plate tectonics.', 1),
(3, 'Dyke', 'A dyke in geology is a type of later vertical rock between older layers of rock. Technically, it is any geologic body which cuts across: flat wall rock structures, such as bedding or massive rock formations, usually igneous in origin.Dikes can therefore be either pushed in between (intrusive) or laid down (sedimentary) in origin.', 1),
(4, 'Dike', 'A dyke in geology is a type of later vertical rock between older layers of rock. Technically, it is any geologic body which cuts across: flat wall rock structures, such as bedding or massive rock formations, usually igneous in origin.Dikes can therefore be either pushed in between (intrusive) or laid down (sedimentary) in origin.', 1),
(5, 'Foliation', 'Foliation in geology refers to repetitive layering in metamorphic rocks. Each layer can be as thin as a sheet of paper, or over a meter in thickness.', 1),
(6, 'Cleavage', 'Cleavage, in structural geology and petrology, describes a type of planar rock feature that develops as a result of deformation and metamorphism. The degree of deformation and metamorphism along with rock type determines the kind of cleavage feature that develops. Generally these structures are formed in fine grained rocks composed of minerals affected by pressure solution.', 1),
(7, 'Anticline', 'In structural geology, an anticline is a type of fold that is an arch-like shape and has its oldest beds at its core. A typical anticline is convex up in which the hinge or crest is the location where the curvature is greatest, and the limbs are the sides of the fold that dip away from the hinge.', 1),
(8, 'Syncline', 'In structural geology, a syncline is a fold with younger layers closer to the center of the structure. A synclinorium (plural synclinoriums or synclinoria) is a large syncline with superimposed smaller folds. Synclines are typically a downward fold (synform), termed a synformal syncline (i.e. a trough); but synclines that point upwards can be found when strata have been overturned and folded (an antiformal syncline).', 1),
(9, 'Joint', 'In geology, a joint is a fracture dividing rock into two sections that moved away from each other. A joint does not involve shear displacement, and forms when tensile stress breaches its threshold. In other kinds of fracturing, like in a fault, the rock is parted by a visible crack that forms a gap in the rock.', 1),
(10, 'Rased', 'Rasedi su mehani?ki diskontinuiteti stenske mase, po kojima se odigralo kretanje. Nastaju usled naprezanja u stenskoj masi.', 2),
(11, 'Nabor', 'Nabor je oblik koji nastaje kontinuiranim deformacijama stena u Zemljinoj kori. Nastaju pod dejstvom orogenih kretanja i mogu se uo?iti u svim stenama koje imaju slojevitost ili planaran raspored minerala, dakle u sedimentnim, metamorfnim i trakastim ili škriljavim magmatskim stenama.', 2),
(12, 'Dajk', 'Dajk je intruziv, koji diskordantno preseca: planarne stenske strukture, kao što su slojevitost ili folijacija; ili masivne stenske formacije, kao što to ?ine magmatske intruzije ili dijapiri halita. Stoga, dajkovi mogu imati magmatsko ili sedimentno poreklo.', 2),
(13, 'Folijacija', 'Folijacija je sistem penetrativnih pukotina na stenskoj masi. Obi?no se javlja u stenama koje su bile izložene kompresiji u toku regionalnog metamorfizma, što je tipi?no kod orogenih pojaseva. Stene, u kojima se obi?no javlja folijacija, su argilošist, filit, škriljci i gnajs.', 2),
(14, 'Klivaž', 'Klivaž, u strukturnoj geologiji i petrologiji, ozna?ava tip planarne strukture stene, koja nastaje kao rezultat deformacija i metamorfizma. Koji ?e se tip klivaža razviti u steni zavisi od stepena deformacija i metamorfizma, kao i od tipa stene koja je podvrgnuta promenama. U opštem slu?aju, ove strukture se formiraju u finozrnim stenama, koje izgra?uju minerali koji se mogu stapati pod pritiskom.', 2),
(15, 'Antiklinala', 'Antiklinala je naborni oblik koji u jezgru ima najstarije slojeve. Za naborni oblik konveksan naviše upotrebljava se termin antiforma. Antiklinala je obi?no (sem kada nabor nije prevrnut), konveksna naviše. Ali, za pouzdanu determinaciju karaktera nabornog oblika, moraju se istražiti odnos relativne starosti jedinica koje ga izgra?uju.', 2),
(16, 'Sinklinala', 'Sinklinala je termin strukturne geologije za naborni oblik koji u jezgru ima najmla?e slojeve.', 2),
(17, 'Pukotina', 'Pukotine su mehani?ki diskontinuiteti stenske mase, po kojima je kretanje toliko malo da se ono može zanemariti u datom veli?inskom podru?ju posmatranja. Dakle, za razliku od raseda, pukotine su razlomi, po kojima „nije došlo“ do kretanja blokova stenske mase.', 2);

-- --------------------------------------------------------

--
-- Table structure for table `translations`
--

CREATE TABLE `translations` (
  `ID` int(11) NOT NULL,
  `Term1ID` int(11) NOT NULL,
  `Term2ID` int(11) NOT NULL,
  `Priority` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `translations`
--

INSERT INTO `translations` (`ID`, `Term1ID`, `Term2ID`, `Priority`) VALUES
(1, 1, 10, 1),
(2, 2, 11, 1),
(3, 3, 12, 1),
(4, 4, 12, 1),
(5, 5, 13, 1),
(6, 6, 14, 1),
(7, 7, 15, 1),
(8, 8, 16, 1),
(9, 9, 17, 1),
(10, 10, 1, 1),
(11, 11, 2, 1),
(12, 12, 3, 1),
(13, 12, 4, 2),
(14, 13, 5, 1),
(15, 14, 6, 1),
(16, 15, 7, 1),
(17, 16, 8, 1),
(18, 17, 9, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `languages`
--
ALTER TABLE `languages`
  ADD PRIMARY KEY (`ID`),
  ADD UNIQUE KEY `IsoCode` (`IsoCode`);

--
-- Indexes for table `terms`
--
ALTER TABLE `terms`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `LanguageID` (`LanguageID`);

--
-- Indexes for table `translations`
--
ALTER TABLE `translations`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK_Term1` (`Term1ID`),
  ADD KEY `FK_Term2` (`Term2ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `languages`
--
ALTER TABLE `languages`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `terms`
--
ALTER TABLE `terms`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;
--
-- AUTO_INCREMENT for table `translations`
--
ALTER TABLE `translations`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
