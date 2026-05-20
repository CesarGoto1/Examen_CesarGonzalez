package ec.edu.espe.springlab.web.controller;

import ec.edu.espe.springlab.domain.Device;
import ec.edu.espe.springlab.dto.DeviceCreateRequest;
import ec.edu.espe.springlab.dto.DeviceResponse;
import ec.edu.espe.springlab.dto.DeviceStatsResponse;
import ec.edu.espe.springlab.dto.DeviceUpdateRequest;
import ec.edu.espe.springlab.service.DeviceService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cesargonzalez/devices")
public class DeviceController {
    //Inyección de Dependencias
    private final DeviceService service;

    public DeviceController(DeviceService service){
        this.service = service;
    }

    //Crear un estudiante
    @PostMapping
    public ResponseEntity<DeviceResponse> createStudent(@Valid @RequestBody DeviceCreateRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    //Obtener un estudiante por ID
    @GetMapping("/{id}")
    public ResponseEntity<DeviceResponse> getStudentById(@PathVariable Long id){
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<java.util.List<DeviceResponse>> listDevices(@RequestParam(required = false) String name){
        if (name != null) {
            return ResponseEntity.ok(service.searchByName(name));
        }
        return ResponseEntity.ok(service.list());
    }



    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<DeviceResponse> deactivateStudent(@PathVariable Long id){
        return ResponseEntity.ok(service.deactivate(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/stats")
    public ResponseEntity<DeviceStatsResponse> getStats(){
        return ResponseEntity.ok(service.getStats());
    }

    @GetMapping("/low-stock")
    public ResponseEntity<java.util.List<DeviceResponse>> getLowStock(){
        return ResponseEntity.ok(service.getLowStock());
    }

}
