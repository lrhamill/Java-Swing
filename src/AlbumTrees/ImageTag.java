
package AlbumTrees;

import java.io.File;
import java.io.Serializable;

/**
 *
 * @author c1031996
 */
public class ImageTag implements Serializable {
    
    // Image tag class. Contains a filename, path, and user-visible name
    
    String albumTag; // Note: This is only to place a tag in the right album at creation
    String filePath;
    String fileName;
    String tagName;
    
    // Class is fairly self-explanatory.
    
    public ImageTag(String album, File location) {
        
        albumTag = album;
        filePath = location.getPath();
        fileName = location.getName();
        tagName = fileName;
        
    }
    
    public String getFilePath() {
        return filePath;
    }
    
    public String getName() {
        return tagName;
    }
    
    public String getAlbum() {
        return albumTag;
    }
    
    public void changeName( String newName ) {
        tagName = newName;
    }
    
    public String toString() {
        return tagName;
    }
}
