import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Queue;

public class Assign2 extends JPanel implements Runnable {
    double startTime;
    double lastTime;
    double currentTime;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Lab6");
        Assign2 l = new Assign2();
        frame.add(l);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setVisible(true);

        (new Thread(l)).start();
    }

    // tool ----------------------------------------
    public static void myFillRect(Graphics g, int x, int y, int width, int height) {
        // Loop to fill the rectangle with individual pixels
        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                g.fillRect(i, j, 1, 1); // Drawing a single pixel
            }
        }
    }

    public void lowPixelBezierCurve(Graphics g, int x1, int y1, int ctrlx1, int ctrly1, int ctrlx2, int ctrly2, int x2,
            int y2, int thickness) {
        for (double t = 0; t <= 1; t += 0.01) {
            double x = Math.pow(1 - t, 3) * x1 + 3 * t * Math.pow(1 - t, 2) * ctrlx1 +
                    3 * Math.pow(t, 2) * (1 - t) * ctrlx2 + Math.pow(t, 3) * x2;
            double y = Math.pow(1 - t, 3) * y1 + 3 * t * Math.pow(1 - t, 2) * ctrly1 +
                    3 * Math.pow(t, 2) * (1 - t) * ctrly2 + Math.pow(t, 3) * y2;
            g.fillRect((int) x, (int) y, thickness, thickness); // Drawing a "pixel" of size 5x5
        }
    }

    public void lowPixelBezierCurve(Graphics g, Double x1, Double y1, Double ctrlx1, Double ctrly1, Double x2,
            Double y2, int thickness) {
        lowPixelBezierCurve(g, x1.intValue(), y1.intValue(), ctrlx1.intValue(), ctrly1.intValue(), x2.intValue(),
                y2.intValue(), thickness);
    }

    public int[] calTwoXYpath(double angleDegrees, int x, int y, int a, int b, int size) {
        double angleRadians = Math.toRadians(angleDegrees);
        double angleRadians2 = Math.toRadians(angleDegrees + 180);

        int ovalX1 = x + (int) (a * Math.cos(angleRadians)) - size / 2;
        int ovalY1 = y + (int) (b * Math.sin(angleRadians)) - size / 2;

        int ovalX2 = x + (int) (a * Math.cos(angleRadians2)) - size / 2;
        int ovalY2 = y + (int) (b * Math.sin(angleRadians2)) - size / 2;

        return new int[] { ovalX1, ovalY1, ovalX2, ovalY2 };
    }

    public void lowPixelBezierCurve(Graphics g, int x1, int y1, int ctrlx1, int ctrly1, int x2,
            int y2, int thickness) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(thickness));
        g2d.draw(new QuadCurve2D.Double(x1, y1, ctrlx1, ctrly1, x2, y2));
        // for (double t = 0; t <= 1; t += 0.01) {
        // double x = Math.pow(1 - t, 2) * x1 + 2 * (1 - t) * t * ctrlx1 + Math.pow(t,
        // 2) * x2;
        // double y = Math.pow(1 - t, 2) * y1 + 2 * (1 - t) * t * ctrly1 + Math.pow(t,
        // 2) * y2;
        // g.fillRect((int) x, (int) y, thickness, thickness);
        // Drawing a "pixel" ofsize 5x5
        // }
    }

    private boolean isVarid(int x, int y) {
        return (x > -1 && x < 601) && (y > -1 && y < 601);
    }

    public BufferedImage floodFill(BufferedImage m, int x, int y, Color border, Color replace) {
        Queue<int[]> q = new LinkedList<>();

        q.add(new int[] { x, y });
        int borderRGB = border.getRGB();
        int replaceRGB = replace.getRGB();
        int[] currentPos;
        // System.out.println(x + " " + y);
        // m.setRGB(x, y, Color.RED.getRGB());
        while (!q.isEmpty()) {
            currentPos = q.poll();
            int x1 = currentPos[0];
            int y1 = currentPos[1];

            try {
                if (m.getRGB(x1, y1) != borderRGB && m.getRGB(x1, y1) != replaceRGB) {
                    m.setRGB(x1, y1, replaceRGB);
                    // south
                    try {
                        if (m.getRGB(x1, y1 + 1) != borderRGB
                                && m.getRGB(x1, y1 + 1) != replaceRGB) {
                            q.add(new int[] { x1, y1 + 1 });
                        }
                    } catch (Exception e) {
                        // System.out.println(x1 + " " + y1);
                    }
                    try {
                        // north
                        if (m.getRGB(x1, y1 - 1) != borderRGB
                                && m.getRGB(x1, y1 - 1) != replaceRGB) {
                            q.add(new int[] { x1, y1 - 1 });
                        }
                    } catch (Exception e) {
                        // System.out.println(x1 + " " + y1);
                    }
                    try {
                        // east
                        if (m.getRGB(x1 + 1, y1) != borderRGB
                                && m.getRGB(x1 + 1, y1) != replaceRGB) {
                            q.add(new int[] { x1 + 1, y1 });
                        }
                    } catch (Exception e) {
                        // System.out.println(x1 + " " + y1);
                    }
                    try {
                        // west
                        if (m.getRGB(x1 - 1, y1) != borderRGB
                                && m.getRGB(x1 - 1, y1) != replaceRGB) {
                            q.add(new int[] { x1 - 1, y1 });
                        }
                    } catch (Exception e) {
                        // System.out.println(x1 + " " + y1);
                    }
                }

            } catch (Exception e) {
                System.out.println("error in flood fill");
            }

        }

        return m;
    }

    public static BufferedImage floodFill02(BufferedImage image, int x, int y, Color border, Color replace) {
        int width = image.getWidth();
        int height = image.getHeight();

        // int targetColor = image.getRGB(x, y);
        try {

            Queue<int[]> queue = new LinkedList<>();
            queue.add(new int[] { x, y });

            while (!queue.isEmpty()) {
                int[] position = queue.remove();
                int currentX = position[0];
                int currentY = position[1];

                if (currentX < 0 || currentX >= width || currentY < 0 || currentY >= height) {
                    continue;
                }

                if (image.getRGB(currentX, currentY) != border.getRGB()
                        && image.getRGB(currentX, currentY) != replace.getRGB()) {
                    image.setRGB(currentX, currentY, replace.getRGB());

                    queue.add(new int[] { currentX + 1, currentY });
                    queue.add(new int[] { currentX - 1, currentY });
                    queue.add(new int[] { currentX, currentY + 1 });
                    queue.add(new int[] { currentX, currentY - 1 });
                }
            }

        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            // System.out.println("");
        }
        return image;
    }

    private Color interpolateColor(Color startColor, Color endColor, float ratio) {

        int red = (int) (startColor.getRed() + ratio * (endColor.getRed() - startColor.getRed()));
        int green = (int) (startColor.getGreen() + ratio * (endColor.getGreen() - startColor.getGreen()));
        int blue = (int) (startColor.getBlue() + ratio * (endColor.getBlue() - startColor.getBlue()));
        int alpha = (int) (startColor.getAlpha() + ratio * (endColor.getAlpha() - startColor.getAlpha()));

        return new Color(red, green, blue, alpha);
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
    }
    // work space------------------------------------------------------------
    // work space------------------------------------------------------------
    // work space------------------------------------------------------------

    // controler

    Double movingCloud = 0.0;
    Double movingCicle = 210.0;
    Color clorCStart = Color.MAGENTA;
    Color clorCEnd = Color.WHITE;
    Boolean past = true;
    int yOfSun = 200;

    private void weather(Graphics g) {
        currentTime = System.currentTimeMillis();
        Double elapsedTime = currentTime - lastTime;
        // Define the oval path dimensions
        int ovalSize = 50;
        int a = 300;
        int b = 200;
        int pathCenterX = 300;
        int pathCenterY = 200;

        g.setColor(Color.BLACK);
        g.drawOval(pathCenterX - a, pathCenterY - b, a * 2, b * 2);
        // System.out.println("cloud " + elapsedTime);
        int[] points = calTwoXYpath(movingCicle, pathCenterX, pathCenterY, a, b, ovalSize);
        // drawnSky
        drawnSky(g, points[1]);

        // moon&sun
        // rotate x r per second
        movingCicle += 23 * elapsedTime / 1000.0;
        moonAndSun(g, points, ovalSize);
        // System.out.println(currentTime - startTime);

        // clounds
        // Move 50 pixel per second
        movingCloud += 50 * elapsedTime / 1000.0;
        // System.out.println(movingCloud);

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
        Color clorCloud = interpolateColor(clorCStart, clorCEnd, ratio);
        ;
        // cloud
        cloud(g, 50 + movingCloud.intValue() * 2, 25, 0.5, clorCloud);
        cloud(g, movingCloud.intValue() - 330, 25, 1, clorCloud);
        cloud(g, 123 + movingCloud.intValue(), 50, 1, clorCloud);
        cloud(g, 300 + (int) (movingCloud.intValue() * 0.5), 2, 1, clorCloud);
        cloud(g, (int) (movingCloud.intValue() * 1.3 - 250), 50, 1, clorCloud);
        cloud(g, movingCloud.intValue() - 100, 50, 1, clorCloud);
        cloud(g, movingCloud.intValue() - 50, 60, 1.3, clorCloud);
        cloud(g, 400 + movingCloud.intValue(), 40, 1.5, clorCloud);

    }

    // controler----------------------------------------
    // element controler----------------------------------------
    private void moonAndSun(Graphics g, int[] points, int size) {
        BufferedImage buffer = new BufferedImage(600, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = buffer.createGraphics();

        // Draw the object following the path
        g2.setColor(Color.ORANGE);
        g2.fillOval(points[0], points[1], size, size);
        g2.setColor(Color.white);
        g2.fillOval(points[2], points[3], size, size);

        g2.setColor(Color.black);
        g2.drawOval(points[0], points[1], size, size);
        g2.drawOval(points[2], points[3], size, size);

        // Draw the image buffer to the screen
        g.drawImage(buffer, 0, 0, null);
    }

    // element controler
    // element
    private void drawnSky(Graphics g, int y) {
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
            Color colorCurrent = interpolateColor(colorSStart, colorSEnd, ratio);
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

    private void cloud(Graphics g, int x, int y, double size, Color color) {
        BufferedImage buffer = new BufferedImage(600, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = buffer.createGraphics();
        // int sizeI = (int) size;
        x /= size;
        y /= size;
        int thickness = 2;

        g2.setColor(Color.BLACK);
        AffineTransform originalTransform = g2.getTransform();
        // System.out.println(originalTransform);

        AffineTransform transform = new AffineTransform();
        // transform.rotate(Math.toRadians(20), 0, 0);
        // transform.translate(0, 100);
        // transform.scale(sizeI, sizeI);
        g2.transform(transform);

        int xNew = (int) ((x + 11 + thickness) * size);
        int yNew = (int) ((y + 11 + thickness) * size);
        Point2D.Double originalPoint = new Point2D.Double(xNew, yNew);
        Point2D.Double transformedPoint = new Point2D.Double();

        transform.transform(originalPoint, transformedPoint);
        if (transformedPoint.x < 0) {
            if ((((x + 90) * size) - size) > 0) {
                transformedPoint.x = ((x + 90) * size) - size;
                transformedPoint.y += 6 * size;

            }
        }
        // while (transformedPoint.x<0) {
        // if(transformedPoint.x>(x + 101) * size){
        // break;
        // }
        // transformedPoint.x+=1;
        // System.out.println("run");
        // }
        lowPixelBezierCurve(g2, (x + 11) * size, (y + 11) * size, (x + 20) * size, (y - 10) * size,
                (x + 36) * size, (y + 6) * size, thickness);
        lowPixelBezierCurve(g2, (x + 36) * size, (y + 6) * size, (x + 44) * size, (y - 10) * size, (x + 56) * size,
                (y + 6) * size, thickness);
        lowPixelBezierCurve(g2, (x + 56) * size, (y + 6) * size, (x + 68) * size, (y - 10) * size, (x + 79) * size,
                (y + 10) * size, thickness);
        lowPixelBezierCurve(g2, (x + 79) * size, (y + 10) * size, (x + 101) * size, (y + 20) * size,
                (x + 85) * size, (y + 25) * size, thickness);
        lowPixelBezierCurve(g2, (x + 85) * size, (y + 25) * size, (x + 81) * size, (y + 39) * size,
                (x + 70) * size, (y + 27) * size, thickness);
        lowPixelBezierCurve(g2, (x + 70) * size, (y + 27) * size, (x + 65) * size, (y + 35) * size,
                (x + 60) * size, (y + 27) * size, thickness);
        lowPixelBezierCurve(g2, (x + 60) * size, (y + 27) * size, (x + 55) * size, (y + 31) * size,
                (x + 50) * size, (y + 27) * size, thickness);
        lowPixelBezierCurve(g2, (x + 50) * size, (y + 27) * size, (x + 45) * size, (y + 35) * size,
                (x + 40) * size, (y + 27) * size, thickness);

        lowPixelBezierCurve(g2, (x + 40) * size, (y + 27) * size, (x + 35) * size, (y + 31) * size,
                (x + 30) * size, (y + 27) * size, thickness);
        lowPixelBezierCurve(g2, (x + 30) * size, (y + 27) * size, (x + 20) * size, (y + 30) * size,
                (x + 15) * size, (y + 27) * size, thickness);
        lowPixelBezierCurve(g2, (x + 15) * size, (y + 27) * size, x * size, (y + 30) * size, (x + 11) * size,
                (y + 11) * size, thickness);

        g2.setTransform(originalTransform);
        g2.setColor(Color.red);
        // System.out.println("tset "+isVarid((int)originalPoint.x,
        // (int)originalPoint.y));
        // g2.fillRect((int) transformedPoint.x, (int) transformedPoint.y, 2, 2);
        buffer = floodFill02(buffer, (int) transformedPoint.x, (int) transformedPoint.y, Color.BLACK, color);
        g.drawImage(buffer, 0, 0, null);
    }
}

class element {

}