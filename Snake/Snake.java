import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.*;
import java.util.ArrayList;

public class Snake extends JPanel implements KeyListener {

    //Final variables

    private static final long serialVersionUID = 1L;
    private final int SIZE = 25; // Size of the snake and the apple
    private final int SPEED = 100; //Speed that the snake moves at

    //Initialised variables

    private int snakeLength = 1, score = 0;
    private Color snakeColor = Color.GREEN, appleColor = Color.RED;
    private boolean left = false, right = false, up = false, down = false, flag = false;
                    
    //Declared variables

    private float fSnakeXPosition, fSnakeYPosition, fAppleXPosition, fAppleYPosition;
    private int iSnakeXPosition, iSnakeYPosition, iAppleXPosition, iAppleYPosition, x, y;

    private ArrayList <Integer> snakeX = new ArrayList <Integer> ();
    private ArrayList <Integer> snakeY = new ArrayList <Integer> ();
    private ArrayList <Integer> movesX = new ArrayList <Integer> ();
    private ArrayList <Integer> movesY = new ArrayList <Integer> ();
    
    private Timer timer;
    private ActionListener task;

    public Snake() {

        String[] options = {"No", "Lets Play!"};
        int clicked = JOptionPane.showOptionDialog(null, "Welcome to Snake!\nDo you want to play?", "Snake", 
                                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                                    options, options[0]);
        if(clicked == 0) {  
            Screen.closeScreen();
        } else {

            placeSnake();
            snakeX.add(iSnakeXPosition);
            snakeY.add(iSnakeYPosition);
            placeApple();
            while((iSnakeXPosition == iAppleXPosition) && (iSnakeYPosition == iAppleYPosition)) {
                placeApple();
            }

            task = new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    moveSnake();
                }
            };
            
            timer = new Timer(SPEED, task);
            addKeyListener(this); 
            setFocusable(true);
            System.out.println("Score: " + score);
        }    
    }

    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 500, 497);
        g.setColor(appleColor);
        g.fillRect(iAppleXPosition, iAppleYPosition, SIZE, SIZE);
        g.setColor(snakeColor);
        g.fillRect(snakeX.get(0), snakeY.get(0), SIZE, SIZE);
        if(snakeLength > 1) {
            for(int i = 1; i < snakeLength; i++) {
                g.fillRect(movesX.get(i), movesY.get(i), SIZE, SIZE);
            }
        }
    }

    public void placeSnake() {
        fSnakeXPosition = (float) Math.random() * 19;
        iSnakeXPosition = (int) Math.floor(fSnakeXPosition) * 25;
        fSnakeYPosition = (float) Math.random() * 18;
        iSnakeYPosition = (int) Math.floor(fSnakeYPosition) * 25;
    }

    public void placeApple() {
        fAppleXPosition = (float) Math.random() * 19;
        iAppleXPosition = (int) Math.floor(fAppleXPosition) * 25;
        fAppleYPosition = (float) Math.random() * 18;
        iAppleYPosition = (int) Math.floor(fAppleYPosition) * 25;
    }

    public void checkDirection() {
        if(left) {
            x = -25;
            y = 0;
        } else if(right) {
            x = 25;
            y = 0;
        } else if(up) {
            x = 0;
            y = -25;
        } else if(down) {
            x = 0;
            y = 25;
        } 
    }

    public void moveSnake() {
        checkDirection();
        snakeX.set(0, snakeX.get(0) + x);
        snakeY.set(0, snakeY.get(0) + y);
        movesX.add(0, snakeX.get(0));
        movesY.add(0, snakeY.get(0));
        repaint();
        
        if((snakeX.get(0) > 475) || (snakeX.get(0) < 0) || (snakeY.get(0) > 450) || (snakeY.get(0) < 0)) {
            System.out.println("You Lose...");
            setFocusable(false);
            timer.stop();
            gameover(score);
        } 
        if(snakeLength > 1) {
            for(int i = 1; i <= snakeLength; i++) {
                if((snakeX.contains(movesX.get(i))) && (snakeY.contains(movesY.get(i)))) {
                    System.out.println("You Lose...");
                    setFocusable(false);
                    timer.stop();
                    gameover(score);
                }
            }
        }
        snakeEatsApple();
        flag = false;
    }

    public void snakeEatsApple() {
        if((snakeX.get(0) == iAppleXPosition) && (snakeY.get(0) == iAppleYPosition)) {
            score++;
            System.out.println("Score: " + score);
            snakeLength++;
            placeApple();
            while((snakeX.contains(iAppleXPosition)) && (snakeY.contains(iAppleYPosition))) {
                placeApple();
            }
            for(int i = 1; i <= snakeLength; i++) {
                if((movesX.get(i) == iAppleXPosition) && (movesY.get(i) == iAppleYPosition)) {
                    placeApple();
                    System.out.println("Moving Apple");
                }
            }
        }
    }

    public void gameover(int playerScore) {
        String[] options = {"I Quit...", "Play Again!"};
        int clicked = JOptionPane.showOptionDialog(null, "You scored " + playerScore + " points!", "Game Over", 
                                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                                    options, options[0]);
        if(clicked == 0) {
            Screen.closeScreen();
        } else {
            snakeLength = 1; score = 0;
            left = false; right = false; up = false; down = false;         
            snakeX.clear();
            snakeY.clear();

            placeSnake();
            snakeX.add(iSnakeXPosition);
            snakeY.add(iSnakeYPosition);
            placeApple();
            while((iSnakeXPosition == iAppleXPosition) && (iSnakeYPosition == iAppleYPosition)) {
                placeApple();
            }
            repaint();

            task = new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    moveSnake();
                }
            };
            
            timer = new Timer(SPEED, task);
            addKeyListener(this); 
            setFocusable(true);
            System.out.println("Score: " + score);
        }
    }

    //Required methods for the KeyListener interface

    public void keyTyped(KeyEvent e) { }

    public void keyPressed(KeyEvent e) { 
        int key = e.getKeyCode();

        if(!flag) {
            flag = true;
            if((key == KeyEvent.VK_LEFT) && (!right)) {
                left = true; right = false; up = false; down = false;
            } else if((key == KeyEvent.VK_RIGHT) && (!left)) {
                left = false; right = true; up = false; down = false;
            } else if((key == KeyEvent.VK_UP) && (!down)) {
                left = false; right = false; up = true; down = false;
            } else if((key == KeyEvent.VK_DOWN) && (!up)) {
                left = false; right = false; up = false; down = true;
            } else {
                System.out.println("Invalid key...");
                flag = false;
            }
        }

        timer.start();
    }

    public void keyReleased(KeyEvent e) {}
}
