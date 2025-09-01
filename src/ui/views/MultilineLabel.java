package ui.views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.Hashtable;

import javax.swing.JPanel;

/**
 * Custom TextWrap label
 */
public class MultilineLabel extends JPanel {
    
    // Iterator to get the lines for rendering
    private LineBreakMeasurer lineMeasurer;
    // Index value to start from
    private int paragraphStart;
    // Index value for text end
    private int paragraphEnd;

    // Text style attribute
    private Hashtable<TextAttribute, Object> map = new Hashtable<TextAttribute, Object>();

    // Text with style map
    private AttributedString value = new AttributedString("Empty", map);
    // Value text helpler variable for updating attributed string
    private String text = "Empty";

    // Allow calling empty constructor
    public MultilineLabel() {}

    // Will create a text layout with default style
    public MultilineLabel(String text) {
        super();
        this.text = text;
        initialize();
    }

    /**
     * Initialize the UI data
     */
    private void initialize() {
        value = new AttributedString(text, map);
        map.put(TextAttribute.FAMILY, "Default");
        map.put(TextAttribute.SIZE, Float.valueOf(18));
    }

    /**
     * Set text to the label, will auto wrap the text based on layout
     * @param text
     */
    public void setText(String text) {
        this.text = text;
        value = new AttributedString(text, map);
    }

    /**
     * Set font style
     * @param name
     * @param size
     * @param weight
     */
    public void setFont(String name, int size, float weight) {
        map.put(TextAttribute.FAMILY, name);
        map.put(TextAttribute.SIZE, size);
        map.put(TextAttribute.WEIGHT, weight);
        value = new AttributedString(text, map);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Avoid the gray background from the TextLayout
        setBackground(Color.white);
        
        Graphics2D g2d = (Graphics2D) g;

        // Update the font render component 
        if (lineMeasurer == null) {
            AttributedCharacterIterator paragraph = value.getIterator();
            paragraphStart = paragraph.getBeginIndex();
            paragraphEnd = paragraph.getEndIndex();
            FontRenderContext frc = g2d.getFontRenderContext();
            lineMeasurer = new LineBreakMeasurer(paragraph, frc);
        }

        // Max size of the text layout
        float breakWidth = (float)getSize().width;
        // Y position for drawing the text
        float drawPosY = 0;

        // Initial position
        lineMeasurer.setPosition(paragraphStart);

        // Loop continues until the paragraphEnd
        while (lineMeasurer.getPosition() < paragraphEnd) {
            // Iterate through lines
            TextLayout layout = lineMeasurer.nextLayout(breakWidth);

            // Fort align the text on right
            float drawPosX = layout.isLeftToRight() ? 0 : breakWidth - layout.getAdvance();

            // Increase the Y position for ascent
            drawPosY += layout.getAscent();

            // Draw the layout in canvas
            layout.draw(g2d, drawPosX, drawPosY);

            // Increase the Y position for next layout
            drawPosY += layout.getDescent() + layout.getLeading();
        }
    }
}