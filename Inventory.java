/**
 * Created by nsama on 4/21/14.
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

public class Inventory extends JPanel {
    private JButton addCar;
    private ResultSet inventorySet;
    private ArrayList<GridMenuItem> buttons;
    private GridButtonPanel ButtonPanel;
    Properties props;
    private DBConnection dbcon;


    public Inventory(final DBConnection dbconn){
        addCar = new JButton("ADD A CAR");
        this.dbcon = dbconn;
        this.setLayout(new BorderLayout());
        buttons = new ArrayList<GridMenuItem>();
        inventorySet = dbcon.getAllInventoryCarInformation();
        try {
            while (inventorySet.next()) {
                props = new Properties();
                props.put("Name", inventorySet.getString("make")+" "+ inventorySet.getString("model"));
                props.put("VIN",inventorySet.getString("VIN"));
                props.put("Year",inventorySet.getString("year"));
                props.put("Type",inventorySet.getString("type"));
                props.put("Color",inventorySet.getString("color"));
                props.put("Transmission",inventorySet.getString("transmission"));
                props.put("FuelType",inventorySet.getString("fuel_type"));
                props.put("RetailPrice",inventorySet.getString("retail_price"));
                buttons.add(new GridMenuItem(dbcon,props));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        addCar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e ){
                JPanel panel = new AddACar(dbcon);
                DatabaseUI.refresh(panel);

            }
        });

        ButtonPanel = new GridButtonPanel("Inventory",buttons,null);
        this.add(addCar, BorderLayout.NORTH);
        this.add(ButtonPanel, BorderLayout.CENTER);

    }


}
