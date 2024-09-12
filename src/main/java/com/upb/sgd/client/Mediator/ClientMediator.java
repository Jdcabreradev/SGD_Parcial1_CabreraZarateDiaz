/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.upb.sgd.client.Mediator;

import com.upb.sgd.client.GUI.Interface.GUIWindow;
import com.upb.sgd.client.GUI.LoginWindow;
import com.upb.sgd.client.RMI.DataService;
import com.upb.sgd.client.RMI.UserService;
import com.upb.sgd.client.Socket.ClientProcess;
import com.upb.sgd.shared.domain.User;

/**
 *
 * @author sebastian
 */
public class ClientMediator {
    public UserService userService;
    public DataService dataService;
    public User loggedUser;
    private GUIWindow currentWindow;
    private ClientProcess clientProcess;

    public void Init(){
        this.userService = new UserService("rmi://25.49.100.226:1802/appserver/user");
        this.userService.Init();

        this.dataService = new DataService("rmi://25.49.100.226:1802/appserver/data");
        this.dataService.Init();

        this.clientProcess = new ClientProcess(1803,this);
        this.clientProcess.Init();
        
        this.currentWindow = new LoginWindow(this);
        this.currentWindow.Init();
    }

    public void ChangeCurrentGUIWindow(GUIWindow newWindow){
        this.currentWindow.Close();
        this.currentWindow = newWindow;
        this.currentWindow.Init();
    }
}
