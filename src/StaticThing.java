import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//StaticThing contient deux dimensions et une ImageView
public class StaticThing {
    private double sizeX;
    private double sizeY;
    private final ImageView imageView;

    public StaticThing(double sizeX, double sizeY, String fileName) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        Image image = new Image(fileName);//crée un objet Image à partir du nom de fichier
        imageView = new ImageView(image);//crée un objet ImageView à partir de l'image

        // On ajuste la taille de l'ImageView selon les attributs
        imageView.setFitWidth(sizeX);
        imageView.setFitHeight(sizeY);
    }
    public double getSizeX() {
        return sizeX;
    }
    public double getSizeY() {
        return sizeY;
    }
    public ImageView getImageView() {
        return imageView;
    }
}