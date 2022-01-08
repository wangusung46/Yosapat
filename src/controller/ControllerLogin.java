package controller;

import javax.swing.JOptionPane;
import model.User;
import view.FormLogin;
import service.ServiceLogin;
import view.FormMainMenu;
import view.FormRegister;

public class ControllerLogin {

    public void initController(FormLogin formLogin, User user) {
        formLogin.getjButtonLogin().addActionListener(e -> performLogin(formLogin, user));
        formLogin.getjButtonRegister().addActionListener(e -> doRegister(formLogin, user));
    }

    @SuppressWarnings("deprecation")
    private void performLogin(FormLogin formLogin, User user) {

        ServiceLogin serviceLogin = new ServiceLogin();
        String result = serviceLogin.login(formLogin.getjTextUserName().getText(), formLogin.getjPasswordField().getText(), user);

        switch (result) {
            case "EMPTY":
                JOptionPane.showMessageDialog(null, "Username cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                break;
            case "COMPLETE":
                JOptionPane.showMessageDialog(null, "Login Success", "Success", JOptionPane.INFORMATION_MESSAGE);
                doMainMenu(formLogin, user);
                break;
            case "INCOMPLETE":
                formLogin.getjTextUserName().setText("");
                formLogin.getjPasswordField().setText("");
                JOptionPane.showMessageDialog(null, "Login Failed", "Failed", JOptionPane.ERROR_MESSAGE);
                break;
            default:
                JOptionPane.showMessageDialog(null, result);
                break;
        }
    }

    private void doRegister(FormLogin formLogin, User user) {
        formLogin.setVisible(false);
        FormRegister formRegister = new FormRegister();
        formRegister.setVisible(true);
        ControllerRegister controllerRegister = new ControllerRegister();
        controllerRegister.initController(formRegister, user);
    }
    private void doMainMenu(FormLogin formLogin, User user) {
        formLogin.setVisible(false);
        FormMainMenu formMainMenu = new FormMainMenu(user);
        formMainMenu.setVisible(true);
        ControllerMainMenu controllerMainMenu = new ControllerMainMenu();
        controllerMainMenu.initController(formMainMenu, user);
    }
}
