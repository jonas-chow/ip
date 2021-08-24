package duke.task;

import java.time.LocalDate;

public class Event extends Task {
    private LocalDate date;

    public Event(String name, LocalDate date) {
        super(name);
        this.date = date;
    }

    /**
     * Returns whether the event is happening today.
     *
     * @return true if the date is today. False otherwise.
     */
    @Override
    public boolean isToday() {
        return date.equals(LocalDate.now());
    }

    /**
     * Loads an event from data parsed from the save file
     *
     * @param loadDatas A line from the csv, split by commas
     * @return Event created from provided data
     */
    public static Event load(String[] loadDatas) {
        boolean isDone = loadDatas[1].equals("o");
        String name = loadDatas[2];
        LocalDate time = LocalDate.parse(loadDatas[3]);

        Event event = new Event(name, time);
        if (isDone) {
            event.doTask();
        }

        return event;
    }

    /**
     * Returns whether the event has passed.
     *
     * @return true if the date is before today. False otherwise.
     */
    @Override
    public boolean isExpired() {
        return date.isBefore(LocalDate.now());
    }

    /**
     * Returns a string representation of the event and its data
     *
     * @return string representation of the event
     */
    @Override
    public String toString() {
        return "[E] " + super.toString() + " (at: " + date + ")";
    }

    /**
     * Returns a string representing the event compliant to the saveFile format
     *
     * @return String to be saved as a line in save.csv
     */
    @Override
    public String getSaveString() {
        return "e," + super.getSaveString() + "," + date;
    }
}
