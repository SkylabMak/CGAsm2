import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Queue;

public class Assignment2_65050660_65051027 extends JPanel implements Runnable {
    static long startTime;
    double lastTime;
    double currentTime;
    static final int width = 600, height = 600;
    Tool tool = new Tool();
    Element e = new Element();

    public static void main(String[] args) {
        JFrame frame = new JFrame(" FROM BABIES TO BIG");
        Assignment2_65050660_65051027 l = new Assignment2_65050660_65051027();
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

            repaint();

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
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, 600, 600);
        weather(g);
        g2.setColor(Color.black);
        g2.fillRect(400, 400, 100, 100);
        map(g);
        MarioMovement(g);
        mushroomBlock(g);
    }
    // work space------------------------------------------------------------
    // work space------------------------------------------------------------
    // work space------------------------------------------------------------

    // time controler-------------------------------
    int startHeightGround = 400;
    // _________________Mario&&mushroom__________________
    double marioScale = 1;
    double marioMove = -100;
    int startScaleTime = 3000;
    int currentAltitude = startHeightGround;
    int mushroomAltitude = 350;

    public void MarioMovement(Graphics g) {
        BufferedImage buffer = new BufferedImage(600, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = buffer.createGraphics();
        int x = 0, y = startHeightGround;
        currentTime = System.currentTimeMillis();
        Double elapsedTime = currentTime - lastTime;
        // int startSize = 1;
        int currentWidth = 2;
        int currentHeight = 1;
        int frame = 1;
        long totalElapsedTime = (System.currentTimeMillis() - startTime);

        if (currentAltitude < startHeightGround) {
            frame = 4;
        } else {
            frame = (int) ((currentTime / 10) % 3) + 1;
        }

        if (totalElapsedTime < 4000) {
            int des = 200;
            double t = 4000;
            marioMove = (des * (totalElapsedTime / t)) - 100;
        } else if (totalElapsedTime < 4750) {
            int target = 50;
            double t = 750;
            currentAltitude = Math.round((int) (y - (target * ((totalElapsedTime - 4000) / t))));
        } else if (totalElapsedTime < 5500) {
            int target = 55;
            int mushroomTarget = 25;
            double t = 750;
            currentAltitude = Integer.min(startHeightGround,
                    (Math.round((int) (y - 50 + (target * ((totalElapsedTime - 4750) / t))))));
            mushroomAltitude = Math.round((int) (325 - (mushroomTarget * (totalElapsedTime - 4750) / t)));
        } else if (totalElapsedTime < 6000) {
            int des = 25;
            double t = 500;
            marioMove = 100 + (des * ((totalElapsedTime - 5500) / t));
        } else if (totalElapsedTime < 7000) {
            int des = 50;
            int target = 75;
            double t = 1000;
            marioMove = 125 + (des * ((totalElapsedTime - 6000) / t));
            mushroomAltitude = (Math.round((int) (300 + (target * ((totalElapsedTime - 6000) / t)))));
        } else if (totalElapsedTime < 8000) {
            frame = 4;
            currentHeight = (((int) totalElapsedTime % 200) / 100) + 1;
        } else if (marioMove <= 600) {
            currentHeight = 2;
            marioMove += 50 * elapsedTime / 1000;
        } else {
            startTime = System.currentTimeMillis();
            marioMove = -100;
            currentHeight = 1;
        }
        e.drawMarioFrame(frame, (int) (x + marioMove), currentAltitude, currentWidth, currentHeight, g2d);
        if (totalElapsedTime >= 4750 && totalElapsedTime <= 7000) {
            e.drawMushroom((int) (x + marioMove), mushroomAltitude, 2, g2d);
        }
        g.drawImage(buffer, 0, 0, null);
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
        currentTime = System.currentTimeMillis();
        Double elapsedTime = currentTime - lastTime;
        // Define the oval path dimensions
        int ovalSize = 50;
        int a = 350;
        int b = 300;
        int pathCenterX = 320;
        int pathCenterY = 300;

        g.setColor(Color.BLACK);
        g.drawOval(pathCenterX - a, pathCenterY - b, a * 2, b * 2);
        int[] points = tool.calTwoXYpath(movingCicle, pathCenterX, pathCenterY, a, b, ovalSize);
        // drawnSky
        drawnSky(g, points[1]);

        // moon&sun
        // rotate x r per second
        movingCicle += 23 * elapsedTime / 1000.0;
        moonAndSun(g, points, ovalSize);

        float weight = 0.8f;
        double i = ((movingCicle + 90) % 180);
        float ratio = (float) Math.pow(i / 180, weight);
        int movingCicleMod = (int) i;

        if (movingCicleMod > 50) {
            past = true;
        }
        if (movingCicleMod < (50 * elapsedTime / 1000.0) && past) {
            past = false;
            // Swap colors
            Color tempColor = clorCStart;
            clorCStart = clorCEnd;
            clorCEnd = tempColor;
        }
        Color clorCloud = tool.interpolateColor(clorCStart, clorCEnd, ratio);
        // cloud
        // Move 50 pixel per second
        movingCloud += speedCloud * elapsedTime / 1000.0;
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

    public void mushroomBlock(Graphics g) {
        e.luckyBox(g, 100, 300, 1);
    }

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
        e.luckyBox(g, brickX + 125, brickY - 100, 1);
    }
    // ____________________Map____________________

    // time controler----------------------------------------
    // element controler----------------------------------------
    public void moonAndSun(Graphics g, int[] points, int size) {
        BufferedImage buffer = new BufferedImage(600, 600, BufferedImage.TYPE_INT_ARGB);

        // my oval
        int thickness = 1;
        size /= 2;
        tool.drawEllipse(g, points[0], points[1], size, size, Color.ORANGE, Color.BLACK, thickness);
        tool.drawEllipse(g, points[2], points[3], size, size, Color.white, Color.BLACK, thickness);

        // Draw the image buffer to the screen
        g.drawImage(buffer, 0, 0, null);
    }

    // element controler
    // -----------------------------------------------------------
    // Combine elements
    public void drawGround(Graphics g) {
        int boxSizeScale = 1;
        int boxSize = boxSizeScale * 25;
        for (int y = startHeightGround; y < height; y += boxSize) {
            for (int x = 0; x < width; x += boxSize) {
                e.boxGround(g, x, y, boxSizeScale);
            }
        }
    }

    // sky
    public void drawnSky(Graphics g, int y) {
        BufferedImage buffer = new BufferedImage(600, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = buffer.createGraphics();
        Color colorSStart = Color.decode("#61DEF2");// light color
        Color colorSEnd = Color.decode("#024DC0");// dark color
        float count = 100f;
        float height = 600f;
        float stepSize = height / count;
        // sky
        for (int i = 0; i <= count; i++) {
            float ratio = (float) i / count;
            Color colorCurrent = tool.interpolateColor(colorSStart, colorSEnd, ratio);
            int currentY = (int) ((ratio * height));
            g2.setColor(colorCurrent);
            g2.setStroke(new BasicStroke(stepSize + 1));
            g2.drawLine(-1, y + currentY, 601, y + currentY);
            g2.drawLine(-1, y - currentY, 601, y - currentY);
        }
        g.drawImage(buffer, 0, 0, null);
    }

    // Combine elements

}

class Tool {
    // tool ----------------------------------------
    public void plot(Graphics g, int x, int y) {
        g.fillRect(x, y, 1, 1);
    }

    public void fillRectMine(Graphics g, int x, int y, int thickness) {
        int halfThickness = thickness / 2;
        for (int i = -halfThickness; i <= halfThickness; i++) {
            for (int j = -halfThickness; j <= halfThickness; j++) {
                plot(g, x + i, y + j);
            }
        }
    }

    public static void myFillRect(Graphics g, int x, int y, int width, int height) {
        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                g.fillRect(i, j, 1, 1); 
            }
        }
    }

    public boolean isInsideEllipse(int x, int y, int centerX, int centerY, int xRadius, int yRadius, int thickness) {
        double normalizedX = (x - centerX) / (double) (xRadius - thickness);
        double normalizedY = (y - centerY) / (double) (yRadius - thickness);
        double equationResult = Math.pow(normalizedX, 2) + Math.pow(normalizedY, 2);
        return equationResult < 1;
    }

    public void drawEllipse(Graphics g, int centerX, int centerY, int a, int b, Color color, Color colorBorder,
            int thickness) {
        BufferedImage buffer = new BufferedImage(600, 600, BufferedImage.TYPE_INT_ARGB);

        drawEllipse(buffer, centerX, centerY, a, b, color, colorBorder, thickness);

        g.drawImage(buffer, 0, 0, null);
    }

    public void drawEllipse(BufferedImage buffer, int centerX, int centerY, int a, int b, Color color,
            Color colorBorder, int thickness) {
        int a2 = a * a;
        int b2 = b * b;
        int twoA2 = 2 * a2;
        int twoB2 = 2 * b2;
        // REGION 1
        int x = 0;
        int y = b;
        int D = Math.round(b2 - a2 * b + a2 / 4);
        int Dx = 0;
        int Dy = twoA2 * y;

        while (Dx <= Dy) {
            plotQuadrants(buffer, centerX, centerY, x, y, colorBorder, thickness);
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
            plotQuadrants(buffer, centerX, centerY, x, y, colorBorder, thickness);
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
        int xR = a;
        int yR = b;
        if (!isVarid(x, y)) {
            int xStart = x - xR;
            int yStart = y - yR;
            int xEnd = x + xR;
            int yEnd = y + yR;

            for (int i = xStart; i <= xEnd; i++) {
                for (int j = yStart; j <= yEnd; j++) {
                    if (isVarid(i, j) && isInsideEllipse(i, j, centerX, centerY, xR, yR, thickness)) {
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

        buffer = floodFill02(buffer, (int) (x), (int) (y), colorBorder, color);

    }

    public void plotQuadrants(BufferedImage buffer, int centerX, int centerY, int x, int y, Color color,
            int thickness) {
        setRGBMine(buffer, centerX + x, centerY + y, thickness, color);
        setRGBMine(buffer, centerX - x, centerY + y, thickness, color);
        setRGBMine(buffer, centerX + x, centerY - y, thickness, color);
        setRGBMine(buffer, centerX - x, centerY - y, thickness, color);
    }

    public void setRGBMine(BufferedImage buffer, int x, int y, int thickness, Color color) {
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
    }

    public boolean isVarid(int x, int y) {
        return (x > -1 && x < 601) && (y > -1 && y < 601);
    }

    public BufferedImage floodFill02(BufferedImage image, int x, int y, Color border, Color replace) {
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

    public Color interpolateColor(Color startColor, Color endColor, float ratio) {

        int red = (int) (startColor.getRed() + ratio * (endColor.getRed() - startColor.getRed()));
        int green = (int) (startColor.getGreen() + ratio * (endColor.getGreen() - startColor.getGreen()));
        int blue = (int) (startColor.getBlue() + ratio * (endColor.getBlue() - startColor.getBlue()));
        int alpha = (int) (startColor.getAlpha() + ratio * (endColor.getAlpha() - startColor.getAlpha()));

        return new Color(red, green, blue, alpha);
    }
}

class Element {

    Tool tool = new Tool();

    // bush
    public void bush(Graphics g, int x, int y, double size, double lean) {
        BufferedImage buffer = new BufferedImage(600, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = buffer.createGraphics();
        x /= size;
        y /= size;
        int thickness = 2;
        Color color = Color.decode("#85B905");
        g2d.setColor(Color.decode("#0D7F0D"));
        g2d.setStroke(new BasicStroke(thickness));
        g2d.draw(new QuadCurve2D.Double((x) * size, (y + 34) * size, (x + 16 + lean) * size, (y + 9) * size,
                (x + 21) * size, (y + 14) * size));
        g2d.draw(new QuadCurve2D.Double((x + 21) * size, (y + 14) * size, (x + 38 + lean) * size, (y + 3) * size,
                (x + 48) * size, (y + 11) * size));
        g2d.draw(new QuadCurve2D.Double((x + 48) * size, (y + 11) * size, (x + 60 + lean) * size, (y + 2) * size,
                (x + 71) * size, (y + 15) * size));
        g2d.draw(new QuadCurve2D.Double((x + 71) * size, (y + 15) * size, (x + 86 + lean) * size, (y + 7) * size,
                (x + 101) * size, (y + 15) * size));
        g2d.draw(new QuadCurve2D.Double((x + 71) * size, (y + 15) * size, (x + 86 + lean) * size, (y + 7) * size,
                (x + 101) * size, (y + 15) * size));
        g2d.draw(new QuadCurve2D.Double((x + 101) * size, (y + 15) * size, (x + 125 + lean) * size, (y + 13) * size,
                (x + 130) * size, (y + 34) * size));
        g2d.draw(new QuadCurve2D.Double((x + 130) * size, (y + 34) * size, (x + 64 + lean) * size, (y + 40) * size,
                (x) * size, (y + 34) * size));
        buffer = tool.floodFill02(buffer, (int) ((x + 58) * size), (int) ((y + 24) * size), Color.decode("#0D7F0D"),
                color);
        g.drawImage(buffer, 0, 0, null);
    }

    // luckyBox
    public void luckyBox(Graphics g, int x, int y, int size) {
        x /= size;
        y /= size;
        int thickness = 2;
        int halfThickness = thickness / 2;
        Color color = Color.decode("#F78E2F");
        int era = 25 * size;

        Graphics2D g2d = (Graphics2D) g;
        g.setColor(color);
        g.fillRect(x, y, era, era);

        g2d.setStroke(new BasicStroke(thickness));

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
        g2d.drawLine(x + halfThickness, y + halfThickness - 1, x + era - halfThickness, y + halfThickness - 1);
        g2d.drawLine(x + halfThickness, y + halfThickness, x + halfThickness, y + era - halfThickness);
        g.setColor(Color.black);
        g2d.drawLine(x + era - halfThickness, y + era - halfThickness, x + era - halfThickness, y + halfThickness);
        g2d.drawLine(x + era - halfThickness, y + era - halfThickness, x + halfThickness, y + era - halfThickness);

        int margin = 2;
        int addMargin = 3;
        int dotSize = 2;
        g2d.fillRect(x + margin, y + margin, dotSize, dotSize);
        g2d.fillRect(x + era - margin - addMargin, y + margin, dotSize, dotSize);
        g2d.fillRect(x + margin, y + era - margin - addMargin, dotSize, dotSize);
        g2d.fillRect(x + era - margin - addMargin, y + era - margin - addMargin, dotSize, dotSize);

    }

    // boxBrick
    public void boxBrick(Graphics g, int x, int y, int size) {
        x /= size;
        y /= size;
        int thickness = 2;
        int halfThickness = thickness / 2;
        Color color = Color.decode("#C84C0C");
        int era = 25 * size;

        Graphics2D g2d = (Graphics2D) g;
        g.setColor(color);
        g.fillRect(x, y, era, era);

        g2d.setStroke(new BasicStroke(thickness));

        g.setColor(Color.black);
        int count = 4, dis = era / count;
        int halfEra = era / 2;
        int halfEra2 = halfEra / 2;
        int index = 1;
        for (int i = dis; i < era; i += dis) {
            g2d.drawLine(x + halfThickness, y + i, x + era - halfThickness, y + i);

            if (index % 2 != 0) {
                g2d.drawLine(x + halfEra, y + i, x + halfEra, y + i - dis);
                g2d.drawLine(x + era - halfThickness, y + i, x + era - halfThickness, y + i - dis);
            } else {
                g2d.drawLine(x + halfEra2, y + i, x + halfEra2, y + i - dis);
                g2d.drawLine(x + era - halfEra2, y + i, x + era - halfEra2, y + i - dis);
            }
            index++;
        }

        // border
        g.setColor(Color.decode("#FFB39C"));
        g2d.drawLine(x + halfThickness, y + halfThickness - 1, x + era - halfThickness, y + halfThickness - 1);
        g.setColor(Color.black);
        g2d.drawLine(x + era - halfThickness, y + era - halfThickness, x + halfThickness, y + era - halfThickness);
    }

    // boxGround
    public void boxGround(Graphics g, int x, int y, int size) {
        x /= size;
        y /= size;
        int thickness = 2;
        int halfThickness = thickness / 2;
        Color color = Color.decode("#C84C0C");
        int era = 25 * size;

        Graphics2D g2d = (Graphics2D) g;
        g.setColor(color);
        g.fillRect(x, y, era, era);

        // border
        g2d.setStroke(new BasicStroke(thickness));
        g.setColor(Color.decode("#F1C5A7"));
        g2d.drawLine(x + halfThickness, y + halfThickness - 1, x + era - halfThickness, y + halfThickness - 1);
        g2d.drawLine(x + halfThickness, y + halfThickness, x + halfThickness, y + era - halfThickness);
        g.setColor(Color.black);
        g2d.drawLine(x + era - halfThickness, y + era - halfThickness, x + era - halfThickness, y + halfThickness);
        g2d.drawLine(x + era - halfThickness, y + era - halfThickness, x + halfThickness, y + era - halfThickness);

        g.setColor(Color.black);
        g2d.draw(new QuadCurve2D.Double(x + (era - 7), y, x + (era - 5), y + (era - 15), x + 10,
                y + era - halfThickness));
        g2d.draw(new QuadCurve2D.Double(x + (era - 7), y + (era - 15), x + (era - 3), y + (era - 12), x + era,
                y + (era - 15)));
        g2d.draw(new QuadCurve2D.Double(x, y + (era - 10), x + 5, y + (era - 8), x + (era - 10), y + (era - 10)));
    }

    // cloud
    public void cloud(Graphics g, int x, int y, double size, Color color) {
        BufferedImage buffer = new BufferedImage(600, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = buffer.createGraphics();
        x /= size;
        y /= size;
        int thickness = 2;

        g2.setColor(Color.BLACK);
        AffineTransform originalTransform = g2.getTransform();

        AffineTransform transform = new AffineTransform();
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
        tool.lowPixelBezierCurve(g2, (x + 11) * size, (y + 11) * size, (x + 20) * size, (y - 10) * size,
                (x + 36) * size, (y + 6) * size, thickness);
        tool.lowPixelBezierCurve(g2, (x + 36) * size, (y + 6) * size, (x + 44) * size, (y - 10) * size, (x + 56) * size,
                (y + 6) * size, thickness);
        tool.lowPixelBezierCurve(g2, (x + 56) * size, (y + 6) * size, (x + 68) * size, (y - 10) * size, (x + 79) * size,
                (y + 10) * size, thickness);
        tool.lowPixelBezierCurve(g2, (x + 79) * size, (y + 10) * size, (x + 101) * size, (y + 20) * size,
                (x + 85) * size, (y + 25) * size, thickness);
        tool.lowPixelBezierCurve(g2, (x + 85) * size, (y + 25) * size, (x + 81) * size, (y + 39) * size,
                (x + 70) * size, (y + 27) * size, thickness);
        tool.lowPixelBezierCurve(g2, (x + 70) * size, (y + 27) * size, (x + 65) * size, (y + 35) * size,
                (x + 60) * size, (y + 27) * size, thickness);
        tool.lowPixelBezierCurve(g2, (x + 60) * size, (y + 27) * size, (x + 55) * size, (y + 31) * size,
                (x + 50) * size, (y + 27) * size, thickness);
        tool.lowPixelBezierCurve(g2, (x + 50) * size, (y + 27) * size, (x + 45) * size, (y + 35) * size,
                (x + 40) * size, (y + 27) * size, thickness);

        tool.lowPixelBezierCurve(g2, (x + 40) * size, (y + 27) * size, (x + 35) * size, (y + 31) * size,
                (x + 30) * size, (y + 27) * size, thickness);
        tool.lowPixelBezierCurve(g2, (x + 30) * size, (y + 27) * size, (x + 20) * size, (y + 30) * size,
                (x + 15) * size, (y + 27) * size, thickness);
        tool.lowPixelBezierCurve(g2, (x + 15) * size, (y + 27) * size, x * size, (y + 30) * size, (x + 11) * size,
                (y + 11) * size, thickness);

        g2.setTransform(originalTransform);
        g2.setColor(Color.red);
        buffer = tool.floodFill02(buffer, (int) transformedPoint.x, (int) transformedPoint.y, Color.BLACK, color);
        g.drawImage(buffer, 0, 0, null);
    }

    // mushroom
    Color mushroom1 = new Color(254, 151, 59);
    Color mushroom2 = new Color(213, 42, 0);
    Color mushroom3 = new Color(252, 252, 252);

    public void drawMushroom(int x, int y, int size, Graphics g) {
        g.setColor(mushroom1);
        rect(g, x, y, size, size, 1, 4, 14, 1);
        rect(g, x, y, size, size, 0, 5, 16, 4);
        rect(g, x, y, size, size, 1, 9, 14, 2);
        rect(g, x, y, size, size, 2, 11, 12, 1);
        rect(g, x, y, size, size, 3, 12, 10, 1);
        rect(g, x, y, size, size, 4, 13, 8, 1);
        rect(g, x, y, size, size, 5, 14, 6, 1);
        rect(g, x, y, size, size, 6, 15, 4, 1);
        g.setColor(mushroom3);
        rect(g, x, y, size, size, 4, 1, 8, 3);
        rect(g, x, y, size, size, 5, 0, 6, 5);
        g.setColor(mushroom1);
        rect(g, x, y, size, size, 9, 0, 1, 1);
        rect(g, x, y, size, size, 10, 1, 1, 2);
        g.setColor(mushroom2);
        rect(g, x, y, size, size, 2, 4, 3, 1);
        rect(g, x, y, size, size, 11, 4, 3, 1);
        rect(g, x, y, size, size, 3, 6, 3, 5);
        rect(g, x, y, size, size, 2, 7, 5, 3);
        rect(g, x, y, size, size, 13, 6, 2, 2);
        rect(g, x, y, size, size, 12, 7, 2, 2);
        rect(g, x, y, size, size, 9, 11, 3, 1);
        rect(g, x, y, size, size, 8, 12, 5, 1);
        rect(g, x, y, size, size, 8, 13, 4, 1);
        rect(g, x, y, size, size, 9, 14, 2, 1);

    }

    // mario
    Color mario1 = new Color(255, 0, 0);
    Color mario2 = new Color(165, 42, 42);
    Color mario3 = new Color(255, 165, 0);

    public void drawMarioFrame(int n, int x, int y, int sizeX, int sizeY, Graphics g) {
        switch (n) {
            case 1:
                marioFrame1(x, y, sizeX, sizeY, g);
                break;
            case 2:
                marioFrame2(x, y, sizeX, sizeY, g);
                break;
            case 3:
                marioFrame3(x, y, sizeX, sizeY, g);
                break;
            case 4:
                marioFrame4(x, y, sizeX, sizeY, g);
                break;
        }
    }

    public void rect(Graphics g, int x, int y, int n, int m, int posX, int posY, int sizeX, int sizeY) {
        g.fillRect(x + (posX * n), y - (posY * m) - (sizeY * m), (sizeX * n), (sizeY * m));
    }

    public void marioFrame1(int x, int y, int n, int m, Graphics g) {
        g.setColor(mario2);
        rect(g, x, y, n, m, 2, 0, 3, 1);
        rect(g, x, y, n, m, 1, 1, 3, 2);
        rect(g, x, y, n, m, 0, 3, 5, 1);
        rect(g, x, y, n, m, 0, 4, 4, 1);
        rect(g, x, y, n, m, 0, 5, 3, 1);
        rect(g, x, y, n, m, 2, 6, 1, 1);
        g.setColor(mario1);
        rect(g, x, y, n, m, 5, 3, 1, 1);
        rect(g, x, y, n, m, 4, 4, 4, 1);
        rect(g, x, y, n, m, 3, 5, 3, 1);
        rect(g, x, y, n, m, 3, 6, 2, 1);
        rect(g, x, y, n, m, 3, 7, 1, 1);
        g.setColor(mario2);
        rect(g, x, y, n, m, 6, 5, 2, 1);
        rect(g, x, y, n, m, 5, 6, 2, 1);
        rect(g, x, y, n, m, 4, 7, 1, 1);
        g.setColor(mario1);
        rect(g, x, y, n, m, 8, 5, 3, 9);
        rect(g, x, y, n, m, 6, 6, 3, 8);
        rect(g, x, y, n, m, 5, 7, 1, 6);
        rect(g, x, y, n, m, 4, 8, 1, 2);
        rect(g, x, y, n, m, 4, 12, 3, 1);
        rect(g, x, y, n, m, 11, 9, 2, 4);
        rect(g, x, y, n, m, 7, 14, 3, 1);
        rect(g, x, y, n, m, 8, 15, 2, 3);
        rect(g, x, y, n, m, 7, 18, 2, 1);
        rect(g, x, y, n, m, 4, 19, 4, 1);
        rect(g, x, y, n, m, 12, 14, 1, 3);
        rect(g, x, y, n, m, 11, 17, 1, 2);
        rect(g, x, y, n, m, 10, 19, 1, 1);
        g.setColor(mario2);
        rect(g, x, y, n, m, 11, 4, 5, 5);
        rect(g, x, y, n, m, 14, 9, 2, 1);
        rect(g, x, y, n, m, 15, 10, 1, 1);
        rect(g, x, y, n, m, 9, 12, 1, 2);
        rect(g, x, y, n, m, 1, 12, 3, 5);
        rect(g, x, y, n, m, 2, 13, 4, 5);
        rect(g, x, y, n, m, 3, 14, 4, 5);
        rect(g, x, y, n, m, 7, 15, 1, 3);
        g.setColor(mario3);
        rect(g, x, y, n, m, 0, 9, 1, 3);
        rect(g, x, y, n, m, 1, 8, 3, 4);
        rect(g, x, y, n, m, 4, 10, 1, 2);
        rect(g, x, y, n, m, 9, 13, 1, 1);
        rect(g, x, y, n, m, 12, 13, 1, 1);
        g.setColor(mario2);
        rect(g, x, y, n, m, 11, 13, 1, 4);
        rect(g, x, y, n, m, 10, 14, 1, 5);
        rect(g, x, y, n, m, 9, 18, 1, 2);
        rect(g, x, y, n, m, 8, 19, 1, 1);
        rect(g, x, y, n, m, 12, 17, 1, 1);
        rect(g, x, y, n, m, 13, 13, 1, 3);
        rect(g, x, y, n, m, 14, 14, 1, 1);
        rect(g, x, y, n, m, 15, 15, 1, 1);
        g.setColor(mario3);
        rect(g, x, y, n, m, 13, 16, 3, 3);
        rect(g, x, y, n, m, 14, 15, 1, 5);
        g.setColor(mario2);
        rect(g, x, y, n, m, 5, 20, 3, 1);
        rect(g, x, y, n, m, 3, 21, 2, 1);
        rect(g, x, y, n, m, 1, 22, 3, 1);
        rect(g, x, y, n, m, 1, 23, 2, 2);
        rect(g, x, y, n, m, 2, 25, 1, 2);
        rect(g, x, y, n, m, 3, 27, 3, 1);
        rect(g, x, y, n, m, 5, 24, 1, 3);
        rect(g, x, y, n, m, 6, 24, 1, 2);
        rect(g, x, y, n, m, 8, 26, 1, 2);
        rect(g, x, y, n, m, 9, 26, 1, 1);
        rect(g, x, y, n, m, 8, 23, 6, 1);
        rect(g, x, y, n, m, 9, 22, 5, 1);
        rect(g, x, y, n, m, 10, 24, 1, 1);
        g.setColor(mario3);
        rect(g, x, y, n, m, 3, 24, 2, 3);
        rect(g, x, y, n, m, 3, 23, 5, 1);
        rect(g, x, y, n, m, 4, 22, 5, 1);
        rect(g, x, y, n, m, 5, 21, 8, 1);
        rect(g, x, y, n, m, 8, 20, 2, 1);
        rect(g, x, y, n, m, 6, 26, 2, 2);
        rect(g, x, y, n, m, 7, 24, 3, 2);
        rect(g, x, y, n, m, 9, 27, 3, 1);
        rect(g, x, y, n, m, 10, 25, 4, 2);
        rect(g, x, y, n, m, 11, 24, 4, 2);
        rect(g, x, y, n, m, 9, 29, 2, 1);
        rect(g, x, y, n, m, 10, 30, 1, 1);
        g.setColor(mario1);
        rect(g, x, y, n, m, 3, 28, 11, 1);
        rect(g, x, y, n, m, 3, 29, 6, 1);
        rect(g, x, y, n, m, 4, 30, 6, 1);
        rect(g, x, y, n, m, 6, 31, 5, 1);
    }

    public void marioFrame2(int x, int y, int n, int m, Graphics g) {
        g.setColor(mario2);
        rect(g, x, y, n, m, 0, 1, 1, 1);
        rect(g, x, y, n, m, 0, 2, 2, 1);
        rect(g, x, y, n, m, 0, 3, 4, 5);
        rect(g, x, y, n, m, 9, 2, 4, 2);
        rect(g, x, y, n, m, 9, 0, 6, 2);
        rect(g, x, y, n, m, 8, 5, 1, 1);
        rect(g, x, y, n, m, 9, 6, 2, 1);
        rect(g, x, y, n, m, 11, 7, 1, 1);
        rect(g, x, y, n, m, 12, 8, 1, 1);
        g.setColor(mario1);
        rect(g, x, y, n, m, 4, 4, 3, 8);
        rect(g, x, y, n, m, 3, 8, 1, 7);
        rect(g, x, y, n, m, 4, 12, 2, 2);
        rect(g, x, y, n, m, 4, 14, 1, 4);
        rect(g, x, y, n, m, 7, 5, 1, 6);
        rect(g, x, y, n, m, 8, 6, 1, 5);
        rect(g, x, y, n, m, 9, 7, 2, 3);
        rect(g, x, y, n, m, 11, 8, 1, 2);
        rect(g, x, y, n, m, 12, 9, 1, 2);
        rect(g, x, y, n, m, 9, 4, 4, 2);
        rect(g, x, y, n, m, 11, 6, 3, 1);
        rect(g, x, y, n, m, 12, 7, 2, 1);
        g.setColor(mario2);
        rect(g, x, y, n, m, 2, 13, 1, 3);
        rect(g, x, y, n, m, 3, 15, 1, 3);
        rect(g, x, y, n, m, 4, 18, 1, 1);
        rect(g, x, y, n, m, 5, 14, 1, 4);
        rect(g, x, y, n, m, 6, 12, 1, 6);
        rect(g, x, y, n, m, 7, 11, 1, 7);
        rect(g, x, y, n, m, 8, 11, 1, 6);
        rect(g, x, y, n, m, 9, 10, 1, 6);
        rect(g, x, y, n, m, 10, 10, 2, 5);
        rect(g, x, y, n, m, 12, 11, 1, 2);
        rect(g, x, y, n, m, 9, 17, 1, 1);
        rect(g, x, y, n, m, 10, 16, 1, 1);
        g.setColor(mario1);
        rect(g, x, y, n, m, 5, 18, 3, 1);
        rect(g, x, y, n, m, 8, 17, 1, 1);
        rect(g, x, y, n, m, 9, 16, 1, 1);
        rect(g, x, y, n, m, 10, 15, 2, 1);
        g.setColor(mario3);
        rect(g, x, y, n, m, 12, 13, 2, 3);
        rect(g, x, y, n, m, 13, 11, 3, 4);
        rect(g, x, y, n, m, 4, 21, 2, 4);
        rect(g, x, y, n, m, 5, 20, 5, 1);
        rect(g, x, y, n, m, 6, 21, 3, 1);
        rect(g, x, y, n, m, 6, 19, 8, 1);
        rect(g, x, y, n, m, 8, 18, 3, 1);
        rect(g, x, y, n, m, 8, 22, 3, 2);
        rect(g, x, y, n, m, 7, 24, 2, 2);
        rect(g, x, y, n, m, 10, 25, 3, 1);
        rect(g, x, y, n, m, 11, 23, 4, 2);
        rect(g, x, y, n, m, 12, 22, 4, 2);
        g.setColor(mario2);
        rect(g, x, y, n, m, 5, 19, 1, 1);
        rect(g, x, y, n, m, 3, 20, 2, 1);
        rect(g, x, y, n, m, 2, 21, 2, 2);
        rect(g, x, y, n, m, 3, 23, 1, 2);
        rect(g, x, y, n, m, 4, 25, 3, 1);
        rect(g, x, y, n, m, 6, 24, 1, 1);
        rect(g, x, y, n, m, 6, 22, 2, 2);
        rect(g, x, y, n, m, 9, 25, 1, 1);
        rect(g, x, y, n, m, 9, 24, 2, 1);
        rect(g, x, y, n, m, 9, 21, 6, 1);
        rect(g, x, y, n, m, 10, 20, 5, 1);
        rect(g, x, y, n, m, 11, 22, 1, 1);
        g.setColor(mario1);
        rect(g, x, y, n, m, 4, 26, 11, 1);
        rect(g, x, y, n, m, 4, 27, 1, 1);
        rect(g, x, y, n, m, 5, 27, 2, 2);
        rect(g, x, y, n, m, 7, 27, 5, 3);
        g.setColor(mario3);
        rect(g, x, y, n, m, 10, 27, 2, 1);
        rect(g, x, y, n, m, 11, 28, 1, 1);
    }

    public void marioFrame3(int x, int y, int n, int m, Graphics g) {
        g.setColor(mario2);
        rect(g, x, y, n, m, 5, 0, 5, 2);
        rect(g, x, y, n, m, 3, 1, 5, 2);
        rect(g, x, y, n, m, 4, 3, 4, 1);
        rect(g, x, y, n, m, 4, 4, 1, 1);
        g.setColor(mario1);
        rect(g, x, y, n, m, 5, 4, 3, 1);
        rect(g, x, y, n, m, 4, 5, 2, 1);
        rect(g, x, y, n, m, 3, 6, 2, 5);
        rect(g, x, y, n, m, 2, 8, 2, 4);
        rect(g, x, y, n, m, 1, 9, 1, 4);
        rect(g, x, y, n, m, 2, 12, 1, 6);
        rect(g, x, y, n, m, 5, 7, 1, 3);
        rect(g, x, y, n, m, 6, 8, 1, 2);
        g.setColor(mario2);
        rect(g, x, y, n, m, 8, 4, 1, 1);
        rect(g, x, y, n, m, 9, 3, 2, 2);
        rect(g, x, y, n, m, 6, 5, 4, 1);
        rect(g, x, y, n, m, 5, 6, 6, 1);
        rect(g, x, y, n, m, 6, 7, 6, 1);
        rect(g, x, y, n, m, 7, 8, 3, 1);
        rect(g, x, y, n, m, 7, 9, 2, 1);
        g.setColor(mario1);
        rect(g, x, y, n, m, 10, 8, 2, 1);
        rect(g, x, y, n, m, 9, 9, 4, 1);
        rect(g, x, y, n, m, 10, 10, 3, 1);
        rect(g, x, y, n, m, 11, 11, 2, 1);
        rect(g, x, y, n, m, 11, 12, 1, 1);
        g.setColor(mario3);
        rect(g, x, y, n, m, 7, 10, 1, 4);
        rect(g, x, y, n, m, 8, 10, 2, 5);
        rect(g, x, y, n, m, 10, 11, 1, 3);
        g.setColor(mario2);
        rect(g, x, y, n, m, 1, 13, 1, 5);
        rect(g, x, y, n, m, 2, 18, 1, 1);
        rect(g, x, y, n, m, 3, 12, 4, 6);
        rect(g, x, y, n, m, 4, 11, 3, 1);
        rect(g, x, y, n, m, 5, 10, 2, 1);
        rect(g, x, y, n, m, 4, 18, 2, 1);
        rect(g, x, y, n, m, 7, 14, 1, 1);
        rect(g, x, y, n, m, 10, 14, 1, 3);
        rect(g, x, y, n, m, 9, 16, 1, 3);
        rect(g, x, y, n, m, 8, 18, 1, 1);
        g.setColor(mario1);
        rect(g, x, y, n, m, 7, 15, 3, 1);
        rect(g, x, y, n, m, 7, 16, 2, 2);
        rect(g, x, y, n, m, 6, 18, 2, 1);
        rect(g, x, y, n, m, 3, 18, 1, 1);
        rect(g, x, y, n, m, 3, 19, 4, 1);
        g.setColor(mario2);
        rect(g, x, y, n, m, 2, 20, 2, 1);
        rect(g, x, y, n, m, 1, 21, 2, 1);
        rect(g, x, y, n, m, 0, 22, 2, 2);
        rect(g, x, y, n, m, 1, 24, 1, 2);
        rect(g, x, y, n, m, 2, 26, 3, 1);
        rect(g, x, y, n, m, 4, 23, 1, 3);
        rect(g, x, y, n, m, 5, 23, 1, 2);
        rect(g, x, y, n, m, 7, 25, 1, 2);
        rect(g, x, y, n, m, 8, 25, 1, 1);
        rect(g, x, y, n, m, 7, 22, 6, 1);
        rect(g, x, y, n, m, 8, 21, 5, 1);
        rect(g, x, y, n, m, 9, 23, 1, 1);
        g.setColor(mario3);
        rect(g, x, y, n, m, 7, 19, 2, 1);
        rect(g, x, y, n, m, 4, 20, 8, 1);
        rect(g, x, y, n, m, 3, 21, 5, 1);
        rect(g, x, y, n, m, 2, 22, 5, 1);
        rect(g, x, y, n, m, 2, 23, 2, 3);
        rect(g, x, y, n, m, 5, 25, 2, 2);
        rect(g, x, y, n, m, 6, 23, 3, 2);
        rect(g, x, y, n, m, 8, 26, 3, 1);
        rect(g, x, y, n, m, 9, 24, 4, 2);
        rect(g, x, y, n, m, 10, 23, 4, 2);
        rect(g, x, y, n, m, 8, 28, 2, 1);
        rect(g, x, y, n, m, 9, 29, 1, 1);
        g.setColor(mario1);
        rect(g, x, y, n, m, 2, 27, 11, 1);
        rect(g, x, y, n, m, 2, 28, 6, 1);
        rect(g, x, y, n, m, 3, 29, 6, 1);
        rect(g, x, y, n, m, 5, 30, 5, 1);
    }

    public void marioFrame4(int x, int y, int n, int m, Graphics g) {
        g.setColor(mario2);
        rect(g, x, y, n, m, 0, 0, 1, 7);
        rect(g, x, y, n, m, 1, 1, 1, 6);
        rect(g, x, y, n, m, 2, 2, 1, 5);
        g.setColor(mario1);
        rect(g, x, y, n, m, 3, 2, 1, 5);
        rect(g, x, y, n, m, 4, 2, 2, 4);
        rect(g, x, y, n, m, 6, 2, 1, 3);
        rect(g, x, y, n, m, 7, 3, 1, 2);
        g.setColor(mario2);
        rect(g, x, y, n, m, 3, 7, 1, 1);
        rect(g, x, y, n, m, 4, 6, 2, 1);
        rect(g, x, y, n, m, 6, 5, 2, 1);
        g.setColor(mario1);
        rect(g, x, y, n, m, 3, 8, 1, 2);
        rect(g, x, y, n, m, 4, 7, 1, 4);
        rect(g, x, y, n, m, 5, 7, 1, 5);
        rect(g, x, y, n, m, 6, 6, 1, 7);
        rect(g, x, y, n, m, 7, 6, 1, 8);
        rect(g, x, y, n, m, 8, 5, 1, 13);
        rect(g, x, y, n, m, 7, 17, 1, 2);
        rect(g, x, y, n, m, 4, 18, 3, 1);
        rect(g, x, y, n, m, 9, 5, 1, 11);
        rect(g, x, y, n, m, 10, 4, 1, 10);
        rect(g, x, y, n, m, 11, 4, 1, 9);
        rect(g, x, y, n, m, 12, 9, 1, 6);
        rect(g, x, y, n, m, 11, 15, 1, 2);
        rect(g, x, y, n, m, 10, 17, 1, 2);
        g.setColor(mario2);
        rect(g, x, y, n, m, 12, 4, 2, 5);
        rect(g, x, y, n, m, 14, 4, 1, 6);
        rect(g, x, y, n, m, 15, 4, 1, 7);
        rect(g, x, y, n, m, 0, 13, 1, 4);
        rect(g, x, y, n, m, 1, 14, 1, 4);
        rect(g, x, y, n, m, 2, 15, 2, 3);
        rect(g, x, y, n, m, 4, 14, 1, 4);
        rect(g, x, y, n, m, 5, 12, 1, 6);
        rect(g, x, y, n, m, 6, 13, 1, 5);
        rect(g, x, y, n, m, 7, 14, 1, 3);
        rect(g, x, y, n, m, 8, 18, 1, 1);
        rect(g, x, y, n, m, 9, 16, 1, 3);
        rect(g, x, y, n, m, 10, 14, 1, 3);
        rect(g, x, y, n, m, 11, 13, 1, 2);
        g.setColor(mario3);
        rect(g, x, y, n, m, 0, 10, 1, 3);
        rect(g, x, y, n, m, 1, 9, 1, 1);
        rect(g, x, y, n, m, 1, 11, 1, 3);
        rect(g, x, y, n, m, 2, 9, 1, 6);
        rect(g, x, y, n, m, 3, 10, 1, 5);
        rect(g, x, y, n, m, 4, 11, 1, 3);
        rect(g, x, y, n, m, 9, 12, 1, 1);
        rect(g, x, y, n, m, 12, 13, 1, 1);
        g.setColor(mario2);
        rect(g, x, y, n, m, 11, 17, 1, 2);
        rect(g, x, y, n, m, 12, 15, 1, 4);
        rect(g, x, y, n, m, 13, 16, 1, 4);
        rect(g, x, y, n, m, 14, 18, 1, 4);
        rect(g, x, y, n, m, 9, 20, 4, 1);
        rect(g, x, y, n, m, 8, 21, 6, 1);
        rect(g, x, y, n, m, 10, 22, 1, 1);
        rect(g, x, y, n, m, 3, 19, 2, 2);
        rect(g, x, y, n, m, 2, 20, 1, 5);
        rect(g, x, y, n, m, 1, 20, 1, 2);
        rect(g, x, y, n, m, 3, 25, 3, 1);
        rect(g, x, y, n, m, 5, 22, 1, 3);
        rect(g, x, y, n, m, 6, 22, 1, 2);
        rect(g, x, y, n, m, 8, 24, 1, 2);
        g.setColor(mario3);
        rect(g, x, y, n, m, 3, 21, 2, 4);
        rect(g, x, y, n, m, 5, 21, 3, 1);
        rect(g, x, y, n, m, 5, 20, 4, 1);
        rect(g, x, y, n, m, 5, 19, 8, 1);
        rect(g, x, y, n, m, 13, 20, 1, 1);
        rect(g, x, y, n, m, 6, 24, 2, 2);
        rect(g, x, y, n, m, 7, 22, 3, 2);
        rect(g, x, y, n, m, 9, 24, 3, 2);
        rect(g, x, y, n, m, 10, 23, 4, 2);
        rect(g, x, y, n, m, 11, 22, 4, 2);
        g.setColor(mario2);
        rect(g, x, y, n, m, 12, 25, 2, 1);
        rect(g, x, y, n, m, 14, 24, 1, 3);
        rect(g, x, y, n, m, 15, 22, 1, 5);
        rect(g, x, y, n, m, 11, 27, 5, 1);
        rect(g, x, y, n, m, 13, 29, 2, 1);
        rect(g, x, y, n, m, 13, 30, 1, 1);
        g.setColor(mario3);
        rect(g, x, y, n, m, 11, 28, 2, 3);
        rect(g, x, y, n, m, 13, 28, 3, 1);
        rect(g, x, y, n, m, 15, 29, 1, 1);
        rect(g, x, y, n, m, 14, 30, 2, 1);
        rect(g, x, y, n, m, 12, 31, 3, 1);
        g.setColor(mario1);
        rect(g, x, y, n, m, 3, 26, 11, 1);
        rect(g, x, y, n, m, 3, 27, 8, 1);
        rect(g, x, y, n, m, 4, 28, 7, 1);
        rect(g, x, y, n, m, 6, 29, 5, 1);
    }
}