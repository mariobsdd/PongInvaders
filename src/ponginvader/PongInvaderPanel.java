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
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;
import static ponginvader.Commons.DEFENDER_HEIGHT;
import static ponginvader.Commons.DEFENDER_WIDTH;



public class PongInvaderPanel extends JPanel implements ActionListener, KeyListener, Commons {
    private PongInvader game;
    private Ball ball;
    private Racket player1, player2;
    private Cannon stricker1, stricker2;
    private int score1, score2;

    public PongInvaderPanel(PongInvader game) {
        setBackground(Color.BLACK);
        this.game = game;
        
        //recibo:
        //angulo, pos en x, pos en y
        
        //a que juego pertenece
        ball = new Ball(game,game.getWidth()/2,game.getHeight()/2);
        //for each player: juego al que pertenecen, move up, move down, posX
        player1 = new Racket(game, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, 300, 100);
        player2 = new Racket(game, KeyEvent.VK_A, KeyEvent.VK_D, game.getWidth() - 300 ,game.getHeight()-150);
        
        stricker1 = new Cannon(game,KeyEvent.VK_C,KeyEvent.VK_V,game.getWidth()/2 - SHOOTER_WIDTH/2,-1,false);
        stricker2 = new Cannon(game,KeyEvent.VK_N,KeyEvent.VK_B, (int) (game.getWidth()/2),game.getHeight()-29,true);
        Timer timer = new Timer(5, this);
        timer.start();
        addKeyListener(this);
        setFocusable(true);
    }

    public Racket getDefender(int playerNo) {
        if (playerNo == 1)
            return player1;
        else
            return player2;
    }
    public Cannon getStricker(int playerNo) {
        if (playerNo == 1)
            return stricker1;
        else
            return stricker2;
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
        stricker1.update();
        stricker2.update();
    }

    public void actionPerformed(ActionEvent e) {
        update();
        repaint();
    }

    public void keyPressed(KeyEvent e) {
        player1.pressed(e.getKeyCode());
        player2.pressed(e.getKeyCode());
        stricker1.pressed(e.getKeyCode());
        stricker2.pressed(e.getKeyCode());
    }

    public void keyReleased(KeyEvent e) {
        player1.released(e.getKeyCode());
        player2.released(e.getKeyCode());
        stricker1.released(e.getKeyCode());
        stricker2.released(e.getKeyCode());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //g.drawString(game.getPanel().getScore(1) + " : " + game.getPanel().getScore(2), game.getWidth() / 2, 10);
        g.setColor(Color.RED);
        ball.paint(g);
        int it = ball.getIteracion();
        int it2 = ball.getIteracion2();
        player1.paint(g,it);
        player2.paint(g,it2);
        
        //base de strickers
        g.setColor(Color.GREEN);
        g.fillRect(game.getWidth()/2-100, 0, DEFENDER_WIDTH, DEFENDER_HEIGHT);
        g.setColor(Color.GREEN);
        g.fillRect(game.getWidth()/2 - 100, game.getHeight()-44, DEFENDER_WIDTH, DEFENDER_HEIGHT);
        //cannons
        g.setColor(Color.GREEN);
        stricker1.paint(g);
        g.setColor(Color.GREEN);
        stricker2.paint(g);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        ;
    }
}