package rs.apoteka.entity.business;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
public class VacationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long employeeID;
    @ManyToOne
    private Pharmacy pharmacy;
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime vacationStart;
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime vacationEnd;
    @Column
    private Boolean accepted;

    public VacationRequest() {
    }

    public Long getId() {
        return id;
    }

    public Long getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Long employeeID) {
        this.employeeID = employeeID;
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }

    public LocalDateTime getVacationStart() {
        return vacationStart;
    }

    public void setVacationStart(LocalDateTime vacationStart) {
        this.vacationStart = vacationStart;
    }

    public LocalDateTime getVacationEnd() {
        return vacationEnd;
    }

    public void setVacationEnd(LocalDateTime vacationEnd) {
        this.vacationEnd = vacationEnd;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }
}
