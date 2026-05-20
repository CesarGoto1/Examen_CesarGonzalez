package ec.edu.espe.springlab.repository;

import ec.edu.espe.springlab.domain.Device;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    //Buscar estudiante por serial
    Optional<Device> findBySerialAndDeletedFalse(String serial);

    //Respuesta si existe al menos un registro
    boolean existsBySerialAndDeletedFalse(String serial);

    List<Device> findAllByDeletedFalse();

    Optional<Device> findByIdAndDeletedFalse(Long id);

    long countByDeletedFalse();

    long countByActiveTrueAndDeletedFalse();

    long countByActiveFalseAndDeletedFalse();

    List<Device> findByNameContainingIgnoreCaseAndDeletedFalse(String name);

    List<Device> findByStockLessThanAndDeletedFalse(int threshold);
}
