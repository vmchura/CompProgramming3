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
public class HangmanJudge_489 {

    private Scanner scanner;
    private FileReader fileOfficialInput;
    private FileReader fileOfficialOutput;
    private FileWriter fileMyOutput;
    private FileReader fileMyOutput_reader;
    private File file_3;
    private PrintStream PS;
    private HangmanJudge_489(){
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
               int round=scanner.nextInt();
               if(round==-1)
                   break;
               String key=scanner.next();
               String guesses=scanner.next();
               boolean[] mustBeGuessed=new boolean[27];
               boolean[] presentInTheGuess=new boolean[27];
               Arrays.fill(mustBeGuessed, false);
               Arrays.fill(presentInTheGuess, false);
               int toWin=0;
               int toLose=0;
               for(int i=0;i<key.length();i++){
                   int idx=key.charAt(i)-'a';
                   if(!mustBeGuessed[idx])
                       toWin++;
                   mustBeGuessed[idx]=true;
               }
               boolean itHasAnswer=false;
               PS.println(String.format("Round %d",round));
               for(int i=0;i<guesses.length();i++){
                   int idx=guesses.charAt(i)-'a';
                   if(mustBeGuessed[idx]){
                       if(!presentInTheGuess[idx])
                           toWin--;
                   }else{
                     toLose++;  
                   }
                   presentInTheGuess[idx]=true;
                   
                   if(toLose>=7){
                       PS.println("You lose.");
                       itHasAnswer=true;
                       break;
                   }
                   if(toWin<=0){
                       PS.println("You win.");
                       itHasAnswer=true;
                       break;
                   }
               }
               if(!itHasAnswer)
                   PS.println("You chickened out.");
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
        HangmanJudge_489 solver=new HangmanJudge_489();
        solver.solve();
    }
}
