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
public class Jollo_12247 {

private Scanner scanner;
    private FileReader fileOfficialInput;
    private FileReader fileOfficialOutput;
    private FileWriter fileMyOutput;
    private FileReader fileMyOutput_reader;
    private File file_3;
    private PrintStream PS;
    private Jollo_12247(){
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
        boolean dealt[]=new boolean[53];
        int[] A=new int[3];
        int[] B=new int[3];
        while(scanner.hasNext()){
            
            Arrays.fill(dealt,false);
            for(int i=0;i<3;i++){
                B[i]=scanner.nextInt();
                dealt[B[i]]=true;
            }
            if(B[0]==0)
                break;
            for(int i=0;i<2;i++){
                A[i]=scanner.nextInt();
                dealt[A[i]]=true;
            }
            int answer=-1;
            for(int i=1;i<=52;i++){
                if(!dealt[i]){
                    A[2]=i;
                    int k=countTimesAWins(A, B, 0, 0);
         //           PS.println(k);
                    if(k>=6){
                        answer=i;
                        break;
                    }
                }
            }
            
            PS.println(answer);
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
    public int countTimesAWins(int[] A,int[] B,int game,int points){
       /*
        for(int i=0;i<3;i++){
            PS.print(A[i]+" ");
        }
        PS.println(" p:"+points+" g:"+game);
               */
        if(game==3){
            if(points>=2)
                return 1; 
            return 0;
        }
        int ans=0;
        for(int i=0;i<3;i++){
            if(A[i]!=-1){
                int x=A[i];
                A[i]=-1;
                ans+=countTimesAWins(A,B,game+1,points+(x>B[game]?1:0));
                A[i]=x;
            }
        }
        return ans;
    }
    public boolean firstWins(int[] A,int[] B){
        int pointsA=0;
        for(int i=0;i<3;i++)
            pointsA+=A[i]>B[i]?1:0;
        return pointsA>=2;
    }
    public static void main(String[] args){
        Jollo_12247 solver=new Jollo_12247();
        solver.solve();       
    }
}
