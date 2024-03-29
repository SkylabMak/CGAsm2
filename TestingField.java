import javax.swing.*;

import java.awt.*;

public class TestingField extends JPanel {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Lab6");
        TestingField l = new TestingField();
        frame.add(l);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 700);
        frame.setVisible(true);
    }

    public void paintComponent(Graphics g) {
        element.drawMarioFrame(1, 300, 200, 4, 4, g);
        element.drawMarioFrame(1, 100, 200, 3, 2, g);
        element.drawMarioFrame(2, 300, 350, 4, 4, g);
        element.drawMarioFrame(2, 100, 350, 3, 2, g);
        element.drawMarioFrame(3, 300, 500, 4, 4, g);
        element.drawMarioFrame(3, 100, 500, 3, 2, g);
        element.drawMarioFrame(4, 300, 650, 4, 4, g);
        element.drawMarioFrame(4, 100, 650, 3, 2, g);
        element.drawMushroom(200, 400, 4, g);
        g.setColor(Color.red);
    }
}

class element {

    static Color mario1 = new Color(255, 0, 0);
    static Color mario2 = new Color(165, 42, 42);
    static Color mario3 = new Color(255, 165, 0);

    static Color mushroom1 = new Color(254, 151, 59);
    static Color mushroom2 = new Color(213, 42, 0);
    static Color mushroom3 = new Color(252, 252, 252);

    public static void drawMarioFrame(int n, int x, int y, int sizeX, int sizeY, Graphics g) {
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

    public static void drawMushroom(int x, int y, int size, Graphics g) {
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

    private static void rect(Graphics g, int x, int y, int n, int m, int posX, int posY, int sizeX, int sizeY) {
        g.fillRect(x + (posX * n), y - (posY * m) - (sizeY * m), (sizeX * n), (sizeY * m));
    }

    private static void marioFrame1(int x, int y, int n, int m, Graphics g) {
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

    private static void marioFrame2(int x, int y, int n, int m, Graphics g) {
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

    private static void marioFrame3(int x, int y, int n, int m, Graphics g) {
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

    private static void marioFrame4(int x, int y, int n, int m, Graphics g) {
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