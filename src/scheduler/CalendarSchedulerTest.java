package scheduler;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.time.LocalTime;
import java.util.List;

public class CalendarSchedulerTest {

    // Poprawne dane majace zaprezentowac poprawnosc dzialania algorytmu
    @Test
    public void testFindAvailableMeetingTimes() {
        // Tworzenie kalendarzy dla dwóch osób
        Calendar calendar1 = new Calendar(
                new WorkingHours(LocalTime.of(10, 0), LocalTime.of(14, 0)),
                List.of(
                        new Meeting(LocalTime.of(11, 50), LocalTime.of(12, 0))
                )
        );

        Calendar calendar2 = new Calendar(
                new WorkingHours(LocalTime.of(11, 0), LocalTime.of(16, 0)),
                List.of(
                        new Meeting(LocalTime.of(12, 0), LocalTime.of(12, 30)),
                        new Meeting(LocalTime.of(13, 30), LocalTime.of(14, 0))
                )
        );

        // Długość oczekiwanego spotkania
        int meetingDuration = 30;

        // Wywołanie metody znajdującej dostępne terminy spotkań
        MeetingScheduler scheduler = new MeetingScheduler();
        List<TimeSlot> availableTimes = scheduler.findAvailableTimeSlots(calendar1, calendar2, meetingDuration);

        // Oczekiwana liczba dostępnych terminów
        int expectedCount = 2;

//        W przypadku checi wypisania zawartosci listy z dostepnymi terminami odkomentowac
//        for (TimeSlot timeSlot : availableTimes) {
//            System.out.println(timeSlot.getStart() + " - " + timeSlot.getEnd());
//        }

        // Sprawdzenie, czy liczba dostępnych terminów jest zgodna z oczekiwaną
        Assertions.assertEquals(expectedCount, availableTimes.size());

        // Sprawdzenie, czy znalezione terminy są poprawne
        TimeSlot expectedTimeRange1 = new TimeSlot(LocalTime.of(11, 0), LocalTime.of(11, 50));
        TimeSlot expectedTimeRange2 = new TimeSlot(LocalTime.of(12, 30), LocalTime.of(13, 30));
        Assertions.assertTrue(availableTimes.contains(expectedTimeRange1));
        Assertions.assertTrue(availableTimes.contains(expectedTimeRange2));
    }

    // Dane, ktore byly uzyte w przykładzie znajdujacym sie w mailu
    @Test
    public void testFindAvailableMeetingTimesFromMail() {
        // Tworzenie kalendarzy dla dwóch osób
        Calendar calendarFirst = new Calendar(
                new WorkingHours(LocalTime.of(9, 0), LocalTime.of(19, 55)),
                List.of(
                        new Meeting(LocalTime.of(9, 0), LocalTime.of(10, 30)),
                        new Meeting(LocalTime.of(12, 0), LocalTime.of(13, 0)),
                        new Meeting(LocalTime.of(16, 0), LocalTime.of(18, 0))
                )
        );

        Calendar calendarSecond = new Calendar(
                new WorkingHours(LocalTime.of(10, 0), LocalTime.of(18, 30)),
                List.of(
                        new Meeting(LocalTime.of(10, 0), LocalTime.of(11, 30)),
                        new Meeting(LocalTime.of(12, 30), LocalTime.of(14, 30)),
                        new Meeting(LocalTime.of(14, 30), LocalTime.of(15, 0)),
                        new Meeting(LocalTime.of(16, 0), LocalTime.of(17, 0))
                )
        );

        // Długość oczekiwanego spotkania
        int meetingDuration = 30;

        // Wywołanie metody znajdującej dostępne terminy spotkań
        MeetingScheduler scheduler = new MeetingScheduler();
        List<TimeSlot> availableTimes = scheduler.findAvailableTimeSlots(calendarFirst, calendarSecond, meetingDuration);

        // Oczekiwana liczba dostępnych terminów
        int expectedCount = 3;

//        W przypadku checi wypisania zawartosci listy z dostepnymi terminami odkomentowac
//        for (TimeSlot timeSlot : availableTimes) {
//            System.out.println(timeSlot.getStart() + " - " + timeSlot.getEnd());
//        }

        // Sprawdzenie, czy liczba dostępnych terminów jest zgodna z oczekiwaną
        Assertions.assertEquals(expectedCount, availableTimes.size());

        // Sprawdzenie, czy znalezione terminy są poprawne
        TimeSlot expectedTimeRange1 = new TimeSlot(LocalTime.of(11, 30), LocalTime.of(12, 0));
        TimeSlot expectedTimeRange2 = new TimeSlot(LocalTime.of(15, 0), LocalTime.of(16, 0));
        TimeSlot expectedTimeRange3 = new TimeSlot(LocalTime.of(18, 0), LocalTime.of(18, 30));
        Assertions.assertTrue(availableTimes.contains(expectedTimeRange1));
        Assertions.assertTrue(availableTimes.contains(expectedTimeRange2));
        Assertions.assertTrue(availableTimes.contains(expectedTimeRange3));
    }

    // Przypadek, kiedy dlugosc spotkania jest wieksza niz dostepny czas
    @Test
    public void testMeetingDurationGreaterThanAvailableTime() {
        Calendar calendar1 = new Calendar(
                new WorkingHours(LocalTime.of(9, 0), LocalTime.of(12, 0)),
                List.of(
                        new Meeting(LocalTime.of(10, 0), LocalTime.of(11, 0))
                )
        );

        Calendar calendar2 = new Calendar(
                new WorkingHours(LocalTime.of(13, 0), LocalTime.of(16, 0)),
                List.of(
                        new Meeting(LocalTime.of(14, 0), LocalTime.of(15, 0))
                )
        );

        int meetingDuration = 120; // Spotkanie trwa 2 godziny

        MeetingScheduler scheduler = new MeetingScheduler();
        List<TimeSlot> availableTimes = scheduler.findAvailableTimeSlots(calendar1, calendar2, meetingDuration);

        int expectedCount = 0;

//        W przypadku checi wypisania zawartosci listy z dostepnymi terminami odkomentowac
//        for (TimeSlot timeSlot : availableTimes) {
//            System.out.println(timeSlot.getStart() + " - " + timeSlot.getEnd());
//        }

        Assertions.assertEquals(expectedCount, availableTimes.size()); // Oczekiwana liczba dostępnych terminów to 0
    }

    // Przypadek kiedy czasy pracy nakladaja sie na któryms z krancow czasu pracy
    @Test
    public void testMeetingsAtWorkingHourBoundaries() {
        Calendar calendar1 = new Calendar(
                new WorkingHours(LocalTime.of(9, 0), LocalTime.of(12, 0)),
                List.of(
                        new Meeting(LocalTime.of(9, 0), LocalTime.of(10, 0)),
                        new Meeting(LocalTime.of(11, 0), LocalTime.of(12, 0))
                )
        );

        Calendar calendar2 = new Calendar(
                new WorkingHours(LocalTime.of(12, 0), LocalTime.of(16, 0)),
                List.of(
                        new Meeting(LocalTime.of(13, 0), LocalTime.of(14, 0)),
                        new Meeting(LocalTime.of(15, 0), LocalTime.of(16, 0))
                )
        );

        int meetingDuration = 60; // Spotkanie trwa 1 godzinę

        MeetingScheduler scheduler = new MeetingScheduler();
        List<TimeSlot> availableTimes = scheduler.findAvailableTimeSlots(calendar1, calendar2, meetingDuration);

        int expectedCount = 0;

//        W przypadku checi wypisania zawartosci listy z dostepnymi terminami odkomentowac
//        for (TimeSlot timeSlot : availableTimes) {
//            System.out.println(timeSlot.getStart() + " - " + timeSlot.getEnd());
//        }

        Assertions.assertEquals(expectedCount, availableTimes.size());
    }

    //Test na duza czestotliwosc spotkan
    @Test
    public void testFindAvailableMeetingTimesLargeNumberOfMeetings() {
        // Tworzenie kalendarzy dla dwóch osób
        Calendar calendar1 = new Calendar(
                new WorkingHours(LocalTime.of(9, 0), LocalTime.of(18, 0)),
                List.of(
                        new Meeting(LocalTime.of(10, 0), LocalTime.of(10, 30)),
                        new Meeting(LocalTime.of(12, 0), LocalTime.of(12, 30)),
                        new Meeting(LocalTime.of(14, 0), LocalTime.of(14, 30)),
                        new Meeting(LocalTime.of(16, 0), LocalTime.of(16, 30))
                )
        );

        Calendar calendar2 = new Calendar(
                new WorkingHours(LocalTime.of(9, 0), LocalTime.of(18, 0)),
                List.of(
                        new Meeting(LocalTime.of(10, 30), LocalTime.of(11, 0)),
                        new Meeting(LocalTime.of(12, 30), LocalTime.of(13, 0)),
                        new Meeting(LocalTime.of(14, 30), LocalTime.of(15, 0)),
                        new Meeting(LocalTime.of(16, 30), LocalTime.of(17, 0))
                )
        );

        // Długość oczekiwanego spotkania
        int meetingDuration = 30;

        // Wywołanie metody znajdującej dostępne terminy spotkań
        MeetingScheduler scheduler = new MeetingScheduler();
        List<TimeSlot> availableTimes = scheduler.findAvailableTimeSlots(calendar1, calendar2, meetingDuration);

        // Oczekiwana liczba dostępnych terminów
        int expectedCount = 5;

//        W przypadku checi wypisania zawartosci listy z dostepnymi terminami odkomentowac
//        for (TimeSlot timeSlot : availableTimes) {
//            System.out.println(timeSlot.getStart() + " - " + timeSlot.getEnd());
//        }

        // Sprawdzenie, czy liczba dostępnych terminów jest zgodna z oczekiwaną
        Assertions.assertEquals(expectedCount, availableTimes.size());

        // Sprawdzenie, czy znalezione terminy są poprawne
        TimeSlot expectedTimeRange1 = new TimeSlot(LocalTime.of(9, 0), LocalTime.of(10, 0));
        TimeSlot expectedTimeRange2 = new TimeSlot(LocalTime.of(11, 0), LocalTime.of(12, 0));
        TimeSlot expectedTimeRange3 = new TimeSlot(LocalTime.of(13, 0), LocalTime.of(14, 0));
        TimeSlot expectedTimeRange4 = new TimeSlot(LocalTime.of(15, 0), LocalTime.of(16, 0));
        TimeSlot expectedTimeRange5 = new TimeSlot(LocalTime.of(17, 0), LocalTime.of(18, 0));
        Assertions.assertTrue(availableTimes.contains(expectedTimeRange1));
        Assertions.assertTrue(availableTimes.contains(expectedTimeRange2));
        Assertions.assertTrue(availableTimes.contains(expectedTimeRange3));
        Assertions.assertTrue(availableTimes.contains(expectedTimeRange4));
        Assertions.assertTrue(availableTimes.contains(expectedTimeRange5));
    }
}
