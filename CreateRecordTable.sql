USE SkiReSort;

DROP TABLE IF EXISTS Record;

CREATE TABLE Record (
	RecordID INT AUTO_INCREMENT,
    SkierID INT,
    LiftID INT,
    DayNum INT,
    CONSTRAINT pk_Record_RecordID PRIMARY KEY (RecordID)
);
