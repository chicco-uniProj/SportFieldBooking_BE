package com.prog.progettopsw.repositories;

import com.prog.progettopsw.entities.Court;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;

@Repository
public interface CourtRepo extends JpaRepository<Court,Integer> {


    Court findById(int id);

    List<Court> findByType(String type);
    Page<Court> findByType(String type, Pageable p);

    List<Court>findByCity(String city);
    Page<Court>findByCity(String city,Pageable p);

    List<Court>findByPriceHourlyAndType(float price,String type);
    Page<Court>findByPriceHourlyAndType(float price,String type,Pageable p);

    List<Court>findByCityAndType(String city,String type);
    Page<Court>findByCityAndType(String city,String type,Pageable p);

    List<Court>findAll();
    Page<Court>findAll(Pageable p);

    boolean existsById(int id);
    boolean existsByName(String name);
}
