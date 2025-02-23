package org.bidwik.bid.EntityRepository;

import java.util.UUID;

import org.bidwik.bid.Entity.IndividualBid;
import org.bidwik.bid.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.bidwik.bid.Entity.Bid;
import java.util.List;


@Repository
public interface IndividualBidRepository extends JpaRepository<IndividualBid, UUID> {
    List<IndividualBid> findByBid(Bid bid);
    List<IndividualBid> findByUser(User user);
}
