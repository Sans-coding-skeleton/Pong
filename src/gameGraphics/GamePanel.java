package gameGraphics;

import keyInputs.KeyHandler;
import entity.Player;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // screen settings
    final int screenWidth = 800;
    final int screenHeight = 450;
    //  final int scale = 2; TODO

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    Player player1 = new Player(this, keyH, true);
    Player player2 = new Player(this, keyH, false);
/*
    int leftPlayerX = 10;
    int leftPlayerY = screenHeight / 2;
    int rightPlayerX = screenWidth - 20;
    int rightPlayerY = screenHeight / 2;
    int leftPlayerPaddleWidth = 10;
    int rightPlayerPaddleWidth = 10;
    int leftPlayerPaddleHeight = 100;
    int rightPlayerPaddleHeight = 100;
    int leftPlayerSpeed = 10;
    int rightPlayerSpeed = 10;
 */

    int FPS = 60;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    //sleep method
/*
    @Override
    public void run() {
        double drawInternal = 1000000.0 / FPS;
        double nextDrawTime = System.currentTimeMillis() + drawInternal;
        while (gameThread.isAlive()) {
            //1. UPDATE: update information such as character position
            update();
            //2. DRAW: draw the screen with the updated information
            repaint();
            try {
                double remainingTime = nextDrawTime - System.currentTimeMillis();
                remainingTime = remainingTime / 1000000;
                if(remainingTime < 0) {
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInternal;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
 */

    //delta method
    @Override
    public void run() {
        double drawInternal = 1000000000.0 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        while (gameThread.isAlive()) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInternal;
            timer += currentTime - lastTime;
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }
            if (timer >= 1000000000) {
                System.out.println("FPS:" + drawCount);
                drawCount = 0;
                timer = 0;
            }

        }
    }

    public void update() {
        player1.update();
        player2.update();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        player1.draw(g2);
        player2.draw(g2);
        g2.dispose();
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }
}