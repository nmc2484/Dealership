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

    private ConnectionUI cui;

    private Properties props;

    private ConnectionProps cProps;
    private ConnectionProps inputProps;
    private DBConnection dbConnection;




    public DatabaseUI(){
        Dimension screenSize =Toolkit.getDefaultToolkit().getScreenSize();
        frame = new JFrame();
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE );
        frame.setBounds(0,0,screenSize.width,screenSize.height);
        west = createLeftPanel();
        frame.getContentPane().add(west, BorderLayout.WEST);
        frame.setVisible(true);

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

    }


    public JPanel createLeftPanel(){

        west = new JPanel();
        west.setLayout(new GridLayout(6,1));

        //Creates the buttons for the panel
        sqlInput = new JButton("SQL Input");
        sqlInput.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                JPanel newPanel = new SqlInput(dbConnection);
                refresh(newPanel);
            }
        });

        allCars = new JButton("All Cars");

        inventory = new JButton("Inventory");

        sold = new JButton("Sold");

        service = new JButton("Car on Service");

        testDrive = new JButton("Cars on Test Drive");


        west.add(sqlInput);
        west.add(allCars);
        west.add(inventory);
        west.add(sold);
        west.add(service);
        west.add(testDrive);

        return west;

    }


    public static void refresh(JPanel pnaelA){
        frame.add(pnaelA, BorderLayout.CENTER);
        frame.revalidate();
    }


    public static void main(String args[]){
        DatabaseUI db = new DatabaseUI();
    }



}
