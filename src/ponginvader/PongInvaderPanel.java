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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class PongInvaderPanel extends JPanel implements ActionListener, KeyListener, Commons {
    private PongInvader game;
    private Ball ball;
    private Racket player1, player2, player3, player4;
    private int score1, score2;

    public PongInvaderPanel(PongInvader game) {
        setBackground(Color.BLACK);
        this.game = game;
        //a que juego pertenece
        ball = new Ball(game);
        //for each player: juego al que pertenecen, move up, move down, posX
        player1 = new Racket(game, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, game.getWidth()/2, 100);
        player2 = new Racket(game, KeyEvent.VK_A, KeyEvent.VK_D, game.getWidth()/2 ,game.getHeight()-100);
        Timer timer = new Timer(5, this);
        timer.start();
        addKeyListener(this);
        setFocusable(true);
    }

    public Racket getPlayer(int playerNo) {
        if (playerNo == 1)
            return player1;
        //else if(playerNo == 2)
        else
            return player2;
    }

    public void increaseScore(int playerNo) {
        if (playerNo == 1)
            score1++;
        else
            score2++;
    }

    public int getScore(int playerNo) {
        if (playerNo == 1)
            return score1;
        else
            return score2;
    }

    private void update() {
        ball.update();
        player1.update();
        player2.update();
    }

    public void actionPerformed(ActionEvent e) {
        update();
        repaint();
    }

    public void keyPressed(KeyEvent e) {
        player1.pressed(e.getKeyCode());
        player2.pressed(e.getKeyCode());
    }

    public void keyReleased(KeyEvent e) {
        player1.released(e.getKeyCode());
        player2.released(e.getKeyCode());
    }

    

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawString(game.getPanel().getScore(1) + " : " + game.getPanel().getScore(2), game.getWidth() / 2, 10);
        g.setColor(Color.RED);
        ball.paint(g);
        player1.paint(g);
        player2.paint(g);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        ;
    }
}