public class Particle {
    private int px;
    private int py;
    private int vx;
    private int vy;
    private String color;
    private boolean isRed;
    private Circle circle;

    public Particle(String color, boolean isRed, int px, int py, int vx, int vy) {
        this.color = color;
        this.isRed = isRed;
        this.px = px;
        this.py = py;
        this.vx = vx;
        this.vy = vy;
        this.circle = new Circle(color, 5, px, py, isRed ? "red" : "blue", true);
    }

    public void move(int xMin, int xMax, int yMin, int yMax) {
        if (px + vx < xMin || px + vx > xMax) {
            vx = -vx;
        }
        if (py + vy < yMin || py + vy > yMax) {
            vy = -vy;
        }
        px += vx;
        py += vy;
        circle.moveHorizontal(vx);
        circle.moveVertical(vy);
    }

    public int getPx() {
        return px;
    }

    public int getPy() {
        return py;
    }

    public int getVx() {
        return vx;
    }

    public int getVy() {
        return vy;
    }

    public Circle getCircle() {
        return circle;
    }

    public boolean isRed() {
        return isRed;
    }
}
