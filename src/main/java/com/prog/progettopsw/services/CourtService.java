package com.prog.progettopsw.services;


import com.prog.progettopsw.entities.Court;
import com.prog.progettopsw.repositories.CourtRepo;
import com.prog.progettopsw.support.exception.CourtAlreadyExistException;
import com.prog.progettopsw.support.exception.CourtNotExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

@Service
public class CourtService {
    @Autowired
    CourtRepo courtRepo;

    @Transactional(readOnly = false)
    public void addCourt(Court court) throws CourtAlreadyExistException{
        if(courtRepo.existsById(court.getId()) || courtRepo.existsByName(court.getName()))
            throw new CourtAlreadyExistException();
        courtRepo.save(court);
    }
    @Transactional(readOnly = false)
    public void deleteCourt(Court court) throws CourtNotExistsException {
        if(!courtRepo.existsById(court.getId()))
            throw new CourtNotExistsException();
        courtRepo.delete(court);
    }

    @Transactional(readOnly = true)
    public List<Court>showByPriceAndType(float price,String type){
        return courtRepo.findByPriceHourlyAndType(price,type);
    }
    @Transactional(readOnly = true)
    public List<Court>showByPriceAndType(float price,String type,int numPage,int dimPage,String sortby){
        Pageable page= PageRequest.of(numPage,dimPage, Sort.by(sortby));
        Page<Court>pagined = courtRepo.findByPriceHourlyAndType(price,type,page);
        if(pagined.hasContent())
            return pagined.getContent();
        return new ArrayList<>(); //restituisco una lista vuota cosi non ho problemi con risultati di tipo null
    }

    @Transactional(readOnly = true)
    public List<Court>showByCityAndType(String city,String type){
        return courtRepo.findByCityAndType(city,type);
    }
    @Transactional(readOnly = true)
    public List<Court>showByCityAndType(String city,String type,int numPage,int dimPage,String sortby){
        Pageable page= PageRequest.of(numPage,dimPage, Sort.by(sortby));
        Page<Court>pagined = courtRepo.findByCityAndType(city,type,page);
        if(pagined.hasContent())
            return pagined.getContent();
        return new ArrayList<>(); //restituisco una lista vuota cosi non ho problemi con risultati di tipo null
    }


    @Transactional(readOnly = true)
    public List<Court>showCourtByType(String type){
        return courtRepo.findByType(type);
    }
    @Transactional
    public List<Court>showCourtByType(String type,int numPage,int dimPage,String sortby){
        Pageable page= PageRequest.of(numPage,dimPage, Sort.by(sortby));
        Page<Court>pagined = courtRepo.findByType(type,page);
        if(pagined.hasContent())
            return pagined.getContent();
        return new ArrayList<>();
    }

    @Transactional(readOnly = true)
    public List<Court>showCourtByCity(String city){
        return courtRepo.findByCity(city);
    }
    @Transactional
    public List<Court>showCourtByCity(String city,int numPage,int dimPage,String sortby){
        Pageable page= PageRequest.of(numPage,dimPage, Sort.by(sortby));
        Page<Court>pagined = courtRepo.findByCity(city,page);
        if(pagined.hasContent())
            return pagined.getContent();
        return new ArrayList<>();
    }

    @Transactional(readOnly = true)
    public List<Court>showAllCourts(){
        return courtRepo.findAll();
    }
    @Transactional
    public List<Court>showAllCourts(int numPage,int dimPage,String sortby){
        Pageable page= PageRequest.of(numPage,dimPage, Sort.by(sortby));
        Page<Court>pagined = courtRepo.findAll(page);
        if(pagined.hasContent())
            return pagined.getContent();
        return new ArrayList<>();
    }

    @Transactional(readOnly = true)
    public Court showById(int id){
        return courtRepo.findById(id);
    }



}
