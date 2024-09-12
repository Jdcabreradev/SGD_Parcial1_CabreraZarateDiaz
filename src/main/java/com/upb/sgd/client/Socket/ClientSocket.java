/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.upb.sgd.client.Socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 *
 * @author sebastian
 */
public class ClientSocket {
    private final Socket socket;
    private final InetSocketAddress ipEndPoint;
    private final ClientProcess clientProcess;
    private InputStream inputStream;
    private OutputStream outputStream;
    private Boolean running = false;

    public ClientSocket(int port, String url, ClientProcess clientProcess) throws IOException {
        this.clientProcess = clientProcess;
        InetAddress localIpAddress = InetAddress.getByName(url);
        ipEndPoint = new InetSocketAddress(localIpAddress, port);
        socket = new Socket();
    }

    public Boolean Close() {
        try {
            socket.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public Boolean Connect() {
        try {
            socket.connect(ipEndPoint);
            this.inputStream = socket.getInputStream();
            this.outputStream = socket.getOutputStream();
            this.running = true;
            return true;
        } catch (IOException e) {
            System.out.println("Unable to connect to the server");
            return false;
        }
    }

    public void Read() {
        while (running) {
            try {
                byte[] lengthBuffer = new byte[4];
                inputStream.read(lengthBuffer);

                int messageLength = ByteBuffer.wrap(lengthBuffer).getInt();
                if (messageLength > 0) {
                    byte[] buffer = new byte[messageLength];
                    inputStream.read(buffer, 0, messageLength);

                    //TODO: SEND TO UI
                    //router.Route(buffer);
                } else {
                    running = false;
                    System.out.println("Forcefully disconnected");
                }
            } catch (IOException e) {
                running = false;
                System.out.println("Disconnected from server");
                break;
            }
        }

        System.out.println("Read thread closed");
    }

    public void Write(byte[] data) {
        try {
            byte[] lengthBytes = ByteBuffer.allocate(4).putInt(data.length).array();

            outputStream.write(lengthBytes);
            outputStream.flush();

            outputStream.write(data);
            outputStream.flush();
        } catch (IOException e) {
            System.out.println("Error while writing data: " + e.getMessage());
        }
    }
}
