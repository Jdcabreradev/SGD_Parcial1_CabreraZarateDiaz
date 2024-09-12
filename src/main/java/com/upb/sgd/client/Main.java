package com.upb.sgd.client;

import com.upb.sgd.client.Mediator.ClientMediator;

/**
 *
 * @author jj
 */
public class Main {
    public static void main(String[] args) {
        //LoginWindow gui = new LoginWindow();
        //gui.showLogin();

        ClientMediator client = new ClientMediator();
        client.Init();
    }
}