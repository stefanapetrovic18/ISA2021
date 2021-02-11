package rs.apoteka.service.impl.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.apoteka.entity.business.VacationRequest;
import rs.apoteka.repository.business.VacationRequestRepository;
import rs.apoteka.service.intf.auth.AuthenticationService;
import rs.apoteka.service.intf.business.VacationRequestService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VacationRequestServiceImpl implements VacationRequestService {
    @Autowired
    private VacationRequestRepository vacationRequestRepository;
    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public List<VacationRequest> findAll() {
        return vacationRequestRepository.findAll();
    }

    @Override
    public List<VacationRequest> findAllParametrized(Long id, LocalDateTime startDate, LocalDateTime endDate, Long pharmacyID,
                                                     Long employeeID, Boolean accepted) {
        List<VacationRequest> vacationRequests = findAll();
        if (id != null) {
            vacationRequests.removeIf(p -> !p.getId().equals(id));
        }
        if (pharmacyID != null) {
            vacationRequests.removeIf(p -> !p.getPharmacy().getId().equals(pharmacyID));
        }
        if (employeeID != null) {
            vacationRequests.removeIf(p -> !p.getEmployeeID().equals(employeeID));
        }
        if (accepted != null) {
            vacationRequests.removeIf(p -> !p.getAccepted().equals(accepted));
        }
        if (startDate != null) {
            vacationRequests.removeIf(p -> p.getVacationStart().isBefore(startDate));
        }
        if (endDate != null) {
            vacationRequests.removeIf(p -> p.getVacationEnd().isAfter(endDate));
        }
        return vacationRequests;
    }

    @Override
    public VacationRequest getOne(Long id) {
        return vacationRequestRepository.getOne(id);
    }

    @Override
    public VacationRequest create(VacationRequest vacationRequest) {
        if (!checkDates(vacationRequest)) {
            return null;
        }
        vacationRequest.setAccepted(false);
        return vacationRequestRepository.save(vacationRequest);
    }

    @Override
    public VacationRequest update(VacationRequest vacationRequest) throws Exception {
        if (!checkDates(vacationRequest)) {
            return null;
        }
        if (!authenticationService.getAuthorities().contains("ROLE_PHARMACY_ADMIN") && vacationRequest.getAccepted()) {
            throw new Exception("Jedino administrator apoteke prihvata zahteve za odmor/odsustvo!");
        }
        return vacationRequestRepository.save(vacationRequest);
    }

    @Override
    public VacationRequest accept(VacationRequest vacationRequest) throws Exception {
        if (!checkDates(vacationRequest)) {
            return null;
        }
        if (!authenticationService.getAuthorities().contains("ROLE_PHARMACY_ADMIN")) {
            throw new Exception("Jedino administrator apoteke prihvata zahteve za odmor/odsustvo!");
        }
        vacationRequest.setAccepted(true);
        return update(vacationRequest);
    }

    @Override
    public VacationRequest reject(VacationRequest vacationRequest) throws Exception {
        if (!checkDates(vacationRequest)) {
            return null;
        }
        if (!authenticationService.getAuthorities().contains("ROLE_PHARMACY_ADMIN")) {
            throw new Exception("Jedino administrator apoteke prihvata zahteve za odmor/odsustvo!");
        }
        vacationRequest.setAccepted(false);
        return update(vacationRequest);
    }

    private Boolean checkDates(VacationRequest vacationRequest) {
        return vacationRequest.getVacationStart().isBefore(vacationRequest.getVacationEnd());
    }

    @Override
    public Boolean delete(Long id) {
        vacationRequestRepository.deleteById(id);
        return true;
    }
}
