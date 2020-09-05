package com.nerdygadgets.monitoring.app.backtracking;

import com.nerdygadgets.monitoring.app.backtracking.models.BestComponent;
import com.nerdygadgets.monitoring.app.backtracking.models.CalculatedComponent;
import com.nerdygadgets.monitoring.app.backtracking.models.CalculatedDesign;
import com.nerdygadgets.monitoring.data.enums.ComponentName;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

public class AvailabilityCalculator {
    private static int possibilities = 8;

    private static List<BestComponent> calculateBestComponents(double[][] failureSet, double[][] priceSet, double userPercentage) {
        List<BestComponent> bestComponents = new ArrayList<>();

        for (int i = 0; i < possibilities; i++) {
            for (int j = 0; j < possibilities; j++) {
                for (int k = 0; k < possibilities; k++) {
                    BestComponent bestComponent = new BestComponent();

                    // Get the failure percentage and total price of component one (web or db)
                    double pctComponentOne = failureSet[0][i];
                    double priceComponentOne = priceSet[0][i];

                    // Get the failure percentage and total price of component two (web or db)
                    double pctComponentTwo = failureSet[1][j];
                    double priceComponentTwo = priceSet[1][j];

                    // Get the failure percentage and total price of component three (web or db)
                    double pctComponentThree = failureSet[2][k];
                    double priceComponentThree = priceSet[2][k];

                    // Set calculated percentages to 1, to prevent dividing by zero in the formula down below.
                    if (pctComponentOne == 0) {
                        pctComponentOne = 1;
                    }
                    if (pctComponentTwo == 0) {
                        pctComponentTwo = 1;
                    }
                    if (pctComponentThree == 0) {
                        pctComponentThree = 1;
                    }

                    double calculatedPercentage = ((1 - (pctComponentOne * pctComponentTwo * pctComponentThree)));

                    // Place the data inside of an
                    if (calculatedPercentage >= (userPercentage / 100)) {
                        bestComponent.percentage = calculatedPercentage;
                        bestComponent.totalPrice = (priceComponentOne + priceComponentTwo + priceComponentThree);
                        bestComponent.componentOneAmount = i;
                        bestComponent.componentTwoAmount = j;
                        bestComponent.componentThreeAmount = k;

                        bestComponents.add(bestComponent);
                    }
                }
            }
        }

        return bestComponents;
    }

    public CalculatedDesign getCalcualtedDesign(double userPercentage) throws ParseException {
        CalculatedDesign calculatedDesign = new CalculatedDesign();

        // Construct pseudo data
        double[] percentageSet = {0.80, 0.90, 0.95, 0.90, 0.95, 0.98};
        int[] priceSet = {2200, 3200, 5100, 5100, 7700, 12200};
        String[] types = {"web", "web", "web", "db", "db", "db"};

        double firewallPercentage = 0.99998;
        double firewallPrice = 4000;

        // Initialize failure sets
        double[][] webFailureSet = new double[3][possibilities];
        double[][] dbFailureSet = new double[3][possibilities];

        // Initialize price sets
        double[][] webPriceSet = new double[3][possibilities];
        double[][] dbPriceSet = new double[3][possibilities];

        // Define starting indices
        int webIndex = 0;
        int dbIndex = 0;

        // Loop through the set of types
        for (int t = 0; t < types.length; t++) {
            // Loop through the defined possibilities
            for (int p = 0; p < possibilities; p++) {
                // define the failure percentage
                // (the base to the power of 0 results in 1; we prevent that by using the value 0.
                // Otherwise we would end up with a failure percentage of 100% that a component fails,
                // when the amount of that component is 0)
                final double failurePercentage = (p == 0) ? 0 : Math.pow((1 - percentageSet[t]), (p));

                // Populate the failureSet and priceSet for the 'web' type components.
                if (types[t].equals("web")) {
                    webFailureSet[webIndex][p] = failurePercentage;

                    if (p == 0) {
                        webPriceSet[webIndex][p] = 0;
                    } else {
                        webPriceSet[webIndex][p] = (priceSet[t] * p);
                    }

                    if (p == (possibilities - 1)) {
                        webIndex++;
                    }
                }

                // Populate the failureSet and priceSet for the 'db' type components.
                if (types[t].equals("db")) {
                    dbFailureSet[dbIndex][p] = failurePercentage;

                    if (p == 0) {
                        dbPriceSet[dbIndex][p] = 0;
                    } else {
                        dbPriceSet[dbIndex][p] = (priceSet[t] * p);
                    }

                    if (p == (possibilities - 1)) {
                        dbIndex++;
                    }
                }
            }
        }

        // Calculate the best possible combination of components, for both the 'web' and 'db' type.
        List<BestComponent> bestWebComponents = calculateBestComponents(webFailureSet, webPriceSet, userPercentage);
        List<BestComponent> bestDbComponents = calculateBestComponents(dbFailureSet, dbPriceSet, userPercentage);

        for (BestComponent bestWebComponent : bestWebComponents) {
            for (BestComponent bestDbComponent : bestDbComponents) {
                double lastSavedPrice = calculatedDesign.totalPrice;
                double totalPrice = (firewallPrice + bestWebComponent.totalPrice + bestDbComponent.totalPrice);
                double newPercentage = (firewallPercentage * bestWebComponent.percentage * bestDbComponent.percentage) * 100;

                if (newPercentage >= userPercentage) {
                    if (lastSavedPrice > totalPrice || lastSavedPrice <= 0) {
                        calculatedDesign.percentage = newPercentage;
                        calculatedDesign.totalPrice = totalPrice;

                        calculatedDesign.components.clear();
                        calculatedDesign.components.add(new CalculatedComponent(ComponentName.HAL9001DB, bestDbComponent.componentOneAmount));
                        calculatedDesign.components.add(new CalculatedComponent(ComponentName.HAL9002DB, bestDbComponent.componentTwoAmount));
                        calculatedDesign.components.add(new CalculatedComponent(ComponentName.HAL9003DB, bestDbComponent.componentThreeAmount));
                        calculatedDesign.components.add(new CalculatedComponent(ComponentName.HAL9001W, bestWebComponent.componentOneAmount));
                        calculatedDesign.components.add(new CalculatedComponent(ComponentName.HAL9002W, bestWebComponent.componentTwoAmount));
                        calculatedDesign.components.add(new CalculatedComponent(ComponentName.HAL9003W, bestWebComponent.componentThreeAmount));
                        calculatedDesign.components.add(new CalculatedComponent(ComponentName.pfsense, 1));
                    }
                }
            }
        }

        return calculatedDesign;
    }
}
