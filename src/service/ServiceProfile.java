/*
 * Copyright (c) 2022 Khanza.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Khanza - initial API and implementation and/or initial documentation
 */
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

/**
 *
 * @author Khanza
 */
public class ServiceProfile {

    private final Connection connection;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
    private String sql;

    public ServiceProfile() {
        connection = Conn.getConnection();
    }

    public String updateProfile(
            String id,
            String role,
            String userName,
            String email,
            String gender,
            String year,
            String month,
            String day,
            String phoneNumber,
            String address,
            String password,
            String confirmPassword,
            String oldPassword) {
        if (userName.isEmpty()
                || email.isEmpty()
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
                ServiceData serviceData = new ServiceData();
                String[] dbTableDatas = new String[]{
                    "UserID",
                    "Username",
                    "UserEmail",
                    "UserPassword",
                    "UserGender",
                    "UserDOB",
                    "UserPhoneNumber",
                    "UserAddress",
                    "UserRole"
                };
                String[] dbDatas = new String[]{
                    id,
                    userName,
                    email,
                    password,
                    gender,
                    year + "-" + month + "-" + day,
                    phoneNumber,
                    address,
                    role
                };
                serviceData.editDatas("user", dbTableDatas, dbDatas, 0);
                return "COMPLETE";
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

}
