package test;

public class calcExcutionTime implements Runnable{
    private boolean exit = false;
    private Thread thread;
    private long startTime;
    private long endTime;

    private String name;

    private long duration;

    calcExcutionTime(String name){
        thread = new Thread(this);
        this.name = name;
    }

    public void start(){
        thread.start();
    }

    public void run()
    {
    startTime = System.nanoTime();
    while(!exit){}
    }

    public void stop()
    {
        exit = true;
        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println(name + " time is " + duration/1000000 + " ms");
    }
}