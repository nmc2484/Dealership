import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by nsama on 4/20/14.
 */
public class AllCars extends JPanel {

    private JButton addNewCar, deleteCar, viewCars;
    private JPanel frame, topView;
    private JLabel numberOfCars;
    private String totalCars;
    private JTextArea results = null;
    private DBConnection dbc;

    public AllCars(DBConnection conn){
    	this.dbc = conn;
        this.setLayout(new BorderLayout());
        frame = new JPanel();
        frame.setLayout(new BorderLayout());
        topView = new JPanel();
        addNewCar = new JButton("ADD A CAR");
        addNewCar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                // Create dialog box
            }
        });
        deleteCar = new JButton("DELETE A CAR");
        deleteCar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                // Create dialog box
            }
        });
        viewCars = new JButton("VIEW ALL CARS");

        viewCars.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                // Display all cars
            	results.setText("");
                results.setText(DatabaseUI.exQuery.executeResults("select * from car", dbc));
            }
        });
        numberOfCars = new JLabel("# OF CARS: ");
        
        results = new JTextArea();
        results.setFont(new Font("Courier New", Font.BOLD, 12));
        JScrollPane scrollResults = new JScrollPane(results);
        scrollResults.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Result"),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)),
                scrollResults.getBorder()
        ));
        frame.add(topView, BorderLayout.NORTH);
        frame.add(scrollResults, BorderLayout.CENTER);
        
        topView.setLayout(new FlowLayout(FlowLayout.LEFT));
        topView.add(addNewCar);
        topView.add(deleteCar);
        topView.add(viewCars);
        topView.add(numberOfCars);
        
        this.add(frame);
    }


}
