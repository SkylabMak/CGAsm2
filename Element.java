import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.awt.image.BufferedImage;

public class Element {

    Tool tool = new Tool();

    // bush
    public void bush(Graphics g, int x, int y, double size, double lean) {
        BufferedImage buffer = new BufferedImage(600, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = buffer.createGraphics();
        x /= size;
        y /= size;
        int thickness = 2;
        int halfThickness = thickness / 2;
        Color color = Color.decode("#85B905");
        // Graphics2D g2d = (Graphics2D) g;
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
        // (x + 11) * size
        // Draw the square border
        int era = 25 * size;

        // Draw the border rectangle
        Graphics2D g2d = (Graphics2D) g;
        // Set the color for the fill
        g.setColor(color);
        // Draw the filled square
        g.fillRect(x, y, era, era);

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
        // (x + 11) * size
        // Draw the square border
        int era = 25 * size;

        // Draw the border rectangle
        Graphics2D g2d = (Graphics2D) g;
        // Set the color for the fill
        g.setColor(color);
        // Draw the filled square
        g.fillRect(x, y, era, era);

        g2d.setStroke(new BasicStroke(thickness));

        // g.drawRect(x, y, era, era);
        // g2d.setStroke(new BasicStroke(thickness-1));

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
        // g2d.drawLine(x+halfThickness, y+halfThickness, x+halfThickness,
        // y+era-halfThickness);
        g.setColor(Color.black);
        // g2d.drawLine(x+era-halfThickness, y+era-halfThickness, x+era-halfThickness,
        // y+halfThickness);
        g2d.drawLine(x + era - halfThickness, y + era - halfThickness, x + halfThickness, y + era - halfThickness);
    }

    // boxGround
    public void boxGround(Graphics g, int x, int y, int size) {
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
        g.fillRect(x, y, era, era);

        // border
        g2d.setStroke(new BasicStroke(thickness));
        g.setColor(Color.decode("#F1C5A7"));
        g2d.drawLine(x + halfThickness, y + halfThickness - 1, x + era - halfThickness, y + halfThickness - 1);
        g2d.drawLine(x + halfThickness, y + halfThickness, x + halfThickness, y + era - halfThickness);
        g.setColor(Color.black);
        g2d.drawLine(x + era - halfThickness, y + era - halfThickness, x + era - halfThickness, y + halfThickness);
        g2d.drawLine(x + era - halfThickness, y + era - halfThickness, x + halfThickness, y + era - halfThickness);
        // g.drawRect(x, y, era, era);
        // g2d.setStroke(new BasicStroke(thickness-1));

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
        // System.out.println("tset "+isVarid((int)originalPoint.x,
        // (int)originalPoint.y));
        // g2.fillRect((int) transformedPoint.x, (int) transformedPoint.y, 2, 2);
        buffer = tool.floodFill02(buffer, (int) transformedPoint.x, (int) transformedPoint.y, Color.BLACK, color);
        g.drawImage(buffer, 0, 0, null);
    }

    // mushroom
     Color mushroom1 = new Color(254, 151, 59);
     Color mushroom2 = new Color(213, 42, 0);
     Color mushroom3 = new Color(252, 252, 252);

    public  void drawMushroom(int x, int y, int size, Graphics g) {
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

    public  void drawMarioFrame(int n, int x, int y, int sizeX, int sizeY, Graphics g) {
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

    public  void rect(Graphics g, int x, int y, int n, int m, int posX, int posY, int sizeX, int sizeY) {
        g.fillRect(x + (posX * n), y - (posY * m) - (sizeY * m), (sizeX * n), (sizeY * m));
    }

    public  void marioFrame1(int x, int y, int n, int m, Graphics g) {
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

    public  void marioFrame2(int x, int y, int n, int m, Graphics g) {
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

    public  void marioFrame3(int x, int y, int n, int m, Graphics g) {
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

    public  void marioFrame4(int x, int y, int n, int m, Graphics g) {
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
