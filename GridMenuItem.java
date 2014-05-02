
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.*;

public class GridMenuItem {
	
	//The object the menu item is representing
	private Object target;
	
	//The name of the Object
	private String name;
	
	//Button of object
	JButton button;
	
	//Image of the object

    public  GridMenuItem(DBConnection conn,Properties props){
        createButton(conn,props);
    }


	public boolean searchName(String searchText){
		return name.toUpperCase().contains(searchText.toUpperCase());
	}

	/**
	 * This function returns an button representation of the object
	 * @return
	 */
	 
	public JButton createButton(final DBConnection conn, final Properties props){
		//Create Button
        this.name = props.getProperty("Name");
		button = new JButton(props.getProperty("Name"));
        button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                JPanel panel = new CarInformation(conn, props);
                DatabaseUI.refresh(panel);
            }
        });
		return button;
	}
	
	/**
	 * This function returns an button representation of the object
	 * @return
	 */
	public JButton getButton(){
		return button;
	}

}
