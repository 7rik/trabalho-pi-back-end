package com.piiv.piiv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.piiv.piiv.entities.RentingProposal;

@Repository
public interface RentingProposalRepository extends JpaRepository<RentingProposal, Long> {}