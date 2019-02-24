package db;

/*
 * This object represents a row in the MySQL person table.
 * We can simultae an object like this using 
 * PreparedStatement st = conn.preparedStatement(query);
 * ResultSet rs = st.executeQuery();
 */
public class Person 
{
	private String m_FirstName, m_LastName, m_Email, m_PhoneNumber, m_Address, m_City, m_ZipCode;
	private int m_PersonID;
	
	public Person() 
	{
		m_PersonID = (Integer) null;
		m_FirstName = null;
		m_LastName = null;
		m_Email = null;
		m_PhoneNumber = null;
		m_City = null;
		m_ZipCode = null;
	}
	
	public Person(int PersonID, String FirstName, String LastName, String Email, String PhoneNumber, String Address, String City, String ZipCode)
	{
		m_PersonID = PersonID;
		m_FirstName = FirstName;
		m_LastName = LastName;
		m_Email = Email;
		m_PhoneNumber = PhoneNumber;
		m_Address = Address;
		m_City = City;
		m_ZipCode = ZipCode;
	}
	
	public int getPersonID()
	{
		return m_PersonID;
	}
	public String getFirstName()
	{
		return m_FirstName;
	}
	public String getLastName()
	{
		return m_LastName;
	}
	public String getEmail()
	{
		return m_Email;
	}
	public String getPhoneNumber()
	{
		return m_PhoneNumber;
	}
	public String getAddress()
	{
		return m_Address;
	}
	public String getCity()
	{
		return m_City;
	}
	public String getZipCode()
	{
		return m_ZipCode;
	}
	

}
