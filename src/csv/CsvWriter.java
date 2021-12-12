package csv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import trip.Trip;

public class CsvWriter {
	private final String SEPARATOR = ", ";
	StringBuilder sb;
	
	private void writeLine(String value, boolean isLast) {
		sb.append(value);
		sb.append(isLast ? "\n": SEPARATOR);
	}
	
	/** 
	 * Given an array of Trip's, output it to a CSV file
	 */
	public void outputTripsToCsv(ArrayList<Trip> trips) {
		File csvFile = new File("trips.csv");
		try {
			csvFile.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try (PrintWriter writer = new PrintWriter("trips.csv")) {

			sb = new StringBuilder();
			this.writeLine("Started", false);
			this.writeLine("Finished", false);
			this.writeLine("DurationSecs", false);
			this.writeLine("FromStopId", false);
			this.writeLine("ToStopId", false);
			this.writeLine("ChargeAmount", false);
			this.writeLine("CompanyId", false);
			this.writeLine("BusID", false);
			this.writeLine("PAN", false);
			this.writeLine("Status", true);
			
			trips.forEach(trip -> {
				this.writeLine(trip.getStartedDate(), false);
				this.writeLine(trip.getEndedDate(), false);
				this.writeLine(Long.toString(trip.getDurationInSeconds()), false);
				this.writeLine(trip.getStartStopId(), false);
				this.writeLine(trip.getEndStopId(), false);
				this.writeLine("$" + trip.getTripCost(), false);
				this.writeLine(trip.getCompanyId(), false);
				this.writeLine(trip.getBusId(), false);
				this.writeLine(Long.toString(trip.getPan()), false);
				this.writeLine(trip.getTripStatus().toString(), true);
			});
			
			writer.write(sb.toString());
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
}
