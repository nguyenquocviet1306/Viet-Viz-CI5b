package models;

/**
 * Created by Viet on 7/28/2016.
 */
public class Plane {
    public int x;
    public int y;
    public int dx;
    public int dy;
    public  void move() {
        x += dx;
        y += dy;
    }
//    public Plane(int x, int dx, int y, int dy) {
//        this.x = x;
//        this.dx = dx;
//        this.y = y;
//        this.dy = dy;
//    }
    public Plane(int x, int y) {
        this.x = x;
        //this.dx = dx;
        this.y = y;
    }
    public void moveTo(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
