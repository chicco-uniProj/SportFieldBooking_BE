package com.prog.progettopsw.services;

import com.prog.progettopsw.entities.Booking;
import com.prog.progettopsw.entities.Court;
import com.prog.progettopsw.entities.User;
import com.prog.progettopsw.repositories.BookingRepo;
import com.prog.progettopsw.repositories.CourtRepo;
import com.prog.progettopsw.support.exception.CourtAlreadyBookedException;
import com.prog.progettopsw.support.exception.NullBuyerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BookingService {
    @Autowired
    private BookingRepo bookingRepo;
    @Autowired
    private CourtRepo courtRepo;


    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Booking addBooking(Booking booking) throws NullBuyerException,CourtAlreadyBookedException {
        if(booking.getBuyer()==null)
            throw new NullBuyerException();
        Court c = courtRepo.findById(booking.getCourt().getId());
        boolean courtIsbooked = bookingRepo.existsByCourtAndData(booking.getCourt(), booking.getData());//se esiste entry con la stessa data per lo stesso campetto questo è occupato
        if(courtIsbooked)
            throw new CourtAlreadyBookedException();

        return bookingRepo.save(booking);
    }

    @Transactional(readOnly = true,propagation = Propagation.REQUIRED)
    public List<Booking>showBookingByUser(User buyer){
        return bookingRepo.findByBuyer(buyer);
    }

    @Transactional(readOnly = true)
    public List<Booking>showBookingByUser(User user, int numPage, int dimPage, String sortby){
        Pageable page= PageRequest.of(numPage,dimPage, Sort.by(sortby));
        Page<Booking> pagined = bookingRepo.findByBuyer(user,page);
        if(pagined.hasContent())
            return pagined.getContent();
        return new ArrayList<>();
    }

    @Transactional(readOnly = true)
    public List<Booking>showAll(){
        return bookingRepo.findAll();
    }
    @Transactional(readOnly = true)
    public List<Booking>showAll(int numPage, int dimPage, String sortby){
        Pageable page= PageRequest.of(numPage,dimPage, Sort.by(sortby));
        Page<Booking> paginated = bookingRepo.findAll(page);
        if(paginated.hasContent())
            return paginated.getContent();
        return new ArrayList<>();
    }

    @Transactional(readOnly = true)
    public List<Booking>showBookingByBuyerInPeriod(Date startDate, Date endDate, User buyer){
        return bookingRepo.findByBuyerInPeriod(startDate,endDate,buyer);
    }
    @Transactional(readOnly = true)
    public List<Booking>showBookingByBuyerInPeriod(Date startDate,Date endDate, User buyer, int numPage, int dimPage, String sortby){
        Pageable page= PageRequest.of(numPage,dimPage, Sort.by(sortby));
        Page<Booking> paginated = bookingRepo.findByBuyerInPeriod(startDate,endDate,buyer,page);
        if(paginated.hasContent())
            return paginated.getContent();
        return new ArrayList<>();
    }



}
