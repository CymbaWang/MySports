package com.example.mysports.util;

import android.app.Application;

import com.example.mysports.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ApplicationUtil extends Application {

    public static final String ADDRESS = "172.20.10.3";
    public static final int PORT = 8080;

    private Socket socket;
    private OutputStream outputStream;
    private InputStream inputStream;
    private User user;

    public void init() {
         try{
             socket = new Socket();
             socket.connect(new InetSocketAddress(ADDRESS,PORT), 5000);
             outputStream = socket.getOutputStream();
             inputStream = socket.getInputStream();
             }catch (IOException e){
                 e.printStackTrace();
            }
            catch (Exception ee){
             ee.printStackTrace();
         }
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
