package fr.dauphine.mido.doctophine.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

@MappedSuperclass
public class AbstractEvent {
	
	@OneToOne(cascade = CascadeType.ALL )
	@JoinColumn(name = "activity_ID", referencedColumnName = "id")
	protected Activity activity;
	
	@Column(name="start_date")
	protected Date startDate;
	
	@Column(name="end_date")
	protected Date endDate;

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	

}
