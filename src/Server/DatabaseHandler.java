package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DatabaseHandler extends Configs{
    Connection dbConnection;
    public DatabaseHandler(){};

    public Connection getDbConnection() throws ClassNotFoundException, SQLException
    {
        String connectionString="jdbc:mysql://localhost:3306/airport"+
                "?verifyServerCertificate=false"+
                "&useSSL=false"+
                "&requireSSL=false"+
                "&useLegacyDatetimeCode=false"+
                "&amp"+
                "&serverTimezone=UTC";
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString,
                dbUser, dbPass);
        return dbConnection;
    }

    public void close(ResultSet rs) {
        if (rs != null) {
            try
            {
                rs.close();
            }
            catch (Exception e3)
            {
                e3.printStackTrace();
            }
        }
    }
}
