package controller;

import model.User;
import view.FormItemProfile;
import view.FormLogin;
import view.FormMainMenu;
import view.FormViewAllMenu;
import view.FormViewItemManage;
import view.FormViewItemTransaction;
import view.FormViewManageMenu;

public class ControllerMainMenu {

    @SuppressWarnings("empty-statement")
    public void initController(FormMainMenu formMainMenu, User user) {
        formMainMenu.getjMenuItemProfile().addActionListener(e -> doItemProfile(formMainMenu, user));
        formMainMenu.getjMenuItemLogout().addActionListener(e -> doItemLogout(formMainMenu, user));
        formMainMenu.getjMenuItemAllMenu().addActionListener(e -> doMenuItemAllMenu(formMainMenu, user));
        formMainMenu.getjMenuItemManage().addActionListener(e -> doMenuItemManage(formMainMenu, user));
        formMainMenu.getjMenuItemHistory().addActionListener(e -> doMenuItemHistory(formMainMenu, user));
        formMainMenu.getjMenuIManageMenu().addActionListener(e -> doMenuManageMenu(formMainMenu, user));
    }

    private void doItemProfile(FormMainMenu formMainMenu, User user) {
        formMainMenu.setVisible(false);
        FormItemProfile formItemProfile = new FormItemProfile();
        formItemProfile.setVisible(true);
        ControllerProfile controllerProfile = new ControllerProfile();
        controllerProfile.initController(formItemProfile, user);
    }

    private void doItemLogout(FormMainMenu formMainMenu, User user) {
        formMainMenu.setVisible(false);
        FormLogin formLogin = new FormLogin();
        formLogin.setVisible(true);
        ControllerLogin controllerLogin = new ControllerLogin();
        controllerLogin.initController(formLogin, user);
    }

    private void doMenuItemAllMenu(FormMainMenu formMainMenu, User user) {
        formMainMenu.setVisible(false);
        FormViewAllMenu formViewAllMenu = new FormViewAllMenu();
        formViewAllMenu.setVisible(true);
        ControllerViewAllMenu controllerViewAllMenu = new ControllerViewAllMenu();
        controllerViewAllMenu.initController(formViewAllMenu, user);
    }

    private void doMenuItemManage(FormMainMenu formMainMenu, User user) {
        formMainMenu.setVisible(false);
        FormViewItemManage formViewItemManage = new FormViewItemManage();
        formViewItemManage.setVisible(true);
        ControllerViewItemManage controllerViewItemManage = new ControllerViewItemManage();
        controllerViewItemManage.initController(formViewItemManage, user);
    }

    private void doMenuItemHistory(FormMainMenu formMainMenu, User user) {
        formMainMenu.setVisible(false);
        FormViewItemTransaction formViewItemTransaction = new FormViewItemTransaction();
        formViewItemTransaction.setVisible(true);
//        ControllerLogin controllerLogin = new ControllerLogin(user, formLogin);
//        controllerLogin.initController();
    }

    private void doMenuManageMenu(FormMainMenu formMainMenu, User user) {
        formMainMenu.setVisible(false);
        FormViewManageMenu formViewManageMenu = new FormViewManageMenu();
        formViewManageMenu.setVisible(true);
//        ControllerLogin controllerLogin = new ControllerLogin(user, formLogin);
//        controllerLogin.initController();
    }
}
