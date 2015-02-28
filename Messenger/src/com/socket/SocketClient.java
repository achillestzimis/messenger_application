package com.socket;

import com.ui.ChatApplication;
import java.io.*;
import java.net.*;
import java.util.Date;
import java.text.*;         
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class SocketClient implements Runnable{
    
    public ChatApplication gui;
    public ObjectInputStream Input;
    public ObjectOutputStream Output;
    public int portAddress;
    public String serverAddress;
    public Socket socketPt;
    public DateFormat df = DateFormat.getInstance(); 
                     
    public SocketClient(ChatApplication frame) throws IOException{
        gui = frame; 
        this.serverAddress = gui.serverField; 
        this.portAddress = gui.portAddress;
        startConnection();
    }
   
    private void startConnection() throws IOException{
      try{
        socketPt = new Socket(InetAddress.getByName(serverAddress), portAddress);    
        Output = new ObjectOutputStream(socketPt.getOutputStream());
        Output.flush();
        Input = new ObjectInputStream(socketPt.getInputStream());
      }catch(Exception e){}
   }
    
    public void run(){
        boolean search = true;
        while(search){
            try{
                Message message = (Message) Input.readObject(); 
                if(message.type.equals("message")){
                    if(message.recipient.equals(gui.user)){
                        gui.jTextArea333.append("\n"+df.format(System.currentTimeMillis())+" "+message.sender +" says : " + message.content + "\n");
                    }
                    else{
                        gui.jTextArea333.append("\n"+df.format(System.currentTimeMillis())+" "+message.sender + " says : " + message.content + "\n");
                    }
                }
                else if(message.type.equals("loginClient")){
                    if(message.content.equals("TRUE")){
                        gui.jButton20.setEnabled(false); 
                        gui.jButton88.setEnabled(false);                        
                        gui.jButton44.setEnabled(true); 
                        gui.jTextArea333.append("\n"+df.format(System.currentTimeMillis())+" Server says : Login Successful\n");
                        gui.jTextField24.setEnabled(false); 
                        gui.jPasswordField74.setEnabled(false);
                    }
                    else{
                        gui.jTextArea333.append("\n"+df.format(System.currentTimeMillis())+" Server says : Login Failed\n");
                    }
                }
                else if(message.type.equals("connect")){
                    gui.jButton55.setEnabled(false);
                    gui.jButton20.setEnabled(true); 
                    gui.jButton88.setEnabled(true);
                    gui.jTextField24.setEnabled(true); 
                    gui.jPasswordField74.setEnabled(true);
                    gui.jTextArea333.setEditable(false);
                    gui.jTextField33.setEditable(false);
                }
                else if(message.type.equals("NUSER")){
                    if(!message.content.equals(gui.user)){
                        boolean exists = false;
                        for(int i = 0; i < gui.model.getSize(); i++){
                            if(gui.model.getElementAt(i).equals(message.content)){
                                exists = true; 
                                break;
                            }
                        }
                        if(!exists){ gui.model.addElement(message.content); }
                    }
                }
                else if(message.type.equals("SO")){
                    if(message.content.equals(gui.password)){
                        gui.jTextArea333.append("\n"+df.format(System.currentTimeMillis())+" "+ message.sender +" says : Bye\n");
                        gui.jButton55.setEnabled(true); 
                        gui.jButton44.setEnabled(false);
                        gui.jTextField66.setEditable(true); 
                        gui.jTextField33.setEditable(true);
                        
                        for(int ii = 1; ii < gui.model.size(); ii++){
                            gui.model.removeElementAt(ii);
                        }
                        gui.socketThread.stop();
                    }
                    else{
                        gui.model.removeElement(message.content);
                        gui.jTextArea333.append("\n"+df.format(System.currentTimeMillis())+" "+ message.sender +" says : "+ message.content +" has signed out\n");
                    }
                }
                else if(message.type.equals("signoff")){
                    if(message.content.equals("TRUE")){
                        gui.jButton20.setEnabled(false); 
                        gui.jButton88.setEnabled(false);
                        gui.jButton44.setEnabled(true); 
                        gui.jTextArea333.append("\n"+df.format(System.currentTimeMillis())+" Server says : Signoff Successful\n");
                    }
                    else{
                        gui.jTextArea333.append("\n"+df.format(System.currentTimeMillis())+" Server says : Signup Failed\n");
                    }
                }
                else{
                    gui.jTextArea333.append("\n"+df.format(System.currentTimeMillis())+" Server says : Unknown message type\n");
                }
            }
            catch(Exception ex) {
                search = false;
                gui.jTextArea333.append("\n"+df.format(System.currentTimeMillis())+" Application says : Connection Failure\n");
                gui.jButton55.setEnabled(true); 
                gui.jTextField66.setEditable(true); 
                gui.jTextField33.setEditable(true);
                gui.jButton44.setEnabled(false);
                removeClient();
                gui.socketThread.stop();
                ex.printStackTrace();
            }
        }
    }
    
    public void removeClient(){
         for(int i = 1; i < gui.model.size(); i++){
           gui.model.removeElementAt(i);
         }
    }
    
    public void send(Message msg){
        try {
            Output.writeObject(msg);
            Output.flush();
        } 
        catch (IOException ex) {
        }
    }
    
    public void closeThread(Thread thread){
        thread = null;
    }
}
