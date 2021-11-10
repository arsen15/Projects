package contoller;

import static model.PropertyChangeEnabledMutableColor.PROPERTY_BLUE;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import model.ColorModel;
import model.PropertyChangeEnabledMutableColor;

/**
 * Represents a Panel with components used to change and display the Blue value for the 
 * backing Color model.
 * 
 * <p>Arsen Shintemirov edited the addListeners() method, adding event 
 * listeners for myValueSlider, myEnableEditButton, myIncreaseButton,
 * myDecreaseButton, and myValueField.
 *
 * @author Charles Bryan
 * @author Arsen Shintemirov
 * @version Winter 2021
 */
public class BlueRowPanel extends JPanel implements PropertyChangeListener {

    /**  
     * A generated serial version UID for object Serialization. 
     * http://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html
     */
    private static final long serialVersionUID = 2284116355218892348L;
    
    /** The size of the increase/decrease buttons. */
    private static final Dimension BUTTON_SIZE = new Dimension(26, 26);
    
    /** The size of the text label. */
    private static final Dimension LABEL_SIZE = new Dimension(45, 26);
    
    /** The number of columns in width of the TextField. */
    private static final int TEXT_FIELD_COLUMNS = 3;
    
    /** The amount of padding for the change panel. */
    private static final int HORIZONTAL_PADDING = 30;
    
    /** The max value that JSlider can have. */
    private static final int MAX_VALUE = 255;
    
    /** The least value that JSlider can have. */
    private static final int LEAST_VALUE = 0;
    
    /** The backing model for the system. */
    private final PropertyChangeEnabledMutableColor myColor;

    /** The CheckBox that enables/disables editing of the TextField. */
    private final JCheckBox myEnableEditButton;
    
    /** The TextField that allows the user to type input for the color value. */
    private final JTextField myValueField;
    
    /** The Button that when clicked increases the color value. */
    private final JButton myIncreaseButton;
    
    /** The Button that when clicked decreases the color value. */
    private final JButton myDecreaseButton;
    
    /** The Slider that when adjusted, changes the color value. */
    private final JSlider myValueSlider;
    
    /** The panel that visually displays ONLY the BLUE value for the color. */
    private final JPanel myColorDisplayPanel;
    
    /**
     * Creates a Panel with components used to change and display the Blue value for the 
     * backing Color model. 
     * @param theColor the backing model for the system
     */
    public BlueRowPanel(final PropertyChangeEnabledMutableColor theColor) {
        super();
        myColor = theColor;
        myEnableEditButton = new JCheckBox("Enable edit");
        myValueField = new JTextField();
        myIncreaseButton = new JButton();
        myDecreaseButton = new JButton();
        myValueSlider = new JSlider();
        myColorDisplayPanel = new JPanel();
        layoutComponents();
        addListeners();
    }
    
    /**
     * Setup and add the GUI components for this panel. 
     */
    private void layoutComponents() {
        myColorDisplayPanel.setPreferredSize(BUTTON_SIZE);
        myColorDisplayPanel.setBackground(new Color(0, 0, myColor.getBlue()));
        final JLabel rowLabel = new JLabel("Blue: ");
        rowLabel.setPreferredSize(LABEL_SIZE);
        myValueField.setText(String.valueOf(myColor.getBlue()));
        myValueField.setEditable(false);
        myValueField.setColumns(TEXT_FIELD_COLUMNS);
        myValueField.setHorizontalAlignment(JTextField.RIGHT);
        
        final JPanel rightPanel = new JPanel();
        rightPanel.setBorder(BorderFactory.createEmptyBorder(0, 
                                                             HORIZONTAL_PADDING, 
                                                             0, 
                                                             HORIZONTAL_PADDING));
        rightPanel.setBackground(rightPanel.getBackground().darker());
        myIncreaseButton.setIcon(new ImageIcon("./images/ic_increase_value.png"));
        myIncreaseButton.setPreferredSize(BUTTON_SIZE);
        myValueSlider.setMaximum(ColorModel.MAX_VALUE);
        myValueSlider.setMinimum(ColorModel.MIN_VALUE);
        myValueSlider.setValue(myColor.getBlue());
        myValueSlider.setBackground(rightPanel.getBackground());
        myDecreaseButton.setIcon(new ImageIcon("./images/ic_decrease_value.png"));
        myDecreaseButton.setPreferredSize(BUTTON_SIZE);
        rightPanel.add(myDecreaseButton);
        rightPanel.add(myValueSlider);
        rightPanel.add(myIncreaseButton);
        
        add(myColorDisplayPanel);
        add(rowLabel);
        add(myValueField);
        add(myEnableEditButton);
        add(rightPanel);
    }
    
    /**
     * Add listeners (event handlers) to any GUI components that require handling.  
     */
    private void addListeners() {
        //DO not remove the following statement.
        myColor.addPropertyChangeListener(this);
        
      //The check box listener.
        myEnableEditButton.addItemListener(new ItemListener() {
           
            @Override
           public void itemStateChanged(final ItemEvent theEvent) {
                if (theEvent.getStateChange() == ItemEvent.SELECTED) {
                    myValueField.setEditable(true);
                } else {
                    myValueField.setEditable(false);
                }
            }
        });
        
        //The slider listeners.
        myValueSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent theEvent) {
                
                final JSlider source = (JSlider) theEvent.getSource();
                myColorDisplayPanel.setBackground(new Color(0, 0, source.getValue()));
                
                final int blueInt = myValueSlider.getValue();
                myColor.setBlue(blueInt);                 
                myValueField.setText("" + blueInt);                
                if (blueInt >= MAX_VALUE) {
                    myIncreaseButton.setEnabled(false);
                } else if (blueInt <= LEAST_VALUE) {
                    myDecreaseButton.setEnabled(false);
                } else {
                    myIncreaseButton.setEnabled(true);
                    myDecreaseButton.setEnabled(true);
                }       
            }
        });
        
        textFieldListener(myValueSlider, myValueField);
        
        /**
         * The listener for the increase button.
         */
        myIncreaseButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                final int increase = myValueSlider.getValue() + 1;
                myValueSlider.setValue(increase);
            }
        });
        
        /**
         * The listener for the decrease button.
         */
        myDecreaseButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                final int decrease = myValueSlider.getValue() - 1;
                
                myValueSlider.setValue(decrease);
            }
        });
        
    }
    
    /**
     * Method that creates the listener for the color textField.
     * 
     * @param theSlider is the JSlider.
     * @param theTextField is the textField.
     */
    private void textFieldListener(final JSlider theSlider, final JTextField theTextField) {
        myValueField.addActionListener(theEvent -> myValueField.transferFocus());
        myValueField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent theEvent) {
                updateItem(theSlider, theTextField);
            }
        });
        
        myValueSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent theEvent) {
                myValueField.setText(String.valueOf(myValueSlider.getValue()));
            }
        });
    
    }
    
    /**
     * Method that updates the slider when textField is changed.
     * 
     * @param theSlider is the JSlider.
     * @param theQuantity is the textField.
     */
    private void updateItem(final JSlider theSlider, final JTextField theQuantity) {
        final String text = theQuantity.getText();
        int number;
        try {
            number = Integer.parseInt(text);
            if (number < 0 || number > MAX_VALUE) {
                                
                theQuantity.setText("");
            }
        } catch (final NumberFormatException e) {
            number = 0;
            theQuantity.setText("");
        }
        myValueSlider.setValue(number);
        
    }
    
   
    

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if (PROPERTY_BLUE.equals(theEvent.getPropertyName())) {
            myValueField.setText(theEvent.getNewValue().toString());
            myValueSlider.setValue((Integer) theEvent.getNewValue());
            myColorDisplayPanel.
                setBackground(new Color(0, 0, (Integer) theEvent.getNewValue()));
        }
    }
    
}
