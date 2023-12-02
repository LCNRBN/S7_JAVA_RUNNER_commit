public class Camera {
    private double x;
    private double y;
    private double vx;

    // Constants
    private final double m; // Mass constant
    private final double k; // Spring constant
    private final double f; // Damping constant
    private static final double INITIAL_VELOCITY_X = 0;
    public Camera(double x, double y, double m, double k, double f) {
        this.x = x;
        this.y = y;
        this.m = m;
        this.k = k;
        this.f = f;
        this.vx = INITIAL_VELOCITY_X;
    }
    // méthodes getX et getY  pour accéder aux attributs x et y
    public double getX() {return x;}
    public double getY() {return y;}
    //méthodes setter pour modifier les coordonnées
    public void setX(int x) {this.x = x;}
    public void setY(int y) {this.y = y;}

    public void movecamera(double deltaX, double deltaY) {
        x += deltaX;
        y += deltaY;
    }
    // Update method to apply physics equations
    public void updatecamera(double deltaTime, double targetX) {
        double springForce = k * (targetX - x);
        double dampingForce = f * vx;
        double totalForce = springForce - dampingForce;
        double ax = totalForce / m;
        vx += ax * deltaTime;
        x += vx * deltaTime;
    }

    @Override
    public String toString() {
        return "camera x,y : "+ x + "," + y;
    }
}