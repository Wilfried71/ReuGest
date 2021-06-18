package fr.thomas.swing;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 * Event that is displayed on {@link JScheduler}.
 * @author tpeyr
 */
public class JEvent extends JComponent {

    private Color backgroundColor;

    private Date start, end;

    private String header;

    /**
     * Custom object that is stored in JEvent
     */
    private Object event;

    /**
     *
     */
    private static final long serialVersionUID = 7403753831978103955L;

    public JEvent() {
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // ------ Draw background ------
        g.setColor(backgroundColor);
        g.fillRect((int) (getWidth() * 0.05), 0, getWidth(), getHeight());
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, (int) (getWidth() * 0.05), getHeight());

        // ------ Draw separator -------
        g.setColor(Color.BLACK);

        g.drawString(header, (int) (getWidth() * 0.06), g.getFont().getSize());

    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public JEvent(Color backgroundColor, Date start, Date end, String header, Object event) throws Exception {
        super();
        this.backgroundColor = backgroundColor;
        Calendar cStart = Calendar.getInstance();
        cStart.setTime(start);
        Calendar cEnd = Calendar.getInstance();
        cEnd.setTime(end);
        // If not same day
        if (ChronoUnit.DAYS.between(cEnd.toInstant(), cStart.toInstant()) != 0) {
            throw new Exception("La date de début et de fin doivent être le même jour.");
        }
        if (cEnd.before(cStart)) {
            throw new Exception("La date de début ne peut pas être après la date de fin.");
        }
        this.start = start;
        this.end = end;
        this.header = header;
        this.event = event;
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Object getEvent() {
        return event;
    }

    public void setEvent(Object event) {
        this.event = event;
    }    
}
