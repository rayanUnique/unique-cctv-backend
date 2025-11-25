package com.uniquecctv.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.uniquecctv.backend.dto.AppointmentRequestDTO;
import com.uniquecctv.backend.entity.Appointment;
import com.uniquecctv.backend.service.AppointmentService;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "*")
public class AppointmentController {
    
    @Autowired
    private AppointmentService appointmentService;
    
    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        try {
            List<Appointment> appointments = appointmentService.getAllAppointments();
            System.out.println("📋 Retrieved " + appointments.size() + " appointments");
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            System.out.println("❌ Error fetching appointments: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) {
        try {
            Optional<Appointment> appointment = appointmentService.getAppointmentById(id);
            if (appointment.isPresent()) {
                System.out.println("✅ Found appointment with ID: " + id + " - Service: " + appointment.get().getSelectedService());
                return ResponseEntity.ok(appointment.get());
            } else {
                System.out.println("❌ Appointment not found with ID: " + id);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.out.println("❌ Error fetching appointment: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@RequestBody AppointmentRequestDTO appointmentDTO) {
        try {
            // Convert DTO to Entity
            Appointment appointment = new Appointment();
            appointment.setName(appointmentDTO.getName());
            appointment.setMobNo(appointmentDTO.getMobNo());
            appointment.setEmail(appointmentDTO.getEmail());
            appointment.setAppointmentDate(appointmentDTO.getAppointmentDate());
            appointment.setAppointmentTime(appointmentDTO.getAppointmentTime());
            appointment.setAddress(appointmentDTO.getAddress());
            appointment.setDescription(appointmentDTO.getDescription());
            appointment.setSelectedService(appointmentDTO.getSelectedService());
            
            System.out.println("📝 Creating new appointment for service: " + appointmentDTO.getSelectedService());
            
            // Then save the entity
            Appointment savedAppointment = appointmentService.createAppointment(appointment);
            return new ResponseEntity<>(savedAppointment, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("❌ Error creating appointment: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable Long id, 
                                                        @RequestBody Appointment appointmentDetails) {
        try {
            System.out.println("🔄 Updating appointment with ID: " + id + " - New Service: " + appointmentDetails.getSelectedService());
            Appointment updatedAppointment = appointmentService.updateAppointment(id, appointmentDetails);
            if (updatedAppointment != null) {
                System.out.println("✅ Appointment updated successfully");
                return ResponseEntity.ok(updatedAppointment);
            } else {
                System.out.println("❌ Appointment not found for update with ID: " + id);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.out.println("❌ Error updating appointment: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @PatchMapping("/{id}/status")
    public ResponseEntity<Appointment> updateAppointmentStatus(@PathVariable Long id, 
                                                              @RequestBody Map<String, String> statusUpdate) {
        try {
            String newStatus = statusUpdate.get("status");
            System.out.println("🔄 Updating appointment " + id + " status to: " + newStatus);
            
            if (newStatus == null || newStatus.trim().isEmpty()) {
                System.out.println("❌ Status cannot be null or empty");
                return ResponseEntity.badRequest().build();
            }
            
            Appointment updatedAppointment = appointmentService.updateAppointmentStatus(id, newStatus);
            if (updatedAppointment != null) {
                System.out.println("✅ Appointment status updated successfully - Service: " + updatedAppointment.getSelectedService());
                return ResponseEntity.ok(updatedAppointment);
            } else {
                System.out.println("❌ Appointment not found for status update with ID: " + id);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.out.println("❌ Error updating appointment status: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        try {
            System.out.println("🗑️ Deleting appointment with ID: " + id);
            boolean deleted = appointmentService.deleteAppointment(id);
            if (deleted) {
                System.out.println("✅ Appointment deleted successfully");
                return ResponseEntity.noContent().build();
            } else {
                System.out.println("❌ Appointment not found for deletion with ID: " + id);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.out.println("❌ Error deleting appointment: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/email/{email}")
    public ResponseEntity<List<Appointment>> getAppointmentsByEmail(@PathVariable String email) {
        try {
            System.out.println("📧 Fetching appointments for email: " + email);
            List<Appointment> appointments = appointmentService.getAppointmentsByEmail(email);
            System.out.println("✅ Found " + appointments.size() + " appointments for email: " + email);
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            System.out.println("❌ Error fetching appointments by email: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/date/{date}")
    public ResponseEntity<List<Appointment>> getAppointmentsByDate(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        try {
            System.out.println("📅 Fetching appointments for date: " + date);
            List<Appointment> appointments = appointmentService.getAppointmentsByDate(date);
            System.out.println("✅ Found " + appointments.size() + " appointments for date: " + date);
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            System.out.println("❌ Error fetching appointments by date: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/mobile/{mobNo}")
    public ResponseEntity<List<Appointment>> getAppointmentsByMobile(@PathVariable String mobNo) {
        try {
            System.out.println("📱 Fetching appointments for mobile: " + mobNo);
            List<Appointment> appointments = appointmentService.getAppointmentsByMobile(mobNo);
            System.out.println("✅ Found " + appointments.size() + " appointments for mobile: " + mobNo);
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            System.out.println("❌ Error fetching appointments by mobile: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    
    // NEW ENDPOINTS FOR SERVICE-BASED QUERIES
    
    @GetMapping("/service/{service}")
    public ResponseEntity<List<Appointment>> getAppointmentsByService(@PathVariable String service) {
        try {
            System.out.println("🔧 Fetching appointments for service: " + service);
            List<Appointment> appointments = appointmentService.getAppointmentsByService(service);
            System.out.println("✅ Found " + appointments.size() + " appointments for service: " + service);
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            System.out.println("❌ Error fetching appointments by service: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/service/{service}/status/{status}")
    public ResponseEntity<List<Appointment>> getAppointmentsByServiceAndStatus(
            @PathVariable String service, @PathVariable String status) {
        try {
            System.out.println("🔧 Fetching appointments for service: " + service + " with status: " + status);
            List<Appointment> appointments = appointmentService.getAppointmentsByService(service)
                    .stream()
                    .filter(appt -> status.equals(appt.getStatus()))
                    .toList();
            System.out.println("✅ Found " + appointments.size() + " appointments for service: " + service + " with status: " + status);
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            System.out.println("❌ Error fetching appointments by service and status: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/services/available")
    public ResponseEntity<String[]> getAvailableServices() {
        try {
            String[] services = appointmentService.getAvailableServices();
            System.out.println("📋 Retrieved " + services.length + " available services");
            return ResponseEntity.ok(services);
        } catch (Exception e) {
            System.out.println("❌ Error fetching available services: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/services/stats")
    public ResponseEntity<Map<String, Long>> getServiceStatistics() {
        try {
            System.out.println("📊 Generating service statistics");
            String[] services = appointmentService.getAvailableServices();
            Map<String, Long> stats = new java.util.HashMap<>();
            
            for (String service : services) {
                long count = appointmentService.getAppointmentsByService(service).size();
                stats.put(service, count);
            }
            
            System.out.println("✅ Generated statistics for " + services.length + " services");
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            System.out.println("❌ Error generating service statistics: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    
    // Optional: Add endpoint to get today's appointments
    @GetMapping("/today")
    public ResponseEntity<List<Appointment>> getTodaysAppointments() {
        try {
            Date today = new Date();
            System.out.println("📅 Fetching today's appointments");
            List<Appointment> appointments = appointmentService.getAppointmentsByDate(today);
            System.out.println("✅ Found " + appointments.size() + " appointments for today");
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            System.out.println("❌ Error fetching today's appointments: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}