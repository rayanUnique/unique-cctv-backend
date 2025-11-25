package com.uniquecctv.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uniquecctv.backend.entity.Appointment;

import java.util.Date;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    
    List<Appointment> findByEmail(String email);
    
    List<Appointment> findByAppointmentDate(Date appointmentDate);
    
    List<Appointment> findByMobNo(String mobNo);
    
    List<Appointment> findByStatus(String status);
    
    List<Appointment> findByStatusOrderByAppointmentDateAsc(String status);
    
    // NEW METHOD: Find appointments by selected service
    List<Appointment> findBySelectedService(String selectedService);
    
    // NEW METHOD: Find appointments by service and status
    List<Appointment> findBySelectedServiceAndStatus(String selectedService, String status);
    
    // NEW METHOD: Find appointments by service and date
    List<Appointment> findBySelectedServiceAndAppointmentDate(String selectedService, Date appointmentDate);
    
    // NEW METHOD: Find appointments by service, date, and status
    List<Appointment> findBySelectedServiceAndAppointmentDateAndStatus(String selectedService, Date appointmentDate, String status);
    
    // NEW METHOD: Find appointments by service and order by date
    List<Appointment> findBySelectedServiceOrderByAppointmentDateAsc(String selectedService);
    
    // NEW METHOD: Count appointments by service type
    long countBySelectedService(String selectedService);
    
    // NEW METHOD: Count appointments by service and status
    long countBySelectedServiceAndStatus(String selectedService, String status);
    
    long countByStatus(String status);
    
    // REMOVED: findDistinctSelectedService() method that was causing the error
}