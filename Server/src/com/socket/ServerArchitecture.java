package com.socket;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import com.socket.*;

public class ServerArchitecture extends JFrame{
  
    private JScrollPane jScrollPane11;
    public JTextArea jTextArea11;
    private JTextField jTextField33;
    private JButton jButton11;
    private JButton jButton22;
    private JLabel jLabel33;
    public Server_Socket server;
    public Thread serverThread;
    public String filePath = "";
    public JFileChooser fileChooser;
    public ServerArchitecture(){
        initComponents();     
        jTextField33.setEditable(false);
        jTextField33.setBackground(Color.WHITE);
        
        fileChooser = new JFileChooser();
        jTextArea11.setEditable(false);
    }

    private void initComponents() {

        jButton11 = new JButton();
        jButton22 = new JButton();
        jLabel33 = new JLabel();
        jTextArea11 = new JTextArea();
        jTextField33 = new JTextField();
        jScrollPane11 = new JScrollPane();

        jButton11.setText("Start Server");
        jButton11.setEnabled(false);
        jButton11.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                startServerAct(evt);
            }
        });

        jTextArea11.setColumns(20);
        jTextArea11.setFont(new Font("Consolas", 0, 12));  
        jTextArea11.setRows(5);
        jScrollPane11.setViewportView(jTextArea11);

        jButton22.setText("Browse...");
        jButton22.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });
        
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Server Application");
        
        jLabel33.setText("Load Users : ");
                
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
         layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane11) 
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel33)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField33, GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton22, GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton11)))
                .addContainerGap()));
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)             
                      .addComponent(jScrollPane11, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                     ) 
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField33, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33)
                    .addComponent(jButton22)
                    .addComponent(jButton11)
                    )
                )
        );
        pack();
    }
    
    public boolean isWin32(){
        return System.getProperty("os.name").startsWith("Windows");
    }
    
    
    public void startServer(int port){
        if(server != null){ 
          server.stop(); 
        }
        server = new Server_Socket(this, port);
    }
    
    private void jButton22ActionPerformed(ActionEvent evt){ 
      fileChooser.showDialog(this, "Select");
        File file = fileChooser.getSelectedFile();
        if(file != null){
            filePath = file.getPath();
            if(this.isWin32()){ filePath = filePath.replace("\\", "/"); }
            jTextField33.setText(filePath);
            jButton11.setEnabled(true);
        }
    }
    
    private void startServerAct(ActionEvent evt) { 
        jButton22.setEnabled(false);
        jButton11.setEnabled(false); 
        server = new Server_Socket(this);
    } 
    
}
