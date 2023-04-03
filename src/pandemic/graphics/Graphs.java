package pandemic.graphics;

import java.awt.Graphics;

import javax.swing.JPanel;

public class Graphs extends JPanel {
    String str;
    Graphics g;

    public Graphs(String str, Graphics g) {
        this.str = str;
        this.g = g;
    }

    public void paint(Graphics g) {
        g.drawString(this.str, 200, 200);
    }

    public void drawRectPlusString(Graphics g, String str, int c, int d, int a, int b) {
        g.drawRect(a, b, c, d);
        g.drawString(str, (a + c) / 2, (b + d) / 2);
    }

    public void drawLine(Graphics g) {
        g.drawLine(500, 300, 1000, 180);
    }

    public void drawString(Graphics g, String str) {
        g.drawString(str, 200, 200);
    }

}
