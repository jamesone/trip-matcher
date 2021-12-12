package trip;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import tap.Tap;
import tap.TapType;

class TripTest {
	/** 
	 * COMPLETE trips, 
	 */
	Tap tap1 = new Tap(1, LocalDateTime.now(), TapType.ON, "Stop1", "Company1", "Bus37", 5500005555555559L);
	Tap tap2 = new Tap(2, LocalDateTime.now().plusMinutes(5), TapType.OFF, "Stop2", "Company1", "Bus37", 5500005555555559L);

	Trip tripStopOneAndTwoRange = new Trip(tap1, tap2);
	
	Tap tap3 = new Tap(1, LocalDateTime.now(), TapType.ON, "Stop2", "Company1", "Bus37", 5500005555555559L);
	Tap tap4 = new Tap(2, LocalDateTime.now().plusMinutes(5), TapType.OFF, "Stop3", "Company1", "Bus37", 5500005555555559L);

	Trip tripStopTwoAndThreeRange = new Trip(tap3, tap4);

	Tap tap5 = new Tap(3, LocalDateTime.now(), TapType.ON, "Stop1", "Company1", "Bus37", 5500005555555559L);
	Tap tap6 = new Tap(4, LocalDateTime.now().plusMinutes(5), TapType.OFF, "Stop3", "Company1", "Bus37", 5500005555555559L);


	Trip tripStopOneAndThreeRange = new Trip(tap5, tap6);
	
	@DisplayName("All trip calculations are correct")
	@Test
	void tripCalculationsAreCorrect() {	
		assertEquals(tripStopOneAndTwoRange.getTripCost(), 3.25);
		assertEquals(tripStopTwoAndThreeRange.getTripCost(), 5.50);
		assertEquals(tripStopOneAndThreeRange.getTripCost(), 7.30);
	}
		
	/** 
	 * Test and ensure all trip statuses are displayed correctly
	 */
	@DisplayName("Trip status must be COMPLETED")
	@Test
	void tripStatusIsCompleted() {
		assertEquals(tripStopOneAndThreeRange.getTripStatus(), TripStatus.COMPLETED);
	}
	
	@DisplayName("Trip status must be INCOMPLETE")
	@Test
	void tripStatusIsIncomplete() {
		Tap tap = new Tap(1, LocalDateTime.now(), TapType.ON, "Stop1", "Company1", "Bus37", 5500005555555559L);
		Trip incompleteTrip = new Trip(tap);
		assertEquals(incompleteTrip.getTripStatus(), TripStatus.INCOMPLETE);
	}
	
	@DisplayName("Trip status must be CANCELLED")
	@Test
	void tripStatusIsCancelled() {
		Tap tapSameStop1 = new Tap(3, LocalDateTime.now(), TapType.ON, "Stop1", "Company1", "Bus37", 5500005555555559L);
		Tap tapSameStop2 = new Tap(4, LocalDateTime.now().plusMinutes(5), TapType.OFF, "Stop1", "Company1", "Bus37", 5500005555555559L);
		Trip cancelledTrip = new Trip(tapSameStop1, tapSameStop2);
		
		assertEquals(cancelledTrip.getTripStatus(), TripStatus.CANCELLED);
	}
}
