package scheduler;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MeetingScheduler {
    public List<TimeSlot> findAvailableTimeSlots(Calendar calendar1, Calendar calendar2, int meetingDuration) {
        List<TimeSlot> availableTimeSlots = new ArrayList<>();

        // Pobieram maksymalną godzinę rozpoczęcia i minimalną godzinę zakończenia z harmonogramów kalendarzy
        LocalTime start = TimeUtils.getMaxTime(calendar1.getWorkingHours().getStart(), calendar2.getWorkingHours().getStart());
        LocalTime end = TimeUtils.getMinTime(calendar1.getWorkingHours().getEnd(), calendar2.getWorkingHours().getEnd());

        // Iteruje przez możliwe sloty czasowe, zaczynając od godziny rozpoczęcia do godziny zakończenia
        while (start.plusMinutes(meetingDuration).isBefore(end) || start.plusMinutes(meetingDuration).equals(end)) {
            boolean isAvailable = true;

            // Sprawdzam, czy w kalendarzu 1 są konflikty z innymi spotkaniami
            for (Meeting meeting : calendar1.getPlannedMeetings()) {
                if (isOverlapping(start, start.plusMinutes(meetingDuration), meeting.getStart(), meeting.getEnd())) {
                    // Jeśli występuje nakładanie się spotkań, oznaczam jako niedostępne
                    isAvailable = false;
                    break;
                }
            }

            if (isAvailable) {
                // Jeśli w kalendarzu 1 nie ma konfliktów, sprawdzam również w kalendarzu 2
                for (Meeting meeting : calendar2.getPlannedMeetings()) {
                    if (isOverlapping(start, start.plusMinutes(meetingDuration), meeting.getStart(), meeting.getEnd())) {
                        // Jeśli występuje nakładanie się spotkań, oznaczam jako niedostępne
                        isAvailable = false;
                        break;
                    }
                }
            }

            if (isAvailable) {
                // Jeśli slot jest dostępny, dodaje go do listy dostępnych slotów czasowych
                availableTimeSlots.add(new TimeSlot(start, start.plusMinutes(meetingDuration)));
            }

            // Dodaje kolejna minute do iteracji
            start = start.plusMinutes(1);
        }

        // Po zakończeniu iteracji, łącze dostępne sloty czasowe, które mogą być podzielone
        List<TimeSlot> mergedTimeSlots = mergeTimeSlots(availableTimeSlots);

        // Zwracam połączone sloty czasowe
        return mergedTimeSlots;
    }

    // Metoda sprawdzająca czy dwa przedziały czasowe nachodzą na siebie
    private boolean isOverlapping(LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2) {
        return start1.isBefore(end2) && start2.isBefore(end1);
    }

    // Metoda stworzona w celu scalenia nachodzących na siebie przedziałów czasowych,
    // zeby wystepowaly w czytelnej i przejrzystej formie
    private List<TimeSlot> mergeTimeSlots(List<TimeSlot> timeSlots) {
        List<TimeSlot> mergedTimeSlots = new ArrayList<>();

        if (timeSlots.isEmpty()) {
            return mergedTimeSlots;
        }

        // Sortowanie przedziałów według godziny rozpoczęcia
        Collections.sort(timeSlots, Comparator.comparing(TimeSlot::getStart));

        TimeSlot currentSlot = timeSlots.get(0);

        for (int i = 1; i < timeSlots.size(); i++) {
            TimeSlot nextSlot = timeSlots.get(i);

            if (currentSlot.getEnd().isAfter(nextSlot.getStart())) {
                // Aktualizacja końca przedziału, jeśli istnieje nachodzący przedział
                currentSlot = new TimeSlot(currentSlot.getStart(), nextSlot.getEnd());
            } else {
                // Dodanie aktualnego przedziału do listy scalonych przedziałów i ustawienie nowego aktualnego przedziału
                mergedTimeSlots.add(currentSlot);
                currentSlot = nextSlot;
            }
        }

        // Dodanie ostatniego przedziału do listy scalonych przedziałów
        mergedTimeSlots.add(currentSlot);

        return mergedTimeSlots;
    }

}
