/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Solved.DataStructures;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;
/**
 *
 * @author vchura
 */
public class WhatIsTheMedian_10107 {

    private Scanner scanner;
    private FileReader fileOfficialInput;
    private FileReader fileOfficialOutput;
    private FileWriter fileMyOutput;
    private FileReader fileMyOutput_reader;
    private File file_3;
    private PrintStream PS;
    private WhatIsTheMedian_10107(){
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
        PriorityQueue<Integer> R=new PriorityQueue<Integer>();
        PriorityQueue<Integer> L=new PriorityQueue<Integer>(1, new Comparator<Integer>(){

            @Override
            public int compare(Integer o1, Integer o2) {
               return o2.compareTo(o1);
            }
        });
         while(scanner.hasNext()){
             int x=Integer.parseInt(scanner.next());
             //System.out.println("Inserted: "+x);
             if(0==L.size()){
                 L.add(x);
             }else{
                 int m=L.peek();                 
                if(x<=m){
                    L.add(x);
                    //System.out.println("added to L because  x<=m "+m);
                }else{
                    R.add(x);
                    //System.out.println("added to R because  x>m "+m);
                }
                 
                 
             }
             if(L.size()-2>=R.size()){
                 int m=L.poll();
                 R.add(m);
               //  System.out.println("L to R   "+m);
             }else{
                 if(L.size()<=R.size()-2){
                     
                     int M=R.poll();
                 //    System.out.println("R to L   "+M);
                     L.add(M);
                 }
             }
             if(L.size()==R.size()){
                 int m=L.peek();
                 int M=R.peek();
                 PS.println((m+M)>>1);
             }else{
                 if(L.size()-1==R.size()){
                    int m=L.peek();
                 
                    PS.println(m);    
                 }else{
                 
                    int M=R.peek();
                    PS.println(M);    
                 }
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
        WhatIsTheMedian_10107 solver=new WhatIsTheMedian_10107();
        solver.solve();       
    }
}
