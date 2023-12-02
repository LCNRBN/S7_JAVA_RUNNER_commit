import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Hero extends AnimatedThing {
    //Variables
    private final double initialX;
    private final double initialY;
    private double jumpSpeed;
    private double jumpTopTime;
    private boolean isJumping;
    //Constants
    private final int IDLEFRAME_X = 0;
    private final int IDLEFRAME_Y = 1;
    private final int WINDOWOFFSET_X = 640;
    private static double MOVEMENT_SPEED = 5.0;
    private static final double INITIAL_JUMP_SPEED = -600;
    private static final double JUMP_ACCELERATION_UP = 1800;
    private static final double JUMP_ACCELERATION_DOWN = 1200;
    private static final double JUMP_TOP_DURATION = 0.10;
    private static final double MAX_JUMP_HEIGHT = 100;
    public Hero(double x, double y) {
        //comme en CM : la ligne dessous appelle le constructeur de la classe parente AnimatedThing avec les mêmes paramètres
        super(x, y,123,130, "ATSTspritesheet.png",0,0,5,8,8,0,0);
        this.initialX = x;
        this.initialY = y;
        this.jumpSpeed = 0;
        this.jumpTopTime = 0;
        this.isJumping = false;
        updateViewport(IDLEFRAME_X, IDLEFRAME_Y, frameOffsetY, height);
    }
    // Method for movement with direction
    public void movehero(double direction, double deltaTime) {
        if (direction == 0.0){
            stop(attitude);
            setDirection(direction);
        }
        if (direction == -1){ //gauche
            setDirection(direction);
            attitude = 0;
            frameMaxIndex = 0;
        }
        if (direction == 1){ //droite
            setDirection(direction);
            attitude = 1;
            frameMaxIndex = 5;
        }
        super.renderHero(deltaTime);
        //this.setDirection(direction);
        setX(getX() + MOVEMENT_SPEED * direction);
    }
    public void setJump(double deltaTime) {
        if (!isJumping) { //only jump if not already jumping
            this.isJumping = true;
            this.jumpSpeed = INITIAL_JUMP_SPEED;
            this.jumpTopTime = JUMP_TOP_DURATION;
            //this.attitude = 2; //jumping
            if(attitude == 0){ //gauche
                frameMaxIndex = 0;
                attitude =2;
            }
            if(attitude == 1){ //droite
                frameMaxIndex = 3;
                attitude =3;
            }
            super.renderHero(deltaTime);
        }
    }
    public void updateJump(double deltaTime) { //updateJump(double deltaTime)
        if (isJumping) { //if jumping
            this.y += jumpSpeed * deltaTime; //update y position by speed * duration
            if (jumpSpeed < 0) { //if going up
                jumpSpeed += JUMP_ACCELERATION_UP * deltaTime; //increase speed by acceleration * duration
                if (jumpSpeed >= 0) { //if reached the top
                    jumpSpeed = 0; //set speed to zero
                }
            } else { //if going down or at the top
                if (jumpTopTime > 0) { //if at the top
                    jumpTopTime -= deltaTime; //decrease jump top time by duration
                } else { //if going down
                    jumpSpeed += JUMP_ACCELERATION_DOWN * deltaTime; //increase speed by acceleration * duration
                }
            }
            if (y >= initialY) { //if reached the ground
                y = initialY; //set y to initial y
                isJumping = false; //stop jumping
            }
        }
    }
    public void draw(Camera camera) {
        getImageView().setX(getX() - camera.getX() + WINDOWOFFSET_X);
        getImageView().setY(getY() - camera.getY());
     }
    // gauche : ((dir:1 + 1)/2) * WINDOW_OFFSET_X = WINDOW_OFFSET_X
    // droite : ((dir:-1 + 1)/2) * WINDOW_OFFSET_X = 0 * WINDOW_OFFSET_X = 0
    @Override
    public void renderHero(double deltaTime) {
        //super.render(deltaTime);
        updateJump(deltaTime);
    }
    public void stop(int attitude){
        if(attitude == 0){ //gauche
            updateViewport(frameIndex, attitude,frameOffsetY, height);
        }
        if(attitude == 1){ //droite
            updateViewport(frameIndex, attitude,frameOffsetY, height);
        }
    }
}