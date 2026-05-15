package ec.edu.espe.springlab.repository;

import ec.edu.espe.springlab.domain.Device;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    //Buscar estudiante por serial
    Optional<Device> findBySerial(String serial);

    //Respuesta si existe al menos un registro
    boolean existsBySerial(String serial);


    long countByActiveTrue();
}
