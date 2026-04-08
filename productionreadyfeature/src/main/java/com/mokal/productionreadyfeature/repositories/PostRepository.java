package com.mokal.productionreadyfeature.repositories;

import com.mokal.productionreadyfeature.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
}
