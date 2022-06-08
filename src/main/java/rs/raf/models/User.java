package rs.raf.models;


import org.apache.commons.codec.digest.DigestUtils;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class User {

    private Integer id;
    @NotNull(message = "email can't be null")
    @NotEmpty(message = "email cant be empty")
    private String email;
    @NotNull(message = "name can't be null")
    @NotEmpty(message = "name cant be empty")
    private String name;
    @NotNull(message = "lastname can't be null")
    @NotEmpty(message = "lastname cant be empty")
    private String lastname;
    @NotNull(message = "role can't be null")
    @NotEmpty(message = "role cant be empty")
    private String role;
    @NotNull(message = "status can't be null")
    @NotEmpty(message = "status cant be empty")
    private String status;                              //Active NotActive
    @NotNull(message = "password can't be null")
    @NotEmpty(message = "password cant be empty")
    private String password;

    public User() {
    }

    public User(Integer id, String email, String name, String lastname, String role, String status, String password) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.lastname = lastname;
        this.role = role;
        this.status = status;
        this.password = password;
    }

    public String getPassHash() {
        return DigestUtils.sha256Hex(this.password);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}