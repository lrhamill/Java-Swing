/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AlbumTrees;

import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author c1031996
 */
public class AlbumContainer implements Serializable {
    
    /** 
     * Initialise contents as a List interface as a defensive measure.
     * Makes it easier to adapt the code if ArrayList turns out to be an
     * inappropriate choice later on in development.
    **/
    
    List<PhotoAlbum> contents = new ArrayList();
    
    // Comparator allows us to sort contents by name
    static final Comparator<PhotoAlbum> NAME_ORDER = new Comparator<PhotoAlbum>() {
        public int compare(PhotoAlbum a1, PhotoAlbum a2) {
            return a1.getName().compareTo(a2.getName());
        }
    };
    
    public void addNewAlbum(PhotoAlbum newAlbum) {
        // Add album and sort by name
        
        contents.add(newAlbum);
        Collections.sort(contents, NAME_ORDER);        
    }
    
    public void removeAlbum ( String input ) {
        
        if ( input == null ) { return; }
        
        /**
         * A limitation of the app is that users can't delete an individual
         * album which shares a name with other albums. This method will delete
         * all albums with the name provided.
        **/
        
        //Copy list to iterate on (in case of multiple albums w/ same name)
        List<PhotoAlbum> iterateOn = new ArrayList<PhotoAlbum>(contents);
        for ( PhotoAlbum item : iterateOn ) {
            if ( item.getName() == input ) {
                contents.remove(item);
            } 
        }
    }
    
    public int getSize() {
        return contents.size();
    }
    
    public ArrayList<PhotoAlbum> getContents() {
        // Contents must be type cast with the appropriate List implementation
        return ( ArrayList<PhotoAlbum> ) contents;
    }
    
    public void deleteTag( String albumName, String tagName ) {
        
        // Finds the album and deletes the tag
        
        for (PhotoAlbum item : contents) {
            if ( item.getName() == albumName ) {
                item.deleteTag(tagName);
                return;
            }
        }
    }
    
    public String toString() {
        // Debug code
        
        if ( getSize() == 0 ) { return "Contents: nothing."; }
        
        String returnVal = "Contents:";
        
        for ( PhotoAlbum item : contents) {
            returnVal += "\n" + item.toString();
        }
        
        return returnVal;
        
    }
    
}
