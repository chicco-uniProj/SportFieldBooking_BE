package com.prog.progettopsw.support.exception;

public class EmailAlreadyUsedException extends Exception{

    public EmailAlreadyUsedException(){
        System.out.println("email gia in uso da un altro utente");
    }
}
