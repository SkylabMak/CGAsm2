import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.QuadCurve2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Queue;

public class Tool {
    // tool ----------------------------------------
    public void plot(Graphics g, int x, int y) {
        g.fillRect(x, y, 1, 1);
    }

    public void fillRectMine(Graphics g, int x, int y, int thickness) {
        int halfThickness = thickness / 2;
        // g.setColor(color);
        for (int i = -halfThickness; i <= halfThickness; i++) {
            for (int j = -halfThickness; j <= halfThickness; j++) {
                plot(g, x + i, y + j);
            }
        }
    }

    public static void myFillRect(Graphics g, int x, int y, int width, int height) {
        // Loop to fill the rectangle with individual pixels
        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                g.fillRect(i, j, 1, 1); // Drawing a single pixel
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
        // Graphics2D g2 = buffer.createGraphics();

        drawEllipse(buffer, centerX, centerY, a, b, color, colorBorder, thickness);
        // Draw the image buffer to the screen
        g.drawImage(buffer, 0, 0, null);
    }

    public void drawEllipse(BufferedImage buffer, int centerX, int centerY, int a, int b, Color color,
            Color colorBorder, int thickness) {
        int a2 = a * a;
        int b2 = b * b;
        int twoA2 = 2 * a2;
        int twoB2 = 2 * b2;
        // g2.setColor(colorBorder);
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
                    // setRGBMine(buffer,i, j,1,Color.red);
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
        // g2.setColor(Color.red);
        // g2.fillRect(x, y, 2,2);
        // setRGBMine(buffer,x, y,5,Color.red);
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
        // for (double t = 0; t <= 1; t += 0.01) {
        // double x = Math.pow(1 - t, 2) * x1 + 2 * (1 - t) * t * ctrlx1 + Math.pow(t,
        // 2) * x2;
        // double y = Math.pow(1 - t, 2) * y1 + 2 * (1 - t) * t * ctrly1 + Math.pow(t,
        // 2) * y2;
        // g.fillRect((int) x, (int) y, thickness, thickness);
        // Drawing a "pixel" ofsize 5x5
        // }
    }

    public boolean isVarid(int x, int y) {
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
