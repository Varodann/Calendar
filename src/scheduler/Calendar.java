package scheduler;

import java.util.List;

public class Calendar {
    private WorkingHours workingHours;
    private List<Meeting> plannedMeetings;


    public Calendar(WorkingHours workingHours, List<Meeting> plannedMeetings) {
        this.workingHours = workingHours;
        this.plannedMeetings = plannedMeetings;
    }

    public WorkingHours getWorkingHours() {
        return workingHours;
    }

    public List<Meeting> getPlannedMeetings() {
        return plannedMeetings;
    }
}
