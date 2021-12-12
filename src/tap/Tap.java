package tap;
import java.time.LocalDateTime;

public class Tap {
	public int id;
	public LocalDateTime dateTapped;
	public TapType tapType;
	public String stopId;
	public String companyId;
	public String busId;

	public long pan;

	public Tap(int id, LocalDateTime dateTapped, TapType tapType, String stopId, String companyId, String busId,
			long pan) {
		super();
		this.id = id;
		this.dateTapped = dateTapped;
		this.tapType = tapType;
		this.stopId = stopId;
		this.companyId = companyId;
		this.busId = busId;
		this.pan = pan;
	}
	
	/** 
	 * Extracts the numeric stopId from the string so that it can be used for 
	 * price calculation
	 */
	public int getNumericStopId() {
		return Integer.parseInt(String.valueOf(this.stopId.charAt(stopId.length() - 1)));
	}

	public LocalDateTime getDateTapped() {
		return dateTapped;
	}

}
