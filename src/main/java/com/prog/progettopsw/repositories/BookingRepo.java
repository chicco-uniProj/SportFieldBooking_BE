package com.prog.progettopsw.repositories;

import com.prog.progettopsw.entities.Booking;
import com.prog.progettopsw.entities.Court;
import com.prog.progettopsw.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface BookingRepo extends JpaRepository<Booking,Integer> {

    Booking findById(int id);
    boolean existsByCourtAndData(Court court, String data); //controllo se il campetto è occupato in quell'ora, se esiste una entry allora è occupato

    List<Booking>findByBuyer(User buyer);
    Page<Booking> findByBuyer(User buyer, Pageable p);

    List<Booking>findAll();
    Page<Booking>findAll(Pageable p);

    @Query("SELECT b from Booking b WHERE b.purchaseTime > ?1 AND b.purchaseTime < ?2 AND b.buyer = ?3")
    List<Booking> findByBuyerInPeriod(Date startDate,Date endDate, User buyer);
    @Query("SELECT b from Booking b WHERE b.purchaseTime > ?1 AND b.purchaseTime < ?2 AND b.buyer = ?3")
    Page<Booking> findByBuyerInPeriod(Date startDate,Date endDate, User buyer,Pageable p);


}
