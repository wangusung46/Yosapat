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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

    Boolean clickTable = false;

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
        formViewAllMenu.getjButtonAdd().addActionListener(e -> performAdd(formViewAllMenu, user, defaultTableModel, serviceLoadData));
        formViewAllMenu.getjButtonView().addActionListener(e -> doViewManage(formViewAllMenu, user));
        formViewAllMenu.getjButtonBack().addActionListener(e -> doMainMenu(formViewAllMenu, user));

        formViewAllMenu.getjTableCake().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                jTableCakeMouseClicked();
            }

            private void jTableCakeMouseClicked() {
                clickTable = true;
            }
        });
    }

    private void performAdd(FormViewAllMenu formViewAllMenu, User user, DefaultTableModel defaultTableModel, ServiceLoadData serviceLoadData) {
        if (clickTable) {
            ServiceData serviceData = new ServiceData();
            if (serviceData.checkCostume(defaultTableModel.getValueAt(formViewAllMenu.getjTableCake().getSelectedRow(), 0).toString(), user.getUserId())) {
                String[] dbDatas = new String[]{
                    user.getUserId(),
                    defaultTableModel.getValueAt(formViewAllMenu.getjTableCake().getSelectedRow(), 0).toString(),
                    formViewAllMenu.getjSpinnerQuantity().getValue().toString()
                };
                String result = serviceData.saveDatas("cart", dbDatas);
                switch (result) {
                    case "COMPLETE":
                        loadTable(serviceLoadData, user, defaultTableModel);
                        JOptionPane.showMessageDialog(null, "Cake Succesfully add to Cart!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, result, "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                }
            } else {
                JOptionPane.showMessageDialog(null, "This Cake is Allready in your cart! if you want to change the quantity, do it on the update page!", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(null, "Select Cake to add", "Error", JOptionPane.ERROR_MESSAGE);
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
    
    private void loadTable(ServiceLoadData serviceLoadData, User user, DefaultTableModel defaultTableModel) {
        String[] dbTableDatas = new String[]{
            "CakeID",
            "CakeName",
            "CakePrice",
            "CakeShape",
            "CakeSize",
        };

        serviceLoadData.loadDataTable("cake", dbTableDatas, defaultTableModel);
    }

}
