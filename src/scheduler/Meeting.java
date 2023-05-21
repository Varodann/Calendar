package scheduler;

import java.time.LocalTime;

public class Meeting {
    private LocalTime start;
    private LocalTime end;

    public Meeting(LocalTime start, LocalTime end) {
        this.start = start;
        this.end = end;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }
}
