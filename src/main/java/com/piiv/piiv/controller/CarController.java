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

import lombok.var;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class CarController {

    @Autowired
    CarRepository carRepository;
    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping("/public/cars")
    public ResponseEntity<List<Car>> getAllCarsPublic() {
    	try {
    		List<Car> cars = carRepository.findByInteressadoNull();
    		return new ResponseEntity<>(cars, HttpStatus.OK);
    	}catch(Exception e) {
          return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    }

    @GetMapping("/cars")
    public ResponseEntity<List<Car>> getAllCars() {
    	try {
    		List<Car> cars = carRepository.findAll();
    		return new ResponseEntity<>(cars, HttpStatus.OK);
    	}catch(Exception e) {
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

    @GetMapping("/cars/interesse")
    public ResponseEntity<List<CarResponseDto>> getAllCarsInterested() {
    	try {
    		List<Car> cars = carRepository.findByInteressadoNotNull();
    		List<CarResponseDto> carDTO = new ArrayList<CarResponseDto>();
    
    		cars.forEach(car->{
    			CarResponseDto carTemp = new CarResponseDto();
    			Usuario userDTO = usuarioRepository.findById(car.getInteressado()).get();
    			carTemp.setInteressado(userDTO);
    			carTemp.setAnoDeFabricacao(car.getAnoDeFabricacao());
    			carTemp.setAnoDoModelo(car.getAnoDoModelo());
    			carTemp.setDescricao(car.getDescricao());
    			carTemp.setFoto(car.getFoto());
    			carTemp.setHistoricoInteressado(car.getHistoricoInteressado());
    			carTemp.setId(car.getId());
    			carTemp.setMarca(car.getMarca());
    			carTemp.setModelo(car.getModelo());
    			carTemp.setValor(car.getValor());
    			carDTO.add(carTemp);
    		});
    	  return new ResponseEntity<>(carDTO, HttpStatus.OK);
    	}catch(Exception e) {
          return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    }

    @PutMapping("/cars/{id}/interesse/{userId}")
    public ResponseEntity<Car> updateInteresse(@PathVariable("id") Long id, @PathVariable("userId") Integer interessado) {
    	try {
    		Car carro = carRepository.findById(id).get();
    		if(carro == null) {
    			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    		}

    		carro.setInteressado(interessado);
    		List<Integer> historico = new ArrayList<>(); 
    		if(carro.getHistoricoInteressado() == null || carro.getHistoricoInteressado().isEmpty()) {
    			historico.add(interessado);
    			carro.setHistoricoInteressado(historico);
    		}else {
    			historico = carro.getHistoricoInteressado();
    			carro.setHistoricoInteressado(historico);
    		}
	        return new ResponseEntity<>(carRepository.save(carro), HttpStatus.CREATED);
    	} catch (Exception e) {
	    	System.out.println("error: "+ e.getMessage());
	        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
    }
    
    @PutMapping("/cars/{id}/removeinteresse/{userId}")
    public ResponseEntity<Car> removeInteresse(@PathVariable("id") Long id, @PathVariable("userId") Integer interessado) {
    	try {
    		Car carro = carRepository.findById(id).get();
    		carro.getInteressado();
    		if (carro.getInteressado() != null) {
    			carro.setInteressado(null);
    		}
    		
    		return new ResponseEntity<>(carRepository.save(carro), HttpStatus.OK);
    	} catch (Exception e) {
    		System.out.println("error: "+ e.getMessage());
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
            _car.setHistoricoInteressado(car.getHistoricoInteressado());
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