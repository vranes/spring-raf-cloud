package rs.raf.demo.responses;

import lombok.Data;
import rs.raf.demo.model.Permissions;

@Data
public class LoginResponse {
    private String jwt;
    private String permissions;

    public LoginResponse(String jwt, String permissions) {
        this.jwt = jwt;
        this.permissions = permissions;
    }
}
