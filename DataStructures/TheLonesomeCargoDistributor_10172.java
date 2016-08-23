/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Tried.DataStructures;

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
public class TheLonesomeCargoDistributor_10172 {

    private Scanner scanner;
    private FileReader fileOfficialInput;
    private FileReader fileOfficialOutput;
    private FileWriter fileMyOutput;
    private FileReader fileMyOutput_reader;
    private File file_3;
    private PrintStream PS;
    private TheLonesomeCargoDistributor_10172(){
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
         int SET=scanner.nextInt();
         while(SET-->0){
             int N=scanner.nextInt();
             int S=scanner.nextInt();
             int Q=scanner.nextInt();
             Queue<Integer>[] B=new Queue[N];
             int packagesDeparted=0;
             for(int i=0;i<N;i++){
                 int Qi=scanner.nextInt();
                 packagesDeparted+=Qi;
                 B[i]=new LinkedList<>();
                         
                 while(Qi-->0){
                     int dest=scanner.nextInt()-1;
                     B[i].add(dest);
                 }
             }
             Stack<Integer> carry=new Stack<>();
             int t=0;
             for(int station=0;packagesDeparted>0;t+=2,station=((station+1)>=N?0:(station+1))){
                 while(!carry.empty()){
                     int x=carry.peek();
                     if(x==station){
                         carry.pop();
                         packagesDeparted--;
                         
                         t++;
                         continue;
                     }
                     if(B[station].size()<Q){
                         B[station].add(carry.pop());
                         t++;
                     }else{
                         break;
                     }
                 }
                 while(!B[station].isEmpty()){
                     if(carry.size()<S){
                        carry.add(B[station].poll());
                        t++;
                     }else{
                         break;
                     }
                 }
             }
             PS.println(t-2);
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
        TheLonesomeCargoDistributor_10172 solver=new TheLonesomeCargoDistributor_10172();
        solver.solve();       
    }
}
