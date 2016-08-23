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
public class IntervalProduct_12532 {

    private Scanner scanner;
    private FileReader fileOfficialInput;
    private FileReader fileOfficialOutput;
    private FileWriter fileMyOutput;
    private FileReader fileMyOutput_reader;
    private File file_3;
    private PrintStream PS;
    private IntervalProduct_12532(){
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
       
        
        BIT_CountZeros counter=new BIT_CountZeros(100002);
        while(scanner.hasNext()){
            
            int N=scanner.nextInt();
            int Q=scanner.nextInt();
            
            counter.reset();
            for(int i=0;i<N;i++){
                int v=scanner.nextInt();
                counter.update(i, v);
                
            }
            StringBuilder sb=new StringBuilder();
            while(Q-->0){
                String cmd=scanner.next();
                int x=scanner.nextInt()-1;
                int v=scanner.nextInt();
                if(cmd.equals("C")){
                    counter.update(x, v);
                    
                }else{
                    int res=counter.query(x, v-1);
                    
                    if(res==0){
                        sb.append('0');
                    }else{
                        
                        sb.append(res>0?'+':'-');
                    }
                }
            }
            PS.println(sb.toString());
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
        IntervalProduct_12532 solver=new IntervalProduct_12532();
        solver.solve();       
    }
}

class BIT_CountZeros{
    final int[] bitZeros;
    final int[] bitNeg;
    final int[] A;
    final int L;
    public BIT_CountZeros(int L) {
        this.L=L;
        this.bitZeros=new int[L];
        this.bitNeg=new int[L];
        A=new int[L];
    }
    
    private void update(int x,boolean val,int[] bit){
        
        for(;x<L;x+=x&-x){
            bit[x]+=val?1:-1;
          //  System.out.println(x+"]"+bit[x]);
        }
    }
    /**
     * 
     * @param x 0 indxed
     * @param newVal 
     */
    public void update(int x,int newVal){
        x++;
        //System.out.println("Zeroz quitando");
        if(A[x]==0){
            update(x,false,bitZeros);
        }
        //System.out.println("neg quitando");
        if(A[x]<0){
            update(x,false,bitNeg);
        }
        A[x]=newVal;
        //System.out.println("Zeroz aumentando");
        if(A[x]==0)
            update(x,true,bitZeros);
        //System.out.println("neg aumentando");
        if(A[x]<0)
            update(x,true,bitNeg);
    }
    /**
     * 
     * @param x 0 index
     * @return 
     */
    private int query(int x,int[] bit){
        
        int ans=0;
        for(;x>0;x-=x&-x){
            ans+=bit[x];
        }
        return ans;
    }
    public int query(int x,int y){
        x++;
        y++;
        
        int cantZeros=query(x,y,bitZeros);
        if(cantZeros>0)
            return 0;
        int cantNeg=query(x,y,bitNeg);
        return cantNeg%2==1?-1:1;
    }
    
    public void reset(){
       Arrays.fill(bitZeros, 0);
       Arrays.fill(bitNeg, 0);
       Arrays.fill(A, 1);
    }

    private int query(int x, int y, int[] bit) {
     return query(y,bit)-query(x-1,bit);   
    }
    
}