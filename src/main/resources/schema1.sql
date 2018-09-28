CREATE DATABASE IF NOT EXISTS geodictionary;

USE geodictionary;

CREATE TABLE IF NOT EXISTS languages (
                    ID int NOT NULL AUTO_INCREMENT,
                    EnglishName varchar(255) NOT NULL,
                    NativeName varchar(255),
                    IsoCode char(3) UNIQUE,
                    PRIMARY KEY (ID))
					ENGINE=INNODB;
					
CREATE TABLE IF NOT EXISTS terms (
                    ID int NOT NULL AUTO_INCREMENT,
                    Term varchar(255) NOT NULL,
                    Meaning text,
                    LanguageID int NOT NULL,
                    PRIMARY KEY (ID),
                    FOREIGN KEY (LanguageID) REFERENCES Languages(ID))
					ENGINE=INNODB;

CREATE TABLE IF NOT EXISTS translations (
                    ID int NOT NULL AUTO_INCREMENT,
                    Term1ID int NOT NULL,
                    Term2ID int NOT NULL,
                    Priority int NOT NULL,
                    PRIMARY KEY (ID),
                    CONSTRAINT FK_Term1 FOREIGN KEY (Term1ID) REFERENCES Terms(ID),
                    CONSTRAINT FK_Term2 FOREIGN KEY (Term2ID) REFERENCES Terms(ID))
					ENGINE=INNODB;					