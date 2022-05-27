package rs.raf.models;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Category {

    private Integer id;
    @NotNull(message = "name can't be null")
    @NotEmpty(message = "name cant be empty")
    private String name;
    @NotNull(message = "description can't be null")
    @NotEmpty(message = "description cant be empty")
    private String description;

    public Category() {
    }

    public Category(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
