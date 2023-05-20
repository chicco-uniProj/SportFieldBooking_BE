package com.prog.progettopsw.controllers;

import com.prog.progettopsw.entities.User;
import com.prog.progettopsw.services.AccountingService;
import com.prog.progettopsw.support.exception.EmailAlreadyUsedException;
import com.prog.progettopsw.support.exception.UserNotFoundException;
import lombok.Data;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class AccountingController {

    @Autowired
    private AccountingService accountingService;
    @PostMapping("/add")
    public ResponseEntity create(@RequestBody @Valid UserReq userReq) {
        try {
            User added = accountingService.addUser(userReq.getUser(), userReq.getPassword());
            return new ResponseEntity(added, HttpStatus.OK);
        } catch (EmailAlreadyUsedException e) {
            return new ResponseEntity<>("User already Exists",HttpStatus.BAD_REQUEST);
        }
    }
    //@PreAuthorize("hasRole('admin')")
    @PostMapping("/delete")
    public ResponseEntity delete(@RequestParam @Valid User user){
        if(user.getEmail()!=null) {
            try {
                accountingService.deleteUserByEmail(user.getEmail());
            } catch (UserNotFoundException e) {
                return new ResponseEntity("User not found", HttpStatus.BAD_REQUEST);
            }
        }else
            return new ResponseEntity("email not found ", HttpStatus.BAD_REQUEST);

        return new ResponseEntity("User "+user.getEmail()+" succesfully deleted",HttpStatus.OK);
    }
    @PostMapping("/delete/email")
    public ResponseEntity deleteEmail(@RequestParam String email){
        try {
            accountingService.deleteUserByEmail(email);
        } catch (UserNotFoundException e) {
            return new ResponseEntity("User not found", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("User "+email+" succesfully deleted",HttpStatus.OK);
    }

    @GetMapping("/paged")
    public ResponseEntity getAll(@RequestParam(defaultValue = "0") int numPage,
                                 @RequestParam(defaultValue = "10") int dimPage,
                                 @RequestParam(value = "sortBy", defaultValue = "email") String sortBy){
        List<User>result = accountingService.showAllUsers(numPage, dimPage, sortBy);
        if(result.size() <= 0)
            return new ResponseEntity("No user registered in the system",HttpStatus.OK);
        return new ResponseEntity(result,HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity getAll(){
        List<User>result = accountingService.showAllUsers();
        if(result.size() <= 0)
            return new ResponseEntity("No user registered in the system",HttpStatus.OK);
        return new ResponseEntity(result,HttpStatus.OK);
    }

    @GetMapping("/token")
    public ResponseEntity getToken(String user,String pass) throws IOException {
        String result = "";
        HttpPost post = new HttpPost("http://localhost:8080/realms/myrealm/protocol/openid-connect/token");
        post.setHeader("Content-Type", "application/x-www-form-urlencoded");
        ArrayList<BasicNameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("grant_type", "password"));
        parameters.add(new BasicNameValuePair("client_id", "myclient"));
        parameters.add(new BasicNameValuePair("client_secret", "u5kadDfKitzeDg8yux824bRa9tvvvlWW"));
        parameters.add(new BasicNameValuePair("username", user));
        parameters.add(new BasicNameValuePair("password", pass));
        post.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {
            result = EntityUtils.toString(response.getEntity());
            return new ResponseEntity(result, HttpStatus.OK);
        }
    }
}

@Data
class UserReq{
    private User user;
    private String password;
}