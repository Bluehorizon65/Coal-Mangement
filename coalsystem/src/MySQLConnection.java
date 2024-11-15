import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/coal_management";
        String user = "root";
        String password = "password";
        return DriverManager.getConnection(url, user, password);
    }
}
