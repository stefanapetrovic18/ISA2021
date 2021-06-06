package rs.apoteka.service.impl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import rs.apoteka.entity.user.SystemAdmin;
import rs.apoteka.repository.user.SystemAdminRepository;
import rs.apoteka.service.intf.user.SystemAdminService;

import java.util.List;

@Service
public class SystemAdminServiceImpl implements SystemAdminService {
    @Autowired
    private SystemAdminRepository systemAdminRepository;

    @Override
    public List<SystemAdmin> findAll() {
        return systemAdminRepository.findAll();
    }

    @Override
    public SystemAdmin getOne(Long id) {
        return systemAdminRepository.getOne(id);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
    public SystemAdmin create(SystemAdmin systemAdmin) {
        systemAdmin.setEnabled(false);
        systemAdmin.setPasswordChanged(false);
        systemAdmin.setValidated(false);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        systemAdmin.setPassword(encoder.encode(systemAdmin.getPassword()));
        return systemAdminRepository.save(systemAdmin);
    }

    @Override
    public SystemAdmin update(SystemAdmin systemAdmin) {
        return systemAdminRepository.save(systemAdmin);
    }

    @Override
    public Boolean delete(Long id) {
        systemAdminRepository.deleteById(id);
        return true;
    }
}
