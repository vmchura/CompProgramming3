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
public class NetworkConnections_793 {

    private Scanner scanner;
    private FileReader fileOfficialInput;
    private FileReader fileOfficialOutput;
    private FileWriter fileMyOutput;
    private FileReader fileMyOutput_reader;
    private File file_3;
    private PrintStream PS;
    private NetworkConnections_793(){
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
        int casos=Integer.parseInt(scanner.nextLine());
        scanner.nextLine();
        while(casos-->0){
            int nC=Integer.parseInt(scanner.nextLine());
            DSU dsu=new DSU(nC);
            int successAnswers=0;
            int unsuccessAnswers=0;
            while(scanner.hasNext()){
                String L=scanner.nextLine();
                if(L==null || L.isEmpty() || L.equals("\n") )
                    break;
                String[] ws=L.split(" ");
                int x=Integer.parseInt(ws[1])-1;
                int y=Integer.parseInt(ws[2])-1;
                if("c".equals(ws[0])){
                   dsu.joint(x, y);
                }else{
                    if(dsu.areConnected(x, y)){
                        successAnswers++;
                    }else{
                        unsuccessAnswers++;
                    }
                }
            }
            PS.println(String.format("%d,%d", successAnswers,unsuccessAnswers));
            if(casos>0)
                PS.println();
            
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
        NetworkConnections_793 solver=new NetworkConnections_793();
        solver.solve();       
    }
}
class DSU{
    final private int N;
    final private int parent[];
    public DSU(int N) {
        this.N=N;
        parent=new int[N];
        for(int i=0;i<N;i++)
            parent[i]=i;
    }
    public boolean areConnected(int x,int y){
        int p=getFather(x);
        int q=getFather(y);
        return p==q;
    }

    private int getFather(int y) {
       while(parent[y]!=y){
           parent[y]=parent[parent[y]];
           y=parent[y];
       } 
       return y;
    }
    public void joint(int x,int y){
        int p=getFather(x);
        int q=getFather(y);
        parent[p]=q;
    }
    
}
