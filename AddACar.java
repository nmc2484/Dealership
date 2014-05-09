
/**
 * Created by nsama on 5/1/14.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

public class AddACar extends JPanel {
    private Properties carProops;
    private DBConnection dbconn;
    private JButton okButton;
    private JButton cancelButton;
    private JPanel tPanel;
    private JPanel cPanel;
    private JLabel theVin
    ,themake
    ,themodle
    ,theyear
    ,thetype
    ,thecolor
    ,thetransmisson
    ,thefueltype
    ,theretailPrice
    ,thepriceobtainedat
    ,thedateobtained;

    private JTextField vinfield
    ,makefield
    ,modlefield
    ,yearfield
    ,typefield
    ,colorfield
    ,transmissionfield
    ,fueltypefield
    ,retailpricefield
    ,obtainedpricefield
    ,dateobtainedfield;


    public AddACar(DBConnection dbcon){
        this.dbconn =dbcon;
        carProops = new Properties();
        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");
        tPanel = new JPanel();
        this.setLayout(new BorderLayout());
        cPanel = new JPanel();
        cPanel.setLayout(new GridLayout(11,11));

        theVin = new JLabel("Enter Vin (must be 17 characters)");
        themake = new JLabel("Enter Make");
        themodle = new JLabel("Enter Model");
        theyear = new JLabel("Enter Car Year yyyy");
        thetype = new JLabel("Enter Car type");
        thecolor = new JLabel("Enter color");
        thetransmisson = new JLabel("Enter Transmission");
        thefueltype = new JLabel("Enter Fuel type");
        thepriceobtainedat = new JLabel("Enter price obtained");
        theretailPrice = new JLabel("Enter Retail Price");
        thedateobtained = new JLabel("Enter Date Obtained yyyy/mm/dd");


        vinfield = new JTextField();
        makefield = new JTextField();
        modlefield = new JTextField();
        yearfield = new JTextField();
        typefield = new JTextField();
        colorfield = new JTextField();
        transmissionfield = new JTextField();
        fueltypefield = new JTextField();
        retailpricefield = new JTextField();
        obtainedpricefield = new JTextField();
        dateobtainedfield = new JTextField();

        tPanel.add(okButton);
        tPanel.add(cancelButton);

        cPanel.add(theVin);
        cPanel.add(vinfield);

        cPanel.add(themake);
        cPanel.add(makefield);

        cPanel.add(themodle);
        cPanel.add(modlefield);

        cPanel.add(theyear);
        cPanel.add(yearfield);

        cPanel.add(thetype);
        cPanel.add(typefield);

        cPanel.add(thecolor);
        cPanel.add(colorfield);

        cPanel.add(thetransmisson);
        cPanel.add(transmissionfield);

        cPanel.add(thefueltype);
        cPanel.add(fueltypefield);

        cPanel.add(theretailPrice);
        cPanel.add(retailpricefield);

        cPanel.add(thepriceobtainedat);
        cPanel.add(obtainedpricefield);

        cPanel.add(thedateobtained);
        cPanel.add(dateobtainedfield);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                carProops.put("VIN", vinfield.getText());
                carProops.put("make", makefield.getText());
                carProops.put("type", typefield.getText());
                carProops.put("model", modlefield.getText());
                carProops.put("year",yearfield.getText());
                carProops.put("color", colorfield.getText());
                carProops.put("transmission",transmissionfield.getText());
                carProops.put("fuel_type", fueltypefield.getText());
                carProops.put("retail_price",retailpricefield.getText());
                carProops.put("obtained_price",obtainedpricefield.getText());
                carProops.put("date_obtained", dateobtainedfield.getText());
                dbconn.addCarToInventory(carProops);
                JPanel panel = new Inventory(dbconn);
                DatabaseUI.refresh(panel);
            }
        });

        this.add(tPanel,BorderLayout.NORTH);
        this.add(cPanel, BorderLayout.CENTER);





    }


}
