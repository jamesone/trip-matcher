package trip;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import tap.*;

/**
 * A Trip consists of a start and an end tap. This class will validate and
 * calculate the input Taps.
 *
 * For the simplicity of this exercise, stops will be in numeric order.
 */
public class Trip {
	private Tap startTap;
	private Tap endTap;
	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	private final double MAX_TRIP_COST = 7.30;
	private final double STOP_1_2_COST = 3.25;
	private final double STOP_2_3_COST = 5.50;
	private final double CANCELLED_TRIP_COST = 0.00;
	
	public Trip(Tap startTap, Tap endTap) {
		super();
		this.startTap = startTap;
		this.endTap = endTap;
	}

	public Trip(Tap startTap) {
		super();
		this.startTap = startTap;
	}

	/**
	 * Potentially move into own Class? A Numeric class which does any numeric
	 * specific functions
	 * 
	 * @return
	 */
	private boolean isBetweenRange(int numberToCheck, int startRange, int endRange) {
		return numberToCheck >= startRange && numberToCheck <= endRange;
	}

	/**
	 * The following function will calculate the trip
	 * 
	 * Trips between Stop 1 and Stop 2 cost $3.25
	 * Trips between Stop 2 and Stop 3 cost $5.50 
	 * Trips between Stop 1 and Stop 3 cost $7.30
	 * 
	 */
	public double getTripCost() {
		TripStatus currentTripStatus = this.getTripStatus();
		if (currentTripStatus == TripStatus.INCOMPLETE) {			
			return startTap.getNumericStopId() == 2 ? STOP_2_3_COST : MAX_TRIP_COST;
		} else if (currentTripStatus == TripStatus.CANCELLED) {
			return CANCELLED_TRIP_COST;
		}

		int startStopId = this.startTap.getNumericStopId();
		int endStopId = this.endTap.getNumericStopId();

		
		if (this.isBetweenRange(startStopId, 1, 2) && this.isBetweenRange(endStopId, 1, 2)) {
			return STOP_1_2_COST;
		} else if (this.isBetweenRange(startStopId, 2, 3) && this.isBetweenRange(endStopId, 2, 3)) {
			return STOP_2_3_COST;
		}

		return MAX_TRIP_COST;
	}
	

	public Tap getEndTap() {
		return endTap;
	}

	public void setEndTap(Tap endTap) {
		this.endTap = endTap;
	}
	
	/** 
	 * Return the trip status based on the given Tap's
	 */
	public TripStatus getTripStatus() {
		if (this.endTap == null) {
			return TripStatus.INCOMPLETE;
		}
		
		/** 
		 * When the stopId's are the same, we render the trip as CANCELLED. 
		 * 
		 * @note An assumption has been made: Please refer to README.md -> Assumptions -> Trip -> 1
		 */
		return this.startTap.stopId == this.endTap.stopId ? TripStatus.CANCELLED : TripStatus.COMPLETED;
	}
	
	public String getStartedDate() {
		return  dateFormatter.format(this.startTap.getDateTapped()).toString();
	}
	
	public String getEndedDate() {
		return this.endTap != null ? dateFormatter.format(this.endTap.getDateTapped()).toString() : "";
	}
	
	public long getDurationInSeconds() {
		return this.endTap != null ? this.startTap.dateTapped.until(this.endTap.dateTapped, ChronoUnit.SECONDS) : 0;
	}
	
	public String getStartStopId() {
		return this.startTap.stopId;
	}
	
	public String getEndStopId() {
		return this.endTap != null ? this.endTap.stopId : "";
	}
	
	public String getCompanyId() {
		return this.startTap.companyId;
	}
	
	public String getBusId() {
		return this.startTap.busId;
	}
	
	public long getPan() {
		return this.startTap.pan;
	}
}
