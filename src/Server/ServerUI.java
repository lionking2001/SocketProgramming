package Server;

/**
 * Created by Administrator on 11/11/2559.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 11/11/2559.
 */
public class ServerUI extends JFrame {

    JTextArea text;
    JTextField portNum;

    String capitalizedSentence;
    String portNumber;
    String serverNumber = null;
    String inFromClientMSG;
    JTextField txt;


    JComboBox protocolbox;
    JButton startServerButton, stopServerButton;

    Font font = new Font("Courier", Font.BOLD, 25);
    Runnable runserverTCP = new Runnable() {

        @Override
        public void run() {
            try {
                //create welcoming socket at port ???
                ServerSocket serverSocket = new ServerSocket(Integer.parseInt(portNum.getText()));
                while (true) {
                    //wait on welcoming socket for contact by client
                    Socket connectionSocket = serverSocket.accept();

                    text.append("Connection Success\n");
                    String Message = "";
                    BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                    //create input stream attached to socket
                    inFromClientMSG = inFromClient.readLine();
                    Pattern mgs = Pattern.compile("(.*?)-");
                    Pattern sv = Pattern.compile("-(.*?)-");
                    Pattern port = Pattern.compile("'(.*?)");
                    Matcher msgText = mgs.matcher(inFromClientMSG);
                    if (msgText.find()) {
                        Message = msgText.group(1);
                    }
                    Matcher serverText = sv.matcher(inFromClientMSG);
                    if (serverText.find()) {
                        serverNumber = serverText.group(1);
                    }
                    portNumber = inFromClientMSG.substring(inFromClientMSG.lastIndexOf('-') + 1).trim();
                    //create output stream attached to socket
                    DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                    capitalizedSentence = Message.toUpperCase() + '\n';
                    //write out line to socket
                    outToClient.writeBytes(capitalizedSentence);
                    text.append("The number is: " + Message + " from client " + serverNumber + " on port " + portNumber + "\n");
                    Thread.sleep(1000);
                }

            } catch (IOException ex) {
                Logger.getLogger(ServerUI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(ServerUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };
    Runnable runserverUDP = new Runnable() {
        @Override
        public void run() {

            try {
                System.out.println("UDP ServerUI connected!!");
                //create datagram socket at port ???
                DatagramSocket serverSocket = new DatagramSocket(Integer.parseInt(portNum.getText()));

                String Message = "";
                String portNum;
                String serverNum = null;
                while (true) {
                    byte[] receiveData = new byte[1024];
                    byte[] sendData = new byte[1024];
                    //create space for received datagram
                    DatagramPacket receivePacket
                            = new DatagramPacket(receiveData, receiveData.length);
                    //receive datagram
                    serverSocket.receive(receivePacket);

                    String sentence = new String(receivePacket.getData());
                    Pattern mgs = Pattern.compile("(.*?)-");
                    Pattern sv = Pattern.compile("-(.*?)-");
                    Pattern port = Pattern.compile("'(.*?)");
                    Matcher msgText = mgs.matcher(sentence);
                    if (msgText.find()) {
                        Message = msgText.group(1);
                    }
                    Matcher serverText = sv.matcher(sentence);
                    if (serverText.find()) {
                        serverNum = serverText.group(1);
                    }
                    portNum = sentence.substring(sentence.lastIndexOf('-') + 1).trim();
                    //get IP addr port # of sender
                    InetAddress IPAddress = receivePacket.getAddress();
                    int port1 = receivePacket.getPort();
                    String capitalizedSentence = sentence.toUpperCase();
                    sendData = capitalizedSentence.getBytes();
//                    System.out.println(sentence);
                    //create datagramt to send to client
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port1);
                    text.append("The number is: " + Message + " from client " + serverNum + " on port " + portNum + "\n");
                    //write out datagram to socket
                    serverSocket.send(sendPacket);
                }
            } catch (SocketException ex) {
                Logger.getLogger(ServerUI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ServerUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };


    public ServerUI() {
        //main panel
        setLayout(null);
        setBounds(700, 100,550, 370);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);


//           JPanel MainPanel = new JPanel();
//        MainPanel.setLayout(new BorderLayout());
//        getContentPane().add(MainPanel);


        text = new JTextArea();
        text.setBounds(20, 20, 490, 300);
        add(text);
        text.append("******Welcome to Server*******\n");
        text.append("Server is Ready to Connect!\n");
        text.append("Wait for connection...\n");

        JDialog d = new JDialog();
        d.setLayout(null);
        d.setTitle("ServerUI Potocol");
        d.setSize(400,200);
        d.setLocationRelativeTo(null);
        d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JButton btnTCP = new JButton("TCP");
        btnTCP.setBounds(80, 100, 90, 25);
        d.add(btnTCP);

        JLabel portText = new JLabel("PORT NUMBER:");
        portText.setBounds(70, 50, 90, 25);
        d.add(portText);

        portNum = new JTextField();
        portNum.setBounds(170, 50, 90, 25);
        d.add(portNum);

        btnTCP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                checkPortNum();
                d.setVisible(false);
                setVisible(true);
                setTitle("TCP ServerUI");
                new Thread(runserverTCP).start();
            }

        });

        JButton btnUDP = new JButton("UDP");
        btnUDP.setBounds(190, 100, 90, 25);
        d.add(btnUDP);
        btnUDP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkPortNum();
                d.setVisible(false);
                setTitle("UDP ServerUI");
                setVisible(true);

                new Thread(runserverUDP).start();

            }
        });

        d.setVisible(true);


    }

    public void checkPortNum() {
        if (portNum.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Port number cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        } else if (portNum.getText().matches(".*[^0-9].*")) {
            JOptionPane.showMessageDialog(null, "Invalided port number", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        } else if (portNum.getText().length() != 4) {
            JOptionPane.showMessageDialog(null, "Invalided port number", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }


    }


}
