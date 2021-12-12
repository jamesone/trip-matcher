import csv.CsvParser;
import csv.CsvWriter;
import trip.*;

public class Main {
	public static void main(String[] args) {
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
		
		/** 
		 * Parse the CSV (currently mocked arr) to an array of Tap's
		 */
		CsvParser parser = new CsvParser();
		
		/** 
		 * Pass the parsed CSV and run the controller logic which:
		 * a) Matches the Tap's into Trip's 
		 * b) Outputs the Trips
		 */
		TripsController tripsController = new TripsController(parser.parseTapsCsv(csv));
		tripsController.run();
		
		/** 
		 * Output the Trips to ROOT_REPO/trips.csv
		 */
		CsvWriter csvWriter = new CsvWriter();
		csvWriter.outputTripsToCsv(tripsController.getTrips());
		
		System.out.println("Success! Trips have been written to 'trips.csv' in the root of this repository");
	}

}
