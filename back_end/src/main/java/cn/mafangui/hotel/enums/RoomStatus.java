package cn.mafangui.hotel.enums;

public enum RoomStatus {
    UNAVAILABLE(0, "Không khả dụng"),
    AVAILABLE(1, "Trống"),
    ORDERED(2, "Đã được đặt"),
    IN_USE(3, "Đang sử dụng")
    ;

    private int code;
    private String status;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    RoomStatus(int code, String status) {
        this.code = code;
        this.status = status;
    }
}
