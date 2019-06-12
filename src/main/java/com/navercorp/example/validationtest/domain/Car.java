package com.navercorp.example.validationtest.domain;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Range;

public class Car {
	@NotBlank(message = "The manufacturer must not be empty.")
	private String manufacturer;

	@Range(min = 0, max = 10, message = "The seat count must be between 0 ~ 10")
	private int seatCount;

	@Range(min = 0, max = 300, message = "The speed must be between 0 ~ 300")
	private int topSpeed;

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public int getSeatCount() {
		return seatCount;
	}

	public void setSeatCount(int seatCount) {
		this.seatCount = seatCount;
	}

	public int getTopSpeed() {
		return topSpeed;
	}

	public void setTopSpeed(int topSpeed) {
		this.topSpeed = topSpeed;
	}

	@Override
	public String toString() {
		return "Car {" +
			"manufacturer='" + manufacturer + '\'' +
			", seatCount=" + seatCount +
			", topSpeed=" + topSpeed +
			'}';
	}
}
