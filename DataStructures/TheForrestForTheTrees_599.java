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
public class TheForrestForTheTrees_599 {

    private Scanner scanner;
    private FileReader fileOfficialInput;
    private FileReader fileOfficialOutput;
    private FileWriter fileMyOutput;
    private FileReader fileMyOutput_reader;
    private File file_3;
    private PrintStream PS;
    private TheForrestForTheTrees_599(){
            try{
                File file_alwaysExists=new File("src/ParserToSubmit/ParserToMainJava.java");
                if(!file_alwaysExists.exists()){
                    throw new AssertionError();
                }
                
                File file_1=new File("src/Tried/DataStructures/"+this.getClass().getSimpleName()+"_input.txt");
                File file_2=new File("src/Tried/DataStructures/"+this.getClass().getSimpleName()+"_output.txt");
                     file_3=new File("src/Tried/DataStructures/"+this.getClass().getSimpleName()+"_myoutput.txt");
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
        
        int testCases=scanner.nextInt();
        while(testCases-->0){
            BitSet bitSet=new BitSet();

            bitSet.clear();
            int cntEdges=0;
            while(scanner.hasNext()){
                String dat=scanner.next();

                if(dat.charAt(0)=='*'){
                    break;
                }
                cntEdges++;
                int x=dat.charAt(1)-'A';
                int y=dat.charAt(3)-'A';
                bitSet.set(x);
                bitSet.set(y);
            }
            String vertices=scanner.next();
            int cantVertices=(vertices.length()+1)/2;

            int countAcorns=cantVertices-bitSet.cardinality();
            //x+y+z+Acorns=cantVertices;
            //(x-1) +( y-1 )+(z-1) ... (p pares, p trees) =edges

            //p =cantVertices-edges-Acorns
            int trees=cantVertices-cntEdges-countAcorns;
            PS.println(String.format("There are %d tree(s) and %d acorn(s).", trees,countAcorns));
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
        TheForrestForTheTrees_599 solver=new TheForrestForTheTrees_599();
        solver.solve();       
    }
}
