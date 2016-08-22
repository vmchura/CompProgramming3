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
public class CancerOrScorpio_11947 {

    private Scanner scanner;
    private FileReader fileOfficialInput;
    private FileReader fileOfficialOutput;
    private FileWriter fileMyOutput;
    private FileReader fileMyOutput_reader;
    private File file_3;
    private PrintStream PS;
    private CancerOrScorpio_11947(){
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
        int T=scanner.nextInt();
        for(int caso=1;caso<=T;caso++){
            int mmddyyyy=scanner.nextInt();
            int yyyy=mmddyyyy%10000;
            int mmdd=mmddyyyy/10000;
            int dd=mmdd%100;
            int mm=mmdd/100;
            GregorianCalendar gc=new GregorianCalendar(yyyy,mm-1,dd);
            gc.add(Calendar.WEEK_OF_YEAR, 40);
            String ans="";
            switch(gc.get(Calendar.MONTH)){
                case Calendar.JANUARY:
                    if(gc.get(Calendar.DAY_OF_MONTH)<=20){
                        ans="capricorn";
                    }else{
                        ans="aquarius";
                    }
                    break;
                case Calendar.FEBRUARY:
                    if(gc.get(Calendar.DAY_OF_MONTH)<=19){
                        ans="aquarius";
                    }else{
                        ans="pisces";
                    }
                    break;
                case Calendar.MARCH:
                    if(gc.get(Calendar.DAY_OF_MONTH)<=20){
                        ans="pisces";
                    }else{
                        ans="aries";
                    }
                    break;
                case Calendar.APRIL:
                    if(gc.get(Calendar.DAY_OF_MONTH)<=20){
                        ans="aries";
                    }else{
                        ans="taurus";
                    }
                    break;
                case Calendar.MAY:
                    if(gc.get(Calendar.DAY_OF_MONTH)<=21){
                        ans="taurus";
                    }else{
                        ans="gemini";
                    }
                    break;
                case Calendar.JUNE:
                    if(gc.get(Calendar.DAY_OF_MONTH)<=21){
                        ans="gemini";
                    }else{
                        ans="cancer";
                    }
                    break;
                case Calendar.JULY:
                    if(gc.get(Calendar.DAY_OF_MONTH)<=22){
                        ans="cancer";
                    }else{
                        ans="leo";
                    }
                    break;
                case Calendar.AUGUST:
                    if(gc.get(Calendar.DAY_OF_MONTH)<=21){
                        ans="leo";
                    }else{
                        ans="virgo";
                    }
                    break;
                case Calendar.SEPTEMBER:
                    if(gc.get(Calendar.DAY_OF_MONTH)<=23){
                        ans="virgo";
                    }else{
                        ans="libra";
                    }
                    break;
                case Calendar.OCTOBER:
                    if(gc.get(Calendar.DAY_OF_MONTH)<=23){
                        ans="libra";
                    }else{
                        ans="scorpio";
                    }
                    break;
                case Calendar.NOVEMBER:
                    if(gc.get(Calendar.DAY_OF_MONTH)<=22){
                        ans="scorpio";
                    }else{
                        ans="sagittarius";
                    }
                    break;
                case Calendar.DECEMBER:
                    if(gc.get(Calendar.DAY_OF_MONTH)<=22){
                        ans="sagittarius";
                    }else{
                        ans="capricorn";
                    }
                    break;
            }
            String day=""+gc.get(Calendar.DAY_OF_MONTH);
            String month=""+(gc.get(Calendar.MONTH)+1);
            String year=""+gc.get(Calendar.YEAR);
            while(day.length()<2)
                day="0"+day;
            while(month.length()<2)
                month="0"+month;
            while(year.length()<4)
                year="0"+year;
            PS.println(String.format("%d %s/%s/%s %s",caso,month,day,year,ans));
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
        CancerOrScorpio_11947 solver=new CancerOrScorpio_11947();
        solver.solve();       
    }
}
