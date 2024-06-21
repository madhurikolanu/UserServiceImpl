package com.apis.repositories;

import com.apis.models.Token;
import com.apis.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    @Override
    Token save(Token token);

    Optional<Token> findByValueAndDeleted(String tokenValue, boolean Deleted);

    Optional<Token> findByValueAndDeletedAndExpiryAtGreaterThan(String tokenValue, boolean Deleted, Date expiryAt);
}
