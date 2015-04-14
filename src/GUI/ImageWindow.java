/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.*;

/**
 *
 * @author liamhamill
 */
public class ImageWindow extends JFrame implements ActionListener {
    
    JMenuBar menuBar;
    JPanel menuPanel;
    File imageFileToTag;
    
    public ImageWindow( BufferedImage input, File savedFile ) {
        
        imageFileToTag = savedFile;
        setTitle("Image");
        ImagePanel imgToDisplay = new ImagePanel(input);
        
        createMenu();
        add(imgToDisplay);
        pack();        
        setVisible( true );
                
    }
    
    private void createMenu() {
        
        menuPanel = new JPanel();
        menuBar = new JMenuBar();
        
        JMenu tag = new JMenu("Tag");
        tag.getAccessibleContext().setAccessibleDescription(
            "Tag this image for an album");
        JMenuItem tagImg = new JMenuItem( "Tag This Photo" );
        tagImg.setActionCommand("tagImage");
        
        tag.add(tagImg);
        menuBar.add(tag);
        menuPanel.add(menuBar);
        menuPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        add(menuPanel, BorderLayout.PAGE_START);

    }
    
    public void actionPerformed (ActionEvent e) {
        
        if ( "tagImage".equals(e.getActionCommand()) ) {
            
        }
        
    }
    
}

class ImagePanel extends JPanel {
    
    public ImagePanel( BufferedImage input ) {
        JLabel picLabel = new JLabel( new ImageIcon(input) );
        add(picLabel, BorderLayout.CENTER);
    }
}

