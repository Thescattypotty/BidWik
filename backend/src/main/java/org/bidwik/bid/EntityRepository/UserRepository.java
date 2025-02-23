package org.bidwik.bid.EntityRepository;

import java.util.UUID;

import org.bidwik.bid.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, UUID>{
    Optional<User> findByEmail(String email);
}
