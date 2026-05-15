package ec.edu.espe.springlab.dto;

public class DeviceStatsResponse {
    private long total;
    private long available;
    private long unavailable;

    public DeviceStatsResponse(long total, long available, long unavailable) {
        this.total = total;
        this.available = available;
        this.unavailable = unavailable;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getAvailable() {
        return available;
    }

    public void setAvailable(long available) {
        this.available = available;
    }

    public long getUnavailable() {
        return unavailable;
    }

    public void setUnavailable(long unavailable) {
        this.unavailable = unavailable;
    }
}
