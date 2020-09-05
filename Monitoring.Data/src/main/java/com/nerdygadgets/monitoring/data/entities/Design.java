package com.nerdygadgets.monitoring.data.entities;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * This entity class is used for generated designs in the GUI application
 * (see the ERD scheme in the Technical Design document for a better overview)
 */
@Entity
@Table(name = "design")
public class Design extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(
            mappedBy = "design",
            orphanRemoval = true,
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private Set<DesignComponent> designComponents;

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<DesignComponent> getDesignComponents() {
        return designComponents;
    }

    public void setDesignComponents(Set<DesignComponent> designComponents) {
        this.designComponents = designComponents;
    }
}
