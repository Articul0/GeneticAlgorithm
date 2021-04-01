import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

class Draw extends Frame {

    Draw() throws IOException {
        setSize(608, 608);
        setTitle("Task X");
        setVisible(true);
        addWindowListener(new WindowAdapter() {
                              public void windowClosing(WindowEvent we) {
                                  System.exit(0);
                              }
                          }
        );
    }
    private final Image backgroundImage = ImageIO.read(new File("src\\img\\m2.jpg"));

    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(backgroundImage, 50, 50, null);

        List<Integer> pos = Solution.bestSolution;
        for (int i = 0; i < pos.get(0); i++) {
            int oX = pos.get(2 * i + 1);
            int oY = pos.get(2 * i + 2);
            g.setColor(Color.RED);
            g.drawOval(oX, oY, 100, 100);
        }
    }
}