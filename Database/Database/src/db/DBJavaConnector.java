package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * DBJavaConnector is a class will allow for easily passing SQL statements to a database
 * using MySQL connections
 * @author David
 *
 */
public class DBJavaConnector 
{
	
	private String DB_CONNECTOR, DB_USER, DB_PW, DB_DRIVER;
	private Connection dbConnection;
	/**
	 * Constructor sets Database Connection variables and connects to database
	 */
	public DBJavaConnector()
	{
		DB_CONNECTOR = "jdbc:mysql://localhost:3306/mysqldb";
		DB_USER = "root";
		DB_PW = "password";
		DB_DRIVER = "com.mysql.cj.jdbc.Driver";
		makeDBConnection();
		dbConnection = getDBConnection();
	}
	public DBJavaConnector(String DBU, String DBPW)
	{
		//DBJavaConnection DBJC = new DBJavaConnector(String x, String y) <-- Signature = DBJavaConnector(String, String)
		DB_CONNECTOR = "jdbc:mysql://localhost:3306/mysqldb";
		DB_USER = DBU;
		DB_PW = DBPW;
		DB_DRIVER = "com.mysql.cj.jdbc.Driver";
		makeDBConnection();
		dbConnection = getDBConnection();
	}
	public DBJavaConnector(String DBC, String DBU, String DBPW, String DBD)
	{
		
		DB_CONNECTOR = DBC;
		DB_USER = DBU;
		DB_PW = DBPW;
		DB_DRIVER = DBD;
		makeDBConnection();
		dbConnection = getDBConnection();
	}
	
	/*
	 * METHOD DECLARATIONS BELOW
	 */
	public String getDBConnector()
	{
		return DB_CONNECTOR;
	}
	public String getDBUser()
	{
		return DB_USER;
	}
	public String getDBPassword()
	{
		return DB_PW;
	}
	public String getDBDriver()
	{
		return DB_DRIVER;
	}
	public Connection getDBConnection()
	{
		return dbConnection;
	}
	/** 
	 * makeDBConnection returns a Connection object that represents the database
	 * used for all methods
	 */
	private void makeDBConnection() 
	{
		try
		{
			/* Connect to Database using Connection dbConnection object */
			dbConnection = DriverManager.getConnection(DB_CONNECTOR, DB_USER, DB_PW); //Connects to database
			System.out.println("Successfully connected to " + DB_CONNECTOR + " database");
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public void printUserData(int inputPersonID)
	{
		/**
		 * pullUserData method will print to the screen the information of the row referenced by PersonID
		 * querying against a database in java
		 * 	1. Create a JDBC ResultSet Object
		 * 	2. Execute the SQL Query 
		 * 	3. Read the results
		 */
		PreparedStatement pSt = null;
		ResultSet rs = null;
		String query = "SELECT * FROM person WHERE PersonID = ?";
		
		try
		{
			int id = inputPersonID;
			/* PreparedStatement pSt deleting */
			pSt = dbConnection.prepareStatement(query);
			pSt.setInt(1, id);
			rs = pSt.executeQuery();
			if(!rs.next())
			{
				System.out.format("PersonID %s not found", id);
			}
			while (rs.next())	//view all contents pulled from query
			{		    
				int PersonID;
				String LastName, FirstName, Email, PhoneNumber, Address, City, ZipCode;		  
				PersonID = rs.getInt("PersonID");
				LastName = rs.getString("LastName");
				FirstName = rs.getString("FirstName");
				Email = rs.getString("Email");
				PhoneNumber = rs.getString("PhoneNumber");
				Address = rs.getString("Address");
				City = rs.getString("City");
				ZipCode = rs.getString("ZipCode");
				  
				System.out.format("PersonID: %s"
						+ "\nLast Name: %s"
						+ "\nFirst Name: %s"
						+ "\nEmail: %s"
						+ "\nPhone Number: %s"
						+ "\nAddress: %s"
						+ "\nCity %s"
						+ "\nZip Code: %s"
						+ "\n", PersonID, LastName, FirstName, Email, PhoneNumber, Address, City, ZipCode);
			
			}
			rs.close();	//close ResultSet object
		}
		catch(SQLException e) 
		{
			e.printStackTrace();
		}
	}
	/**
	 * VIEW ALL DATA JAVA MySQL METHOD 
	 */
	public void viewAllData() 
	{
		Statement st = null;
		ResultSet rs = null;
		String query = "SELECT * FROM person";
		try
		{		
			st = dbConnection.createStatement();
		    rs = st.executeQuery(query);	//create ResultSet object
		    int count = 1; 
		      
			while (rs.next())	//view all contents pulled from query
			{		    
				int PersonID;
				String LastName, FirstName, Email, PhoneNumber, Address, City, ZipCode;		  
				PersonID = rs.getInt("PersonID");
				LastName = rs.getString("LastName");
				FirstName = rs.getString("FirstName");
				Email = rs.getString("Email");
				PhoneNumber = rs.getString("PhoneNumber");
				Address = rs.getString("Address");
				City = rs.getString("City");
				ZipCode = rs.getString("ZipCode");
				  
				System.out.format("ROW %d:, %s, %s, %s, %s, %s, %s, %s, %s,"
						+ "\n", count,PersonID, LastName, FirstName, Email, PhoneNumber, Address, City, ZipCode);
				count++;
			}
			rs.close();	//close ResultSet object
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}      
	}
	
	/**
	 *  INSERT DATA JAVA MySQL METHOD
	 */
	public void insertData(int PersonID, String LastName, String FirstName, String Email, String PhoneNumber, String Address, String City, String ZipCode) 
	{
		/* User Data is passed to the method and then appended onto the table */
		String insert = "INSERT INTO person(PersonID, LastName, FirstName, Email, PhoneNumber, Address, City, ZipCode) "
				+ "VALUES(?,?,?,?,?,?,?,? )";
		PreparedStatement pSt = null;
		try 
		{
			/* Prepare Statement */
			pSt = dbConnection.prepareStatement(insert);
			pSt.setInt(1,PersonID);
			pSt.setString(2,LastName);
			pSt.setString(3,FirstName);
			pSt.setString(4,Email);
			pSt.setString(5, PhoneNumber);
			pSt.setString(6,Address);
			pSt.setString(7,City);
			pSt.setString(8,ZipCode);			
			pSt.execute();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		System.out.println("Successfully executed update of database using the SQL declaration: \n" + insert);
	}
	/* DELETE FROM JAVA MySQL METHOD */
	public void deleteData(int PersonID)
	{
		/*
		  Pass the PersonID value of the row you wish to delete
		*/
		PreparedStatement pSt = null;
		String deletion = "DELETE FROM person WHERE PersonID = ?";
		try
		{
			int id = PersonID;
			/* PreparedStatement pSt deleting */
			pSt = dbConnection.prepareStatement(deletion);
			pSt.setInt(1, id);
			pSt.executeUpdate();	
			
		}
		catch(SQLException e) 
		{
			e.printStackTrace();
		}
	}
	/**
	 * returns count of people
	 * @return
	 */
	public int getNextAvailableID()
	{
		int count = 0;
		String countSQL = "SELECT * FROM person";
		Statement st = null;		
		try
		{
			st = dbConnection.createStatement();
			ResultSet rs  = st.executeQuery(countSQL);
			while(rs.next())
			{
				count ++;
			}
		}
		catch(SQLException e)
		{
			System.out.println(e);
		}
				
		return count;
	}
	
	

}
