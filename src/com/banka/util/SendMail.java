/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.banka.util;

import java.security.Security;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author hakan
 */
public class SendMail {
    private String to;
    private String subject;
    private String text;

    public SendMail(String to, String subject, String text){
            this.to = to;
            this.subject = subject;
            this.text = text;
    }

    public boolean send() throws NoSuchProviderException, AddressException{
        try
        {
                Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
                Properties props=new Properties();
                props.setProperty("mail.transport.protocol","smtp");
                props.setProperty("mail.host","smtp.gmail.com");
                props.put("mail.smtp.auth","true");
                props.put("mail.smtp.port","465");
                props.put("mail.debug","true");
                props.put("mail.smtp.socketFactory.port","465");
                props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
                props.put("mail.smtp.socketFactory.fallback","false");
                Session session=Session.getDefaultInstance(props,new GJMailAuthenticator());
                session.setDebug(true);
                Transport transport=session.getTransport();
                InternetAddress addressFrom=new InternetAddress("bbmbanka@gmail.com");
                MimeMessage message=new MimeMessage(session);
                message.setSender(addressFrom);
                message.setSubject(subject);
                message.setContent(text,"text/html");
                InternetAddress addressTo=new InternetAddress(to);
                message.setRecipient(Message.RecipientType.TO,addressTo);
                transport.connect();
                Transport.send(message);
                transport.close();
                System.out.println("DONE");
                return true;
        }
        catch(Exception e)
        {
                e.printStackTrace();
                return false;
        }
    }
}//end of SendMail class

class GJMailAuthenticator extends javax.mail.Authenticator{
    protected PasswordAuthentication getPasswordAuthentication()
    {
            return new PasswordAuthentication("bbmbanka@gmail.com","bankasifresi");

    }
}//end of mail authenticator class
