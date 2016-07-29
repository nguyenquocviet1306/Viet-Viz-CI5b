/**
 * Created by Viet on 7/24/2016.
 */

import models.Bullet;
import models.Plane;
import models.enemyPlane;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

public class GameWindow  extends Frame implements Runnable {
    Image background;
    Image plane1Image;
    Image plane2Image;
    Image bulletImage;
    Image enemyPlaneImage;

    Plane plane1;
    Plane plane2;
    Vector<Bullet> bulletVector;
    Vector<enemyPlane>  enemyPlaneVector;

    int plane2Width;
    int plane2Height;
    int plane1Width;
    int plane1Height;

    Thread thread;
    BufferedImage bufferedImage;
    Graphics bufferedImageGraphic;

    public GameWindow() {
        System.out.println("Game Window contructor");
        this.setTitle("VietNQ-techkids");
        this.setVisible(true);
        this.setSize(600, 800);
        setLocation(0, 0);
        plane1 = new Plane(250, 300);
        plane2 = new Plane(350, 400);
        bulletVector = new Vector<>();
        enemyPlaneVector = new Vector<>();
        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
            }
            @Override
            public void mouseMoved(MouseEvent e) {
               plane1.moveTo(e.getX() - plane2Width / 2, e.getY() - plane2Height / 2);
            }

        });
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                System.out.println("windowOpened");

            }

            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
                System.out.println("windowClosing");

            }

            @Override
            public void windowClosed(WindowEvent e) {


            }

            @Override
            public void windowIconified(WindowEvent e) {
                System.out.println("windowIconified");

            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                System.out.println("windowDeiconified");

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }


            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
        try {
            background = ImageIO.read(new File("resources/background.png"));
            plane1Image = ImageIO.read(new File("resources/plane4.png"));
            plane2Image = ImageIO.read(new File("resources/plane3.png"));
            bulletImage = ImageIO.read(new File("resources/bullet.png"));
            enemyPlaneImage = ImageIO.read(new File("resources/enemy_plane_yellow_1.png"));
            this.plane2Width = plane2Image.getWidth(null);
            this.plane2Height = plane2Image.getHeight(null);
            this.plane1Width = plane1Image.getWidth(null);
            this.plane1Height = plane1Image.getHeight(null);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        this.repaint();
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_SPACE:
                        Bullet bullet = new Bullet();
                        bullet.moveTo(plane1.x + plane1Width / 2, plane1.y);
                        bulletVector.add(bullet);
                        break;
                    case KeyEvent.VK_LEFT:
                        plane2.x -= 10;
                        break;
                    case KeyEvent.VK_RIGHT:
                        plane2.x += 10;
                        break;
                    case KeyEvent.VK_UP:
                        plane2.y -= 10;
                        break;
                    case KeyEvent.VK_DOWN:
                        plane2.y += 10;
                        break;
                }
                repaint();
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        this.bufferedImage = new BufferedImage(600, 800, BufferedImage.TYPE_INT_ARGB);
        this.bufferedImageGraphic = bufferedImage.getGraphics();

        thread = new Thread(this);
        thread.start();


    }
    @Override



    public void update(Graphics g) {
        bufferedImageGraphic.drawImage(background, 0, 0, null);
        bufferedImageGraphic.drawImage(plane1Image, plane1.x, plane1.y, null);
        bufferedImageGraphic.drawImage(plane2Image,plane2.x, plane2.y, null);
        for(Bullet bullet : bulletVector) {
            bufferedImageGraphic.drawImage(bulletImage, bullet.x, bullet.y , null);
        }
        for(enemyPlane enemyplane : enemyPlaneVector) {
            bufferedImageGraphic.drawImage(enemyPlaneImage, enemyplane.x, enemyplane.y , null);
        }
        g.drawImage(bufferedImage, 0, 0, null);
        System.out.println("Paint");
    }

    public void run() {
        Random rand = new Random();
        int t = 0;
        while (true) {
            try {
                Thread.sleep(27);
                t++;
                int n = rand.nextInt(700);
                if (t == 5) {
                    enemyPlaneVector.add(new enemyPlane(n,0));
                    t = 0;
                }
                Iterator<enemyPlane> enemyPlaneIterator = enemyPlaneVector.iterator();
                while(enemyPlaneIterator.hasNext()) {
                    enemyPlane enemyplane = enemyPlaneIterator.next();
                    enemyplane.y += 10;
                    if (enemyplane.y > 600) {
                        enemyPlaneIterator.remove();
                    }
                    Rectangle enemyPlaneRectangle = new Rectangle(enemyplane.x,enemyplane.y,enemyPlaneImage.getHeight(null),enemyPlaneImage.getWidth(null));
                    Iterator<Bullet> bulletIterator = bulletVector.iterator();
                    while(bulletIterator.hasNext()) {
                        Bullet bullet = bulletIterator.next();
                        bullet.y -= 10;
                        if (bullet.y <= 0) {
                            bulletIterator.remove();
                        }
                        Rectangle bulletRectangle = new Rectangle(bullet.x,bullet.y,bulletImage.getWidth(null),bufferedImage.getHeight(null));
                        if (bulletRectangle.intersects(enemyPlaneRectangle)) {
                            bulletIterator.remove();
                            enemyPlaneIterator.remove();
                        }
                    }
                }
                repaint();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
