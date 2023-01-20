import javax.swing.*;
import javax.swing.text.StyledEditorKit.ItalicAction;
import java.lang.Runnable;
import java.nio.channels.OverlappingFileLockException;
import java.awt.*;
import java.awt.geom.*;
import java.security.SecureRandom;
import java.security.cert.PolicyQualifierInfo;
import java.awt.event.*; 
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;
import javax.swing.JColorChooser;


public class NewEngland extends JComponent{
    private final int width, height; 
    private final double gravity =  0.03; 
    private UpdateThread mUpdateThread; 
    private Snowflake[] snowflakes;
    private Train[] trains = {new Train(1290, 200)}; 
    private int snowflakesCount;   
    private Color lightColor; 
    private Color[] primary = {new Color(240, 249, 255), new Color(185, 221, 237),
         new Color(184, 227, 245)}; 

    public NewEngland(int width, int height, int numSnowflakes, Color lightColor){
        this.width = width; this.height = height; this.snowflakesCount = numSnowflakes; this.lightColor = lightColor;
        snowflakes = new Snowflake[snowflakesCount]; 
        fillSnowflakes(); 
        mUpdateThread = new UpdateThread(); 
        mUpdateThread.start(); 
    }

    private void fillSnowflakes(){
        for(int i=0; i<snowflakesCount; i++){
            snowflakes[i] = new Snowflake(); 
        }
    }

    private class UpdateThread extends Thread {
        public volatile boolean stopped=false;
        @Override
        public void run() {
            while (!stopped) {
                NewEngland.this.repaint();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override 
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        drawForegroundBackground(g2d);
        drawTracks(g2d); 

        for(int i=0; i<trains.length; i++){
            trains[i].draw(g2d); 
        }
        for(int i=0; i<trains.length; i++){
            trains[i].update();
        }
        
       
        drawHouses(g2d);
        drawArches(g2d);
        drawTrees(g2d);
        
        Lights lights = new Lights(0, 400); 
        lights.draw(g2d); 
        Girl girl = new Girl(500, 350);
        girl.draw(g2d); 
       
        
        
        //g2d.setColor(Color.white); 
        //g2d.fill(new Ellipse2D.Double(snowflakeStartX, snowflakeStartY, 15, 15)); 
        for(int i=0; i < snowflakes.length; i++){
            snowflakes[i].draw(g2d);
        }
         
        for(int i=0; i<snowflakes.length; i++){
            snowflakes[i].update();
            
            
            //snowflakeStartY += 5; 
        }
        
         
    }
    
    public void drawTracks(Graphics2D g2d){
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(rh);
        g2d.setColor(primary[1]);
        g2d.draw(new Line2D.Double(0, 200, width, 200));
        g2d.draw(new Line2D.Double(0, 201, width, 202));

    }

    class Lights{
        double x, y; 
        public Lights(double x, double y){
            this.x = x; 
            this.y = y;
        }

        public void draw(Graphics2D g2d){
            RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(rh);
        double saveX = x; 
            g2d.setColor(new Color(25, 29, 33, 75));
            Path2D path = new Path2D.Double();
            path.moveTo(x, y);
            for(int i=0; i<6; i++){
                path.curveTo(x+45, y+40, x+175, y+40, x+250, y);
                x += 250; 
            }
            g2d.setStroke(new BasicStroke(5));
            g2d.draw(path);
            
            
            for(int i=0; i<5; i++){
                g2d.setColor(lightColor);
                g2d.fill(new Ellipse2D.Double(saveX+95, y+30, 20, 20)); 
                g2d.setColor(lightColor.darker());
                g2d.fill(new Ellipse2D.Double(saveX+100, y+35, 10, 10)); 
                saveX += 250; 
            }
            


        }
    }
    class Girl{
        double x, y;
        public Girl(double x, double y){
            this.x = x; this.y = y; 
        } 

        public void draw(Graphics2D g2d){
            RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(rh);

            g2d.setColor(new Color(53, 131, 232)); 
            Path2D path = new Path2D.Double();
            path.moveTo(x, y); 
            path.curveTo(x+0.5, y-5, x+70,y-5, x+70, y);
            
            path.moveTo(x+0.5, y); 
            path.curveTo(x, y, x - 0.1, y + 30, x + 20, y + 100);

            path.moveTo(x+70, y); 
            path.curveTo(x+70, y, x+70 + 0.1, y + 30, x + 50, y +100);
            
            path.moveTo(x+20, y +100);
            path.lineTo(x+50, y+100);


            g2d.draw(path);
            

            g2d.setColor(new Color(53, 131, 232)); 
            path = new Path2D.Double();
            path.moveTo(x-30, y+230);
            //path.lineTo(x+25+50, y+20+40);
            path.curveTo(x+30, y+40, x+40, y+40, x+100, y+230);
            path.closePath();
            
            g2d.fill(path);

            path = new Path2D.Double(); 
            path.moveTo(x-10, y-10);
            path.lineTo(x-15-5 , y-15 + 80);
            path.curveTo(x-15-5+3, y -15 +75, x-15-5+20, y -15 +75, x+10, y-10);
            g2d.setColor(new Color(255, 243, 240));
            path.closePath();
            g2d.fill(path); 

            path = new Path2D.Double(); 
            path.moveTo(x+70+10, y-10);
            path.lineTo(x+70+15+5 , y-15 + 80);
            path.curveTo(x+70 + 15+5-3, y -15 +75, x+70+15+5-20, y -15 +75, x+60, y-10);
            
            path.closePath();
            g2d.fill(path); 

            g2d.setColor(new Color(53, 131, 232)); 
            g2d.fill(new Ellipse2D.Double(x-15, y-15, 30, 30)); 
            g2d.fill(new Ellipse2D.Double(x-15+70, y-15, 30, 30)); 

            path = new Path2D.Double();
            path.moveTo(x-10, y-10);
            path.curveTo(x+40, y+200, x+45, y+200, x+75, y-10);
            path.closePath();
            g2d.fill(path);

            g2d.setColor(new Color(255, 243, 240));
            g2d.fill(new Ellipse2D.Double(x+9, y-60, 50, 50));
            
            path = new Path2D.Double();
            path.moveTo(x-10, y+67);
            path.curveTo(x+9, y-107, x +55, y -107, x-20+80, y+67);
            
            path.closePath();
            g2d.setColor(new Color(28, 28, 28, 255));
            g2d.fill(path); 
            
            g2d.setStroke(new BasicStroke(3));
            g2d.setColor(new Color(33, 79, 138, 135));
            //g2d.draw(new Line2D.Double(x + 15, y + 170, x + 25, y + 200));
            path = new Path2D.Double();
            path.moveTo(x + 15, y + 170);
            path.curveTo(x+25, y+150, x+45, y+190, x+55, y+110);
            g2d.draw(path);
            g2d.setColor(new Color(20, 30, 43, 30));
            g2d.fill(new Rectangle2D.Double(x-30, y+230, 130, 5));

            
        }
    }

    class Train{
        int x, y;
        double vel = 0.15; 
        Graphics2D g2d;
        public Train(int x, int y){
            this.x = x;
            this.y = y; 
        }
        public void draw(Graphics2D g2d){
            RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(rh);
            
            //g2d.draw(new Line2D.Double(x, y, x + 15, y - 10)); g2d.draw(new Line2D.Double(x, y, x+100,y));
           //g2d.draw(new Line2D.Double(x+15, y-10, x+100, y-10)); g2d.draw(new Line2D.Double(x+100, y-10, x+100, y));
            Polygon firstCar = new Polygon(); 

            g2d.setColor(new Color(173, 172, 172));
            firstCar.addPoint(x-10, y);
            firstCar.addPoint(x +15-10, y -10); 
            firstCar.addPoint(x+100-10, y-10);
            firstCar.addPoint(x+100-10, y);

            g2d.fill(firstCar);
            g2d.draw(firstCar);
            
            firstCar = new Polygon(); 
            firstCar.addPoint(x, y);
            firstCar.addPoint(x +15, y -10); 
            firstCar.addPoint(x+100, y-10);
            firstCar.addPoint(x+100, y);
            g2d.setColor(new Color(237, 235, 235));
            g2d.fill(firstCar);
            g2d.draw(firstCar);

            g2d.setColor(new Color(142, 169, 245));
            g2d.fill(new Rectangle2D.Double(x + 20 ,y-6, 5, 3));g2d.fill(new Rectangle2D.Double(x + 30 ,y-6, 5, 3));
            g2d.fill(new Rectangle2D.Double(x + 40 ,y-6, 5, 3));g2d.fill(new Rectangle2D.Double(x + 50 ,y-6, 5, 3));
            g2d.fill(new Rectangle2D.Double(x + 60 ,y-6, 5, 3));g2d.fill(new Rectangle2D.Double(x + 70 ,y-6, 5, 3));
            g2d.fill(new Rectangle2D.Double(x + 80 ,y-6, 5, 3));g2d.fill(new Rectangle2D.Double(x + 90 ,y-6, 5, 3));
            
            g2d.setColor(new Color(105, 8, 3));
            g2d.fill(new Rectangle2D.Double(x, y-1, 120, 2)); 
            

            int secondCarX = x + 115; 
            Polygon secondCar = new Polygon();

            g2d.setColor(new Color(173, 172, 172));
            secondCar.addPoint(secondCarX - 10, y);
            secondCar.addPoint(secondCarX -10, y-10);
            secondCar.addPoint(secondCarX+120 - 10, y-10);
            secondCar.addPoint(secondCarX+120 -10, y);

            g2d.fill(secondCar);
            g2d.draw(secondCar);

            secondCar = new Polygon(); 
            secondCar.addPoint(secondCarX, y);
            secondCar.addPoint(secondCarX, y-10);
            secondCar.addPoint(secondCarX+120, y-10);
            secondCar.addPoint(secondCarX+120, y);
            g2d.setColor(new Color(237, 235, 235));
            g2d.fill(secondCar);
            g2d.draw(secondCar);

            g2d.setColor(new Color(142, 169, 245));
            g2d.fill(new Rectangle2D.Double(secondCarX + 10 ,y-6, 5, 3));
            g2d.fill(new Rectangle2D.Double(secondCarX + 20 ,y-6, 5, 3));g2d.fill(new Rectangle2D.Double(secondCarX + 30 ,y-6, 5, 3));
            g2d.fill(new Rectangle2D.Double(secondCarX + 40 ,y-6, 5, 3));g2d.fill(new Rectangle2D.Double(secondCarX + 50 ,y-6, 5, 3));
            g2d.fill(new Rectangle2D.Double(secondCarX + 60 ,y-6, 5, 3));g2d.fill(new Rectangle2D.Double(secondCarX + 70 ,y-6, 5, 3));
            g2d.fill(new Rectangle2D.Double(secondCarX + 80 ,y-6, 5, 3));g2d.fill(new Rectangle2D.Double(secondCarX + 90 ,y-6, 5, 3));
            g2d.fill(new Rectangle2D.Double(secondCarX + 100 ,y-6, 5, 3));g2d.fill(new Rectangle2D.Double(secondCarX + 110 ,y-6, 5, 3));
            
            g2d.setColor(new Color(105, 8, 3));
            g2d.fill(new Rectangle2D.Double(secondCarX, y-1, 120, 2)); 

            int thirdCarX = secondCarX + 135;
            Polygon thridCar = new Polygon();
            thridCar.addPoint(thirdCarX-10, y);
            thridCar.addPoint(thirdCarX-10, y-10);
            thridCar.addPoint(thirdCarX+100-10, y-10);
            thridCar.addPoint(thirdCarX+100-10, y);
            g2d.setColor(new Color(173, 172, 172));
            g2d.fill(thridCar);
            g2d.draw(thridCar);


            thridCar = new Polygon(); 
            thridCar.addPoint(thirdCarX, y);
            thridCar.addPoint(thirdCarX, y-10);
            thridCar.addPoint(thirdCarX+100, y-10);
            thridCar.addPoint(thirdCarX+100, y);
            g2d.setColor(new Color(237, 235, 235));
            g2d.fill(thridCar);
            g2d.draw(thridCar);

            g2d.setColor(new Color(142, 169, 245));
            g2d.fill(new Rectangle2D.Double(thirdCarX + 10 ,y-6, 5, 3));
            g2d.fill(new Rectangle2D.Double(thirdCarX + 20 ,y-6, 5, 3));g2d.fill(new Rectangle2D.Double(thirdCarX + 30 ,y-6, 5, 3));
            g2d.fill(new Rectangle2D.Double(thirdCarX + 40 ,y-6, 5, 3));g2d.fill(new Rectangle2D.Double(thirdCarX + 50 ,y-6, 5, 3));
            g2d.fill(new Rectangle2D.Double(thirdCarX + 60 ,y-6, 5, 3));g2d.fill(new Rectangle2D.Double(thirdCarX + 70 ,y-6, 5, 3));
            g2d.fill(new Rectangle2D.Double(thirdCarX + 80 ,y-6, 5, 3));g2d.fill(new Rectangle2D.Double(thirdCarX + 90 ,y-6, 5, 3));
            
            g2d.setColor(new Color(105, 8, 3));
            g2d.fill(new Rectangle2D.Double(thirdCarX, y-1, 100, 2)); 

            Tree one = new Tree(100, 100, 30, new Color(1, 110, 10));
        one.draw(g2d);                                                                 
        Tree two = new Tree(1200, 100, 30, new Color(1, 110, 10));
        two.draw(g2d); 

        g2d.setColor(new Color(186, 212, 224));
        g2d.fill(new Rectangle(0, 385, width, 50));

        g2d.setColor(primary[1]);
        g2d.fill(new Rectangle(0, 400, width, 320));
        //185, 221, 237, \ 186, 212, 224
        g2d.setColor(new Color(99, 41, 38));
        g2d.fill(new Rectangle(0, 490, width, 320));
        
        }

        public void update(){
            if(x < 0){
                x = 1290; 
            }
            x -= vel; 
        }
    }


    class Snowflake{
        double x, y;
        double prevX, prevY; 
        double size; 
        double accel;double vel=0;
        double wind;

        public Snowflake(){
            SecureRandom random = new SecureRandom(); 
            x = random.nextDouble(width);
            y = -1 * random.nextDouble(width) + 10;  
            //size = random.nextDouble(25);
            size = random.nextGaussian(5, 5) + 3; 
            wind = random.nextDouble(0.15) + (-1 * random.nextDouble(0.15)); 
        }

        public void draw(Graphics2D g2d){
             RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(rh);
            g2d.setColor(Color.white);
            g2d.fill(new Ellipse2D.Double(x, y, size, size));
        }

        public void update(){
            SecureRandom random = new SecureRandom();
            if(y > height || x > width || x < 0){
                vel = 0;
                //size = random.nextDouble(25);
                wind = random.nextDouble(0.15) + (-1 * random.nextDouble(0.15)); 
                size = random.nextGaussian(5, 5) + 3; 
                x = random.nextDouble(1280);
                y = -1 * random.nextDouble(1280) + 10;
            }
            vel += gravity * size * 0.15; 
            //wind = random.nextDouble(0.15) + (-1 * random.nextDouble(0.15));
            // terminal velocity
            if(vel > size * 0.15){
                vel = size * 0.15; 
            }
            y += vel;
            x += wind * size * 0.15;  
        }
    }


        
    public void drawForegroundBackground(Graphics2D g2d){
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(rh);
        g2d.setColor(primary[0]);
        g2d.fill(new Rectangle2D.Double(0, 0, width, 400));
         
        g2d.setColor(new Color(186, 212, 224));
        g2d.fill(new Rectangle(0, 385, width, 50));
        
        g2d.setColor(primary[1]);
        g2d.fill(new Rectangle(0, 400, width, 320));
        //185, 221, 237, \ 186, 212, 224
        g2d.setColor(primary[2]);
        g2d.fill(new Rectangle(0, 490, width, 320));
    }

    public void drawTrees(Graphics2D g2d){
        Tree fbg =  new Tree(180, 42, 2, new Color(8, 77, 16));
        fbg.draw(g2d); 

        Tree sbg =  new Tree(280, 42, 10, new Color(12, 130, 25));
        sbg.draw(g2d); 

        Tree tbg =  new Tree(580, 65, 10, new Color(12, 130, 25));
        tbg.draw(g2d);

        Tree ttbg =  new Tree(780, 75, 10, new Color(12, 130, 25));
        ttbg.draw(g2d);

        Tree ffbg =  new Tree(1080, 45, 10, new Color(12, 130, 25));
        ffbg.draw(g2d);

        Tree fffbg =  new Tree(480, 60, 20, new Color(12, 130, 25));
        fffbg.draw(g2d);

    }

    class Tree{
        private double x, y; 
        private int size; 
        Color color; 
        public Tree(double x, double y, int size, Color color){
            this.x = x; 
            this.y = y; 
            this.size = size;
            this.color = color; 
        }

        public void draw(Graphics2D g2d){
            RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(rh);
            g2d.setStroke(new BasicStroke(1));
            double saveX = x; double saveY = y; 
            double fx; double fy; 
            Path2D treePath = new Path2D.Double(); 
            for(int i=0; i<size; i++){
                treePath.moveTo(saveX, saveY); 
                treePath.lineTo(saveX+5, saveY+5);
                treePath.lineTo(saveX+3, saveY+10);
                //g2d.setColor(new Color(1, 110, 10));
                g2d.setColor(color);
                g2d.draw(treePath);
                saveX += 3; 
                saveY += 10; 
            }
            fx = saveX; fy = saveY; 
            saveX = x; saveY = y; 
            for(int i=0; i<size; i++){
                treePath.moveTo(saveX, saveY); 
                treePath.lineTo(saveX-5, saveY+5); 
                treePath.lineTo(saveX-3, saveY+10);
                g2d.setColor(color);
                g2d.draw(treePath);
                saveX -= 3; 
                saveY += 10; 
            }
            treePath.moveTo(saveX, saveY);
            treePath.lineTo(x, y);
            g2d.draw(treePath);
            g2d.setColor(color);
            g2d.fill(treePath);
            
            treePath = new Path2D.Double();
            treePath.moveTo(x, y);
            treePath.lineTo(fx, fy);
            g2d.draw(treePath);
            treePath.lineTo(saveX, saveY); 
            g2d.draw(treePath);
            treePath.closePath();
            g2d.fill(treePath);
            
            //g2d.setColor(new Color(128, 87, 13));
            //g2d.fill(new Rectangle2D.Double(fx-size*3, saveY, size, 10*size));
               
        }


    }

    public void drawHouses(Graphics2D g2d){
        double x = 200; double y = 55;
        
        g2d.setColor(new Color(220, 240, 252));
        g2d.fill(new Rectangle2D.Double(0, 0, width, y)); 
        g2d.setColor(primary[2]);
        g2d.draw(new Line2D.Double(0, y, width, y)); 
        g2d.setColor(new Color(199, 44, 44)); 
        Rectangle2D.Double house = new Rectangle2D.Double(x, y, 20, 10);
        g2d.fill(house);
        g2d.setColor(new Color(74, 16, 16));
        g2d.fill(new Rectangle2D.Double(220, 55, 15, 10));
        g2d.setColor(new Color(89, 60, 38));
        g2d.fill(new Rectangle2D.Double(220, 45, 5, 7));
        Path2D path = new Path2D.Double();
        path.moveTo(x, y);
        path.lineTo(x+10, y-5);
        path.lineTo(x+20, y);
        path.closePath();
        g2d.setColor(new Color(89, 60, 38));  
        g2d.fill(path);

        path = new Path2D.Double(); 
        path.moveTo(x+10, y-5);
        path.lineTo(x+25, y-5);
        path.lineTo(x+35, y);
        path.lineTo(x+20, y);
        path.closePath();
        g2d.setColor(new Color(255, 255, 255));
        g2d.fill(path); 

        g2d.setColor(Color.black); 
        g2d.fill(new Rectangle2D.Double(x+9, y+5, 3, 5)); 


    }

    

    public void drawArches(Graphics2D g2d){
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(rh);
        g2d.setColor(new Color(86, 141, 166));
        double startX = 50;
        for(int i=0; i<5; i++){
            g2d.fill(new Arc2D.Double(startX, 425, 120, 130, 0, 180, Arc2D.OPEN));
            startX += 250; 
        }
        startX = 50; 
        g2d.setColor(primary[0]); 
        for(int i=0; i<5; i++){
            if(i==0){
                g2d.setColor(new Color(1, 110, 10));
            }
            else{
                g2d.setColor(primary[0]);
            }
            g2d.fill(new Arc2D.Double(startX, 425, 100, 130, 0, 180, Arc2D.OPEN));
            startX += 250; 
        }
       
    }

    class Water{
        private double x, y, vel;
        public Water(double x, double y, double vel){
            this.x = x; this.y = y; this.vel = vel; 
        } 

    }
}


