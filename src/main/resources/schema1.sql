CREATE DATABASE IF NOT EXISTS geodictionary;

CREATE TABLE IF NOT EXISTS geodictionary.languages (
                    ID int NOT NULL AUTO_INCREMENT,
                    EnglishName varchar(255) NOT NULL,
                    NativeName varchar(255),
                    IsoCode char(3) UNIQUE,
                    PRIMARY KEY (ID));
					
CREATE TABLE IF NOT EXISTS geodictionary.terms (
                    ID int NOT NULL AUTO_INCREMENT,
                    Term varchar(255) NOT NULL,
                    Meaning text,
                    LanguageID int NOT NULL,
                    PRIMARY KEY (ID),
                    FOREIGN KEY (LanguageID) REFERENCES Languages(ID));

CREATE TABLE IF NOT EXISTS geodictionary.translations (
                    ID int NOT NULL AUTO_INCREMENT,
                    Term1ID int NOT NULL,
                    Term2ID int NOT NULL,
                    Priority int NOT NULL,
                    PRIMARY KEY (ID),
                    CONSTRAINT FK_Term1 FOREIGN KEY (Term1ID) REFERENCES Terms(ID),
                    CONSTRAINT FK_Term2 FOREIGN KEY (Term2ID) REFERENCES Terms(ID));					