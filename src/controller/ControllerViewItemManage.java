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
import view.FormViewAllMenu;
import view.FormViewCheckOut;
import view.FormViewItemManage;

/**
 *
 * @author Khanza
 */
class ControllerViewItemManage {

    void initController(FormViewItemManage formViewItemManage, User user) {

        ServiceLoadData serviceLoadData = new ServiceLoadData();
        DefaultTableModel defaultTableModel = new DefaultTableModel();
        formViewItemManage.getjTableCake().setModel(defaultTableModel);

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
        formViewItemManage.getjTableCake().getColumnModel().getColumn(0).setMinWidth(0);
        formViewItemManage.getjTableCake().getColumnModel().getColumn(0).setMaxWidth(0);
        formViewItemManage.getjTableCake().getColumnModel().getColumn(5).setMinWidth(0);
        formViewItemManage.getjTableCake().getColumnModel().getColumn(5).setMaxWidth(0);
        formViewItemManage.getjTableCake().getColumnModel().getColumn(6).setMinWidth(0);
        formViewItemManage.getjTableCake().getColumnModel().getColumn(6).setMaxWidth(0);

        formViewItemManage.getjButtonRemove().addActionListener(e -> performRemove(formViewItemManage, defaultTableModel));
        formViewItemManage.getjButtonUpdate().addActionListener(e -> performUpdate(formViewItemManage, defaultTableModel));
        formViewItemManage.getjButtonCheckOut().addActionListener(e -> doCheckOut(formViewItemManage, user, defaultTableModel));
        formViewItemManage.getjButtonView().addActionListener(e -> doViewAllItem(formViewItemManage, user));
        formViewItemManage.getjButtonBack().addActionListener(e -> doBack(formViewItemManage, user));
    }

    private void performRemove(FormViewItemManage formViewItemManage, DefaultTableModel defaultTableModel) {
        ServiceData serviceData = new ServiceData();
        String result = serviceData.deleteDatas("cart", "CakeId", defaultTableModel.getValueAt(formViewItemManage.getjTableCake().getSelectedRow(), 6).toString());
        switch (result) {
            case "COMPLETE":
                JOptionPane.showMessageDialog(null, "Succesfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                break;
            default:
                JOptionPane.showMessageDialog(null, "Error", "Error", JOptionPane.ERROR_MESSAGE);
                break;
        }
    }

    private void performUpdate(FormViewItemManage formViewItemManage,
            DefaultTableModel defaultTableModel
    ) {
        String[] dbDatas = new String[]{
            defaultTableModel.getValueAt(formViewItemManage.getjTableCake().getSelectedRow(), 5).toString(),
            defaultTableModel.getValueAt(formViewItemManage.getjTableCake().getSelectedRow(), 6).toString(),
            formViewItemManage.getjSpinnerQuantity().getValue().toString()
        };
        ServiceData serviceData = new ServiceData();
        String[] dbTableDatas = new String[]{
            "UserID",
            "CakeID",
            "Quantity"
        };
        serviceData.editDatas("cart", dbTableDatas, dbDatas, 1);
    }

    private void doCheckOut(FormViewItemManage formViewItemManage, User user, DefaultTableModel defaultTableModel) {
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
        String result = serviceData.saveTransaction(dbValues);
        switch (result) {
            case "COMPLETE":
                JOptionPane.showMessageDialog(null, "Succesfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                formViewItemManage.setVisible(false);
                FormViewCheckOut formViewCheckOut = new FormViewCheckOut();
                formViewCheckOut.setVisible(true);
                ControllerCheckOut controllerCheckOut = new ControllerCheckOut();
                controllerCheckOut.initController(formViewCheckOut, user);
                break;
            default:
                JOptionPane.showMessageDialog(null, result, "Error", JOptionPane.ERROR_MESSAGE);
                break;
        }
    }

    private void doViewAllItem(FormViewItemManage formViewItemManage, User user) {
        formViewItemManage.setVisible(false);
        FormViewAllMenu formViewAllMenu = new FormViewAllMenu();
        formViewAllMenu.setVisible(true);
        ControllerViewAllMenu controllerViewAllMenu = new ControllerViewAllMenu();
        controllerViewAllMenu.initController(formViewAllMenu, user);
    }

    private void doBack(FormViewItemManage formViewItemManage, User user) {
        formViewItemManage.setVisible(false);
        FormMainMenu formMainMenu = new FormMainMenu(user);
        formMainMenu.setVisible(true);
        ControllerMainMenu controllerMainMenu = new ControllerMainMenu();
        controllerMainMenu.initController(formMainMenu, user);
    }

}
