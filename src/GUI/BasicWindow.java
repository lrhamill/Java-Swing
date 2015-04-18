package GUI;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author c1031996
 */

import AlbumTrees.AlbumContainer;
import AlbumTrees.AlbumSerializer;
import AlbumTrees.ImageTag;
import AlbumTrees.PhotoAlbum;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class BasicWindow extends JFrame implements ActionListener {
    
    // Used to save/load albums
    AlbumSerializer Serializer = new AlbumSerializer(); 
    AlbumContainer albumsToDisplay;
    
    // UI components
    JPanel pnl = new JPanel();
    JPanel menuPanel = new JPanel();
    ImagePanel iPnl = new ImagePanel();
    JPanel tPnl = new ImagePanel();
    TreeViewer tree = new TreeViewer();
    JMenuBar menuBar;
    
    // Menu icons
    ImageIcon open = new ImageIcon("open.png");
    ImageIcon tag = new ImageIcon("tag.png"); 
    ImageIcon albumIcon = new ImageIcon("album.png");
    
    // Image and selected file object
    BufferedImage img =  null;
    File selFile = null;
    
    private void createTree() {
        
        // Load albums if there are none already
        if ( albumsToDisplay == null ) {
            albumsToDisplay = Serializer.deserializeAlbums();
        }
        tree.createNodes(albumsToDisplay);
        
        JButton delBtn = new JButton("Delete Album/Tag");
        delBtn.setActionCommand("delNode");
        delBtn.addActionListener(this);
        
        JButton openBtn = new JButton("Open Selected Image");
        openBtn.setActionCommand("openTag");
        openBtn.addActionListener(this);
        
        JPanel btnPnl = new JPanel();
        btnPnl.add( openBtn, BorderLayout.CENTER );
        btnPnl.add( delBtn, BorderLayout.SOUTH );
        
        tPnl.setLayout( new BorderLayout() );
        tPnl.add( tree, BorderLayout.CENTER );
        tPnl.add( btnPnl, BorderLayout.SOUTH );
        
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
        
        JMenu albumMenu = new JMenu("Album");
        filemenu.getAccessibleContext().setAccessibleDescription(
            "Create and manage albums");
        
        JMenuItem newAlbum = new JMenuItem( "New Album", albumIcon );
        newAlbum.setActionCommand( "newAlbum" );
        newAlbum.addActionListener( this );
        albumMenu.add( newAlbum );
        
        JMenu tagMenu = new JMenu("Tag");
        filemenu.getAccessibleContext().setAccessibleDescription(
            "Tag an image");
        
        JMenuItem tagImage = new JMenuItem( "Tag the current image", tag );
        tagImage.setActionCommand( "tagImage" );
        tagImage.addActionListener( this );
        tagMenu.add( tagImage );
        
        
        menuBar.add( filemenu );
        menuBar.add( albumMenu );
        menuBar.add( tagMenu );
        menuPanel.add( menuBar );
        menuPanel.setLayout( new FlowLayout( FlowLayout.LEFT ) );
        
        pnl.add(menuPanel, BorderLayout.NORTH);

    }
    
    public void actionPerformed(ActionEvent e) {
        if ("openImage".equals(e.getActionCommand())) {
            JFileChooser imageFC = new JFileChooser();
            
            // Array that stores ImageIO file types
            String[] fileTypes = ImageIO.getReaderFileSuffixes();
            
            // Set 
            for (int i = 0; i < fileTypes.length; i++) {
                FileFilter filter = new FileNameExtensionFilter(fileTypes[i] + " files", fileTypes[i]);
                imageFC.addChoosableFileFilter(filter);
                }
            
            
            int returnVal = imageFC.showOpenDialog(this);
            
            if ( returnVal == javax.swing.JFileChooser.APPROVE_OPTION ) {
                    
                    selFile = imageFC.getSelectedFile();
                    openImage(selFile);

            }
        }
        
        else if ("newAlbum".equals(e.getActionCommand())) {
            JFrame container = new JFrame();
            String prompt = "What would you like to call your new Album?";
            String input = JOptionPane.showInputDialog(container, prompt);
            
            if (input != null) {
                addNewAlbum(input);
            }
            
        } else if ("delNode".equals(e.getActionCommand())) {
            int yesNo = JOptionPane.YES_NO_OPTION;
            int confirm = JOptionPane.showConfirmDialog ( null, "Are you sure you want to delete this tag/album?", "Warning", yesNo );
            
            if ( confirm == JOptionPane.YES_OPTION) {
                if ( tree.isNodeAlbum()) {
                albumsToDisplay.removeAlbum( tree.deleteNode() );
            } else {
                    String targetAlbum = tree.getNodeAlbumName();
                    albumsToDisplay.deleteTag( targetAlbum, tree.deleteNode() );
                }
                
            // Save modified AlbumContainer    
            Serializer.serializeAlbums(albumsToDisplay);
        
          }
            
        } else if ("openTag".equals(e.getActionCommand())) {
            if ( tree.isNodeAlbum() ) {
                JOptionPane.showMessageDialog(null, "You can only open an "
                                          + "image tag, not an album.");
                return;
            } else {
                String[] tagInfo = tree.getNodeTagInfo();
                for ( PhotoAlbum item : albumsToDisplay.getContents() ) {
                    if ( item.getName() == tagInfo[0] ) {
                        for ( ImageTag entry : item.getContents() ) {
                            if ( entry.getName() == tagInfo[1] ){
                                File selFile = new File(entry.getFilePath());
                                openImage(selFile);
                            }
                        }
                    }
                }
            }
            
        } else if ("tagImage".equals(e.getActionCommand())) {
                tagImage();
        }
    }
    
    public void tagImage() {
        
        // Check that there is an image to be tagged and at least one album.
        
        if ( albumsToDisplay.getSize() == 0 ) {
            JOptionPane.showMessageDialog(null, "You must add an album before "
                                          + "you can tag an image.");
            return;
        }
        
        if ( img == null || selFile == null ) {
            JOptionPane.showMessageDialog(null, "You must open an image before "
                                          + "you can tag it.");
            return;
        }
        
        // Get a list of available albums, extract names and convert to Object[]
        
        ArrayList<PhotoAlbum> availableAlbums = albumsToDisplay.getContents();
        
        List<String> availableAlbumNames = new ArrayList<String>();
        
        for ( PhotoAlbum item : availableAlbums ) {
            availableAlbumNames.add(item.getName());
        }
        
        Object[] nameArray = new Object[availableAlbumNames.size()];
        nameArray = availableAlbumNames.toArray(nameArray);
        
        String albumName = (String) JOptionPane.showInputDialog(
                                                null,
                                                "Which album would you like"
                                                + "to tag this image into?",
                                                "Tag an Image",
                                                JOptionPane.PLAIN_MESSAGE,
                                                null,
                                                nameArray, nameArray[0]);
        
        if ( albumName == null ) { return; }
        
        ImageTag newTag = new ImageTag(albumName, selFile);
        for (PhotoAlbum item : albumsToDisplay.getContents()) {
            if ( item.getName() == albumName ) {
                item.addToContents(newTag);
            }
        }
        
        //Update TreeViewer
        
        tree.addTag(newTag);
    }
    
    public void openImage (File selFile) {

            try {
                img = ImageIO.read(selFile);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Unable to open the "
                                          + "designated image.");
                return;
            }
            
            iPnl.newImage(img);
            iPnl.validate();
            iPnl.repaint();
            
            this.pack();            
    }
    
    public void addNewAlbum( String input ) {
        
        PhotoAlbum newAlbum = new PhotoAlbum(input);
        albumsToDisplay.addNewAlbum( newAlbum );
        Serializer.serializeAlbums(albumsToDisplay);
        
        tree.addAlbum(newAlbum);

    }
    
    public void deleteAlbum( String input ) {
        
        albumsToDisplay.removeAlbum(input);
        Serializer.serializeAlbums(albumsToDisplay);
        
    }
    
    public BasicWindow() {
        super( "Image Viewer" );
        
        // Save albums on exit
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(WindowEvent winEvt) {
                Serializer.serializeAlbums(albumsToDisplay);
                System.exit(0);
            }
        });
        
        setLayout( new BorderLayout() );
        pnl.setLayout( new BorderLayout() );
        
        createMenu();
        createTree();
        pnl.add( tPnl, BorderLayout.WEST );
        pnl.add( iPnl, BorderLayout.CENTER );
        add( pnl );
        pack();
        setVisible( true );
    }

}

  