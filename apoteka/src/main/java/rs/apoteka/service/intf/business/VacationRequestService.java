package rs.apoteka.service.intf.business;

import rs.apoteka.entity.business.VacationRequest;

import java.time.LocalDateTime;
import java.util.List;

public interface VacationRequestService {
    List<VacationRequest> findAll();

    List<VacationRequest> findAllParametrized(Long id, LocalDateTime startDate, LocalDateTime endDate, Long pharmacyID,
                                              Long employeeID, Boolean accepted);

    VacationRequest getOne(Long id);

    VacationRequest create(VacationRequest vacationRequest);

    VacationRequest update(VacationRequest vacationRequest) throws Exception;

    VacationRequest accept(VacationRequest vacationRequest) throws Exception;

    VacationRequest reject(VacationRequest vacationRequest) throws Exception;

    Boolean delete(Long id);
}
