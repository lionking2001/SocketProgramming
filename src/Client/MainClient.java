package Client;

import Server.ServerUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

/**
 * Created by Administrator on 5/11/2559.
 */
public class MainClient extends JFrame {
    JTextField number;
    JTextField ipAddress;
    JTextField port;
    JTextField message;
    JComboBox tcpUdp;
    JLabel tcpudpLabel;
    JLabel ipaddressLabel;
    JLabel portLabel;
    JLabel numberLabel;
    JTextField ownIPaddress;
    JLabel ipAddress1;
    JLabel openConsole;
    JLabel openServer1;
    JButton openServer;
    JButton sent;
    JButton stop;
    JButton console;

    InetAddress host;


    public MainClient() throws UnknownHostException {
        String[] TCPUDP = {
                "TCP", "UDP"
        };

        System.out.println("******************************Welcome to MainClientCmd MainClient Side*************************************************");
        System.out.println("******************************Create By Kitsada Tangpoolponsavatdi 582115003*****************************");
        System.out.println("******************************This is Guide for Using this program***************************************");
        System.out.println("•\t-x <number>, where number is a 32-bit unsigned integer \n" +
                "•\t-t udp or tcp \n" +
                "o\tfor tcp,  the client opens a TCP connection to the ServerUI \n" +
                "o\tfor udp, the client sends the data to the ServerUI using UDP \n" +
                "•\t-s host name of the ServerUI (ex: dmx.cs.colostate.edu) \n" +
                "•\t-p the port being used by the ServerUI \n");
        setLayout(null);
        setBounds(150, 150, 400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JFrame jframe = new JFrame("Client");
        jframe.setTitle("Socket Programming by Kitsada Tangpoolponsavatdi");
        number = new JTextField("Please input Number");
        ipAddress = new JTextField("Please input IP-Address");
        port = new JTextField("Please input PortNumber");
        tcpudpLabel = new JLabel("PROTOCOL:");
        tcpudpLabel.setForeground(Color.RED);
        ipaddressLabel = new JLabel("IP ADDRESS:");
        ipaddressLabel.setForeground(Color.RED);
        portLabel = new JLabel("PORT NUMBER:");
        portLabel.setForeground(Color.RED);
        numberLabel = new JLabel("MESSAGE:");
        numberLabel.setForeground(Color.RED);
        tcpUdp = new JComboBox(TCPUDP);
        tcpUdp.setBackground(Color.LIGHT_GRAY);
        setTitle("MainClient Side By Kitsada Tangpoolponsavatdi 582115003");
        //ipAddress Position
        ipAddress.setText("127.0.0.1");
        ipAddress.setBounds(110, 20, 200, 30);
        ipAddress.setFont(new Font("Tahoma", Font.PLAIN, 15));
        ipAddress.setBackground(Color.LIGHT_GRAY);
        add(ipAddress);
        ipAddress.getText();
        //ipAddressLabel set Position
        ipaddressLabel.setBounds(20, 20, 200, 30);
        ipaddressLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        add(ipaddressLabel);
        //setNUmber Position
        number.setText("123");
        number.setBounds(110, 70, 200, 30);
        number.setBackground(Color.LIGHT_GRAY);
        number.setFont(new Font("Tahoma", Font.PLAIN, 15));
        add(number);
        number.getText();
        //SetNUmberLabel Position
        numberLabel.setBounds(35, 70, 200, 30);
        numberLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        add(numberLabel);
        //SetPort
        port.setText("8080");
        port.setBounds(110, 120, 200, 30);
        port.setFont(new Font("Tahoma", Font.PLAIN, 15));
        port.setBackground(Color.LIGHT_GRAY);
        add(port);
        port.getText();
        //SetPortLabel
        portLabel.setBounds(17, 120, 200, 30);
        port.setFont(new Font("Tahoma", Font.PLAIN, 15));
        add(portLabel);
        //SettcpandUDPPosition
        tcpUdp.setBounds(110, 170, 200, 30);
        add(tcpUdp);
        tcpUdp.getSelectedItem();
        //SetProtocolLabel
        tcpudpLabel.setBounds(25, 170, 200, 30);
        tcpudpLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        add(tcpudpLabel);

        sent = new JButton("Sent");
        sent.setBounds(110, 230, 80, 30);
        add(sent);
        sent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    onClickSent(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        });

        stop = new JButton("Stop");
        stop.setBounds(200, 230, 80, 30);
        add(stop);
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onClickStop(e);
            }
        });
        openServer1 = new JLabel("SERVER:");
        openServer1.setFont(new Font("Tahoma", Font.PLAIN, 15));
        openServer1.setBounds(220, 330, 200, 30);
        openServer1.setForeground(Color.RED);
        add(openServer1);
        openServer = new JButton("SERVER");
        openServer.setBounds(280, 330, 100, 30);
        add(openServer);
        openServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ServerUI mainServer = new ServerUI();
            }
        });

        openConsole = new JLabel("CONSOLE:");
        openConsole.setFont(new Font("Tahoma", Font.PLAIN, 15));
        openConsole.setBounds(20, 330, 200, 30);
        openConsole.setForeground(Color.RED);
        add(openConsole);
        console = new JButton("CONSOLE");
        console.setBounds(100, 330, 100, 30);
        add(console);
        console.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Console console = new Console();
                    console.setVisible(true);
                } catch (UnknownHostException e1) {
                    e1.printStackTrace();
                }
            }
        });


        host = InetAddress.getLocalHost();
        ownIPaddress = new JTextField(String.valueOf(host));
        ownIPaddress.setBounds(110, 280, 240, 30);
        ownIPaddress.setForeground(Color.BLACK);
        ownIPaddress.setBackground(Color.LIGHT_GRAY);
        ownIPaddress.setFont(new Font("Tahoma", Font.PLAIN, 15));
        add(ownIPaddress);

        ipAddress1 = new JLabel("Your IPV4 IS:");
        ipAddress1.setForeground(Color.RED);
        ipAddress1.setBounds(10, 280, 200, 30);
        ipAddress1.setFont(new Font("Tahoma", Font.PLAIN, 15));
        add(ipAddress1);

        setResizable(false);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    MainClient client = new MainClient();
                    client.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void onClickStop(ActionEvent e) {
        System.exit(0);
    }

    private void onClickSent(ActionEvent e) throws IOException {
        if (!ipValue().matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) {
            JOptionPane.showMessageDialog(null, "Please input a Valid IP Address!");
            System.exit(0);
        }
        if (ipValue().isEmpty()) {
            JOptionPane.showMessageDialog(null, "The ip address is empty. Program will now exit.", "Error", JOptionPane.ERROR_MESSAGE);

        }
        if (PortValue().isEmpty()) {
            JOptionPane.showMessageDialog(null, "The ServerUI port number is empty. Program will now exit.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        if (numberValue().isEmpty()) {
            JOptionPane.showMessageDialog(null, "The Message is empty. Program will now exit.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        try {
            if (Integer.parseInt(number.getText()) < 0) {

                JOptionPane.showMessageDialog(null, "Number must be a 32-bit unsigned integer (can't be negative). Program will now exit.", "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }

        } catch (Exception c) {
            JOptionPane.showMessageDialog(null, "Please enter 32bit of Interger", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        try {
            if (Integer.parseInt(port.getText()) < 0 || Integer.parseInt(port.getText()) > 65535) {

                JOptionPane.showMessageDialog(null, "Port out of range. Program will now exit.", "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }

        } catch (Exception a) {
            JOptionPane.showMessageDialog(null, "Please input only Interger", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        if (PortValue().length() <= 3) {
            JOptionPane.showMessageDialog(null, "Port value cannot be less than 3 digit!", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        while (IPEmpty().equals(false)) {
            try {
                if (tcpUdp.getSelectedItem().equals("TCP")) {
                    String modifiedSentence;
                    Socket clientSocket = new Socket(ipValue(), Integer.parseInt(PortValue()));
                    PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream(), true);
                    BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    outToServer.println(numberValue() + "-" + ipValue() + "-" + PortValue() + '\n');
                    clientSocket.setSoTimeout(3000);
                    System.out.println("Sent number to ServerUI " + ipValue() + ":" + PortValue() + " via TCP");
                    JOptionPane.showMessageDialog(null, "Success to forward your Massage", "Successful", JOptionPane.INFORMATION_MESSAGE);
                    modifiedSentence = inFromServer.readLine();
                    System.out.println("REPLIED FROM TCP SERVER: Successful");
//                   System.out.println("REPLIED FROM TCP SERVER: " + modifiedSentence);
                    clientSocket.close();
                    System.exit(0);

                }
            } catch (Exception i) {
                JOptionPane.showMessageDialog(null, "Fail to forward your Massage", "Fail", JOptionPane.ERROR_MESSAGE);
                System.out.println("REPLIED FROM TCP SERVER: Fail");

            }
            try {
                if (tcpUdp.getSelectedItem().equals("UDP")) {
                    DatagramSocket clientSocket = new DatagramSocket();
                    InetAddress IPAddress = InetAddress.getByName(ipValue());
                    byte[] sendData = new byte[1024];
                    byte[] receiveData = new byte[1024];
                    String sentence = numberValue() + "-" + ipValue() + "-" + PortValue();
                    sendData = sentence.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, Integer.parseInt(PortValue()));
                    clientSocket.send(sendPacket);
                    clientSocket.setSoTimeout(3000);
                    System.out.println("Sent number to ServerUI " + ipValue() + ":" + PortValue() + " via UDP");
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    clientSocket.receive(receivePacket);
                    String modifiedSentence = new String(receivePacket.getData());
                    JOptionPane.showMessageDialog(null, "Success to forward your Massage", "Successful", JOptionPane.INFORMATION_MESSAGE);
                    System.out.println("REPLIED FROM UDP SERVER: Successful");
//                   System.out.println("REPLIED FROM UDP SERVER:" + modifiedSentence);
                    clientSocket.close();
                }
            } catch (Exception c) {
                JOptionPane.showMessageDialog(null, "Fail to forward your Massage", "Fail", JOptionPane.ERROR_MESSAGE);
                System.out.println("REPLIED FROM UDP SERVER: Fail");
            }
            break;
        }
    }

    protected String ipValue() {
        return ipAddress.getText();
    }

    protected String PortValue() {
        return port.getText();
    }

    protected String numberValue() {
        return number.getText();
    }

    private Boolean IPEmpty() {
        if (ipValue() == null || ipValue().trim().equals("")) {
            return true;
        } else {
            return false;
        }
    }

    private Boolean PortEmpty() {
        if (PortValue() == null || PortValue().trim().equals(" ")) {
            return true;
        } else {
            return false;
        }
    }


    private Boolean NumberEmpty() {
        if (numberValue() == null || numberValue().trim().equals("")) {
            return true;
        } else {
            return false;
        }
    }
}
