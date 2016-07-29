package models;

/**
 * Created by Viet on 7/28/2016.
 */
public class Bullet {
    public int x;
    public int y;
    public int dx;
    public int dy;
    public void moveTo(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
