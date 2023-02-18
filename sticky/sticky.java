package sticky;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.awt.event.MouseEvent;
import java.awt.Shape;

import java.awt.geom.Rectangle2D;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

/*import java.io.File;
import java.io.FileWriter;
*/
import java.io.IOException;

public class sticky extends JFrame {
    private Canvas theCanvas;
    private Color ccolor = Color.black;
    int events = 0;
    int i = 0;
    char input = 'z';
    int iValue = 0;
    private static int[] hA = new int[] {};

    ArrayList<Integer> x1a = new ArrayList<Integer>();
    ArrayList<Integer> y1a = new ArrayList<Integer>();
    ArrayList<Integer> sx1a = new ArrayList<Integer>();
    int x = 50;
    double y = 50;
    double speed = 1;
    double vSpeed = 0;
    // player x and y cords
    static double timer;
    int times = 1;
    double prevMillis;
    int z = 0;
    int rotation = 0;
    long prevMillisSpace = 0;
    // initializing array to hold subimages
    final BufferedImage imgs[] = new BufferedImage[9];
    boolean mgen = true;
    // transform for pgostick
    int x1 = 20;
    int y1 = 50;
    int sx1 = 0;
    // player width
    int pWdith = 20;
    // animation selection
    int aSelection = 4;
    boolean physics = true;
    int timeSpent = 0;
    int finalTime = 0;
    Shape player = new Rectangle2D.Double(x, (int) y, 50, 50);
    boolean touching = false;
    boolean win = false;
    // for the animation as it is in a 3x1 image array
    int backgroundy = 0;
    int spacePressed = 0;
    // how many platforms there are
    int platformNum = 20;
    int mX;
    int mY;
    int smX = 830;
    static String highscoreString = "";
    boolean bSettings = false;
    final BufferedImage platform = ImageIO.read(new File("platform.png"));
    final BufferedImage basePlatform = ImageIO.read(new File("basePlatform.png"));
    final BufferedImage star = ImageIO.read(new File("star.png"));
    final BufferedImage background = ImageIO.read(new File("background.png"));
    final BufferedImage playerimg = ImageIO.read(new File("Player.png"));
    final BufferedImage icon = ImageIO.read(new File("icon.png"));
    final BufferedImage maps = ImageIO.read(new File("maps.png"));
    final BufferedImage setting = ImageIO.read(new File("setting.png"));
    final BufferedImage settings = ImageIO.read(new File("settings.png"));
    final BufferedImage settingsShowcase = ImageIO.read(new File("settingsShowcase.png"));
    final BufferedImage sticky = ImageIO.read(new File("sticky_situation.png"));

    public sticky() throws IOException {

        super("Pogo Jump");
        hA = new int[2];
        hA[0] = 1000;
        hA[1] = 1000;
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(2000, 1000);
        setFocusable(true);
        timeSpent = (int) System.currentTimeMillis();
        theCanvas = new Canvas();
        addKeyListener(new ColorSelector(theCanvas));
        theCanvas.addMouseListener(new start(theCanvas));
        theCanvas.setFocusable(true);
        add(theCanvas);
        imageSplicer();
        while (!player.intersects(5, 900, 1900, 20)) {
            /*----------------------------------------------------------------------------------------------------------------
            for people with weaker systems, you can have it check for inputs every 10 ms+,
            for an easier time on the cpu/gpu  2ms 40cpu  50 gpu and without the 2ms its runs at 45cpu 58gpu
            // */
            if ((System.currentTimeMillis() - timer) % 2 == 0) {
                repaint();
            }
        }
    }

    public void imageSplicer() {
        int rows = 1;
        int columns = 9;

        // Equally dividing original image into subimages
        int subimage_Width = playerimg.getWidth() / columns;
        int subimage_Height = playerimg.getHeight() / rows;
        int current_img = 0;

        // iterating over rows and columns for each sub-image
        for (i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                // Creating sub image
                imgs[current_img] = new BufferedImage(subimage_Width, subimage_Height, 6);
                Graphics2D img_creator = imgs[current_img].createGraphics();

                // coordinates of source image
                int src_first_x = subimage_Width * j;
                int src_first_y = subimage_Height * i;

                // coordinates of sub-image
                int dst_corner_x = subimage_Width * j + subimage_Width;
                int dst_corner_y = subimage_Height * i + subimage_Height;

                img_creator.drawImage(playerimg, 0, 0, subimage_Width, subimage_Height,
                        src_first_x, src_first_y,
                        dst_corner_x, dst_corner_y, null);
                current_img++;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        sticky main = new sticky();

    }

    class Canvas extends JPanel {

        private MouseEvent[] eventHistory;
        private static final int MAX_EVENTS = 1000;

        private int totalEvents;

        public Canvas() {
            setBackground(Color.white);
            eventHistory = new MouseEvent[MAX_EVENTS];
            totalEvents = 0;
            setFocusable(true);

        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.black);
            // if no key pressed it will display this
            if (events == 0) {
                g.setColor(ccolor);
                g.setFont(g.getFont().deriveFont(30.0f));
                g.drawImage(icon, 50, 50, null);
                g.drawString("Pogo Jump", 1100, 250);
                g.drawImage(maps, 50, 300, null);
                g.drawImage(settingsShowcase, 800, 550, null);
                g.setFont(g.getFont().deriveFont(20.0f));
                g.drawString(
                        "By adjusting the slider, you can change the difficulty with the number of platforms",
                        750, 850);
                g.drawString(
                        "By going to the right, you can load diffrent maps",
                        40, 600);
                g.drawString(
                        " and return to the previous map by going backwards.",
                        30, 630);
                g.drawString("Welcome to Pogo Jump, the game where you try to get to the top and get the lollypop", 800,
                        300);
                g.drawString("Press 'a' to angle left and 'd' to angle right and hold the space bar to jump", 850,
                        350);
                g.drawString("Press any button to continue...", 1050,
                        450);

            } else if (events >= 1 && !win) {
                g.setFont(new Font("Arial", 10, 30));
                movement();
                physics();

                // for background "gif" display
                if ((int) (System.currentTimeMillis() - timer) % 250 == 0) {
                    backgroundy -= 1000;
                }
                if (backgroundy <= -3000) {
                    backgroundy = 0;
                }
                g.drawImage(background, 0, backgroundy, null);
                // map generation

                if (mgen) {
                    mapgen();
                }
                // base platform
                g.drawImage(basePlatform, 0, 900, null);
                // displays the player based off of the x1 var, or how many times 'a' or 'd'
                // have been pressed
                g.drawImage(imgs[aSelection], x, (int) y, null);

                // for adding the platforms
                for (i = 0; i <= platformNum; i++) {
                    try {
                        g.drawImage(platform, x1a.get(i + iValue), y1a.get(i + iValue), this);
                        g.drawImage(sticky, x1a.get(i + iValue) + sx1a.get(i + iValue), y1a.get(i + iValue) - 30, this);
                        // selecting where the stars go
                        if ((int) y1a.get(i + iValue) < (int) hA[1]) {
                            hA[0] = x1a.get(i + iValue) + 40;
                            hA[1] = y1a.get(i + iValue);
                        }
                    } catch (Exception e) {
                    }
                }
                // star draw
                g.drawImage(star, hA[0], hA[1] - 25, null);
                // if player goes off screen, will show a little hint as to where the player is
                if (y < -60) {
                    g.drawLine(x, 5, x - 25, 35);
                    g.drawLine(x, 5, x + 25, 35);
                    g.drawOval(x - 25, 24, 50, 50);
                    g.drawImage(imgs[aSelection], x - 10, 25, null);
                }
                g.drawString("Map " + (iValue / platformNum + 1), 20, 30);
                // final time for when the player wins
                finalTime = (int) (System.currentTimeMillis() - timeSpent) / 1000;
                // settings button

                if (mX > 1920 && mX < 1970 && mY > 10 && mY < 60) {
                    input = 'z';
                    bSettings = true;
                }
                // if clicked
                if (bSettings) {
                    g.drawImage(settings, 150, 100, null);
                    g.drawString("Number of platforms " + (platformNum + 1)
                            + "  Draw slider for change the number of platforms", 600, 300);
                    g.fillRect(smX, 425, 20, 40);
                    platformNum = 3 + Math.round((smX - 490) / 20);
                    mapgen();
                    if (mX > 1736 && mX < 1829 && mY > 118 && mY < 209) {
                        bSettings = false;
                    }
                } else {
                    g.drawImage(setting, 1920, 10, null);
                }
                // win condition
                if (x > hA[0] - 25 && x < hA[0] + pWdith && y > hA[1] - 100 && y < hA[1] + 20) {
                    g.setColor(Color.black);
                    win = true;
                    input = 'z';
                    vSpeed = 0;
                    x1 = 20;
                }
            } else if (win) {
                /*
                 * My attempt at adding in a file to record the highscore, although for some
                 * reaseon, kept throughing errors, so I decided not to implement.
                 * try {
                 * File myObj = new File("highScore.txt");
                 * if (myObj.createNewFile()) {
                 * System.out.println("File created: " + myObj.getName());
                 * } else {
                 * System.out.println("File already exists.");
                 * }
                 * } catch (IOException e) {
                 * System.out.println("An error occurred.");
                 * e.printStackTrace();
                 * }
                 * System.out.println("An error occurred.");
                 * e.printStackTrace();
                 * try {
                 * File scanObj = new File("highScore.txt");
                 * Scanner myReader = new Scanner(scanObj);
                 * while (myReader.hasNextLine()) {
                 * highscoreString = myReader.nextLine();
                 * }
                 * myReader.close();
                 * } catch (FileNotFoundException e) {
                 * System.out.println("An error occurred.");
                 * e.printStackTrace();
                 * }
                 * if (highscoreString == "") {
                 * try {
                 * System.out.println("nothing in file, overiding with: '" + finalTime + "'");
                 * FileWriter myWriter = new FileWriter("highScore.txt");
                 * myWriter.write(Integer.toString(finalTime));
                 * myWriter.close();
                 * highscoreString = "0";
                 * } catch (IOException ex) {
                 * System.out.println("An error occurred.");
                 * ex.printStackTrace();
                 * }
                 * } else if (Integer.parseInt(highscoreString) < finalTime && highscoreString
                 * != "") {
                 * System.out.println(highscoreString + " " + finalTime);
                 * try {
                 * FileWriter myWriter = new FileWriter("highScore.txt");
                 * myWriter.write(Integer.toString(finalTime));
                 * myWriter.close();
                 * g.drawString("NEW HIGH SCORE: " + finalTime, 800, 480);
                 * } catch (IOException ea) {
                 * System.out.println("An error occurred.");
                 * ea.printStackTrace();
                 * }
                 * } else {
                 * g.drawString("High score: " + highscoreString, 800, 480);
                 * 
                 * }
                 */
                g.setFont(new Font("Arial", 10, 30));
                // displays stars to make it beddalized
                for (int height = 0; height < 16; height++) {
                    for (int width = 0; width < 33; width++) {
                        g.drawImage(star, 10 + width * 60, 10 + height * 60, null);
                    }
                }

                String score = "Time taken "
                        + String.valueOf(finalTime) + " Seconds \n"
                        + " Number times space pressed " + spacePressed;
                g.drawString("You Won", 800, 300);
                g.drawString("Statistics", 800, 370);
                g.drawString(score, 800, 420);
            }
        }

        public void movement() {
            if (input == ' ' && speed == 0 && touching) {
                speed -= 3;
                physics = true;
                y -= 2;
                y1 = 70;

            }
            if (input == 'd' && x1 < 40) {
                for (int z = 0; z < 5; z++) {
                    x1 += 1;
                }
                vSpeed -= 1;
                aSelection -= 1;
            } else if (input == 'a' && x1 > 0) {
                for (int z = 0; z < 5; z++) {
                    x1 -= 1;
                }
                vSpeed += 1;
                aSelection += 1;
            }
            // changes the input to another char that way the input chagens and it doesnt
            // keep running the same code
            input = 'z';
            // checks to see the bounds, if out of bounds, either updates the map or resets
            // position
            if (x > 1960) {
                mgen = true;
                x = 20;
                iValue += platformNum;
            }
            if (x < 5 && iValue != 0) {
                mgen = true;
                x = 1950;
                iValue -= platformNum;
            }
            if (x < 5 && (iValue / platformNum + 1) == 1) {
                vSpeed = 0;
                x1 = 20;
                aSelection = 4;
                x += 1;
            }
        }

        public void mapgen() {
            hA[1] = 500;
            for (int z = 0; z <= platformNum; z++) {
                int x1 = (int) Math.round(Math.random() * 2000);
                // while you can just make the range up to 800, its better like this beacuse it
                // means that there will be a higher probablity of platforms on the lower level.
                int y1 = (int) Math.round(Math.random() * 1000);
                int sx1 = (int) Math.round(Math.random() * 70);
                // makes sure that the platforms are in reasonable locations
                if (x1 < 150) {
                    x1 += 150;
                } else if (x1 > 1800) {
                    x1 -= 200;
                }
                if (y1 < 60) {
                    y1 += 60;
                }

                else if (y1 > 800) {
                    y1 -= 150;
                }
                x1a.add(x1);
                y1a.add(y1);
                sx1a.add(sx1);

            }
            timer = System.currentTimeMillis();
            mgen = false;
        }

        public void addEvent(MouseEvent e) {
            eventHistory[totalEvents++] = e;
        }

    }

    public boolean touching() {
        // checks to see if pogo jumper guy is touching the top part of the platform, if
        // true then touching =true, and returns true
        for (i = 0; i <= platformNum; i++) {
            if (((x > x1a.get(i + iValue) && x < x1a.get(i + iValue) + 100)
                    || (x + pWdith > x1a.get(i + iValue) && x + pWdith < x1a.get(i + iValue) + 100))
                    && (y < y1a.get(i + iValue) - 45 && y > y1a.get(i + iValue) - 50)) {
                touching = true;
                return true;
            }
        }
        for (i = 0; i <= platformNum; i++) {
            if (((x > (x1a.get(i + iValue) + sx1a.get(i + iValue))
                    && x < x1a.get(i + iValue) + sx1a.get(i + iValue) + 30)
                    || (x + pWdith > x1a.get(i + iValue) + sx1a.get(i + iValue)
                            && x + pWdith < x1a.get(i + iValue) + sx1a.get(i + iValue) + 30))
                    && (y < y1a.get(i + iValue) - 45 && y > y1a.get(i + iValue) - 50)) {
                touching = true;
                return true;
            }
        }
        // if y>1000, brings player back to start, although this should be imposiable.
        if (y > 1000) {
            x = 50;
            y = 50;
            x1 = 20;
            vSpeed = 0;
            speed = 0;
            aSelection = 4;
        }
        player = new Rectangle2D.Double(x, (int) y, 50, 50);
        if (!player.intersects(5, 899, 1990, 20)) {
            touching = false;
            y1 = 70;
            return false;

        } else {
            y1 = 70;
            touching = true;
        }
        return physics;
    }

    public void physics() {
        // updates every 10ms for optimization
        if ((System.currentTimeMillis() - timer) % 10 == 0) {
            if (speed > 0) {
                for (int z = 0; z < speed; z++) {
                    // if player is off the ground, gravity works
                    if (!touching()) {
                        y += 1;
                    } else if (touching()) {
                        // if player is on the ground, gravity doesnt work
                        speed = 0;
                    }
                    // vertical transform
                    if (vSpeed > 0 && touching == false) {
                        for (i = 0; i < vSpeed; i++) {
                            x += 1;
                        }
                    } else if (vSpeed < 0 && touching == false) {
                        for (i = 0; i < -vSpeed; i++) {
                            x -= 1;
                        }
                    }
                }
            } else {
                // brings the player upwards
                for (z = 0; z < -speed; z++) {
                    if (y > -1000) {
                        y -= 1;
                        // speed = 0;
                    } else {
                        speed = 1;
                    }
                    // vertical transform
                    if (vSpeed > 0) {
                        for (i = 0; i < vSpeed; i++) {
                            x += 1;
                        }
                    } else if (vSpeed < 0) {
                        for (i = 0; i < -vSpeed; i++) {
                            x -= 1;
                        }
                    }

                }

            }
        }

        if ((int) (System.currentTimeMillis() - timer) % 40 == 0 && !touching()) {
            speed += .1;
        }

    }

    class start implements MouseListener {
        private Canvas theCanvas;

        public start(Canvas theCanvas) {
            this.theCanvas = theCanvas;
        }

        public void mouseClicked(MouseEvent e) {
        }

        public void mousePressed(MouseEvent e) {
            if (events <= 1) {
                theCanvas.addEvent(e);
                theCanvas.repaint();
                events += 2;
            } else {
                // gets the x and y which will be used for the settings interface
                mX = e.getX();
                mY = e.getY();
                if (mX > 490 && mX < 1540 && mY > 425 && mY < 465) {
                    smX = e.getX();
                }
                physics = true;
            }

        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }
    }

    class ColorSelector implements KeyListener {
        private Canvas theCanvas;

        public ColorSelector(Canvas theCanvas) {
            this.theCanvas = theCanvas;
        }

        public void keyPressed(KeyEvent e) {
            if (events < 2) {
                theCanvas.repaint();
                events += 2;
            } else if (events > 2) {
                physics = true;
            }
            switch (e.getKeyChar()) {
                case 'a':
                    input = 'a';
                    break;
                case 'd':
                    input = 'd';
                    break;
                case ' ':
                    input = ' ';
                    if (!win && touching()) {
                        spacePressed += 1;
                    }
                    break;

            }
            theCanvas.repaint();
        }

        public void keyReleased(KeyEvent e) {
        }

        public void keyTyped(KeyEvent e) {
        }

    }
}