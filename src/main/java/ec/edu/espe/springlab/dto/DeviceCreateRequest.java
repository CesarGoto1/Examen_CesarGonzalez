package ec.edu.espe.springlab.dto;
import jakarta.validation.constraints.*;

public class DeviceCreateRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 120, message = "El nombre debe contener entre 3 y 120 caracteres")
    private String name;

    @NotBlank(message = "La categoria es obligatoria")
    @Size(max = 120)
    private String category;

    @NotBlank(message = "El serial es obligatorio")
    @Size(max = 120)
    private String serial;

    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
