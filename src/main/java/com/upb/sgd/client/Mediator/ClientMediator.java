/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.upb.sgd.client.Mediator;

import com.upb.sgd.client.GUI.Interface.GUIWindow;
import com.upb.sgd.client.GUI.LoginWindow;
import com.upb.sgd.client.RMI.UserService;

/**
 *
 * @author sebastian
 */
public class ClientMediator {
    public UserService userService;
    private GUIWindow currentWindow;

    public void Init(){
        this.userService = new UserService("rmi://localhost:1802/appserver/user");
        this.userService.Init();
        
        this.currentWindow = new LoginWindow(this);
        this.currentWindow.Init();
    }

    public void ChangeCurrentGUIWindow(GUIWindow newWindow){
        this.currentWindow.Close();
        this.currentWindow = newWindow;
        this.currentWindow.Init();
    }
}
