package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import main.Conn;

public class ServiceLoadData {

    private final Connection connection; // Mendeklarasikan Class Connection bawaan java untuk keperluan query
    private ResultSet resultSet; // Mendeklarasikan Class ResultSet bawaan java untuk keperluan query
    private PreparedStatement preparedStatement; // Mendeklarasikan Class PreparedStatement bawaan java untuk keperluan query
    private String sql; // Mendeklarasikan Class String Untuk Keperluan Query

    public ServiceLoadData() { // Membuat constructor yang merupakan keunggulan dari java OOP
        connection = Conn.getConnection(); // Memanggil class connection
    }

    public void loadDataTable(String dbTableName, String[] dbTableDatas, DefaultTableModel defaultTableModel) {
        defaultTableModel.getDataVector().removeAllElements();
        defaultTableModel.fireTableDataChanged();
        Object[] objects = new Object[dbTableDatas.length];
        try {
            sql = "SELECT * FROM " + dbTableName;
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery(sql);
            while (resultSet.next()) {
                Integer i = 0;
                for (String dbTableData : dbTableDatas) {
                    objects[i] = resultSet.getString(dbTableData);
                    i++;
                }
                defaultTableModel.addRow(objects);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.getMessage();
        }
    }
    
    public void loadDataTableCustomeCake(String[] dbTableRow, String whereValue, DefaultTableModel defaultTableModel) {
        defaultTableModel.getDataVector().removeAllElements();
        defaultTableModel.fireTableDataChanged();
        Object[] objects = new Object[dbTableRow.length];
        try {
            sql = "SELECT "
                    + "cake.CakeID, "
                    + "cake.CakeName, "
                    + "cake.CakeShape, "
                    + "cake.CakeSize, "
                    + "cake.CakePrice, "
                    + "cart.UserID, "
                    + "cart.CakeID, "
                    + "cart.Quantity "
                    + "FROM cake "
                    + "INNER JOIN cart "
                    + "ON cake.CakeID = cart.CakeID "
                    + "WHERE cart.UserID = '" + whereValue + "'";
            System.out.println(sql);
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery(sql);
            while (resultSet.next()) {
                Integer i = 0;
                for (String dbTableData : dbTableRow) {
                    objects[i] = resultSet.getString(dbTableData);
                    i++;
                }
                defaultTableModel.addRow(objects);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.getMessage();
        }
    }
//    public static void main(String[] args) {
//        ServiceLoadData data = new ServiceLoadData();
//        DefaultTableModel defaultTableModel = new DefaultTableModel();
//        String dbTableName = "cart";
//        String idKey = "UserID";
//        String[] dbTableDatas = new String[]{
//            "CakeID",
//            "CakeName",
//            "CakePrice",
//            "CakeShape",
//            "CakeSize",};
//    }
}
