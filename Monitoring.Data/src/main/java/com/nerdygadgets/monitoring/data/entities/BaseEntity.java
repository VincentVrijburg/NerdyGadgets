package com.nerdygadgets.monitoring.data.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@MappedSuperclass
public abstract class BaseEntity
{
    @Id
    @Column(length = 36)
    private String id;

    private Date updated;
    private Date created;

    public BaseEntity() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() { return id; }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date date) {
        this.updated = date;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date date) {
        this.created = date;
    }
}
