package org.bidwik.bid.EntityRepository;

import java.util.UUID;

import org.bidwik.bid.Entity.Bid;
import org.bidwik.bid.Entity.Deal;
import org.bidwik.bid.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface DealRepository extends JpaRepository<Deal, UUID>{
    List<Deal> findByWinner(User winner);
    Optional<Deal> findByBid(Bid bid);
}
