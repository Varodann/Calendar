# Scheduler

Scheduler to prosta aplikacja do zarządzania spotkaniami i wyszukiwania dostępnych slotów czasowych w kalendarzach. Zrealizowana na podstawie wytycznych zadania.

## Opis

Aplikacja umożliwia znajdowanie dostępnych slotów czasowych dla spotkań na podstawie dwóch kalendarzy. Wykorzystuje metodę `findAvailableTimeSlots()` w klasie `Scheduler`, która porównuje harmonogramy kalendarzy oraz planowane spotkania, aby znaleźć dostępne sloty czasowe, które nie kolidują z żadnymi istniejącymi spotkaniami. Spotkania mają ustaloną minimalną długość czasu.

## Instalacja

1. Sklonuj repozytorium na swój lokalny komputer.
2. Otwórz projekt w wybranym środowisku programistycznym.
3. Zbuduj projekt i uruchom testy, w których znajdują sie przykłady.

## Użycie

1. Zainicjalizuj obiekty kalendarzy, określając godziny pracy i zaplanowane spotkania.
2. Wywołaj metodę `findAvailableTimeSlots()` na obiekcie `Scheduler`, przekazując oba kalendarze oraz żądaną długość spotkania.
3. Otrzymasz listę dostępnych slotów czasowych, które nie kolidują z zaplanowanymi spotkaniami w obu kalendarzach.

## Przykład użycia

```java
// Inicjalizacja kalendarzy i spotkań
Calendar calendar1 = new Calendar(...);
Calendar calendar2 = new Calendar(...);

// Utworzenie obiektu Scheduler
Scheduler scheduler = new Scheduler();

// Znalezienie dostępnych slotów czasowych
List<TimeSlot> availableTimeSlots = scheduler.findAvailableTimeSlots(calendar1, calendar2, 60);

// Wyświetlenie wyników
System.out.println("Dostępne sloty czasowe:");
for (TimeSlot timeSlot : availableTimeSlots) {
    System.out.println(timeSlot.getStart() + " - " + timeSlot.getEnd());
}
