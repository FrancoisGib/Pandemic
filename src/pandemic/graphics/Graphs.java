package pandemic.graphics;

import java.awt.Graphics;

import javax.swing.JPanel;

public class Graphs extends JPanel {
    public void paint(Graphics g){  
        this.drawRectPlusString(g, "Town", 100, 100, 200, 150);
        this.drawLine(g);
    }

    public void drawRectPlusString(Graphics g, String str, int c, int d, int a, int b) {
        g.drawRect(a, b, c, d);
        g.drawString(str, (a+c)/2, (b+d)/2);
    }

    public void drawLine(Graphics g) {
        g.drawLine(500, 300, 1000, 180);
    }

    public void drawString(Graphics g, String str) {
        g.drawString(str, 200, 200);
    }

}
