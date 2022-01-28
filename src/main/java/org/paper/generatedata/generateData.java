package org.paper.generatedata;

import aitoa.examples.jssp.JSSPInstance;
import org.paper.configuration.timeAndEnergyConfiguration;


public class generateData {

    public int[][] generateDataMatrix(JSSPInstance data){
        int machines = data.m;
        int jobs = data.n;
        int consumptionMatrix[][];

        consumptionMatrix = new int[jobs][machines];

        for(int i= 0; i < jobs; i++){
            for(int j= 1; j <= machines; j++){
                consumptionMatrix[i][j-1] = data.jobs[i][j*2-1];
            }
        }

    return consumptionMatrix;
    }
}