import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;

public class ImageToMap {
    //src/
    public static String mapPath = "maps/";

    public static int[][] imageToMap(String filePath) {
        try {
            BufferedImage image = ImageIO.read(new File(    ImageToMap.class.getResource(mapPath + filePath).getPath()    )  );
            int[][] map = new int[image.getWidth()][image.getHeight()];

            //Iterating through image
            for (int i = 0; i < image.getWidth(); i++)
                for (int j = 0; j < image.getHeight(); j++) {
                    Color currentColor = new Color(image.getRGB(i, j));
                    //Setting int value for color
                    if(currentColor.equals(Color.RED)) map[i][j] = 1;
                    if(currentColor.equals(Color.BLUE)) map[i][j] = 2;
                    if(currentColor.equals(Color.GREEN)) map[i][j] = 3;
                    if(currentColor.equals(Color.BLACK)) map[i][j] = 4;

                    //Start and finish points
                    if(currentColor.equals(new Color(255,255,0))) map[i][j] = 5;
                    if(currentColor.equals(new Color(120,255,0))) map[i][j] = 6;

                    //Tutorials
                    if(currentColor.equals(new Color(69,255,0))) map[i][j] = 7;
                }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
