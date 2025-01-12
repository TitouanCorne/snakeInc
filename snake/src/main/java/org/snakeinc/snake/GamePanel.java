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
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

import org.snakeinc.snake.model.Anaconda;
import org.snakeinc.snake.model.Apple;
import org.snakeinc.snake.model.Aliment;
import org.snakeinc.snake.model.BoaConstrictor;
import org.snakeinc.snake.model.Broccoli;
import org.snakeinc.snake.model.Difficulty;
import org.snakeinc.snake.model.Python;
import org.snakeinc.snake.model.Snake;
import org.snakeinc.snake.model.Snake.Direction;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    public static final int TILE_SIZE = 20;
    public static final int N_TILES_X = 20;
    public static final int N_TILES_Y = 20;
    public static final int GAME_WIDTH = TILE_SIZE * N_TILES_X; // région où le serpent peut se déplacer
    public static final int GAME_HEIGHT = TILE_SIZE * N_TILES_Y;
    private int INFO_ADDITIONAL_HEIGHT = 40; //rectangle de 40px x GAME_WIDTH pour afficher des infos (scores, ...)
    private int TOTAL_GAME_HEIGHT = GAME_HEIGHT + INFO_ADDITIONAL_HEIGHT; 
    private int TOTAL_GAME_WIDTH = GAME_WIDTH;
    private Timer timer;
    private Difficulty difficulty = new Difficulty(Difficulty.Mode.NORMAL); // Par défaut, la difficulté est "NORMAL"
    private Snake snake;
    private Aliment aliment;
    private boolean running = false;
    private Direction direction = Direction.RIGHT;
    private Integer score = 0;
    private boolean difficultySelected = false; // Pour savoir si la difficulté a été sélectionnée

    private void showDifficultySelection(Graphics g) {
        score = 0; //on réinitialise le score
        g.setColor(Color.BLACK);
        g.fillRect(0, GAME_HEIGHT, GAME_WIDTH, INFO_ADDITIONAL_HEIGHT); //on peind en noir la zone ou il y a le score pour la masquer
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Select Difficulty", (GAME_WIDTH - metrics.stringWidth("Select Difficulty")) / 2, (GAME_HEIGHT + 50) / 4);

        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("1. Easy", (TOTAL_GAME_WIDTH - 90) / 2, TOTAL_GAME_HEIGHT / 2);
        g.drawString("2. Normal", (TOTAL_GAME_WIDTH - 90) / 2, TOTAL_GAME_HEIGHT / 2 + 30);
        g.drawString("3. Hard", (TOTAL_GAME_WIDTH - 90) / 2, TOTAL_GAME_HEIGHT / 2 + 60);
    }

    public GamePanel() {
        this.setPreferredSize(new Dimension(TOTAL_GAME_WIDTH, TOTAL_GAME_HEIGHT)); // 50px en bas pour afficher score
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(this);

        startGame(difficulty);
    }

    private void startGame(Difficulty difficulty) {
        this.difficulty = difficulty;
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
        aliment.updateLocation(snake,difficulty);
        timer = new Timer(150, this);
        timer.start();
        running = true;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //peindre la zone pour afficher le score en blanc :
        g.setColor(Color.WHITE);
        g.fillRect(0, GAME_HEIGHT, GAME_WIDTH, INFO_ADDITIONAL_HEIGHT); // Zone du score
        if(difficultySelected){
            if (running) {
                aliment.draw(g);
                snake.draw(g);
                showScore(g, score);
                showDifficulty(g, difficulty);
            } else {
                gameOver(g);
                showScore(g, score);
                showDifficulty(g, difficulty);
            }
        }
        else{
            showDifficultySelection(g);
        }
    }

    private void saveScore(Snake snake, Integer score, Difficulty difficulty){
        try {
            // URL de l'API
            URL url = new URL("http://localhost:8080/api/v1/score");
            
            // Corps de la requête JSON
            String jsonInput = String.format("{\"snake\":\"%s\", \"difficulty\":\"%s\", \"score\":%d}", snake.getName(), difficulty.getMode().toString().toLowerCase(), score);
            
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInput.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                System.out.println("Score sent successfully.");
            } else {
                System.out.println("Failed to send score: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void gameOver(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (GAME_WIDTH - metrics.stringWidth("Game Over")) / 2, GAME_HEIGHT / 2);
    }

    private void showScore(Graphics g, Integer score){
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.drawString("Score : " + score.toString(), 10, GAME_HEIGHT + 25);
    }

    private void showDifficulty(Graphics g, Difficulty difficulty){
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 15)); 
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Difficulty : " + difficulty.getMode().toString(), GAME_WIDTH - 10 - metrics.stringWidth("Difficulty : " + difficulty.getMode().toString()) , GAME_HEIGHT + 25);
    }

    private void checkCollision() {
        // Vérifie si le serpent se mord ou sort de l'écran
        if (snake.checkSelfCollision() || snake.checkWallCollision()) {
            running = false;
            saveScore(snake, score, difficulty);
            timer.stop();
        }
        // Vérifie si le serpent mange l'aliment'
        if (snake.getHead().equals(aliment.getPosition())) {
            if(aliment.getColor() == Color.GREEN && !snake.canEatBroccoli()){
                running = false;
                saveScore(snake, score, difficulty);
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
                aliment.updateLocation(snake, difficulty);
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
        if (!difficultySelected) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_1:
                    difficulty = new Difficulty(Difficulty.Mode.EASY); // Sélectionner la difficulté "Easy"
                    difficultySelected = true; // L'utilisateur a sélectionné une difficulté
                    startGame(difficulty); // Lancer le jeu avec la difficulté choisie
                    break;
                case KeyEvent.VK_2:
                    difficulty = new Difficulty(Difficulty.Mode.NORMAL); // Sélectionner la difficulté "Normal"
                    difficultySelected = true;
                    startGame(difficulty);
                    break;
                case KeyEvent.VK_3:
                    difficulty = new Difficulty(Difficulty.Mode.HARD); // Sélectionner la difficulté "Hard"
                    difficultySelected = true;
                    startGame(difficulty);
                    break;
            }
        }
        else{
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
                        difficultySelected=false; //On redemande le choix de la difficulté et le jeu se lance dès que le choix est fait
                    }
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
