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

        String[] dbTableDatas = new String[]{
            "CakeID",
            "CakeName",
            "CakePrice",
            "CakeShape",
            "CakeSize",
            "UserID",
            "CakeID",
            "Quantity"
        };

        serviceLoadData.loadDataTableCustomeCake(dbTableDatas, user.getUserId(), defaultTableModel);

        formViewCheckOut.getjTableCake().getColumnModel().getColumn(0).setMinWidth(0);
        formViewCheckOut.getjTableCake().getColumnModel().getColumn(0).setMaxWidth(0);
        formViewCheckOut.getjTableCake().getColumnModel().getColumn(5).setMinWidth(0);
        formViewCheckOut.getjTableCake().getColumnModel().getColumn(5).setMaxWidth(0);
        formViewCheckOut.getjTableCake().getColumnModel().getColumn(6).setMinWidth(0);
        formViewCheckOut.getjTableCake().getColumnModel().getColumn(6).setMaxWidth(0);

        formViewCheckOut.getjButtonCheckOut().addActionListener(e -> performCheckOut(formViewCheckOut, defaultTableModel, user));
        formViewCheckOut.getjButtonBack().addActionListener(e -> doMainMenu(formViewCheckOut, user));
    }

    private void performCheckOut(FormViewCheckOut formViewCheckOut, DefaultTableModel defaultTableModel, User user) {
        ServiceData serviceData = new ServiceData();
        String id = "T" + Integer.toString(ThreadLocalRandom.current().nextInt(1000, 9999));
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-mm-dd");
        LocalDateTime now = LocalDateTime.now();
        String[] dbValues = new String[]{
            id,
            user.getUserId(),
            dtf.format(now),
            defaultTableModel.getValueAt(formViewCheckOut.getjTableCake().getSelectedRow(), 0).toString()
        };
        String result = serviceData.saveTransactionHeader(dbValues);
        switch (result) {
            case "COMPLETE":
                JOptionPane.showMessageDialog(null, "Succesfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                break;
            default:
                JOptionPane.showMessageDialog(null, "Error", "Error", JOptionPane.ERROR_MESSAGE);
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

}
