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
public class MobileCasanova_12085 {

    private Scanner scanner;
    private FileReader fileOfficialInput;
    private FileReader fileOfficialOutput;
    private FileWriter fileMyOutput;
    private FileReader fileMyOutput_reader;
    private File file_3;
    private PrintStream PS;
    private MobileCasanova_12085(){
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
        int caso=0;
        while(scanner.hasNext()){
            
            int N=scanner.nextInt();
            if(N==0)
                break;
            PS.println(String.format("Case %d:",++caso));
            long last=-1;
            long count=1;
            String init_str="";
            String last_str="";
            for(int i=0;i<N;i++){
                String current_str=scanner.next();
                long current=Long.parseLong(current_str);
                if(last+1L==current){
                    count++;
                }else{
                    if(last!=-1){
                        if(count==1){
                            PS.println(last_str);
                        }else{
                            
                            PS.print(init_str+"-");
                            for(int j=0;j<init_str.length();j++){
                                if(init_str.charAt(j)!=last_str.charAt(j)){
                                    PS.println(last_str.substring(j));
                                    break;
                                }
                            }
                        }

                        
                    }
                    init_str=current_str;                    
                    count=1;
                }
                last=current;
                last_str=current_str;
            }
            if(count==1){
                  PS.println(last_str);
            }else{

                  PS.print(init_str+"-");
                  for(int j=0;j<init_str.length();j++){
                      if(init_str.charAt(j)!=last_str.charAt(j)){
                          PS.println(last_str.substring(j));
                          break;
                      }
                  }
              }
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
        MobileCasanova_12085 solver=new MobileCasanova_12085();
        solver.solve();       
    }
}
