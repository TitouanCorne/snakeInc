package org.snakeinc.snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

import org.snakeinc.snake.model.Anaconda;
import org.snakeinc.snake.model.Apple;
import org.snakeinc.snake.model.Aliment;
import org.snakeinc.snake.model.BoaConstrictor;
import org.snakeinc.snake.model.Broccoli;
import org.snakeinc.snake.model.Python;
import org.snakeinc.snake.model.Snake;
import org.snakeinc.snake.model.Snake.Direction;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    public static final int TILE_SIZE = 20;
    public static final int N_TILES_X = 20;
    public static final int N_TILES_Y = 20;
    public static final int GAME_WIDTH = TILE_SIZE * N_TILES_X;
    public static final int GAME_HEIGHT = TILE_SIZE * N_TILES_Y;
    private Timer timer;
    private Snake snake;
    private Aliment aliment;
    private boolean running = false;
    private Direction direction = Direction.RIGHT;
    //private char direction = 'R';
    private Integer score = 0;

    public GamePanel() {
        this.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(this);

        startGame();
    }

    private void startGame() {
        Random random = new Random(); 
        int randomNumber = random.nextInt(3);
        switch (randomNumber) {
            case 0:
                snake = new Anaconda();
                break;
            case 1:
                snake = new Python();
                break;
            case 2:
                snake = new BoaConstrictor();
                break;
        }
        aliment = new Apple();
        aliment.updateLocation(snake); //pour que l'aliment ne soit pas caché derrière le serpent
        timer = new Timer(100, this);
        timer.start();
        running = true;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (running) {
            aliment.draw(g);
            snake.draw(g);
            showScore(g, score);
        } else {
            gameOver(g);
        }
    }

    private void gameOver(Graphics g) {
        score = 0; //on réinitialise le score
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (GAME_WIDTH - metrics.stringWidth("Game Over")) / 2, GAME_HEIGHT / 2);
    }

    private void showScore(Graphics g, Integer score){
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score : " + score.toString(), 5 , 20);
    }

    private void checkCollision() {
        // Vérifie si le serpent se mord ou sort de l'écran
        if (snake.checkSelfCollision() || snake.checkWallCollision()) {
            running = false;
            timer.stop();
        }
        // Vérifie si le serpent mange l'aliment'
        if (snake.getHead().equals(aliment.getPosition())) {
            if(aliment.getColor() == Color.GREEN && !snake.canEatBroccoli()){
                running = false;
                timer.stop();
            }
            else{
                score++;
                aliment.beEatenBy(snake);
                Random random = new Random();
                Integer randomNumber = random.nextInt(3);
                if(randomNumber < 2){ // deux tiers des fruits sont des pommes (pour être sur de pouvoir finir le jeu !)
                    aliment = new Apple ();
                }else{
                    aliment = new Broccoli();
                }
                aliment.updateLocation(snake);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            snake.move(direction);
            checkCollision();
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if (direction != Direction.RIGHT) {
                    direction = Direction.LEFT;
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (direction != Direction.LEFT) {
                    direction = Direction.RIGHT;
                }
                break;
            case KeyEvent.VK_UP:
                if (direction != Direction.DOWN) {
                    direction = Direction.UP;
                }
                break;
            case KeyEvent.VK_DOWN:
                if (direction != Direction.UP) {
                    direction = Direction.DOWN;
                }
                break;
            case KeyEvent.VK_SPACE:
                if (!running){
                    direction = Direction.RIGHT; //On remet la direction par défaut (celle du début)
                    startGame();
                }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
