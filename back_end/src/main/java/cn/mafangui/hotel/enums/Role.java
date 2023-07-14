package cn.mafangui.hotel.enums;

public enum Role {
    ADMIN("admin", "Quản trị viên"),
    OPERATOR("operator", "Người điều hành")
    ;

    private String value;
    private String role;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    Role(String value, String role) {
        this.value = value;
        this.role = role;
    }

}
