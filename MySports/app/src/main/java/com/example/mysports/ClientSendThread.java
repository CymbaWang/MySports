package com.example.mysports;

import com.example.mysports.model.News;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientSendThread {
    private Socket mSocket = null;
    private ObjectOutputStream oos = null;
    public ClientSendThread(Socket socket)
    {
        this.mSocket=socket;
        try{
            oos= new ObjectOutputStream(mSocket.getOutputStream());
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void sendMessage(News news) throws IOException{
        oos.writeObject(news);
        oos.flush();
    }

}
