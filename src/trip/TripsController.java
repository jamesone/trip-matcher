package trip;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import tap.*;

/**
 * Responsible for ingesting a dataset of Tap's (CSV) and matching them into Trip's.
 */
public class TripsController {
	private ArrayList<Tap> taps;
	private ArrayList<Trip> trips = new ArrayList<>();

	HashMap<String, List<Tap>> tapsSortedByPanCompanyIdBusId = new HashMap<>();

	/**
	 * @param taps Expects a multidimensional array matching the structured like so: [[ID, DateTimeUTC, TapType, StopId, CompanyId, BusID, PAN], ...]
	 */
	public TripsController(ArrayList<Tap> taps) {
		super();
		this.taps = taps;
	}

	/**
	 * Sort the taps list by the dateTapped
	 */
	private void sortTapsByTapDate() {
		taps.sort((tap1, tap2) -> tap1.dateTapped.compareTo(tap2.dateTapped));
	}

	/**
	 * Given a sorted array of Taps, Bucket them into a HashMap with a key: pan:CompanyId:busId
	 * 
	 * Assumptions enforced here:
	 * - README.md -> Assumptions -> TripsController -> 1, 2 
	 * 
	 * Once they are bucketed, creating trips from the Taps will be super easy.
	 */
	private void sortTapsIntoUniquePanBusAndCompanyGroups() {
		/**
		 * Put Taps into a HashMap identified by Pan:CompanyId Performance Cost =
		 * Looping over taps.size();
		 */
		for (int i = 0; i <= taps.size() - 1; i++) {
			Tap tapToAdd = taps.get(i);
			String hashmapKey = tapToAdd.pan + ":" + tapToAdd.companyId + ":" + tapToAdd.busId;
			/**
			 * If first item for key, create ArrayList, then continue to add tap.
			 */
			tapsSortedByPanCompanyIdBusId.computeIfAbsent(hashmapKey, v -> new ArrayList<>()).add(tapToAdd);
		}
	}

	/**
	 * Sort Taps into Trips - It should loop over the Tap's and setup complete/incomplete/cancelled Trip's
	 *  
	 * Assumptions I have made here:
	 * - Refer to README.md -> Assumptions -> Trip -> 1
	 */
	private void sortTapsIntoTrips() {
		for (String key : tapsSortedByPanCompanyIdBusId.keySet()) {
			List<Tap> panCompanyIdBusIdTaps = tapsSortedByPanCompanyIdBusId.get(key);
			
			/**
			 * Stores the
			 */
			int nextSortedIndex = 0;

			/**
			 * Loop over the sorted Taps and sorts them into Trips. 
			 */
			tapsLoop:
			for (int i = 0; i < panCompanyIdBusIdTaps.size() - 1; i++) {
				
				/**
				 * Any index which was already processed will cause this iteration to be
				 * skipped. Reason: The current Tap has already been assigned a Trip.
				 */
				if (i != 0 && nextSortedIndex == i) {
					continue tapsLoop;
				}

				int nextIndex = i + 1;
				Tap tap1 = panCompanyIdBusIdTaps.get(i);
				Tap tap2;
				
				Trip trip = new Trip(tap1);
				
				/**
				 * Try match the current Tap (tap1) and the next Tap (tap2) together to form a Trip.
				 */
				if (nextIndex <= panCompanyIdBusIdTaps.size() - 1) {
					tap2 = panCompanyIdBusIdTaps.get(i + 1);			
					
					/**
					 * Run the checks needed to match the two Tap's to create a Trip and determine it's status. 
					 */
					if (tap1.tapType == TapType.ON && tap2.tapType == TapType.OFF) {
						trip.setEndTap(tap2);
						/**
						 * Track the next Tap index so that we can ensure we don't process it.
						 */
						nextSortedIndex = nextIndex;
					}
				} 
				
				
				trips.add(trip);
			}

		}
	}
	
	/** 
	 * Match Tap's into Trip's
	 */
	private void matchTapsToTrips() {
		/**
		 * Ensure the Tap's are sorted chronological order
		 */
		this.sortTapsByTapDate();
		
		/**
		 * Throw an error if there are no taps available.
		 */
		if (this.taps.size() == 0) {
			throw new Error("No taps available - Check the data you're passing and try again.");
		}
		
		

		/**
		 * Group the Taps into the unique PAN:CompanyId HashMap sets which will be the
		 * data-structure that makes sorting the Taps into trips easy and performant
		 */
		this.sortTapsIntoUniquePanBusAndCompanyGroups();


		/**
		 * Now we can easily match each Tap into a Trip as they are grouped into a
		 * HashMap and identified by pan:companyId:busId AND sorted by touch on date. This
		 * allows us to loop over the HashMap items (which contain all taps for a bus
		 * company, PAN, and busId) without doing as much recursion.
		 */
		this.sortTapsIntoTrips();
	}
	
	/** 
	 * Matches Tap's into Trip's and outputs the Trip's CSV. 
	 */
	public void run() {
		this.matchTapsToTrips();
	}
	
	public ArrayList<Trip> getTrips() {
		return trips;
	}
}
