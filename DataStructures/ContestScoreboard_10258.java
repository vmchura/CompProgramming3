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
public class ContestScoreboard_10258 {

    private Scanner scanner;
    private FileReader fileOfficialInput;
    private FileReader fileOfficialOutput;
    private FileWriter fileMyOutput;
    private FileReader fileMyOutput_reader;
    private File file_3;
    private PrintStream PS;
    private ContestScoreboard_10258(){
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
         int B=Integer.parseInt(scanner.nextLine());
         scanner.nextLine();
         while(B-->0){
             Team[] crowd=new Team[101];
             for(int i=0;i<101;i++){
                 crowd[i]=new Team(i+1);
             }
             while(scanner.hasNext()){
                String s=scanner.nextLine();
              //  System.out.println(s);
                 if(s.isEmpty() || s.equals("") || s.equals("\n"))
                    break;
                
                String[] ws=s.split(" ");
                int contestant=Integer.parseInt(ws[0])-1;
                int prob=Integer.parseInt(ws[1]);
                int time=Integer.parseInt(ws[2]);
                char cmd=ws[3].charAt(0);
                crowd[contestant].submission(prob, time, cmd);
             }
             Arrays.sort(crowd);
             for(int i=0;i<101;i++){
                 if(crowd[i].madeSubmission){
                     PS.println(crowd[i].toString());
                 }else{
                     break;
                 }
             }
             if(B>0){
                 PS.println();
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
        ContestScoreboard_10258 solver=new ContestScoreboard_10258();
        solver.solve();       
    }
}
class Team implements Comparable<Team>{
    int[] time;
    BitSet solved;
    int number;
    boolean madeSubmission=false;
    Team(int number){
        this.number=number;
        time=new int[10];
        solved=new BitSet(10);
    }
    void submission(int p,int min,char cmd){
        madeSubmission=true;
        if(solved.get(p))
            return;
        if(cmd=='C'){
           time[p]+=min;
           solved.set(p);
        }else{
            if(cmd=='I'){
                time[p]+=20;
            }
        }
    }
    int getPenalty(){
        int ans=0;
        for(int i=0;i<10;i++){
            ans+=solved.get(i)?time[i]:0;
        }
        return ans;
    }
    int probsSolved(){
        int ans=0;
        for(int i=0;i<10;i++){
            ans+=solved.get(i)?1:0;
        }
        return ans;
    }

    @Override
    public int compareTo(Team o) {
        if(!madeSubmission)
            return 1;
        if(!o.madeSubmission)
            return -1;
        if(probsSolved()!=o.probsSolved())
            return probsSolved()>o.probsSolved()?-1:1;
        if(getPenalty()!=o.getPenalty()){
            return getPenalty()<o.getPenalty()?-1:1;
        }
        return number<o.number?-1:1;
    }
    @Override public String toString(){
        return number+" "+probsSolved()+" "+getPenalty();
    }
    public boolean hasMadeASubmission(){
        return madeSubmission;
    }


}