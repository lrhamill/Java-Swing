package AlbumTrees;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author c1031996
 */
public class AlbumSerializer {
    
    // Class contains code for loading and saving albums.
    
    final String savedAlbums = "savedAlbums.ser";
    AlbumContainer returnAC;
    
    public void serializeAlbums (AlbumContainer albums) {
        
        //Save albums to disk.
        
        try {
            
            FileOutputStream fileOut = new FileOutputStream(savedAlbums);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(albums);
            objectOut.close();
            
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    public AlbumContainer deserializeAlbums() {
        
        // Load albums.
        
        try {
            
            FileInputStream fileIn = new FileInputStream(savedAlbums);
            BufferedInputStream buffIn =new BufferedInputStream(fileIn);
            ObjectInputStream objectIn = new ObjectInputStream(buffIn);
            
            Object loadedAlbums = objectIn.readObject();
            objectIn.close();
            
            if (loadedAlbums instanceof AlbumContainer) {
                returnAC = (AlbumContainer) loadedAlbums;
            }
        } catch (Exception ex) {
            
            // In the event that there is no saved album container,
            // create and save a blank one.
            
            returnAC = new AlbumContainer();
            serializeAlbums(returnAC);
        }
        return returnAC;
    }
}
