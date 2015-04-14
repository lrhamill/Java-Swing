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
public class PhotoAlbum implements Serializable {
    
    ArrayList<ImageTag> contents = new ArrayList();
    String name;
    
    // Comparator allows us to sort contents by name
    static final Comparator<ImageTag> NAME_ORDER = new Comparator<ImageTag>() {
        public int compare(ImageTag i1, ImageTag i2) {
            return i1.getName().compareTo(i2.getName());
        }
    };
    
    public PhotoAlbum(String nameInput) {
        name = nameInput;
    }
    
    public void addToContents(ImageTag newItem) {
        contents.add(newItem);
        Collections.sort(contents, NAME_ORDER);
    }
    
    public ArrayList<ImageTag> getContents() {
        return contents;
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        String contentsAsString = "";
        contentsAsString += name;
        contentsAsString += "\n"; 
        contentsAsString += "\n"; 
        for ( ImageTag item : contents ) {
            contentsAsString += item.fileName;
            contentsAsString += "\n";             
        } return contentsAsString;
        
    }
    
    
}
