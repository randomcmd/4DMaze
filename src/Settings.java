import java.awt.*;

public abstract class Settings {

    public static double FPS = 60.0; //High FPS makes game unstable idk why //Not used anymore
    public static double FOV = 0.5; //Lower number -> Higher FOV
    public static Point resoulution = new Point(1920,1080); //Try out other resoulutions for weird effects
    public static boolean FULLSCREEN = true;

    //Does the player start crouching or sprinting? Mainly for cinematics
    public static boolean default_sprint_state = false;
    public static boolean default_crouch_state = false;

    //Can the player even crouch or sprint
    public static boolean enable_sprint = false;
    public static boolean enable_crouch = false;

    //Check for collision before teleporting?
    public static boolean check_collision_preport = true;
}
