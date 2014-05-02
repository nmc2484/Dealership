import javax.swing.*;
import java.awt.*;
import java.util.Properties;

/**
 * Created by Nsama on 4/29/2014.
 */
public class CarInformation extends JPanel {
    private DBConnection dbconn;
    private JButton sellCar;
    private JButton removeCar;
    private JButton editCar; // this might involve a lot of stuff.
    private JButton removeFromService;
    private JPanel centerGrid;
    private JPanel topFlowPanel;
    private JLabel carName;
    private JLabel carYear;
    private JLabel carType;
    private JLabel carColor;
    private JLabel carFuelType;
    private JLabel carRetailPrice;
    private JLabel carTransmission;
    private JLabel carVin;




    public CarInformation(DBConnection conn, Properties props){
        this.dbconn = conn;
        this.setLayout(new BorderLayout());
        sellCar = new JButton("Sell The Car");
        removeCar = new JButton("Remove Car");
        editCar = new JButton("Edit Car information");
        removeFromService = new JButton("Remove From Service");
        centerGrid = new JPanel();
        centerGrid.setLayout(new GridLayout(20,1));
        topFlowPanel = new JPanel();
        carName = new JLabel        ("Make and Model"+"------------------------"+props.getProperty("Name"));
        carYear = new JLabel        ("Year of the car"+"-------------------------"+props.getProperty("Year"));
        carType = new JLabel        ("Type of the car"+"------------------------"+props.getProperty("Type"));
        carTransmission = new JLabel("Transmission of the car"+"-------------"+props.getProperty("Transmission"));
        carColor = new JLabel       ("Color of the car"+"-------------------------"+props.getProperty("Color"));
        carFuelType = new JLabel    ("Fuel type of the car"+"-------------------"+props.getProperty("FuelType"));
        carVin = new JLabel         ("VIN of the car"+"----------------------------"+props.getProperty("VIN"));
        carRetailPrice = new JLabel ("Price of the car"+"-------------------------"+props.getProperty("RetailPrice"));
        
        
        centerGrid.add(carVin);
        centerGrid.add(carName);
        centerGrid.add(carColor);
        
        if(props.getProperty("RetailPrice") != null) {
        	centerGrid.add(carRetailPrice);
        	topFlowPanel.add(sellCar, BorderLayout.WEST);
            topFlowPanel.add(removeCar, BorderLayout.EAST);
            topFlowPanel.add(editCar, BorderLayout.CENTER);
        } else {
        	topFlowPanel.add(removeFromService, BorderLayout.CENTER);
        }
        
        centerGrid.add(carFuelType);
        centerGrid.add(carTransmission);
        centerGrid.add(carType);
        centerGrid.add(carYear);
        

        this.add(topFlowPanel, BorderLayout.NORTH);
        this.add(centerGrid, BorderLayout.CENTER);

    }


}
