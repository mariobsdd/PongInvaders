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
    private static final int WIDTH = 15, HEIGHT = 15;
    private PongInvader game;
    private int x, y, xa = 1, ya = 1;
    private int iteracion = 0;
    private int iteracion2 = 0;
    /*
        x, y - > posiciones iniciales de la pelota
    */
    
    public Ball(PongInvader game,int x, int y) {
        this.game = game;
        this.x = x;
        this.y = y;
    }

    public int getIteracion() {
        return iteracion;
    }

    public void setIteracion(int iteracion) {
        this.iteracion = iteracion;
    }

    public int getIteracion2() {
        return iteracion2;
    }

    public void setIteracion2(int iteracion2) {
        this.iteracion2 = iteracion2;
    }
    
    public void update(){
        x += xa;
        y += ya;
        boolean exit = false;
        
        if (y < 0) { //si se sale del tablero del lado de arriba, incrementa el marcador al player2
            game.getPanel().increaseScore(2);
            y = game.getHeight() / 2;
            ya = -ya;
            System.out.println("incremento marcador P1");
        }
        else if (y > game.getHeight() - HEIGHT - 7) { 
            System.out.println("Incremento marcador P2");
            game.getPanel().increaseScore(1);
            y = game.getHeight() / 2;
            ya = -ya;
        }
        else if (x < 0 || x > game.getWidth() - WIDTH - 29)
            xa = -xa;
        
        
        //Finalizacion del juego
        if (game.getPanel().getScore(1) == 10){
            JOptionPane.showMessageDialog(null, "Player 1 wins", "Pong", JOptionPane.PLAIN_MESSAGE);
            exit = true;
        }
        else if (game.getPanel().getScore(2) == 10){
            JOptionPane.showMessageDialog(null, "Player 2 wins", "Pong", JOptionPane.PLAIN_MESSAGE);
            exit = true;
        }
        if(!exit)
            checkCollision();
        //posicion de la pelota
        //System.out.println("X: "+x + ", Y:"+y);
    }

    public void checkCollision() {
        if (game.getPanel().getDefender(1).getBounds().intersects(getBounds())){
            ya = -ya;
            System.out.println("la bola choca jugador1");
            iteracion +=1;
        }
        else if(game.getPanel().getDefender(2).getBounds().intersects(getBounds())){
            ya = -ya;
            System.out.println("la bola choca jugador2");
            iteracion2 +=1;
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }

    public void paint(Graphics g) {
        g.fillRect(x, y, WIDTH, HEIGHT);
        g.setColor(Color.WHITE);
    }
}