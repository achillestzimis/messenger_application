package com.socket;

import java.io.*;
import java.net.*;
import java.util.Date;
import java.text.*; 

class S_Thread extends Thread { 
 
    public Server_Socket server = null;
    public Socket socket = null;
    public int clientID = -1;
    public String username = "";
    public ObjectInputStream streamIn  =  null;
    public ObjectOutputStream streamOut = null;
    public ServerArchitecture ui;

    public DateFormat df = DateFormat.getInstance(); 
    
    public S_Thread(Server_Socket _server, Socket _socket){  
        super();
        server = _server;
        socket = _socket;
        clientID     = socket.getPort();
        ui     = _server.ui;
    }
    
    public void send(Message msg){
        try {
            streamOut.writeObject(msg);
            streamOut.flush();
        } 
        catch (IOException ex) {
        }
    }
    
    public int getID(){  
     return clientID;
    }
   
    public void run(){  
     ui.jTextArea11.append("\n"+df.format(System.currentTimeMillis())+" Client " + clientID + " running.");
        while (true){  
         try{  
          Message msg = (Message) streamIn.readObject();
          server.handle(clientID, msg);
            }
            catch(Exception ioe){  
                server.remove(clientID);
                stop();
            }
        }
    }
    
    public void openStream() {  
      try{
        streamOut = new ObjectOutputStream(socket.getOutputStream());
        streamOut.flush();
        streamIn = new ObjectInputStream(socket.getInputStream());
       }catch(IOException e){ 
      }
    }
    
    public void close() {  
      
      if (socket != null){ 
      try{
        socket.close();
         }catch(IOException e){
        }
      }
      
      if (streamIn != null){ 
      try{
        streamIn.close();
        }catch(IOException e){
       }
     }
      
      if (streamOut != null){ 
        try{
          streamOut.close();
        }
      catch(IOException e){
     }
      }
    }
  }
