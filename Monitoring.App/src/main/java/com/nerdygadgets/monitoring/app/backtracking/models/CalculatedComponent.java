package com.nerdygadgets.monitoring.app.backtracking.models;

import com.nerdygadgets.monitoring.data.enums.ComponentName;

public class CalculatedComponent {
    public ComponentName componentName;
    public int amount;

    public CalculatedComponent(ComponentName componentName, int amount) {
        this.componentName = componentName;
        this.amount = amount;
    }
}
