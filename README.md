# SKJProject

# Dokumentacja Aplikacji DAS

## Wstęp
Aplikacja DAS (Distributed Averaging System) realizuje rozproszony system uśredniający, wykorzystując protokół UDP. Umożliwia ona komunikację pomiędzy procesami działającymi w trybie **master** i **slave** w sieci lokalnej.

## Struktura aplikacji

Aplikacja składa się z trzech klas:
1. **DAS** - Klasa główna, odpowiedzialna za uruchamianie aplikacji w trybie `master` lub `slave`, zarządzanie testami i interakcję z użytkownikiem.
2. **DASclass** - Klasa implementująca główną logikę aplikacji, w tym obsługę protokołu komunikacyjnego UDP, przechowywanie danych oraz obliczanie średniej.
3. **TestCases** - Klasa pomocnicza do uruchamiania scenariuszy testowych, które weryfikują poprawność działania aplikacji.

## Opis działania aplikacji

### Tryb `master`
Proces w trybie `master` otwiera gniazdo UDP na zadanym porcie i oczekuje na komunikaty. Działanie procesu jest następujące:

1. **Odbiór komunikatów**:
   - Wartości różne od `0` i `-1` są zapisywane w liście `recivedNumbers` i wyświetlane na konsoli.
   - Wartość `0` wywołuje obliczenie średniej z dotychczas odebranych liczb oraz jej rozgłoszenie w sieci lokalnej.
   - Wartość `-1` powoduje zakończenie działania procesu, po uprzednim rozgłoszeniu wartości `-1` w sieci.

2. **Obliczanie średniej**:
   Średnia jest obliczana jako suma wszystkich liczb podzielona przez ich ilość. Wynik jest zaokrąglany do najbliższej liczby całkowitej.

3. **Rozgłoszenie komunikatów**:
   Aplikacja rozsyła komunikaty UDP do wszystkich komputerów w lokalnej sieci na zdefiniowanym porcie.

### Tryb `slave`
Proces w trybie `slave` wysyła wartość do `master` na podanym porcie i kończy działanie. Tryb `slave` wybierany jest automatycznie, jeśli port podany przy uruchomieniu aplikacji jest zajęty.

### Kluczowe metody w `DASclass`
- `start()`: Inicjalizuje tryb działania (master/slave).
- `master()`: Obsługuje odbieranie i przetwarzanie komunikatów w trybie `master`.
- `slave()`: Wysyła wartość liczbową w trybie `slave`.
- `calculateAverage()`: Oblicza średnią z liczb zapisanych w liście `recivedNumbers`.
- `sendMessage(String message)`: Wysyła wiadomość jako broadcast w sieci lokalnej.

### Klasa `TestCases`
Klasa ta zawiera gotowe scenariusze testowe:
1. Uruchomienie `master` na zadanym porcie.
2. Wysłanie liczby przez `slave`.
3. Wysłanie wartości `0` przez `slave`, co wywołuje obliczenie średniej.
4. Wysłanie wartości `-1` przez `slave`, co kończy działanie `master`.

## Instrukcja uruchamiania

1. Skompiluj plik za pomocą polecenia:
   ```
   javac *.java
   ```

2. Uruchom aplikację:
   - Jako serwer `master`:
     ```
     java DAS <port> <number>
     ```
     gdzie:
     - `<port>` - numer portu UDP,
     - `<number>` - liczba całkowita przesyłana przy uruchomieniu.
   
   - W trybie interakcji:
     ```
     java DAS
     ```

3. Wybierz testy lub uruchomienie serwera z menu w trybie interakcji.

## Problemy i ograniczenia
- **Brak mechanizmu potwierdzania komunikatów**: UDP nie gwarantuje dostarczenia wiadomości, co może prowadzić do utraty danych.
- **Brak obsługi wielu instancji master**: System zakłada, że w sieci lokalnej działa tylko jedna instancja `master` na danym porcie.

## Podsumowanie
Aplikacja realizuje rozproszony system komunikacji pomiędzy serverem, a klientem. Umożliwia łatwą konfigurację i testowanie w różnych środowiskach. Jej głównymi założeniami jest wyświetlanie dotychczasowo podanych liczb oraz obliczanie ich średniej. Mechanizm komunikacji UDP oraz implementacja trybów `master` i `slave` spełniają oczekiwania, choć istnieje możliwość dalszego rozwoju aplikacji, np. dodania mechanizmu potwierdzania odbioru wiadomości.


