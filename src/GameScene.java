import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.Group;
import java.util.ArrayList;
import java.util.Random;

public class GameScene extends Scene {
    private final Camera camera;
    private StaticThing backgroundLeft;
    private StaticThing backgroundRight;
    private int numberOfLives = 10;
    private ImageView[] hearts; // un tableau d'ImageView pour afficher les coeurs
    private ArrayList<StaticThing> backgrounds;
    private ArrayList<AnimatedThing> animatedThings; // une liste d'objets animés
    private Hero hero;
    private static final double INITIAL_HERO_X = -500; // par rapport au background, pas au game screen
    private static final double INITIAL_HERO_Y = 750;
    private static final double BACKGROUND_WIDTH = 1200;
    private static final double BACKGROUND_HEIGHT = 900;
    private static final String[] BACKGROUND_IMAGES = {"forêtEndor0.jpg", "forêtEndor1.jpg", "forêtEndor1_Reversed.png", "forêtEndor3.jpg", "forêtEndor3_Reversed.jpg", "forêtEndor2.jpg", "forêtEndor2_Reversed.jpg", "forêtEndor_end.jpg"}; // un tableau de noms de fichiers pour les arrière-plans
    private static final int BACKGROUND_NBR = 20;
    private static final double HEART_WIDTH = 50;
    private static final double HEART_HEIGHT = 50;
    private static final String HEART_IMAGE = "coeur.png";

    public GameScene (Camera camera, Group root, double width, double height) {
        super (root, width, height);//classe parente Scene

        this.camera = camera;

        initBackgrounds(root);
        initHearts(root);

        // On crée une liste d'objets animés et on y ajoute le héros
        initHero(root);
        animatedThings = new ArrayList<AnimatedThing>();
        animatedThings.add(hero);

        setOnMouseClicked(event -> handleClickPress(event.getButton()));
        setOnKeyPressed(event -> handleKeyPress(event.getCode()));
        setOnKeyReleased(event -> handleKeyRelease(event.getCode()));
    }
    private void initBackgrounds(Group root){
        // On crée une liste d'objets StaticThing et on la remplit avec des arrière-plans
        backgrounds = new ArrayList<StaticThing>();
        for (int i = 0; i < BACKGROUND_NBR; i++) { // génère des backgrounds aléatoires
            Random random = new Random();
            int x = random.nextInt(BACKGROUND_IMAGES.length);
            StaticThing background = new StaticThing(BACKGROUND_WIDTH, BACKGROUND_HEIGHT, BACKGROUND_IMAGES[x]);
            background.getImageView().setX(i * BACKGROUND_WIDTH); // On décale l'arrière-plan selon son indice dans le tableau
            background.getImageView().setY(0);
            backgrounds.add(background);
        }
        // On ajoute les ImageView de chaque arrière-plan au groupe root
        for (StaticThing background : backgrounds) {
            root.getChildren().add(background.getImageView());
        }
    }
    private void initHearts(Group root){
        //Les coeurs
        //numberOfLives = 10;//pour modif le nb de coeur
        hearts = new ImageView[numberOfLives];// On crée un tableau d'ImageView de la même taille que le nombre de vies
        Image heart = new Image(HEART_IMAGE);
        // On remplit le tableau d'ImageView avec des cœurs
        for (int i = 0; i < numberOfLives; i++) {
            hearts[i] = new ImageView(heart);// On crée un objet ImageView à partir de l'image du coeur
            hearts[i].setFitWidth(HEART_WIDTH);//ajuste la taille de l'ImageView (coeurs écrasés ou pas)
            hearts[i].setFitHeight(HEART_HEIGHT);//pareil
            hearts[i].setX(10 + i * 60);// On positionne l'ImageView en haut à gauche de la scène, avec un espacement de 10 pixels entre les coeurs
            hearts[i].setY(10);
            root.getChildren().add(hearts[i]);// On ajoute l'ImageView au groupe root
        }
    }
    private void initHero(Group root){
        // Instantiate the hero
        hero = new Hero(camera, root, INITIAL_HERO_X, INITIAL_HERO_Y);
        root.getChildren().add(hero.getImageView());
    }

    // Method to handle click events
    private void handleClickPress(MouseButton button) {
        switch (button) {
            //case PRIMARY -> hero.shootProjectile(); // Left-click
            case SECONDARY -> {} // Right-click
        }
    }
    // Method to handle key press events
    private void handleKeyPress(KeyCode code) {
        switch (code) {
            case LEFT, A -> hero.movehero(-1.0, 0.01);

            case RIGHT, D -> hero.movehero(1.0, 0.01);

            case UP, W, SPACE -> hero.setJump(0.01);
            //case P -> audioManager.playMusic("Glorious_Morning");
        }
    }
    // Method to handle key release events
    private void handleKeyRelease(KeyCode code) {
        switch (code) {
            case LEFT, RIGHT, A, D -> hero.movehero(0.0, 0.01);
        }
    }
    public void render(double deltaTime) {
        // Get the camera coordinates
        double cameraX = camera.getX();
        double cameraY = camera.getY();

        // Move the camera using physics equations
        camera.updatecamera(deltaTime, hero.getX() + hero.getDirection() * 500); //mouvement de camera selon la direction du hero

        // Adjust the position of the ImageViews of static elements based on the camera
        for (StaticThing background : backgrounds) {
            background.getImageView().setX(backgrounds.indexOf(background) * getWidth()/1.2 - cameraX); // On déplace l'ImageView de l'arrière-plan selon la position de la caméra
            background.getImageView().setY(cameraY);
        }

        // Render the hero at the camera position
        hero.draw(camera);
        hero.renderHero(deltaTime);

    }
    public Camera getGameCamera() {
        return camera;
    }
}