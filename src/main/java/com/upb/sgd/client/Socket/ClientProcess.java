/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.upb.sgd.client.Socket;

import java.io.IOException;

import com.upb.sgd.client.Mediator.ClientMediator;

/**
 *
 * @author sebastian
 */
public class ClientProcess {
    private final int port;
    public boolean connected = false;
    public ClientSocket socket;
    public ClientMediator mediator;

    public ClientProcess(int port, ClientMediator mediator) {
        this.port = port;
        this.mediator = mediator;
    }

    public Boolean Init() {
        try {
            socket = new ClientSocket(
                    port,
                    "25.49.100.226",
                    this
            );

            return Connect();
        } catch (IOException e) {
            System.out.println("Failed to initialize ClientSocket: " + e.getMessage());
            return false;
        }
    }

    public void Close() {
        if (socket != null && socket.Close()) {
            connected = false;
            System.out.println("Closed client socket");
        } else if (connected) {
            System.out.println("Failed to safely close socket");
        }
    }

    private boolean Connect() {
        if (!connected) {
            if (socket != null && socket.Connect()) {
                connected = true;
                System.out.println("Connected to server");

                Thread newThread = new Thread(socket::Read);
                newThread.start();

                System.out.println("Read thread started");

                return true;
            } else {
                System.out.println("Failed connection to server");
                return false;
            }
        } else {
            System.out.println("Socket already connected");
            return false;
        }
    }

    public void Write(byte[] data) {
        if (connected && socket != null) {
            socket.Write(data);
        } else {
            System.out.println("Socket not connected.");
        }
    }
}
