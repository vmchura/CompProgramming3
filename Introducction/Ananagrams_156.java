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
public class Ananagrams_156 {
    
    private Scanner scanner;
    private FileReader fileOfficialInput;
    private FileReader fileOfficialOutput;
    private FileWriter fileMyOutput;
    private FileReader fileMyOutput_reader;
    private File file_3;
    private PrintStream PS;
    
    private Ananagrams_156(){
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
        
        String[] ans=new String[1000000];
        DuplaString[] todos=new DuplaString[100000];
        int LengthTodos=0;
        int LengthAns=0;
        int n=0;
        
        
        while(scanner.hasNext()){
            String key=scanner.next();
        
            if(key.equals("#"))
                break;
            
            char[] chars=key.toLowerCase().toCharArray();
            
            Arrays.sort(chars);
            String ordered=new String(chars);
        
            todos[LengthTodos++]=(new DuplaString(ordered, key));
        
            n++;
        }
        
        Arrays.sort(todos,0,LengthTodos);
        //todos.sort(null);
        
        for(int i=0;i<n;i++){
            
            boolean abajo,arriba;            
            if(i==0){
                abajo=true;
            }else{
                abajo=todos[i-1].compareTo(todos[i])!=0;
            }
            if(i==LengthTodos-1){
                arriba=true;
            }else{
                arriba=todos[i+1].compareTo(todos[i])!=0;
                
            }
            if(arriba && abajo){
                ans[LengthAns++]=(todos[i].b);
            }
        }
                
        Arrays.sort(ans,0,LengthAns);
        //ans.sort(null);
        //System.out.println("LengthAns "+LengthAns);
        
        for(int i=0;i<LengthAns;i++){
            PS.println(ans[i]);
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
           System.err.println("error closing files "+ex);
        }
    }
    private class DuplaString implements Comparable<DuplaString>{
        String a,b;

        public DuplaString(String a,String b) {
            this.a=a;
            this.b=b;
        }

        @Override
        public int compareTo(DuplaString o) {
            return a.compareTo(o.a);
        }
        
    }
    
    public static void main(String[] args){
        Ananagrams_156 solver=new Ananagrams_156();
        solver.solve();
    }
}
