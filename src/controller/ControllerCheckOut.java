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
package controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.User;
import service.ServiceData;
import service.ServiceLoadData;
import view.FormMainMenu;
import view.FormViewCheckOut;

/**
 *
 * @author Khanza
 */
class ControllerCheckOut {

    void initController(FormViewCheckOut formViewCheckOut, User user) {
        ServiceLoadData serviceLoadData = new ServiceLoadData();
        DefaultTableModel defaultTableModel = new DefaultTableModel();
        formViewCheckOut.getjTableCake().setModel(defaultTableModel);

        defaultTableModel.addColumn("");
        defaultTableModel.addColumn("Cake Name");
        defaultTableModel.addColumn("Cake Shape");
        defaultTableModel.addColumn("Cake Size");
        defaultTableModel.addColumn("Cake Price");
        defaultTableModel.addColumn("");
        defaultTableModel.addColumn("");
        defaultTableModel.addColumn("Quantity");
        
        loadTable(serviceLoadData, user, defaultTableModel);

        formViewCheckOut.getjTableCake().getColumnModel().getColumn(0).setMinWidth(0);
        formViewCheckOut.getjTableCake().getColumnModel().getColumn(0).setMaxWidth(0);
        formViewCheckOut.getjTableCake().getColumnModel().getColumn(5).setMinWidth(0);
        formViewCheckOut.getjTableCake().getColumnModel().getColumn(5).setMaxWidth(0);
        formViewCheckOut.getjTableCake().getColumnModel().getColumn(6).setMinWidth(0);
        formViewCheckOut.getjTableCake().getColumnModel().getColumn(6).setMaxWidth(0);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();

        formViewCheckOut.getjTextFieldPickUp().setText(dtf.format(now.plusDays(3)));
        Integer price = 0;
        Integer count = 0;
        Integer subTotal = 0;
        Integer total = 0;
        for (int i = 0; i < formViewCheckOut.getjTableCake().getRowCount(); i++) {
            price = Integer.parseInt(formViewCheckOut.getjTableCake().getValueAt(i, 4).toString());
            count = Integer.parseInt(formViewCheckOut.getjTableCake().getValueAt(i, 7).toString());
            subTotal = price * count;
            total = total + subTotal;
        }
        formViewCheckOut.getjTextFieldTotal().setText("Rp." + total.toString());
        formViewCheckOut.getjButtonCheckOut().addActionListener(e -> performCheckOut(formViewCheckOut, defaultTableModel, user, serviceLoadData));
        formViewCheckOut.getjButtonBack().addActionListener(e -> doMainMenu(formViewCheckOut, user));
    }

    private void performCheckOut(FormViewCheckOut formViewCheckOut, DefaultTableModel defaultTableModel, User user, ServiceLoadData serviceLoadData) {
        ServiceData serviceData = new ServiceData();
        String id = "T" + Integer.toString(ThreadLocalRandom.current().nextInt(1000, 9999));
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        String[] dbValues = new String[]{
            id,
            user.getUserId(),
            dtf.format(now),
            dtf.format(now.plusDays(3))
        };

        String result = serviceData.saveTransactionHeader(dbValues);
        switch (result) {
            case "COMPLETE":
                for (int i = 0; i < formViewCheckOut.getjTableCake().getRowCount(); i++) {
                    String[] dbValues2 = new String[]{
                        id,
                        formViewCheckOut.getjTableCake().getValueAt(i, 0).toString(),
                        formViewCheckOut.getjTableCake().getValueAt(i, 7).toString()
                    };
                    String result2 = serviceData.saveTransactionDetail(dbValues2);
                    switch (result2) {
                        case "COMPLETE":
                            if (i + 1 == formViewCheckOut.getjTableCake().getRowCount()) {
                                serviceData.deleteDatas("cart", "UserID", user.getUserId());
                                loadTable(serviceLoadData, user, defaultTableModel);
                                JOptionPane.showMessageDialog(null, "Transaction Successfully! Remember to Pickup! :)", "Success", JOptionPane.INFORMATION_MESSAGE);
                            }
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, result2, "Error", JOptionPane.ERROR_MESSAGE);
                            break;
                    }
                }
                break;
            default:
                JOptionPane.showMessageDialog(null, result, "Error", JOptionPane.ERROR_MESSAGE);
                break;
        }
    }

    private void doMainMenu(FormViewCheckOut formViewCheckOut, User user) {
        formViewCheckOut.setVisible(false);
        FormMainMenu formMainMenu = new FormMainMenu(user);
        formMainMenu.setVisible(true);
        ControllerMainMenu controllerMainMenu = new ControllerMainMenu();
        controllerMainMenu.initController(formMainMenu, user);
    }

    private void loadTable(ServiceLoadData serviceLoadData, User user, DefaultTableModel defaultTableModel) {
        String[] dbTableDatas = new String[]{
            "CakeID",
            "CakeName",
            "CakeShape",
            "CakeSize",
            "CakePrice",
            "UserID",
            "CakeID",
            "Quantity"
        };

        serviceLoadData.loadDataTableCustomeCake(dbTableDatas, user.getUserId(), defaultTableModel);
    }

}
