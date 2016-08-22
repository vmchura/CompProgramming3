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
public class LoansomeCarBuyer_10114 {

    private Scanner scanner;
    private FileReader fileOfficialInput;
    private FileReader fileOfficialOutput;
    private FileWriter fileMyOutput;
    private FileReader fileMyOutput_reader;
    private File file_3;
    private PrintStream PS;
    private LoansomeCarBuyer_10114(){
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
        
        while(scanner.hasNext()){
        
            int months=scanner.nextInt();
            
            if(months<=0)
                break;
            
            final double downPayment=scanner.nextDouble();
            
            double loan=scanner.nextDouble();
            final double paymentMonthly=loan/months;
            int records=scanner.nextInt();
            
            
            scanner.nextInt();//0
            
            double currentDepreciation=scanner.nextDouble();
            double nextDepreciation=0.0f;
            records--;
            int next=Integer.MAX_VALUE;
            if(records>0){
                next=scanner.nextInt();
                nextDepreciation=scanner.nextDouble();
                records--;
            }
            
            
            double currentCarValue=(loan+downPayment)*(1.0-currentDepreciation);
            double currentDoubt=loan;
            /*
            PS.println(String.format(
                        "starting month: %d , depre: %f, car: %f, doubt:%f",
                        1,currentDepreciation,currentCarValue,currentDoubt));   
            */
            if(currentDoubt>currentCarValue){
                for(int i=1;i<=months;i++){
                    if(i>=next ){

                        currentDepreciation=nextDepreciation;
                        if(records>0){
                            next=scanner.nextInt();
                            nextDepreciation=scanner.nextDouble();
                            records--;
                        }else{
                            next=Integer.MAX_VALUE;
                        }

                    }




                    currentDoubt-=paymentMonthly;
                    currentCarValue=currentCarValue*(1.0-currentDepreciation);

                /*    
                    PS.println(String.format(
                            "end of month: %d , depre: %f, car: %f, doubt:%f",
                            i,currentDepreciation,currentCarValue,currentDoubt));   
                  */  
                    if(currentDoubt<currentCarValue){

                        PS.println(String.format("%d month%s",i,i==1?"":"s"));
                        break;
                    }                 
                }
            }else{
                        PS.println(String.format("0 months"));
            }
            
            while(records-->0){
                scanner.nextInt();
                scanner.nextDouble();
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
        
        LoansomeCarBuyer_10114 solver=new LoansomeCarBuyer_10114();
        
        solver.solve();       
        
    }

    
}
