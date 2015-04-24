/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author c1031996
 */
public class ImageFileFilter extends FileFilter {
    
    // Array that stores ImageIO file types
    String[] validExtensions = ImageIO.getReaderFileSuffixes();        
    
    @Override
    public boolean accept(File f) {
        
        // Code to identify valid file types
        
        // Directories are fine
        if ( f.isDirectory() ) {
            return true;
        }
        
        // Extract filename and compare to array of valid file types
        
        String filename = f.getName();
        String extension = filename.substring(filename.lastIndexOf(".")+1);
        extension = extension.toLowerCase();
        
        for ( String item : validExtensions ) {
            if ( extension.equals(item) ) {
                return true;
            }
        }
        
        return false;
        
        
    }
    
    @Override
    public String getDescription() {
        return "Image Files";
    }
        
    
}
