/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package swing.book;
import javax.swing.*;

/**
 *
 * @author c1031996
 */
public class BasicWindow extends JFrame {
    
    ImageIcon tick = new ImageIcon( "tick.png" );
    ImageIcon cross= new ImageIcon( "cross.png" );
    
    
    JPanel pnl = new JPanel();
    JButton click = new JButton( "Click me" );
    JButton tckBtn = new JButton( tick );
    JButton crossBtn = new JButton( "STOP", cross );
    
    public BasicWindow() {
        super( "Swing Window" );
        setSize( 500, 200 );
        setDefaultCloseOperation( EXIT_ON_CLOSE );
        
        pnl.add( click );
        pnl.add( tckBtn );
        pnl.add( crossBtn );
        add( pnl );
        setVisible( true );
    }
    
    
}
