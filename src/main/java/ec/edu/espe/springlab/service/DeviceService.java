package ec.edu.espe.springlab.service;

import ec.edu.espe.springlab.dto.DeviceCreateRequest;
import ec.edu.espe.springlab.dto.DeviceResponse;
import ec.edu.espe.springlab.dto.DeviceStatsResponse;
import ec.edu.espe.springlab.dto.DeviceUpdateRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DeviceService {
    //Crear un estudiante
    DeviceResponse create(DeviceCreateRequest request);
    //Buscar estudiante por ID
    DeviceResponse getById(Long id);
    List<DeviceResponse> list();
    //Cambiar el estado
    DeviceResponse deactivate(Long id);

    List<DeviceResponse> searchByName(String name);

    List<DeviceResponse> getLowStock();

    //Eliminación lógica
    void delete(Long id);

    //Obtener estadísticas
    DeviceStatsResponse getStats();




}
