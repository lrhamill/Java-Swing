/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package swing.book;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;


/**
 *
 * @author c1031996
 */
public class MenuWindow extends JFrame {
    
    JPanel pnl = new JPanel();
    JMenuBar menuBar;
    JMenu filemenu;
    JMenuItem openFile;
    ImageIcon open = new ImageIcon("open.png");
    
    private void createMenu() {

        menuBar = new JMenuBar();
        
        filemenu = new JMenu("File");
        filemenu.getAccessibleContext().setAccessibleDescription(
            "Open files & exit");
        
        openFile = new JMenuItem( "Open file", open );
        openFile.setActionCommand("openImage");
        openFile.addActionListener(this);
        filemenu.add( openFile );
        
        menuBar.add(filemenu);
        
        pnl.add(menuBar);

    }
    
    public void openImage ( ActionEvent e ) {
        if ("openImage".equals(e.getActionCommand())) {
            JFileChooser imageFC = new JFileChooser();
            int returnVal = imageFC.showOpenDialog(this);
            
            if (returnVal == javax.swing.JFileChooser.APPROVE_OPTION) {
                BufferedImage img =  null;
                File selFile = imageFC.getSelectedFile();
                try {
                    img = ImageIO.read(selFile);
                } catch (IOException ex) {
                    Logger.getLogger(MenuWindow.class.getName()).log(Level.SEVERE, null, ex);
                    System.exit(1);
                }
                ImageViewer displayImg = new ImageViewer(img);
}
        }
        
    }
    
    public MenuWindow() {
        super( "Window w/ a menu" );
        setSize( 500, 200 );
        setDefaultCloseOperation( EXIT_ON_CLOSE );
        setLayout(new FlowLayout(FlowLayout.LEFT));
        
        createMenu();

        add( pnl );
        setVisible( true );
    }
    
}

class MenuActionListener implements ActionListener {
  
    public void actionPerformed(ActionEvent e) {
        if ("openImage".equals(e.getActionCommand())) {
            openImage();
        }
    }
    
}
    

  