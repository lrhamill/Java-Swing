
package GUI;

import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author c1031996
 */
public class TabbedImagePane extends JPanel implements ChangeListener {
    
    // Contains a BufferedImage that is converted to JLabel and displayed in a
    // JScrollpane. imgToDisplay is the "canonical" image to which
    // scale transformations are applied to.
    
    JLabel picLabel = new JLabel();
    BufferedImage imgToDisplay;
    JScrollPane scroller = new JScrollPane(picLabel);
    File source;
    
    TabbedImagePane( File input ) {
        
        // Constructor binds scroller to to the pane.
        add(scroller, BorderLayout.CENTER);
        
        // When the user wants to tag an image
        source = input;
    }
    
    public File getSource() {
        return source;
    }
    
    public void newImage ( BufferedImage input ) {
        
        // Updates image and loads new imgToDisplay
        
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
        
        revalidate();
        repaint();
        
    }
    
    
    
    public BufferedImage zoomImage( double zoomScale ) {
        
        // Code to scale an image using affine transformation
        
        // First acquire new height/width
        
        int width  = (int)( zoomScale * imgToDisplay.getWidth() );  
        int height  = (int)( zoomScale * imgToDisplay.getHeight() );
        
        // Create an empty BufferedImage, construct a graphics object out of it
        
        BufferedImage zoomedImg = new BufferedImage(width, height, imgToDisplay.getType());  
        Graphics2D zoomGraphics = zoomedImg.createGraphics();  
        
        // Define the AffineTransform scale and create a scaled image
        
        AffineTransform affine = AffineTransform.getScaleInstance(zoomScale, zoomScale);  
        zoomGraphics.drawRenderedImage(imgToDisplay, affine);  
        zoomGraphics.dispose();  
        
        return zoomedImg;  
        
        
    }
    
    @Override
    public void stateChanged(ChangeEvent ce) {
        
        // When the user moves the scale, zoom the image as appropriate.
        
        if ( imgToDisplay == null ) { return; }
        
        double zoom = ( (JSlider) ce.getSource() ).getValue();
        double zoomScale = zoom/100.0;
        
        updateImage( zoomImage(zoomScale) );
        
    }
    
    
    
    
}
