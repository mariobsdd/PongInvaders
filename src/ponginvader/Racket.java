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
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Racket implements Commons {
    private PongInvader game;
    private int left, right;
    private int x, xa;
    private int y,playerNumber;
    private String ip;
    
    /*
        x,y -> posiciones iniciales del jugador
    */

    public Racket(PongInvader game, int left, int right, int x, int y, String ip,int playerNumber) {
        this.game = game;
        this.x = x;
        //y = game.getHeight() / 2;
        this.y = y;
        this.left = left;
        this.right = right;
        this.ip = ip;
        this.playerNumber = playerNumber;
    }

    public void update() {
        if (x > 0 && x < game.getWidth() - DEFENDER_WIDTH)
            x += xa;
        else if (x == 0)
            x+=2;
        else if (x == game.getWidth() - DEFENDER_WIDTH)
            x-=2;
    }

    public void pressed(int keyCode) {
        if (keyCode == left)
            xa = -2;
        else if (keyCode == right)
            xa = 2;
        int port = 125;
        DatagramSocket clientSocket;
        try {
            clientSocket = new DatagramSocket();
            String message = "";
            //need to be changed for the number of player
            message = playerNumber+" " + x+ "\r";
            byte[] msg = message.getBytes("UTF-8");
            DatagramPacket sendPacket = new DatagramPacket(msg, msg.length, InetAddress.getByName(ip), port);
            clientSocket.send(sendPacket);
        } catch (SocketException ex) {
            Logger.getLogger(Racket.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Racket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void released(int keyCode) {
        if (keyCode == left || keyCode == right)
            xa = 0;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, DEFENDER_WIDTH, DEFENDER_HEIGHT);
    }
   
    public void setMovement(int x){
        this.x = x;
    }
    public void paint(Graphics g, int it) {
        g.fillRect(x, y, DEFENDER_WIDTH - 7*it, DEFENDER_HEIGHT);
        //g.setColor(Color.red);
    }
}