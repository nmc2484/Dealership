import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by benji on 5/02/14.
 */
public class Report extends JPanel {

    private JButton report1, report2, report3, report4;
    private JPanel frame, topView, buttonPanel;
    private JLabel description;
    private JTextArea results = null;
    private DBConnection dbc;

    public Report(DBConnection conn){
    	this.dbc = conn;
        this.setLayout(new BorderLayout());
        frame = new JPanel();
        frame.setLayout(new BorderLayout());
        topView = new JPanel();
        report1 = new JButton("Report 1");
        report1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                description.setText("Reports on the total number of cars sold each month for a year, " +
                		"with make, model and if these cars had service on them during that year");
                results.setText(DatabaseUI.exQuery.executeResults("select car.VIN, make, model, date_in, date_out, " +
                		"service_type from car natural join sold left join service on car.VIN = service.VIN " +
                		"where date_in between '2013-01-01' and '2014-01-01' group by MONTH(date_in);", dbc));
            }
        });
        
        report2 = new JButton("Report 2");
        report2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	description.setText("Reports on the total profit from car sales each month by model and salesman");
            	
            }
        });
        
        report3 = new JButton("Report 3");
        report3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	description.setText("Reports on car sales per month with info on if the customer took a test " +
            			"drive before purchasing the car, and what employees helped them, which information summarized");
            	
            }
        });
        
        report4 = new JButton("Report 4");
        report4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	description.setText("A monthy report on total number of cars sold by make - " +
            			"with summary information on model and salesman");
            	
            }
        });
        
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
        
        description = new JLabel("\n");
        
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(report1);
        buttonPanel.add(report2);
        buttonPanel.add(report3);
        buttonPanel.add(report4);
        
        topView.setLayout(new BorderLayout());
        topView.add(buttonPanel, BorderLayout.CENTER);
        topView.add(description, BorderLayout.SOUTH);
        
        this.add(frame);
    }


}
