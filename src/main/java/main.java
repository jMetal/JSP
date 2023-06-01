import org.uma.jmetal.algorithm.multiobjective.smpso.SMPSO;
import org.uma.jmetal.experimental.componentbasedalgorithm.algorithm.multiobjective.nsgaii.NSGAII;
import org.uma.jmetal.experimental.componentbasedalgorithm.algorithm.multiobjective.moead.MOEAD;
import org.uma.jmetal.experimental.componentbasedalgorithm.algorithm.multiobjective.smsemoa.SMSEMOA;
import org.uma.jmetal.operator.crossover.CrossoverOperator;
import org.uma.jmetal.operator.mutation.MutationOperator;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;
import org.uma.jmetal.solution.integersolution.IntegerSolution;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.aggregativefunction.AggregativeFunction;
import org.uma.jmetal.util.aggregativefunction.impl.PenaltyBoundaryIntersection;
import org.uma.jmetal.util.aggregativefunction.impl.Tschebyscheff;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;
import org.uma.jmetal.util.observer.impl.RunTimeChartObserver;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;
import org.uma.jmetal.util.termination.impl.TerminationByComputingTime;
import org.uma.jmetal.util.termination.impl.TerminationByEvaluations;

import org.paper.mutation.jsspMutation;
import org.paper.problem.JSSP;
import org.paper.crossover.jsspCrossover;
import org.paper.createCSV.CreateCSV;
import org.uma.jmetal.qualityindicator.impl.hypervolume.Hypervolume;
import org.uma.jmetal.qualityindicator.impl.InvertedGenerationalDistancePlus;

import javax.swing.*;
import java.util.List;


public class main {
  public static void main(final String[] args) {
    Boolean showChart = false;
    String name = "";
    String[] dataSource = new String[] {"abz5","dmu11","dmu40","ft06","la04","la10","orb01","swv20","ta12","ta77","yn03"};
    for (int m = 0; m < dataSource.length; m++) {

      for (int l = 0; l < 10; l++) {
        name = Integer.toString(l);
        JSSP problem = new JSSP("data/" + dataSource[m] + ".txt");

        double crossoverProbability = 0.9;
        CrossoverOperator<IntegerSolution> crossover;
        crossover = new jsspCrossover(crossoverProbability);

        int populationSize = 50;
        int offspringPopulationSize = populationSize;

        NSGAII<DoubleSolution> nsga2;
        MOEAD<DoubleSolution> moead;
        SMSEMOA<DoubleSolution> smsemoa;



        MutationOperator<IntegerSolution> mutation;
        double mutationProbability = 1.0;


        mutation = new jsspMutation(mutationProbability);
        TerminationByEvaluations termination = new TerminationByEvaluations(400000);

        nsga2 =
            new NSGAII(
                problem, populationSize, offspringPopulationSize, crossover, mutation, termination);

        if (showChart) {
          RunTimeChartObserver<DoubleSolution> chartNsgaII =
              new RunTimeChartObserver<>("NSGA-II", 1, 10000, null);


          nsga2.getObservable().register(chartNsgaII);
        }

        nsga2.run();
        List<DoubleSolution> population = nsga2.getResult();


        JMetalLogger.logger.info(
            "Total execution time NSGA2 : " + nsga2.getTotalComputingTime() + "ms");
        JMetalLogger.logger.info("Number of evaluations NSGA2: " + nsga2.getEvaluations());

        new SolutionListOutput(population)
            .setVarFileOutputContext(
                new DefaultFileOutputContext("results/fun/NSGAII/" + dataSource[m] + "/VAR" + name + ".csv", ","))
            .setFunFileOutputContext(
                new DefaultFileOutputContext("results/fun/NSGAII/" + dataSource[m] + "/FUN" + name  + ".csv", ","))
            .print();

        JMetalLogger.logger.info("Random seed: " + JMetalRandom.getInstance().getSeed());
        JMetalLogger.logger.info("Objectives values have been written to file FUN.csv");
        JMetalLogger.logger.info("Variables values have been written to file VAR.csv");
        long computingTime = nsga2.getTotalComputingTime();
        JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");
        JMetalLogger.logger.info("Total execution  NSGA-II time: " + computingTime + "ms");
        mutation = new jsspMutation(mutationProbability);
        AggregativeFunction aggregativeFunction = new Tschebyscheff();
        double neighborhoodSelectionProbability = 0.9;
        int neighborhoodSize = 20;
        int maximumNumberOfReplacedSolutions = 2;
        moead =
            new MOEAD(
                problem,
                populationSize,
                mutation,
                crossover,
                aggregativeFunction,
                neighborhoodSelectionProbability,
                maximumNumberOfReplacedSolutions,
                neighborhoodSize,
                "resources/weightVectorFiles/moead",
                new TerminationByEvaluations(400000));
        if (showChart) {
          RunTimeChartObserver<DoubleSolution> chartMoead = new RunTimeChartObserver<>("MOEA/D", 1, 10000, null);

          moead.getObservable().register(chartMoead);
        }
        moead.run();

        population = moead.getResult();
        JMetalLogger.logger.info("Total execution time : " + moead.getTotalComputingTime() + "ms");
        JMetalLogger.logger.info("Number of evaluations: " + moead.getEvaluations());

        new SolutionListOutput(population)
            .setVarFileOutputContext(
                new DefaultFileOutputContext("results/fun/MOEAD/" + dataSource[m] + "/VAR" + name + ".csv", ","))
            .setFunFileOutputContext(
                new DefaultFileOutputContext("results/fun/MOEAD/" + dataSource[m] +  "/FUN" + name  + ".csv", ","))
            .print();

        JMetalLogger.logger.info("Random seed: " + JMetalRandom.getInstance().getSeed());
        JMetalLogger.logger.info("Objectives values have been written to file FUN.csv");
        JMetalLogger.logger.info("Variables values have been written to file VAR.csv");
        computingTime = moead.getTotalComputingTime();
        JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");

        termination = new TerminationByEvaluations(400000);
        smsemoa = new SMSEMOA(problem, populationSize, crossover, mutation, termination);
        if (showChart) {
          RunTimeChartObserver<DoubleSolution> chartEmoa = new RunTimeChartObserver<>("SMS-EMOA", 1, 1000, null);

          // algorithm.getObservable().register(evaluator);
          smsemoa.getObservable().register(chartEmoa);
        }
        smsemoa.run();

        population = smsemoa.getResult();
        JMetalLogger.logger.info(
            "Total execution time SMS EMOA : " + smsemoa.getTotalComputingTime() + "ms");
        JMetalLogger.logger.info("Number of evaluations SMS EMOA: " + smsemoa.getEvaluations());

        new SolutionListOutput(population)
            .setVarFileOutputContext(
                new DefaultFileOutputContext("results/fun/SMSEMOA/" + dataSource[m] + "/VAR" + name + ".csv", ","))
            .setFunFileOutputContext(
                new DefaultFileOutputContext("results/fun/SMSEMOA/" + dataSource[m] +  "/FUN" + name  + ".csv", ","))
            .print();

        JMetalLogger.logger.info("Random seed: " + JMetalRandom.getInstance().getSeed());
        JMetalLogger.logger.info("Objectives values have been written to file FUN.csv");
        JMetalLogger.logger.info("Variables values have been written to file VAR.csv");
        computingTime = smsemoa.getTotalComputingTime();
        JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");


      }
    }
      }
}
