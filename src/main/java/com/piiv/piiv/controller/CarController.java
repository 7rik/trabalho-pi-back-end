package com.piiv.piiv.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.piiv.piiv.dto.CarRegistroDto;
import com.piiv.piiv.dto.CarResponseDto;
import com.piiv.piiv.entities.Car;
import com.piiv.piiv.entities.Usuario;
import com.piiv.piiv.repository.CarRepository;
import com.piiv.piiv.repository.UsuarioRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class CarController {

    @Autowired
    CarRepository carRepository;
    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping("/cars")
    public ResponseEntity<List<CarResponseDto>> getAllCars() {
        try {
            List<Car> cars = carRepository.findAll();

            if (cars.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            List<CarResponseDto> carDto = new ArrayList<>();

            cars.forEach(car->{
            	CarResponseDto carTemp = new CarResponseDto();
            	if(car.getInteressado() != null) {
                	Usuario userDTO = usuarioRepository.findById(car.getInteressado()).get();
                	carTemp.setInteressado(userDTO);
            	}
            	carTemp.setFoto(car.getFoto());
            	carTemp.setAnoDeFabricacao(car.getAnoDeFabricacao());
            	carTemp.setAnoDoModelo(car.getAnoDoModelo());
            	carTemp.setDescricao(car.getDescricao());
            	carTemp.setId(car.getId());
            	carTemp.setMarca(car.getMarca());
            	carTemp.setModelo(car.getModelo());
            	carTemp.setValor(car.getValor());
            	carDto.add(carTemp);
            });
            return new ResponseEntity<>(carDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/cars/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable("id") Long id) {
        Optional<Car> carData = carRepository.findById(id);

        if (carData.isPresent()) {
            return new ResponseEntity<>(carData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/cars/{id}/interesse/{userId}")
    public ResponseEntity<Car> updateInteresse(@PathVariable("id") Long id, @PathVariable("userId") Integer interessado) {
    	try {
    		Car carro = carRepository.findById(id).get();
    		carro.setInteressado(interessado);
	        return new ResponseEntity<>(carRepository.save(carro), HttpStatus.CREATED);
    	} catch (Exception e) {
	    	System.out.println("error: "+e.getMessage());
	        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
    }
    
    @PostMapping("/cars")
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        try {
        	Car carro = new Car();
        	carro.setFoto(car.getFoto());
        	carro.setDescricao(car.getDescricao());
        	carro.setAnoDeFabricacao(car.getAnoDeFabricacao());
        	carro.setMarca(car.getMarca());
        	carro.setAnoDoModelo(car.getAnoDoModelo());
        	carro.setModelo(car.getModelo());
        	carro.setValor(car.getValor());

            Car _car = carRepository.save(carro);
            return new ResponseEntity<>(_car, HttpStatus.CREATED);
        } catch (Exception e) {
        	System.out.println("error: "+e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/cars/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable("id") Long id, @RequestBody Car car) {
        Optional<Car> carData = carRepository.findById(id);

        if (carData.isPresent()) {
            Car _car = carData.get();
            _car.setMarca(car.getMarca());
            _car.setModelo(car.getModelo());
            _car.setDescricao(car.getDescricao());
            _car.setAnoDeFabricacao(car.getAnoDeFabricacao());
            _car.setAnoDoModelo(car.getAnoDoModelo());
            _car.setValor(car.getValor());
            _car.setInteressado(car.getInteressado());
            return new ResponseEntity<>(carRepository.save(_car), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/cars/{id}")
    public ResponseEntity<HttpStatus> deleteCar(@PathVariable("id") Long id) {
        try {
            carRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/cars")
    public ResponseEntity<HttpStatus> deleteAllCars() {
        try {
            carRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}