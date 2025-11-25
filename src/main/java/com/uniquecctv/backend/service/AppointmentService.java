package com.uniquecctv.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniquecctv.backend.entity.Appointment;
import com.uniquecctv.backend.repository.AppointmentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    
    @Autowired
    private AppointmentRepository appointmentRepository;
    
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }
    
    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }
    
    public Appointment createAppointment(Appointment appointment) {
        // Ensure default values are set
        if (appointment.getAppointmentTime() == null || appointment.getAppointmentTime().trim().isEmpty()) {
            appointment.setAppointmentTime("09:00");
        }
        if (appointment.getStatus() == null || appointment.getStatus().trim().isEmpty()) {
            appointment.setStatus("PENDING");
        }
        if (appointment.getSelectedService() == null || appointment.getSelectedService().trim().isEmpty()) {
            appointment.setSelectedService("CCTV Installation");
        }
        
        System.out.println("💾 Saving appointment: " + appointment.getName() + 
                          " for service: " + appointment.getSelectedService() + 
                          " at " + appointment.getAppointmentTime());
        return appointmentRepository.save(appointment);
    }
    
    public Appointment updateAppointment(Long id, Appointment appointmentDetails) {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(id);
        
        if (optionalAppointment.isPresent()) {
            Appointment appointment = optionalAppointment.get();
            
            // Update only if new value is provided (partial update)
            if (appointmentDetails.getName() != null) {
                appointment.setName(appointmentDetails.getName());
            }
            if (appointmentDetails.getMobNo() != null) {
                appointment.setMobNo(appointmentDetails.getMobNo());
            }
            if (appointmentDetails.getEmail() != null) {
                appointment.setEmail(appointmentDetails.getEmail());
            }
            if (appointmentDetails.getAppointmentDate() != null) {
                appointment.setAppointmentDate(appointmentDetails.getAppointmentDate());
            }
            if (appointmentDetails.getAppointmentTime() != null) {
                appointment.setAppointmentTime(appointmentDetails.getAppointmentTime());
            }
            if (appointmentDetails.getAddress() != null) {
                appointment.setAddress(appointmentDetails.getAddress());
            }
            if (appointmentDetails.getDescription() != null) {
                appointment.setDescription(appointmentDetails.getDescription());
            }
            if (appointmentDetails.getSelectedService() != null) {
                appointment.setSelectedService(appointmentDetails.getSelectedService());
            }
            if (appointmentDetails.getStatus() != null) {
                appointment.setStatus(appointmentDetails.getStatus());
            }
            
            System.out.println("🔄 Updated appointment ID: " + id + 
                              " - Service: " + appointment.getSelectedService());
            return appointmentRepository.save(appointment);
        }
        System.out.println("❌ Appointment not found for update, ID: " + id);
        return null;
    }
    
    public Appointment updateAppointmentStatus(Long id, String status) {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(id);
        
        if (optionalAppointment.isPresent()) {
            Appointment appointment = optionalAppointment.get();
            String oldStatus = appointment.getStatus();
            appointment.setStatus(status);
            
            Appointment updatedAppointment = appointmentRepository.save(appointment);
            System.out.println("✅ Updated appointment " + id + 
                              " (" + appointment.getSelectedService() + 
                              ") status from " + oldStatus + " to " + status);
            return updatedAppointment;
        }
        System.out.println("❌ Appointment not found for status update, ID: " + id);
        return null;
    }
    
    public boolean deleteAppointment(Long id) {
        if (appointmentRepository.existsById(id)) {
            Optional<Appointment> appointment = appointmentRepository.findById(id);
            String serviceInfo = appointment.map(appt -> "Service: " + appt.getSelectedService()).orElse("");
            appointmentRepository.deleteById(id);
            System.out.println("🗑️ Deleted appointment ID: " + id + " " + serviceInfo);
            return true;
        }
        System.out.println("❌ Appointment not found for deletion, ID: " + id);
        return false;
    }
    
    public List<Appointment> getAppointmentsByEmail(String email) {
        return appointmentRepository.findByEmail(email);
    }
    
    public List<Appointment> getAppointmentsByDate(java.util.Date date) {
        return appointmentRepository.findByAppointmentDate(date);
    }
    
    public List<Appointment> getAppointmentsByMobile(String mobNo) {
        return appointmentRepository.findByMobNo(mobNo);
    }
    
    // NEW METHOD: Get appointments by service type
    public List<Appointment> getAppointmentsByService(String service) {
        return appointmentRepository.findBySelectedService(service);
    }
    
    // Additional useful methods you might want to add:
    
    public List<Appointment> getAppointmentsByStatus(String status) {
        return appointmentRepository.findByStatus(status);
    }
    
    public List<Appointment> getPendingAppointments() {
        return appointmentRepository.findByStatus("PENDING");
    }
    
    public long getTotalAppointmentsCount() {
        return appointmentRepository.count();
    }
    
    public boolean existsById(Long id) {
        return appointmentRepository.existsById(id);
    }
    
    // NEW METHOD: Get available services (can be used by controllers)
    public String[] getAvailableServices() {
        return Appointment.getAvailableServices();
    }
    
    // NEW METHOD: Get appointment statistics by service
    public void printAppointmentStatistics() {
        List<Appointment> allAppointments = getAllAppointments();
        System.out.println("=== Appointment Statistics by Service ===");
        
        for (String service : getAvailableServices()) {
            long count = allAppointments.stream()
                .filter(appt -> service.equals(appt.getSelectedService()))
                .count();
            System.out.println(service + ": " + count + " appointments");
        }
        System.out.println("Total appointments: " + allAppointments.size());
    }
}