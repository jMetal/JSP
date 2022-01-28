package org.paper.createCSV;

import org.paper.configuration.timeAndEnergyConfiguration;
import org.paper.readFiles.ReadFiles;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;
import org.uma.jmetal.solution.integersolution.impl.DefaultIntegerSolution;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class CreateCSV {
    timeAndEnergyConfiguration timeEnergyConfiguration = new timeAndEnergyConfiguration();
    private int numberOfVelocities = timeEnergyConfiguration.numberOfVelocities;
    private int[] machineWorkingEnergy = timeEnergyConfiguration.machineWorkingEnergy;
    private int[] machineIdleEnergy = timeEnergyConfiguration.machineIdleEnergy;
    private int[] machineWorkingToStopEnergy = timeEnergyConfiguration.machineWorkingToStopEnergy;
    private int[] machineWorkingToIdleEnergy = timeEnergyConfiguration.machineWorkingToIdleEnergy;

    // Time
    private int[] machineTimeToIdle = timeEnergyConfiguration.machineTimeToIdle;
    private int[] machineTimeToStop = timeEnergyConfiguration.machineTimeToStop;

    private int machineByJob[][];
    private int timeByJob[][];

    private ReadFiles data;
    //private JSSPInstance data;



    public void calculateMakespan(List<DoubleSolution> solution, int jobs, int machines ) {

        int[][] machineMakespan = new int[machines][jobs];
        int actualJob;
        int actualTime;
        int actualMachine;
        int totalExecutions = jobs * machines;
        int[] jobStep = new int[jobs];
        int[] makespanInstance = new int[totalExecutions];//solution.getVariables().toArray(new Integer[0]);
        int[] velocityInstance = new int[totalExecutions];
        int[] idleInstance = new int[totalExecutions];
        for (int i = 0; i < totalExecutions; i++) {
            int iVelocity = totalExecutions + i;
            int iIdle = totalExecutions * 2 + i;
        Object algo = solution.get(0);
            Object algo23 = algo;
            int huhu = solution.get(0).variables().get(1).intValue();

            int hjh = 7878;
       //  velocityInstance[i] = solution.get(iVelocity).varget(1).intValue();
         //  idleInstance[i] = solution.get(iIdle).intValue();
        }

        int[][] machineGantt = new int[machines][jobs * 2];
        int[][] idleByMachine = new int[machines][jobs];
        int[] machineStep = new int[machines];


        for (int i = 0; i < totalExecutions; i++) {
            actualJob = makespanInstance[i];
            actualMachine = machineByJob[actualJob][(jobStep[actualJob])];
            actualTime = timeByJob[actualJob][(jobStep[actualJob])] * (velocityInstance[i] + 1);

            if ((machineStep[actualMachine]) == 0) {
                machineGantt[actualMachine][0] = 0;
                machineGantt[actualMachine][1] = actualTime;
            } else {
                int previousTime = machineGantt[actualMachine][2 * (machineStep[actualMachine]) - 1];
                int previousJobStep = jobStep[actualJob] - 1;
                int previousJobTime = 0;
                if (previousJobStep > 0) {
                    int previousMachine = machineByJob[actualJob][previousJobStep];
                    previousJobTime = Arrays.stream(machineGantt[previousMachine]).reduce((x, y) -> x > y ? x : y).getAsInt();
                }
                int initTime = Math.max(previousTime, previousJobTime);
                int endTime = initTime + actualTime;

                machineGantt[actualMachine][2 * (machineStep[actualMachine])] = initTime;
                machineGantt[actualMachine][2 * (machineStep[actualMachine]) + 1] = endTime;
            }
            idleByMachine[actualMachine][actualJob] = idleInstance[i];
            machineStep[actualMachine] = machineStep[actualMachine] + 1;
            jobStep[actualJob] = jobStep[actualJob] + 1;
            int asdfa = 23;
        }

    }

}
