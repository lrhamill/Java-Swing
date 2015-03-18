/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package swing.book;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.*;

/**
 *
 * @author c1031996
 */
public class ImageViewer extends JFrame {
    
    public ImageViewer(BufferedImage input) {
        
        setTitle("Image");
        setSize(300, 200);
        
        
        
        setVisible( true );
        
        
    }
    
}

class ImageComponent extends Component {
    
    private BufferedImage image;
    
    public ImageComponent (BufferedImage input) {
        
        image = input;
        
    }
    
    public void PaintComponent () {
        Graphics2D g = image.createGraphics();
        
    }
    
}
