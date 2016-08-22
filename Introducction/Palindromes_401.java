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
public class Palindromes_401 {

    private final String character="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final String reversed ="A---3--HIL-JM-O---2TUVWXY501SE-Z--8-"; 
    private Scanner scanner;
    private FileReader fileOfficialInput;
    private FileReader fileOfficialOutput;
    private FileWriter fileMyOutput;
    private FileReader fileMyOutput_reader;
    private File file_3;
    private PrintStream PS;
    private Palindromes_401(){
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
            String str=scanner.next();
            char[] W=str.toCharArray();
            if(Mirrored(W) && RegularPalindrome(W)){
                PS.println(String.format("%s -- is a mirrored palindrome.\n",str));
            }else{
                if(Mirrored(W)){
                PS.println(String.format("%s -- is a mirrored string.\n",str));    
                }else{
                    if(RegularPalindrome(W)){
                PS.println(String.format("%s -- is a regular palindrome.\n",str));        
                    }else{
                PS.println(String.format("%s -- is not a palindrome.\n",str));        
                    }
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
    public boolean RegularPalindrome(final char[] W){
        int i=0;
        int j=W.length-1;
        while(i<j){
            if(W[i]!=W[j])
                return false;
            i++;
            j--;
        }
        return true;
        
    }
    public boolean Mirrored(final char[] W){
        int i=0;
        int j=W.length-1;
        while(i<=j){
            
            char charReversed=reversed.charAt(character.indexOf(W[i]));
            if(charReversed!=W[j])
                return false;
            i++;
            j--;
        }
        return true;
    }
    public static void main(String[] args){
        Palindromes_401 solver=new Palindromes_401();
        solver.solve();
    }
}
