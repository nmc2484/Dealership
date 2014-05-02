import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

/**
 * Created by Nsama on 4/29/2014.
 */
public class CarInformation extends JPanel {
    private DBConnection dbconn;
    private JButton sellCar;
    private JButton removeCar;
    private JButton editCar; // this might involve a lot of stuff.
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
    private final  JFrame topFrame;




    public CarInformation(DBConnection conn, final Properties props){
        this.dbconn = conn;
        this.setLayout(new BorderLayout());
        sellCar = new JButton("Sell The Car");
        removeCar = new JButton("Remove Car");
        editCar = new JButton("Edit Car information");
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
        topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);

        sellCar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JDialog sell = new SellACar(topFrame,dbconn,props.getProperty("VIN"));
                sell.setVisible(true);
            }
        });

        centerGrid.add(carVin);
        centerGrid.add(carName);
        centerGrid.add(carColor);
        centerGrid.add(carRetailPrice);
        centerGrid.add(carFuelType);
        centerGrid.add(carTransmission);
        centerGrid.add(carType);
        centerGrid.add(carYear);

        topFlowPanel.add(sellCar, BorderLayout.WEST);
        topFlowPanel.add(removeCar, BorderLayout.EAST);
        topFlowPanel.add(editCar, BorderLayout.CENTER);

        this.add(topFlowPanel, BorderLayout.NORTH);
        this.add(centerGrid, BorderLayout.CENTER);

    }


}
