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
public class BookletPrinting_637 {

   private Scanner scanner;
    private FileReader fileOfficialInput;
    private FileReader fileOfficialOutput;
    private FileWriter fileMyOutput;
    private FileReader fileMyOutput_reader;
    private File file_3;
    private PrintStream PS;
    private BookletPrinting_637(){
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
        
        while(true){
            final int cantPages=scanner.nextInt();    
            if(cantPages==0)
                break;
            final int cantSheets=(cantPages+3)/4;
            int sheet[][][]=new int[cantSheets][2][2];
            for(int i=0;i<cantSheets;i++){
                for(int j=0;j<2;j++)
                    sheet[i][j][0]=sheet[i][j][1]=0;
            }
            Puntero pointer=new  Puntero(cantSheets);
            for(int i=0;i<cantPages;i++,pointer.next()){
              //  PS.println(String.format("%d: %d %d %d",i+1,pointer.sheet,pointer.sideFB,pointer.sideLR));
                sheet[pointer.sheet-1][pointer.sideFB][pointer.sideLR]=i+1;                
            }
            PS.println(String.format("Printing order for %d pages:",cantPages));
            for(int s=0;s<cantSheets;s++){
                for(int fb=0;fb<2;fb++){
                    if((sheet[s][fb][0]+sheet[s][fb][1])!=0){
                        PS.println(String.format("Sheet %d, %s: %s, %s",
                                    s+1,
                                    fb==1?"back ":"front",
                                    sheet[s][fb][0]==0?"Blank":sheet[s][fb][0],
                                    sheet[s][fb][1]==0?"Blank":sheet[s][fb][1]));
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
    private class Puntero{
        public int sheet;
        public int sideFB; //
        public int sideLR;
        int cantSheets;
        //0 left, 1 right
        Puntero(int cantSheets){
            this.cantSheets=cantSheets;
            sheet=1;
            sideFB=0;
            sideLR=1;
        }
        public void next(){
            if(sideLR==1){
                sideLR=0;
                sideFB=1-sideFB;
            }else{
                if(sideFB==1){
                   if(sheet==cantSheets){
                       sideLR=1;
                   }else{
                       sheet++;
                       sideFB=0;
                       sideLR=1;
                   } 
                }else{
                    sheet--;
                    sideFB=1;
                    sideLR=1;
                }
            }
        }
    }
    public static void main(String[] args){
        BookletPrinting_637 solver=new BookletPrinting_637();
        solver.solve();       
    }
}
