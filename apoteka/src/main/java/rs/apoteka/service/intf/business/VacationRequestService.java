package rs.apoteka.service.intf.business;

import rs.apoteka.entity.business.VacationRequest;

import java.time.LocalDateTime;
import java.util.List;

public interface VacationRequestService {
    List<VacationRequest> findAll();

    List<VacationRequest> findAllParametrized(Long id, Long vacationRequestItemID, Long supplierID);

    VacationRequest getOne(Long id);

    VacationRequest create(VacationRequest vacationRequest);

    VacationRequest update(VacationRequest vacationRequest);

    Boolean delete(Long id);
}
