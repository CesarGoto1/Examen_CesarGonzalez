package ec.edu.espe.springlab.service.impl;
import ec.edu.espe.springlab.domain.Device;
import ec.edu.espe.springlab.dto.DeviceCreateRequest;
import ec.edu.espe.springlab.dto.DeviceResponse;
import ec.edu.espe.springlab.dto.DeviceStatsResponse;
import ec.edu.espe.springlab.dto.DeviceUpdateRequest;

import ec.edu.espe.springlab.repository.DeviceRepository;
import ec.edu.espe.springlab.service.DeviceService;
import ec.edu.espe.springlab.web.advice.ConflictException;
import ec.edu.espe.springlab.web.advice.NotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {
    //Inyección de Dependencias
    private final DeviceRepository repo;
    public DeviceServiceImpl(DeviceRepository repo){
        this.repo = repo;
    }

    @Override
    public DeviceResponse create(DeviceCreateRequest request) {
        if (repo.existsBySerial(request.getSerial())) {
            throw new ConflictException("El serial ya esta registrado");
        }
        Device d = new Device();
        d.setName(request.getName());
        d.setCategory(request.getCategory());
        d.setSerial(request.getSerial());
        d.setStock(request.getStock());
        d.setActive(true);
        Device saved = repo.save(d);
        return toResponse(saved);
    }

    @Override
    public DeviceResponse getById(Long id) {
        Device d = repo.findById(id).orElseThrow(() -> new
                NotFoundException("No esta registrado"));
        return toResponse(d);
    }


    @Override
    public DeviceResponse deactivate(Long id) {
        Device s = repo.findById(id).orElseThrow(() -> new
                NotFoundException("Dispositivo no asoma"));
        s.setActive(false);
        return toResponse(repo.save(s));
    }

    @Override
    public List<DeviceResponse> list(){
        return repo.findAll().stream().map(
                this::toResponse).toList();

    }

    @Override
    public DeviceStatsResponse getStats() {
        long total = repo.count();
        long active = repo.countByActiveTrue();
        long inactive = total - active;
        return new DeviceStatsResponse(total, active, inactive);
    }

    //Mapeo interno Entidad -> DTO de salida
    private DeviceResponse toResponse(Device d){
        DeviceResponse r = new DeviceResponse();
        r.setId(d.getId());
        r.setName(d.getName());
        r.setCategory(d.getCategory());
        r.setSerial(d.getSerial());
        r.setStock(d.getStock());
        r.setActive(d.getActive());
        return r;
    }
}

