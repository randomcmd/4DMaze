import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;

public class Game extends JFrame implements Runnable {

    public static State state = State.START;

    private static final long serialVersionUID = 1L;
    private Thread thread;
    private boolean running;
    private BufferedImage image;
    public int[] pixels;
    public Camera camera;
    public Screen screen;

    public static int[][] map;

    //////Hello person reading my code :D
    public Game() {
        //Setup + Window
        thread = new Thread(this);
        image = new BufferedImage(Settings.resoulution.x, Settings.resoulution.y, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        camera = new Camera(1.5, 1.5, Settings.FOV, 0, 0, -.66, this);
        screen = new Screen(map, Texture.textures);
        addKeyListener(camera);
        setSize(Settings.resoulution.x, Settings.resoulution.y);
        setResizable(false);
        setTitle("Maze 4D");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.black);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon("src/textures/icon.png").getImage());

        //Hiding cursor
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "blank cursor");
        getContentPane().setCursor(blankCursor);

        //FULLSCREEN
        if (Settings.FULLSCREEN) {
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            setUndecorated(true);
        }
        setVisible(true);

        //Choose map
        map = ImageToMap.imageToMap("R.png");

        start();
    }

    FPSCounter fpsCounter;

    private synchronized void start() {
        running = true;
        if(Settings.music) new Music().play("src/music/tempmusic.wav");
        fpsCounter = new FPSCounter();
        fpsCounter.start();
        //new Music().play("src/music/drodolf1.wav");
        thread.start();
    }

    public synchronized void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
        bs.show();
    }

    public void run() {
        requestFocus();
        while (running) {

            long timeBefore;
            long timeAfter;
            long delta = 1;
            while (true)//Make sure update is only happening FPS times a second
            {
                timeBefore = System.nanoTime();
                //handles all of the logic restricted time
                try {screen.update(camera, pixels);}
                catch (Exception e) {nextMap();}
                camera.update(map, delta);
                render();//displays to the screen unrestricted time
                delta = System.nanoTime() - timeBefore;
                //System.out.println(delta);
            }
        }
    }

    int currentMapIndex = 0;
    int[][] tempmap; //used for detecting collision before entering map to avoid "stuck" state

    public void nextMap() {
        currentMapIndex++;
        switch (currentMapIndex) {
            default:
                tempmap = ImageToMap.imageToMap("R.png");
                currentMapIndex = 0;
                break;
            case 1:
                tempmap = ImageToMap.imageToMap("G.png");
                break;
            case 2:
                tempmap = ImageToMap.imageToMap("B.png");
                break;
        }
        if (!Settings.check_collision_preport) {
            map = tempmap;
            return;
        }
        if (Settings.check_collision_preport && tempmap[(int) (camera.xPos)][(int) camera.yPos] == 0 && tempmap != null)
            map = tempmap;
        else nextMap();
    }

    public static void main(String[] args) {
        Game game = new Game();
    }
}