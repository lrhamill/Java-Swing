/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import AlbumTrees.AlbumContainer;
import AlbumTrees.ImageTag;
import AlbumTrees.PhotoAlbum;
import java.awt.BorderLayout;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
/**
 *
 * @author liamhamill
 */
public class TreeViewer extends JPanel implements TreeSelectionListener {
    
    private JTree tree;
    private JScrollPane scrollPane;
    private DefaultMutableTreeNode top = new DefaultMutableTreeNode("Albums");
    
    public TreeViewer() {
        
        setLayout( new BorderLayout() );
        tree = new JTree(top);
        tree.addTreeSelectionListener(this);
        scrollPane = new JScrollPane(tree);
               
        add( scrollPane, BorderLayout.CENTER );   
    
    }
    
    public void createNodes(AlbumContainer albums) {
        
        for ( PhotoAlbum item : albums.getContents() ) {
            
            DefaultMutableTreeNode thisAlbum = new DefaultMutableTreeNode(item.getName());
            top.add(thisAlbum);
            
            for ( ImageTag entry : item.getContents() ) {
                DefaultMutableTreeNode thisTag = new DefaultMutableTreeNode(entry.getName());
                thisAlbum.add(thisTag);
            }
        }
       
    }
    
    public void valueChanged(TreeSelectionEvent e) {
        
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

        if (node == null) return;
    }
}
