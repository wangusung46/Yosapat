package controller;

import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.JOptionPane;
import model.User;
import service.ServiceRegister;
import view.FormLogin;
import view.FormRegister;

public class ControllerRegister {

    @SuppressWarnings("empty-statement")
    public void initController(FormRegister formRegister, User user) {
        formRegister.getjRadioMale().setActionCommand("Male");
        formRegister.getjRadioFamale().setActionCommand("Famale");
        formRegister.getjButtonRegister().setEnabled(false);
        
        formRegister.getjButtonRegister().addActionListener(e -> performRegister(formRegister, user));
        formRegister.getjButtonLogin().addActionListener(e -> doLogin(formRegister, user));
        formRegister.getjCheckBox1().addItemListener((ItemEvent e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                formRegister.getjButtonRegister().setEnabled(true);
            } else {
                formRegister.getjButtonRegister().setEnabled(false);
            };
        });
        formRegister.getjTextPhoneNumber().addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                numberValidation(e);
            }
        });
        formRegister.getjTextDay().addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                numberValidation(e);
            }
        });
        formRegister.getjTextMonth().addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                numberValidation(e);
            }
        });
        formRegister.getjTextYears().addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                numberValidation(e);
            }
        });
    }

    private void numberValidation(KeyEvent e) {
        char c = e.getKeyChar();
        if (!((c >= '0') && (c <= '9')
                || (c == KeyEvent.VK_BACK_SPACE)
                || (c == KeyEvent.VK_DELETE))) {
            e.consume();
        }
    }

    @SuppressWarnings("deprecation")
    private void performRegister(FormRegister formRegister, User user) {
        String id = "U" + Integer.toString(ThreadLocalRandom.current().nextInt(1000, 9999));

        ServiceRegister serviceRegister = new ServiceRegister();
        String result = serviceRegister.saveRegister(
                id,
                formRegister.getjTextUserName().getText(),
                formRegister.getjTextEmail().getText(),
                formRegister.getButtonGroupGender().getSelection().getActionCommand(),
                formRegister.getjTextYears().getText(),
                formRegister.getjTextMonth().getText(),
                formRegister.getjTextDay().getText(),
                formRegister.getjTextPhoneNumber().getText(),
                formRegister.getjTextAreaAddress().getText(),
                formRegister.getjPasswordField().getText(),
                formRegister.getjPasswordConfirm().getText()
        );

        switch (result) {
            case "EMPTY":
                JOptionPane.showMessageDialog(null, "Field cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                empty(formRegister);
                break;
            case "WRONG":
                JOptionPane.showMessageDialog(null, "Password Not Equals and Aphanumeric", "Error", JOptionPane.ERROR_MESSAGE);
                empty(formRegister);
                break;
            case "EXIXST":
                JOptionPane.showMessageDialog(null, "Username Exist", "Error", JOptionPane.ERROR_MESSAGE);
                empty(formRegister);
                break;
            case "LENGTHUSERNAME":
                JOptionPane.showMessageDialog(null, "Username Must be 5 - 15", "Error", JOptionPane.ERROR_MESSAGE);
                empty(formRegister);
                break;
            case "LENGTHEMAIL":
                JOptionPane.showMessageDialog(null, "Email Not valid", "Error", JOptionPane.ERROR_MESSAGE);
                empty(formRegister);
                break;
            case "LENGTHHP":
                JOptionPane.showMessageDialog(null, "Phone Number Not Valid", "Error", JOptionPane.ERROR_MESSAGE);
                empty(formRegister);
                break;
            case "DATE":
                JOptionPane.showMessageDialog(null, "Date Not Valid", "Error", JOptionPane.ERROR_MESSAGE);
                empty(formRegister);
                break;
            case "ADDRESS":
                JOptionPane.showMessageDialog(null, "Addres Not Valid", "Error", JOptionPane.ERROR_MESSAGE);
                empty(formRegister);
                break;
            case "COMPLETE":
                JOptionPane.showMessageDialog(null, "Login Success", "Success", JOptionPane.INFORMATION_MESSAGE);
                doLogin(formRegister, user);
                break;
            default:
                JOptionPane.showMessageDialog(null, result);
                break;
        }
    }

    private void empty(FormRegister formRegister) {
        formRegister.getjPasswordField().setText("");
        formRegister.getjPasswordConfirm().setText("");
    }

    private void doLogin(FormRegister formRegister, User user) {
        formRegister.setVisible(false);
        FormLogin formLogin = new FormLogin();
        formLogin.setVisible(true);
        ControllerLogin controllerLogin = new ControllerLogin();
        controllerLogin.initController(formLogin, user);
    }
}
