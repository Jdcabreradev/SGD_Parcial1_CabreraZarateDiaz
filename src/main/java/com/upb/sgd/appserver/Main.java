/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.upb.sgd.appserver;

import com.upb.sgd.appserver.Infrastructure.Server.AppServer;

/**
 *
 * @author sebastian
 */
public class Main {
    public static void main(String[] args) {
        AppServer appServer = new AppServer("localhost", "1802", "appserver");
        appServer.Init();
    }
}
