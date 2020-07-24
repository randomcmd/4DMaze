import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Texture {

    public int[] pixels;
    private String loc;
    public final int SIZE;

    public Texture(String location, int size) {
        loc = location;
        SIZE = size;
        pixels = new int[SIZE * SIZE];
        load();
    }

    private void load() {
        try {
            BufferedImage image = ImageIO.read(new File(getClass().getResource(loc).getPath()));
            int w = image.getWidth();
            int h = image.getHeight();
            image.getRGB(0, 0, w, h, pixels, 0, w);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Create Texture Array List "src/"
    static String texturePath = "textures/";
    public static ArrayList<Texture> textures = new ArrayList<>();
    static {
        textures.add(new Texture(texturePath + "red.png", 64));
        textures.add(new Texture(texturePath + "blue.png", 64));
        textures.add(new Texture(texturePath + "green.png", 64));
        textures.add(new Texture(texturePath + "black.png", 64));

        //Start and win
        textures.add(new Texture(texturePath + "start.png", 64));
        textures.add(new Texture(texturePath + "end.png", 64));

        //Tutorials
        textures.add(new Texture(texturePath + "tutorial1.png", 64));
    }
}