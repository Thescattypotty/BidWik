package org.bidwik.bid.EntityRepository;

import java.util.UUID;

import org.bidwik.bid.Entity.Bid;
import org.bidwik.bid.Entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import org.bidwik.bid.Enum.EStatus;

@Repository
public interface BidRepository extends JpaRepository<Bid, UUID>{
    Optional<Bid> findByItem(Item item);
    List<Bid> findByStatus(EStatus status);
}
