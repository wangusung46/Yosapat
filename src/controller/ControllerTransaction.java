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
import javax.swing.table.DefaultTableModel;
import model.User;
import service.ServiceLoadData;
import view.FormMainMenu;
import view.FormViewItemTransaction;

/**
 *
 * @author Khanza
 */
public class ControllerTransaction {

    void initController(FormViewItemTransaction formViewItemTransaction, User user) {
        ServiceLoadData serviceLoadData = new ServiceLoadData();
        DefaultTableModel defaultTableModel = new DefaultTableModel();
        DefaultTableModel defaultTableModel2 = new DefaultTableModel();

        formViewItemTransaction.getjTableHeader().setModel(defaultTableModel);

        defaultTableModel.addColumn("Transaction ID");
        defaultTableModel.addColumn("");
        defaultTableModel.addColumn("Transaction Date");
        defaultTableModel.addColumn("Pick Up Date");

        loadTable(serviceLoadData, user, defaultTableModel);

        formViewItemTransaction.getjTableHeader().getColumnModel().getColumn(1).setMinWidth(0);
        formViewItemTransaction.getjTableHeader().getColumnModel().getColumn(1).setMaxWidth(0);

        formViewItemTransaction.getjTableCake().setModel(defaultTableModel2);

        defaultTableModel2.addColumn("Cake Name");
        defaultTableModel2.addColumn("Cake Size");
        defaultTableModel2.addColumn("Cake Shape");
        defaultTableModel2.addColumn("Cake Price");
        defaultTableModel2.addColumn("Quantity");
        defaultTableModel2.addColumn("Sub Total");

        formViewItemTransaction.getjTextFieldSelectedId().setEditable(false);
        formViewItemTransaction.getjTextFieldTotal().setEditable(false);
        formViewItemTransaction.getjButtonBack().addActionListener(e -> doBack(formViewItemTransaction, user));
        formViewItemTransaction.getjTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                jTableCakeMouseClicked();
            }

            private void jTableCakeMouseClicked() {
                loadTable2(formViewItemTransaction, serviceLoadData, user, defaultTableModel, defaultTableModel2);
                Integer price = 0;
                Integer count = 0;
                Integer subTotal = 0;
                Integer total = 0;
                for (int i = 0; i < formViewItemTransaction.getjTableCake().getRowCount(); i++) {
                    price = Integer.parseInt(formViewItemTransaction.getjTableCake().getValueAt(i, 3).toString());
                    count = Integer.parseInt(formViewItemTransaction.getjTableCake().getValueAt(i, 4).toString());
                    subTotal = price * count;
                    total = total + subTotal;
                }
                formViewItemTransaction.getjTextFieldSelectedId().setText(defaultTableModel.getValueAt(formViewItemTransaction.getjTableHeader().getSelectedRow(), 0).toString());
                formViewItemTransaction.getjTextFieldTotal().setText("Rp." + total.toString());
            }
        });
    }

    private void doBack(FormViewItemTransaction formViewItemTransaction, User user) {
        formViewItemTransaction.setVisible(false);
        FormMainMenu formMainMenu = new FormMainMenu(user);
        formMainMenu.setVisible(true);
        ControllerMainMenu controllerMainMenu = new ControllerMainMenu();
        controllerMainMenu.initController(formMainMenu, user);
    }

    private void loadTable(ServiceLoadData serviceLoadData, User user, DefaultTableModel defaultTableModel) {
        String[] dbTableDatas = new String[]{
            "TransactionID",
            "UserID",
            "TransactionDate",
            "PickUpDate"
        };

        serviceLoadData.loadDataTable("transactionheader", dbTableDatas, defaultTableModel);
    }

    private void loadTable2(FormViewItemTransaction formViewItemTransaction, ServiceLoadData serviceLoadData, User user, DefaultTableModel defaultTableModel, DefaultTableModel defaultTableModel2) {
        String[] dbTableDatas = new String[]{
            "cake.CakeName",
            "cake.CakeSize",
            "cake.CakeShape",
            "cake.CakePrice",
            "transactiondetail.Quantity",
            "SUB"
        };
        serviceLoadData.loadDataTableCustomeCakeSubTotal(dbTableDatas, defaultTableModel.getValueAt(formViewItemTransaction.getjTableHeader().getSelectedRow(), 0).toString(), user.getUserId(), defaultTableModel2);
    }
}
