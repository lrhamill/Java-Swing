/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AlbumTrees;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author liamhamill
 */
public class AlbumContainer implements Serializable {
    
    ArrayList<PhotoAlbum> contents = new ArrayList();
    
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
    
    public ArrayList<PhotoAlbum> getContents() {
        return contents;
    }
    
}
