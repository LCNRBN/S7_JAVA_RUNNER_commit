import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Projectile extends AnimatedThing {
    private double speed;
    private double direction;
    private double range;
    private boolean exploded;
    public Projectile(Camera camera, Group root, double x, double y, int duration, double speed, double direction, double range) {
        super(camera, root , x, y, 29, 118, "explosion&beam.png", 0, 0, 0, duration, 8, 0, 0);
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.direction = direction;
        this.range = range;
        this.exploded = false;

        updateViewport(0, 0, 0, 0,0);
        //imageView.setX(x);
        //imageView.setY(y);
    }

    /*@Override
    public void update(long time) {
        // Mise à jour de la position du projectile selon sa vitesse et sa direction
        x += speed * Math.cos(direction);
        y += speed * Math.sin(direction);
        // Mise à jour de la portée du projectile
        range -= speed;
        // Vérification de la collision du projectile avec un obstacle ou un ennemi
        checkCollision();
        // Vérification de la fin de la portée du projectile
        if (range <= 0) {
            // Explosion du projectile
            explode();
        }
    }*/

    /*public void animateProjectile(double deltaTime) {
        // Appel de la méthode renderHero de la classe parente AnimatedThing
        super.renderProjectile(deltaTime);
        // Changement de l'image du projectile selon son état
        if (exploded) {
            // Image d'explosion
            updateViewport(0, 0, frameOffsetY, width, height);
        } else {
            // Image de projectile
            updateViewport(0, 0, frameOffsetY, width, height);
        }
    }*/


    public void draw(Camera camera) {
        // Dessin du projectile sur le canvas selon sa position et la position de la caméra
        //imageView.setX(x - camera.getX());
        //imageView.setY(y - camera.getY());
        getImageView().setX(x - camera.getX());
        getImageView().setY(y - camera.getY());
    }

    // Méthode propre à la classe Projectile qui vérifie la collision du projectile avec un obstacle ou un ennemi
    public void checkCollision() {
        // TODO: implémenter la logique de collision
    }

    // Méthode propre à la classe Projectile qui fait exploser le projectile
    public void explode() {
        // TODO: implémenter la logique d'explosion
    }
}
















/*
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Projectile {
    private double x;
    private double y;
    private double direction; //angle in radians
    private double speed; //pixels per second
    private double range; //maximum distance in pixels
    private double damage; //damage to enemies
    private double distance; //distance traveled so far
    private final ImageView imageView; //the image view of the projectile
    private final double width; //the width of the projectile
    private final double height; //the height of the projectile
    private boolean removable; //a flag to indicate if the projectile should be removed
    public Projectile(Pane layer, Image image, double x, double y, double direction, double speed, double range, double damage) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.speed = speed;
        this.range = range;
        this.damage = damage;
        this.distance = 0;
        this.imageView = new ImageView(image); //create an image view from the image
        this.width = image.getWidth(); //get the width of the image
        this.height = image.getHeight(); //get the height of the image
        this.removable = false; //set the removable flag to false
        //set the initial position and rotation of the image view
        imageView.setX(x);
        imageView.setY(y);
        imageView.setRotate(Math.toDegrees(direction));
        //add the image view to the layer
        layer.getChildren().add(imageView);
    }

    public void update(double deltaTime) {
        //update the position of the image view
        double dx = Math.cos(direction) * speed * deltaTime;
        double dy = Math.sin(direction) * speed * deltaTime;
        imageView.setTranslateX(imageView.getTranslateX() + dx);
        imageView.setTranslateY(imageView.getTranslateY() + dy);
        //update the distance traveled
        distance += Math.sqrt(dx * dx + dy * dy);
        //check if the projectile is out of range
        if (distance > range) {
            setRemovable(true);
        }
        //check if the projectile hits an enemy
        for (Enemy enemy : EnemyManager.getEnemies()) { //use the method getEnemies of the class EnemyManager
            if (getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                //damage the enemy
                enemy.getDamagedBy(this);
                //play the explosion animation
                playExplosionAnimation();
                //mark the projectile as removable
                setRemovable(true);
                //break the loop
                break;
            }
        }
    }

    public void playExplosionAnimation() {
        //create an animation timer
        AnimationTimer timer = new AnimationTimer() {
            //define the number of frames and the duration of each frame
            private final int frames = 13;
            private final double duration = 0.05; //in seconds
            //define the current frame and the time elapsed
            private int frame = 0;
            private double time = 0;

            @Override
            public void handle(long now) {
                //update the time elapsed
                time += 1.0 / Game.getFPS();
                //check if it is time to change the frame
                if (time >= duration) {
                    //reset the time
                    time = 0;
                    //update the viewport of the image view
                    updateViewport(frame, 2); //assuming the explosion animation is on the third row of the spritesheet
                    //increment the frame
                    frame++;
                }
            }
        };
        //set the cycle count of the timer
        timer.setCycleCount(frames);
        //set the on finished action of the timer
        timer.setOnFinished(event -> {
            //remove the image view from the layer
            removeFromLayer();
        });
        //start the timer
        timer.start();
    }

    //the rectangle2D on the spritesheet
    public void updateViewport(int frameIndex, int attitude) {
        Rectangle2D currentviewport = new Rectangle2D(frameIndex * width,attitude * height, width, height);
        imageView.setViewport(currentviewport);
    }

    public double getDamage() {
        return damage;
    }

    public boolean isRemovable() {
        return removable;
    }

    public void setRemovable(boolean removable) {
        this.removable = removable;
    }

    public void removeFromLayer() {
        imageView.getParent().getChildren().remove(imageView);
    }

    public Bounds getBoundsInParent() {
        return imageView.getBoundsInParent();
    }
}
*/