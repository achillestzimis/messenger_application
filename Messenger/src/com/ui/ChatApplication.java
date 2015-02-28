package com.ui;

import com.socket.*;

import java.awt.event.*; 
import java.util.*;
import java.text.*;         
import java.io.*;
import java.awt.*;
import javax.swing.*;
//import com.ui.*;

public class ChatApplication extends JFrame {

    public SocketClient s_client;
    public int portAddress;
    public String serverField;
    public String user;
    public String password;
    public Thread socketThread;
    public DefaultListModel model; 
    
    public JButton jButton55;
    public JButton jButton20;
    public JButton jButton88;
    public JButton jButton44;
    public JButton jButton77;
    private JLabel jLabel10; 
    private JLabel jLabel20; 
    private JLabel jLabel33; 
    private JLabel jLabel40;
    public JList jList100;
    public JPasswordField jPasswordField74;
    private JScrollPane jScrollPane1; 
    private JScrollPane jScrollPane2;
    private JSeparator jSeparator1; 
    private JSeparator jSeparator2;
    public JTextArea jTextArea333;
    public JTextField jTextField33;
    public JTextField jTextField24; 
    public JTextField jTextField6;
    public JTextField jTextField66; 
    
    public DateFormat df = DateFormat.getInstance(); 
                             
    public ChatApplication() {
        drawGUI();
        this.setTitle("Chat Application");
        model.addElement("All");
        jList100.setSelectedIndex(0); 
        
        this.addWindowListener(new WindowListener() {

            public void windowOpened(WindowEvent e) {}
            public void windowClosing(WindowEvent e) { try{ s_client.send(new Message("message", user, ".bye", "SERVER")); socketThread.stop();  }catch(Exception ex){} }
            public void windowClosed(WindowEvent e) {}
            public void windowIconified(WindowEvent e) {}
            public void windowDeiconified(WindowEvent e) {}
            public void windowActivated(WindowEvent e) {}
            public void windowDeactivated(WindowEvent e) {}
        });
    }
    
    private void drawGUI(){
    
        jTextField66 = new JTextField();
        jTextField33 = new JTextField();
        jTextField24 = new JTextField();
        jTextField6 = new JTextField();
        jScrollPane2 = new JScrollPane();
        jScrollPane1 = new JScrollPane(); 
        jButton88 = new JButton();
        jButton55 = new JButton();
        jButton44 = new JButton();
        jButton20 = new JButton();
        jButton77 = new JButton();
        jLabel10 = new JLabel();
        jLabel20 = new JLabel();
        jLabel33 = new JLabel();
        jLabel40 = new JLabel();
        jPasswordField74 = new JPasswordField();
        jSeparator1 = new JSeparator();
        jSeparator2 = new JSeparator();
        jTextArea333 = new JTextArea();
        jList100 = new JList();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jLabel10.setText("Server : ");
        jTextField66.setText("localhost");
        jLabel20.setText("Port : ");
        jTextField33.setText("13000");

        jButton55.setText("Connect");
        jButton55.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                connectAction(evt);
            }
        });

        jTextField24.setText("Donald");
        jTextField24.setEnabled(false);

        jLabel33.setText("Password :");
        jLabel40.setText("Username :");

        jButton88.setText("SignOff");
        jButton88.setEnabled(false);
        jButton88.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                signOffAction(evt);
            }
        });

        jPasswordField74.setText("password");
        jPasswordField74.setEnabled(false);

        jTextArea333.setColumns(20);
        jTextArea333.setFont(new Font("Consolas", 0, 12));  
        jTextArea333.setRows(5);
        jScrollPane1.setViewportView(jTextArea333);

        jList100.setModel((model = new DefaultListModel()));
        jScrollPane2.setViewportView(jList100);

        jButton44.setText("Send Message ");
        jButton44.setEnabled(false);
        jButton44.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                sendMessageAction(evt);
            }
        });
        
        jTextField6.setText("Type your message here");

        jButton20.setText("Login");
        jButton20.setEnabled(false);
        jButton20.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                loginAction(evt);
            }
        });
        
        javax.swing.GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2)
                    .addComponent(jSeparator1, GroupLayout.Alignment.TRAILING)
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1)
                        )
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGap(18, 18, 18)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        )
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField6)  //blank message textfield 
                        .addGap(18, 18, 18)
                        .addComponent(jButton44, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE) //send message button
                         )   
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10)  //server
                            .addComponent(jLabel40)  //username
                            )   
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField24)   
                                    .addComponent(jTextField66)   
                                    )
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel20)  //port :
                                    .addComponent(jLabel33)  //password
                                    )
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField33)  //13000
                                    .addComponent(jPasswordField74)   
                                    )
                                   )   
                              )
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false) 
                            .addComponent(jButton55, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE) 
                            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING) 
                                     .addComponent(jButton20, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE) //login button
                                    )
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton88, GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)  //signup button   
                                    )))
                             )
                        )
                .addContainerGap())
        );
        
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    ) 
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE) 
                )   
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton44) 
                    .addComponent(jTextField6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                )       
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jTextField66, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(jTextField33, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton55)
                  )   
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField24, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33)  
                    .addComponent(jLabel40)
                    .addComponent(jButton88)
                    .addComponent(jPasswordField74, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton20)
                    )   
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE, false)
                  )
                .addContainerGap())
        );
        pack();
    } 
    
    //connect
    private void connectAction(ActionEvent evt){ 
        serverField = jTextField66.getText(); 
        portAddress = Integer.parseInt(jTextField33.getText());
        if(!serverField.isEmpty() && !jTextField33.getText().isEmpty()){
            try{
                s_client = new SocketClient(this);
                socketThread = new Thread(s_client);
                socketThread.start();  
                 s_client.send(new Message("connect", "connectUser", "connectContent", "SERVER"));
            }catch(Exception ex){
                jTextArea333.append("\n"+df.format(System.currentTimeMillis())+" Application says : Server not found\n");
            }
        }
    }
    
    
    private void sendMessageAction(ActionEvent evt) { 
        String message = jTextField6.getText();
        String targ = jList100.getSelectedValue().toString();
        
        if(!message.isEmpty() && !targ.isEmpty()){
            jTextField6.setText("");
          s_client.send(new Message("message", user, message, targ));
        }
    }
 
    private void signOffAction(ActionEvent evt) { 
        user = jTextField24.getText();
        password = jPasswordField74.getText();
        if(!user.isEmpty() && !password.isEmpty()){
            s_client.send(new Message("signoff", user, "remove", "SERVER"));
        }
        jButton88.setEnabled(false);
    } 
    
    private void loginAction(ActionEvent evt) { 
        user = jTextField24.getText();
        password = jPasswordField74.getText();
        if(!user.isEmpty() && !password.isEmpty()){
            s_client.send(new Message("loginClient", user, password, "SERVER"));
        }
    }
    public boolean isWin32(){
        return System.getProperty("os.name").startsWith("Windows");
    }
    
}
