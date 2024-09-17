package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.models.DBUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<DBUser, Long> {
    Optional<DBUser> findByEmail(String email);
}
