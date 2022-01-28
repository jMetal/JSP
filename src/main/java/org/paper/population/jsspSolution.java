package org.paper.population;

import org.uma.jmetal.solution.integersolution.impl.DefaultIntegerSolution;
import org.uma.jmetal.util.bounds.Bounds;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;

import java.util.List;
import java.util.Random;

public class jsspSolution extends DefaultIntegerSolution {

    public jsspSolution(int jobs, int numberOfMachines, int numberOfVelocities, int numberOfObjectives, int numberOfConstraints, List<Bounds<Integer>> boundsList) {
        super(numberOfObjectives, numberOfConstraints, boundsList);
        this.bounds = boundsList ;
        int totalRuns = jobs*numberOfMachines;
        Random r = new Random();
        int[] randomData = r.ints(0, totalRuns)
                .distinct()
                .limit(totalRuns)
                .toArray();
        for (int i = 0; i < totalRuns; i++) {
            variables().set(i, randomData[i]%jobs);
            variables().set(i+totalRuns, JMetalRandom.getInstance().nextInt(0, numberOfVelocities - 1));
            variables().set(i+totalRuns*2,JMetalRandom.getInstance().nextInt(0, 1));
        }
    }
}