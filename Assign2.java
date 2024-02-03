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
    static long startTime;
    double lastTime;
    double currentTime;
    static final int width = 600, height = 600;

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
    private void plot(Graphics g, int x, int y) {
        g.fillRect(x, y, 1, 1);
    }

    // private void drawRectangle(Graphics g, int x1, int y1, int x2, int y2, Color color) {
    //     g.setColor(color);
    //     for (int x = x1; x < x2; x++) {
    //         for (int y = y1; y < y2; y++) {
    //             plot(g, x, y);
    //         }
    //     }
    // }

    private void drawRectangle(Graphics g, int x1, int y1, int sizeX, int sizeY, Color color) {
        g.setColor(color);
        for (int x = x1; x < x1+sizeX; x++) {
            for (int y = y1; y < y1+sizeY; y++) {
                plot(g, x, y);
            }
        }
    }

    private void fillRectMine(Graphics g, int x, int y, int thickness) {
        int halfThickness = thickness / 2;
        // g.setColor(color);
        for (int i = -halfThickness; i <= halfThickness; i++) {
            for (int j = -halfThickness; j <= halfThickness; j++) {
                plot(g, x + i, y + j);
            }
        }
    }
    private void fillRectMineVertical(Graphics g, int x, int y, int thickness) {
        int halfThickness = thickness / 2;
        // g.setColor(color);

            for (int j = -halfThickness; j <= halfThickness; j++) {
                plot(g, x, y + j);
            }
        
    }

    private boolean isInsideEllipse(int x, int y, int centerX, int centerY, int xRadius, int yRadius, int thickness) {
        double normalizedX = (x - centerX) / (double) (xRadius - thickness);
        double normalizedY = (y - centerY) / (double) (yRadius - thickness);
        double equationResult = Math.pow(normalizedX, 2) + Math.pow(normalizedY, 2);
        return equationResult < 1;
    }
    private void drawLineVertical(Graphics g, int x1, int y1, int x2, int y2, Color color, int thickness) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;
        boolean isSwap = false;
        if (dy > dx) {
            int dxT = dx;
            dx = dy;
            dy = dxT;
            isSwap = true;
        }
        int D = 2 * dy - dx;

        int x = x1;
        int y = y1;

        for (int i = 1; i <= dx; i++) {
            fillRectMineVertical(g, x, y, thickness);

            if (D >= 0) {
                if (isSwap)
                    x += sx;
                else
                    y += sy;

                D -= 2 * dx;
            }
            if (isSwap)
                y += sy;
            else
                x += sx;

            D += 2 * dy;
        }

    }

    private void drawLine(Graphics g, int x1, int y1, int x2, int y2, Color color, int thickness) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;
        g.setColor(color);
        boolean isSwap = false;
        if (dy > dx) {
            int dxT = dx;
            dx = dy;
            dy = dxT;
            isSwap = true;
        }
        int D = 2 * dy - dx;

        int x = x1;
        int y = y1;

        for (int i = 1; i <= dx; i++) {
            fillRectMine(g, x, y, thickness);

            if (D >= 0) {
                if (isSwap)
                    x += sx;
                else
                    y += sy;

                D -= 2 * dx;
            }
            if (isSwap)
                y += sy;
            else
                x += sx;

            D += 2 * dy;
        }

    }
    public void lowPixelBezierCurve(Graphics g, Double x1, Double y1, Double ctrlx1, Double ctrly1, Double ctrlx2, Double ctrly2, Double x2,
            Double y2, int thickness) {
        for (double t = 0; t <= 1; t += 0.01) {
            double x = Math.pow(1 - t, 3) * x1 + 3 * t * Math.pow(1 - t, 2) * ctrlx1 +
                    3 * Math.pow(t, 2) * (1 - t) * ctrlx2 + Math.pow(t, 3) * x2;
            double y = Math.pow(1 - t, 3) * y1 + 3 * t * Math.pow(1 - t, 2) * ctrly1 +
                    3 * Math.pow(t, 2) * (1 - t) * ctrly2 + Math.pow(t, 3) * y2;
            g.fillRect((int) x, (int) y, thickness, thickness); // Drawing a "pixel" of size 5x5
            fillRectMine(g, (int) x, (int) y, thickness);
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
            fillRectMine(g, (int) x, (int) y, thickness);
        }
    }
    public void lowPixelBezierCurve(Graphics g, Double x1, Double y1, Double ctrlx1, Double ctrly1, Double x2,
    Double y2, int thickness) {
        lowPixelBezierCurve(g, x1.intValue(), y1.intValue(),ctrlx1.intValue(), ctrly1.intValue(), x2.intValue(),
            y2.intValue(), thickness);
}

    public void lowPixelBezierCurve(Graphics g, int x1, int y1, int ctrlx1, int ctrly1, int x2,
            int y2, int thickness) {
        int resolution = 500;
        // g.setColor(color);

        for (int t = 0; t <= resolution; t++) {
            float u = t / (float) resolution;
            float uComp = 1 - u;

            int x = (int) (uComp * uComp * x1 + 2 * uComp * u * ctrlx1 + u * u * x2);
            int y = (int) (uComp * uComp * y1 + 2 * uComp * u * ctrly1 + u * u * y2);

            fillRectMine(g, x, y, thickness);
        }
        // lowPixelBezierCurve(g, x1.intValue(), y1.intValue(), ctrlx1.intValue(),
        // ctrly1.intValue(), x2.intValue(),
        // y2.intValue(), thickness);
    }
    // public void lowPixelBezierCurve(Graphics g, int x1, int y1, int ctrlx1, int
    // ctrly1, int x2,
    // int y2, int thickness) {
    // Graphics2D g2d = (Graphics2D) g;
    // g2d.setStroke(new BasicStroke(thickness));
    // g2d.draw(new QuadCurve2D.Double(x1, y1, ctrlx1, ctrly1, x2, y2));
    // // for (double t = 0; t <= 1; t += 0.01) {
    // // double x = Math.pow(1 - t, 2) * x1 + 2 * (1 - t) * t * ctrlx1 +
    // Math.pow(t,
    // // 2) * x2;
    // // double y = Math.pow(1 - t, 2) * y1 + 2 * (1 - t) * t * ctrly1 +
    // Math.pow(t,
    // // 2) * y2;
    // // g.fillRect((int) x, (int) y, thickness, thickness);
    // // Drawing a "pixel" ofsize 5x5
    // // }
    // }

    private void drawEllipse(Graphics g, int centerX, int centerY, int a, int b,Color color,int thickness){
        drawEllipse(g,centerX,centerY,a, b,color,color,thickness);
    }
    private void drawEllipse(Graphics g, int centerX, int centerY, int a, int b,Color color,Color colorBorder,int thickness){
        BufferedImage buffer = new BufferedImage(600, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = buffer.createGraphics();

        drawEllipse(buffer,g2, centerX,centerY,a,b,color,colorBorder,thickness);
        // Draw the image buffer to the screen
        g.drawImage(buffer, 0, 0, null);
    }
    private void drawEllipse(BufferedImage buffer,Graphics2D g2, int centerX, int centerY, int a, int b,Color color,int thickness) {
        drawEllipse(buffer,g2, centerX,centerY, a, b,color,color,thickness);
    }
    private void drawEllipse(BufferedImage buffer,Graphics2D g2, int centerX, int centerY, int a, int b,Color color,Color colorBorder,int thickness) {
        int a2 = a * a;
        int b2 = b * b;
        int twoA2 = 2 * a2;
        int twoB2 = 2 * b2;
        g2.setColor(colorBorder);
        // REGION 1
        int x = 0;
        int y = b;
        int D = Math.round(b2 - a2 * b + a2 / 4);
        int Dx = 0;
        int Dy = twoA2 * y;

        while (Dx <= Dy) {
            plotQuadrants( buffer, centerX, centerY, x, y,colorBorder,thickness);
            x = x + 1;
            Dx = Dx + twoB2;
            D = D + Dx + b2;

            if (D >= 0) {
                y = y - 1;
                Dy = Dy - twoA2;
                D = D - Dy;
            }
        }

        // REGION 2
        x = a;
        y = 0;
        D = Math.round(a2 - b2 * a + b2 / 4);
        Dx = twoB2 * x;
        Dy = 0;

        while (Dx >= Dy) {
            plotQuadrants( buffer, centerX, centerY, x, y,colorBorder,thickness);
            y = y + 1;
            Dy = Dy + twoA2;
            D = D + Dy + a2;

            if (D >= 0) {
                x = x - 1;
                Dx = Dx - twoB2;
                D = D - Dx;
            }
        }
        boolean found = false;
        x = centerX;
        y = centerY;
        int xR = a / 2;
        int yR = b / 2;
        if (!isVarid(x, y)) {
            int xStart = x - xR ;
            int yStart = y - yR;
            int xEnd = x + xR;
            int yEnd = y + yR;
           
            for (int i = xStart; i <= xEnd; i++) {
                for (int j = yStart; j <= yEnd; j++) {
                    if (isVarid(i, j) && isInsideEllipse(i, j, x, y, xR, yR, 2)) {
                        found = true;
                        x = i;
                        y = j;
                        break;
                    }
                }
                if (found)
                    break;
            }
        }
        g2.setColor(Color.red);
        g2.fillRect(x, y, 2,2);
        buffer = floodFill02(buffer, (int) (x), (int) (y), colorBorder, color);
        
    }

    private void plotQuadrants(BufferedImage buffer, int centerX, int centerY, int x, int y,Color color,int thickness) {
        setRGBMine(buffer,centerX + x, centerY + y,thickness,color);
        setRGBMine(buffer,centerX - x, centerY + y,thickness,color);
        setRGBMine(buffer,centerX + x, centerY - y,thickness,color);
        setRGBMine(buffer,centerX - x, centerY - y,thickness,color);
    }
    private void setRGBMine(BufferedImage buffer, int x, int y, int thickness, Color color) {
        int halfThickness = thickness / 2;
        for (int i = -halfThickness; i <= halfThickness; i++) {
            for (int j = -halfThickness; j <= halfThickness; j++) {
                try {
                    if (!isVarid(x + i, y + j)) {
                        continue;
                    }
                    buffer.setRGB(x + i, y + j, color.getRGB());
                } catch (Exception e) {
                    continue;
                }

            }
        }
    }
    private void plotQuadrants(Graphics g, int centerX, int centerY, int x, int y,int thickness) {
        fillRectMine(g, centerX + x, centerY + y,thickness);
        fillRectMine(g, centerX - x, centerY + y,thickness);
        fillRectMine(g, centerX + x, centerY - y,thickness);
        fillRectMine(g, centerX - x, centerY - y,thickness);
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
        // g2.fillRect(400, 400, 100, 100);
        map(g);
    }
    // work space------------------------------------------------------------
    // work space------------------------------------------------------------
    // work space------------------------------------------------------------

    // controler

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

    private void weather(Graphics g) {
        // System.out.println(maskTime);
        currentTime = System.currentTimeMillis();
        Double elapsedTime = currentTime - lastTime;
        // Define the oval path dimensions
        int ovalSize = 25;
        int a = 350;
        int b = 250;
        int pathCenterX = 275;
        int pathCenterY = 270;

        // g.setColor(Color.BLACK);
        // drawEllipse(g,pathCenterX - a, pathCenterY - b, a * 2, b * 2,Color.BLACK);
        // // System.out.println("cloud " + elapsedTime);
        int[] points = calTwoXYpath(movingCicle, pathCenterX, pathCenterY, a, b, ovalSize);
        // drawnSky
        drawnSky(g, points[1]);

        // moon&sun
        // rotate x r per second
        movingCicle += 23 * elapsedTime / 1000.0;
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
        Color clorCloud = interpolateColor(clorCStart, clorCEnd, ratio);
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
        int thickness = 2;
        // Draw the object following the path
        g2.setColor(Color.ORANGE);
        drawEllipse(g,points[0], points[1], size, size,Color.ORANGE,Color.BLACK,thickness);
        g2.setColor(Color.white);
        drawEllipse(g,points[2], points[3], size, size,Color.white,Color.BLACK,thickness);

        // g2.setColor(Color.black);
        // g2.drawOval(points[0], points[1], size, size);
        // g2.drawOval(points[2], points[3], size, size);

        // Draw the image buffer to the screen
        g.drawImage(buffer, 0, 0, null);
    }

    double speedBush = 10;

    private void map(Graphics g) {
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
        bush(g, 50, bushY, 1, movingLean);
        bush(g, 400, bushY + 17, 0.5, movingLean);
        // System.out.println(movingLean);

        drawGround(g);
        int brickY = 300;
        int brickX = 300;
        for (int i = 1; i <= 6; i++) {
            if (i == 2) {
                luckyBox(g, brickX + (i * 25), brickY, 1);
            } else {
                boxBrick(g, brickX + (i * 25), brickY, 1);
            }
        }
        luckyBox(g, 100, brickY, 1);
        luckyBox(g, brickX + 125, brickY - 100, 1);
    }

    // element controler
    // element
    private void drawGround(Graphics g) {
        int floorHeight = 200, startHeight = 400;
        int boxSizeScale = 1;
        int boxSize = boxSizeScale * 25;
        for (int y = startHeight; y < height; y += boxSize) {
            for (int x = 0; x < width; x += boxSize) {
                boxGround(g, x, y, boxSizeScale);
            }
        }
        // boxGround(g,100,100,boxSizeScale);
    }

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
            int thickness = (int) (stepSize + 1);
            g2.setStroke(new BasicStroke(stepSize + 1));
            // g2.drawLine(-1, y + currentY, 601, y + currentY);
            // g2.drawLine(-1, y - currentY, 601, y - currentY);
            drawLineVertical(g2,-1, y + currentY, 601, y + currentY,colorCurrent,thickness);
            drawLineVertical(g2,-1, y - currentY, 601, y - currentY,colorCurrent,thickness);
        }
        g.drawImage(buffer, 0, 0, null);
    }

    private void bush(Graphics g, int x, int y, double size, double lean) {
        BufferedImage buffer = new BufferedImage(600, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = buffer.createGraphics();
        x /= size;
        y /= size;
        int thickness = 2;
        int halfThickness = thickness / 2;
        Color color = Color.decode("#85B905");
        Color colorG = Color.decode("#0D7F0D");
        // Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(colorG);
        // g2d.setStroke(new BasicStroke(thickness));
        lowPixelBezierCurve(g2d,(x) * size, (y + 34) * size, (x + 16 + lean) * size, (y + 9) * size,
                (x + 21) * size, (y + 14) * size,thickness);
        lowPixelBezierCurve(g2d,(x + 21) * size, (y + 14) * size, (x + 38 + lean) * size, (y + 3) * size,
                (x + 48) * size, (y + 11) * size,thickness);
        lowPixelBezierCurve(g2d,(x + 48) * size, (y + 11) * size, (x + 60 + lean) * size, (y + 2) * size,
                (x + 71) * size, (y + 15) * size,thickness);
        lowPixelBezierCurve(g2d,(x + 71) * size, (y + 15) * size, (x + 86 + lean) * size, (y + 7) * size,
                (x + 101) * size, (y + 15) * size,thickness);
        lowPixelBezierCurve(g2d,(x + 71) * size, (y + 15) * size, (x + 86 + lean) * size, (y + 7) * size,
                (x + 101) * size, (y + 15) * size,thickness);
        lowPixelBezierCurve(g2d,(x + 101) * size, (y + 15) * size, (x + 125 + lean) * size, (y + 13) * size,
                (x + 130) * size, (y + 34) * size,thickness);
        lowPixelBezierCurve(g2d,(x + 130) * size, (y + 34) * size, (x + 64 + lean) * size, (y + 40) * size,
                (x) * size, (y + 34) * size,thickness);
        buffer = floodFill02(buffer, (int) ((x + 58) * size), (int) ((y + 20) * size), colorG, color);
        // g2d.fillRect((int) ((x + 58) * size), (int) ((y + 20) * size), 5, 5);
        g.drawImage(buffer, 0, 0, null);
    }

    private void luckyBox(Graphics g, int x, int y, int size) {
        x /= size;
        y /= size;
        int thickness = 2;
        int halfThickness = thickness / 2;
        Color color = Color.decode("#F78E2F");
        // (x + 11) * size
        // Draw the square border
        int era = 25 * size;

        // Draw the border rectangle
        Graphics2D g2d = (Graphics2D) g;
        // Set the color for the fill
        g.setColor(color);
        // Draw the filled square
        // g.fillRect(x, y, era, era);
        drawRectangle(g,x,y,era,era,color);

        g2d.setStroke(new BasicStroke(thickness));

        // g.drawRect(x, y, era, era);
        // g2d.setStroke(new BasicStroke(thickness-1));

        g.setColor(Color.black);
        int thicknessFont = 3;
        int halfEra = era / 2;
        int halfEra2 = halfEra / 2;
        g2d.fillRect(x + halfEra - 2, y + halfEra + halfEra2, thicknessFont, thicknessFont);
        g2d.setStroke(new BasicStroke(thicknessFont));
        g2d.draw(new CubicCurve2D.Float(x + halfEra, y + halfEra + halfEra2 - 6, x + era, y + 8, x + halfEra, y,
                x + halfEra2, y + 8));
        g2d.fillRect(x + halfEra - 2, y + halfEra + halfEra2 - 6, thicknessFont, thicknessFont + 2);

        // border
        g2d.setStroke(new BasicStroke(thickness));
        g.setColor(Color.decode("#CD4D07"));
        drawLine(g,x + halfThickness, y + halfThickness - 1, x + era - halfThickness, y + halfThickness - 1,Color.decode("#CD4D07"),thickness);
        drawLine(g,x + halfThickness, y + halfThickness, x + halfThickness, y + era - halfThickness,Color.decode("#CD4D07"),thickness);
        g.setColor(Color.black);
        drawLine(g,x + era - halfThickness, y + era - halfThickness, x + era - halfThickness, y + halfThickness,Color.BLACK,thickness);
        drawLine(g,x + era - halfThickness, y + era - halfThickness, x + halfThickness, y + era - halfThickness,Color.BLACK,thickness);

        int margin = 2;
        int addMargin = 3;
        int dotSize = 2;
        g2d.fillRect(x + margin, y + margin, dotSize, dotSize);
        g2d.fillRect(x + era - margin - addMargin, y + margin, dotSize, dotSize);
        g2d.fillRect(x + margin, y + era - margin - addMargin, dotSize, dotSize);
        g2d.fillRect(x + era - margin - addMargin, y + era - margin - addMargin, dotSize, dotSize);

    }

    private void boxBrick(Graphics g, int x, int y, int size) {
        x /= size;
        y /= size;
        int thickness = 2;
        int halfThickness = thickness / 2;
        Color color = Color.decode("#C84C0C");
        // (x + 11) * size
        // Draw the square border
        int era = 25 * size;

        // Draw the border rectangle
        Graphics2D g2d = (Graphics2D) g;
        // Set the color for the fill
        g.setColor(color);
        // Draw the filled square
        // g.fillRect(x, y, era, era);
        drawRectangle(g,x,y,era,era,color);

        // g2d.setStroke(new BasicStroke(thickness));

        // g.drawRect(x, y, era, era);
        // g2d.setStroke(new BasicStroke(thickness-1));

        g.setColor(Color.black);
        Color colorB = Color.black;
        int count = 4, dis = era / count;
        int halfEra = era / 2;
        int halfEra2 = halfEra / 2;
        int index = 1;
        for (int i = dis; i < era; i += dis) {
            drawLine(g,x + halfThickness, y + i, x + era - halfThickness, y + i,colorB,thickness);
            // g2d.drawLine(x + halfThickness, y + i, x + era - halfThickness, y + i);

            if (index % 2 != 0) {
                drawLine(g,x + halfEra, y + i, x + halfEra, y + i - dis,colorB,thickness);
                drawLine(g,x + era - halfThickness, y + i, x + era - halfThickness, y + i - dis,colorB,thickness);
            } else {
                drawLine(g,x + halfEra2, y + i, x + halfEra2, y + i - dis,colorB,thickness);
                drawLine(g,x + era - halfEra2, y + i, x + era - halfEra2, y + i - dis,colorB,thickness);
            }
            index++;
        }

        // border
        // g.setColor(Color.decode("#FFB39C"));
        Color colorG = Color.decode("#FFB39C");
        drawLine(g,x + halfThickness, y + halfThickness - 1, x + era - halfThickness, y + halfThickness - 1,colorG,thickness);
        // g2d.drawLine(x+halfThickness, y+halfThickness, x+halfThickness,
        // y+era-halfThickness);
        g.setColor(Color.black);
        // g2d.drawLine(x+era-halfThickness, y+era-halfThickness, x+era-halfThickness,
        // y+halfThickness);
        drawLine(g,x + era - halfThickness, y + era - halfThickness, x + halfThickness, y + era - halfThickness,Color.black,thickness);
    }

    private void boxGround(Graphics g, int x, int y, int size) {
        x /= size;
        y /= size;
        int thickness = 2;
        int halfThickness = thickness / 2;
        Color color = Color.decode("#C84C0C");
        // (x + 11) * size
        // Draw the square border
        int era = 25 * size;

        // Draw the border rectangle
        Graphics2D g2d = (Graphics2D) g;
        // Set the color for the fill
        g.setColor(color);
        // Draw the filled square
        drawRectangle(g,x,y,era,era,color);
        // g.fillRect(x, y, era, era);

        // border
        g2d.setStroke(new BasicStroke(thickness));
        g.setColor(Color.decode("#F1C5A7"));
        Color colorB = Color.decode("#F1C5A7");
        drawLine(g,x + halfThickness, y + halfThickness - 1, x + era - halfThickness, y + halfThickness - 1,colorB,thickness);
        drawLine(g,x + halfThickness, y + halfThickness, x + halfThickness, y + era - halfThickness,colorB,thickness);
        g.setColor(Color.black);
        drawLine(g,x + era - halfThickness, y + era - halfThickness, x + era - halfThickness, y + halfThickness,Color.BLACK,thickness);
        drawLine(g,x + era - halfThickness, y + era - halfThickness, x + halfThickness, y + era - halfThickness,Color.BLACK,thickness);
        // g.drawRect(x, y, era, era);
        // g2d.setStroke(new BasicStroke(thickness-1));

        g.setColor(Color.black);
        lowPixelBezierCurve(g,x + (era - 7), y, x + (era - 5), y + (era - 15), x + 10,
                y + era - halfThickness,thickness);
        lowPixelBezierCurve(g,x + (era - 7), y + (era - 15), x + (era - 3), y + (era - 12), x + era,
                y + (era - 15),thickness);
        lowPixelBezierCurve(g,x, y + (era - 10), x + 5, y + (era - 8), x + (era - 10), y + (era - 10),thickness);
    }

    private void cloud(Graphics g, int x, int y, double size, Color color) {
        BufferedImage buffer = new BufferedImage(600, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = buffer.createGraphics();
        // int sizeI = (int) size;
        // System.out.println("x :"+x+" "+(x/size));
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
        int yNew = (int) ((y + 13 + thickness) * size);
        Point2D.Double originalPoint = new Point2D.Double(xNew, yNew);
        Point2D.Double transformedPoint = new Point2D.Double();

        transform.transform(originalPoint, transformedPoint);
        if (transformedPoint.x < 0) {
            if ((((x + 87) * size) - size) > 0) {
                transformedPoint.x = ((x + 87) * size) - size;
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
        lowPixelBezierCurve(g2, (x + 30) * size, (y + 27) * size, (x + 20) * size, (y + 33) * size,
                (x + 15) * size, (y + 27) * size, thickness);
        lowPixelBezierCurve(g2, (x + 15) * size, (y + 27) * size, x * size, (y + 30) * size, (x + 11) * size,
                (y + 11) * size, thickness);

        g2.setTransform(originalTransform);
        g2.setColor(Color.red);
        // System.out.println("tset "+isVarid((int)originalPoint.x,
        // (int)originalPoint.y));
        g2.fillRect((int) transformedPoint.x, (int) transformedPoint.y, 2, 2);
        buffer = floodFill02(buffer, (int) transformedPoint.x, (int) transformedPoint.y, Color.BLACK, color);
        g.drawImage(buffer, 0, 0, null);
    }
}

class element {

}