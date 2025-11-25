package com.uniquecctv.backend.entity;

import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "appointments")
public class Appointment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(name = "mobile_number", nullable = false)
    private String mobNo;
    
    @Column(nullable = false)
    private String email;
    
    @Column(name = "appointment_date", nullable = false)
    private Date appointmentDate;
    
    // TIME FIELD - FIXED: Changed from LocalTime to String
    @Column(name = "appointment_time")
    private String appointmentTime;
    
    @Column(name = "booking_date", nullable = false)
    private LocalDateTime bookingDate;
    
    @Column(nullable = false)
    private String address;
    
    @Column(length = 1000)
    private String description;
    
    @Column(nullable = false)
    private String status = "PENDING";
    
    // NEW FIELD: Service selection
    @Column(name = "selected_service", nullable = false)
    private String selectedService;
    
    // Constructors - FIXED
    public Appointment() {
        this.bookingDate = LocalDateTime.now();
        this.status = "PENDING";
        this.appointmentTime = "09:00"; // Default time
        this.selectedService = "CCTV Installation"; // Default service
    }
    
    // FIXED: Changed parameter type from LocalTime to String
    public Appointment(String name, String mobNo, String email, Date appointmentDate, 
                      String appointmentTime, String address, String description, String selectedService) {
        this();
        this.name = name;
        this.mobNo = mobNo;
        this.email = email;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime != null ? appointmentTime : "09:00";
        this.address = address;
        this.description = description;
        this.selectedService = selectedService != null ? selectedService : "CCTV Installation";
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getMobNo() {
        return mobNo;
    }
    
    public void setMobNo(String mobNo) {
        this.mobNo = mobNo;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public Date getAppointmentDate() {
        return appointmentDate;
    }
    
    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }
    
    // FIXED: Correct setter method
    public String getAppointmentTime() {
        return appointmentTime;
    }
    
    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime != null ? appointmentTime : "09:00";
    }
    
    public LocalDateTime getBookingDate() {
        return bookingDate;
    }
    
    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    // NEW: Getter and setter for selectedService
    public String getSelectedService() {
        return selectedService;
    }
    
    public void setSelectedService(String selectedService) {
        this.selectedService = selectedService != null ? selectedService : "CCTV Installation";
    }
    
    // Optional: Add a helper method to get available services (can be used in frontend)
    public static String[] getAvailableServices() {
        return new String[]{
            "CCTV Installation",
            "CCTV Maintenance",
            "CCTV Repair",
            "Security System Setup",
            "Home Automation",
            "Network Installation",
            "Technical Consultation"
        };
    }
    
    // Optional: Add toString method for debugging
    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mobNo='" + mobNo + '\'' +
                ", email='" + email + '\'' +
                ", appointmentDate=" + appointmentDate +
                ", appointmentTime='" + appointmentTime + '\'' +
                ", bookingDate=" + bookingDate +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                ", selectedService='" + selectedService + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}