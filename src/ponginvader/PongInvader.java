/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ponginvader;

/**
 *
 * @author mariobsdd
 */
import java.awt.Color;
import java.net.Socket;
import javax.swing.JFrame;

public class PongInvader extends JFrame implements Commons{
    private PongInvaderPanel panel;

    public PongInvader(Socket socket, int playerNumber, String ip) {
        //Default Settings
        setSize(BOARD_WIDTH, BOARD_HEIGHT);
        setTitle("Space-Pong Invader");
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        panel = new PongInvaderPanel(this, socket, playerNumber, ip);
        add(panel);
    }

    public PongInvaderPanel getPanel() {
        return panel;
    }

    public static void main(String[] args) {
        new PongInvader(null,0,null);
    }
}