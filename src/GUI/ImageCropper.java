/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.*;

/**
 *
 * @author Liam Hamill
 */
public class ImageCropper extends JComponent implements MouseListener, MouseMotionListener {
    
    private int x1, y1, x2, y2;
    private BufferedImage imgToCrop;
    private boolean cropping = true;
    
    public ImageCropper( ) {
        addMouseListener(this);
        addMouseMotionListener(this);
        

    }
    
    public void newImage( BufferedImage input ) {
        imgToCrop = input;
    }
    
    public BufferedImage getImage() {
        return imgToCrop;
    }
    
    public BufferedImage crop(BufferedImage src, Rectangle rect) {
        
        BufferedImage dest;
        dest = new BufferedImage( (int) rect.getWidth(), (int) rect.getHeight(), src.getType() );
        Graphics g = dest.getGraphics();
        g.drawImage(src, 0, 0, (int) rect.getWidth(), (int) rect.getHeight(), (int) rect.getX(), (int) rect.getY(), (int) rect.getX() + (int) rect.getWidth(), (int) (rect.getY() + rect.getHeight()), null);
        g.dispose();
        return dest;
    }
    
    public void paintComponent( Graphics g ) {
        g.drawImage(imgToCrop, 0, 0, this);
        
        if (cropping) {
          // Paint the area we are going to crop.
          g.setColor(Color.RED);
          g.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.max(x1, x2), Math.max(y1, y2));
        }
    }
    
    public void mousePressed(MouseEvent evt) {
        x1 = evt.getX();
        y1 = evt.getY();
        
    }
    
    public void mouseReleased(MouseEvent evt){
       cropping = false;

       BufferedImage cropped = crop( imgToCrop, new Rectangle( Math.min(x1, x2), Math.min(y1, y2), Math.max(x1, x2), Math.max(y1, y2) ) );

       imgToCrop = cropped;
    }
     
    public void mouseEntered(MouseEvent e) {   
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }
    
    public void mouseMoved(MouseEvent e) {
    }
    
    public void mouseDragged(MouseEvent e) {
    }
}
