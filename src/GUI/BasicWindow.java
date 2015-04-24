package GUI;

/**
 *
 * @author c1031996
 */

import AlbumTrees.AlbumContainer;
import AlbumTrees.AlbumSerializer;
import AlbumTrees.ImageTag;
import AlbumTrees.PhotoAlbum;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.tree.DefaultMutableTreeNode;

public class BasicWindow extends JFrame implements ActionListener {
    
    // Used to save/load albums
    AlbumSerializer Serializer = new AlbumSerializer(); 
    AlbumContainer albumsToDisplay;
    
    // Top-level UI components
    JPanel pnl = new JPanel();
    JPanel menuPanel = new JPanel();
    JTabbedPane iPnl = new JTabbedPane();
    JScrollPane scrollableiPnl = new JScrollPane(iPnl);
    JPanel tPnl = new JPanel();
    TreeViewer tree = new TreeViewer();
    JMenuBar menuBar;
    JSlider slider;
    
    // Minimum size setting for image panel
    Dimension minSize = new Dimension(300,300);    
    
    // Menu icons
    ImageIcon open = new ImageIcon("open.png");
    ImageIcon tag = new ImageIcon("tag.png"); 
    ImageIcon albumIcon = new ImageIcon("album.png");
    
    // Image and array to contain selected file objects
    BufferedImage img =  null;
    List<File> selFiles = new ArrayList<File>();
    
    // Image to tag
    File imgToTag = null;
    
    private void createTree() {
        
        // Load albums if there are none already
        
        if ( albumsToDisplay == null ) {
            albumsToDisplay = Serializer.deserializeAlbums();
        }
        tree.createNodes(albumsToDisplay);
        tree.setPreferredSize( new Dimension(200, 300) );
        
        // Create buttons for managing the album system
        
        JButton delBtn = new JButton("Delete Album/Tag");
        delBtn.setActionCommand("delNode");
        delBtn.addActionListener(this);
        
        JButton openBtn = new JButton("Open Selected Image");
        openBtn.setActionCommand("openTag");
        openBtn.addActionListener(this);
        
        JButton renameBtn = new JButton("Rename Album/Tag");
        renameBtn.setActionCommand("renameTag");
        renameBtn.addActionListener(this);
        
        // Buttons get their own panel inside tPnl
        
        JPanel btnPnl = new JPanel();
        btnPnl.setLayout( new BorderLayout() );
        btnPnl.add( openBtn, BorderLayout.NORTH );
        btnPnl.add( delBtn, BorderLayout.SOUTH );
        btnPnl.add( renameBtn, BorderLayout.CENTER );
        
        // Create labels and place them in tPnl
        
        JLabel albumView = new JLabel("Album View\n");
        JLabel zoomer = new JLabel ("Zoom (%) \n");
        
        JPanel lblPnl = new JPanel();
        lblPnl.setLayout( new BorderLayout() );
        lblPnl.add(albumView, BorderLayout.WEST);
        lblPnl.add(zoomer, BorderLayout.EAST);
        
        tPnl.setLayout( new BorderLayout() );
        tPnl.add( tree, BorderLayout.CENTER );
        tPnl.add( btnPnl, BorderLayout.SOUTH );
        tPnl.add( lblPnl, BorderLayout.NORTH);
        
        // Add zoom slider
        
        createSlider();
        tPnl.add( slider, BorderLayout.EAST );
    }
    
    public void createSlider() {
        
        // Creates the slider object used to zoom images.
        
        slider = new JSlider(JSlider.VERTICAL, 25, 200, 100);  
        slider.setMajorTickSpacing(25);  
        slider.setMinorTickSpacing(10);  
        slider.setPaintTicks(true);  
        slider.setPaintLabels(true);    
    }
    
    private void createMenu() {
        
        // Creates a JMenuBar. Fairly self-explanatory.

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
        
        JMenuItem tagImage = new JMenuItem( "Tag an open image", tag );
        tagImage.setActionCommand( "tagImage" );
        tagImage.addActionListener( this );
        tagMenu.add( tagImage );
        
        // Pull the menubar together.
        
        menuBar.add( filemenu );
        menuBar.add( albumMenu );
        menuBar.add( tagMenu );
        menuPanel.add( menuBar );
        menuPanel.setLayout( new FlowLayout( FlowLayout.LEFT ) );
        
        pnl.add(menuPanel, BorderLayout.NORTH);

    }
    
    @Override
    public void actionPerformed( ActionEvent e ) {
        
        // Event handling for various menu and button commands.
        
        if ( null != e.getActionCommand() ) switch ( e.getActionCommand() ) {
            case "openImage":
                
                // Creates an open image dialog that only allows files that can
                // be parsed by ImageIO to be selected.
                
                JFileChooser imageFC = new JFileChooser();
                FileFilter validFiles = new ImageFileFilter();
                imageFC.addChoosableFileFilter(validFiles);
                imageFC.setAcceptAllFileFilterUsed(false);
                imageFC.setFileFilter(validFiles);
                int returnVal = imageFC.showOpenDialog(this);
                
                // Open image
                if ( returnVal == javax.swing.JFileChooser.APPROVE_OPTION ) {
                    
                    File newFile = imageFC.getSelectedFile();
                    selFiles.add(newFile);
                    openImage(newFile);
                    
                }   
                
                break;
                
            case "newAlbum":{
                
                // Opens a dialog for an album name prompt.
                
                JFrame container = new JFrame();
                String prompt = "What would you like to call your new Album?";
                String input = JOptionPane.showInputDialog(container, prompt);
                
                // Creates a new album.
                
                if (input != null) {
                    addNewAlbum(input);
                }       
                
                break;
            
                }
            
            case "delNode":   
                
                // Confirm decision to delete node.
                
                int yesNo = JOptionPane.YES_NO_OPTION;
                int confirm = JOptionPane.showConfirmDialog ( null, "Are you sure you want to delete this tag/album?", "Warning", yesNo );
                if ( confirm == JOptionPane.YES_OPTION) {
                    
                    // Delete node.
                    
                    if ( tree.isNodeAlbum() ) {
                        albumsToDisplay.removeAlbum( tree.deleteNode() );
                    } else {
                        try {
                            String targetAlbum = tree.getNodeAlbumName();
                            albumsToDisplay.deleteTag( targetAlbum, tree.deleteNode() );
                            
                        } catch ( NullPointerException nullE ) {
                            // Occurs if you try to delete the AlbumContainer.
                            JOptionPane.showMessageDialog(null, "You cannot delete the album container.");
                            return;
                        }
                    }
                                        
                    // Save modified AlbumContainer
                    Serializer.serializeAlbums(albumsToDisplay);
                    
                }     
                
                break;
                
            case "openTag":
                
                // Opens a tag, if it's an image and not an album.
                
                if ( tree.isNodeAlbum() ) {
                    JOptionPane.showMessageDialog(null, "You can only open an "
                            + "image tag, not an album.");
                    
                } else {
                    try {
                        // Get album and tag name.
                        String[] tagInfo = tree.getNodeTagInfo();
                        
                        // Search for tag.
                        for ( PhotoAlbum item : albumsToDisplay.getContents() ) {
                            if ( item.getName() == tagInfo[0] ) {
                                for ( ImageTag entry : item.getContents() ) {
                                    if ( entry.getName() == tagInfo[1] ){
                                        
                                        // Open tag.
                                        File newFile = new File(entry.getFilePath());
                                        selFiles.add(newFile);
                                        openImage(newFile);
                                    }
                                }
                            }
                        }
                    } catch (NullPointerException nullE) {
                        // Occurs if you try to open the AlbumContainer.
                        JOptionPane.showMessageDialog(null, "You can only open an "
                                + "image tag, not an album.");
                    }
                }
                
                break;
            
            case "tagImage":
                
                // Creates a new ImageTag, if an image is open.
                tagImage();
                
                break;
            
            case "renameTag":
                
                // Prompt for a new name.
                JFrame container = new JFrame();
                String prompt = "What would you like to rename this album/tag?";
                    String input = JOptionPane.showInputDialog(container, prompt);
                    if (input != null) {
                        
                        // Rename album/tag.
                        renameNode(input);
                    }
                
                break;            
                
        }
    }
    
    public void tagImage() {
        
        // Reset imgToTag field
        this.imgToTag = null;
        
        // Check that there is an image to be tagged and at least one album.
        
        if ( albumsToDisplay.getSize() == 0 ) {
            JOptionPane.showMessageDialog(null, "You must add an album before "
                                          + "you can tag an image.");
            return;
        }
        
        if ( selFiles.size() == 0 ) {
            JOptionPane.showMessageDialog(null, "You must open an image before "
                                          + "you can tag it.");
            return;
        }
        
        // Get a list of available albums, extract names and convert to array
        
        ArrayList<PhotoAlbum> availableAlbums = albumsToDisplay.getContents();        
        List<String> availableAlbumNames = new ArrayList<String>();
        
        for ( PhotoAlbum item : availableAlbums ) {
            availableAlbumNames.add(item.getName());
        }
        
        Object[] nameArray = new Object[availableAlbumNames.size()];
        nameArray = availableAlbumNames.toArray(nameArray);        
        
        // Prompt user to select from array of album names.
        
        String albumName = (String) JOptionPane.showInputDialog(
                                                null,
                                                "Which album would you like"
                                                + "to tag an image into?",
                                                "Tag an Image",
                                                JOptionPane.PLAIN_MESSAGE,
                                                null,
                                                nameArray, nameArray[0]);
        
        if ( albumName == null ) { return; }
        
        if ( selFiles.size() == 1 ) {
            
            // If only one file is open, use that one.
            this.imgToTag = selFiles.get(0);
        
        } else {
            
            // Otherwise, create an array of filenames.
            List<String> imgNames = new ArrayList<String>();
            for ( File item : selFiles ) {
                imgNames.add( item.getName() );
            }
            Object[] imgNameArray = new Object[imgNames.size()];
            imgNameArray = imgNames.toArray(imgNameArray);
            
            // Prompt user to select from array of images.
        
            String imgToTagName = (String) JOptionPane.showInputDialog(
                                                    null,
                                                    "Which image would you like"
                                                    + "to tag?", "Tag an Image",
                                                    JOptionPane.PLAIN_MESSAGE,
                                                    null,
                                                    imgNameArray, imgNameArray[0]);
            
            if ( imgToTagName == null ) { return; }
            
            // Find the right image file path
            
            for ( File item : selFiles ) {

                if ( item.getName().equals(imgToTagName) ) {
                    this.imgToTag = item;
                    break;
                }
            }
            
        }

        
        // Create new ImageTag and add to the designated album.
        ImageTag newTag = new ImageTag(albumName, imgToTag);
        for (PhotoAlbum item : albumsToDisplay.getContents()) {
            if ( item.getName() == albumName ) {
                item.addToContents(newTag);
            }
        }
        
        //Update TreeViewer
        
        tree.addTag(newTag);
    }
    
    public void renameNode( String input ) {
        
        // Changes album or tag name.
        
        try {
            if ( tree.isNodeAlbum()) {
                
            albumsToDisplay.renameAlbum( tree.getNodeName(), input);
            
            } else {
                
                albumsToDisplay.renameTag( tree.getNodeAlbumName(), tree.getNodeName(), input );
            
            }

            // Save modified AlbumContainer    
            Serializer.serializeAlbums(albumsToDisplay);

            // Update JTree
            DefaultMutableTreeNode selectedNode = tree.getSelectedNode();
            selectedNode.setUserObject(input);
            tree.reload();
                
        } catch (NullPointerException e) {
            
            // Occurs when the user attempts to rename the album container.
            JOptionPane.showMessageDialog(null, "You cannot rename the album container.");
        }
    }
    
    public void openImage (File selFile) {

            try {
                // Open image.
                img = ImageIO.read(selFile);
            
            } catch (IOException ex) {
                
                // Occurs when a file cannot be read, e.g. if a user attempts
                // to open an image tag when the image has been deleted.
                
                JOptionPane.showMessageDialog(null, "Unable to open the "
                                          + "designated image.");
                
                return;
            
            }
            
            // Reset preferred size of image pane to default.
            if ( scrollableiPnl.getPreferredSize() != null ) {
                scrollableiPnl.setPreferredSize(null);
            }
            // Create a new TabbedImagePane containing the opened image.
            TabbedImagePane newImgPane = new TabbedImagePane(selFile);
            newImgPane.newImage(img);
            
            // Binds the new tabbed pane to the zoom slider and tab closer.
            slider.addChangeListener(newImgPane);
            TabCloseActionHandler tabCloser = new TabCloseActionHandler(selFile.getName());
            
            // Creates a title JPanel for the tab.
            JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            titlePanel.setOpaque(false);
            JLabel titleLbl = new JLabel(selFile.getName());
            titleLbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
            titlePanel.add(titleLbl);
            
            // Create & add close button.
            JButton closeButton = new JButton("x");
            closeButton.addActionListener(tabCloser);
            titlePanel.add(closeButton);
            
            // Add the TabbedImagePane to the image panel
            iPnl.addTab(selFile.getName(), newImgPane);
            iPnl.setTabComponentAt(iPnl.indexOfComponent(newImgPane), titlePanel);
            this.pack();            
    }
    
    public void addNewAlbum( String input ) {
        
        // Adds an album to the AlbumContainer.
        
        PhotoAlbum newAlbum = new PhotoAlbum(input);
        albumsToDisplay.addNewAlbum( newAlbum );
        Serializer.serializeAlbums(albumsToDisplay);
        
        // Update the tree.
        tree.addAlbum(newAlbum);

    }
    
    public void deleteAlbum( String input ) {
        
        // Removes album.
        
        albumsToDisplay.removeAlbum(input);
        Serializer.serializeAlbums(albumsToDisplay);
        
    }
    
    public BasicWindow() {
        
        // Constructor for the central window.
        
        super( "Image Viewer" );
        
        // Always save albums on exit, as a safety precaution
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(WindowEvent winEvt) {
                Serializer.serializeAlbums(albumsToDisplay);
                System.exit(0);
            }
        });
        
        //Set up GUI
        
        setLayout( new BorderLayout() );
        pnl.setLayout( new BorderLayout() );
        
        createMenu();
        createTree();
        pnl.add( tPnl, BorderLayout.WEST );
        scrollableiPnl.setPreferredSize(minSize);
        pnl.add( scrollableiPnl, BorderLayout.CENTER );
        
        add( pnl );
        pack();
        setVisible( true );
    }
    
    // Nested class. An instance of this class is created to handle tab closing
    // events.
    
    public class TabCloseActionHandler implements ActionListener {

        private final String tabName;

        public TabCloseActionHandler(String tabName) {
            this.tabName = tabName;
        }

        public String getTabName() {
            return tabName;
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            
            // Close tab and get rid of associated file object
            
            int index = iPnl.indexOfTab(getTabName());
            if (index >= 0) {
                File target = selFiles.get(index);
                selFiles.remove(target);
                iPnl.removeTabAt(index);
            }

        }

    }   
    
}