package test;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.awt.Desktop;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;


public class App 
{
    static long[] data;
    static calcExcutionTime iCalcLoadingTime = new calcExcutionTime("Loading");
    static calcExcutionTime iCalcWritingTime = new calcExcutionTime("Writing");
    static calcExcutionTime iCalcExcutionTime = new calcExcutionTime("Excution");

    static String fileName;
    static int sortTech;

    static Scanner lineReader;

    public static void main( String[] args ) throws InterruptedException, IOException
    {
        Thread loadData = new Thread(){
            public void run(){
                try {
                        iCalcLoadingTime.start();
                        data = readData(fileName + ".csv");
                        System.out.println("\nData Loaded from " + fileName + ".csv");
                        iCalcLoadingTime.stop();
                } catch (IOException e) {
                    System.out.println("\n File " + fileName + ".csv" + " is not found");
                    System.exit(1);
                }
            };
        };
        Thread sortDataHeap = new Thread(){
            public void run(){
                    iCalcExcutionTime.start();
                    new HeapSort().sort(data);;
                    System.out.println("\nData Sorted");
                    iCalcExcutionTime.stop();
            };
        };
        Thread sortDataBucket = new Thread(){
            public void run(){
                    iCalcExcutionTime.start();
                    new BucketSort(data);
                    System.out.println("\nData Sorted");
                    iCalcExcutionTime.stop();
            };
        };
        Thread writeData = new Thread(){
            public void run(){
                try {
                        iCalcWritingTime.start();
                        writeData(fileName + "_sorted.csv",data);
                        System.out.println("\nData Written into " + fileName + "_sorted.csv");
                        iCalcWritingTime.stop();
                } catch (IOException e) {}
            };
        };
        System.out.println("Welcome to Sort Application !");
            System.out.print("Enter File Name: ");
            lineReader = new Scanner(System.in);
            fileName = lineReader.nextLine();
            System.out.println("1- Heap Sort\n2- Bucket Sort");
            sortTech = lineReader.nextInt();
            System.out.print("Loading Data.");
            loadData.start();
            while(loadData.isAlive()){
                System.out.print(".");
                TimeUnit.MILLISECONDS.sleep(10);
            }
            switch(sortTech){
                case 1:
                    System.out.print("Heap sorting data.");
                    sortDataHeap.start();
                    while(sortDataHeap.isAlive()){
                        System.out.print(".");
                        TimeUnit.MILLISECONDS.sleep(10);
                    }
                    break;
                case 2:
                    System.out.print("Bucket sorting data.");
                    sortDataBucket.start();
                    while(sortDataBucket.isAlive()){
                        System.out.print(".");
                        TimeUnit.MILLISECONDS.sleep(10);
                    }
                    break;
                default:
                    System.out.println("Invalid Choice");   
                    System.exit(1);             
            }
            System.out.print("Writing Data.");
            writeData.start();
            while(writeData.isAlive()){
                System.out.print(".");
                TimeUnit.MILLISECONDS.sleep(10);
            }
            Desktop desktop = Desktop.getDesktop();
            File file = new File(fileName+".csv");
            File file_sorted = new File(fileName+"_sorted.csv");
            if(file_sorted.exists()) desktop.open(file_sorted);  
            TimeUnit.SECONDS.sleep(1);
            if(file.exists()) desktop.open(file);
            System.exit(1);
    }

    public static long[] readData(String file) throws IOException
    {
        FileReader filereader = new FileReader(file);
        CSVReader csvReader = new CSVReaderBuilder(filereader)
                                  .build();
        List<String[]> dataStored = csvReader.readAll();
        long[] data = new long[dataStored.size()];
        int key = 0;
        for(String[] line: dataStored)
            data[key++] = Long.valueOf(line[0]);
        return data;
    }

    public static void writeData(String file, long[] data) throws IOException
    {
        FileWriter writer = new FileWriter(file);
        int n = data.length;
        for (int i = 0; i < n; i++) {
            writer.write(data[i] + "\n"+ "");
        }
        writer.close();
    }

}