package org.paper.readFiles;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.*;



public class ReadFiles {
    public int m = 0;
    public int n = 0;
    public int[][] jobs = new int[25][];
    public ReadFiles(String route){
        try{
            // Open the file that is the first
            // command line parameter
            FileInputStream fstream = new FileInputStream(route);
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            int i = 0;

            //Read File Line By Line
            while ((strLine = br.readLine()) != null)   {
                // Print the content on the console
                String[] ary = strLine.split("\t");
                System.out.println(ary.length);
                if(ary.length == 2){
                    this.n = Integer.parseInt(ary[0].trim());
                    this.m = Integer.parseInt(ary[1].trim());
                    this.jobs = new int[n][m*2];
                }else{
                    for(int j = 0; j < ary.length; j++){
                        this.jobs[i][j] = Integer.parseInt(ary[j].trim());
                    }
                    i = i + 1;
                }

                System.out.println (strLine);
            }
            //Close the input stream
            in.close();
        }catch (Exception e){//Catch exception if any
            System.err.println("Error: " + e.getMessage());
            System.out.println(System.getProperty("user.dir"));

        }
    }
}
