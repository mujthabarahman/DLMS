package UserInterface;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

class RoundButton extends JButton {
    private Color backgroundColor;
    private int arcSize;

    public RoundButton(String label, Color backgroundColor, int arcSize) {
        super(label);
        this.backgroundColor = backgroundColor;
        this.arcSize = arcSize;
        setUI(new BasicButtonUI());
        setContentAreaFilled(false);
        setFocusPainted(false);
        setOpaque(false);
    }

    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            g.setColor(backgroundColor.darker());
        } else {
            g.setColor(backgroundColor);
        }
        g.fillRoundRect(0, 0, getSize().width - 1, getSize().height - 1, arcSize, arcSize);
        super.paintComponent(g);
    }

    protected void paintBorder(Graphics g) {
        g.setColor(Color.black);
        g.drawRoundRect(0, 0, getSize().width - 1, getSize().height - 1, arcSize, arcSize);
    }

    Shape shape;

    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new java.awt.geom.RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, arcSize, arcSize);
        }
        return shape.contains(x, y);
    }
}
