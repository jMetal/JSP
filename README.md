# JSP MULTIOBJETIVE FOR ENERGY AND MAKESPAN ON INDUSTRIAL ENVIRONMENTS

This repository refers to the code used for the use case of the paper: "Energy-Aware Multi-Objective Jobshop Scheduling Optimization with Metaheuristics in Manufacturing Industries: A Critical Survey, Results and Perspectives" published in [applied sciences](https://www.mdpi.com/2076-3417/12/3/1491)

The aim of this repository is to provide in an open repository all the code used in the paper, making it easily reproducible and to be used as a starting point for further research.

To run the code you only need to execute the main. Some configurations are possible, as for example the times, velocities  and energy consumptions per machine. To do this, you must edit the *timeAndEnergyConfiguration* class:

Variables related with energy consumption per machine (units of energy)
- machineWorkingEnergy (integer[]), assign per machine the energy consumption while it is working.
- machineIdleEnergy (integer[]), assign per machine the energy consumption while it is in idle. 
- machineWorkingToStopEnergy (integer[]), assign per machine the energy consumption while it is from working to stop state.
- machineWorkingToIdleEnergy (integer[]), assign per machine the energy consumption while it is from working to idle.

Variables related with velocity
- numberOfVelocities (integer), the number of velocities at the machines can run.
- velocityPenalty (integer[]), the penalty of running at these velocities.

Variables related with time (units of time)
- machineTimeToIdle (integer[]), the time to change the state for the machine, from working to idle.
- machineTimeToStop (integer[]), the time to change the state for the machine, from working to stop.

The literature instances used in the paper are also provided: 
- **la04**,  5 machines and  10 jobs.
- **la10**,  5 machines and  15 jobs.
- **ft06**, 6 machines and 6 jobs.
- **orb01**, 10 machines and  10 jobs.
- **abz5**,  10 machines and 10 jobs.
- **swv20**, 10 machines and 50 jobs.
- **ta12**, 15 machines and 20 jobs.
- **dmu11**, 15 machines and  30 jobs.
- **yn03**, 20 machines and 20 jobs.
- **dmu40**, 20 machines and  50 jobs.
- **ta77**, 20 machines and 100 jobs.

These are some examples, but you can add your own dataset by including it under the data path 
