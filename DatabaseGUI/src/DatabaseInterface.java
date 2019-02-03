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
		add(panel);
		setVisible(true);
	}
	private void buildPanel()
	{
		/*
		 * Display Box for user to input Database URL
		 */
		dbURLLabel = new JLabel("Database URL");
		dbURLLabel.setAlignmentX(CENTER_ALIGNMENT);
		dbURLTextField =  new JTextField(24);
		dbURLTextField.setAlignmentX(CENTER_ALIGNMENT);
		dbURLTextField.setHorizontalAlignment(JTextField.CENTER);
		
		/*
		 * Display Box for user to input username for database
		 */
		dbUserLabel = new JLabel("Username");
		dbUserLabel.setAlignmentX(CENTER_ALIGNMENT);
		dbUserTextField = new JTextField(24);
		dbUserTextField.setAlignmentX(CENTER_ALIGNMENT);
		dbUserTextField.setHorizontalAlignment(JTextField.CENTER);
		
		/*
		 * Display box for user to input password for database
		 */
		dbPWLabel = new JLabel("Password");
		dbPWLabel.setAlignmentX(CENTER_ALIGNMENT);
		dbPWTextField = new JPasswordField(24);
		dbPWTextField.setAlignmentX(CENTER_ALIGNMENT);
		dbPWTextField.setHorizontalAlignment(JTextField.CENTER);
		
		connectButton = new JButton("Connect");
		connectButton.setAlignmentX(CENTER_ALIGNMENT);
		connectButton.addActionListener(new ConnectButtonListener());
		dbURLTextField.setMaximumSize(dbURLTextField.getPreferredSize());
		
		dbUserTextField.setMaximumSize(dbUserTextField.getPreferredSize());
		
		dbPWTextField.setMaximumSize(dbPWTextField.getPreferredSize());
		
		
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		panel.add(dbURLLabel);
		panel.add(dbURLTextField);
		
		panel.add(dbUserLabel);
		panel.add(dbUserTextField);
		
		panel.add(dbPWLabel);
		panel.add(dbPWTextField);
		panel.add(connectButton);
		
	}
	/*
	 * action listener
	 */
	public class ConnectButtonListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			String dbURL, user, pw;
			
			dbURL = dbURLTextField.getText();
			user = dbUserTextField.getText();
			pw = new String(dbPWTextField.getPassword());
			String strBreak = "--";
			
			for(int i = 0; i < dbURL.length()-5; i++)
			{
				strBreak += "--";
			}
			Connection conn = null; 
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
				System.out.println("SQLException: " + e1.getMessage());
			}		

		}
		
	}

	//Embedded Main in GUI class	
	public static void main(String[] args)
	{
		String driver = "com.mysql.cj.jdbc.Driver";
		boolean okay = true;
		/*
		 * Register DB Driver
		 */
		try
		{
			Class.forName(driver);
		}
		catch(Exception e)
		{
			okay = false;
			System.out.println("Failed to load and register driver: " + e.getMessage());
		}
		
		if(okay) {
			DatabaseInterface DBI = new DatabaseInterface();
		}
	}
}
