package com.uniquecctv.backend.controller;

import com.uniquecctv.backend.dto.ProjectRequestDTO;
import com.uniquecctv.backend.entity.Project;
import com.uniquecctv.backend.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "*")
public class ProjectController {
    
    @Autowired
    private ProjectService projectService;
    
    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        try {
            List<Project> projects = projectService.getAllProjects();
            return ResponseEntity.ok(projects);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/active")
    public ResponseEntity<List<Project>> getActiveProjects() {
        try {
            List<Project> projects = projectService.getActiveProjects();
            return ResponseEntity.ok(projects);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        try {
            Optional<Project> project = projectService.getProjectById(id);
            if (project.isPresent()) {
                return ResponseEntity.ok(project.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody ProjectRequestDTO projectDTO) {
        try {
            // Convert DTO to Entity
            Project project = new Project();
            project.setTitle(projectDTO.getTitle());
            project.setType(projectDTO.getType());
            project.setDescription(projectDTO.getDescription());
            project.setImage(projectDTO.getImage()); // Store only file name
            
            Project savedProject = projectService.createProject(project);
            return new ResponseEntity<>(savedProject, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody ProjectRequestDTO projectDTO) {
        try {
            // Convert DTO to Entity
            Project projectDetails = new Project();
            projectDetails.setTitle(projectDTO.getTitle());
            projectDetails.setType(projectDTO.getType());
            projectDetails.setDescription(projectDTO.getDescription());
            projectDetails.setImage(projectDTO.getImage());
            
            Project updatedProject = projectService.updateProject(id, projectDetails);
            if (updatedProject != null) {
                return ResponseEntity.ok(updatedProject);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        try {
            boolean deleted = projectService.deleteProject(id);
            if (deleted) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @PatchMapping("/{id}/toggle-status")
    public ResponseEntity<Project> toggleProjectStatus(@PathVariable Long id) {
        try {
            Project updatedProject = projectService.toggleProjectStatus(id);
            if (updatedProject != null) {
                return ResponseEntity.ok(updatedProject);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/type/{type}")
    public ResponseEntity<List<Project>> getProjectsByType(@PathVariable String type) {
        try {
            List<Project> projects = projectService.getProjectsByType(type);
            return ResponseEntity.ok(projects);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/stats/count")
    public ResponseEntity<Long> getActiveProjectsCount() {
        try {
            long count = projectService.getActiveProjectsCount();
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}