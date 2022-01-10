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
package controller;

import javax.swing.JOptionPane;
import model.User;
import service.ServiceProfile;
import view.FormItemProfile;
import view.FormMainMenu;

/**
 *
 * @author Yosapat
 */
class ControllerProfile {

    @SuppressWarnings("deprecation")
    void initController(FormItemProfile formItemProfile, User user) {
        formItemProfile.getjRadioMale().setActionCommand("Male");
        formItemProfile.getjRadioFamale().setActionCommand("Famale");
        formItemProfile.getjRadioMale().setEnabled(false);
        formItemProfile.getjRadioFamale().setEnabled(false);
        
        formItemProfile.getjTextUserName().setText(user.getUsername());
        formItemProfile.getjTextEmail().setText(user.getUserEmail());
        if(user.getUserGender().equals("Male")){
            formItemProfile.getjRadioMale().setSelected(true);
        } else{
            formItemProfile.getjRadioFamale().setSelected(true);
        }
        formItemProfile.getjTextDay().setText(Integer.toString(user.getUserDOB().getDate()));
        formItemProfile.getjTextMonth().setText(Integer.toString(user.getUserDOB().getMonth() + 1));
        formItemProfile.getjTextYears().setText(Integer.toString(user.getUserDOB().getYear() + 1900));
        formItemProfile.getjTextPhoneNumber().setText(user.getUserPhoneNumber());
        formItemProfile.getjTextAreaAddress().setText(user.getUserAddress());
        formItemProfile.getjButtonRegister().addActionListener(e -> performUpdate(formItemProfile, user));
    }

    @SuppressWarnings("deprecation")
    private void performUpdate(FormItemProfile formItemProfile, User user) {
        ServiceProfile serviceProfile = new ServiceProfile();
        String result = serviceProfile.updateProfile(
                user.getUserId(),
                user.getUserRole(),
                formItemProfile.getjTextUserName().getText(),
                formItemProfile.getjTextEmail().getText(),
                formItemProfile.getButtonGroupGender().getSelection().getActionCommand(),
                formItemProfile.getjTextYears().getText(),
                formItemProfile.getjTextMonth().getText(),
                formItemProfile.getjTextDay().getText(),
                formItemProfile.getjTextPhoneNumber().getText(),
                formItemProfile.getjTextAreaAddress().getText(),
                formItemProfile.getjPasswordField().getText(),
                formItemProfile.getjPasswordConfirm().getText(),
                formItemProfile.getjPasswordFieldOld().getText()
        );

        switch (result) {
            case "EMPTY":
                JOptionPane.showMessageDialog(null, "Field cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                empty(formItemProfile);
                break;
            case "WRONG":
                JOptionPane.showMessageDialog(null, "Password Not Equals and Aphanumeric", "Error", JOptionPane.ERROR_MESSAGE);
                empty(formItemProfile);
                break;
            case "EXIXST":
                JOptionPane.showMessageDialog(null, "Username Exist", "Error", JOptionPane.ERROR_MESSAGE);
                empty(formItemProfile);
                break;
            case "LENGTHUSERNAME":
                JOptionPane.showMessageDialog(null, "Username Must be 5 - 15", "Error", JOptionPane.ERROR_MESSAGE);
                empty(formItemProfile);
                break;
            case "LENGTHEMAIL":
                JOptionPane.showMessageDialog(null, "Email Not valid", "Error", JOptionPane.ERROR_MESSAGE);
                empty(formItemProfile);
                break;
            case "LENGTHHP":
                JOptionPane.showMessageDialog(null, "Phone Number Not Valid", "Error", JOptionPane.ERROR_MESSAGE);
                empty(formItemProfile);
                break;
            case "DATE":
                JOptionPane.showMessageDialog(null, "Date Not Valid", "Error", JOptionPane.ERROR_MESSAGE);
                empty(formItemProfile);
                break;
            case "ADDRESS":
                JOptionPane.showMessageDialog(null, "Addres Not Valid", "Error", JOptionPane.ERROR_MESSAGE);
                empty(formItemProfile);
                break;
            case "COMPLETE":
                JOptionPane.showMessageDialog(null, "Login Success", "Success", JOptionPane.INFORMATION_MESSAGE);
                doUpdate(formItemProfile, user);
                break;
            default:
                JOptionPane.showMessageDialog(null, result);
                break;
        }
    }

    private void empty(FormItemProfile formItemProfile) {
        formItemProfile.getjPasswordField().setText("");
        formItemProfile.getjPasswordConfirm().setText("");
        formItemProfile.getjPasswordFieldOld().setText("");
    }

    private void doUpdate(FormItemProfile formItemProfile, User user) {
        formItemProfile.setVisible(false);
        FormMainMenu formMainMenu = new FormMainMenu(user);
        formMainMenu.setVisible(true);
        ControllerMainMenu controllerMainMenu = new ControllerMainMenu();
        controllerMainMenu.initController(formMainMenu, user);
    }
    
}
