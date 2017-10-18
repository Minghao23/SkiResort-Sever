package CS6650.as2.model;

/**
 * Created by hu_minghao on 10/17/17.
 */
public class Record {

    private int recordID;
    private int skierID;
    private int liftID;
    private int dayNum;

    public Record() {
    }

    public Record(int recordID) {
        this.recordID = recordID;
    }

    public Record(int skierID, int liftID, int dayNum) {
        this.skierID = skierID;
        this.liftID = liftID;
        this.dayNum = dayNum;
    }

    public Record(int recordID, int skierID, int liftID, int dayNum) {
        this.recordID = recordID;
        this.skierID = skierID;
        this.liftID = liftID;
        this.dayNum = dayNum;
    }

    public int getRecordID() {
        return recordID;
    }

    public void setRecordID(int recordID) {
        this.recordID = recordID;
    }

    public int getSkierID() {
        return skierID;
    }

    public void setSkierID(int skierID) {
        this.skierID = skierID;
    }

    public int getLiftID() {
        return liftID;
    }

    public void setLiftID(int liftID) {
        this.liftID = liftID;
    }

    public int getDayNum() {
        return dayNum;
    }

    public void setDayNum(int dayNum) {
        this.dayNum = dayNum;
    }
}
