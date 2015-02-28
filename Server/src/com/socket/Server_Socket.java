package com.socket;

import java.io.*;
import java.net.*;
import java.util.*;
import java.text.*;         

public class Server_Socket implements Runnable {
    
    public S_Thread clientCollection[];
    public ServerSocket server = null;
    public Thread thread = null;
    public int counter = 0, port = 13000;
    public ServerArchitecture ui;
    public Database database;
    
    public DateFormat df = DateFormat.getInstance(); 
    
    public Server_Socket(ServerArchitecture frame){
       
      clientCollection = new S_Thread[10];
      ui = frame;
      database = new Database(ui.filePath);
      
      try{  
        server = new ServerSocket(port);
        port = server.getLocalPort();
        ui.jTextArea11.append("\n"+df.format(System.currentTimeMillis())+" Server started. IP : " + InetAddress.getLocalHost() + ", Port : " + server.getLocalPort());
        start(); 
      }
      catch(IOException ioe){  
        ui.jTextArea11.append("\n"+df.format(System.currentTimeMillis())+" Could not connect to port : " + port + "\nRetrying"); 
        ui.startServer(0);
      }
    }
    
    public Server_Socket(ServerArchitecture frame, int Port){
       
      clientCollection = new S_Thread[10];
      ui = frame;
      port = Port;
      database = new Database(ui.filePath);
      
      try{  
        server = new ServerSocket(port);
        port = server.getLocalPort();
        ui.jTextArea11.append("\n"+df.format(System.currentTimeMillis())+" Server started. IP : " + InetAddress.getLocalHost() + ", Port : " + server.getLocalPort());
        start(); 
      }
      catch(IOException ioe){  
        ui.jTextArea11.append("\n"+df.format(System.currentTimeMillis())+" Can not bind to port " + port + ": " + ioe.getMessage()); 
      }
    }
 
    public void run(){  
      while (thread != null){  
        try{  
          ui.jTextArea11.append("\n"+df.format(System.currentTimeMillis())+" Waiting for a client ..."); 
          addThread(server.accept()); 
        }
        catch(Exception ioe){ 
          ui.jTextArea11.append("\n"+df.format(System.currentTimeMillis())+" Server error: \n");
          ui.startServer(0);
        }
      }
    }
 
    public void start(){  
      if (thread == null){  
        thread = new Thread(this); 
        thread.start();
      }
    }
    
    public void stop(){  
      if (thread != null){  
        thread.stop(); 
        thread = null;
      }
    }
    
    private int findClient(int ID){  
      for (int i = 0; i < counter; i++){
        if (clientCollection[i].getID() == ID){
          return i;
        }
      }
      return -1;
    }
 
    public synchronized void handle(int ID, Message message){  
      if (message.content.equals("remove")){
        broadCast("SO", "SERVER", message.sender);
        remove(ID); 
      }
      else{
        if(message.type.equals("loginClient")){
          if(findUserThread(message.sender) == null){
            if(database.verifyLogin(message.sender, message.content)){
              clientCollection[findClient(ID)].username = message.sender;
              clientCollection[findClient(ID)].send(new Message("loginClient", "SERVER", "TRUE", message.sender));
              broadCast("NUSER", "SERVER", message.sender);
              doUsers(message.sender);
            }
            else{
              clientCollection[findClient(ID)].send(new Message("loginClient", "SERVER", "FALSE", message.sender));
            } 
          }
          else{
            clientCollection[findClient(ID)].send(new Message("loginClient", "SERVER", "FALSE", message.sender));
          }
        }
        else if(message.type.equals("message")){
          if(message.recipient.equals("All")){
            broadCast("message", message.sender, message.content);
          }
          else{
            findUserThread(message.recipient).send(new Message(message.type, message.sender, message.content, message.recipient));
            clientCollection[findClient(ID)].send(new Message(message.type, message.sender, message.content, message.recipient));
          }
        }
        else if(message.type.equals("connect")){
          clientCollection[findClient(ID)].send(new Message("connect", "SERVER", "OK", message.sender));
        }       
        else if(message.type.equals("signoff")){
          if(findUserThread(message.sender) == null){
            if(!database.verifyUser(message.sender)){
 
              clientCollection[findClient(ID)].username = message.sender;
              clientCollection[findClient(ID)].send(new Message("signoff", "SERVER", "TRUE", message.sender));
              clientCollection[findClient(ID)].send(new Message("loginClient", "SERVER", "TRUE", message.sender));
              broadCast("NUSER", "SERVER", message.sender);
              doUsers(message.sender);
            }
            else{
              clientCollection[findClient(ID)].send(new Message("signoff", "SERVER", "FALSE", message.sender));
            }
          }
          else{
            clientCollection[findClient(ID)].send(new Message("signoff", "SERVER", "FALSE", message.sender));
          }
        }
      }
    }
    
    public void broadCast(String type, String sender, String content){
        Message msg = new Message(type, sender, content, "All");
        for(int i = 0; i < counter; i++){
            clientCollection[i].send(msg);
        }
    }
    
    public void doUsers(String toWhom){
        for(int i = 0; i < counter; i++){
            findUserThread(toWhom).send(new Message("NUSER", "SERVER", clientCollection[i].username, toWhom));
        }
    }
    
    public S_Thread findUserThread(String usr){
        for(int i = 0; i < counter; i++){
            if(clientCollection[i].username.equals(usr)){
                return clientCollection[i];
            }
        }
        return null;
    }
 
    public synchronized void remove(int ID){  
      int pos = findClient(ID);
      if (pos >= 0){  
        S_Thread toTerminate = clientCollection[pos];
        ui.jTextArea11.append("\n"+df.format(System.currentTimeMillis())+" Removing client " + ID + " at " + pos);
        if (pos < counter-1){
          for (int i = pos+1; i < counter; i++){
            clientCollection[i-1] = clientCollection[i];
          }
        }
        counter--;
        try{  
          toTerminate.close(); 
        }
        catch(Exception e){  
          ui.jTextArea11.append("\nError closing client " + e); 
        }
        toTerminate.stop(); 
      }
    }
    
    private void addThread(Socket socket){  
      if (counter < 10){  
        ui.jTextArea11.append("\n"+df.format(System.currentTimeMillis())+" Client accepted: " + socket);
        clientCollection[counter] = new S_Thread(this, socket);
        try{  
          clientCollection[counter].openStream(); 
          clientCollection[counter].start();  
          counter++; 
        }
        catch(Exception e){  
          ui.jTextArea11.append("\n"+df.format(System.currentTimeMillis())+" Error opening thread: " + e); 
        } 
      }
      else{
        ui.jTextArea11.append("\n"+df.format(System.currentTimeMillis())+" Too many clients to connect now.");
      }
    }
}
