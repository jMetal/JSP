package org.paper.mutation;

import org.uma.jmetal.operator.mutation.MutationOperator;
import org.uma.jmetal.solution.integersolution.IntegerSolution;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;
import org.paper.problem.JSSP;

public class jsspMutation implements MutationOperator<IntegerSolution> {
    private double mutationProbability;


    /** Constructor */
    public jsspMutation(double mutationProbability) {
        this.mutationProbability = mutationProbability;
    }

    /* Getters */
    public double getMutationProbability() {
        return mutationProbability;
    }

    /* Setters */
    public void setMutationProbability(double mutationProbability) {
        this.mutationProbability = mutationProbability;
    }

    /* Execute() method */
    @Override
    public IntegerSolution execute(IntegerSolution solution) {
        doMutation(solution);
        return solution;
    }

    /** Performs the operation */
    public void doMutation(IntegerSolution solution) {
        if (JMetalRandom.getInstance().nextDouble(0, 1) <= this.mutationProbability) {
            int sequenceLength = solution.variables().size();

            int numberOfJobs = sequenceLength / 3;
            int numberToChange = JMetalRandom.getInstance().nextInt(0, 2);
            int lowerValue = numberOfJobs * numberToChange;
            int upperValue = lowerValue + numberOfJobs - 1;
            int interchangePositionA = JMetalRandom.getInstance().nextInt(lowerValue, upperValue);
            int interchangePositionB = JMetalRandom.getInstance().nextInt(lowerValue, upperValue);

            if (numberToChange == 0) {
                while (interchangePositionB == interchangePositionA) {
                    interchangePositionB = JMetalRandom.getInstance().nextInt(lowerValue, upperValue);
                }
                int positionApreviousValue = solution.variables().get(interchangePositionA);
                solution.variables().set(interchangePositionA, solution.variables().get(interchangePositionB));
                solution.variables().set(interchangePositionB, positionApreviousValue);
            } else {
                int lowerBound = solution.getBounds(interchangePositionA).getLowerBound();
                int upperBound = solution.getBounds(interchangePositionA).getUpperBound();
                int randomNumber = JMetalRandom.getInstance().nextInt(lowerBound, upperBound);
                solution.variables().set(interchangePositionA, randomNumber);
            }

        }
    }
}