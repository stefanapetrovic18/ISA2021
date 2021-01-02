package rs.apoteka.service.intf.user;

import rs.apoteka.entity.user.Dermatologist;

import java.util.List;

public interface DermatologistService {
    List<Dermatologist> findAll();

    Dermatologist getOne(Long id);

    Dermatologist create(Dermatologist dermatologist);

    Dermatologist update(Dermatologist dermatologist);

    Boolean delete(Long id);
}
