
package AlbumTrees;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author c1031996
 */
public class PhotoAlbum implements Serializable {
    
    /** 
     * Initialise contents as a List interface as a defensive measure.
     * Makes it easier to adapt the code if ArrayList turns out to be an
     * inappropriate choice later.
    **/
    
    List<ImageTag> contents = new ArrayList();
    String name;
    
    // Comparator allows us to sort contents by name
    static final Comparator<ImageTag> NAME_ORDER = new Comparator<ImageTag>() {
        @Override
        public int compare(ImageTag i1, ImageTag i2) {
            return i1.getName().compareTo(i2.getName());
        }
    };
    
    public PhotoAlbum(String nameInput) {
        name = nameInput;
    }
    
    public void addToContents(ImageTag newItem) {
        // Add ImageTag and sort
        
        contents.add(newItem);
        Collections.sort(contents, NAME_ORDER);
    }
    
    public ArrayList<ImageTag> getContents() {
        
        // Contents must be type cast with the appropriate List implementation
        return ( ArrayList<ImageTag> ) contents;
    }
    
    public String getName() {
        return name;
    }
    
    public void deleteTag( String target ) {
        if ( target == null ) { return; }
        
        /**
         * A limitation of the app is that users can't delete an individual
         * tag which shares a name with other tags. This method will delete
         * all tags with the name provided.
        **/
        
        //Copy list to iterate on (in case of multiple tags w/ same name)
        List<ImageTag> iterateOn = new ArrayList<ImageTag>(contents);
        for ( ImageTag item : iterateOn ) {
            if ( item.getName() == target ) {
                contents.remove(item);
            } 
        }
    }
    
     public void renameTag( String target, String input ) {
        
        
        //Copy list to iterate on (in case of multiple tags w/ same name)
        List<ImageTag> iterateOn = new ArrayList<ImageTag>(contents);
        for ( ImageTag item : iterateOn ) {
            if ( item.getName().equals(target) ) {
                item.changeName(input);
                Collections.sort(contents, NAME_ORDER);
            } 
        }
    }
    
    public void changeName( String newName ) {
        name = newName;
    }
    
    @Override
    public String toString() {
        return name;        
    }
    
    
}
