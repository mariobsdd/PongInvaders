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

public class Racket implements Commons {
    private PongInvader game;
    private int left, right;
    private int x, xa;
    private int y;
    
    /*
        x,y -> posiciones iniciales del jugador
        
    */

    public Racket(PongInvader game, int left, int right, int x, int y) {
        this.game = game;
        this.x = x;
        //y = game.getHeight() / 2;
        this.y = y;
        this.left = left;
        this.right = right;
    }

    public void update() {
        if (x > 0 && x < game.getWidth() - DEFENDER_WIDTH)
            x += xa;
        else if (x == 0)
            x++;
        else if (x == game.getWidth() - DEFENDER_WIDTH)
            x--;
    }

    public void pressed(int keyCode) {
        if (keyCode == left)
            xa = -1;
        else if (keyCode == right)
            xa = 1;
    }

    public void released(int keyCode) {
        if (keyCode == left || keyCode == right)
            xa = 0;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, DEFENDER_WIDTH, DEFENDER_HEIGHT);
    }

    public void paint(Graphics g) {
        g.fillRect(x, y, DEFENDER_WIDTH, DEFENDER_HEIGHT);
        //g.setColor(Color.red);
    }
}