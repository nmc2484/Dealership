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

public class Service extends JPanel {
    private JButton addCar;
    private ResultSet serviceSet;
    private ArrayList<GridMenuItem> buttons;
    private GridButtonPanel ButtonPanel;
    Properties props;
    private DBConnection dbcon;


    public Service(final DBConnection dbconn){
        addCar = new JButton("SERVICE A CAR");
        this.dbcon = dbconn;
        this.setLayout(new BorderLayout());
        buttons = new ArrayList<GridMenuItem>();
        serviceSet = dbcon.getAllServiceInformation();
        try {
            while (serviceSet.next()) {
                props = new Properties();
                props.put("Name", serviceSet.getString("make")+" "+ serviceSet.getString("model"));
                props.put("VIN",serviceSet.getString("VIN"));
                props.put("Year",serviceSet.getString("year"));
                props.put("Type",serviceSet.getString("type"));
                props.put("Color",serviceSet.getString("color"));
                props.put("Transmission",serviceSet.getString("transmission"));
                props.put("FuelType",serviceSet.getString("fuel_type"));
                props.put("Customer ID", serviceSet.getString("customer_id"));
                props.put("Date In", serviceSet.getString("date_in"));
                if(serviceSet.getString("date_out") == null) {
                	props.put("Date Out", "");
                } else {
                	props.put("Date Out", serviceSet.getString("date_out"));
                }
                props.put("Cost", serviceSet.getString("cost"));
                props.put("Service Type", serviceSet.getString("service_type"));
                buttons.add(new GridMenuItem(dbcon,props));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        addCar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e ){
                JPanel panel = new AddAServiceCar(dbcon);
                DatabaseUI.refresh(panel);

            }
        });

        ButtonPanel = new GridButtonPanel("Serviced Cars", buttons,null);
        this.add(addCar, BorderLayout.NORTH);
        this.add(ButtonPanel, BorderLayout.CENTER);

    }


}
