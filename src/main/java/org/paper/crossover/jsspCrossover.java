package org.paper.crossover;

import org.uma.jmetal.operator.crossover.CrossoverOperator;
import org.uma.jmetal.solution.integersolution.IntegerSolution;
import org.uma.jmetal.solution.permutationsolution.PermutationSolution;
import org.uma.jmetal.util.errorchecking.JMetalException;
import org.uma.jmetal.util.pseudorandom.BoundedRandomGenerator;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;
import org.uma.jmetal.util.pseudorandom.RandomGenerator;

import java.util.ArrayList;
import java.util.List;



public class jsspCrossover implements CrossoverOperator<IntegerSolution> {

        private double crossoverProbability  ;
        private BoundedRandomGenerator<Integer> cuttingPointRandomGenerator ;
        private RandomGenerator<Double> crossoverRandomGenerator ;

        /** Constructor */
        public jsspCrossover(double crossoverProbability) {
                this.crossoverProbability = crossoverProbability;
        }

        /* Getters */
        @Override
        public double getCrossoverProbability() {
                return crossoverProbability;
        }

        /* Setters */
        public void setCrossoverProbability(double crossoverProbability) {
                this.crossoverProbability = crossoverProbability;
        }

        /** Execute() method */
        @Override
        public List<IntegerSolution> execute(List<IntegerSolution> solutions) {
                if (null == solutions) {
                        throw new JMetalException("Null parameter") ;
                } else if (solutions.size() != 2) {
                        throw new JMetalException("There must be two parents instead of " + solutions.size()) ;
                }

                return doCrossover(crossoverProbability, solutions.get(0), solutions.get(1)) ;
        }

        /** doCrossover method */
        public List<IntegerSolution> doCrossover(
                double probability, IntegerSolution parent1, IntegerSolution parent2) {
                List<IntegerSolution> offspring = new ArrayList<IntegerSolution>(2);

                offspring.add((IntegerSolution) parent1.copy()) ;
                offspring.add((IntegerSolution) parent2.copy()) ;

                int permutationLength = parent1.variables().size() ;
                int size = parent1.variables().size();
                if (JMetalRandom.getInstance().nextDouble(0,1) <= this.crossoverProbability) {
                        int cuttingPoint1;
                        int cuttingPoint2;
                        int areaToChange = JMetalRandom.getInstance().nextInt(1, 2);
                        int lowerBound = (size/3)*areaToChange;
                        int upperBound = lowerBound + (size/3) - 1;
                        // STEP 1: Get two cutting points
                        cuttingPoint1 = JMetalRandom.getInstance().nextInt(lowerBound, upperBound);
                        cuttingPoint2 =  JMetalRandom.getInstance().nextInt(lowerBound, upperBound);
                        while (cuttingPoint2 == cuttingPoint1)
                                cuttingPoint2 =  JMetalRandom.getInstance().nextInt(lowerBound,upperBound);

                        if (cuttingPoint1 > cuttingPoint2) {
                                int swap;
                                swap = cuttingPoint1;
                                cuttingPoint1 = cuttingPoint2;
                                cuttingPoint2 = swap;
                        }

                        // STEP 2: Get the subchains to interchange
                        int replacement1[] = new int[permutationLength];
                        int replacement2[] = new int[permutationLength];
                        for (int i = 0; i < permutationLength; i++)
                                replacement1[i] = replacement2[i] = -1;

                        // STEP 3: Interchange
                        for (int i = cuttingPoint1; i <= cuttingPoint2; i++) {
                                offspring.get(0).variables().set(i, parent2.variables().get(i));
                                offspring.get(1).variables().set(i, parent1.variables().get(i));

                                replacement1[parent2.variables().get(i)] = parent1.variables().get(i) ;
                                replacement2[parent1.variables().get(i)] = parent2.variables().get(i) ;
                        }


                }

                return offspring;
        }

        @Override
        public int getNumberOfRequiredParents() {
                return 2 ;
        }

        @Override
        public int getNumberOfGeneratedChildren() {
                return 2;
        }
}