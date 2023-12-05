package com.piiv.piiv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.piiv.piiv.entities.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
}