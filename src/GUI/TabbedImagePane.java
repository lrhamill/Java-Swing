/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author c1031996
 */
public class TabbedImagePane extends JPanel implements ChangeListener {
    
    JLabel picLabel = new JLabel();
    BufferedImage imgToDisplay;
    JScrollPane scroller;
    
    TabbedImagePane ( ) {
        
        scroller = new JScrollPane(picLabel);
        add(scroller, BorderLayout.CENTER);
        
    }

    public void newImage ( BufferedImage input ) {
        System.out.println(input);
        
        imgToDisplay = input;
        remove(scroller);
        picLabel = new JLabel( new ImageIcon(input) );
        scroller = new JScrollPane(picLabel);
        add(scroller, BorderLayout.CENTER);
        
        validate();
        repaint();
    }
    
    public void updateImage( BufferedImage input ) {
        
        // Updates image without changing imgToDisplay
        
        remove(scroller);
        picLabel = new JLabel( new ImageIcon(input) );
        scroller = new JScrollPane(picLabel);
        add(scroller, BorderLayout.CENTER);
        
        validate();
        repaint();
        
    }
    
    public void cropImage( Rectangle rect ) {
        
        System.out.println(rect);
        BufferedImage postCrop = imgToDisplay.getSubimage(rect.x, rect.y, rect.width, rect.height);
        
        newImage(postCrop);

    }
    
    public BufferedImage zoomImage( double zoomScale ) {
        
        // Code to scale an image using affine transformation
        
        int width  = (int)( zoomScale * imgToDisplay.getWidth() );  
        int height  = (int)( zoomScale * imgToDisplay.getHeight() );
        
        BufferedImage zoomedImg = new BufferedImage(width, height, imgToDisplay.getType());  
        Graphics2D zoomGraphics = zoomedImg.createGraphics();  
        zoomGraphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION,  
                            RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        
        AffineTransform affine = AffineTransform.getScaleInstance(zoomScale, zoomScale);  
        zoomGraphics.drawRenderedImage(imgToDisplay, affine);  
        zoomGraphics.dispose();  
        
        return zoomedImg;  
        
        
    }
    
    @Override
    public void stateChanged(ChangeEvent ce) {
        
        if ( imgToDisplay == null ) return;
        
        double zoom = ( (JSlider) ce.getSource() ).getValue();
        double zoomScale = zoom/100.0;
        
        updateImage( zoomImage(zoomScale) );
        
    }
    
    
    
    
}
