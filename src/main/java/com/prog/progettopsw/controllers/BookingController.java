package com.prog.progettopsw.controllers;

import com.prog.progettopsw.entities.Booking;
import com.prog.progettopsw.entities.User;
import com.prog.progettopsw.services.BookingService;
import com.prog.progettopsw.support.exception.CourtAlreadyBookedException;
import com.prog.progettopsw.support.exception.NullBuyerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;


    @PostMapping("/add")
    public ResponseEntity addBooking(@RequestBody Booking booking){
        try{
            bookingService.addBooking(booking);
        }catch (NullBuyerException nbe){
            return new ResponseEntity("User is null", HttpStatus.BAD_REQUEST);
        }catch (CourtAlreadyBookedException cabe){
            return new ResponseEntity("court is already booked",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("court booked correctly",HttpStatus.OK);
    }

    @GetMapping("/byUser")
    public ResponseEntity getBookingByUser(@RequestBody User user,
                                           @RequestParam(defaultValue = "0") int numPage,
                                           @RequestParam(defaultValue = "10") int dimPage,
                                           @RequestParam(value = "sortBy", defaultValue = "id") String sortBy) {
        List<Booking>result = bookingService.showBookingByUser(user);
        if(result.size() <= 0)
            return new ResponseEntity("user: "+user.getEmail()+",has no booking",HttpStatus.OK);
        return new ResponseEntity(result,HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity getAll(@RequestParam(defaultValue = "0") int numPage,
                                 @RequestParam(defaultValue = "10") int dimPage,
                                 @RequestParam(value = "sortBy", defaultValue = "id") String sortBy) {
        List<Booking>result = bookingService.showAll(numPage, dimPage, sortBy);
        if(result.size() <= 0)
            return new ResponseEntity("no booking in db",HttpStatus.OK);
        return new ResponseEntity(result,HttpStatus.OK);
    }

    @GetMapping("/byBuyerInPeriod")
    public ResponseEntity getBookingByBuyerInPeriod(@RequestParam Date startDate,
                                                    @RequestParam Date endDate,
                                                    @RequestParam User user,
                                                    @RequestParam(defaultValue = "0") int numPage,
                                                    @RequestParam(defaultValue = "10") int dimPage,
                                                    @RequestParam(value = "sortBy", defaultValue = "id") String sortBy){
        List<Booking>result = bookingService.showBookingByBuyerInPeriod(startDate,endDate,user,numPage,dimPage,sortBy);
        if(result.size() <= 0)
            return new ResponseEntity("no booking in period",HttpStatus.OK);
        return new ResponseEntity(result,HttpStatus.OK);
    }

}
