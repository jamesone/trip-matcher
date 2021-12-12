# Littlepay Trip Matcher

Welcome to my Trip Matcher program. GUESS WHAT: I love writing Java code, it is really enjoyable! It has been too long since I touched it (5 odd years 😳). With that being said, I would love to keep writing it at Littlepay on such an awesome product!! I am a super quick learner and pick things up quickly, especially when I am surrounded by the best. 

## Setting up the project

1. Open it with Eclipse (this was built on version 4.22.0)
> This has not been tested on other IDE's such as Netbeans.
2. Ensure you have JUnit5 installed (should be included with your Eclipse installation)


## Running the project 

1. From within eclipse, right click on the 'src' folder, hover over 'Run As', Click 'Java Application'
2. This will generate a 'trips.csv' in the root of the repository.

Bonus:

3. If you want to play around with the mock csv input data, check out `Main.java` and update the `csv` variable values.

## Running tests

1. From within eclipse, right click on the 'src' folder, hover over 'Run As', Click 'JUnit Test'
2. 7/7 tests should pass

## Design overview

### TripsController

Parses csv data (mocked as a multidimensional array) and sort the `Tap`'s into `Trip`'s.

1. `sortTapsCsvIntoTapList`: Ingests the CSV dataset & converts it into a List of `Tap` objects which are sorted based on the `Tap` date.
2. `sortTapsIntoUniquePanBusAndCompanyGroups`: Sorts the `Tap` objects into a unique HashMap data-structure (PAN:CompanyId:BusId -> List<Tap>)
3. `sortTapsIntoTrips`: Loop over the HashMap lists & match the `Tap`'s into `Trip`'s. 
4. `outputTrips`: Outputs the `Trip`'s  into a CSV

### Tap

Holds the data that we received from the CSV
### Trip

Contains the `Tap`'s which were matched together to form a `Trip`

1. `calculateTrip`: Calculates a Trip based on the start and end `Tap`'s
2. `getTripStatus`: Returns the Trip status based on the start and end `Tap`'s

### CsvParser

Handles parsing data into the corresponding objects.

1. `parseTapsCsv`: Parses the Tap's CSV (mocked) into an array of Tap's

### CsvWriter

Handles writing data to a CSV file.

1. `outputTripsToCsv`:  Given an array of Trip's, output it to a CSV file in the requested format

## Assumptions

In an attempt to not bloat the code with comments, I am listing all assumptions here and will reference them in the code using the following syntax: README.md -> <HEADER_2 (##)> -> <HEADER_3 (###)> -> <LIST_NUMBER>: <OPTIONAL_DESCRIPTION> (more list numbers can be added with a `,` separator) 

**Example comments:**

- `README.md -> Assumptions -> Trip -> 1`
- `README.md -> Assumptions -> Trip -> 1, 2`
- `README.md -> Assumptions -> Trip -> 1: This is a an added description`

### TripsController

1. If User taps on bus A, leaves bus without tapping off and then taps on Bus B we MUST treat this as a new trip.
2. Taps on/off with different companies must be treated as separate trips
### Trip

1. If a User taps on bus A Stop1 on Monday, forgets to tap off, then taps on bus A Stop1 the next day (this time would be configurable by bus company card reader system), the card reader should be treating this as a TAP_ON and not a TAP_OFF. I was thinking of adding a check in this system for this BUT I am assuming the card reader will handle it.

### CsvParser

1. The data given is valid. If an exception is thrown when parsing the csv line into a Tap object, that Tap is skipped.
> Some further work could be done here to log this to some monitoring system so it does not go unnoticed.