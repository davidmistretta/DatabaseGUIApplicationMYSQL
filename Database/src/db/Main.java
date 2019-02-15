package db;
import java.sql.Connection;
/*
 * data base implements MySQL Connections and Operations through terminal
 */
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
public class Main {
	
	/* Hard coded Database - can query user for info */
	private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/mysqldb";
	private static final String DB_USER = "root";
	private static final String DB_PW = "password";
	private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
	
	
	public static void main(String[] args) 
	{
				
		/* Load and Register the driver */
		try
		{
			Class.forName(DB_DRIVER);
		}
		catch(Exception e)
		{
			System.out.println("Failed to load and register driver: " + e.getMessage());
		}
		
		
		/* Define SQL Commands to be used for database operations */
		/* What conditions exist in SQL
		 * SQL requires single quotes around text values 
		 * (most database systems will also allow double quotes).
		 * However, numeric fields should not be enclosed in quotes:
		 * 
		 * WHERE numericVar = 1
		 * WHERE stringVar = 'string'
		 *  Operators
		 *  	=
		 *  	<> (not equal)
		 *  	>
		 *  	<
		 * 		>=
		 * 		<=
		 * 		BETWEEN a certain range
		 * 		LIKE	a certain pattern
		 * 		IN		to specify multiple values in a column
		 * 
		 * SQLCOMMANDS BELOW
		 */
		viewUserData();
		viewAllData();
		insertData();
		removeData();
		createTable();
	}
	/* getDBConnection returns a Connection object that represents the database */
	private static Connection getDBConnection() 
	{
		Connection dbConnection = null;
		try
		{
			/* Connect to Database using Connection dbConnection object */
			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PW); //Connects to database
			System.out.println("Successfully connected to " + DB_CONNECTION + " database");
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return dbConnection;
	}
	
	/* CREATE TABLE JAVA MySQL METHOD */
	private static void createTable() 
	{
		Connection dbConnection = null;
		PreparedStatement pSt = null;
		
		
		String SQLTable = "CREATE TABLE ALIEN("
				+ "USER_ID NUMBER(5) NOT NULL, "
				+ "USERNAME VARCHAR(20) NOT NULL, "
				+ "CREATED_BY VARCHAR(20) NOT NULL, "
				+ "CREATED_DATE DATE NOT NULL, " + "PRIMARY KEY (USER_ID) "
				+ ")";
		try
		{
			dbConnection = getDBConnection();
			pSt = dbConnection.prepareStatement(SQLTable);
			System.out.println(SQLTable);
			
			/* execute and create SQL Table */
			pSt.executeUpdate();
			System.out.println("Table \"ALIEN\"created");
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
	}

	/* VIEW ALL DATA JAVA MySQL METHOD */
	private static void viewAllData() 
	{
		Connection dbConnection = null;
		Statement st = null;
		String query = "SELECT * FROM person";

		try
		{		
			dbConnection = getDBConnection();
			st = dbConnection.createStatement();
		    ResultSet rs = st.executeQuery(query);	//create ResultSet object
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
	
	/* VIEW SPECIFIC USER DATA - VARIATION OF VIEW ALL DATA */
	private static void viewUserData()
	{
		Connection dbConnection = null;
		Statement st = null;
		String query = "SELECT * FROM person";

		try 
		{
			dbConnection = getDBConnection();
			st = dbConnection.createStatement();
			System.out.println("Enter the Person ID of who you want to search for: ");
			Scanner keyboard = new Scanner(System.in);
			int id = keyboard.nextInt();
			ResultSet rs = st.executeQuery(query);		//initialize pSt
	      	int count = 0;
	      	
		    while (rs.next())	//view all contents pulled from query
		    {
		    	if(id == rs.getInt("PersonID"))	//Only pulls contents that matches the ID user inputs
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
			        System.out.format("%s, %s, %s, %s, %s, %s, %s, %s, %s,\n", count,PersonID, LastName, FirstName, Email, PhoneNumber, Address, City, ZipCode);
		    	}
		    	count++;
		    }
		    keyboard.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	/* INSERT DATA JAVA MySQL METHOD */
	private static void insertData() 
	{
		/* Passed a prepared statement object, an String object holding the SQL insert statement */
		/* and a Connection object in order to initialize the prepared statement object and append data to the DB */
		String insert = "INSERT INTO person(PersonID, LastName, FirstName, Email, PhoneNumber, Address, City, ZipCode) "
				+ "VALUES(?,?,?,?,?,?,?,? )";
		Connection dbConnection = null;
		PreparedStatement pSt = null;
		try 
		{
			dbConnection = getDBConnection();
			/* Prepare Statement */
			pSt = dbConnection.prepareStatement(insert);
			pSt.setInt(1,06);
			pSt.setString(2,"Payne");
			pSt.setString(3,"Kalli");
			pSt.setString(4,"kalliPayne@gmail.com");
			pSt.setString(5, "978855418");
			pSt.setString(6,"943 Lakedale Way");
			pSt.setString(7,"Sunnyvale");
			pSt.setString(8,"94089");			
			pSt.execute();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}

		System.out.println("Successfully executed update of database using the SQL declaration: \n" + insert);
		
	}
	
	/* DELETE FROM JAVA MySQL METHOD */
	private static void removeData()
	{
		/*
		  	Create a Java Connection to our MySQL database. PASSED
			Create a SQL DELETE query statement.
			Create a Java PreparedStatement for our SQL DELETE query. PASSED
			Set the fields on our Java PreparedStatement object.
			Execute our Java PreparedStatement.
			Close our Java MySQL database connection.
			Catch any SQL exceptions that may come up during the process.
		*/
		Connection dbConnection = null;
		PreparedStatement pSt = null;
		String deletion = "DELETE FROM person WHERE PersonID = ?";
		Scanner keyboard = new Scanner(System.in);
		try
		{
			dbConnection = getDBConnection();
			System.out.println("Enter the PersonID of the user you'd like to delete.");
			viewAllData();	//View database before deletion
			System.out.println(": ");
			int id = keyboard.nextInt();
			
			/* PreparedStatement pSt deleting */
			pSt = dbConnection.prepareStatement(deletion);
			pSt.setInt(1, id);
			pSt.executeUpdate();
			
			viewAllData();		//View database after deletion
			keyboard.close();		
			
		}
		catch(SQLException e) 
		{
			e.printStackTrace();
		}
	}


}
