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
public class ImagePanel extends JPanel implements ChangeListener, MouseListener, MouseMotionListener {
    
    JLabel picLabel;
    BufferedImage imgToDisplay;
    JScrollPane scroller;
    
    private int x1, y1, x2, y2;
    private boolean cropping = false;
    private boolean dragging = false;

    
    ImagePanel ( ) {
        
        picLabel = new JLabel();
        //Dimension defaultSize = new Dimension(300, 300);
        
        //picLabel.setPreferredSize(defaultSize);
 
        scroller = new JScrollPane(picLabel);
        add(scroller, BorderLayout.CENTER);
        
        picLabel.addMouseListener(this);
        picLabel.addMouseMotionListener(this);
    }

    public void newImage ( BufferedImage input ) {
        System.out.println(input);
        
        imgToDisplay = input;
        remove(scroller);
        picLabel = new JLabel( new ImageIcon(input) );
        picLabel.addMouseListener(this);
        picLabel.addMouseMotionListener(this);
        scroller = new JScrollPane(picLabel);
        add(scroller, BorderLayout.CENTER);
        
        validate();
        repaint();
    }
    
    public void updateImage( BufferedImage input ) {
        
        // Updates image without changing imgToDisplay
        
        remove(scroller);
        picLabel = new JLabel( new ImageIcon(input) );
        picLabel.addMouseListener(this);
        picLabel.addMouseMotionListener(this);
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
    
    public void nowCropping() {
        cropping = true;
        scroller.setFocusable(true);
        System.out.println(cropping);
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
    
     public void mousePressed(MouseEvent evt) {
        if (cropping) {
            x1 = evt.getX();
            y1 = evt.getY();
            
            System.out.println(x1);
            System.out.println(y1);
        }
        
    }
    
    public void mouseReleased(MouseEvent evt){
        if (cropping) {
        x2 = evt.getX();
        y2 = evt.getY();

        System.out.println(x2);
        System.out.println(y2);
        
        cropImage( new Rectangle( Math.min(x1, x2), Math.min(y1, y2), Math.max(x1, x2), Math.max(y1, y2) ) );

        cropping = false;
       }
    }
     
    public void mouseEntered(MouseEvent e) {   
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
        System.out.println("click");
    }
    
    public void mouseMoved(MouseEvent e) {
    }
    
    public void mouseDragged(MouseEvent e) {        
    }
    
    
}
