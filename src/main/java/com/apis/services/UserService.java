package com.apis.services;

import com.apis.dtos.SendEmailRequestDto;
import com.apis.models.Token;
import com.apis.models.User;
import com.apis.repositories.TokenRepository;
import com.apis.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private TokenRepository tokenRepository;
    private KafkaTemplate<String, String> kafkaTemplate;
    private ObjectMapper objectMapper;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                TokenRepository tokenRepository, KafkaTemplate kafkaTemplate, ObjectMapper objectMapper){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenRepository = tokenRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public User save(String name, String email, String password){
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setHashedPassword(bCryptPasswordEncoder.encode(password));

        SendEmailRequestDto sendEmailRequestDto = new SendEmailRequestDto();
        sendEmailRequestDto.setTo(user.getEmail());
        sendEmailRequestDto.setFrom("");
        sendEmailRequestDto.setSubject("Welcome back !!!");
        sendEmailRequestDto.setBody("Hope you're doing great");

        try{
            kafkaTemplate.send(
                    "sendEmail",
                    objectMapper.writeValueAsString(sendEmailRequestDto)
            );
        }
        catch (Exception e){
            System.out.println("exception occurred ..." + e);
        }
        return userRepository.save(user);
    }

    public Token login(String email, String password){
       Optional<User> optionalUser =  userRepository.findByEmail(email);
       if(optionalUser.isEmpty()){
           return null;
       }
       User user = optionalUser.get();
       if(!bCryptPasswordEncoder.matches(password, user.getHashedPassword())){
           return null;
       }
       Token token = generateToken(user);
       tokenRepository.save(token);
       return token;
    }

    private Token generateToken(User user){
        LocalDate currentDate = LocalDate.now();
        LocalDate dateAfter30days = currentDate.plusDays(30);

        Date expiryDate = Date.from(dateAfter30days.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Token token = new Token();
        token.setUser(user);
        token.setExpiryAt(expiryDate);
        token.setDeleted(false);
        token.setValue(RandomStringUtils.randomAlphanumeric(128));
        return token;
    }

    public void logout(String tokenValue){
        Optional<Token> optionalToken = tokenRepository.findByValueAndDeleted(tokenValue, false);
        if(optionalToken.isEmpty()){
            return;
        }
        Token dbToken = optionalToken.get();
        dbToken.setDeleted(true);
        tokenRepository.save(dbToken);
    }

    public User validateToken(String tokenValue){
        Optional<Token> optionalToken = tokenRepository.findByValueAndDeletedAndExpiryAtGreaterThan(tokenValue, false, new Date());
        if(optionalToken.isEmpty()){
            return null;
        }
        Token dbToken = optionalToken.get();
        return dbToken.getUser();
    }

}
