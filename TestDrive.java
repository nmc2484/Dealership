/**
 * Created by benji on 5/6/14.
 */
import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class TestDrive extends JPanel {
    private JButton addCar;
    private ResultSet testDriveSet;
    private ArrayList<GridMenuItem> buttons;
    private GridButtonPanel ButtonPanel;
    Properties props;
    private DBConnection dbcon;


    public TestDrive(final DBConnection dbconn){
        addCar = new JButton("TEST DRIVE A CAR");
        this.dbcon = dbconn;
        this.setLayout(new BorderLayout());
        buttons = new ArrayList<GridMenuItem>();
        testDriveSet = dbcon.getAllTestDriveInformation();
        try {
            while (testDriveSet.next()) {
                props = new Properties();
                props.put("Name", testDriveSet.getString("make")+" "+ testDriveSet.getString("model"));
                props.put("VIN",testDriveSet.getString("VIN"));
                props.put("Year",testDriveSet.getString("year"));
                props.put("Color",testDriveSet.getString("color"));
                props.put("Date Tested", testDriveSet.getString("date_tested"));
                props.put("Customer ID", testDriveSet.getString("customer_id"));
                props.put("Customer Name", testDriveSet.getString("c.first_name") + " " + 
                		testDriveSet.getString("c.last_name"));          
                props.put("Employee ID", testDriveSet.getString("employee_id"));
                props.put("Employee Name", testDriveSet.getString("e.first_name") + " " + 
                		testDriveSet.getString("e.last_name"));
                props.put("Address", testDriveSet.getString("address"));
                props.put("City", testDriveSet.getString("city"));
                props.put("State", testDriveSet.getString("state"));
                props.put("Zip", testDriveSet.getString("zip"));
                props.put("Email", testDriveSet.getString("email"));
                props.put("Phone Number", testDriveSet.getString("phone_number"));
                buttons.add(new GridMenuItem(dbcon,props));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        addCar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e ){
                JPanel panel = new AddATestDriveCar(dbcon);
                DatabaseUI.refresh(panel);

            }
        });

        ButtonPanel = new GridButtonPanel("Test Drive Cars", buttons,null);
        this.add(addCar, BorderLayout.NORTH);
        this.add(ButtonPanel, BorderLayout.CENTER);

    }


}
