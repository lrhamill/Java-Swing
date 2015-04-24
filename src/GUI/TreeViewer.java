package GUI;

import AlbumTrees.AlbumContainer;
import AlbumTrees.ImageTag;
import AlbumTrees.PhotoAlbum;
import java.awt.BorderLayout;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
/**
 *
 * @author c1031996
 */
public class TreeViewer extends JPanel implements TreeSelectionListener {
    
    // Contains code for displaying and updating the JTree
    
    private JTree tree;
    private JScrollPane scrollPane;
    private DefaultMutableTreeNode top = new DefaultMutableTreeNode("Albums");
    private DefaultTreeModel model;
    
    public TreeViewer() {
        
        // Constructs JTree and places it inside a scroll pane
        
        setLayout( new BorderLayout() );
        tree = new JTree(top);
        model = (DefaultTreeModel) tree.getModel();
        tree.addTreeSelectionListener(this);
        scrollPane = new JScrollPane(tree);
               
        add( scrollPane, BorderLayout.CENTER );   
    
    }
    
    public void createNodes(AlbumContainer albums) {
        
        // Creates the JTree structure out of the AlbumContainer
        
        for ( PhotoAlbum item : albums.getContents() ) {
            
            // Add album
            DefaultMutableTreeNode thisAlbum = new DefaultMutableTreeNode(item.getName());
            top.add(thisAlbum);
            
            // Add album contents to album
            for ( ImageTag entry : item.getContents() ) {
                DefaultMutableTreeNode thisTag = new DefaultMutableTreeNode(entry.getName());
                thisAlbum.add(thisTag);
            }
        }
       
    }
    
    public void addAlbum( PhotoAlbum album ) {
        
        // Insert new album into JTree
        DefaultMutableTreeNode thisAlbum = new DefaultMutableTreeNode(album.getName());
        model.insertNodeInto(thisAlbum, top, top.getChildCount());
    }
    
    public void addTag( ImageTag tag ) {
        
        // Insert new tag into JTree
        
        // Go through album nodes until you find the right album
        for ( int i = 0; i < top.getChildCount(); i++ ) {
            
            DefaultMutableTreeNode album = (DefaultMutableTreeNode) top.getChildAt(i);
            
            // If album is correct, add the tag
            if ( tag.getAlbum().equals( (String) album.getUserObject() ) ) {
                DefaultMutableTreeNode thisTag = new DefaultMutableTreeNode(tag.getName());
                model.insertNodeInto(thisTag, album, album.getChildCount());
            }
        }
    }
    
    public boolean isNodeAlbum() {
    
        // Tests to see if node is an album by looking at its parent
        
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        if ( selectedNode.getParent() == top ) {
            return true;
        } else {
            return false;
        }
    }
    
    public String getNodeAlbumName() {
        
        // Gets the parent node of the selected tag
        
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        DefaultMutableTreeNode targetAlbum = (DefaultMutableTreeNode) selectedNode.getParent();
        
        return (String) targetAlbum.getUserObject();
        
    }
    
    public String[] getNodeTagInfo() {
        
        // Returns tag name and album name
        
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        DefaultMutableTreeNode targetAlbum = (DefaultMutableTreeNode) selectedNode.getParent();
        
        String returnAlbum = (String) targetAlbum.getUserObject();
        String returnTag = (String) selectedNode.getUserObject();
        
        String[] returnVal = { returnAlbum, returnTag };
        
        return returnVal;
        
    }
    
    public String deleteNode() {
        
        // Delete's selected node and returns its name so that it can be deleted
        // from AlbumContainer.
        
        DefaultMutableTreeNode selectedNode = getSelectedNode();
        if (selectedNode != null) {
            try {
                model.removeNodeFromParent(selectedNode);
                return selectedNode.toString();
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, "You cannot delete the album container.");
                return null;
            }
        } else {
            return null;
        }
    }
    
    public String getNodeName() {
        
        // Currently selected node's name.
        
        DefaultMutableTreeNode selectedNode = getSelectedNode();
        return selectedNode.toString();
    
    }
    
    public DefaultMutableTreeNode getSelectedNode() {
        
        return (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
    
    }
    
    @Override
    public void valueChanged(TreeSelectionEvent e) {
        
    }
    
    public void reload() {
        model.reload();
    }
}
