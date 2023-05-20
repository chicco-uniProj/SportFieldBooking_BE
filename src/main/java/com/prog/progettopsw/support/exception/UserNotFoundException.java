package com.prog.progettopsw.support.exception;

public class UserNotFoundException extends Exception{

    public UserNotFoundException(){
        System.out.println("utente non esistente");
    }
}
