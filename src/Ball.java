import acm.graphics.GCanvas;
import acm.graphics.GOval;

public class Ball extends GOval {

    private double deltaX = 1;
    private double deltaY = -1;
    public boolean lost = false;
    private GCanvas screen;

    public Ball(double x, double y, double size, GCanvas screen){
        super(x, y, size, size);
        setFilled(true);
        this.screen = screen;
    }

    public void handleMove(){
        //move the ball
        move(deltaX, -deltaY);

        //Check if the ball is too high
        if(getY() <= 0){
            // start moving down
            deltaY *= -1;
        }
        //Check if the ball is too low
        if(getY() >= screen.getHeight() - getHeight()){
            lost = true;
            deltaX = 1;
            deltaY = 1;
        }
        //Check if ball hits right side
        if(getX() <= 0){
            //start moving right
            deltaX *= -1;
        }
        //Check if ball hits left side.
        if(getX() >= screen.getWidth() - getWidth()){
            //start moving left
            deltaX *= -1;
        }
    }

    public void bounce(){
        deltaY *= -1;
    }

    public void bounceLeft(){
        deltaY *= -1;
        deltaX = -Math.abs(deltaX);
    }

    public void bounceRight(){
        deltaY *= -1;
        deltaX  = Math.abs(deltaX);
    }

}
