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
import AlbumTrees.PhotoAlbum;
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
    
    // Used to save/load albums
    AlbumSerializer Serializer = new AlbumSerializer(); 
    AlbumContainer albumsToDisplay;
    
    // UI components
    JPanel pnl = new JPanel();
    JPanel menuPanel = new JPanel();
    ImagePanel iPnl = new ImagePanel();
    TreeViewer tree = new TreeViewer();
    JMenuBar menuBar;
    
    // Menu icons
    ImageIcon open = new ImageIcon("open.png");
    ImageIcon tag = new ImageIcon("tag.png"); 
    ImageIcon albumIcon = new ImageIcon("album.png");
    
    private void createTree() {
        
        if ( albumsToDisplay == null ) {
        albumsToDisplay = Serializer.deserializeAlbums();
        }
        
        tree.createNodes(albumsToDisplay);        
    }
    
    private void createMenu() {

        menuBar = new JMenuBar();
        
        JMenu filemenu = new JMenu("File");
        filemenu.getAccessibleContext().setAccessibleDescription(
            "Open files & exit");
        
        JMenuItem openFile = new JMenuItem( "Open file", open );
        openFile.setActionCommand("openImage");
        openFile.addActionListener(this);
        filemenu.add( openFile );
        
        JMenu tagMenu = new JMenu("Tag");
        filemenu.getAccessibleContext().setAccessibleDescription(
            "Tag an image");
        
        JMenuItem tagImage = new JMenuItem( "Tag the current image", tag );
        tagImage.setActionCommand( "tagImage" );
        tagImage.addActionListener( this );
        tagMenu.add( tagImage );
        
        JMenu albumMenu = new JMenu("Album");
        filemenu.getAccessibleContext().setAccessibleDescription(
            "Create and manage albums");
        
        JMenuItem newAlbum = new JMenuItem( "New Album", albumIcon );
        newAlbum.setActionCommand( "newAlbum" );
        newAlbum.addActionListener( this );
        albumMenu.add( newAlbum );
        
        
        menuBar.add( filemenu );
        menuBar.add( tagMenu );
        menuBar.add( albumMenu );
        menuPanel.add( menuBar );
        menuPanel.setLayout( new FlowLayout( FlowLayout.LEFT ) );
        
        pnl.add(menuPanel, BorderLayout.NORTH);

    }
    
    public void actionPerformed(ActionEvent e) {
        if ("openImage".equals(e.getActionCommand())) {
            openImage();
        }
        
        else if ("newAlbum".equals(e.getActionCommand())) {
            JFrame container = new JFrame();
            String prompt = "What would you like to call your new Album?";
            String input = JOptionPane.showInputDialog(container, prompt);
            addNewAlbum(input);
        }
    }
    
    public void openImage () {
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
            iPnl.newImage(img);
            iPnl.validate();
            iPnl.repaint();
        }            
    }
    
    public void addNewAlbum( String input ) {
        
        PhotoAlbum newAlbum = new PhotoAlbum(input);
        albumsToDisplay.addNewAlbum( newAlbum );
        
        Serializer.serializeAlbums(albumsToDisplay);
        tree.deleteAllNodes();
        
        
        
    }
    
    public BasicWindow() {
        super( "Image Viewer" );
        //setSize( 500, 200 );System.out.println(input);
        setDefaultCloseOperation( EXIT_ON_CLOSE );
        setLayout( new BorderLayout() );
        pnl.setLayout( new BorderLayout() );
        
        createMenu();
        createTree();
        pnl.add(tree, BorderLayout.WEST);
        pnl.add( iPnl, BorderLayout.CENTER);
        add( pnl );
        pack();
        setVisible( true );
    }

}

  