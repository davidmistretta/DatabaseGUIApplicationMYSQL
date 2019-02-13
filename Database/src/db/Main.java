package db;
import java.sql.Connection;
/*
 * data base implements MySQL Connections and Operations through terminal
 */
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
public class Main {

	public static void main(String[] args) 
	{
		
		/* Hard coded Database - can query user for info */
		String url = "jdbc:mysql://localhost:3306/mysqldb";
		String user = "root";
		String pw = "password";
		String driver = "com.mysql.cj.jdbc.Driver";
		Connection conn = null;//Establish Connection object
		
		/* Load and Register the driver */
		try
		{
			Class.forName(driver);
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
		
		/* ********************************************************************************************************* */
		/* ********************************************************************************************************* */
		/* ********************************************************************************************************* */

		/* insert String for INSERT INTO SQL Command - can query user for information */
		/* the format is used for a preparedStatement statement object */
		/* will probably take out these strings and define them within each method that they are used */
		String insert = "INSERT INTO person(PersonID, LastName, FirstName, Email, PhoneNumber, Address, City, ZipCode) "
		+ "VALUES(?,?,?,?,?,?,?,? )";
		
		/* SELECT * FROM person query's the information in the person table that is within the connected DB */
		String selectFrom = "SELECT * FROM person";
		
		// UPDATE
		
		/* DELETE FROM * WHERE condition -- WHERE Conditions specified above */
		
		
	
		// CREATE DB
		
		// ALTER DB
		
		// CREATE TABLE
		
		// DROP TABLE
		
		// CREATE INDEX
		
		/* ********************************************************************************************************* */
		/* ********************************************************************************************************* */
		/* ********************************************************************************************************* */

		/*
		 * Connect to the MySQL database
		 */
		try
		{
			/* Connect to Database using Connection conn object */
			conn = DriverManager.getConnection(url, user, pw); //Connects to database
			System.out.println("Successfully connected to " + url + " database");
			
			PreparedStatement st = conn.prepareStatement(insert); 
		

			/* Insert Data method */
			//insertData(st, insert, conn);
			
			/* View data methods */
			//viewAllData(st, selectFrom);
			//viewUserData(st, selectFrom);
			
			/* Remove Data method */
			removeData(st, conn);
			
			/* close preparedStatement connection */
		    st.close();
		    
		    /* close database connection */
		    //conn.close();
		}
		catch(SQLException e)
		{
			System.out.println("We caught somethin boys!");
			System.out.println("SQLException: " + e.getMessage());
		}

	}
	
	private static void viewAllData(PreparedStatement st, String query) 
	{
		/* viewAllData method takes in a Prepared Statement object and a defined query */
		/* query format: String: SELECT * FROM tablename */
		/* Fields */
		/* ResultSet rs = st.executeQuery(query): this creates a ResultSet object which holds the */
		/* information found within tablename. It returns the data in a specific row starting at index 0. */
		/* calling rs.next() returns the next row. */
		try
		{		
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
	private static void viewUserData(PreparedStatement st, String query)
	{
		try 
		{
			System.out.println("Enter the Person ID of who you want to search for: ");
			Scanner keyboard = new Scanner(System.in);
			int id = keyboard.nextInt();
			ResultSet rs = st.executeQuery(query);
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
	
	
	private static void insertData(PreparedStatement st, String insert, Connection conn) 
	{
		/* Passed a prepared statement object, an String object holding the SQL insert statement */
		/* and a Connection object in order to initialize the prepared statement object and append data to the DB */
		try 
		{
			/* */
			st = conn.prepareStatement(insert);
			st.setInt(1,06);
			st.setString(2,"Payne");
			st.setString(3,"Kalli");
			st.setString(4,"kalliPayne@gmail.com");
			st.setString(5, "978855418");
			st.setString(6,"943 Lakedale Way");
			st.setString(7,"Sunnyvale");
			st.setString(8,"94089");			
			st.execute();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}

		System.out.println("Successfully executed update of database using the SQL declaration: \n" + insert);
		
	}
	
	private static void removeData(PreparedStatement st, Connection conn)
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
		String selectFrom = "SELECT * FROM person";

		String deletion = "DELETE FROM person WHERE PersonID = ?";
		Scanner keyboard = new Scanner(System.in);
		try
		{
			System.out.println("Enter the PersonID of the user you'd like to delete.");
			viewAllData(st, selectFrom);	//View database before deletion
			System.out.println(": ");
			int id = keyboard.nextInt();
			
			/* PreparedStatement st deleting */
			st = conn.prepareStatement(deletion);
			st.setInt(1, id);
			st.executeUpdate();
			
			viewAllData(st,selectFrom);		//View database after deletion
			keyboard.close();
			
			
		}
		catch(SQLException e) 
		{
			e.printStackTrace();
		}
	}


}
