/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author c1031996
 */
public class ImagePanel extends JPanel {
    
    JLabel picLabel;
    
    ImagePanel ( ) {
        picLabel = new JLabel();
        Dimension defaultSize = new Dimension(300, 300);
        picLabel.setPreferredSize(defaultSize);
        add(picLabel, BorderLayout.CENTER);
    }

    
    
    public void newImage ( BufferedImage input ) {
        remove(picLabel);
        picLabel = new JLabel( new ImageIcon(input) );
        add(picLabel, BorderLayout.CENTER);
    }
}
