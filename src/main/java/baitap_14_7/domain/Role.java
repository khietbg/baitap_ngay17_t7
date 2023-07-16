package baitap_14_7.domain;

import javax.persistence.*;

@Entity
@Table(name = "dtb_role")
public class Role {
    @Id
    private String id;
    private String name;
    private String description;


    public Role() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
