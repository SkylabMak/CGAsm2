import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Assign2 extends JPanel implements Runnable {
    static long startTime;
    double lastTime;
    double currentTime;
    static final int width = 600, height = 600;
    Tool tool = new Tool();
    Element e = new Element();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Lab6");
        Assign2 l = new Assign2();
        frame.add(l);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setVisible(true);
        startTime = System.currentTimeMillis();
        (new Thread(l)).start();
    }

    // tool ----------------------------------------
    // work space------------------------------------------------------------
    // work space------------------------------------------------------------
    // work space------------------------------------------------------------
    @Override
    public void run() {
        lastTime = System.currentTimeMillis();
        Long lastTmp = (long) lastTime;
        while (true) {
            lastTime = lastTmp;
            lastTmp = System.currentTimeMillis();

            repaint();// **********asynchronous**********

            try {
                // Sleep for a frame duration
                Thread.sleep(1000 / 30);
            } catch (InterruptedException e) {
                System.err.println(e);
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        // System.out.println("G run");
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, 600, 600);
        weather(g);
        g2.setColor(Color.black);
        g2.fillRect(400, 400, 100, 100);
        map(g);
        MarioMovement(g);
    }
    // work space------------------------------------------------------------
    // work space------------------------------------------------------------
    // work space------------------------------------------------------------

    // time controler-------------------------------
    int startHeightGround = 400;
    // _________________Mario&&mushroom__________________
    double marioScale = 1;
    double marioMove = 0;
    int startScaleTime = 3000;

    public void MarioMovement(Graphics g) {
        BufferedImage buffer = new BufferedImage(600, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = buffer.createGraphics();
        AffineTransform originalTransform = g2d.getTransform();
        int x = 0, y = startHeightGround;
        currentTime = System.currentTimeMillis();
        Double elapsedTime = currentTime - lastTime;
        int startSize = 2;
        int frame = 1;

        AffineTransform transform = new AffineTransform();
        // transform.translate(100, 0);
        // System.out.println("mario run");
        // System.out.println(System.currentTimeMillis() - startTime);
        if (System.currentTimeMillis() - startTime > startScaleTime) {
            // scaling
            marioScale += 1 * elapsedTime / 1000.0;
            // System.out.println(marioScale);
            g2d.transform(AffineTransform.getTranslateInstance(x + marioMove, y));
            g2d.transform(AffineTransform.getScaleInstance(marioScale, marioScale));
            g2d.transform(AffineTransform.getTranslateInstance(-x - marioMove, -y));
            // scaling
        } else if (System.currentTimeMillis() - startTime > 2000) {

        } else {
            int des = 330;
            int t = 2;
            // move 5 px per s.
            marioMove += (((des - x) / (t * 2))) * elapsedTime / 1000.0;
            frame = (int) ((currentTime/10)%3)+1;
            System.out.println(frame);

            // System.out.println("run");
        }
        g2d.transform(transform);

        e.drawMarioFrame(frame, (int) (x + marioMove), y, startSize, startSize, g2d);
        // System.out.println(startTime);
        g.drawImage(buffer, 0, 0, null);
    }

    public void mushroomMovement(Graphics g) {
        e.drawMushroom(300, 300, 1, g);
    }

    // _________________Mario&&mushroom__________________
    // ________________weather________________
    Double movingCloud = 0.0;
    Double movingCicle = 210.0;
    Color clorCStart = Color.decode("#00A7FA");
    Color clorCEnd = Color.WHITE;
    Boolean past = true;
    int yOfSun = 200;
    Double movingLean = -5.0;
    Long maskTime = System.currentTimeMillis();
    int countCycle = 0;
    double speedCloud = 25;

    public void weather(Graphics g) {
        // System.out.println(maskTime);
        currentTime = System.currentTimeMillis();
        Double elapsedTime = currentTime - lastTime;
        // Define the oval path dimensions
        int ovalSize = 50;
        int a = 350;
        int b = 300;
        int pathCenterX = 325;
        int pathCenterY = 300;

        g.setColor(Color.BLACK);
        g.drawOval(pathCenterX - a, pathCenterY - b, a * 2, b * 2);
        // System.out.println("cloud " + elapsedTime);
        int[] points = tool.calTwoXYpath(movingCicle, pathCenterX, pathCenterY, a, b, ovalSize);
        // drawnSky
        drawnSky(g, points[1]);

        // moon&sun
        // rotate x r per second
        movingCicle += 23 * elapsedTime / 1000.0;
        // movingCicle += 10 * elapsedTime / 1000.0;
        moonAndSun(g, points, ovalSize);
        // System.out.println(currentTime - startTime);

        float weight = 0.8f;
        double i = ((movingCicle + 90) % 180);
        float ratio = (float) Math.pow(i / 180, weight);
        // System.out.println(ratio);
        // System.out.println(((movingCicle - 30) % 180));
        int movingCicleMod = (int) i;
        // System.out.println(movingCicleMod);

        if (movingCicleMod > 50) {
            past = true;
        }
        // Check if movingCicle has passed a multiple of 180 degrees
        if (movingCicleMod < (50 * elapsedTime / 1000.0) && past) {
            past = false;
            // Swap colors
            Color tempColor = clorCStart;
            clorCStart = clorCEnd;
            clorCEnd = tempColor;
            System.out.println("Swap color: " + tempColor.toString());
        }
        Color clorCloud = tool.interpolateColor(clorCStart, clorCEnd, ratio);
        // cloud
        // Move 50 pixel per second
        movingCloud += speedCloud * elapsedTime / 1000.0;
        // System.out.println(movingCloud);
        int lenCloud = 500;
        if (movingCloud > lenCloud) {
            speedCloud = -speedCloud;
        }
        if (movingCloud < -lenCloud) {
            speedCloud = -speedCloud;
        }
        movingCloud += speedCloud * elapsedTime / 1000.0;

        e.cloud(g, 50 + movingCloud.intValue() * 2, 25, 0.5, clorCloud);
        e.cloud(g, movingCloud.intValue() - 330, 25, 1, clorCloud);
        e.cloud(g, 123 + movingCloud.intValue(), 50, 1, clorCloud);
        e.cloud(g, 300 + (int) (movingCloud.intValue() * 0.5), 2, 1, clorCloud);
        e.cloud(g, (int) (movingCloud.intValue() * 1.3 - 250), 50, 1, clorCloud);
        e.cloud(g, movingCloud.intValue() - 100, 50, 1, clorCloud);
        e.cloud(g, movingCloud.intValue() - 50, 60, 1.3, clorCloud);
        e.cloud(g, 400 + movingCloud.intValue(), 40, 1.5, clorCloud);

    }

    // ________________weather________________
    // ____________________Map____________________
    double speedBush = 10;

    public void map(Graphics g) {
        currentTime = System.currentTimeMillis();
        Double elapsedTime = currentTime - lastTime;

        int bushY = 365;
        int lenBush = 5;
        if (movingLean > lenBush) {
            speedBush = -speedBush;
        }
        if (movingLean < -lenBush) {
            speedBush = -speedBush;
        }
        movingLean += speedBush * elapsedTime / 1000.0;
        e.bush(g, 50, bushY, 1, movingLean);
        e.bush(g, 400, bushY + 17, 0.5, movingLean);
        // System.out.println(movingLean);

        drawGround(g);
        int brickY = 300;
        int brickX = 300;
        for (int i = 1; i <= 6; i++) {
            if (i == 2) {
                e.luckyBox(g, brickX + (i * 25), brickY, 1);
            } else {
                e.boxBrick(g, brickX + (i * 25), brickY, 1);
            }
        }
        e.luckyBox(g, 100, brickY, 1);
        e.luckyBox(g, brickX + 125, brickY - 100, 1);
    }
    // ____________________Map____________________

    // time controler----------------------------------------
    // element controler----------------------------------------
    public void moonAndSun(Graphics g, int[] points, int size) {
        BufferedImage buffer = new BufferedImage(600, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = buffer.createGraphics();

        // Draw the object following the path
        // g2.setColor(Color.ORANGE);
        // g2.fillOval(points[0], points[1], size, size);
        // g2.setColor(Color.white);
        // g2.fillOval(points[2], points[3], size, size);

        // g2.setColor(Color.black);
        // g2.drawOval(points[0], points[1], size, size);
        // g2.drawOval(points[2], points[3], size, size);

        // my oval
        int thickness = 1;
        size /= 2;
        // g2.setColor(Color.ORANGE);
        tool.drawEllipse(g, points[0], points[1], size, size, Color.ORANGE, Color.BLACK, thickness);
        // g2.setColor(Color.white);
        tool.drawEllipse(g, points[2], points[3], size, size, Color.white, Color.BLACK, thickness);

        // Draw the image buffer to the screen
        g.drawImage(buffer, 0, 0, null);
    }

    // element controler
    // -----------------------------------------------------------
    // Combine elements
    public void drawGround(Graphics g) {
        int floorHeight = 200;
        int boxSizeScale = 1;
        int boxSize = boxSizeScale * 25;
        for (int y = startHeightGround; y < height; y += boxSize) {
            for (int x = 0; x < width; x += boxSize) {
                e.boxGround(g, x, y, boxSizeScale);
            }
        }
        // boxGround(g,100,100,boxSizeScale);
    }

    // sky
    public void drawnSky(Graphics g, int y) {
        BufferedImage buffer = new BufferedImage(600, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = buffer.createGraphics();
        Color colorSStart = Color.decode("#61DEF2");// light color
        Color colorSEnd = Color.decode("#024DC0");// dark color
        float count = 100f;
        float height = 600f;
        float maxHeight = 600f;
        float stepSize = height / count;
        // System.out.println(step);
        float divider = 0.3f;
        // sky
        for (int i = 0; i <= count; i++) {
            float ratio = (float) i / count;
            // float ratio = (float) i / count;
            // if (i <= count * divider) {
            // ratio = (float) i / (count * divider);
            // } else {
            // ratio = 1.0f;
            // }
            Color colorCurrent = tool.interpolateColor(colorSStart, colorSEnd, ratio);
            // int currentY = (int) (maxHeight - (ratio * height));
            int currentY = (int) ((ratio * height));
            // System.out.println(currentY);
            g2.setColor(colorCurrent);
            g2.setStroke(new BasicStroke(stepSize + 1));
            g2.drawLine(-1, y + currentY, 601, y + currentY);
            g2.drawLine(-1, y - currentY, 601, y - currentY);
        }
        g.drawImage(buffer, 0, 0, null);
    }

    // Combine elements

}
<<<<<<< HEAD

class element {
    public void drawMarioFrame(int n) {
        switch (n) {
            case 0:
        }
    }
}
=======
>>>>>>> f764e2edb702512d78f31f009810015c0538d8c9
