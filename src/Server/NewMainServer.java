package Server;

import java.awt.*;

/**
 * Created by Administrator on 11/11/2559.
 */
public class NewMainServer {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    ServerUI mainServer = new ServerUI();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
