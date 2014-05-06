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
    private PreparedStatement addToService;
    private PreparedStatement addToTestDrive;
    private PreparedStatement totalCars;
    private PreparedStatement inventoryCarInformation;

    private PreparedStatement sellCar;
    private PreparedStatement removecar;
    private PreparedStatement customers;
    private PreparedStatement employees;
    private PreparedStatement carVin;

    private PreparedStatement serviceInformation;
    private PreparedStatement testDriveInformation;


    private int updateResult;
    private int updateResult2;

    private ResultSet result;
    private ResultSet result2;

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
    
    public ResultSet getAllServiceInformation() {
    	try {
    		serviceInformation = conn.prepareStatement("SELECT * FROM car NATURAL JOIN service");
    		result = serviceInformation.executeQuery();
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    	return result;
    }
    
    public ResultSet getAllTestDriveInformation() {
    	try {
    		testDriveInformation = conn.prepareStatement("select * from test_drive inner join person as c on " +
    				"test_drive.customer_id=c.person_id inner join person as e on test_drive.employee_id=e.person_id " +
    				"inner join car on test_drive.VIN=car.VIN");
    		result = testDriveInformation.executeQuery();
    	}
    	catch(Exception e) {
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
    
    public int addCarToService(Properties props) {
    	try{
            addToService =conn.prepareStatement("INSERT INTO service VALUES (?, ?, ?, ?, ?, ?)");

            addToService.setString(1,props.getProperty("VIN"));
            addToService.setString(2,props.getProperty("customer_id"));
            addToService.setString(3,props.getProperty("date_in"));
            addToService.setString(4,props.getProperty("date_out"));
            addToService.setString(5,props.getProperty("cost"));
            addToService.setString(6,props.getProperty("service_type"));

            updateResult = addToService.executeUpdate();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return updateResult;
    }
    
    public int addCarToTestDrive(Properties props) {
    	try{
            addToTestDrive =conn.prepareStatement("INSERT INTO test_drive VALUES (?, ?, ?, ?)");

            addToTestDrive.setString(1,props.getProperty("VIN"));
            addToTestDrive.setString(2,props.getProperty("date_tested"));
            addToTestDrive.setString(3,props.getProperty("employee_id"));
            addToTestDrive.setString(4,props.getProperty("customer_id"));

            updateResult = addToTestDrive.executeUpdate();
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

    public int removeCarFromInventory(String Vin){
        try{
            removecar =conn.prepareStatement("DELETE FROM inventory WHERE VIN = ?");
            removecar.setString(1,Vin);
            updateResult2 =removecar.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return updateResult2;
    }

    public int sellACar(Properties carprops){
        try{
            sellCar = conn.prepareStatement("INSERT INTO sold VALUES(?,?,?,?,?)");
            sellCar.setString(1,carprops.getProperty("Vin"));
            sellCar.setString(2,carprops.getProperty("date_sold"));
            sellCar.setString(3,carprops.getProperty("customer_id"));
            sellCar.setString(4,carprops.getProperty("employee_id"));
            sellCar.setString(5,carprops.getProperty("price_sold"));
            updateResult = sellCar.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
        removeCarFromInventory(carprops.getProperty("Vin"));
        return updateResult;

    }

    public ResultSet getAllCustomers(){
        try{
            customers = conn.prepareStatement("SELECT customer_id FROM customer");
            result2 = customers.executeQuery();

        }catch(Exception e){
            e.printStackTrace();
        }
        return  result2;

    }

    public ResultSet getAllEmployees(){
        try{
            employees = conn.prepareStatement("SELECT employee_id FROM employee");
            result = employees.executeQuery();

        }catch (Exception e){
            e.printStackTrace();
        }
        return  result;
    }

    public ResultSet getCarVin() {
    	try{
            carVin = conn.prepareStatement("SELECT VIN FROM car");
            result = carVin.executeQuery();

        }catch (Exception e){
            e.printStackTrace();
        }
        return  result;
    }
    
    public int executeUpdate(String s) throws SQLException {
        return stat.executeUpdate(s);
    }

    public ResultSet executeQuery(String s) throws SQLException {
        return stat.executeQuery(s);
    }

}

