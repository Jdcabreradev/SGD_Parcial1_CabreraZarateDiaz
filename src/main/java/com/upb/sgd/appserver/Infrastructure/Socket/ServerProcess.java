/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.upb.sgd.appserver.Infrastructure.Socket;

import java.io.IOException;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author sebastian
 */
public class ServerProcess {

    private final ServerSocket socket;
    private final Map<Integer, SessionSocket> clients = new ConcurrentHashMap<>();

    public ServerProcess(ServerSocket socket) {
        this.socket = socket;
    }

    public void Init() {
        System.out.println("Initializing Socket Communication Module");

        if (socket.Init()) {
            System.out.println("Initialized Server Socket");
            Thread thread = new Thread(() -> Listen());
            thread.start();
        } else {
            System.out.println("Socket Error");
        }
    }

    public void Listen() {
        while (true) {
            System.out.println("Server Socket Listening");

            var sessionSocket = socket.Listen();

            if (sessionSocket != null) {
                System.out.println("Client accepted");

                Thread thread = new Thread(() -> HandleConnection(sessionSocket));
                thread.start();
            } else {
                System.out.println("Unable to accept client");
            }
        }
    }

    public void Disconnect(int sessionId) {
        if (this.clients.containsKey(sessionId)) {
            var client = this.clients.get(sessionId);
            client.running = false;
            System.out.println("Removed client with Id: " + sessionId);
            this.clients.remove(sessionId);
        } else {
            System.out.println("Unable to remove client. Id is non existant.");
        }
    }

    public void HandleConnection(Socket sessionSocket) {
        try {
            System.out.println("Setting up new client session");
            SessionSocket session = new SessionSocket(sessionSocket, this.getUserId(), this);
            session.Init();
            System.out.println("Client user registered in map with id" + session.sessionId);
            clients.put(session.sessionId, session);
        } catch (IOException e) {
            System.out.println("Error while handling connection");
        }
    }

    public void Broadcast(byte[] data) {
        int count = 0;
        System.out.println("Broadcasting directory changes");
        for (SessionSocket session : clients.values()) {
            session.Write(data);
            count++;
        }
        System.out.println("Broadcasted to " + count + " clients");
    }

    private Integer getUserId() {
        SecureRandom secureRandom = new SecureRandom();
        int randomNumber = secureRandom.nextInt(10000);

        while (clients.containsKey(randomNumber)) {
            randomNumber = secureRandom.nextInt(10000);
        }

        return randomNumber;
    }
}
