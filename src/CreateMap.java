import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CreateMap {

    static int[][] brightnessMap = new int[500][500];

    public void createBrightnessMap() throws IOException {
        File file = new File("src\\img\\m2.jpg");
        BufferedImage image = ImageIO.read(file);

        for (int i = 0; i < 500; i++) {
            for (int j = 0; j < 500; j++) {
                Color color = new Color(image.getRGB(i, j));
                int brightness = color.getBlue();
                brightnessMap[i][j] = brightness;
            }
        }
    }
}