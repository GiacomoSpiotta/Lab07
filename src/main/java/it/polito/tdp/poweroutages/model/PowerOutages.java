package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class PowerOutages {
	
	int id;
	int year;
	LocalDateTime dateBegan;
	LocalDateTime dateFinish;
	int hoursOfOutage;
	int peopleAffected;
	
	public PowerOutages(int id, LocalDateTime dateBegan, LocalDateTime dateFinish, int peopleAffected) {
		super();
		this.id = id;
		this.dateBegan = dateBegan;
		this.dateFinish = dateFinish;
		LocalDateTime tempDateTime = LocalDateTime.from(dateBegan);
		this.hoursOfOutage = (int)tempDateTime.until(dateFinish, ChronoUnit.HOURS);
		this.peopleAffected = peopleAffected;
		this.year = dateBegan.getYear();
	}

	public int getId() {
		return id;
	}

	public int getYear() {
		return year;
	}

	public LocalDateTime getDateBegan() {
		return dateBegan;
	}

	public LocalDateTime getDateFinish() {
		return dateFinish;
	}

	public int getHoursOfOutage() {
		return hoursOfOutage;
	}

	public int getPeopleAffected() {
		return peopleAffected;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(String.format("\n%-5d", this.year));
		builder.append(String.format("%-18s", dateBegan.toString()));
		builder.append(String.format("%-18s", dateFinish.toString()));
		builder.append(String.format("%-4d", this.hoursOfOutage));
		builder.append(String.format("%-6d", this.peopleAffected));
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PowerOutages other = (PowerOutages) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
