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
public class MatrixTranspose_10895 {

    private Scanner scanner;
    private FileReader fileOfficialInput;
    private FileReader fileOfficialOutput;
    private FileWriter fileMyOutput;
    private FileReader fileMyOutput_reader;
    private File file_3;
    private PrintStream PS;
    private MatrixTranspose_10895(){
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
        while(scanner.hasNext()){
            int N=scanner.nextInt();
            int M=scanner.nextInt();
            //ArrayList<Node>[] colsT=new ArrayList[N];
            ArrayList<Node>[] rowsT=new ArrayList[M];
            ArrayList<Integer> posNonZero=new ArrayList<>(); 
            for(int col=0;col<N;col++){
                int r=scanner.nextInt();
                posNonZero.clear();
                posNonZero.ensureCapacity(r);
                
                for(int i=0;i<r;i++){
                    posNonZero.add(scanner.nextInt()-1);
                }
                for(int i=0;i<r;i++){
                    if(rowsT[posNonZero.get(i)]==null)
                        rowsT[posNonZero.get(i)]=new ArrayList<>();
                    int val=scanner.nextInt();
                    rowsT[posNonZero.get(i)].add(new Node(col,val));
                //    System.out.println("Added to: "+posNonZero.get(i)+" value: "+col+", "+val);
                }
                
            }
            PS.println(M+" "+N);
            for(int row=0;row<M;row++){
                int r=rowsT[row]==null?0:rowsT[row].size();
                PS.print(r);
                if(r>0){
                    for(int i=0;i<r;i++){
                        PS.print(" "+(rowsT[row].get(i)._pos+1));
                    }
                    PS.println();
                    for(int i=0;i<r;i++){
                        if(i>0)
                            PS.print(" ");
                        PS.print(rowsT[row].get(i)._val);
                    }
                    PS.println();
                }else{
                    PS.println();
                    PS.println();
                }
            }
        }
         closeFiles();
        
    }
    class Node  { // utilizing Java "Generics"
        
        int _pos;
        int _val;

        public Node(int f, int s) { _pos = f; _val = s; }

        int pos() { return _pos; }
        int val() { return _val; }

        
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
        MatrixTranspose_10895 solver=new MatrixTranspose_10895();
        solver.solve();       
    }
}
