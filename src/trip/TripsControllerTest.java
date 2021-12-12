package trip;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import csv.CsvParser;

class TripsControllerTest {
	String[][] csv = {
			// Complete trip
			{ "1", "22-01-2018 13:00:00", "ON", "Stop1", "Company1", "Bus37", "5500005555555559" },
			{ "2", "22-01-2018 13:15:00", "OFF", "Stop2", "Company1", "Bus37", "5500005555555559" },
			// Incomplete trips
			{ "4", "20-01-2018 14:00:00", "ON", "Stop1", "Company1", "Bus38", "5500005555555559" },
			{ "5", "20-01-2018 14:15:00", "ON", "Stop2", "Company1", "Bus39", "5500005555555559" },
			// Cancelled trip - This should render Tap #4 incomplete.
			{ "6", "20-01-2018 14:15:00", "ON", "Stop1", "Company1", "Bus38", "5500005555555559" },
			{ "7", "20-01-2018 14:15:00", "OFF", "Stop1", "Company1", "Bus38", "5500005555555559" },
			};
		
	@DisplayName("Does return the correct amount of Trip's")
	@Test
	void tapsCsvTripsCountIsCorrect() {
		CsvParser parser = new CsvParser();
		TripsController tripsController = new TripsController(parser.parseTapsCsv(csv));
		tripsController.run();
		
		assertEquals(tripsController.getTrips().size(), 3);
	}

}
