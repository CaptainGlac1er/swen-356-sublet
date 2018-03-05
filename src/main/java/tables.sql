CREATE SEQUENCE USER_ID_SEQ START WITH 1;
CREATE SEQUENCE LISING_ID_SEQ START WITH 1;

CREATE TABLE StndUser{
  uid BIGINT DEFAULT USER_ID_SEQ.NEXTVAL,
  fname VARCHAR(40) NOT NULL,
  lname VARCHAR(40) NOT NULL,
  username VARCHAR(45) NOT NULL,
  email VARCHAR(64) NOT NULL,
  password VARCHAR(64) NOT NULL,
  birthday DATE NOT NULL,
  gradYear DATE NOT NULL,
  UNIQUE(username),
  UNIQUE(email),
  PRIMARY KEY(uid),
}

CREATE TABLE Listing{
  lid BIGINT DEFAULT LISTING_ID_SEQ.NEXTVAL,
  dis LONGTEXT NOT NULL,
  rent int NOT NULL,
  address VARCHAR(255) NOT NULL,
  payment VARCHAR (10) NOT NULL,
  gender VARCHAR (6) NOT NULL,
  housing VARCHAR (11) NOT NULL,
  is_furnished VARCHAR (11) NOT NULL,
  parking_type VARCHAR (10) NOT NULL,
  util BIT NOT NULL,
  PRIMARY KEY(lid),
  FOREIGN KEY (uid) REFERENCES (StndUser),
  CHECK (payment IN ('MONTHLY','SEMESTERLY','YEARLY')),
  CHECK (is_furnished IN ('FURNISHED','UNFURNISHED','PART')),
  CHECK (parking_type IN ('ON_STR','PVT_GARAGE','PUB_GARAGE','DRIVE','PUB_LOT','PVT_LOT','OTHER'),
  CHECK (housing IN ('COLONY','GLOBAL','PARKPOINT','PERKINS','PROVINCE','RACQUETCLUB','RITINN','RIVERKNOLL','LODGE','UC',
  'OFFCAMPUS')),
  CHECK (gender IN ('COED','FEMALE','MALE')),
}