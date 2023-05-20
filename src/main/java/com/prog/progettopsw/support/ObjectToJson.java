package com.prog.progettopsw.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prog.progettopsw.entities.Booking;
import com.prog.progettopsw.entities.Court;
import com.prog.progettopsw.entities.User;
import com.prog.progettopsw.repositories.CourtRepo;
import com.prog.progettopsw.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

public class ObjectToJson {

    public static void main(String[]args){

        ObjectToJson o=new ObjectToJson();
        Booking booking=new Booking();

        booking=getObjectData(booking);
        // Creating Object of ObjectMapper define in Jackson API
        ObjectMapper Obj = new ObjectMapper();
        try {
            // Converting the Java object into a JSON string
            String jsonStr = Obj.writeValueAsString(booking);
            // Displaying Java object into a JSON string
            System.out.println(jsonStr);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Getting data that we want to insert into an object
    public static Booking getObjectData(Booking booking)
    {
        User user=new User();
        user.setId(5);
        user.setEmail("zchicco1@gmail.com");
        user.setFirstName("francesco");
        user.setLastName("zumpano");
        user.setUserName("chicco1501");
        Court court=new Court();
        court.setId(17);
        court.setName("pianette");
        court.setType("calcetto");
        court.setCity("rovito");

        booking.setCourt(court);
        booking.setBuyer(user);
        booking.setData("20082219");
        // Returning the product object
        return booking;
    }


}
