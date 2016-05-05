package UserInterface;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GameImage {
    public Image image;
    public Coord coord;

    public GameImage(String pathname) {
        try {
            image = ImageIO.read(new File(pathname));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GameImage(String pathname, int x, int y) {
        this(pathname);
        coord = new Coord(x, y);
    }
}