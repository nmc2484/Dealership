import javax.swing.*;
import java.awt.*;

/**
 * Created by nsama on 4/20/14.
 */
public class AllCars extends JPanel {

    private JButton addNewCar;
    private JPanel topView;
    private JLabel numberOfCars;
    private String totalCars;

    public AllCars(DBConnection conn){
        this.setLayout(new BorderLayout());
        topView = new JPanel();
        addNewCar = new JButton("ADD A CAR");
        numberOfCars = new JLabel();
        topView.setLayout(new FlowLayout(FlowLayout.LEFT));
        topView.add(addNewCar);
    }


}
