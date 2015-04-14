/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AlbumTrees;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author liamhamill
 */
public class AlbumSerializer {
    
    final String savedAlbums = "savedAlbums.ser";
    AlbumContainer returnAC;
    
    public void serializeAlbums (AlbumContainer albums) {
        
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
        try {
            
            FileInputStream fileIn = new FileInputStream(savedAlbums);
            BufferedInputStream buffIn =new BufferedInputStream(fileIn);
            ObjectInputStream objectIn = new ObjectInputStream(buffIn);
            
            Object loadedAlbums = objectIn.readObject();
            objectIn.close();
            
            if (loadedAlbums instanceof AlbumContainer) {
                returnAC = (AlbumContainer) loadedAlbums;
            }
        } catch (Exception ex) { }
        return returnAC;
    }
}
