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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.User;
import service.ServiceData;
import service.ServiceLoadData;
import view.FormMainMenu;
import view.FormViewManageMenu;

/**
 *
 * @author Khanza
 */
class ControllerCake {

    Boolean clickTable = false;

    void initController(FormViewManageMenu formViewManageMenu, User user) {
        ServiceLoadData serviceLoadData = new ServiceLoadData();

        DefaultTableModel defaultTableModel = new DefaultTableModel();
        formViewManageMenu.getjTableCake().setModel(defaultTableModel);

        defaultTableModel.addColumn("Cake ID");
        defaultTableModel.addColumn("Cake Name");
        defaultTableModel.addColumn("Cake Price");
        defaultTableModel.addColumn("Cake Shape");
        defaultTableModel.addColumn("Cake Size");

        loadTable(serviceLoadData, user, defaultTableModel);

        formViewManageMenu.getjComboBoxOvalSize().setEnabled(true);
        formViewManageMenu.getjComboBoxRectangleSize().setEnabled(false);
        formViewManageMenu.getjComboBoxShape().setModel(
                new DefaultComboBoxModel<>(
                        new String[]{
                            "Oval",
                            "Rectangle"
                        }
                )
        );
        formViewManageMenu.getjComboBoxOvalSize().setModel(
                new DefaultComboBoxModel<>(
                        new String[]{
                            "15 cm",
                            "20 cm",
                            "25 cm"
                        }
                )
        );
        formViewManageMenu.getjComboBoxRectangleSize().setModel(
                new DefaultComboBoxModel<>(
                        new String[]{
                            "10 x 10 cm",
                            "20 x 20 cm",
                            "30 x 30 cm"
                        }
                )
        );
        formViewManageMenu.getjButtonBack().addActionListener(e -> doBack(formViewManageMenu, user));
        formViewManageMenu.getjButtonAdd().addActionListener(e -> performAdd(formViewManageMenu, user, serviceLoadData, defaultTableModel));
        formViewManageMenu.getjButtonRemove().addActionListener(e -> performRemove(formViewManageMenu, user, defaultTableModel, serviceLoadData));
        formViewManageMenu.getjComboBoxShape().addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxShapeActionPerformed();
            }

            private void jComboBoxShapeActionPerformed() {
                if (formViewManageMenu.getjComboBoxShape().getSelectedItem().toString().equals("Oval")) {
                    formViewManageMenu.getjComboBoxOvalSize().setEnabled(true);
                    formViewManageMenu.getjComboBoxRectangleSize().setEnabled(false);
                } else {
                    formViewManageMenu.getjComboBoxOvalSize().setEnabled(false);
                    formViewManageMenu.getjComboBoxRectangleSize().setEnabled(true);
                }
            }
        });
        formViewManageMenu.getjTableCake().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                jTableCakeMouseClicked();
            }

            private void jTableCakeMouseClicked() {
                clickTable = true;
            }
        });
        formViewManageMenu.getjTextFieldCakePrice().addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                numberValidation(e);
            }
        });
    }

    private void doBack(FormViewManageMenu formViewManageMenu, User user) {
        formViewManageMenu.setVisible(false);
        FormMainMenu formMainMenu = new FormMainMenu(user);
        formMainMenu.setVisible(true);
        ControllerMainMenu controllerMainMenu = new ControllerMainMenu();
        controllerMainMenu.initController(formMainMenu, user);
    }

    private void performAdd(FormViewManageMenu formViewManageMenu, User user, ServiceLoadData serviceLoadData, DefaultTableModel defaultTableModel) {
        ServiceData serviceData = new ServiceData();

        String size = new String();

        if (formViewManageMenu.getjComboBoxShape().getSelectedIndex() == 0) {
            size = formViewManageMenu.getjComboBoxOvalSize().getSelectedItem().toString();
        } else {
            size = formViewManageMenu.getjComboBoxRectangleSize().getSelectedItem().toString();
        }

        String id = generateid(formViewManageMenu);
        String[] dbDatas = new String[]{
            id,
            formViewManageMenu.getjTextFieldCakeName().getText(),
            formViewManageMenu.getjTextFieldCakePrice().getText(),
            size,
            formViewManageMenu.getjComboBoxShape().getSelectedItem().toString()
        };
        if (serviceData.checkEmptyDbDatas(dbDatas)) {
            if ("Cake".equals(formViewManageMenu.getjTextFieldCakeName().getText().substring(formViewManageMenu.getjTextFieldCakeName().getText().lastIndexOf(" ") + 1))) {
                if (Integer.parseInt(formViewManageMenu.getjTextFieldCakePrice().getText()) >= 100000 && Integer.parseInt(formViewManageMenu.getjTextFieldCakePrice().getText()) <= 500000) {
                    String result = serviceData.saveDatas("cake", dbDatas);
                    switch (result) {
                        case "COMPLETE":
                            loadTable(serviceLoadData, user, defaultTableModel);
                            JOptionPane.showMessageDialog(null, "Cake Succesfully Inputted to Database!", "Success", JOptionPane.INFORMATION_MESSAGE);
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, result, "Error", JOptionPane.ERROR_MESSAGE);
                            break;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Cake Price must between 100000 - 500000!", "Success", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Cake Name must ends with ‘Cake’!", "Success", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Field Empty!", "Success", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void performRemove(FormViewManageMenu formViewManageMenu, User user, DefaultTableModel defaultTableModel, ServiceLoadData serviceLoadData) {
        ServiceData serviceData = new ServiceData();
        if (clickTable) {
            String result = serviceData.deleteDatas("cake", "CakeId", defaultTableModel.getValueAt(formViewManageMenu.getjTableCake().getSelectedRow(), 0).toString());
            switch (result) {
                case "COMPLETE":
                    loadTable(serviceLoadData, user, defaultTableModel);
                    JOptionPane.showMessageDialog(null, "Cake Succesfully Remove!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, result, "Error", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Select Cake You Wan to Remove!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadTable(ServiceLoadData serviceLoadData, User user, DefaultTableModel defaultTableModel) {
        String dbTableName = "cake";
        String[] dbTableDatas = new String[]{
            "CakeID",
            "CakeName",
            "CakePrice",
            "CakeShape",
            "CakeSize",};
        serviceLoadData.loadDataTable(dbTableName, dbTableDatas, defaultTableModel);
    }

    private String generateid(FormViewManageMenu formViewManageMenu) {
        String shape = new String();
        String size = new String();
        if (formViewManageMenu.getjComboBoxShape().getSelectedIndex() == 0) {
            shape = "O";
            switch (formViewManageMenu.getjComboBoxOvalSize().getSelectedIndex()) {
                case 0:
                    size = "F";
                    break;
                case 1:
                    size = "N";
                    break;
                case 2:
                    size = "V";
                    break;
            }
        } else {
            shape = "R";
            switch (formViewManageMenu.getjComboBoxRectangleSize().getSelectedIndex()) {
                case 0:
                    size = "T";
                    break;
                case 1:
                    size = "W";
                    break;
                case 2:
                    size = "H";
                    break;
            }
        }
        String id = "C" + shape + size + Integer.toString(ThreadLocalRandom.current().nextInt(1000, 9999));
        return id;
    }

    private void numberValidation(KeyEvent e) {
        char c = e.getKeyChar();
        if (!((c >= '0') && (c <= '9')
                || (c == KeyEvent.VK_BACK_SPACE)
                || (c == KeyEvent.VK_DELETE))) {
            e.consume();
        }
    }

}
