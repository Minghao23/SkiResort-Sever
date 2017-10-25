package CS6650.as2.dal;

import CS6650.as2.model.MyVert;
import CS6650.as2.model.Record;

import java.sql.*;

/**
 * Created by hu_minghao on 10/17/17.
 */
public class MyVertDao {

    // Single pattern: instantiation is limited to one object.
    private static MyVertDao instance = null;
    public static synchronized MyVertDao getInstance() {
        if(instance == null) {
            instance = new MyVertDao();
        }
        return instance;
    }

    private final int[] lift_verticals = {200, 300, 400, 500};

    public void create(Connection connection, MyVert myVert) throws SQLException {
        String insertMyVert = "INSERT MyVert(SkierID,DayNum,Vertical,LiftTimes) VALUE (?,?,?,?);";
        try {
            PreparedStatement insertStmt = connection.prepareStatement(insertMyVert,
                    Statement.RETURN_GENERATED_KEYS);
            insertStmt.setInt(1, myVert.getSkierID());
            insertStmt.setInt(2, myVert.getDayNum());
            insertStmt.setInt(3, myVert.getVertical());
            insertStmt.setInt(4, myVert.getLiftTimes());
            insertStmt.executeUpdate();
            insertStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Connection connection, Record record) throws SQLException {
        String updateMyvert = "UPDATE MyVert SET Vertical=Vertical+?, LiftTimes=LiftTimes+1 WHERE SkierID=? && DayNum=?;";
        try {
            PreparedStatement updateStmt = connection.prepareStatement(updateMyvert);
            updateStmt.setInt(1, getVerticalByLift(record.getLiftID()));
            updateStmt.setInt(2, record.getSkierID());
            updateStmt.setInt(3, record.getDayNum());
            int count = updateStmt.executeUpdate();
            if(count == 0) {
                create(connection, new MyVert(record.getSkierID(), record.getDayNum(),
                        getVerticalByLift(record.getLiftID()), 1));
            }
            updateStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public MyVert getMyVert(Connection connection, int skierID, int dayNum) throws SQLException {
        MyVert myVert = null;
        String selectRecords = "SELECT * FROM MyVert WHERE SkierID=? && DayNum=?;";
        try {
            PreparedStatement selectStmt = connection.prepareStatement(selectRecords);
            selectStmt.setInt(1, skierID);
            selectStmt.setInt(2, dayNum);
            ResultSet results = selectStmt.executeQuery();
            if(results.next()) {
                int vertical = results.getInt("Vertical");
                int liftTimes = results.getInt("LiftTimes");
                myVert = new MyVert(skierID, dayNum, vertical, liftTimes);
            }
            selectStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return myVert;
    }

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

}
