package com.uniquecctv.backend.repository;

import com.uniquecctv.backend.entity.HomepageContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HomepageContentRepository extends JpaRepository<HomepageContent, Long> {
    Optional<HomepageContent> findFirstByOrderByIdAsc();
}