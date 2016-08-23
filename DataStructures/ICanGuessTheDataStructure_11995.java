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
public class ICanGuessTheDataStructure_11995 {

    private Scanner scanner;
    private FileReader fileOfficialInput;
    private FileReader fileOfficialOutput;
    private FileWriter fileMyOutput;
    private FileReader fileMyOutput_reader;
    private File file_3;
    private PrintStream PS;
    private ICanGuessTheDataStructure_11995(){
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
        Comparator<Integer> cmpReversed=new Comparator<Integer>() {

            @Override
            public int compare(Integer o1, Integer o2) {
               return o2.compareTo(o1);
            }
        };
        while(scanner.hasNext()){
            int N=scanner.nextInt();
            Stack<Integer> stack=new Stack<>();
            Queue<Integer> queue=new LinkedBlockingQueue<>();
            PriorityQueue<Integer> priorityQueue=new PriorityQueue<>(N,cmpReversed);
            boolean isStack=true;
            boolean isQueue=true;
            boolean isPQ=true;
            while(N-->0){
                int cmd=scanner.nextInt();
                int x=scanner.nextInt();
                if(cmd==1){
                    if(isStack)
                        stack.add(x);
                    if(isQueue)
                        queue.add(x);
                    if(isPQ)
                        priorityQueue.add(x);
                }else{
                    if(isStack){
                        if(stack.empty()){
                            isStack=false;
                            continue;
                        }
                        int y=stack.pop();
                        isStack=(x==y);
                    }
                    if(isQueue){
                        if(queue.isEmpty()){
                            isQueue=false;
                            continue;
                        }
                        int y=queue.poll();
                        isQueue=(x==y);
                    }
                    if(isPQ){
                        if(priorityQueue.isEmpty()){
                            isPQ=false;
                            continue;
                        }
                        int y=priorityQueue.poll();
                        //System.out.println("PQ extracted "+y);
                        isPQ=(x==y);
                    }
                }
            }
            if(isStack){
                if(isPQ || isQueue){
                    PS.println("not sure");
                }else{
                    PS.println("stack");
                }
            }else{
                if(isPQ){
                    if(isQueue){
                        PS.println("not sure");
                    }else{
                        PS.println("priority queue");
                    }
                }else{
                    if(isQueue){
                        PS.println("queue");
                    }else{
                        PS.println("impossible");
                    }
                }
            }
            //System.out.println("-");
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
        ICanGuessTheDataStructure_11995 solver=new ICanGuessTheDataStructure_11995();
        solver.solve();       
    }
}
