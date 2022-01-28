package org.paper.tasks;

import java.util.Arrays;

public class TidyData {
    public int SumMatrix(int[][] data){
        int sum = 0;
        for(int i=0; i < data.length;i++){
            for(int j=0; j < data[0].length;j++){
                sum += data[i][j];
            }
        }
        return sum;
    }
    public int GetIdleByMachine(int[] jobs, int[] velocityArray, int[] idleArray, int[][] machines){
        int[][] result = new int[machines.length][machines[0].length];
        int[] machinesStep = new int[machines.length];
        for(int i = 0; i < jobs.length;i++){


        }

        return 23;
    }
    public int[][] GetJobsByTime(int rows, int columns, int[][] data){
        int result[][] = new int[rows][columns];
        for(int i= 0; i < rows; i++){
            for(int j=0; j < columns; j++){
                result[i][j] = data[i][2*j + 1];
            }
        }
        return result;
    }
    public int[][] GetJobsByMachine(int rows, int columns, int[][] data){
    int result[][] = new int[rows][columns];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        result[i][j] = data[i][2 * j];
            }
        }
        return result;
    }

    public int[][] SortMachineGantt(int[][] data, int machine, int position){

        int[] arraySorted = Arrays.copyOfRange(data[machine], 0, (position + 1));
        int[] evenArray = new int[arraySorted.length / 2];
        int[] oddArray = new int[arraySorted.length / 2];
        int evenCounter = 0;
        int oddCounter = 0;
        for (int o = 0; o < arraySorted.length; o++) {
            if (o % 2 == 0) {
                evenArray[evenCounter] = arraySorted[o];
                evenCounter += 1;
            } else {
                oddArray[oddCounter] = arraySorted[o];
                oddCounter += 1;
            }
        }
        Arrays.sort(evenArray);
        Arrays.sort(oddArray);
        evenCounter = 0;
        oddCounter = 0;
        for (int o = 0; o < arraySorted.length; o++) {
            if (o % 2 == 0) {
                data[machine][o] = evenArray[evenCounter];
                evenCounter += 1;
            } else {
                data[machine][o] = oddArray[oddCounter];
                oddCounter += 1;
            }
        }

        return data;

    }
}