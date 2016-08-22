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
public class BenderBRodriguezProblem_11507 {

   private Scanner scanner;
    private FileReader fileOfficialInput;
    private FileReader fileOfficialOutput;
    private FileWriter fileMyOutput;
    private FileReader fileMyOutput_reader;
    private File file_3;
    private PrintStream PS;
    private BenderBRodriguezProblem_11507(){
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
    private enum apuntaA{
        masX("+x"),menosX("-x"),masY("+y"),menosY("-y"),masZ("+z"),menosZ("-z");
        String str;
        apuntaA(String v){
            str=v;
        }
        String getStr(){
            return str;
        }
    }
    public void solve(){
            while(scanner.hasNext()){
                int L=scanner.nextInt();
                if(L<=1)
                    break;
                apuntaA ans=apuntaA.masX;
                for(int i=1;i<L;i++){
                    String cmd=scanner.next();
                    apuntaA next=ans;
                    if(!cmd.equals("No")){
                        if(cmd.equals("+y")){
                            switch(ans){
                                case masX:
                                    next=apuntaA.masY; break;
                                    
                                case menosX:
                                    next=apuntaA.menosY; break;
                                case masY:
                                    next=apuntaA.menosX; break;
                                case menosY:
                                    next=apuntaA.masX; break;
                            }
                        }else{
                            if(cmd.equals("-y")){
                                switch(ans){
                                    case masX:
                                        next=apuntaA.menosY; break;
                                    case menosX:
                                        next=apuntaA.masY; break;
                                    case masY:
                                        next=apuntaA.masX; break;
                                    case menosY:
                                        next=apuntaA.menosX; break;
                                }    
                            }else{
                                if(cmd.equals("+z")){
                                    switch(ans){
                                        case masX:
                                            next=apuntaA.masZ; break;
                                        case menosX:
                                            next=apuntaA.menosZ; break;
                                        case masZ:
                                            next=apuntaA.menosX; break;
                                        case menosZ:
                                            next=apuntaA.masX; break;
                                    }                                       
                                }else{
                                    //if(cmd.equals("-z")){
                                    switch(ans){
                                        case masX:
                                            next=apuntaA.menosZ; break;
                                        case menosX:
                                            next=apuntaA.masZ; break;
                                        case masZ:
                                            next=apuntaA.masX; break;
                                        case menosZ:
                                            next=apuntaA.menosX; break;
                                    }
                                }
                            }
                            
                            
                        }
                        
                        ans=next;
                        
                    }
                }
                PS.println(ans.getStr());
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
        BenderBRodriguezProblem_11507 solver=new BenderBRodriguezProblem_11507();
        solver.solve();       
    }
}
