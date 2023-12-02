/*import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Enemy extends AnimatedThing {

    private double health; //the health of the enemy
    private double behavior; //the behavior of the enemy (0: idle, 1: move, 2: attack)
    private double speed; //the speed of the enemy
    private double direction; //the direction of the enemy (-1: left, 1: right)

    public Enemy(Pane layer, Image image, double x, double y, double health, double behavior, double speed, double direction) {
        super(layer, image, x, y, 0, 0, 0, 0, 1, 1);
        this.health = health;
        this.behavior = behavior;
        this.speed = speed;
        this.direction = direction;
        //set the initial rotation of the sprite
        setRotate(Math.toDegrees(direction));
    }

    public void update(double deltaTime) {
        //update the behavior of the enemy
        updateBehavior();
        //update the position of the enemy
        updatePosition(deltaTime);
        //check if the enemy is still alive
        if (health <= 0) {
            //mark the enemy as removable
            setRemovable(true);
        }
    }

    public void updateBehavior() {
        //TODO: implement the logic for changing the behavior of the enemy
        //For example, you can use a random number generator to switch between idle, move and attack behaviors
        //You can also use the distance and the direction between the enemy and the hero to determine the behavior
    }

    public void updatePosition(double deltaTime) {
        //TODO: implement the logic for moving the enemy according to its behavior, speed and direction
        //For example, you can use the methods move, translate and rotate of the class Sprite to change the position and the orientation of the enemy
        //You can also use the methods getBoundsInParent and intersects of the class Sprite to check for collisions with the hero, the projectiles or the boundaries of the game
    }

    public void getDamagedBy(Projectile projectile) {
        //TODO: implement the logic for reducing the health of the enemy by the damage of the projectile
        //For example, you can use the method getDamage of the class Projectile to get the damage value, and subtract it from the health attribute of the enemy
    }
}
*/