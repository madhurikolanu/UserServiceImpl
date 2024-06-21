package com.apis.repositories;

import com.apis.dtos.LoginRequestDto;
import com.apis.dtos.UserDto;
import com.apis.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Override
    User save(User user);

    Optional<User> findByEmail(String email);
}
