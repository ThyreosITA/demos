package com.example.demo.repository;


import com.example.demo.model.Giocatore;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;



public interface GiocatoreRepository
        extends JpaRepository<Giocatore, Long>,
        JpaSpecificationExecutor<Giocatore> {



    List<Giocatore>
    findAllByNomeIgnoreCaseAndSquadraIgnoreCase(
            String nome,
            String squadra
    );



}