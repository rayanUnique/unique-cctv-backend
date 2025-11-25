package com.uniquecctv.backend.dto;

import java.util.Date;

public class AppointmentRequestDTO {
    private String name;
    private String mobNo;
    private String email;
    private Date appointmentDate;
    private String appointmentTime;
    private String address;
    private String description;
    private String selectedService;
    
    // Constructors
    public AppointmentRequestDTO() {}
    
    public AppointmentRequestDTO(String name, String mobNo, String email, Date appointmentDate, 
                                String appointmentTime, String address, String description, String selectedService) {
        this.name = name;
        this.mobNo = mobNo;
        this.email = email;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.address = address;
        this.description = description;
        this.selectedService = selectedService;
    }
    
    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getMobNo() { return mobNo; }
    public void setMobNo(String mobNo) { this.mobNo = mobNo; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public Date getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(Date appointmentDate) { this.appointmentDate = appointmentDate; }
    
    public String getAppointmentTime() { return appointmentTime; }
    public void setAppointmentTime(String appointmentTime) { this.appointmentTime = appointmentTime; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getSelectedService() { return selectedService; }
    public void setSelectedService(String selectedService) { this.selectedService = selectedService; }
    
    // Optional: Helper method to get available services (consistent with Entity)
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
    
    @Override
    public String toString() {
        return "AppointmentRequestDTO{" +
                "name='" + name + '\'' +
                ", mobNo='" + mobNo + '\'' +
                ", email='" + email + '\'' +
                ", appointmentDate=" + appointmentDate +
                ", appointmentTime='" + appointmentTime + '\'' +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                ", selectedService='" + selectedService + '\'' +
                '}';
    }
}