/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author Ventsislav Peychev
 */
public class Images {
    public static ImageIcon COOL_SMILE = new ImageIcon("cool_smile.png"); // load the image to a imageIcon
    public static ImageIcon SMILE = new ImageIcon("smiley_face.png");
    public static ImageIcon UNSMILE = new ImageIcon("unsmiley_face.png");    
    public static ImageIcon BOMB = new ImageIcon("bomb.png");
    public static ImageIcon FLAG = new ImageIcon("flag.png");
    
    static
    {
        Image image = COOL_SMILE.getImage(); // transform it 
        Image newimg = image.getScaledInstance(50, 48,
                java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        COOL_SMILE = new ImageIcon(newimg);  // transform it back
        
        image = SMILE.getImage(); // transform it 
        newimg = image.getScaledInstance(50, 48,
                java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        SMILE = new ImageIcon(newimg);  // transform it back
        
        image = UNSMILE.getImage(); // transform it 
        newimg = image.getScaledInstance(50, 48,
                java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        UNSMILE = new ImageIcon(newimg);  // transform it back
        
        image = BOMB.getImage(); // transform it 
        newimg = image.getScaledInstance(20, 20,
                java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        BOMB = new ImageIcon(newimg);  // transform it back
        
        image = FLAG.getImage(); // transform it 
        newimg = image.getScaledInstance(20, 20,
                java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        FLAG = new ImageIcon(newimg);  // transform it back
    }
}
