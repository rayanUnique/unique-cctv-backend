package com.uniquecctv.backend.service;

import com.uniquecctv.backend.entity.Project;
import com.uniquecctv.backend.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    
    @Autowired
    private ProjectRepository projectRepository;
    
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }
    
    public List<Project> getActiveProjects() {
        return projectRepository.findByIsActiveTrue();
    }
    
    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }
    
    public Project createProject(Project project) {
        return projectRepository.save(project);
    }
    
    public Project updateProject(Long id, Project projectDetails) {
        Optional<Project> optionalProject = projectRepository.findById(id);
        
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            
            // Update fields if provided
            if (projectDetails.getTitle() != null) {
                project.setTitle(projectDetails.getTitle());
            }
            if (projectDetails.getType() != null) {
                project.setType(projectDetails.getType());
            }
            if (projectDetails.getDescription() != null) {
                project.setDescription(projectDetails.getDescription());
            }
            if (projectDetails.getImage() != null) {
                project.setImage(projectDetails.getImage());
            }
            if (projectDetails.getIsActive() != null) {
                project.setIsActive(projectDetails.getIsActive());
            }
            
            project.setUpdatedAt();
            return projectRepository.save(project);
        }
        return null;
    }
    
    public boolean deleteProject(Long id) {
        if (projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    public Project toggleProjectStatus(Long id) {
        Optional<Project> optionalProject = projectRepository.findById(id);
        
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            project.setIsActive(!project.getIsActive());
            project.setUpdatedAt();
            return projectRepository.save(project);
        }
        return null;
    }
    
    public List<Project> getProjectsByType(String type) {
        return projectRepository.findByTypeAndIsActiveTrue(type);
    }
    
    public long getActiveProjectsCount() {
        return projectRepository.countByIsActiveTrue();
    }
}