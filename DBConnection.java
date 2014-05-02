/**
 * Created by nsama chipalo on 4/16/14.
 */

import java.sql.*;
import java.util.Properties;


public class DBConnection {

    PreparedStatement sta = null;
    Connection conn = null;
    Statement stat =null;

    private static String url;
    private static String database;
    private static String username;
    private static String password;
    private static String hostname;
    private static String port;
    private static String driver;

    private PreparedStatement showCars;
    private PreparedStatement onSaleCars;
    private PreparedStatement addCar;
    private PreparedStatement addToInventory;
    private PreparedStatement totalCars;
    private PreparedStatement inventoryCarInformation;

    private int updateResult;

    private ResultSet result;

    private String theTotalCars;


    public DBConnection(Properties props, String pswd){
        url=props.getProperty("DATABASE_URL");
        database=props.getProperty("DATABASE");
        username=props.getProperty("USER_NAME");
        password=pswd;
        hostname=props.getProperty("HOST_NAME");
        port=props.getProperty("PORT");
        driver=props.getProperty("DRIVER");

        url = url+hostname+":"+port+"/"+database;

   }

    public boolean connect(){
        try{
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
            stat = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY );

        }
        catch(Exception e){
            e.printStackTrace();
        }
          return true;
    }

    public boolean close(){
        if (conn != null){
            try{
                conn.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        return true;
    }

    public ResultSet showAllCars(){
        try{
            showCars = conn.prepareStatement("SELECT * FROM car");
            result = showCars.executeQuery();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public ResultSet showAllCarsOnSale(){
        try{
            onSaleCars = conn.prepareStatement("SELECT * FROM Inventory");
            result = onSaleCars.executeQuery();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public ResultSet getAllInventoryCarInformation(){
        try{
            inventoryCarInformation = conn.prepareStatement("SELECT * FROM inventory NATURAL JOIN car");
            result = inventoryCarInformation.executeQuery();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return result;

    }

    public int addCarToInventory(Properties props){
        try{
            addCar =conn.prepareStatement("INSERT INTO car VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

            addCar.setString(1,props.getProperty("VIN"));
            addCar.setString(2,props.getProperty("make"));
            addCar.setString(3,props.getProperty("model"));
            addCar.setString(4,props.getProperty("year"));
            addCar.setString(5,props.getProperty("type"));
            addCar.setString(6,props.getProperty("color"));
            addCar.setString(7,props.getProperty("transmission"));
            addCar.setString(8,props.getProperty("fuel_type"));

            addToInventory = conn.prepareStatement("INSERT INTO inventory VALUES (?, ?, ?, ?)");
            addToInventory.setString(1,props.getProperty("VIN"));
            addToInventory.setString(2,props.getProperty("retail_price"));
            addToInventory.setString(3,props.getProperty("obtained_price"));
            addToInventory.setString(4,props.getProperty("date_obtained"));

            updateResult = addCar.executeUpdate();
            updateResult = addToInventory.executeUpdate();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return updateResult;
    }

    public String getTotalCars(){
        try{
            totalCars = conn.prepareStatement("SELECT COUNT() FROM inventory");
            result = totalCars.executeQuery();

        }
        catch(Exception e){
            e.printStackTrace();
        }
       return theTotalCars;  

    }

    public int executeUpdate(String s) throws SQLException {
        return stat.executeUpdate(s);
    }

    public ResultSet executeQuery(String s) throws SQLException {
        return stat.executeQuery(s);
    }

}

