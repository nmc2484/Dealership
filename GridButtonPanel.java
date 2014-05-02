


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.Border;

/**
 * This class is the grid button panel that is used to create
 * a usable touch screen interface for many of the views in our system.
 * It utilizes the GridMenuItem as it's input data structure.
 * @author Eric Majchrzak
 *
 */
public class GridButtonPanel extends JPanel {
	
	//The current page of buttons being viewed
	private int currentPage = 0;
	
	//The list of menu items
	private ArrayList<GridMenuItem> menuItems;
	
	//The list of filtered menu items that met the search qualifications
	private ArrayList<GridMenuItem> currentList;
	
	//The pane containing all of the buttons and left and right arrow buttons
	private JPanel centerPane;
	
	//The add new button
	private JButton addButton;
	
	//The search bar
	private JPanel searchBar;
	
	//The title bar
	private JPanel titlePanel;
	
	//The search text field
	private JTextField searchText;
	
	//The name of the page
	private String name;
	
	//Does the page have an add button
	boolean hasAddButton;
	
	//Get screen size
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	public GridButtonPanel(String mainName, ArrayList<GridMenuItem> menuItems, ActionListener addButtonListener){
		
		this.menuItems = menuItems;
		this.currentList = menuItems;
		this.name = mainName;
	
		hasAddButton = false;
		
		//Create the title
		titlePanel = new JPanel(new GridLayout(1, 1), false);
		JLabel titleLabel = new JLabel(mainName, JLabel.CENTER);
		titleLabel.setFont(new Font("Serif", Font.BOLD, 30));
		titlePanel.add(titleLabel);
		
		//Title Border
		Border blueline = BorderFactory.createLineBorder(Color.blue);
		titlePanel.setBorder(blueline);
	
		//Create the add button
		if(addButtonListener != null){
			hasAddButton = true;
			addButton = new JButton("Add New " + mainName);
			addButton.setPreferredSize(new Dimension(40, screenSize.height/18));

			//addButton ActionListener
			addButton.addActionListener(addButtonListener);
		}
		
		//Put Center Pane Together
		centerPane = new JPanel();
		centerPane.setLayout(new BorderLayout());
		centerPane.add(buildGridButtons(),BorderLayout.CENTER);
		centerPane.add(buildLeftButton(),BorderLayout.WEST);
		centerPane.add(buildRightButton(),BorderLayout.EAST);
        if (hasAddButton){
            centerPane.add(addButton,BorderLayout.NORTH);
        }
		
		//Create Search bar elements
		searchText = new JTextField("Search...");
		searchText.setFont(new Font("Serif", Font.PLAIN, 25));
		searchText.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                searchText.setText("");
            }
        });
		
		JButton searchButton = new JButton("Search");
		searchButton.setPreferredSize(new Dimension(screenSize.width/18, 40));
		
		//searchButton ActionListener
		searchButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				search();
			}
		});
		
		//Put search bar together
		searchBar = new JPanel();
		searchBar.setLayout(new BorderLayout());
		searchBar.add(searchText,BorderLayout.CENTER);
		searchBar.add(searchButton,BorderLayout.EAST);
		searchBar.setPreferredSize(new Dimension(40, screenSize.height/18));
		
		
		//Add panels to the window
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(titlePanel,BorderLayout.NORTH);
		centerPanel.add(centerPane,BorderLayout.CENTER);
		centerPanel.add(searchBar,BorderLayout.SOUTH);
		this.setLayout(new BorderLayout());
		this.add(centerPanel,BorderLayout.CENTER);

	}
	
	/**
	 * This function will be called when the left arrow 
	 * button is pressed.
	 */
	private void leftButtonPressed(){
		this.currentPage--;
		refresh();
	}
	
	/**
	 * This function will be called when the right arrow 
	 * button is pressed.
	 */
	private void rightButtonPressed(){
		this.currentPage++;
		refresh();
	}
	
	/**
	 * This function will be called to build the grid of
	 * buttons that represent the objects that the menu is
	 * displaying.
	 * @return a 3 by 3 grid view of buttons
	 */
	private JPanel buildGridButtons(){
		//Create the grid
		JPanel newGridPanel = new JPanel(new GridLayout(3, 3, 0, 0), false);
				
		//Add buttons to the grid
		for(int i = (9 * currentPage); i < (9 * currentPage + 9); i++){
			if(i < currentList.size()){
				JButton temp = currentList.get(i).getButton();
				temp.setFont(new Font("Serif", 1, 30));
				newGridPanel.add(temp);
				
			//Keep the grid buttons sized correctly
			} else {
				JButton temp = new JButton();
				temp.setVisible(false);
				newGridPanel.add(temp);
			}
		}
		
		return newGridPanel;
	}
	
	/**
	 * This function will build the right button. The right
	 * button will be grayed out if there is no more objects to display
	 * @return the right page button
	 */
	private JButton buildRightButton(){
		//Create the right button
		JButton rightButton = new JButton(">");
		rightButton.setPreferredSize(new Dimension(screenSize.width/18, 40));
					
		//leftButton ActionListener
		rightButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
					rightButtonPressed();
				}
			});
						
		//Gray right Button if needed
		if((currentPage * 9 + 9) >= currentList.size()){
			rightButton.setEnabled(false);
		}
		return rightButton;
	}
	
	/**
	 * This function will build the right button. The right
	 * button will be grayed out if there is no more objects to display
	 * @return the left page button
	 */
	private JButton buildLeftButton(){
		//Create the left button
		JButton leftButton = new JButton("<");
		leftButton.setPreferredSize(new Dimension(screenSize.width/18, 40));
				
		//leftButton ActionListener
		leftButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				leftButtonPressed();
			}
		});
				
		//Gray left Button if needed
		if(currentPage == 0){
			leftButton.setEnabled(false);
		}
		return leftButton;
	}
	
	/**
	 * This function will search the list of GridMenuItems for items
	 * that share text with the text in the search bar. It will then
	 * update the window with the new list.
	 */
	private void search() {
		ArrayList<GridMenuItem> newList = new ArrayList<GridMenuItem>();
		String text = searchText.getText();
		
		for(int i=0;i<menuItems.size();i++){
			if(menuItems.get(i).searchName(text)){
				newList.add(menuItems.get(i));
			}
		}
		
		currentList = newList;
		currentPage = 0;
		refresh();
		
	}
	
	/**
	 * The addNew() function is called whenever the add new button is
	 * pressed and has yet to be implemented. For now it just refreshes
	 * the panel.
	 */
	private void addNew(){
		refresh();
	}
	
	/**
	 * This is the refresh function. It is used to reload the active
	 * grid button page. It is primarily used in the button functions
	 * and the search function.
	 */
	private void refresh(){
		//build new center pane
		centerPane = new JPanel();
		centerPane.setLayout(new BorderLayout());
		centerPane.add(buildGridButtons(),BorderLayout.CENTER);
		centerPane.add(buildLeftButton(),BorderLayout.WEST);
		centerPane.add(buildRightButton(),BorderLayout.EAST);
		if(hasAddButton)
			centerPane.add(addButton,BorderLayout.NORTH);
		this.removeAll();
		
		//Add panels to the window
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(titlePanel,BorderLayout.NORTH);
		centerPanel.add(centerPane,BorderLayout.CENTER);
		centerPanel.add(searchBar,BorderLayout.SOUTH);
		this.setLayout(new BorderLayout());
		this.add(centerPanel,BorderLayout.CENTER);
		
		this.revalidate();
	}
}
