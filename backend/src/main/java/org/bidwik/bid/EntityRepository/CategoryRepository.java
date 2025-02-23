package org.bidwik.bid.EntityRepository;

import java.util.UUID;

import org.bidwik.bid.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID>{
    Optional<Category> findByName(String name);
    List<Category> findByParent(Category parent);
}
