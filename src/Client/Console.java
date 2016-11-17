package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

/**
 * Created by Administrator on 14/11/2559.
 */
public class Console extends JFrame {
    JTextField console;
    JLabel consoleLabel;
    JTextField guided;
    JButton sent;
    JButton stop;
    InetAddress host;
    JLabel ipAddress;
    private String number, ipAddress1, portValue, tValue, sentence;

    public Console() throws UnknownHostException {
        setLayout(null);
        setBounds(100, 75, 400, 200);
        JFrame jframe = new JFrame("Client CMD");
        jframe.setTitle("Socket Programming by Kitsada Tangpoolponsavatdi");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        //console label
        consoleLabel = new JLabel("Console:");
        consoleLabel.setBounds(5, -5, 200, 150);
        consoleLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        consoleLabel.setForeground(Color.RED);
        add(consoleLabel);
        //console textfield
        console = new JTextField("");
        console.setBounds(120, 50, 200, 40);
        console.setFont(new Font("Tahoma", Font.PLAIN, 12));
        console.setForeground(Color.BLACK);
        console.setBackground(Color.LIGHT_GRAY);
        add(console);
        sent = new JButton("Sent");
        sent.setBounds(120, 100, 80, 40);
        sent.setFont(new Font("Tahoma", Font.PLAIN, 12));
        sent.requestFocus();
        add(sent);
        sent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    onSubmitClick(e);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        //Stop Button
        stop = new JButton("Back");
        stop.setBounds(220, 100, 80, 40);
        stop.setFont(new Font("Tahoma", Font.PLAIN, 12));
        stop.requestFocus();
        add(stop);
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    onClickExit(e);
                } catch (Exception b) {
                    b.printStackTrace();
                }

            }
        });
        host = InetAddress.getLocalHost();
        guided = new JTextField(String.valueOf(host));
        guided.setForeground(Color.BLACK);
        guided.setBackground(Color.LIGHT_GRAY);
        guided.setBounds(120, 5, 250, 40);
        guided.setFont(new Font("Tahoma", Font.PLAIN, 15));
        add(guided);
        //Ip Address Label
        ipAddress = new JLabel("Your IPV4 is:");
        ipAddress.setForeground(Color.RED);
        ipAddress.setBounds(5, -50, 200, 150);
        ipAddress.setFont(new Font("Tahoma", Font.PLAIN, 19));
        add(ipAddress);
        //Starting CMD
        System.out.println("******************************Welcome to MainClientCmd MainClient Side*************************************************");
        System.out.println("******************************Create By Kitsada Tangpoolponsavatdi 582115003*****************************");
        System.out.println("******************************This is Guide for Using this program***************************************");
        System.out.println("•\t-x <number>, where number is a 32-bit unsigned integer \n" +
                "•\t-t udp or tcp \n" +
                "o\tfor tcp,  the client opens a TCP connection to the ServerUI \n" +
                "o\tfor udp, the client sends the data to the ServerUI using UDP \n" +
                "•\t-s host name of the ServerUI (ex: dmx.cs.colostate.edu) \n" +
                "•\t-p the port being used by the ServerUI \n");


    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Console console = new Console();
                    console.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void onSubmitClick(ActionEvent e) throws Exception {
        String[] splitText = console.getText().split(" ");

        for (int i = 0; i < splitText.length; i++) {

            if (splitText[i].charAt(0) == '-') {
                String cut = splitText[i].substring(1);
                splitText[i] = cut;

            }
        }
        int counter = 0;
        int switchX = 0;
        int switchT = 0;
        int switchS = 0;
        int switchP = 0;

        for (int j = 0; j < splitText.length; j++) {
            switch (splitText[j].charAt(0)) {
                case 'x':
                    this.number = splitText[j].substring(1);
                    switchX++;
                    counter++;
                    break;
                case 't':
                    this.tValue = splitText[j].substring(1);
                    switchT++;
                    counter++;
                    break;
                case 's':
                    this.ipAddress1 = splitText[j].substring(1);
                    switchS++;
                    counter++;
                    break;
                case 'p':
                    this.portValue = splitText[j].substring(1);
                    if(!portValue.matches("\\d{1,5}")){
                    JOptionPane.showMessageDialog(null, "Please input port Number!", "Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                    }
                    switchP++;
                    counter++;
                    break;
            }
        }
        if (switchS != 1) {
            JOptionPane.showMessageDialog(null, "Please input -s", "Error", JOptionPane.ERROR_MESSAGE);
        }
        if (switchP != 1) {
            JOptionPane.showMessageDialog(null, "Please input -p", "Error", JOptionPane.ERROR_MESSAGE);
        }
        if (switchT != 1) {
            JOptionPane.showMessageDialog(null, "Please input -t", "Error", JOptionPane.ERROR_MESSAGE);
        }
        if (switchX != 1) {
            JOptionPane.showMessageDialog(null, "Please input -x", "Error", JOptionPane.ERROR_MESSAGE);
        }
        while (counter == 4) {
            if (this.tValue.equalsIgnoreCase("tcp")) {
                tcpClient(Integer.parseInt(this.portValue));
            } else if (this.tValue.equalsIgnoreCase("udp")) {
                udpClient(Integer.parseInt(this.portValue));
            } else {
                JOptionPane.showMessageDialog(null, "Please Select TCP/UDP", "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
            if (this.tValue.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please Select TCP/UDP", "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        }
    }


    private void tcpClient(int port) throws Exception {
        if (!ipAddress1.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) {
            JOptionPane.showMessageDialog(null, "Please input a Valid IP Address!", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        if (ipAddress1.isEmpty()) {
            JOptionPane.showMessageDialog(null, "The ip address is empty. Program will now exit.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        if (number.isEmpty()) {
            JOptionPane.showMessageDialog(null, "The Message is empty. Program will now exit.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        if (Integer.parseInt(number) < 0) {
            JOptionPane.showMessageDialog(null, "Number must be a 32-bit unsigned integer (can't be negative). Program will now exit.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        if (Integer.parseInt(portValue) < 0 || Integer.parseInt(portValue) > 65535) {
            JOptionPane.showMessageDialog(null, "Please input a Port Number again!", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        System.out.println("The number is : " + this.number + " from client " + this.ipAddress1 + " on port " + this.portValue);

        try {
            String modifiedSentence;
            Socket clientSocket = new Socket(ipAddress1, Integer.parseInt(portValue));
            PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outToServer.println(number + "-" + ipAddress1 + "-" + portValue + '\n');
            clientSocket.setSoTimeout(3000);
            System.out.println("Sent number to ServerUI " + ipAddress1 + ":" + portValue + " via TCP");
            JOptionPane.showMessageDialog(null, "Success to forward your Massage", "Successful", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("REPLIED FROM TCP SERVER: Successful");
            modifiedSentence = inFromServer.readLine();
//            System.out.println("REPLIED FROM TCP SERVER: " + modifiedSentence);
            clientSocket.close();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Fail to forward your Massage", "Successful", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("REPLIED FROM TCP SERVER: Fail");
        }
    }

    private void udpClient(int port) throws Exception {

        if (!ipAddress1.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) {
            JOptionPane.showMessageDialog(null, "Please input a Valid IP Address!");
            System.exit(0);
        }
        if (ipAddress1.isEmpty()) {
            JOptionPane.showMessageDialog(null, "The ip address is empty. Program will now exit.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        if (number.isEmpty()) {
            JOptionPane.showMessageDialog(null, "The Message is empty. Program will now exit.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        if (Integer.parseInt(number) < 0) {
            JOptionPane.showMessageDialog(null, "Number must be a 32-bit unsigned integer (can't be negative). Program will now exit.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        if (Integer.parseInt(portValue) < 0 || Integer.parseInt(portValue) > 65535) {
            JOptionPane.showMessageDialog(null, "Please input a Port Number again!", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        System.out.println("The number is : " + this.number + " from client " + this.ipAddress1 + " on port " + this.portValue);

        try {
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName(ipAddress1);
            byte[] sendData = new byte[1024];
            byte[] receiveData = new byte[1024];
            String sentence = number + "-" + ipAddress1 + "-" + portValue;
            sendData = sentence.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, Integer.parseInt(portValue));
            clientSocket.send(sendPacket);
            clientSocket.setSoTimeout(3000);
            System.out.println("Sent number to ServerUI " + ipAddress1 + ":" + portValue + " via UDP");
            JOptionPane.showMessageDialog(null, "Success to forward your Massage", "Successful", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("REPLIED FROM UDP SERVER: Successful");
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);
            String modifiedSentence = new String(receivePacket.getData());

//        System.out.println("REPLIED FROM UDP SERVER:" + modifiedSentence);
            clientSocket.close();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Fail to forward your Massage", "Successful", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("REPLIED FROM UDP SERVER: Fail");
        }
    }

    private void onClickExit(ActionEvent e) throws UnknownHostException {
        MainClient client = new MainClient();

        client.setVisible(true);
        console.setVisible(false);
    }

}
