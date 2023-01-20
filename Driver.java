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



public class Driver {
    public static void main(String[] args) {
        final int w = 1280;
        final int h = 720;
        JFrame winterLandScape = new JFrame();
        winterLandScape.setSize(w, h);
        winterLandScape.setTitle("New England Scene");
        winterLandScape.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int numSnowflakes = Integer.parseInt(JOptionPane.showInputDialog("How many snowflakes would you like? ")); 
        Color c = JColorChooser.showDialog(winterLandScape, "Choose Light Color", new Color(25, 29, 33, 75));
        
        NewEngland scene = new NewEngland(w, h, numSnowflakes, c); 
        winterLandScape.add(scene);
        winterLandScape.setVisible(true);

    }
}

