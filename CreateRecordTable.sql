USE SkiResort;

DROP TABLE IF EXISTS Record;
DROP TABLE IF EXISTS MyVert;

CREATE TABLE Record (
	RecordID INT AUTO_INCREMENT,
    SkierID INT,
    LiftID INT,
    DayNum INT,
    Time INT,
    CONSTRAINT pk_Record_RecordID PRIMARY KEY (RecordID)
);

CREATE TABLE MyVert (
    SkierID INT,
    DayNum INT,
    Vertical INT,
    LiftTimes INT,
    CONSTRAINT pk_MyVert_SkierID_DayNum PRIMARY KEY (SkierID,DayNum)
);