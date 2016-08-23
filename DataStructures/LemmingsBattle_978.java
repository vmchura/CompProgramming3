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
/**
 *
 * @author vchura
 */
public class LemmingsBattle_978 {

    private Scanner scanner;
    private FileReader fileOfficialInput;
    private FileReader fileOfficialOutput;
    private FileWriter fileMyOutput;
    private FileReader fileMyOutput_reader;
    private File file_3;
    private PrintStream PS;
    private LemmingsBattle_978(){
            try{
                File file_alwaysExists=new File("src/ParserToSubmit/ParserToMainJava.java");
                if(!file_alwaysExists.exists()){
                    throw new AssertionError();
                }
                
                File file_1=new File("src/Tried/DataStructures/"+this.getClass().getSimpleName()+"_input.txt");
                File file_2=new File("src/Tried/DataStructures/"+this.getClass().getSimpleName()+"_output.txt");
                     file_3=new File("src/Tried/DataStructures/"+this.getClass().getSimpleName()+"_myoutput.txt");
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
        int casos=scanner.nextInt();
        Comparator<Integer> reverseCmp= new Comparator<Integer>(){

                @Override
                public int compare(Integer o1, Integer o2) {
                    return o2.compareTo(o1);
                }
                
            };
        while(casos-->0){
            
            final int B=scanner.nextInt();
            int NG=scanner.nextInt();
            int NB=scanner.nextInt();
            PriorityQueue<Integer> greenArmy=new PriorityQueue<>(NG,Collections.reverseOrder());
            PriorityQueue<Integer> blueArmy=new PriorityQueue<>(NB,Collections.reverseOrder());
            int[] results=new int[B];
            for(int i=0;i<NG;i++){
                greenArmy.add(scanner.nextInt());
            }
            for(int i=0;i<NB;i++){
                blueArmy.add(scanner.nextInt());
            }
            /*
            System.out.println("BLUE ");
            
                for(Iterator<Integer> iter=blueArmy.iterator();iter.hasNext();){
                    System.out.println(iter.next());
                }
                System.out.println("GREEN ");
                 for(Iterator<Integer> iter=greenArmy.iterator();iter.hasNext();){
                    System.out.println(iter.next());
                }
                    */
                
            while(NG>0 && NB>0){
                
                int b=Math.min(B, Math.min(NG, NB));
            
                NG-=b;
                NB-=b;
                for(int i=0;i<b;i++){
                    results[i]=greenArmy.poll()-blueArmy.poll();
                }
                for(int i=0;i<b;i++){
                    if(results[i]>0){
                        NG++;
                        greenArmy.add(results[i]);
                    }else{
                        if(results[i]<0){
                            NB++;
                            blueArmy.add(-results[i]);
                        }
                    }
                }
            }
            if(NG==0 && NB==0){
                PS.println("green and blue died");
            }else{
                PriorityQueue<Integer> survivors=null;
                if(NG>0){
                    PS.println("green wins");
                    survivors=greenArmy;
                }else{
                    PS.println("blue wins");
                    survivors=blueArmy;
                }
                while(!survivors.isEmpty()){
                    PS.println(survivors.poll());
                }
            }
            
            
            if(casos>0)
                PS.println();
            
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
        LemmingsBattle_978 solver=new LemmingsBattle_978();
        solver.solve();       
    }
}
