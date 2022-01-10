package main;

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


import controller.ControllerLogin;
import model.User;
import view.FormLogin;

/**
 *
 * @author Yosapat
 */
public class Yosapat{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        User user = new User();
        FormLogin formLogin = new FormLogin();
        formLogin.setVisible(true);
        ControllerLogin buttonController = new ControllerLogin();
        buttonController.initController(formLogin, user);
    }
    
}
