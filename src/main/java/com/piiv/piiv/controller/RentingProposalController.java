package com.piiv.piiv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.piiv.piiv.entities.RentingProposal;
import com.piiv.piiv.repository.CarRepository;
import com.piiv.piiv.repository.RentingProposalRepository;
import com.piiv.piiv.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/renting-proposals")
public class RentingProposalController {

    @Autowired
    private RentingProposalRepository rentingProposalRepository;
    @Autowired
    private CarRepository carRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;

    @GetMapping
    public ResponseEntity<List<RentingProposal>> getAllRentingProposals() {
        List<RentingProposal> proposals = rentingProposalRepository.findAll();
        if (proposals.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(proposals, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentingProposal> getRentingProposalById(@PathVariable("id") Long id) {
        Optional<RentingProposal> proposalData = rentingProposalRepository.findById(id);
        return proposalData.map(proposal -> new ResponseEntity<>(proposal, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping("/report")
    public ResponseEntity<List<RentingProposal>> getRentingProposalsDetailed() {
    	List<RentingProposal> proposals = rentingProposalRepository.findAll();
        if (proposals.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
        	RentingProposal proposal;
            for(int i = 0; i < proposals.size(); i++) {
            	proposal = proposals.get(i);
            	proposal.setCar(carRepository.findById(proposal.getCarId().longValue()).get());
            	proposal.setUser(usuarioRepository.findById(proposal.getUserId()).get());
            	proposals.set(i, proposal);
            }
            return new ResponseEntity<>(proposals, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<RentingProposal> createRentingProposal(@RequestBody RentingProposal proposal) {
        try {
            RentingProposal createdProposal = rentingProposalRepository.save(proposal);
            return new ResponseEntity<>(createdProposal, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RentingProposal> updateRentingProposal(
            @PathVariable("id") Long id, @RequestBody RentingProposal proposal) {
        Optional<RentingProposal> proposalData = rentingProposalRepository.findById(id);
        return proposalData.map(existingProposal -> {
            existingProposal.setUserId(proposal.getUserId());
            existingProposal.setCarId(proposal.getCarId());
            // Update other attributes as needed

            RentingProposal updatedProposal = rentingProposalRepository.save(existingProposal);
            return new ResponseEntity<>(updatedProposal, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteRentingProposal(@PathVariable("id") Long id) {
        try {
            rentingProposalRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAllRentingProposals() {
        try {
            rentingProposalRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

