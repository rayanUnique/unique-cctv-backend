package com.uniquecctv.backend.repository;

import com.uniquecctv.backend.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    
    List<Project> findByIsActiveTrue();
    
    List<Project> findByTypeAndIsActiveTrue(String type);
    
    List<Project> findByIsActive(Boolean isActive);
    
    long countByIsActiveTrue();
}