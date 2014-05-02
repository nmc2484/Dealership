import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by nsama on 4/20/14.
 */
public class AllCars extends JPanel {

    private JButton refreshView;
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
        refreshView = new JButton("Refresh");
        refreshView.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	results.setText("");
            	results.setText(DatabaseUI.exQuery.executeResults("select * from car", dbc));
            	String countResult = DatabaseUI.exQuery.executeResults("select count(VIN) from car", dbc);
            	String[] countResultTok = countResult.split("\\s+");
            	numberOfCars.setText("# OF CARS: " + countResultTok[countResultTok.length - 1]); 
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
        topView.add(refreshView);
        topView.add(numberOfCars);
        
        this.add(frame);
        
        // Execute Query
        results.setText("");
    	results.setText(DatabaseUI.exQuery.executeResults("select * from car", dbc));
    	String countResult = DatabaseUI.exQuery.executeResults("select count(VIN) from car", dbc);
    	String[] countResultTok = countResult.split("\\s+");
    	numberOfCars.setText("# OF CARS: " + countResultTok[countResultTok.length - 1]); 
    }


}
