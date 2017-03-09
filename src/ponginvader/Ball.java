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
    private int x, y, xa, ya = 1;
    private int iteracion = 0;
    private int iteracion2 = 0;
    private int otrait = 0;
    private boolean exit;
    private boolean player2;
    /*
        x, y - > posiciones iniciales de la pelota
    */
    
    public Ball(PongInvader game,int x, int y,int xa,boolean player2) {
        this.game = game;
        this.exit = false;
        this.xa = xa;
        this.player2 = player2;
        if(player2){
            this.y = game.getHeight()-50;
            this.x = game.getWidth()/2;
        }
        else{
            this.x = x;
            this.y = y;
        }
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

    public int getOtrait() {
        return otrait;
    }

    public void setOtrait(int otrait) {
        this.otrait = otrait;
    }
    
    public void update(){
        if(!exit){
            if(player2){
                x -= xa;
                y -= ya;
            }
            else{
                x += xa;
                y += ya;
            }
        }
        
        if (y < 0 && !exit) { //si se sale del tablero del lado de arriba, incrementa el marcador al player2
            game.getPanel().increaseScore(2);
            y = game.getHeight() / 2;
            ya = -ya;
            System.out.println("incremento marcador P1");
            y = 0; x = 0;
            exit= true;
        }
        else if (y > game.getHeight() - HEIGHT - 7 && !exit) { 
            System.out.println("Incremento marcador P2");
            game.getPanel().increaseScore(1);
            y = game.getHeight() / 2;
            ya = -ya;
            y = 0; x = 0;
            exit = true;
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
        else{
            otrait = 0;
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