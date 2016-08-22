/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Solved.Introducction;

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
public class CounterfeitDollar_608 {

    private Scanner scanner;
    private FileReader fileOfficialInput;
    private FileReader fileOfficialOutput;
    private FileWriter fileMyOutput;
    private FileReader fileMyOutput_reader;
    private File file_3;
    private PrintStream PS;
    private CounterfeitDollar_608(){
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
        int n=scanner.nextInt();
        while(n-->0){
            int countHeavier[]=new int[12];
            int countLighter[]=new int[12];
            Arrays.fill(countHeavier,0);
            Arrays.fill(countLighter,0);
            for(int i=0;i<3;i++){
                String L=scanner.next();
                String R=scanner.next();
                String w=scanner.next();
                boolean pH=false,pL=false;
                for(char c='A';c<='L';c++){
                    if(w.equals("even")){
                        pH=!present(L,c) && !present(R,c);
                        pL=!present(L,c) && !present(R,c);                        
                    }else if(w.equals("up")){
                        pH=present(L,c);
                        pL=present(R,c);                        
                    }else if(w.equals("down")){
                        pH=present(R,c);
                        pL=present(L,c);                        
                    }
               
                    if(pH){
                        countHeavier[c-'A']++;

                    }
                    if(pL){
                        countLighter[c-'A']++;
                    }
                }
                
            }
            
            char ansH='$',ansL='$';
            int countMax=0,countmax=0;
            int indxM=0,indxm=0;
            int vM=0,vm=0;
            for(int i=0;i<12;i++){
                if(countHeavier[i]>vM){
                    countMax=1;
                    vM=countHeavier[i];
                    indxM=i;
                }else{
                    if(countHeavier[i]==vM){
                        countMax++;
                    }
                }
                if(countLighter[i]>vm){
                    countmax=1;
                    vm=countLighter[i];
                    indxm=i;
                }else{
                    if(countLighter[i]==vm){
                        countmax++;
                    }
                }
            }
            for(int i=0;i<12;i++){
               // PS.println(String.format("%c: (%2d-%2d)",(char)(i+'A'),countHeavier[i],countLighter[i]));
            }
            if(countMax==1 && vM==3){
                PS.println(
                        String.format("%c is the counterfeit coin and it is heavy.",(char)(indxM+'A')));
            }else{
                if(countmax==1 && vm==3){
                    PS.println(
                        String.format("%c is the counterfeit coin and it is light.",(char)(indxm+'A')));
                }else{
                    throw new AssertionError("LEL");
                }
            }
            //
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
    public boolean present(String w, char c){
        return w.indexOf(c)>=0;
    }
    public static void main(String[] args){
        CounterfeitDollar_608 solver=new CounterfeitDollar_608();
        solver.solve();       
    }
}
