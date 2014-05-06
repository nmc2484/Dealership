/**
 * Created by nsama chipalo on 4/17/14.
 */

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.util.*;
import java.awt.event.*;


public class DatabaseUI {

    private JButton allCars;
    private JButton inventory;
    private JButton sold;
    private JButton service;
    private JButton testDrive;
    private JButton sqlInput;
    private JButton report;

    private static DBConnection db;

    private static JFrame frame;
    private static JFrame logFrame;
    private static JTextArea sql = null;
    private static JTextArea results = null;

    private static JLabel sqlLabel;
    private static JLabel resultsLabel;

    private JButton showAllCars;

    private JButton showInventory;


    private JPanel west;
    private JPanel allCarsPane, inventoryPane, sqlInputPane, reportPane, soldPane, servicePane, testDrivePane;
    private static JPanel center;
    
    private CardLayout cl;

    private ConnectionUI cui;

    private Properties props;

    private ConnectionProps cProps;
    private ConnectionProps inputProps;
    private DBConnection dbConnection;

    public static ExecuteQuery exQuery;


    public DatabaseUI(){
        Dimension screenSize =Toolkit.getDefaultToolkit().getScreenSize();
        frame = new JFrame();
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE );
        frame.setBounds(0,0,screenSize.width,screenSize.height);
        west = createLeftPanel();
        frame.getContentPane().add(west, BorderLayout.WEST);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        cProps = new ConnectionProps();
        cui = new ConnectionUI(frame, cProps.props);
        cui.setVisible(true);

        if (cui.isCanceled){
            System.exit(0);
        }
        inputProps = new ConnectionProps(cui.getProps());

        dbConnection = new DBConnection(inputProps.props, cui.getPassword());
        if (!dbConnection.connect()) {
            JOptionPane.showMessageDialog(frame,"Database connection cannot be established. The application will be closed.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        
        // Create singleton ExecuteQuery
        exQuery = ExecuteQuery.getInstance();        

    }


    public JPanel createLeftPanel(){

        west = new JPanel();
        west.setLayout(new GridLayout(7,1));

        //Creates the buttons for the panel
        report = new JButton("Report");
        report.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	reportPane = new Report(dbConnection);
            	refresh(reportPane);
            }
        });
        
        sqlInput = new JButton("SQL Input");
        sqlInput.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	sqlInputPane = new SqlInput(dbConnection);
            	refresh(sqlInputPane);
            }
        });

        allCars = new JButton("All Cars");        
        allCars.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		allCarsPane = new AllCars(dbConnection);
                refresh(allCarsPane);
        	}
        });

        inventory = new JButton("Inventory");
        inventory.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                inventoryPane = new Inventory(dbConnection);
                refresh(inventoryPane);
            }
        });
        
        sold = new JButton("Sold");
        sold.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                soldPane = new Sold(dbConnection);
                refresh(soldPane);
            }
        });

        service = new JButton("Car on Service");
        service.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                servicePane = new Service(dbConnection);
                refresh(servicePane);
            }
        });

        testDrive = new JButton("Cars on Test Drive");
        testDrive.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                testDrivePane = new TestDrive(dbConnection);
                refresh(testDrivePane);
            }
        });

        west.add(report);
        west.add(sqlInput);
        west.add(allCars);
        west.add(inventory);
        west.add(sold);
        west.add(service);
        west.add(testDrive);

        return west;

    }


    public static void refresh(JPanel pnaelA){
        if (center != null){
            center.removeAll();
        }
        center = pnaelA;
        frame.add(center, BorderLayout.CENTER);
        frame.validate();
    }


    public static void main(String args[]){
        DatabaseUI db = new DatabaseUI();
    }



}
