
/**
 * Created by benji on 5/6/14.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;

public class AddATestDriveCar extends JPanel {
    private Properties carProops;
    private DBConnection dbconn;
    private JButton okButton;
    private JButton cancelButton;
    private JPanel tPanel;
    private JPanel cPanel;
    private JLabel theVin
    ,thecustomerid
    ,theemployeeid;
    
    private JComboBox vinfield, customeridfield, employeeidfield;
    private Vector<String> vinVector, customerIdVector, employeeIdVector;

    private ResultSet Results;

    public AddATestDriveCar(DBConnection dbcon){
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
        theemployeeid = new JLabel("Enter Employee ID");

        vinVector = new Vector<String>();
        getVin();
        vinfield = new JComboBox(vinVector);
        
        customerIdVector = new Vector<String>();
        getCustomerId();
        customeridfield = new JComboBox(customerIdVector);
        
        employeeIdVector = new Vector<String>();
        getEmployeeId();
        employeeidfield = new JComboBox(employeeIdVector);      

        tPanel.add(okButton);
        tPanel.add(cancelButton);

        cPanel.add(theVin);
        cPanel.add(vinfield);

        cPanel.add(thecustomerid);
        cPanel.add(customeridfield);

        cPanel.add(theemployeeid);
        cPanel.add(employeeidfield);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
            	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                carProops.put("VIN", vinfield.getSelectedItem());
                carProops.put("customer_id", customeridfield.getSelectedItem());
                carProops.put("employee_id", employeeidfield.getSelectedItem());
                carProops.put("date_tested", dateFormat.format(date));
                dbconn.addCarToTestDrive(carProops);
                JPanel panel = new TestDrive(dbconn);
                DatabaseUI.refresh(panel);
            }
        });
        
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {               
                JPanel panel = new TestDrive(dbconn);
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
    
    public Vector<String> getCustomerId(){
        Results = dbconn.getAllCustomers();
        try{
            while(Results.next()){
                customerIdVector.add(Results.getString("customer_id"));

            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return customerIdVector;

    }
    
    public Vector<String> getEmployeeId(){
        Results = dbconn.getAllEmployees();
        try{
            while(Results.next()){
                employeeIdVector.add(Results.getString("employee_id"));

            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return employeeIdVector;

    }

}
