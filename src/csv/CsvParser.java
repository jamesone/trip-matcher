package csv;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import tap.Tap;
import tap.TapType;

/**
 * Handles converting CSV formatted data to the requested format.
 * 
 * @note This currently mocks a CSV file structure using a multidimensional
 *       array
 */
public class CsvParser {

	public ArrayList<Tap> parseTapsCsv(String[][] csv) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		ArrayList<Tap> taps = new ArrayList<>();

		/**
		 * Simply loop over the csv lines & create new Tap objects TODO Add validation
		 * to filter out bad data - and try/catch?
		 */
		for (int i = 0; i < csv.length; i++) {
			String[] csvLine = csv[i];
			Tap tap;
			/**
			 * Skip any Tap's which cannot be formatted correctly.
			 * An extension to this could be to log all "invalid" Tap's to a logging system so that
			 * it can be looked into.
			 * Assumptions made:
			 * - Please refer to: README.md -> Assumptions -> CsvParser -> 1
			 */
			try {
				tap = new Tap(i, LocalDateTime.parse(csvLine[1], formatter), TapType.valueOf(csvLine[2]), csvLine[3],
						csvLine[4], csvLine[5], Long.parseLong(csvLine[6]));
			} catch (Exception e) {
				continue;
			}

			taps.add(tap);
		}

		return taps;
	}
}
