package GUI;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author liamhamill
 */

import AlbumTrees.AlbumContainer;
import AlbumTrees.AlbumSerializer;
import java.awt.BorderLayout;
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

public class BasicWindow extends JFrame implements ActionListener {
    
    JPanel pnl = new JPanel();
    TreeViewer tree = new TreeViewer();
    JMenuBar menuBar;
    JMenu filemenu;
    JMenuItem openFile;
    ImageIcon open = new ImageIcon("open.png");
    AlbumContainer albumsToDisplay;
    
    private void createTree() {
        AlbumSerializer Serializer = new AlbumSerializer();
        albumsToDisplay = Serializer.deserializeAlbums();
        
        tree.createNodes(albumsToDisplay);
        
    }
    
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
    
    public void actionPerformed(ActionEvent e) {
        if ("openImage".equals(e.getActionCommand())) {
            
            JFileChooser imageFC = new JFileChooser();
            int returnVal = imageFC.showOpenDialog(this);
            
            if ( returnVal == javax.swing.JFileChooser.APPROVE_OPTION ) {
                BufferedImage img =  null;
                File selFile = imageFC.getSelectedFile();
                try {
                    img = ImageIO.read(selFile);
                } catch (IOException ex) {
                    Logger.getLogger(BasicWindow.class.getName()).log(Level.SEVERE, null, ex);
                    System.exit(1);
                }
                ImageWindow displayImg = new ImageWindow(img, selFile);
            }
        }
    }
    
        
    
    public BasicWindow() {
        super( "Image Viewer" );
        //setSize( 500, 200 );
        setDefaultCloseOperation( EXIT_ON_CLOSE );
        setLayout( new BorderLayout() );
        
        createMenu();
        createTree();
        
        add( pnl, BorderLayout.PAGE_START );
        add( tree, BorderLayout.CENTER );
        pack();
        setVisible( true );
    }
    
}

  