import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.geometry.Rectangle2D;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        Group root = new Group();// On crée un objet Group qui sera le parent de la scène

        Camera camera = new Camera(-1000,0, 1,35, 10);

        GameScene gameScene = new GameScene(camera, root, 1200, 900);// On crée un objet GameScene avec le groupe, la largeur et la hauteur souhaités

        primaryStage.setScene(gameScene);// définit la scène du primaryStage comme étant la gameScene

        primaryStage.show();// On affiche le primaryStage

        System.out.println(gameScene.getCamera()); // On teste le code en affichant les coordonnées de la camera

        // On teste le code avec différentes valeurs pour la position de la camera
        //gameScene.getGameCamera().setX(200);
        //gameScene.getGameCamera().setY(300);
        //gameScene.render();
        //gameScene.getGameCamera().setX(400);
        //gameScene.getGameCamera().setY(400);
        //gameScene.render();

        AnimationTimer gameLoop = new AnimationTimer() {
            long prevTime = System.nanoTime(); // Initialize prevTime with the current time in nanoseconds

            @Override
            public void handle(long now) {
                // Calculate the deltaTime (time elapsed since the last frame)
                double deltaTime = (now - prevTime) / 1e9; // Convert nanoseconds to seconds

                // Game logic update
                gameScene.render(deltaTime);

                // Update the previous time for the next frame
                prevTime = now;
            }
        };

        // Start the game loop
        gameLoop.start();
    }
    public static void main(String[] args) {
        launch(args);
    }
}