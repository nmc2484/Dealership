
/**
 * Created by benji on 5/2/14.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.Vector;

public class AddAServiceCar extends JPanel {
    private Properties carProops;
    private DBConnection dbconn;
    private JButton okButton;
    private JButton cancelButton;
    private JPanel tPanel;
    private JPanel cPanel;
    private JLabel theVin
    ,thecustomerid
    ,thedatein
    ,thecost
    ,theservicetype;

    private JTextField customeridfield
    ,dateinfield
    ,costfield
    ,servicetypefield;
    
    private JComboBox vinfield;
    private Vector<String> vinVector;

    private ResultSet Results;

    public AddAServiceCar(DBConnection dbcon){
        this.dbconn =dbcon;
        carProops = new Properties();
        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");
        tPanel = new JPanel();
        this.setLayout(new BorderLayout());
        cPanel = new JPanel();
        cPanel.setLayout(new GridLayout(8,8));

        theVin = new JLabel("Enter Vin");
        thecustomerid = new JLabel("Enter Customer ID");
        thedatein = new JLabel("Enter Date In");
        thecost = new JLabel("Enter Service Cost");
        theservicetype = new JLabel("Enter service description");

        vinVector = new Vector<String>();
        getVin();
        vinfield = new JComboBox(vinVector);
        
        customeridfield = new JTextField();
        dateinfield = new JTextField();
        costfield = new JTextField();
        servicetypefield = new JTextField();     

        tPanel.add(okButton);
        tPanel.add(cancelButton);

        cPanel.add(theVin);
        cPanel.add(vinfield);

        cPanel.add(thecustomerid);
        cPanel.add(customeridfield);

        cPanel.add(thedatein);
        cPanel.add(dateinfield);

        cPanel.add(thecost);
        cPanel.add(costfield);

        cPanel.add(theservicetype);
        cPanel.add(servicetypefield);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                carProops.put("VIN", vinfield.getSelectedItem());
                carProops.put("customer_id", customeridfield.getText());
                carProops.put("date_in", dateinfield.getText());
                carProops.put("cost",costfield.getText());
                carProops.put("service_type", servicetypefield.getText());
                dbconn.addCarToService(carProops);
                JPanel panel = new Service(dbconn);
                DatabaseUI.refresh(panel);
            }
        });
        
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {               
                JPanel panel = new Service(dbconn);
                DatabaseUI.refresh(panel);
            }
        });

        this.add(tPanel,BorderLayout.NORTH);
        this.add(cPanel, BorderLayout.CENTER);





    }

    public Vector<String> getVin(){
        Results = dbconn.getCarVin();
        try{
            while(Results.next()){
                vinVector.add(Results.getString("VIN"));

            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return vinVector;

    }

}
