package com.piiv.piiv.entities;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.*;

@Entity
public class RentingProposal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "car_id", nullable = false)
    private Integer carId;
    
    @JsonInclude()
    @Transient
    private Car car;
    @JsonInclude()
    @Transient
    private Usuario user;

    // Additional attributes for the renting proposal

    // Constructors, getters, setters, etc.

    public RentingProposal() {
    }

    public RentingProposal(Integer userId, Integer carId) {
        this.userId = userId;
        this.carId = carId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}

    // Additional getters and setters for other attributes
}