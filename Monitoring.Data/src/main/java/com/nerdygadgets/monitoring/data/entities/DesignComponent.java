package com.nerdygadgets.monitoring.data.entities;

import javax.persistence.*;

/**
 * This entity class is used for the hardware components within the generated designs
 * (see the ERD scheme in the Technical Design document for a better overview)
 */
@Entity
@Table(name = "designcomponent")
public class DesignComponent extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "designId")
    private Design design;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "componentId")
    private Component component;

    public Design getDesign() {
        return this.design;
    }

    public void setDesign(Design design) {
        this.design = design;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }
}
