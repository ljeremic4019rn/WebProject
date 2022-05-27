package rs.raf.models;

public class JwtResponse {

    private String jwt;

    public JwtResponse() {
    }

    public JwtResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
