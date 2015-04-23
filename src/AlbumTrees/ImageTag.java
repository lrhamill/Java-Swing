/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AlbumTrees;

import java.io.File;
import java.io.Serializable;

/**
 *
 * @author c1031996
 */
public class ImageTag implements Serializable {
    
    String albumTag;
    String filePath;
    String fileName;
    String tagName;
    
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
