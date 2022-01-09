package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.swing.JOptionPane;
import main.Conn;

public class ServiceRegister {

    private final Connection connection;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
    private String sql;

    public ServiceRegister() {
        connection = Conn.getConnection();
    }

    public String saveRegister(
            String id,
            String userName,
            String email,
            String gender,
            String year,
            String month,
            String day,
            String phoneNumber,
            String address,
            String password,
            String confirmPassword
    ) {
        if (userName.isEmpty()
                || email.isEmpty()
                || gender.isEmpty()
                || year.isEmpty()
                || month.isEmpty()
                || day.isEmpty()
                || phoneNumber.isEmpty()
                || address.isEmpty()
                || password.isEmpty()
                || confirmPassword.isEmpty()) {
            return "EMPTY";
        } else {
            if (!password.equals(confirmPassword) || !checkPassword(password)) {
                return "WRONG";
            } else if (checkExistRegister(userName) == false) {
                return "EXIST";
            } else if (checkUserName(userName) == false) {
                return "LENGTHUSERNAME";
            } else if (checkEmail(email) == false) {
                return "LENGTHEMAIL";
            } else if (checkPhoneNumber(phoneNumber) == false) {
                return "LENGTHHP";
            } else if (checkDate(year + "-" + month + "-" + day) == false) {
                return "DATE";
            } else if (checkAddress(address) == false) {
                return "ADDRESS";
            } else {
                try {
                    sql = "INSERT INTO user VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, id);
                    preparedStatement.setString(2, userName);
                    preparedStatement.setString(3, email);
                    preparedStatement.setString(4, password);
                    preparedStatement.setString(5, gender);
                    preparedStatement.setString(6, year + "-" + month + "-" + day);
                    preparedStatement.setString(7, phoneNumber);
                    preparedStatement.setString(8, address);
                    preparedStatement.setString(9, "User");
                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                    return "COMPLETE";
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                    return e.getMessage();
                }
            }
        }
    }

    public Boolean checkExistRegister(String userName) {
        boolean usernameCheck;
        try {
            sql = "SELECT * FROM user";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery(sql);
            while (resultSet.next()) {
                if (resultSet.getString(3).equals(userName)) {
                    usernameCheck = false;
                    return usernameCheck;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        usernameCheck = true;
        return usernameCheck;
    }

    public Boolean checkUserName(String userName) {
        return !(userName.length() < 5 || userName.length() > 15);
    }

    public Boolean checkPhoneNumber(String hp) {
        return !(hp.length() < 10 || hp.length() > 12);
    }

    private Boolean checkEmail(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    private boolean checkAddress(String address) {
        return ("Street".equals(address.substring(address.lastIndexOf(" ") + 1)));
    }

    public boolean checkDate(String date) {
        String DATE_FORMAT = "yyyy-mm-dd";
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public Boolean checkPassword(String password) {
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=\\S+$).{8,20}$";
        Pattern p = Pattern.compile(regex);
        if (password == null) {
            return false;
        }
        Matcher m = p.matcher(password);
        return m.matches();
    }

//    public static void main(String[] args) {
//        ServiceRegister serviceRegister = new ServiceRegister();
//        serviceRegister.saveRegister("U1234", "asaaa", "as@gmail.com", "as", "20211", "121", "121", "1298347654", "12 srg Street", "Sh1k4m4ru", "Sh1k4m4ru");
//    }
}
