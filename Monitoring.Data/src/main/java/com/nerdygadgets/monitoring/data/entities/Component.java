package com.nerdygadgets.monitoring.data.entities;

import com.nerdygadgets.monitoring.data.enums.ComponentName;
import com.nerdygadgets.monitoring.data.enums.ComponentType;

import javax.persistence.*;
import java.util.Set;

/**
 * This entity class is used for hardware components
 * (see the ERD scheme in the Technical Design document for a better overview)
 */
@Entity
@Table(name = "component")
public class Component extends BaseEntity {

    @OneToMany(
            mappedBy = "design",
            orphanRemoval = true,
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private Set<DesignComponent> designComponents;

    @OneToMany(
            mappedBy = "component",
            orphanRemoval = true,
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private Set<ComponentDetail> componentDetails;

    @Enumerated(EnumType.STRING)
    private ComponentName name;
    @Enumerated(EnumType.STRING)
    private ComponentType type;
    private float availability;
    private float cost;

    public Set<DesignComponent> getDesignComponents() {
        return designComponents;
    }

    public void setDesignComponents(Set<DesignComponent> designComponents) {
        this.designComponents = designComponents;
    }

    public Set<ComponentDetail> getComponentDetails() {
        return componentDetails;
    }

    public void setComponentDetails(Set<ComponentDetail> componentDetails) {
        this.componentDetails = componentDetails;
    }

    public ComponentName getName() {
        return name;
    }

    public void setName(ComponentName name) {
        this.name = name;
    }

    public ComponentType getType() {
        return type;
    }

    public void setType(ComponentType type) {
        this.type = type;
    }

    public float getAvailability() {
        return availability;
    }

    public void setAvailability(float availability) {
        this.availability = availability;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }
}
