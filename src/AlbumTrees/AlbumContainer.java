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
        @Override
        public int compare(PhotoAlbum a1, PhotoAlbum a2) {
            return a1.getName().compareTo(a2.getName());
        }
    };
    
    public void addNewAlbum(PhotoAlbum newAlbum) {
        // Add album and sort by name
        
        contents.add(newAlbum);
        Collections.sort(contents, NAME_ORDER);        
    }
    
    public void removeAlbum( String target ) {
        
        if ( target == null ) { return; }
        
        /**
         * A limitation of the app is that users can't delete an individual
         * album which shares a name with other albums. This method will delete
         * all albums with the name provided.
        **/
        
        //Copy list to iterate on (in case of multiple albums w/ same name)
        List<PhotoAlbum> iterateOn = new ArrayList<>(contents);
        for ( PhotoAlbum item : iterateOn ) {
            if ( item.getName().equals(target) ) {
                contents.remove(item);
            } 
        }
    }
    
    public void renameAlbum( String target, String input ) {
        
        if ( input == null || target == null ) { return; }
        
        List<PhotoAlbum> iterateOn = new ArrayList<>(contents);
        for ( PhotoAlbum item : iterateOn ) {
            if ( item.getName().equals(target) ) {
                item.changeName(input);
                Collections.sort(contents, NAME_ORDER);
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
            if ( item.getName().equals(albumName) ) {
                item.deleteTag(tagName);
                return;
            }
        }
    }
    
    public void renameTag( String targetAlbum, String targetTag, String input ) {
        
        if ( input == null || targetAlbum == null || targetTag == null ) { return; }
        
        List<PhotoAlbum> iterateOn = new ArrayList<>(contents);
        for ( PhotoAlbum item : iterateOn ) {
            if ( item.getName().equals(targetAlbum) ) {
                item.renameTag(targetTag, input);
            } 
        }        
    }
    
    @Override
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
