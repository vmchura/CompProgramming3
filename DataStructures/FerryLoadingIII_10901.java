/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Tried.DataStructures;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
/**
 *
 * @author vchura
 */
public class FerryLoadingIII_10901 {

    private Scanner scanner;
    private FileReader fileOfficialInput;
    private FileReader fileOfficialOutput;
    private FileWriter fileMyOutput;
    private FileReader fileMyOutput_reader;
    private File file_3;
    private PrintStream PS;
    public static enum Side{
        Left,Right
    }
    private FerryLoadingIII_10901(){
            try{
                File file_alwaysExists=new File("src/ParserToSubmit/ParserToMainJava.java");
                if(!file_alwaysExists.exists()){
                    throw new AssertionError();
                }
                
                File file_1=new File("src/Tried/"+this.getClass().getSimpleName()+"_input.txt");
                File file_2=new File("src/Tried/"+this.getClass().getSimpleName()+"_output.txt");
                     file_3=new File("src/Tried/"+this.getClass().getSimpleName()+"_myoutput.txt");
                if(file_3.exists())
                    file_3.delete();
                file_3.createNewFile();
                        
                
                if(file_1.exists() && file_2.exists()){
                    fileOfficialInput=new FileReader(file_1);
                    fileOfficialOutput=new FileReader(file_2);
                    
                    PS=new PrintStream(file_3);
                    scanner=new Scanner(fileOfficialInput);
                    
                }else{
                    file_1.createNewFile();
                    file_2.createNewFile();
                           Runtime.getRuntime().exec("gedit "+file_1.getAbsolutePath()+" "+file_2.getAbsolutePath());
                    throw new IllegalStateException("files input and output dont exist, created");
                }
                
                
            }catch(AssertionError ex){
                scanner=new Scanner(System.in);
                PS=System.out;
            }catch(IOException | IllegalStateException ex){
                System.err.println(ex);
                System.exit(1);
            }                        
    }
    public void solve(){
        int c=scanner.nextInt();
        while(c-->0){
           
            int n=scanner.nextInt();
            int t=scanner.nextInt();
            int m=scanner.nextInt();
            
            Queue<Car> leftQueue=new LinkedBlockingQueue<>();
            Queue<Car> rightQueue=new LinkedBlockingQueue<>();
            Side currentSide=Side.Left;
            int currentTime=0;
            Car[] carros=new Car[m];
            
            for(int i=0;i<m;i++){
                int arrivalTime=scanner.nextInt();
                String side=scanner.next();
                Car carro;
                if(side.equals("left")){
                    carro=new Car(arrivalTime,Side.Left);
                }else{
                    carro=new Car(arrivalTime,Side.Right);
                }
                carros[i]=carro;
            }
            int indxCarros=0;
            while(indxCarros<m || !leftQueue.isEmpty() || !rightQueue.isEmpty()){
                while(indxCarros<m){
                    if(carros[indxCarros].arrivalTime<=currentTime){
                        if(carros[indxCarros].side==Side.Left){
                            leftQueue.add(carros[indxCarros]);
                          //   System.out.println("added to left: "+carros[indxCarros].getID());
                        }else{
                            rightQueue.add(carros[indxCarros]);
                        //    System.out.println("added to right: "+carros[indxCarros].getID());
                        }
                        indxCarros++;
                    }else{
                        break;
                    }
                }
                
                int carsLoaded=0;
                if(currentSide==Side.Left){
                    while(!leftQueue.isEmpty() && 
                            carsLoaded<n ){
                        Car carro=leftQueue.poll();
                        carro.setDepartedTime(currentTime+t);
                        carsLoaded++;
       //                 System.out.println("loaded in left and dropped: "+carro.getID());
                    }
                    
                }else{
                    while(!rightQueue.isEmpty() && 
                            carsLoaded<n ){
                        Car carro=rightQueue.poll();
                        carro.setDepartedTime(currentTime+t);
                        carsLoaded++;
     //                   System.out.println("loaded in right and dropped: "+carro.getID());
                    }
                }
                if(carsLoaded>0 ||  !leftQueue.isEmpty() || !rightQueue.isEmpty()){
                    currentTime+=t;
                    if(currentSide==Side.Left)
                        currentSide=Side.Right;
                    else
                        currentSide=Side.Left;
                }else{
                    currentTime++;
                }
            }
            for(int i=0;i<m;i++){
                PS.println(carros[i].getDepartedTime());
            }
            if(c>0){
                PS.println();
            }
        }
         closeFiles();
        
    }
    private void closeFiles() {
        if(PS==System.out)
            return;
        scanner.close();
        PS.close();
        try{
            
            if(fileOfficialInput!=null)
                fileOfficialInput.close();
            if(fileMyOutput!=null)
                fileMyOutput.close();
            
            if(fileOfficialOutput!=null){
                fileMyOutput_reader=new FileReader(file_3);
                while(true){
                    int x=fileMyOutput_reader.read();
                    int y=fileOfficialOutput.read();
                    
                    if(x<0 && y<0){
                        System.out.println("Archivos iguales");
                        break;
                    }
                    
                    if(x<0 && y>=0){
                        System.out.println("El archivo creado tiene menos bytes que el oficial");
                        System.exit(1);
                    }
                    if(y<0 ){
                        System.out.println("El archivo creado tiene mas bytes que el oficial");                        
                        System.exit(1);
                    }
                    
                    if(x!=y){
                        System.out.println("Mientras se comparaba, hay un byte de diferencia");                        
                        System.exit(1);
                    }
                        
                }
                fileOfficialOutput.close();
                fileMyOutput_reader.close();
            }
            
            
        }catch(Exception ex){
            
        }
    }
    public static void main(String[] args){
        FerryLoadingIII_10901 solver=new FerryLoadingIII_10901();
        solver.solve();       
    }
}
class Car{
    int arrivalTime;
    private int departedTime;
    FerryLoadingIII_10901.Side side;
    private static int countCar=0;
    private int carID;
    Car(int arrivalTime, FerryLoadingIII_10901.Side side){
        this.arrivalTime=arrivalTime;
        this.side=side;
        carID=++countCar;
    }
    public void setDepartedTime(int departedTime){
        this.departedTime=departedTime;
    }
    public int getDepartedTime(){
        return departedTime;
    }
    public int getID(){
        return carID;
    }
}