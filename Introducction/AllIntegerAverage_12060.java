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
public class AllIntegerAverage_12060 {

    private Scanner scanner;
    private FileReader fileOfficialInput;
    private FileReader fileOfficialOutput;
    private FileWriter fileMyOutput;
    private FileReader fileMyOutput_reader;
    private File file_3;
    private PrintStream PS;
    private AllIntegerAverage_12060(){
            try{
                File file_alwaysExists=new File("src/ParserToSubmit/ParserToMainJava.java");
                if(!file_alwaysExists.exists()){
                    throw new AssertionError();
                }
                
                File file_1=new File("src/Solved/"+this.getClass().getSimpleName()+"_input.txt");
                File file_2=new File("src/Solved/"+this.getClass().getSimpleName()+"_output.txt");
                     file_3=new File("src/Solved/"+this.getClass().getSimpleName()+"_myoutput.txt");
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
        int caso=0;
        while(scanner.hasNext()){
            int N=scanner.nextInt();
            if(N<=0)
                break;
            int suma=0;
            for(int i=0;i<N;i++){
                suma+=scanner.nextInt();
            }
            boolean neg=false;
            if(suma<0){
                neg=true;
                suma=-suma;
            }
            int a=suma/N;
            int b=suma%N;
            int c=N;
            int g=gcd(b,c);
            b/=g;
            c/=g;
            PS.println(String.format("Case %d:",++caso));
            if(b==0){
                if(neg){
                    PS.println("- "+a);
                }else{
                    PS.println(a);
                }
            }else{
                String c_str=""+c;
                String b_str=""+b;
                while(b_str.length()<c_str.length()){
                    b_str=" "+b_str;
                }
                String line="";
                while(line.length()<c_str.length()){
                    line="-"+line;
                }
                String a_str="";
                if(neg){
                    if(a!=0){
                        a_str="- "+a;
                    }else{
                        a_str="- ";
                    }
                }else{
                    if(a!=0){
                        a_str=""+a;
                    }
                }
                String offset="";
                for(int i=0;i<a_str.length();i++)
                    offset=" "+offset;
                
                PS.println(offset+b_str);
                PS.println(a_str+line);
                PS.println(offset+c_str);
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
    public int gcd(int a,int b){
        //a<b
        int gcd=1;
        for(int i=2;i<=a;i++){
            if(a%i==0 && b%i==0){
                a/=i;
                b/=i;
                gcd*=i;
                i--;
            }
        }
        return gcd;
    }
    
    public static void main(String[] args){
        AllIntegerAverage_12060 solver=new AllIntegerAverage_12060();
        solver.solve();       
    }
}
