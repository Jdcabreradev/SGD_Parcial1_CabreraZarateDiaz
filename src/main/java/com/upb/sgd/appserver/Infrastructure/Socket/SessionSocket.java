/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.upb.sgd.appserver.Infrastructure.Socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 *
 * @author sebastian
 */
public class SessionSocket {
    public Integer sessionId;
    public Socket socket;
    public volatile boolean running = true;

    private final InputStream inputStream;
    private final OutputStream outputStream;  
    private final ServerProcess serverProcess;

    public SessionSocket(Socket socket, Integer sessionId, ServerProcess serverProcess) throws IOException {
        this.socket = socket;
        this.sessionId = sessionId;
        this.inputStream = socket.getInputStream();
        this.outputStream = socket.getOutputStream();
        this.serverProcess = serverProcess;
    }

    public void Init() {
        new Thread(this::ReadThread).start();
    }

    public boolean Close() {
        try {
            socket.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public void Read() throws IOException {
        while (this.running) {
            byte[] lengthBuffer = new byte[4];
            inputStream.read(lengthBuffer);
            int messageLength = ByteBuffer.wrap(lengthBuffer).getInt();

            if (messageLength > 0) {
                byte[] buffer = new byte[messageLength];
                inputStream.read(buffer, 0, messageLength);

                this.serverProcess.Broadcast(buffer);
            } else {
                System.out.println("Forcefully disconnected");
                this.running = false;
            }
        }

        this.Close();
    }

    private void ReadThread() {
        try {
            System.out.println("Read thread started");
            Read();
        } catch (IOException e) {
            System.err.println("Error while reading data: " + e.getMessage());
            Close();
        }
    }

    public void Write(byte[] data) {
        try {
            byte[] lengthBytes = ByteBuffer.allocate(4).putInt(data.length).array();

            outputStream.write(lengthBytes);
            outputStream.flush();

            outputStream.write(data);
            outputStream.flush();
        } catch (IOException e) {
            System.out.println("Error while writing data");
            serverProcess.Disconnect(sessionId);
        }
    }
}
