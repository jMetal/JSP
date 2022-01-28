package org.paper.configuration;

public class timeAndEnergyConfiguration {
/*

    // 2 machines 2 jobs
    public int[] machineWorkingEnergy = new int[]{30,20};
    public int[] machineIdleEnergy = new int[]{1,2};
    public int[] machineWorkingToStopEnergy = new int[]{3,2};
    public int[] machineWorkingToIdleEnergy = new int[]{5,3};
    // Velocity
    public int numberOfVelocities = 3;
    public int[] velocityPenalty = new int[]{6,2,1};
    // Time
    public int[] machineTimeToIdle = new int[]{10,12};
    public int[] machineTimeToStop = new int[]{16,17};*/

    // 5 machines 4 jobs
    // Energy Consumption
//    public int[] machineWorkingEnergy = new int[]{30,20,50,20,30};
//    public int[] machineIdleEnergy = new int[]{1,2,3,4,2};
//    public int[] machineWorkingToStopEnergy = new int[]{3,2,1,2,3};
//    public int[] machineWorkingToIdleEnergy = new int[]{5,3,2,3,4};
//    // Velocity
//    public int numberOfVelocities = 3;
//    public int[] velocityPenalty = new int[]{6,2,1}
//    // Time
//    public int[] machineTimeToIdle = new int[]{10,12,13,10,10};
//    public int[] machineTimeToStop = new int[]{16,17,14,15,18};



    // 2 MACHINES 2 JOBS
//    public int[] machineWorkingEnergy = new int[]{30,20};
//    public int[] machineIdleEnergy = new int[]{1,2};
//    public int[] machineWorkingToStopEnergy = new int[]{5,3};
//    public int[] machineWorkingToIdleEnergy = new int[]{2,2};
//    // Velocity
//    public int numberOfVelocities = 10;
//    public int[] velocityPenalty = new int[]{6,2,1}
//    // Time
//    public int[] machineTimeToIdle = new int[]{10,12};
//    public int[] machineTimeToStop = new int[]{16,17};

    //20 jobs 15 machines
     // Energy Consumption
    // public int[] machineWorkingEnergy = new int[]{30,20,50,20,30,30,20,50,20,30,30,20,50,20,30};
    // public int[] machineIdleEnergy = new int[]{1,2,3,4,2,1,2,3,4,2,1,2,3,4,2};
    // public int[] machineWorkingToStopEnergy = new int[]{5,3,2,3,4,5,3,2,3,4,5,3,2,3,4};
    // public int[] machineWorkingToIdleEnergy = new int[]{3,2,1,2,3,3,2,1,2,3,3,2,1,2,3};
    // Velocity
    // public int numberOfVelocities = 3;
    // public int[] velocityPenalty = new int[]{5,2,1};
    // Time
    // public int[] machineTimeToIdle = new int[]{10,12,13,10,10,10,12,13,10,10,10,12,13,10,10};
    // public int[] machineTimeToStop = new int[]{16,17,14,15,18,16,17,14,15,18,16,17,14,15,18};


    // 20 machines
    // Energy Consumption
    public int[] machineWorkingEnergy = new int[]{30,35,40,20,23,50,30,40,30,50,40,35,50,35,30,50,50,50,50,60};
    public int[] machineIdleEnergy = new int[]{10,20,22,23,22,15,12,14,12,13,12,15,12,15,13,18,21,23,25,12};
    public int[] machineWorkingToStopEnergy = new int[]{12,13,12,13,14,15,13,12,13,14,15,13,12,13,14,16,18,15,19,14,15};
    public int[] machineWorkingToIdleEnergy = new int[]{3,2,1,2,3,3,2,1,2,3,3,2,1,2,3,3,2,2,3,2};
    // Velocity
    public int numberOfVelocities = 3;
    public int[] velocityPenalty = new int[]{60,30,1};
    // Time
    public int[] machineTimeToIdle = new int[]{10,12,13,10,10,10,12,13,10,10,10,12,13,10,10,16,16,16,16,15};
    public int[] machineTimeToStop = new int[]{16,17,14,15,18,16,17,14,15,18,16,17,14,15,18,25,23,24,21,26};

    public timeAndEnergyConfiguration(){
        machineIdleEnergy = this.machineIdleEnergy;
        machineWorkingEnergy = this.machineWorkingEnergy;
        machineWorkingToStopEnergy = this.machineWorkingToStopEnergy;
        machineWorkingToIdleEnergy = this.machineWorkingToIdleEnergy;
        numberOfVelocities = this.numberOfVelocities;
        machineTimeToIdle = this.machineTimeToIdle;
        machineTimeToStop = this.machineTimeToStop;
    }
}
