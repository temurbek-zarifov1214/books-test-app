package com.apptest.book.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    Optional<User> findByIdAndDeletedAtIsNull(Integer id);

    @Query(value = " select * from users ",
            nativeQuery = true)
    List<User> getAllUSers();
}
