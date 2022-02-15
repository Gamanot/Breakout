import acm.graphics.GCanvas;
import java.util.Timer;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.TimerTask;

public class Breakout extends GraphicsProgram {

    //add variable lives
    //make a label that takes the variable lives
    //all of the bricks only have one life
    //what happens if I run out of lives
    //How do I know how many lives I have left
    //How Do I know how many bricks I have broken


    //how could I make some bricks contain powerups
    //how could I make this game have more than one level

    public int lives = (3);
    GLabel livesLabel = new GLabel("Lives left: " + lives + "");
    private int points = 0;
    GLabel pointsLabel = new GLabel("Your score: " + points + "");
    private Ball ball;
    private Paddle paddle;
    private int numBricksInRow;
    private Color [] rowColors = {Color.RED, Color.RED, Color.ORANGE, Color.ORANGE, Color.yellow, Color.yellow, Color.GREEN, Color.GREEN, Color.CYAN, Color.CYAN};
    private double brickX;
    private double brickY;
    int value;
    public boolean dog;


    @Override
    public void init(){

        numBricksInRow = (int) (getWidth() / (Brick.WIDTH + 5.0));

        addBricks();


        ball = new Ball(getWidth()/2, 350, 10, this.getGCanvas());
        add(ball);

        paddle = new Paddle(230, 430, 50, 10);
        add(paddle);

        add(livesLabel,getWidth() - getWidth() + 40 - livesLabel.getWidth()/2, getHeight()/2 - livesLabel.getHeight()/2 + 90);
        add(pointsLabel,(getWidth() - getWidth() + 40 - livesLabel.getWidth()/2), getHeight()/2 - livesLabel.getHeight()/2 + 100);

    }
    @Override
    public void run(){
        addMouseListeners();
        waitForClick();
        gameLoop();

    }
    @Override
    public void mouseMoved(MouseEvent me){
        //Make sure that the paddle dosent go off screen.
        if((me.getX() < getWidth() - paddle.getWidth())&&(me.getX() > paddle.getWidth()/2));
        paddle.setLocation(me.getX() - paddle.getWidth()/2, paddle.getY());
    }

    public void addBricks(){
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < numBricksInRow; col++) {
                value = RandomGenerator.getInstance().nextInt(10, 1);
                    if(value == 7){
                         dog = true;}
                    else{
                        dog = false;
                    }
                double brickX = 10 + col * (Brick.WIDTH + 5);
                double brickY = Brick.HEIGHT + row * (Brick.HEIGHT + 5);

                Brick brick = new Brick(brickX, brickY, rowColors[row], row, dog);
                add(brick);
                System.out.println(value);
            }

        }
    }

    public void powerDown(){
        paddle.setSize(50, 10);
    }

    private void powerUP(){
            paddle.setSize(100, 10);


    }

    private void gameLoop(){
        while(true){
            //move the ball
             ball.handleMove();

             //handle collisions
            handleCollisions();

            //handle losing the ball

             if(ball.lost){
                 handleLoss();
             }

             pause(5);


        }
    }

    private void handleCollisions(){
        // obj can store what we hit
        GObject obj = null;

        // check to see if the ball is about to hit something
        if(obj == null){
            //check the top right corner
            obj = this.getElementAt(ball.getX()+ball.getWidth(), ball.getY());
        }
        if(obj == null){
            //check the top left corner
            obj = this.getElementAt(ball.getX(), ball.getY());
        }
        if(obj == null){
            //check the bottom left corner
        obj = this.getElementAt(ball.getX(), ball.getY() + ball.getHeight());
        }
        if(obj == null){
            //check the bottom right corner
            obj = this.getElementAt(ball.getX() + ball.getWidth(),ball.getY() - ball.getHeight() );
        }

        //let's see if we hit something
        if(obj != null){

            //let's see what we hit
            if(obj instanceof Paddle){
                if(ball.getX() < (paddle.getX() + (paddle.getWidth()*0.2))){
                    // did I hit the left side of the paddle?
                    ball.bounceLeft();
                }else if (ball.getX() > (paddle.getX() + (paddle.getWidth())*0.8)){
                    ball.bounceRight();
                }else{
                    //did I hit the middle of the paddle?
                    ball.bounce();
                }
            }

            if(obj instanceof Brick){
                //bounce the ball
                ball.bounce();
                // destroy the brick
                if(((Brick) obj).power){
                    for (int i = 0; i < 10; i++) {
                        powerUP();
                    }
                }
                    if(((Brick) obj).getFillColor() == Color.CYAN) {
                        this.remove(obj);
                        points = points + 1;
                        pointsLabel.setLabel("Your score: " + points + "");
                    } else if(((Brick) obj).getFillColor() == Color.GREEN){
                        ((Brick) obj).setFillColor(Color.CYAN);
                        points = points + 1;
                        pointsLabel.setLabel("Your score: " + points + "");
                    }else if(((Brick) obj).getFillColor() == Color.YELLOW){
                        ((Brick) obj).setFillColor(Color.GREEN);

                    }else if(((Brick) obj).getFillColor() == Color.ORANGE) {
                        ((Brick) obj).setFillColor(Color.YELLOW);
                    }else if(((Brick) obj).getFillColor() == Color.RED) {
                        ((Brick) obj).setFillColor(Color.ORANGE);
                    }
                }
            }



        //if by the end of the method obj is still null, we hit nothing.
    }

    private void handleLoss(){
        ball.lost = false;
        powerDown();
        reset();

    }

    private void reset(){
        ball.setLocation(getWidth()/2, 349);
        paddle.setLocation(230, 430);
        lives = lives -1;
        livesLabel.setLabel("Lives left: " + lives + "");
        checkLoss();
        waitForClick();
    }

    private void lose(){
        removeAll();
        init();
        points = 0;
        lives = 3;
        livesLabel.setLabel("Lives left: " + lives + "");
        pointsLabel.setLabel("Your score: " + points + "");
    }

    private void checkLoss(){
        if(lives == 0){
            lose();
        }
    }

    public static void main(String[] args) {
        Breakout breakout1 = new Breakout();
        breakout1.start();
    }
}
