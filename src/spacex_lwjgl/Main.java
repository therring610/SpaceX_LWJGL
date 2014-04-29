/*
 * Thomas Herring
 * ICS3U
 * Section 2
 * Final Project - SpaceX
 * 
 */
package spacex_lwjgl;

/**
 *
 * @author ThomasHerring
 */
import java.util.ArrayList;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;


public class Main {
    
     

    //Global Variables
    
    //Objects
    Graphics graphics = new Graphics();
    PlayerEntity player = new PlayerEntity();
    Input keyboard = new Input();
    Timer timer = new Timer();
    CollisionDetect detector = new CollisionDetect();
    public ArrayList<Stage> platforms = new ArrayList();
    
    
    //Game window resolution
    public int WIDTH = 800;
    public int HEIGHT = 600;
    
    
    
    
    //Delta
    int DELTA;

    public void init() {

        //Init display and openGL
        graphics.setRes(WIDTH, HEIGHT);
        graphics.initDisplay();
        graphics.initGl();

        //Set resolution of other objects
        
        //Create a new arraylist entity for the bottom
        platforms.add(new Stage());
        platforms.get(0).setRes(WIDTH, HEIGHT);
        
        //Create a new arraylist entity for the test block
        platforms.add(new Stage());
        platforms.get(1).setRes(WIDTH, HEIGHT);


        player.setRes(WIDTH, HEIGHT);

        //Init delta
        DELTA = timer.getDelta();
        
        //Init counter
        timer.initTime();

        //Set title
        Display.setTitle("SpaceX Test");


    }

    public void render() {

        //Clear GL
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        //Draw the stage
        platforms.get(0).drawPlatform(WIDTH, 0, 25, 0, true);
        
        

        //Check keyboard input
        keyboard.getKeyboardInput();

        //Get time between frames
        DELTA = timer.getDelta();
                
        //Move player
        player.setDelta(DELTA);
        player.setTime(timer.getTime());
       // player.setY(stage.UPPERBOUND);
        player.movePlayer(keyboard.wKey, keyboard.aKey,
                keyboard.sKey, keyboard.dKey);
        player.drawPlayer(player.playerX, player.playerY);
        
        
        
        /*Collision detection test
         * Create a new small box on the platform and printout collision matrix
         * This effectively limits the player, but there still is a gap between the object and the player
         * Instead of passing 4 variables, just pass an array
         */
        platforms.get(1).setColor(0.5f, 1, 1);
        platforms.get(1).drawPlatform(450, 200, 75, 25, false);
        detector.setFirstEntity(player.boundArray);
        detector.setSecondEntity(platforms.get(1).boundArray);
        detector.determineCollisions(20);
        System.out.println(detector.collisionMatrix[2]);
        System.out.println(detector.collisionMatrix[3]);     
        player.limitPlayer(detector.collisionMatrix[3], detector.collisionMatrix[2]);
        
        
        
        
    }

    public void start() {

        //Main loop
        while (!Display.isCloseRequested()) {

            //Render all graphics
            render();
            
            //Sync frame rate and update display
            Display.sync(100);
            Display.setVSyncEnabled(true);
            Display.update();

        }

        //Close display and program
        Display.destroy();
        System.exit(0);

    }

    public static void main(String[] args) {

        //Create new main object
        Main start = new Main();

        //Initilize and start
        start.init();
        start.start();





    }
}
