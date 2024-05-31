package course.course.db.connection;
import course.course.Main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.*;

public class Connect {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static Connection connection ;
    public  static Object  connect() {
        // URL для підключення до локальної MySQL бази даних (XAMPP)
        String url = "jdbc:mysql://localhost:3306/barbershop";
        // Користувач і пароль
        String user = "root";
        String password = ""; // Порожній пароль
        try {
            // Завантаження драйвера JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Підключення до бази даних
            connection = DriverManager.getConnection(url, user, password);
            logger.info("Підключення до бази даних успішне!");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }
    public ResultSet findUser( String username) throws SQLException {
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM employee where 'login_name' = "+username;
        ResultSet resultSet = statement.executeQuery(sql);
        return resultSet;

    }
}

