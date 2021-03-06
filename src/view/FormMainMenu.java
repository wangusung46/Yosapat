/*
 * Copyright (c) 2022 Yosapat.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Yosapat - initial API and implementation and/or initial documentation
 */
package view;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import model.User;

/**
 *
 * @author Yosapat
 */
public class FormMainMenu extends javax.swing.JFrame {

    /**
     * Creates new form FormMainMenu
     */
    public FormMainMenu(User user) {
        initComponents();
    }

    public JMenu getjMenuCakeMenu() {
        return jMenuCakeMenu;
    }

    public JMenu getjMenuTransaction() {
        return jMenuTransaction;
    }

    public JLabel getjLabelTheme() {
        return jLabelTheme;
    }

    public JMenuItem getjMenuItemAllMenu() {
        return jMenuItemAllMenu;
    }

    public JMenuItem getjMenuItemHistory() {
        return jMenuItemHistory;
    }

    public JMenuItem getjMenuItemLogout() {
        return jMenuItemLogout;
    }

    public JMenuItem getjMenuItemManage() {
        return jMenuItemManage;
    }

    public JMenuItem getjMenuItemProfile() {
        return jMenuItemProfile;
    }

    public JMenuItem getjMenuIManageMenu() {
        return jMenuIManageMenu;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelName = new javax.swing.JLabel();
        jLabelTheme = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuManageAccount = new javax.swing.JMenu();
        jMenuItemProfile = new javax.swing.JMenuItem();
        jMenuItemLogout = new javax.swing.JMenuItem();
        jMenuTransaction = new javax.swing.JMenu();
        jMenuItemAllMenu = new javax.swing.JMenuItem();
        jMenuItemManage = new javax.swing.JMenuItem();
        jMenuItemHistory = new javax.swing.JMenuItem();
        jMenuCakeMenu = new javax.swing.JMenu();
        jMenuIManageMenu = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabelName.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        jLabelName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelName.setText("Welcome To CakeLAnd");

        jLabelTheme.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N

        jMenuManageAccount.setText("Manage Account");

        jMenuItemProfile.setText("Profile");
        jMenuManageAccount.add(jMenuItemProfile);

        jMenuItemLogout.setText("Logout");
        jMenuManageAccount.add(jMenuItemLogout);

        jMenuBar1.add(jMenuManageAccount);

        jMenuTransaction.setText("Transaction");

        jMenuItemAllMenu.setText("View All Menu");
        jMenuTransaction.add(jMenuItemAllMenu);

        jMenuItemManage.setText("Manage Cart");
        jMenuTransaction.add(jMenuItemManage);

        jMenuItemHistory.setText("View Transaction History");
        jMenuTransaction.add(jMenuItemHistory);

        jMenuBar1.add(jMenuTransaction);

        jMenuCakeMenu.setText("Cake Menu");

        jMenuIManageMenu.setText("Manage Menu");
        jMenuCakeMenu.add(jMenuIManageMenu);

        jMenuBar1.add(jMenuCakeMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelName, javax.swing.GroupLayout.DEFAULT_SIZE, 548, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelTheme)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(131, Short.MAX_VALUE)
                .addComponent(jLabelTheme)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelName)
                .addGap(124, 124, 124))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormMainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormMainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormMainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormMainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                User user = new User();
                new FormMainMenu(user).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabelName;
    private javax.swing.JLabel jLabelTheme;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuCakeMenu;
    private javax.swing.JMenuItem jMenuIManageMenu;
    private javax.swing.JMenuItem jMenuItemAllMenu;
    private javax.swing.JMenuItem jMenuItemHistory;
    private javax.swing.JMenuItem jMenuItemLogout;
    private javax.swing.JMenuItem jMenuItemManage;
    private javax.swing.JMenuItem jMenuItemProfile;
    private javax.swing.JMenu jMenuManageAccount;
    private javax.swing.JMenu jMenuTransaction;
    // End of variables declaration//GEN-END:variables
}
