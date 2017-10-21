USE SkiResort;

DROP TABLE IF EXISTS Record;

CREATE TABLE Record (
	RecordID INT AUTO_INCREMENT,
    SkierID INT,
    LiftID INT,
    DayNum INT,
    Time INT,
    CONSTRAINT pk_Record_RecordID PRIMARY KEY (RecordID)
);
