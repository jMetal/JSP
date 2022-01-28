package org.paper.tasks;

import org.paper.configuration.timeAndEnergyConfiguration;
import org.paper.tasks.TidyData;

public class CalculateMetrics {
  timeAndEnergyConfiguration timeEnergyConfiguration = new timeAndEnergyConfiguration();

  private int workingEnergyConsumption(int[][] data, int[] velocity) {
    int workingConsumption = 0;
    for (int i = 0; i < data.length; i++) {
      for (int j = 0; j < data[0].length / 2; j++) {
        int consumption = timeEnergyConfiguration.machineWorkingEnergy[i];
        int timeDifference = data[i][2 * j + 1] - data[i][2 * j];
        int velocityPenalty = timeEnergyConfiguration.velocityPenalty[velocity[j]];
        workingConsumption =
            workingConsumption + (timeDifference * consumption) * (velocityPenalty);
      }
    }

    return workingConsumption;
  }

  private int idleEnergyConsumption(int[][] data, int[][] idle) {

    int idleConsumption = 0;
    for (int i = 0; i < data.length; i++) {
      for (int j = 0; j < (data[0].length / 2 - 1); j++) {
        if (idle[i][j] == 1) {
          int consumption = timeEnergyConfiguration.machineIdleEnergy[i];
          int timeDifference = data[i][2 * (j + 1)] - data[i][(2 * j + 1)];

          idleConsumption = idleConsumption + timeDifference * consumption;
        }
      }
    }

    return idleConsumption;
  }

  private int idleToStopEnergyConsumption(int[][] data) {
    int consume = 0;

    for (int i = 0; i < data.length; i++) {
      int sum = 0;
      for (int j = 0; j < data[0].length; j++) {
        sum += data[i][j];
      }
      consume += sum * timeEnergyConfiguration.machineWorkingToIdleEnergy[i];
    }

    return consume;
  }

  private int workingToStopEnergyConsumption(int[][] data) {

    int consume = 0;

    for (int i = 0; i < data.length; i++) {
      int sum = 0;
      for (int j = 0; j < data[0].length; j++) {
        sum += data[i][j];
      }
      consume += sum * timeEnergyConfiguration.machineWorkingToStopEnergy[i];
    }

    return consume;
  }

  public int Energy(int[][] idle, int[] velocity, int[][] data) {

    int idleConsumption = this.idleEnergyConsumption(data, idle);
    int workingConsumption = this.workingEnergyConsumption(data, velocity);
    int idleFromWorkEnergy = this.idleToStopEnergyConsumption(idle);
    int stopFromWorkEnergy = this.workingToStopEnergyConsumption(idle);
    int response = workingConsumption + idleConsumption + stopFromWorkEnergy + idleFromWorkEnergy;

    return (workingConsumption + idleConsumption + stopFromWorkEnergy + idleFromWorkEnergy);
  }

  public int Makespan(int[][] data) {
    int maxValue = 0;
    int numberRows = data[0].length - 1;
    for (int i = 0; i < data.length; i++) {
      if (data[i][numberRows] > maxValue) {
        maxValue = data[i][numberRows];
      }
    }
    return maxValue;
  }
}
