package ec.edu.espe.springlab.service;

import ec.edu.espe.springlab.domain.Device;

import ec.edu.espe.springlab.dto.DeviceCreateRequest;
import ec.edu.espe.springlab.repository.DeviceRepository;

import ec.edu.espe.springlab.service.impl.DeviceServiceImpl;
import ec.edu.espe.springlab.web.advice.ConflictException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import java.time.LocalDate;

import ec.edu.espe.springlab.dto.DeviceResponse;
import ec.edu.espe.springlab.dto.DeviceStatsResponse;
import ec.edu.espe.springlab.web.advice.NotFoundException;
import org.springframework.data.domain.PageRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@Import({DeviceServiceImpl.class})
public class DeviceServiceTest {
    @Autowired
    private DeviceServiceImpl service;

    @Autowired
    private DeviceRepository repository;
    @Autowired
    private DeviceService deviceService;

    @Test
    void shouldNotAllowDuplicatedSerial(){
        // Prueba serial duplicado
        Device existing = new Device();
        existing.setName("Existing");
        existing.setCategory("TESTCAT");
        existing.setSerial("UNIQ-SER");
        existing.setStock(1);
        existing.setActive(true);
        repository.save(existing);

        DeviceCreateRequest req = new DeviceCreateRequest();
        req.setName("New User");
        req.setCategory("TESTCAT");
        req.setSerial("UNIQ-SER");
        existing.setStock(1);

        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ConflictException.class);
        assertThat(repository.findAll()).hasSize(1);
    }

    @Test
    void stockNegativo(){
        System.out.println("Ejecutando prueba de stock negativo...");
        DeviceCreateRequest req = new DeviceCreateRequest();
        req.setName("Negative Stock Device");
        req.setCategory("Test");
        req.setSerial("NEG-001");
        req.setStock(-5);
        assertThatThrownBy(() -> service.create(req))
                .isInstanceOf(Exception.class);
    }

    @Test
    void shouldDeactivateDevice() {
        // Desactivar (ahora solo cambia active=false)
        Device s = new Device();
        s.setName("Active Device");
        s.setCategory("Cat1");
        s.setSerial("ABC-123");
        s.setStock(5);
        s.setActive(true);
        s = repository.save(s);

        service.deactivate(s.getId());

        Device updated = repository.findById(s.getId()).orElseThrow();
        assertThat(updated.getActive()).isFalse();
        assertThat(updated.getDeleted()).isFalse();
    }

    @Test
    void shouldReturnCorrectStats() {
        // Estadisticas
        repository.deleteAll();
        
        Device s1 = new Device(); s1.setName("S1"); s1.setCategory("Cat1"); s1.setSerial("abc-111"); s1.setStock(1); s1.setActive(true); repository.save(s1);
        Device s2 = new Device(); s2.setName("S2"); s2.setCategory("Cat2"); s2.setSerial("abc-222"); s2.setStock(2);s2.setActive(true); repository.save(s2);
        Device s3 = new Device(); s3.setName("S3"); s3.setCategory("Cat3"); s3.setSerial("abc-333"); s3.setStock(0);s3.setActive(false); repository.save(s3);

        DeviceStatsResponse stats = service.getStats();

        assertThat(stats.getTotal()).isEqualTo(3);
        assertThat(stats.getAvailable()).isEqualTo(2);
        assertThat(stats.getUnavailable()).isEqualTo(1);
    }

    @Test
    void testLogicalDelete() {
        // 1. Crear dispositivo
        Device d = new Device();
        d.setName("Laptop");
        d.setSerial("SN-999");
        d.setCategory("Comp");
        d.setStock(5);
        d.setActive(true);
        d.setDeleted(false);
        d = repository.save(d);
        Long id = d.getId();

        service.delete(id);

        assertThatThrownBy(() -> service.getById(id))
                .isInstanceOf(NotFoundException.class);

        Device inDb = repository.findById(id).orElseThrow();
        assertThat(inDb.getDeleted()).isTrue();
        assertThat(service.list()).noneMatch(device -> device.getId().equals(id));
        DeviceStatsResponse stats = service.getStats();
        long totalBefore = repository.countByDeletedFalse();
        assertThat(stats.getTotal()).isEqualTo(totalBefore);
    }

    @Test
    void testPartialSearch() {
        repository.deleteAll();
        
        Device d1 = new Device(); d1.setName("Laptop"); d1.setSerial("S1"); d1.setCategory("C1"); d1.setStock(1); d1.setActive(true); repository.save(d1);
        Device d2 = new Device(); d2.setName("Laptop Gamer"); d2.setSerial("S2"); d2.setCategory("C2"); d2.setStock(1); d2.setActive(true); repository.save(d2);
        Device d3 = new Device(); d3.setName("Router"); d3.setSerial("S3"); d3.setCategory("C3"); d3.setStock(1); d3.setActive(true); repository.save(d3);

        java.util.List<DeviceResponse> results = service.searchByName("lap");

        assertThat(results).hasSize(2);
        assertThat(results).extracting(DeviceResponse::getName)
                .containsExactlyInAnyOrder("Laptop", "Laptop Gamer")
                .doesNotContain("Router");
    }


}
