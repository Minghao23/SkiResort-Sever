package CS6650.as2.rest;

import CS6650.as2.dal.RecordDao;
import CS6650.as2.model.MyVert;
import CS6650.as2.model.Record;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hu_minghao on 10/17/17.
 */
public class DataController {
    private final int[] lift_verticals = {200, 300, 400, 500};

    private int getVerticalByLift(int liftID) {
        if (liftID <= 10) {
            return lift_verticals[0];
        }else if (liftID <= 20) {
            return lift_verticals[1];
        }else if (liftID <= 30) {
            return lift_verticals[2];
        }else{
            return lift_verticals[3];
        }
    }
    public MyVert getMyVertical(int skierID, int dayNum) {
        List<Record> records = new ArrayList<Record>();
        try {
            records = RecordDao.getInstance().getRecordsBySkierIDAndDayNum(skierID, dayNum);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int liftTimes = records.size();
        int totalVertical = 0;
        for (int i = 0; i < liftTimes; i++) {
            totalVertical += getVerticalByLift(records.get(i).getLiftID());
        }
        return new MyVert(totalVertical,liftTimes);
    }

}
