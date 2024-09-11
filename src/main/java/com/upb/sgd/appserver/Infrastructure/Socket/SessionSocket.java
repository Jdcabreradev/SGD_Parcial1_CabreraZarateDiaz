/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.upb.sgd.appserver.Infrastructure.Socket;

import java.io.IOException;
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

    //private final InputStream inputStream;
    private final OutputStream outputStream;  
    private final ServerProcess serverProcess;

    public SessionSocket(Socket socket, Integer sessionId, ServerProcess serverProcess) throws IOException {
        this.socket = socket;
        this.sessionId = sessionId;
        //this.inputStream = socket.getInputStream();
        this.outputStream = socket.getOutputStream();
        this.serverProcess = serverProcess;
    }

    /*public void Init() {
        new Thread(this::ReadThread).start();
    }*/

    public boolean Close() {
        try {
            socket.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /*public void Read() throws IOException {
        while (this.running) {
            byte[] lengthBuffer = new byte[4];
            inputStream.read(lengthBuffer);
            //order(ByteOrder.LITTLE_ENDIAN).
            int messageLength = ByteBuffer.wrap(lengthBuffer).getInt();

            if (messageLength > 0) {
                byte[] buffer = new byte[messageLength];
                inputStream.read(buffer, 0, messageLength);

                router.Route(this.sessionId, buffer);
            } else {
                Logger.Log(LoggerLevel.Warning, name, "Forcefully disconnected");
                this.router.Route(sessionId, "{\"route\":\"Disconnect\", \"data\": \"\"}".getBytes(StandardCharsets.UTF_8));
                this.running = false;
            }
        }

        this.Close();
    }*/

    /*private void ReadThread() {
        try {
            Logger.Log(LoggerLevel.Info, name, "Read thread started");
            Read();
        } catch (IOException e) {
            Logger.Log(LoggerLevel.Error, name, "Error while reading data: " + e.getMessage());
            this.router.Route(sessionId, "{\"route\":\"Disconnect\", \"data\": \"\"}".getBytes(StandardCharsets.UTF_8));
            Close();
        }
    }*/

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
