package rs.apoteka.service.intf.user;

import rs.apoteka.entity.user.SystemAdmin;

import java.util.List;

public interface SystemAdminService {
    List<SystemAdmin> findAll();

    SystemAdmin getOne(Long id);

    SystemAdmin create(SystemAdmin systemAdmin);

    SystemAdmin update(SystemAdmin systemAdmin);

    Boolean delete(Long id);
}
