package com.prog.progettopsw.controllers;


import com.prog.progettopsw.entities.Court;
import com.prog.progettopsw.entities.User;
import com.prog.progettopsw.services.CourtService;
import com.prog.progettopsw.support.exception.CourtAlreadyExistException;
import com.prog.progettopsw.support.exception.CourtNotExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/courts")
public class CourtController {
    @Autowired
    private CourtService courtService;

    @PostMapping("/add")
    public ResponseEntity addCourt(@RequestBody @Valid Court court){
        try{
            courtService.addCourt(court);
        }catch (CourtAlreadyExistException e){
            return new ResponseEntity("Court already exists", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Court "+court.getName()+ " added!",HttpStatus.OK);
    }
    @PostMapping("/delete")
    public ResponseEntity deleteCourt(@RequestBody @Valid Court court){
        try{
            courtService.deleteCourt(court);
        }catch (CourtNotExistsException cnee){
            return new ResponseEntity("Court doesn't exist",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Court: "+court.getName()+" deleted",HttpStatus.OK);
    }

    @GetMapping("/byType")
    public ResponseEntity getCourtType(@RequestParam(defaultValue = "tennis") String type,
                                       @RequestParam(defaultValue = "0") int numPage,
                                       @RequestParam(defaultValue = "10") int dimPage,
                                       @RequestParam(value = "sortBy", defaultValue = "name") String sortBy){
        List<Court> result = courtService.showCourtByType(type,numPage,dimPage,sortBy);
        if(result.size() <= 0)
            return new ResponseEntity("No court: "+type,HttpStatus.OK);
        return new ResponseEntity(result,HttpStatus.OK);
    }

    @GetMapping("/byCity")
    public ResponseEntity getCourtCity(@RequestParam(defaultValue = "rovito") String city,
                                       @RequestParam(defaultValue = "0") int numPage,
                                       @RequestParam(defaultValue = "10") int dimPage,
                                       @RequestParam(value = "sortBy", defaultValue = "name") String sortBy){
        List<Court> result = courtService.showCourtByCity(city,numPage,dimPage,sortBy);
        if(result.size() <= 0)
            return new ResponseEntity("No court in the city: "+city,HttpStatus.OK);
        return new ResponseEntity(result,HttpStatus.OK);
    }

    @GetMapping("/byPriceType")
    public ResponseEntity getCourtPriceType(@RequestParam(defaultValue = "10") float price,
                                            @RequestParam(defaultValue = "tennis") String type,
                                            @RequestParam(defaultValue = "0") int numPage,
                                            @RequestParam(defaultValue = "10") int dimPage,
                                            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy){
        List<Court> result = courtService.showByPriceAndType(price,type,numPage,dimPage,sortBy);
        if(result.size() <= 0)
            return new ResponseEntity("No court with price: "+price+ ", and type: "+type,HttpStatus.OK);
        return new ResponseEntity(result,HttpStatus.OK);
    }

    @GetMapping("/byCityType")
    public ResponseEntity getCourtCityType(@RequestParam(defaultValue = "rovito") String city,
                                            @RequestParam(defaultValue = "tennis") String type,
                                            @RequestParam(defaultValue = "0") int numPage,
                                            @RequestParam(defaultValue = "10") int dimPage,
                                            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy){
        List<Court> result = courtService.showByCityAndType(city,type,numPage,dimPage,sortBy);
        if(result.size() <= 0)
            return new ResponseEntity("No court in: "+city+ ", and type: "+type,HttpStatus.OK);
        return new ResponseEntity(result,HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity getAll(@RequestParam(defaultValue = "0") int numPage,
                                 @RequestParam(defaultValue = "10") int dimPage,
                                 @RequestParam(value = "sortBy", defaultValue = "name") String sortBy){
        List<Court> result = courtService.showAllCourts(numPage,dimPage,sortBy);
        if(result.size() <= 0)
            return new ResponseEntity("No court registered in the system",HttpStatus.OK);
        return new ResponseEntity(result,HttpStatus.OK);
    }

}
