package cn.mafangui.hotel.enums;

public enum OrderStatus {
    WAS_DELETED(-3, "Đã bị xóa"),
    OVERTIME(-2, "Quá thời gian thanh toán"),
    WAS_CANCELED(-1, "Đã hủy"),
    UNPAID(0, "Chưa thanh toán"),
    PAID(1, "Đợi nhận phòng"),
    CHECK_IN(2, "Đã nhận phòng")
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

    OrderStatus(int code, String status) {
        this.code = code;
        this.status = status;
    }
}
