import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class CameraWithStrafe implements KeyListener{
    public double xPos, yPos, xDir, yDir, xPlane, yPlane;
    public boolean left, right, forward, back, stepLeft, stepRight;
    public double normalSpeed = 0.04;
    public double crouchSpeed = 0.02;
    public double sprintSpeed = 1.0;
    public double MOVE_SPEED = .08;
    public double ROTATION_SPEED = .045;

    Game game;

    public CameraWithStrafe(double x, double y, double xd, double yd, double xp, double yp, Game gamee) {
        xPos = x;
        yPos = y;
        xDir = xd;
        yDir = yd;
        xPlane = xp;
        yPlane = yp;
        game = gamee;

        if(Settings.default_crouch_state) MOVE_SPEED = crouchSpeed;
        if(Settings.default_sprint_state) MOVE_SPEED = sprintSpeed;
    }

    boolean sprinting = Settings.default_crouch_state;
    boolean crouching = Settings.default_sprint_state;
    public void keyPressed(KeyEvent key) {
        if((key.getKeyCode() == 81))
            left = true;
        if((key.getKeyCode() == 69))
            right = true;
        if((key.getKeyCode() == 87))
            forward = true;
        if((key.getKeyCode() == 83))
            back = true;
        if((key.getKeyCode() == 32))
        game.nextMap();
        if ((key.getKeyCode() == 65)) {
            stepLeft = true;
        }
        if ((key.getKeyCode() == 68)) {
            stepRight = true;
        }
        if((key.getKeyCode() == 16) && Settings.enable_sprint)
        {
            sprinting = !sprinting;
            crouching = false;
            if(sprinting) MOVE_SPEED = sprintSpeed;
            else MOVE_SPEED = normalSpeed;
        }
        if((key.getKeyCode() == 17) && Settings.enable_crouch)
        {
            crouching = !crouching;
            sprinting = false;
            if(crouching) MOVE_SPEED = crouchSpeed;
            else MOVE_SPEED = normalSpeed;
        }
    }
    public void keyReleased(KeyEvent key) {
        if((key.getKeyCode() == 81))
            left = false;
        if((key.getKeyCode() == 69))
            right = false;
        if((key.getKeyCode() == 87))
            forward = false;
        if((key.getKeyCode() == 83))
            back = false;
        if ((key.getKeyCode() == 65))
            stepLeft = false;
        if ((key.getKeyCode() == 68))
            stepRight = false;
    }
    public void update(int[][] map) {
        if(forward) {
            if(map[(int)(xPos + xDir * MOVE_SPEED)][(int)yPos] == 0) {
                xPos+=xDir*MOVE_SPEED;
            }
            if(map[(int)xPos][(int)(yPos + yDir * MOVE_SPEED)] ==0)
                yPos+=yDir*MOVE_SPEED;
        }
        if(back) {
            if(map[(int)(xPos - xDir * MOVE_SPEED)][(int)yPos] == 0)
                xPos-=xDir*MOVE_SPEED;
            if(map[(int)xPos][(int)(yPos - yDir * MOVE_SPEED)]==0)
                yPos-=yDir*MOVE_SPEED;
        }
        if(right) {
            double oldxDir=xDir;
            xDir=xDir*Math.cos(-ROTATION_SPEED) - yDir*Math.sin(-ROTATION_SPEED);
            yDir=oldxDir*Math.sin(-ROTATION_SPEED) + yDir*Math.cos(-ROTATION_SPEED);
            double oldxPlane = xPlane;
            xPlane=xPlane*Math.cos(-ROTATION_SPEED) - yPlane*Math.sin(-ROTATION_SPEED);
            yPlane=oldxPlane*Math.sin(-ROTATION_SPEED) + yPlane*Math.cos(-ROTATION_SPEED);
        }
        if(left) {
            double oldxDir=xDir;
            xDir=xDir*Math.cos(ROTATION_SPEED) - yDir*Math.sin(ROTATION_SPEED);
            yDir=oldxDir*Math.sin(ROTATION_SPEED) + yDir*Math.cos(ROTATION_SPEED);
            double oldxPlane = xPlane;
            xPlane=xPlane*Math.cos(ROTATION_SPEED) - yPlane*Math.sin(ROTATION_SPEED);
            yPlane=oldxPlane*Math.sin(ROTATION_SPEED) + yPlane*Math.cos(ROTATION_SPEED);
        }
        if (stepLeft) {
            if (map[(int) (xPos)][(int) (yPos + xDir * MOVE_SPEED)] == 0) {
                yPos += xDir * MOVE_SPEED;
            }
            if (map[(int) (xPos - yDir * MOVE_SPEED)][(int) (yPos)] == 0) {
                xPos -= yDir * MOVE_SPEED;
            }
        }
        if (stepRight) {
            if (map[(int) (xPos)][(int) (yPos - xDir * MOVE_SPEED)] == 0) {
                yPos -= xDir * MOVE_SPEED;
            }
            if (map[(int) (xPos + yDir * MOVE_SPEED)][(int) (yPos)] == 0) {
                xPos += yDir * MOVE_SPEED;
            }
        }
    }
    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub

    }
}