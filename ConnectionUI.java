/**
 * Created by nsama on 4/22/14.
 */


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.util.*;
import java.io.*;

public class ConnectionUI extends JDialog implements ActionListener{


    boolean isCanceled = true;

    private Properties props = null;
    private Container container = null;

    private JLabel      l_url = new JLabel("Database URL");
    private JTextField tf_url = new JTextField();
    private JLabel      l_driver = new JLabel("Driver");
    private JTextField tf_driver = new JTextField();
    private JLabel      l_db = new JLabel("Database");
    private JTextField tf_db = new JTextField();
    private JLabel      l_user = new JLabel("User Name");
    private JTextField tf_user = new JTextField();
    private JLabel      l_pswd = new JLabel("Password");
    private JPasswordField tf_pswd = new JPasswordField();
    private JLabel      l_host = new JLabel("Host Name");
    private JTextField tf_host = new JTextField();
    private JLabel      l_port = new JLabel("Port");
    private JTextField tf_port = new JTextField();

    private JButton buttonOK = null;
    private JButton buttonCancel = null;

    public ConnectionUI(JFrame owner,Properties p) {
        // Call the JDialog constructor.
        super(owner, "database connection",true);

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int h = screenSize.height;
        int w = screenSize.width;
        setSize(w/3,h/4);
        setLocation(w/3, h/3);


        props = new Properties(p);
        setProps();

        container = this.getContentPane();

        //  Create a confirmation button.
        buttonOK = new JButton("OK");
        buttonOK.setMnemonic('O');
        buttonOK.setPreferredSize(new Dimension(70, 25));
        buttonOK.addActionListener(this);

        //  Create a cancel button.
        buttonCancel = new JButton("Cancel");
        buttonCancel.setMnemonic('C');
        buttonCancel.setPreferredSize(new Dimension(75, 25));
        buttonCancel.addActionListener(this);

        //  Create a panel to hold the buttons
        JPanel butPanel = new JPanel();
        butPanel.add(buttonOK);
        butPanel.add(buttonCancel);

        //  Use a grid layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(7,2));
        mainPanel.add(l_url);
        mainPanel.add(tf_url);
        mainPanel.add(l_host);
        mainPanel.add(tf_host);
        mainPanel.add(l_port);
        mainPanel.add(tf_port);
        mainPanel.add(l_db);
        mainPanel.add(tf_db);
        mainPanel.add(l_user);
        mainPanel.add(tf_user);
        mainPanel.add(l_pswd);
        mainPanel.add(tf_pswd);
        mainPanel.add(l_driver);
        mainPanel.add(tf_driver);

        container.setLayout(new BorderLayout());
        container.add(mainPanel,BorderLayout.CENTER);
        container.add(butPanel,BorderLayout.SOUTH);
    }

    public void actionPerformed(ActionEvent e)
    {
        if( e.getSource() == buttonOK ) { isCanceled=false; }

        // Close dialog after OK or Cancel button clicked
        this.dispose();
    }

    public void setProps() {
        tf_url.setText(props.getProperty("DATABASE_URL"));
        tf_driver.setText(props.getProperty("DRIVER"));
        tf_db.setText(props.getProperty("DATABASE"));
        tf_user.setText(props.getProperty("USER_NAME"));
        tf_host.setText(props.getProperty("HOST_NAME"));
        tf_port.setText(props.getProperty("PORT"));
    }

    public Properties getProps() {
        props.put("DATABASE_URL",tf_url.getText());
        props.put("DRIVER",tf_driver.getText());
        props.put("DATABASE",tf_db.getText());
        props.put("USER_NAME",tf_user.getText());
        props.put("HOST_NAME",tf_host.getText());
        props.put("PORT",tf_port.getText());
        return props;
    }

    public String getPassword() {
        return new String(tf_pswd.getPassword());
    }

}



