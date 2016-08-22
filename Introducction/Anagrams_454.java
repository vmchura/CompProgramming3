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
public class Anagrams_454 {

   private Scanner scanner;
    private FileReader fileOfficialInput;
    private FileReader fileOfficialOutput;
    private FileWriter fileMyOutput;
    private FileReader fileMyOutput_reader;
    private File file_3;
    private PrintStream PS;
    private Anagrams_454(){
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
        DuplaString[] todos=new DuplaString[101];
        String[] ans=new String[101*101];
        int currentGroup;
        int Length=0;
        int casos=Integer.parseInt(scanner.nextLine());
        scanner.nextLine();
        String grupoAnterior="";
        while(casos-->0){
            Length=0;
            currentGroup=-1;
            grupoAnterior="";
            while(scanner.hasNext()){

                String s=scanner.nextLine();
                if(s.isEmpty())
                    break;
                todos[Length++]=new DuplaString(s);
            }
            Arrays.sort(todos,0,Length);
            //todos.sort(null);
            int indxAns=0;
            for(int i=0;i<Length-1;i++){
                for(int j=i+1;j<Length;j++){
                    if(todos[i].a.compareTo(todos[j].a)==0){
                       
                        ans[indxAns++]=String.format("%s = %s",todos[i].b,todos[j].b);
                    }
                }
            }
            Arrays.sort(ans,0,indxAns);
            for(int i=0;i<indxAns;i++){
                PS.println(ans[i]);
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
    private class DuplaString implements Comparable<DuplaString>{
        String a,b;

        public DuplaString(String b) {
            
            this.b=b;
            char[] array=b.toCharArray();
            Arrays.sort(array);
            
            StringBuilder stringBuilder=new StringBuilder();
            for(char c:array){
                if(c!=' ')
                    stringBuilder.append(c);
            }
            this.a=stringBuilder.toString();
        }

        @Override
        public int compareTo(DuplaString o) {
            if(a.compareTo(o.a)!=0)
                return a.compareTo(o.a);
            else return b.compareTo(o.b);
        }
        
    }
    public static void main(String[] args){
        Anagrams_454 solver=new Anagrams_454();
        solver.solve();
    }
}
