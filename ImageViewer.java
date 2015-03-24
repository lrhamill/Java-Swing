/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package swing.book;
import java.awt.image.BufferedImage;
import javax.swing.*;

/**
 *
 * @author c1031996
 */
public class ImageViewer extends JFrame {
    
    int width;
    int height;
    
    public ImageViewer(BufferedImage input) {
        
        setTitle("Image");
        
        ImagePanel imgToDisplay = new ImagePanel(input);
        add(imgToDisplay);
        pack();        

        setVisible( true );
                
    }
    
}

class ImagePanel extends JPanel {
    
    public ImagePanel(BufferedImage input) {
        JLabel picLabel = new JLabel(new ImageIcon(input));
        add(picLabel);
    }
}
