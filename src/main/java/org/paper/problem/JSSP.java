package org.paper.problem;

import org.paper.configuration.timeAndEnergyConfiguration;
import org.paper.population.jsspSolution;
import org.paper.readFiles.ReadFiles;
import org.paper.tasks.CalculateMetrics;
import org.paper.tasks.TidyData;
import org.uma.jmetal.problem.doubleproblem.DoubleProblem;
import org.uma.jmetal.problem.integerproblem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.integersolution.IntegerSolution;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.collect.TreeTraverser.using;

public class JSSP extends AbstractIntegerProblem implements DoubleProblem {
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
  // private JSSPInstance data;
  @Override
  public IntegerSolution createSolution() {
    return new jsspSolution(data.n, data.m, this.numberOfVelocities, 2, 0, bounds);
  }

  public JSSP(String file) {

    data = new ReadFiles(file);
    timeByJob = new TidyData().GetJobsByTime(data.n, data.m, data.jobs);
    machineByJob = new TidyData().GetJobsByMachine(data.n, data.m, data.jobs);
    int numberJobs = data.m * data.n;

    List<Integer> lowerLimit = new ArrayList<>(numberJobs);
    List<Integer> upperLimit = new ArrayList<>(numberJobs * 3);

    for (int i = 0; i < numberJobs * 3; i++) {
      lowerLimit.add(0);
    }
    for (int i = 0; i < numberJobs; i++) {
      upperLimit.add(data.n - 1);
    }
    for (int i = numberJobs; i < numberJobs * 2; i++) {
      upperLimit.add(this.numberOfVelocities - 1);
    }
    for (int i = numberJobs * 2; i < numberJobs * 3; i++) {
      upperLimit.add(1);
    }
    setVariableBounds(lowerLimit, upperLimit);
  }

  public int getNumberOfObjectives() {
    return 2;
  }

  public int getNumberOfVariables() {
    return data.m * data.n * 3;
  }

  @Override
  public IntegerSolution evaluate(IntegerSolution solution) {
    int[][] machineMakespan = new int[data.m][data.n];
    int actualJob;
    int actualTime;
    int actualMachine;
    int totalExecutions = data.n * data.m;
    int[] jobStep = new int[data.n];
    int[] makespanInstance =
        new int[totalExecutions]; // solution.getVariables().toArray(new Integer[0]);
    int[] velocityInstance = new int[totalExecutions];
    int[] idleInstance = new int[totalExecutions];
    int[] lastTimeJob = new int[data.n];

    for (int i = 0; i < totalExecutions; i++) {
      int iVelocity = totalExecutions + i;
      int iIdle = totalExecutions * 2 + i;
     makespanInstance[i] = solution.variables().get(i);
      velocityInstance[i] = solution.variables().get(iVelocity);
     idleInstance[i] = solution.variables().get(iIdle);

    }

    int[][] machineGantt = new int[data.m][data.n * 2];
    int[][] idleByMachine = new int[data.m][data.n];
    int[] machineStep = new int[data.m];

    int endTime = 0;
    int initTime = 0;
    int contador = 0;


    for (int i = 0; i < totalExecutions; i++) {
      actualJob = makespanInstance[i];
      actualMachine = machineByJob[actualJob][(jobStep[actualJob])];
      actualTime = timeByJob[actualJob][(jobStep[actualJob])] * (velocityInstance[i] + 1);
      int positionOrder = 0;

      if (i == 0) {

        initTime = 0;
        endTime = actualTime;
        machineGantt[actualMachine][0] = initTime;
        machineGantt[actualMachine][1] = endTime;
        positionOrder = 1;

      } else {

        int previousJobTime = lastTimeJob[actualJob];
        // Busco si el primer valor de cada máquina no es cero
        int previousTimeMachine = 0;
        if (actualTime < machineGantt[actualMachine][0]
            && actualTime < (machineGantt[actualMachine][0] - previousJobTime)) {
          previousTimeMachine = previousJobTime;

        } else {

          previousTimeMachine = Arrays.stream(machineGantt[actualMachine]).max().getAsInt();
          int[] diferences = new int[data.n];
          for (int k = 1; k < diferences.length; k += 2) {
            diferences[k] =
                machineGantt[actualMachine][2 * k] - machineGantt[actualMachine][2 * k - 1];

            if (actualTime < diferences[k]) {
              if (machineGantt[actualMachine][2 * k - 1] > previousJobTime) {
                previousTimeMachine = machineGantt[actualMachine][2 * k - 1];
                break;
              }
            }
          }
        }

        initTime = Math.max(previousTimeMachine, previousJobTime);
        endTime = initTime + actualTime;

        machineGantt[actualMachine][2 * (machineStep[actualMachine])] = initTime;
        machineGantt[actualMachine][2 * (machineStep[actualMachine]) + 1] = endTime;

        positionOrder = 2 * (machineStep[actualMachine]) + 1;

      }

      lastTimeJob[actualJob] = endTime;
      idleByMachine[actualMachine][actualJob] = idleInstance[i];
      machineStep[actualMachine] = machineStep[actualMachine] + 1;
      jobStep[actualJob] = jobStep[actualJob] + 1;

      machineGantt = new TidyData().SortMachineGantt(machineGantt,actualMachine,positionOrder);
  }


    int totalMakespan = new CalculateMetrics().Makespan(machineGantt);



    int totalEnergyConsumption =
        new CalculateMetrics().Energy(idleByMachine, velocityInstance, machineGantt);
    solution.objectives()[0] = totalMakespan;
    solution.objectives()[1] = totalEnergyConsumption;

    return solution;
  }
}
