package fr.thomas.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JComponent;

/**
 *
 * @author tpeyr
 *
 */
public class JScheduler extends JComponent {

    /**
     *
     */
    private static final long serialVersionUID = 5374327960507124694L;

    // Liste d'evenements a afficher
    private List<JEvent> events;

    // Couleur du header
    private Color headerColor;

    private Calendar calendar;

    //////////////////////////////////// CONSTRUCTORS AND GET/SET
    //////////////////////////////////// //////////////////////////////////////
    public JScheduler(List<JEvent> events, Color headerColor) {
        super();
        this.events = events;
        this.headerColor = headerColor;
        this.calendar = Calendar.getInstance();
    }

    public List<JEvent> getEvents() {
        return events;
    }

    public void setEvents(List<JEvent> events) {
        this.events = events;
        this.refresh();
    }

    public Color getHeaderColor() {
        return headerColor;
    }

    public void setHeaderColor(Color headerColor) {
        this.headerColor = headerColor;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
        paintComponent(getGraphics());
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Reset all events on the screen
        resetEvents();

        // Reset graphics
        g.setColor(new Color(230, 230, 230));
        g.fillRect(0, 0, getWidth(), getHeight());

        // Draw border
        drawBorder(g);
        // Draw Header
        drawHeader(g);
        // Draw columns
        drawColumns(g);
        // Draw lines
        drawLines(g);
        // Draw current hour line
        drawHour(g);

        List<Calendar> days = getDays(calendar);
        try {
            drawDays(days, g);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Draw events
        drawEvents(g);
    }

    /**
     * Draw the hour of today as a red horizontal line
     */
    private void drawHour(Graphics g) {
        // Get y by hour
        Calendar huitHeures = Calendar.getInstance();
        huitHeures.set(Calendar.HOUR_OF_DAY, 8);
        huitHeures.set(Calendar.MINUTE, 00);
        Calendar today = Calendar.getInstance();
        int y = this.getOrdByHour(huitHeures, today) + p(getHeight(), 7) + 1;
        g.setColor(Color.RED);
        g.drawLine(0, y, getWidth(), y);
    }

    /**
     * Retourne l'heure au format HH:mm
     *
     * @param c
     * @return
     */
    private String getHour(Calendar c) {
        String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
        hour = (hour.length() == 1 ? "0" + hour : hour);
        String min = String.valueOf(c.get(Calendar.MINUTE));
        min = (min.length() == 1 ? "0" + min : min);
        return hour + ":" + min;
    }

    /**
     * Dessine la bordure
     *
     * @param g
     */
    private void drawBorder(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
    }

    /**
     * Dessine l'entete
     *
     * @param g
     */
    private void drawHeader(Graphics g) {
        g.setColor(headerColor);
        g.fillRect(1, 1, getWidth() - 2, p(getHeight(), 7));
        g.setColor(Color.BLACK);
        g.drawLine(0, p(getHeight(), 7), getWidth(), p(getHeight(), 7));
    }

    /**
     * Dessine les colonnes
     *
     * @param g
     */
    private void drawColumns(Graphics g) {
        g.setColor(Color.BLACK);
        for (int i = 0; i < 7; i++) {
            int x = p(getWidth(), 9) + p(getWidth(), 13F * i);
            g.drawLine(x, 0, x, getHeight());
        }
    }

    /**
     * Dessine les lignes
     *
     * @param g
     */
    private void drawLines(Graphics g) {

        // On instancie un calendar pour l'affichage des heures
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 8);
        c.set(Calendar.MINUTE, 00);

        g.setColor(Color.BLACK);
        for (int i = 0; i <= 10; i++) {
            // On recupere l'ordonnee actuelle
            int y = p(getHeight(), 7) + p(getHeight(), 8.5F * i);
            // On dessine la ligne
            g.drawLine(0, y, getWidth(), y);
            // On ecrit l'heure
            g.drawString(getHour(c), 3, y + getFont().getSize());
            // On incremente l'heure
            c.add(Calendar.HOUR_OF_DAY, 1);
        }

        /**
         * On dessine les lignes des demi-heures
         */
        for (int i = 0; i <= 10; i++) {
            // On recupere l'ordonnee actuelle
            int y = p(getHeight(), 11.25F) + p(getHeight(), 8.5F * i);
            int x = p(getWidth(), 9);
            // On dessine la ligne
            g.drawLine(x, y, getWidth(), y);
        }
    }

    /**
     * Renvoie le pourcentage d'une valeur
     *
     * @param value
     * @param percentage
     */
    private int p(int value, float percentage) {
        return (int) (value * (percentage / 100));
    }

    /**
     * Get the list of days of the current week
     *
     * @param c
     * @return
     */
    private List<Calendar> getDays(Calendar c) {
        Calendar lundi, mardi, mercredi, jeudi, vendredi, samedi, dimanche;
        lundi = Calendar.getInstance();
        mardi = Calendar.getInstance();
        mercredi = Calendar.getInstance();
        jeudi = Calendar.getInstance();
        vendredi = Calendar.getInstance();
        samedi = Calendar.getInstance();
        dimanche = Calendar.getInstance();

        lundi.setTime(c.getTime());
        mardi.setTime(c.getTime());
        mercredi.setTime(c.getTime());
        jeudi.setTime(c.getTime());
        vendredi.setTime(c.getTime());
        samedi.setTime(c.getTime());
        dimanche.setTime(c.getTime());

        lundi.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        mardi.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        mercredi.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        jeudi.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        vendredi.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        samedi.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        dimanche.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

        return Arrays.asList(lundi, mardi, mercredi, jeudi, vendredi, samedi, dimanche);
    }

    /**
     * Draw the list of days in the header cells
     *
     * @param days
     * @param g
     * @throws ParseException
     */
    private void drawDays(List<Calendar> days, Graphics g) throws ParseException {
        // Affiche lundi
        g.setColor(Color.BLACK);

        for (int i = 0; i < days.size(); i++) {
            g.drawString(formatDate(days.get(i).getTime()), p(getWidth(), 9.4f + 13f * i),
                    (int) (p(getHeight(), 3.5F) + (getFont().getSize() * 0.5)));
        }
    }

    private String formatDate(Date d) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return simpleDateFormat.format(d);
    }

    /**
     * Add a week to current calendar
     *
     * @return
     */
    public Calendar nextWeek() {
        this.calendar.add(Calendar.DAY_OF_YEAR, 7);
        paintComponent(getGraphics());
        return calendar;
    }

    /**
     * Substract a week to current calendar
     *
     * @return
     */
    public Calendar previousWeek() {
        this.calendar.add(Calendar.DAY_OF_YEAR, -7);
        paintComponent(getGraphics());
        return calendar;
    }

    private void drawEvents(Graphics g) {

        Calendar weekStart = Calendar.getInstance();
        Calendar weekEnd = Calendar.getInstance();
        // Set to actual calendar
        weekEnd.setTime(calendar.getTime());
        weekStart.setTime(calendar.getTime());
        // Set to monday
        weekStart.set(Calendar.DAY_OF_WEEK, 2);
        weekStart.set(Calendar.HOUR_OF_DAY, 0);
        weekStart.set(Calendar.MINUTE, 1);

        // Set weekend
        weekEnd.set(Calendar.DAY_OF_WEEK, 2);
        weekEnd.add(Calendar.DAY_OF_WEEK, 6);
        weekEnd.set(Calendar.HOUR, 23);
        weekEnd.set(Calendar.MINUTE, 59);
        weekEnd.set(Calendar.SECOND, 59);

        /**
         * For each event provided
         */
        for (JEvent evt : events) {
            /**
             * If the event is contained in the current week
             */
            if (isOverlaped(weekStart.getTime(), weekEnd.getTime(), evt.getStart(), evt.getEnd())) {
                // Set calendar values
                Calendar cStart = Calendar.getInstance();
                cStart.setTime(evt.getStart());
                Calendar cEnd = Calendar.getInstance();
                cEnd.setTime(evt.getEnd());
                // Get x by day
                int x = p(getWidth(), 9) + p(getWidth(), 13F * getColumnFromDay(cStart));

                // Get y by hour
                Calendar huitHeures = Calendar.getInstance();
                huitHeures.setTime(evt.getStart());
                huitHeures.set(Calendar.HOUR_OF_DAY, 8);
                huitHeures.set(Calendar.MINUTE, 00);

                // Calcul de y
                int yMin;
                /**
                 * Pour l'affichage : si le creneau commence avant 8h00
                 */
                if (cStart.before(huitHeures)) {
                    yMin = p(getHeight(), 7);
                    cStart.setTime(huitHeures.getTime());
                } else {
                    yMin = getOrdByHour(huitHeures, cStart) + p(getHeight(), 7);
                }

                int yMax = getOrdByHour(cStart, cEnd);
                // Add component to the layout
                this.add(evt);
                evt.setBounds(x + 1, yMin + 1, p(getWidth(), 13F) - 1, yMax + 1);
            }
        }
        this.paintComponents(g);
    }

    /**
     *
     * @param c
     * @return
     */
    private int getColumnFromDay(Calendar c) {
        switch (c.get(Calendar.DAY_OF_WEEK)) {
            // Dimanche
            case Calendar.SUNDAY:
                return 6;
            // Lundi
            case Calendar.MONDAY:
                return 0;
            // Mardi
            case Calendar.TUESDAY:
                return 1;
            // Mercredi
            case Calendar.WEDNESDAY:
                return 2;
            // Jeudi
            case Calendar.THURSDAY:
                return 3;
            // Vendredi
            case Calendar.FRIDAY:
                return 4;
            // Samedi
            case Calendar.SATURDAY:
                return 5;
            default:
                return 0;
        }
    }

    /**
     * Remove all events on the screen
     */
    private void resetEvents() {
        /**
         * For each event provided
         */
        for (JEvent evt : events) {
            remove(evt);
        }
    }

    /**
     * Renvoie l'ordonee y a partir de l'heure de l'event
     *
     * @param c
     * @return
     */
    private int getOrdByHour(Calendar cStart, Calendar cEnd) {
        // Nombre de minutes affichees dans le calendrier (11*60)
        int MIN = 660;

        float diff = ChronoUnit.MINUTES.between(cStart.toInstant(), cEnd.toInstant());
        // On calcule le pourcentage en tenant compte du header
        float p = ((float) (diff / MIN) * 100);
        return (int) ((float) ((p / 100)) * p(getHeight(), 93));
    }

    /**
     * Determine si les evenements se chevauchent
     *
     * @param startFirst
     * @param endFirst
     * @param startSecond
     * @param endSecond
     * @return
     */
    private Boolean isOverlaped(Date startFirst, Date endFirst, Date startSecond, Date endSecond) {
        return (startFirst.before(endSecond) && endFirst.after(startSecond));
    }

    /**
     * Re-paint the scheduler
     */
    public void refresh() {
        this.paintComponent(getGraphics());
    }
    
    public Calendar getStartOfWeek() {
        Calendar monday = this.getDays(calendar).get(0);
        monday.set(Calendar.HOUR_OF_DAY,0);
        monday.set(Calendar.MINUTE, 0);
        monday.set(Calendar.SECOND, 0);
        return monday;
    }
    
    public Calendar getEndOfWeek() {
        Calendar sunday = this.getDays(calendar).get(6);
        sunday.set(Calendar.HOUR_OF_DAY,23);
        sunday.set(Calendar.MINUTE, 59);
        sunday.set(Calendar.SECOND, 59);
        return sunday;
    }
}
