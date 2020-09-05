package com.nerdygadgets.monitoring.data.entities;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Date;

/**
 * This entity class is used for the details of hardware components
 * (see the ERD scheme in the Technical Design document for a better overview)
 */
@Entity
@Table(name = "componentdetail")
public class ComponentDetail extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "componentId")
    private Component component;

    @Column(length = 50)
    private String name;
    @Column(length = 40)
    private String ipAddress;
    private Date availableSince;
    private double processorLoad;
    private double memoryUsed;
    private double memoryTotal;
    private double diskspaceUsed;
    private double diskspaceTotal;

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Date getAvailableSince() {
        return availableSince;
    }

    public void setAvailableSince(Date availableSince) {
        this.availableSince = availableSince;
    }

    public double getProcessorLoad() {
        return processorLoad;
    }

    public void setProcessorLoad(double processorLoad) {
        this.processorLoad = processorLoad;
    }

    public double getMemoryUsed() {
        return memoryUsed;
    }

    public void setMemoryUsed(double memoryUsed) {
        this.memoryUsed = memoryUsed;
    }

    public double getMemoryTotal() {
        return memoryTotal;
    }

    public void setMemoryTotal(double memoryTotal) {
        this.memoryTotal = memoryTotal;
    }

    public double getDiskspaceUsed() {
        return diskspaceUsed;
    }

    public void setDiskspaceUsed(double diskspaceUsed) {
        this.diskspaceUsed = diskspaceUsed;
    }

    public double getDiskspaceTotal() {
        return diskspaceTotal;
    }

    public void setDiskspaceTotal(double diskspaceTotal) {
        this.diskspaceTotal = diskspaceTotal;
    }
}
