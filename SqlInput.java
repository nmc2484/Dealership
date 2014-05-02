import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.*;

/**
 * Created by nsama on 4/22/14.
 */
public class SqlInput extends JPanel  {
    private  JPanel frame;
    private static JTextArea sql = null;
    private static JTextArea results = null;
    private DBConnection dbc;

    public SqlInput(DBConnection db)

    {    	
        this.dbc = db;
        this.setLayout(new BorderLayout());
        frame = new JPanel();
        frame.setLayout(new BorderLayout());

        //Text areas and buttons for a frame

        sql = new JTextArea(5, 40);
        sql.setFont(new Font("Courier New", Font.PLAIN, 12));
        results = new JTextArea();
        results.setFont(new Font("Courier New", Font.BOLD, 12));
        JScrollPane scrollSQL = new JScrollPane(sql);
        scrollSQL.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("SQL Statement"),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)),
                scrollSQL.getBorder()
        ));
        JScrollPane scrollResults = new JScrollPane(results);

        scrollResults.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Result"),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)),
                scrollResults.getBorder()
        ));

        JButton buttonExe = new JButton("Execute");
        buttonExe.setMnemonic('X');
        buttonExe.setPreferredSize(new Dimension(115, 25));
        buttonExe.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                    	results.setText("");
                        results.setText(DatabaseUI.exQuery.executeResults(sql.getText(), dbc));
                    }
                }
        );

        JButton buttonReset = new JButton("Reset");
        buttonReset.setMnemonic('R');
        buttonReset.setPreferredSize(new Dimension(115, 25));
        buttonReset.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        sql.setText("");
                        results.setText("");
                    }
                }
        );

        JPanel butPanel = new JPanel();
        butPanel.add(buttonExe);
        butPanel.add(buttonReset);

        JLabel sqlLabel = new JLabel(" ");

        JPanel sqlPanel = new JPanel();
        sqlPanel.setLayout(new BorderLayout());
        sqlPanel.add(sqlLabel, BorderLayout.NORTH);
        sqlPanel.add(scrollSQL);
        sqlPanel.add(butPanel, BorderLayout.SOUTH);


        frame.add(sqlPanel, BorderLayout.NORTH);
        frame.add(scrollResults, BorderLayout.CENTER);
        this.add(frame);
    }


}
