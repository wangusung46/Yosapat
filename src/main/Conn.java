package main;

import com.mysql.jdbc.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conn {

    private static Connection connection;

    public static Connection getConnection() {

        try {
            String url = "jdbc:mysql://localhost/cake";
            String user = "root";
            String password = "";

            DriverManager.registerDriver(new Driver());

            connection = DriverManager.getConnection(url, user, password);

        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, "Gagal memanggil Connection", "PT Riyad Kurniawan", JOptionPane.INFORMATION_MESSAGE);
        }

        return connection;
    }
}
