package view;

import static model.PropertyChangeEnabledMutableColor.PROPERTY_BLUE;
import static model.PropertyChangeEnabledMutableColor.PROPERTY_RED;
import static model.PropertyChangeEnabledMutableColor.PROPERTY_GREEN;
import static model.PropertyChangeEnabledMutableColor.PROPERTY_COLOR;


import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JPanel;

/**
 * A demo of Observer design pattern implemented with PropertyChange API.
 * 
 * <p>Arsen Shintemirov added the propertyChange method to serve as
 * the Observer.
 * 
 * @author Charles Bryan
 * @author Arsen Shintemirov
 * @version Winter 2021
 */
public class ColorPanel extends JPanel implements PropertyChangeListener {

    /**  
     * A generated serial version UID for object Serialization. 
     * http://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html
     */
    private static final long serialVersionUID = 8385732728740430466L;
        
    /**
     * Create a color panel with the supplied color.
     * @param theColor the color for the background
     */
    public ColorPanel(final Color theColor) {
        super();
        setBackground(theColor);
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        
        if (PROPERTY_COLOR.equals(theEvent.getPropertyName())) {
            this.setBackground(new Color((Integer) theEvent.getNewValue(), 
                                         (Integer) theEvent.getNewValue(), 
                                         (Integer) theEvent.getNewValue()));
        }
        
        if (PROPERTY_BLUE.equals(theEvent.getPropertyName())) {
            this.setBackground(new Color(0, 0, (Integer) theEvent.getNewValue())); 
            
        }
        
        if (PROPERTY_RED.equals(theEvent.getPropertyName())) {
            this.setBackground(new Color((Integer) theEvent.getNewValue(), 0, 0)); 
            
        }
        
        if (PROPERTY_GREEN.equals(theEvent.getPropertyName())) {
            this.setBackground(new Color(0, (Integer) theEvent.getNewValue(), 0));
            
        }
    }
    
}
    
    
    

