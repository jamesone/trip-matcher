package csv;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import tap.Tap;

class CsvParserTest {
	String[][] csv = {
			// Complete trip
			{ "1", "22-01-2018 13:00:00", "ON", "Stop1", "Company1", "Bus37", "5500005555555559" },
			{ "2", "22-01-2018 13:15:00", "OFF", "Stop2", "Company1", "Bus37", "5500005555555559" },
			// Complete trip
			{ "2", "20-01-2018 14:00:00", "ON", "Stop1", "Company1", "Bus37", "5500005555555559" },
			{ "3", "20-01-2018 14:15:00", "OFF", "Stop2", "Company1", "Bus37", "5500005555555559" },
			// Incomplete trips
			{ "4", "20-01-2018 14:00:00", "ON", "Stop1", "Company1", "Bus38", "5500005555555559" },
			{ "5", "20-01-2018 14:15:00", "ON", "Stop2", "Company1", "Bus39", "5500005555555559" },
			// Cancelled trip - This should render Tap #4 incomplete.
			{ "6", "20-01-2018 14:15:00", "ON", "Stop1", "Company1", "Bus38", "5500005555555559" },
			{ "7", "20-01-2018 14:15:00", "OFF", "Stop1", "Company1", "Bus38", "5500005555555559" },
			};
	String[][] invalidTapsCsv = {
			{ "1", "INVALID DATE", "ON", "Stop1", "Company1", "Bus37", "5500005555555559" },
			};
	CsvParser parser = new CsvParser();
	
	@DisplayName("Ensure taps CSV structure is parsed correctly")
	@Test
	void ensureTapsCsvParsedCorrectly() {
		/** 
		 * Parse the CSV (currently mocked arr) to an array of Tap's
		 */
		ArrayList<Tap> taps = parser.parseTapsCsv(csv);
		assertEquals(taps.size(), 8);
		assertEquals(taps.get(0).stopId, "Stop1");
	}	
	
	
	@DisplayName("Ensure invalid tap data does not throw an error and is not parsed")
	@Test
	void ensureInvalidTapsAreNotParsed() {
		/** 
		 * Parse the CSV (currently mocked arr) to an array of Tap's
		 */
		ArrayList<Tap> taps = parser.parseTapsCsv(invalidTapsCsv);
		assertEquals(taps.size(), 0);
	}

}
