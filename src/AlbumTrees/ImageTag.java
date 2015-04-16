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
 * @author liamhamill
 */
public class ImageTag implements Serializable {
    
    String albumTag;
    String filePath;
    String fileName;
    
    public ImageTag(String album, File location) {
        
        albumTag = album;
        filePath = location.getPath();
        fileName = location.getName();
        
    }
    
    public String getFilePath() {
        return filePath;
    }
    
    public String getName() {
        return fileName;
    }
    
    public String getAlbum() {
        return albumTag;
    }
    
    public String toString() {
        return fileName + ":" + filePath;
    }
}
