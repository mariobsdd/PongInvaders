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

import javax.swing.JOptionPane;

public class Ball implements Commons{
    private static final int WIDTH = 30, HEIGHT = 30;
    private PongInvader game;
    private int x, y, xa = 1, ya = 1;
    /*
        x, y - > posiciones iniciales de la pelota
    */

    public Ball(PongInvader game) {
        this.game = game;
        x = game.getWidth() / 2;
        y = game.getHeight() / 2;
    }

    public void update() {
        x += xa;
        y += ya;
        if (y < 0) { //si se sale del tablero del lado izquierdo, incrementa el marcador al player2
            game.getPanel().increaseScore(2);
            y = game.getHeight() / 2;
            ya = -ya;
            System.out.println("incremento marcador P1");
        }
        else if (y > game.getHeight() - HEIGHT - 7) {
            game.getPanel().increaseScore(1);
           y = game.getHeight() / 2;
            ya = -ya;
        }
        else if (x < 0 || x > game.getWidth() - WIDTH - 29)
            xa = -xa;
        if (game.getPanel().getScore(1) == 10)
            JOptionPane.showMessageDialog(null, "Player 1 wins", "Pong", JOptionPane.PLAIN_MESSAGE);
        else if (game.getPanel().getScore(2) == 10)
            JOptionPane.showMessageDialog(null, "Player 2 wins", "Pong", JOptionPane.PLAIN_MESSAGE);
        checkCollision();
        //posicion de la pelota
        System.out.println("X: "+x + ", Y:"+y);
    }

    public void checkCollision() {
        if (game.getPanel().getPlayer(1).getBounds().intersects(getBounds()) || game.getPanel().getPlayer(2).getBounds().intersects(getBounds()))
            ya = -ya;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }

    public void paint(Graphics g) {
        g.fillRect(x, y, WIDTH, HEIGHT);
        g.setColor(Color.WHITE);
    }
}