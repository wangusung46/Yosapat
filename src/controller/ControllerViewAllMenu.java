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

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.User;
import service.ServiceData;
import service.ServiceLoadData;
import view.FormMainMenu;
import view.FormViewAllMenu;
import view.FormViewItemManage;

/**
 *
 * @author Khanza
 */
class ControllerViewAllMenu {

    void initController(FormViewAllMenu formViewAllMenu, User user) {
        ServiceLoadData serviceLoadData = new ServiceLoadData();
        String dbTableName = "cake";
        String[] dbTableDatas = new String[]{
            "CakeID",
            "CakeName",
            "CakePrice",
            "CakeShape",
            "CakeSize",};
        DefaultTableModel defaultTableModel = new DefaultTableModel();
        formViewAllMenu.getjTableCake().setModel(defaultTableModel);
        defaultTableModel.addColumn("ID");
        defaultTableModel.addColumn("Cake Name");
        defaultTableModel.addColumn("Cake Price");
        defaultTableModel.addColumn("Cake Shape");
        defaultTableModel.addColumn("Cake Size");
        serviceLoadData.loadDataTable(dbTableName, dbTableDatas, defaultTableModel);
        formViewAllMenu.getjTableCake().getColumnModel().getColumn(0).setMinWidth(0);
        formViewAllMenu.getjTableCake().getColumnModel().getColumn(0).setMaxWidth(0);
        formViewAllMenu.getjButtonAdd().addActionListener(e -> performAdd(formViewAllMenu, user, defaultTableModel));
        formViewAllMenu.getjButtonView().addActionListener(e -> doViewManage(formViewAllMenu, user));
        formViewAllMenu.getjButtonBack().addActionListener(e -> doMainMenu(formViewAllMenu, user));
    }

    private void performAdd(FormViewAllMenu formViewAllMenu, User user, DefaultTableModel defaultTableModel) {
        ServiceData serviceData = new ServiceData();
        String[] dbDatas = new String[]{
            user.getUserId(),
            defaultTableModel.getValueAt(formViewAllMenu.getjTableCake().getSelectedRow(), 0).toString(),
            formViewAllMenu.getjSpinnerQuantity().getValue().toString()
        };
        String result = serviceData.saveDatas("cart", dbDatas );
        switch (result) {
            case "COMPLETE":
                JOptionPane.showMessageDialog(null, "Succesfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                break;
            default:
                JOptionPane.showMessageDialog(null, "Error", "Error", JOptionPane.ERROR_MESSAGE);
                break;
        }
    }

    private void doMainMenu(FormViewAllMenu formViewAllMenu, User user) {
        formViewAllMenu.setVisible(false);
        FormMainMenu formMainMenu = new FormMainMenu(user);
        formMainMenu.setVisible(true);
        ControllerMainMenu controllerMainMenu = new ControllerMainMenu();
        controllerMainMenu.initController(formMainMenu, user);
    }

    private void doViewManage(FormViewAllMenu formViewAllMenu, User user) {
        formViewAllMenu.setVisible(false);
        FormViewItemManage formViewItemManage = new FormViewItemManage();
        formViewItemManage.setVisible(true);
        ControllerViewItemManage controllerMainMenu = new ControllerViewItemManage();
        controllerMainMenu.initController(formViewItemManage, user);
    }

}
