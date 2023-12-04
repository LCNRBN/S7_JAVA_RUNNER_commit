import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.geometry.Rectangle2D;
import java.util.TimerTask;
import java.util.Timer;

//classe abstraite AnimatedThing :
public abstract class AnimatedThing {
    protected Camera camera;
    protected double x;
    protected double y;
    protected final double width;
    protected double height;
    protected ImageView imageView;
    protected int attitude; //attitude du personnage par exemple : (still, running, jumping up, jumping down)
    protected int frameIndex;
    protected int frameMaxIndex;
    private double direction;
    protected int duration;
    protected int frameDuration;
    private double frameOffsetX;
    protected double frameOffsetY;
    protected AnimationTimer animationTimer;
    public AnimatedThing(Camera camera, Group root, double x, double y,double width, double height, String fileName, int attitude, int frameIndex, int frameMaxIndex, int duration, int frameDuration, double frameOffsetX, double frameOffsetY) {
        this.x = x;
        this.y = y;
        this.camera = camera;
        this.width = width;
        this.height = height;
        this.attitude = attitude;
        this.duration = duration;
        this.frameDuration = duration;
        this.frameIndex = frameIndex;
        this.frameMaxIndex = frameMaxIndex;
        this.frameOffsetX = frameOffsetX;
        this.frameOffsetY = frameOffsetY;
        this.direction = 0;

        Image spriteSheet = new Image(fileName);//crée un objet Image à partir du nom de fichier
        imageView = new ImageView(spriteSheet);//crée un objet ImageView à partir de l'image

        // On positionne l'ImageView avec les attributs x et y :
        imageView.setX(x);
        imageView.setY(y);
    }
    //getters
    public ImageView getImageView() {
        return imageView;
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    //setters
    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }
    public double getDirection() {
        return direction;
    }
    public void setDirection(double direction) {
        this.direction = direction;
    }
    public void update(long time) {}

    //le rectangle2D sur la spritesheet
    public void updateViewport(int frameIndex, int attitude, double frameOffsetY, double width, double height) {
        Rectangle2D currentviewport = new Rectangle2D(frameIndex * width + frameOffsetX,attitude * height + frameOffsetY, width, height);
        imageView.setViewport(currentviewport);
    }
    public void renderHero(double deltaTime) {
        // Update the duration
        frameDuration--;

        // Check if it's time to change frames
        if (frameDuration <= 0) {
            // Reset the duration
            frameDuration = duration;

            // Move to the next frame
            if (attitude == 0 | attitude == 2){ //gauche
                frameIndex--;
            }
            if (attitude == 1 | attitude == 3){ //droite
                frameIndex++;
            }
            // Check if we reached the maximum index
            if (attitude == 0){ //gauche
                if (frameIndex < frameMaxIndex) {
                    // Reset to the first frame
                    frameIndex = 5;
                    height = 130;
                    frameOffsetY = 0;
                }
            }
            if (attitude == 1){ //droite
                if (frameIndex > frameMaxIndex) {
                    // Reset to the first frame
                    frameIndex = 0;
                    height = 130;
                    frameOffsetY = 0;
                }
            }
            if (attitude == 2){ //saut gauche
                if (frameIndex < frameMaxIndex) {
                    // Reset to the first frame
                    frameIndex = 3;
                    height = 153;
                    frameOffsetY = 20;
                }
            }
            if (attitude == 3){ //saut droite
                if (frameIndex > frameMaxIndex) {
                    // Reset to the first frame
                    frameIndex = 0;
                    height = 153;
                    frameOffsetY = 20;
                }
            }
            // Update the viewport based on the new index
            updateViewport(frameIndex, attitude, frameOffsetY, width, height);
        }
    }
    public void renderProjectile(double deltaTime) {
        // Update the duration
        frameDuration--;

        // Check if it's time to change frames
        if (frameDuration <= 0) {
            // Reset the duration
            frameDuration = duration;

            frameIndex++;

        }
            // Update the viewport based on the new index
            updateViewport(frameIndex, attitude, frameOffsetY, width, height);

    }
}