package com.mokal.jpa.repository;

import com.mokal.jpa.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByTitle(String title);

    List<Product> findByCreatedAtAfter(LocalDateTime after);

    List<Product> findByQuantityAndPrice(Integer i, BigDecimal bigDecimal);

    List<Product> findByTitleLike(String title);

    //see methods contaim=nig ignore on docs

//    @Query("select e.title from Product e where e.title=:title and e.price=:price")
//    Optional<Product> findByTitleAndPrice(String title, BigDecimal price);

    @Query("select e from Product e where e.title=:title and e.price=:price")
    Optional<Product> findByTitleAndPrice(String title, BigDecimal price);

    //3.4
    List<Product> findByTitleOrderByPrice(String title);

    List<Product> findByOrderByPrice();

    List<Product> findBy(String title, Pageable pageable);

    List<Product> findByTitleContainingIgnoreCase(String title, Pageable pageable);

}
