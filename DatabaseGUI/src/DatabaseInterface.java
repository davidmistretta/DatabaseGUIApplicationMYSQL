import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.*;

public class DatabaseInterface extends JFrame
{
	private JPanel panel;
	private JLabel dbURLLabel, dbUserLabel, dbPWLabel;
	private JTextField dbURLTextField, dbUserTextField;
	private JPasswordField dbPWTextField;
	private JButton connectButton;
	private JCheckBox checkBox;
	private JProgressBar connectionProgressBar; //declare connection progress bar
	private final int WINDOW_WIDTH = 500;
	private final int WINDOW_HEIGHT = 250;
	

	public DatabaseInterface()
	{	
		//Call JFrame constructor
		super("Database GUI");
		setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/*
		 * build Panel is a private void method
		 */
		buildPanel();
		//adds panel that was built into JFrame
		add(panel);
		setVisible(true);
	}
	private void buildPanel()
	{
		/*
		 * Display Box for user to input Database URL and format
		 */
		dbURLLabel = new JLabel("Database URL");
		dbURLLabel.setAlignmentX(CENTER_ALIGNMENT);
		dbURLTextField =  new JTextField(24);
		dbURLTextField.setAlignmentX(CENTER_ALIGNMENT);
		dbURLTextField.setHorizontalAlignment(JTextField.CENTER);
		dbURLTextField.setMaximumSize(dbURLTextField.getPreferredSize());

		/*
		 * Display Box for user to input username for database and format
		 */
		dbUserLabel = new JLabel("Username");
		dbUserLabel.setAlignmentX(CENTER_ALIGNMENT);
		dbUserTextField = new JTextField(24);
		dbUserTextField.setAlignmentX(CENTER_ALIGNMENT);
		dbUserTextField.setHorizontalAlignment(JTextField.CENTER);
		dbUserTextField.setMaximumSize(dbUserTextField.getPreferredSize());
		
		/*
		 * Display box for user to input password for database and format
		 */
		dbPWLabel = new JLabel("Password");
		dbPWLabel.setAlignmentX(CENTER_ALIGNMENT);
		dbPWTextField = new JPasswordField(24);
		dbPWTextField.setAlignmentX(CENTER_ALIGNMENT);
		dbPWTextField.setHorizontalAlignment(JTextField.CENTER);	
		dbPWTextField.setMaximumSize(dbPWTextField.getPreferredSize());
		dbPWTextField.setEchoChar('*');
		/*
		 * Button that initiates connection to database
		 */
		connectButton = new JButton("Connect");
		connectButton.setAlignmentX(CENTER_ALIGNMENT);
		connectButton.addActionListener(new ConnectButtonListener());
		/*
		 * Progress Bar for data connections
		 * Will be linked to ConnectButtonListener and will show progress as connection is taking place
		 */
		connectionProgressBar = new JProgressBar(0, 100);
		connectionProgressBar.setMaximumSize(dbPWTextField.getPreferredSize());
		/*
		 * Checkbox for password
		 */
		checkBox = new JCheckBox("view password");
		checkBox.setAlignmentX(CENTER_ALIGNMENT);
		checkBox.addActionListener(new CheckBoxPWViewListener());
		/*
		 * Add labels, textfields,  buttons, and checkbox to panel
		 */
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		panel.add(dbURLLabel);
		panel.add(dbURLTextField);
		
		panel.add(dbUserLabel);
		panel.add(dbUserTextField);
		
		panel.add(dbPWLabel);
		panel.add(dbPWTextField);
		panel.add(checkBox);
		panel.add(connectButton);
		panel.add(connectionProgressBar);
		
	}
	/*
	 * private inner class that handles the event
	 * when the check button is clicked
	 */
	private class CheckBoxPWViewListener implements ActionListener
	{
		public void actionPerformed(ActionEvent ex)
		{
			if(checkBox.isSelected())
				dbPWTextField.setEchoChar((char)0);
			else
				dbPWTextField.setEchoChar('*');
		}
	}
	/*
	 * private inner class that handles the event when 
	 * the connect button is clicked
	 * action listener
	 */
	private class ConnectButtonListener implements ActionListener
	{
		/*
		 * for progress bar
		 */
		//connectButton.setEnabled(false);
		//task = new Task();
		@Override
		public void actionPerformed(ActionEvent e)
		{
			String dbURL, user, pw;
			
			
			/*
			 * Get information from panel
			 */
			dbURL = dbURLTextField.getText();
			user = dbUserTextField.getText();
			pw = new String(dbPWTextField.getPassword()); //stringify password
			String strBreak = "--";
			
			/*
			 * for loop for formatting output
			 */
			for(int i = 0; i < dbURL.length()-5; i++)
			{
				strBreak += "--";
			}
			Connection conn = null; 
			/*
			 * try to establish connection
			 * catch print error
			 */
			try
			{
				conn = DriverManager.getConnection(dbURL, user, pw);
				
				JOptionPane.showMessageDialog(null, ""
						+ "Succesfully Connected!"
						+ "\n--------------" + strBreak
						+ "\nDatabase URL: " + dbURL 
						+ "\nUsername: " + user
						+ "\nPassword: " + pw);
			}
			catch(SQLException e1)
			{
				JOptionPane.showMessageDialog(null, "SQLException: " + e1.getMessage());
			}		

		}
		
	}

	//Embedded Main in GUI class	
	public static void main(String[] args)
	{
		
		String driver = "com.mysql.cj.jdbc.Driver";
		boolean okay = true;
		/*
		 * try to Register DB Driver
		 */
		try
		{
			Class.forName(driver);
		}
		catch(Exception e)
		{
			/*
			 * failed to load driver
			 */
			okay = false;
			JOptionPane.showMessageDialog(null, "Failed to load and register driver: " + e.getMessage());
		}
		
		if(okay) {
			DatabaseInterface DBI = new DatabaseInterface();
		}
	}
}
