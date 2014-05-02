import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * Created by nsama on 5/2/14.
 */

public class SellACar extends JDialog {
    private DBConnection dbconn;
    private Properties props;
    private Container container;
    private JPanel mainPanel;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel      employeeLabel = new JLabel("Employee ID");
    private JTextField employeeField = new JTextField();
    private JLabel      customerLabel = new JLabel("Customer ID");
    private JComboBox cstomerField;
    private JLabel priceSoldLable = new JLabel("Price Sold At");
    private JTextField priceSoldField = new JTextField();

    public SellACar(JFrame owner, final DBConnection dbcon, final String carVin){

        super(owner,"Selling car",true);
        props = new Properties();
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int h = screenSize.height;
        int w = screenSize.width;
        setSize(w/3,h/4);
        setLocation(w/3, h/3);
        this.dbconn = dbcon;

        container = this.getContentPane();
        container.setLayout(new BorderLayout());
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3,3));
        JPanel butPanel = new JPanel();

        buttonOK = new JButton("OK");
        buttonOK.setMnemonic('O');
        buttonOK.setPreferredSize(new Dimension(70, 25));
        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                props.setProperty("Vin", carVin);
                props.setProperty("date_sold", dateFormat.format(date));
                props.setProperty("customer_id", cstomerField.getText());
                props.setProperty("employee_id", employeeField.getText());
                props.setProperty("price_sold", priceSoldField.getText());
                dbcon.sellACar(props);
                closeWindow();

            }
        });

        buttonCancel = new JButton("Cancel");
        buttonCancel.setMnemonic('C');
        buttonCancel.setPreferredSize(new Dimension(75, 25));
        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                closeWindow();

            }
        });

        butPanel.add(buttonOK);
        butPanel.add(buttonCancel);
        mainPanel.add(employeeLabel);
        mainPanel.add(employeeField);
        mainPanel.add(customerLabel);
        mainPanel.add(cstomerField);
        mainPanel.add(priceSoldLable);
        mainPanel.add(priceSoldField);
        container.add(mainPanel,BorderLayout.CENTER);
        container.add(butPanel,BorderLayout.SOUTH);



    }

    public void closeWindow(){
        this.dispose();
    }



}
