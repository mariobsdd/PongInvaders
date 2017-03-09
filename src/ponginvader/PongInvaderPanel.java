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
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.Timer;
import static ponginvader.Commons.DEFENDER_HEIGHT;
import static ponginvader.Commons.DEFENDER_WIDTH;



public class PongInvaderPanel extends JPanel implements ActionListener, KeyListener, Commons {
    private PongInvader game;
    private Ball ball;
    private Racket player1, player2, player3, player4;
    static ArrayList<Racket> players = new ArrayList<Racket>();
    static ArrayList playersX = new ArrayList();
    static Racket player1, player2;
    private Cannon stricker1, stricker2;
    private int score1, score2;
    private int playerNumber;

    public PongInvaderPanel(PongInvader game,Socket socket, int playerNumber, String ip) {
        ListenMovements request = new ListenMovements();
        Thread thread = new Thread(request);
        this.playerNumber = playerNumber;
        thread.start();
        playersX.add(0);
        playersX.add(0);
        setBackground(Color.BLACK);
        this.game = game;
        
        //recibo:
        //angulo, pos en x, pos en y
        
        //a que juego pertenece
        ball = new Ball(game,game.getWidth()/2,game.getHeight()/2,1,false);
        //for each player: juego al que pertenecen, move up, move down, posX
        player1 = new Racket(game, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, 300, 100);
        player2 = new Racket(game, KeyEvent.VK_A, KeyEvent.VK_D, game.getWidth() - 300 ,game.getHeight()-150);
        player3 = new Racket(game, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, game.getWidth()/2-100, 0);
        player4 = new Racket(game, KeyEvent.VK_A, KeyEvent.VK_D, game.getWidth()/2 - 100, game.getHeight()-44);
        
        stricker1 = new Cannon(game,KeyEvent.VK_C,KeyEvent.VK_V,KeyEvent.VK_SPACE,game.getWidth()/2 - SHOOTER_WIDTH/2,-1,false);
        stricker2 = new Cannon(game,KeyEvent.VK_N,KeyEvent.VK_B,KeyEvent.VK_M, game.getWidth()/2,game.getHeight()-29,true);
        //ball = new Ball(game,game.getWidth()/2,game.getHeight()/2);
        //for each player: juego al que pertenecen, move up, move down, posX
        if(playerNumber == 0){
            player1 = new Racket(game, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, 300, 100, ip, playerNumber);
            players.add(player1);
            player2 = new Racket(game, KeyEvent.VK_A, KeyEvent.VK_D, game.getWidth() - 300 ,game.getHeight()-150, ip, playerNumber);
            players.add(player2);
        }else{
            player1 = new Racket(game, KeyEvent.VK_A, KeyEvent.VK_D, 300, 100, ip, playerNumber);
            players.add(player1);
            player2 = new Racket(game, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, game.getWidth() - 300 ,game.getHeight()-150, ip, playerNumber);
            players.add(player2);
        }
        Timer timer = new Timer(5, this);
        timer.start();
        addKeyListener(this);
        setFocusable(true);
    }

    public Racket getDefender(int playerNo) {
        if (playerNo == 1)
            return players.get(0);
        else
            return players.get(1);
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
        //ball.update();
        //player1.update();
        //player2.update();
        players.get(0).update();
        players.get(1).update();
        stricker1.update();
        stricker2.update();
    }

    public void actionPerformed(ActionEvent e) {
        update();
        repaint();
    }

    public void keyPressed(KeyEvent e) {
        if(playerNumber == 0)
            players.get(0).pressed(e.getKeyCode());
        if(playerNumber == 1)
            players.get(1).pressed(e.getKeyCode());
        stricker1.pressed(e.getKeyCode());
        stricker2.pressed(e.getKeyCode());
    }

    public void keyReleased(KeyEvent e) {
        players.get(0).released(e.getKeyCode());

        players.get(1).released(e.getKeyCode());
        stricker1.released(e.getKeyCode());
        stricker2.released(e.getKeyCode());
    }
    
     static class ListenMovements implements Runnable{

        @Override
        public void run() {
            int movementPort = 128;
            byte[] receiveData = new byte[1024];
            DatagramSocket serverSocket = null;
            try {
                serverSocket = new DatagramSocket(movementPort);
                while(true){
                    DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length);
                    serverSocket.receive(packet);
                    String read = new String(packet.getData(), packet.getOffset(), packet.getLength(), "UTF-8");
                    String[] split = read.split("\\s+");
                    //split = split[split.length-2].split("\\s+");
                    playersX.set(Integer.parseInt(split[0]),Integer.parseInt(split[1]));
                    System.out.println(packet.getAddress()+ " said: " + read);
                }
            } catch (SocketException ex) {
                Logger.getLogger(PongInvaderPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(PongInvaderPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //g.drawString(game.getPanel().getScore(1) + " : " + game.getPanel().getScore(2), game.getWidth() / 2, 10);
        g.setColor(Color.RED);
        ball.paint(g);
        int it = ball.getIteracion();
        int it2 = ball.getIteracion2();
        int otro = ball.getOtrait();
        if(playerNumber == 0)
            players.get(1).setMovement((int)playersX.get(1));
        if(playerNumber == 1)
            players.get(0).setMovement((int)playersX.get(0));
        players.get(0).paint(g,0);
        players.get(1).paint(g,0);
        //base de strickers
        g.setColor(Color.GREEN);
        player3.paint(g, otro);
        g.setColor(Color.GREEN);
        player4.paint(g, otro);

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