package scheduler;

import java.time.LocalTime;

public class TimeUtils {
    // Metoda statyczna zwracająca maksymalny czas spośród dwóch podanych czasów
    public static LocalTime getMaxTime(LocalTime time1, LocalTime time2) {
        LocalTime maxTime = null;
        if (time1.compareTo(time2) > 0) {
            maxTime = time1;
        } else {
            maxTime = time2;
        }
        return maxTime;
    }

    // Metoda statyczna zwracająca minimalny czas spośród dwóch podanych czasów
    public static LocalTime getMinTime(LocalTime time1, LocalTime time2) {
        LocalTime minTime = null;
        if (time1.compareTo(time2) < 0) {
            minTime = time1;
        } else {
            minTime = time2;
        }
        return minTime;
    }
}
