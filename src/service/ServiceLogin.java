package service;

import main.Conn;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.User;

public class ServiceLogin {

    private final Connection connection;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
    private String sql;

    public ServiceLogin() {
        connection = Conn.getConnection();
    }

    public String login(String userName, String password, User user) {
        if (userName.isEmpty() || password.isEmpty()) {
            return "EMPTY";
        } else {
            try {
                sql = "SELECT * FROM user WHERE Username = ? AND UserPassword = ?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, userName);
                preparedStatement.setString(2, password);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    user.setUserId(resultSet.getString(1));
                    user.setUsername(resultSet.getString(2));
                    user.setUserEmail(resultSet.getString(3));
                    user.setUserPassword(resultSet.getString(4));
                    user.setUserGender(resultSet.getString(5));
                    user.setUserDOB(resultSet.getDate(6));
                    user.setUserPhoneNumber(resultSet.getString(7));
                    user.setUserAddress(resultSet.getString(8));
                    user.setUserRole(resultSet.getString(9));
                    return "COMPLETE";
                } else {
                    return "INCOMPLETE";
                }
            } catch (SQLException e) {
                return e.getMessage();
            }
        }
    }
}
