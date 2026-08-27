package vn.hcmute.webpr330479.connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBConnection{
	private static final String URL="jdbc:sqlserver://localhost:1433;instanceName=SQLEXPRESS;encrypt=true;trustServerCertificate=true;databaseName=ServletCRUDMVC";
	private static final String USERNAME="sa";
	private static final String PASSWORD="YOUR_SA_PASSWORD";
	public Connection getConnection() throws SQLException{
		try{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		}catch(ClassNotFoundException exception){
			throw new SQLException("Khong tim thay SQL Server JDBC Driver.", exception);
		}
		return DriverManager.getConnection(URL, USERNAME, PASSWORD);
	}
}