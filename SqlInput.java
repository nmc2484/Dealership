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
                        executeResults();
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

    private static String spacedString(String s, int w) {
        if ( s==null ) s="null";
        while ( s.length()<w ) {
            s+=" ";
        }
        return s;
    }
    private static String dashedString(String s, int w) {
        while ( s.length()<w ) {
            s+="-";
        }
        return s;
    }



    private void executeResults() {
        results.setText("");

        String s = sql.getText();
        String str = "";
        StringTokenizer st = new StringTokenizer(s);

        if ( st.hasMoreElements() ) {
            str+=st.nextElement();
        }
        if ( st.hasMoreElements() ) {
            str+=st.nextElement();
        }

        if ( str.length()==0 ) {
            results.setText("SQL statement is empty");
            return ;
        }

        if ( (str.length()>=6 && str.substring(0,6).equalsIgnoreCase("SELECT")) || (str.length()>=4 && str.substring(0,4).equalsIgnoreCase("SHOW")) || (str.length()>=4 && str.substring(0,4).equalsIgnoreCase("DESC")) ) {

            try {
                ResultSet r = dbc.executeQuery(s);
                ResultSetMetaData meta = r.getMetaData();
                String out="";
                int w[] = new int[meta.getColumnCount()];
                for ( int i=1; i<=meta.getColumnCount(); i++ ) {
                    w[i-1] = meta.getColumnLabel(i).length();
                    r.beforeFirst();
                    while ( r.next() ) {
                        String temp = new String("");
                        if(r.getString(i) == null) temp="----";
                        else temp = r.getString(i);
                        if (w[i-1] < temp.length()) w[i-1] = temp.length();
                    }
                    r.beforeFirst();
                }

                for ( int i=1; i<meta.getColumnCount(); i++ ) {
                    w[i-1] += 3;
                }

                for ( int i=1; i<=meta.getColumnCount(); i++ ) {
                    out += spacedString(meta.getColumnLabel(i),w[i-1]);
                }
                out+="\n";
                for ( int i=1; i<=meta.getColumnCount(); i++ ) {
                    out += dashedString("",w[i-1]-1);
                    if (i==meta.getColumnCount()) out+="-+";
                    else out+="+";
                }
                out+="\n";

                while ( r.next() ) {
                    for ( int i=1; i<=meta.getColumnCount(); i++ ) {
                        out+=spacedString(r.getString(i),w[i-1]);
                    }
                    out+="\n";
                }
                results.setText(out);

                return;
            }
            catch (SQLException ex) {
                results.setText("Error: "+ex);
                return;
            }
        }


        try {
            int r = dbc.executeUpdate(s);

            if ( str.equalsIgnoreCase("CREATETABLE") && r == 0 ) {results.setText("Table was created successfully");}
            else
            if ( str.equalsIgnoreCase("CREATEINDEX") && r == 0 ) {results.setText("Index was created successfully");}
            else
            if ( str.equalsIgnoreCase("DROPTABLE") && r == 0 )   {results.setText("Table was droped successfully");}
            else
            if ( str.equalsIgnoreCase("DROPINDEX") && r == 0 )   {results.setText("Index was droped successfully");}
            else
            if ( str.length()>=6 && str.substring(0,6).equalsIgnoreCase("INSERT") ) {results.setText(Integer.toString(r)+" rows were inserted successfully");}
            else
            if ( str.length()>=6 && str.substring(0,6).equalsIgnoreCase("DELETE") ) {results.setText(Integer.toString(r)+" rows were deleted successfully");}
            else
            if ( str.length()>=6 && str.substring(0,6).equalsIgnoreCase("UPDATE") ) {results.setText(Integer.toString(r)+" rows were updated successfully");}
            else
            if ( r == 0 )   {results.setText("Statement was executed successfully");}


        }
        catch (SQLException ex) {
            results.setText("Error: "+ex);
            return;
        }

    }




}
