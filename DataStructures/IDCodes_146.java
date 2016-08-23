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
public class IDCodes_146 {

    private Scanner scanner;
    private FileReader fileOfficialInput;
    private FileReader fileOfficialOutput;
    private FileWriter fileMyOutput;
    private FileReader fileMyOutput_reader;
    private File file_3;
    private PrintStream PS;
    private IDCodes_146(){
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
         while(scanner.hasNext()){
             char[] s=scanner.next().toCharArray();
             if(s[0]=='#')
                break;
             boolean hasPermutation=nextPermutation(s, 0);
             if(hasPermutation){
                 PS.println(new String(s));
             }else{
                 PS.println("No Successor");
             }
         }
         closeFiles();
        
    }
    public boolean nextPermutation(char[] word, int i){//inclusive
        
        final int length=word.length;
        if(i==length-1)
            return false;
        if(nextPermutation(word,i+1)){
            return true;
        }else{
           //significa que desde i+1 hasta el fin esta completamente ordenado descendentemente
            //aacd...xyz
            //azyx...dca
            //aacd...xyz
            //aacd...xyz
            
            
            
            int j=i;
            
            while(j<length && word[j]<=word[i])
                j++;
            
            
            if(j>=length){
                
                return false;
            }
            
            flip(word,i+1);
            j=i;
            while(j<length && word[j]<=word[i])
                j++;            
            
            char x=word[i];
            word[i]=word[j]; 
            word[j]=x;
            return true;
            
        }
        /*
        abc
        acb
        bac
        */
    }
    public void flip(char[] word,int i){
        int j=word.length-1;
        while(i<j){
            char x=word[i];
            word[i]=word[j];
            word[j]=x;
            i++;
            j--;
        }
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
        IDCodes_146 solver=new IDCodes_146();
        solver.solve();       
    }
}
