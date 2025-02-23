package org.bidwik.bid.EntityRepository;

import java.util.UUID;

import org.bidwik.bid.Entity.Category;
import org.bidwik.bid.Entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.bidwik.bid.Enum.ECondition;
import org.bidwik.bid.Entity.User;


@Repository
public interface ItemRepository extends JpaRepository<Item, UUID> {
    List<Item> findBySeller(User seller);
    List<Item> findByCategory(Category category);
    List<Item> findBySellerAndCondition(User seller, ECondition condition);
}
