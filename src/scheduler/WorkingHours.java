package scheduler;

import java.time.LocalTime;

public class WorkingHours {
    private LocalTime start;
    private LocalTime end;

    public WorkingHours(LocalTime start, LocalTime end) {
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
