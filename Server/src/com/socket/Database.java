package com.socket;

import java.io.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;

public class Database {
    
    public String userPath;
    
    public Database(String userPath){
        this.userPath = userPath;
    }
    public Document doBuild(File xml){
      Document doc=null;
      try{
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbr = dbf.newDocumentBuilder();
            doc = dbr.parse(xml);
            doc.getDocumentElement().normalize();
          }catch (Exception e){
        } 
            return doc;
    }
    
    public Boolean doSearch(NodeList nnl, String uname){
      
      for (int t = 0; t < nnl.getLength(); t++) {
                Node m = nnl.item(t);
                if (m.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) m;
                    if(getTagValue("u_name", eElement).equals(uname)){
                        return true;
                    }
                }
            }
            return false;
    }
    
    public boolean verifyUser(String uname){
        try{
            File xmlFile = new File(userPath);
            Document doc = doBuild(xmlFile);
            NodeList nodeList = doc.getElementsByTagName("user");
            if(doSearch(nodeList,uname)){
              return true;
            }else{ 
              return false;
            }
        }
        catch(Exception ex){
            return false;
        }
    }
    
    public boolean verifyLogin(String uname, String pword){
        
        if(!verifyUser(uname)){ return false; }
        
        try{
            File xmlFile = new File(userPath);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbr = dbf.newDocumentBuilder();
            Document doc = dbr.parse(xmlFile);
            doc.getDocumentElement().normalize();
            
            NodeList n = doc.getElementsByTagName("user");
            
            for (int t = 0; t < n.getLength(); t++) {
                Node nn = n.item(t);
                if (nn.getNodeType() == Node.ELEMENT_NODE) {
                    Element ee = (Element) nn;
                    if(getTagValue("u_name", ee).equals(uname) && getTagValue("p_word", ee).equals(pword)){
                        return true;
                    }
                }
            }
            return false;
        }
        catch(Exception ex){
            return false;
        }
    }
    
    public static String getTagValue(String sTag, Element eElement) {
      NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
      Node nValue = (Node) nlList.item(0);
      return nValue.getNodeValue();
    }
}
