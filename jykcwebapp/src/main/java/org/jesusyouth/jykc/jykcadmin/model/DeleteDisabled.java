package org.jesusyouth.jykc.jykcadmin.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "delete_disabled")
public class DeleteDisabled implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer zone;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getZone() {
        return zone;
    }

    public void setZone(Integer zone) {
        this.zone = zone;
    }
}
