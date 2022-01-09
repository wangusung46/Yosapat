package service;

import main.Conn;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JComboBox;

public class ServiceData {

    private final Connection connection; // Mendeklarasikan Class Connection bawaan java untuk keperluan query
    private ResultSet resultSet; // Mendeklarasikan Class ResultSet bawaan java untuk keperluan query
    private PreparedStatement preparedStatement; // Mendeklarasikan Class PreparedStatement bawaan java untuk keperluan query
    private String sql; // Mendeklarasikan Class String Untuk Keperluan Query

    public ServiceData() { // Membuat constructor yang merupakan keunggulan dari java OOP
        connection = Conn.getConnection(); // Memanggil class connection
    }

    public String loadId() {
        return null;
    }

    public String checkDatas(String[] dbDatas) {
        for (String dbData : dbDatas) {
            System.out.println(dbData);
            if (dbDatas[0].isEmpty()) {
                break;
            } else if (dbData.isEmpty()) { // fungsi untuk agar data tidak boleh kosong
                return "LENGKAPI_DATA";
            }
        }
        return "LENGKAP";
    }

    public String saveDatas(String dbTableName, String[] dbDatas) {
        try {
            sql = "INSERT INTO " + dbTableName + " VALUES (" + sumSaveDb(dbDatas) + ")";
            preparedStatement = connection.prepareStatement(sql);
            Integer i = 1;
//            dbDatas[0] = null;
            for (String dbData : dbDatas) {
                preparedStatement.setString(i, dbData);
                i++;
            }
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return "COMPLETE";
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }

    public Boolean checkEmptyDbDatas(String[] dbDatas) {
        for (int i = 0; i <= dbDatas.length - 1; i++) {
            if (dbDatas[i].isEmpty()) {
                System.out.println(dbDatas[i]);
                return false;
            }
            System.out.println(dbDatas[i]);
        }
        return true;
    }

    public Boolean checkCostume(String cake, String user) {
        boolean result;
        try {
            sql = "SELECT * FROM cart WHERE UserID = '" + user + "'";
            System.out.println(sql);
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery(sql);
            while (resultSet.next()) {
                if (resultSet.getString(2).equals(cake)) {
                    result = false;
                    return result;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        result = true;
        return result;
    }

    public String deleteDatas(String dbTableName, String dbKey, String dbValue) {
        try {
            sql = "DELETE FROM " + dbTableName + " WHERE " + dbKey + " = '" + dbValue + "'";
            System.out.println(sql);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return "COMPLETE";
        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    public String editDatas(String dbTableName, String[] dbTableDatas, String[] dbDatas, Integer edit) {

        try {
            sql = "UPDATE " + dbTableName + " SET " + sumEditDb(dbTableDatas) + " WHERE " + dbTableDatas[edit] + " = '" + dbDatas[edit] + "'";
            System.out.println(sql);
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 1; i < dbDatas.length; i++) {
                preparedStatement.setString(i, dbDatas[i]);
            }
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return "COMPLETE";
        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    public void showCombo(JComboBox<String> comboBox, String dbTableName, String id, String relationTable) {
        try {
            sql = "SELECT * FROM " + dbTableName + " ORDER by " + id + "";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery(sql);
            while (resultSet.next()) {
                comboBox.addItem(resultSet.getString(id) + " - " + resultSet.getString(relationTable));
            }
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public String sumSaveDb(String[] dbDatas) {
        String result = "?";
        for (int i = 1; i < dbDatas.length; i++) {
            result = result.concat(", ?");
        }

        return result;
    }

    public String sumEditDb(String[] dbDatas) {
        String result = "";
        for (int i = 1; i < dbDatas.length; i++) {
            if (i < dbDatas.length - 1) {
                result = result.concat(dbDatas[i].concat(" = ?, "));
            } else {
                result = result.concat(dbDatas[i].concat(" = ? "));
            }
        }
        return result;
    }

    public String transaction(String dbTableName, String[] dbTableDatas, String[] dbDatas) {
        try {
            sql = "INSERT INTO " + dbTableName + " VALUES (" + sumSaveDb(dbDatas) + ")";
            preparedStatement = connection.prepareStatement(sql);
            Integer i = 1;
            dbDatas[0] = null;
            for (String dbData : dbDatas) {
                preparedStatement.setString(i, dbData);
                i++;
            }
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return "BERHASIL";
        } catch (SQLException e) {
            return e.getMessage();
        }
    }

//    private String sumDbColumn(String[] dbColumn) {
//        String result = "";
//        for (int i = 0; i < dbColumn.length; i++) {
//            if(i < dbColumn.length - 1){
//                result = result.concat(dbColumn[i] + ", ");
//            } else {
//                result = result.concat(dbColumn[i] + " ");
//            }
//            
//        }
//        return result;
//    }
//    public static void main(String[] args) {
//        String[] dbTableDatas = new String[]{
//            "CakeID",
//            "CakeName",
//            "CakePrice"
//        };
//        ServiceData data = new ServiceData();
//    }
    public String saveTransactionDetail(String[] dbValues) {
        try {
            sql = "INSERT INTO transactiondetail VALUES (? , ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 1; i <= dbValues.length; i++) {
                System.out.println(dbValues[i - 1]);
                preparedStatement.setString(i, dbValues[i - 1]);
            }
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return "COMPLETE";
        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    public String saveTransactionHeader(String[] dbValues) {
        try {
            sql = "INSERT INTO transactionheader VALUES (? , ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 1; i <= dbValues.length; i++) {
                preparedStatement.setString(i, dbValues[i - 1]);
            }
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return "COMPLETE";
        } catch (SQLException e) {
            return e.getMessage();
        }
    }
}
