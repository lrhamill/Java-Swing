/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author c1031996
 */
public class ImagePanel extends JPanel implements ChangeListener {
    
    JLabel picLabel;
    BufferedImage imgToDisplay;
    JSlider slider;
    
    ImagePanel ( ) {
        picLabel = new JLabel();
        Dimension defaultSize = new Dimension(300, 300);
        picLabel.setPreferredSize(defaultSize);
        
        createSlider();
        add(slider, BorderLayout.EAST);
        add(picLabel, BorderLayout.CENTER);
    }

    public void createSlider() {
        
        slider = new JSlider(JSlider.VERTICAL, 50, 200, 100);  
        slider.setMajorTickSpacing(50);  
        slider.setMinorTickSpacing(10);  
        slider.setPaintTicks(true);  
        slider.setPaintLabels(true);  
        slider.addChangeListener(this);  
    }
    
    public void newImage ( BufferedImage input ) {
        imgToDisplay = input;
        remove(picLabel);
        picLabel = new JLabel( new ImageIcon(input) );
        add(picLabel, BorderLayout.CENTER);
    }

    public BufferedImage zoomImage( double zoomScale ) {
        int width  = (int)( zoomScale*imgToDisplay.getWidth() );  
        int height  = (int)( zoomScale*imgToDisplay.getHeight() );
        
        System.out.println(height);
        System.out.println(width);
        
        BufferedImage zoomedImg = new BufferedImage(width, height, imgToDisplay.getType());  
        Graphics2D zoomGraphics = zoomedImg.createGraphics();  
        zoomGraphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION,  
                            RenderingHints.VALUE_INTERPOLATION_BICUBIC);  
        AffineTransform affine = AffineTransform.getScaleInstance(zoomScale, zoomScale);  
        zoomGraphics.drawRenderedImage(zoomedImg, affine);  
        zoomGraphics.dispose();  
        return zoomedImg;  
        
        
    }
    
    @Override
    public void stateChanged(ChangeEvent ce) {
        
        if ( imgToDisplay == null ) { return; }
        
        double zoom = ( (JSlider) (ce.getSource() ) ).getValue();
        System.out.println(zoom);
        double zoomScale = zoom/200.0;
        
        newImage( zoomImage(zoomScale) );
        
        
        
    }
    
    
}
