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
public class VirtualFriends_11503 {

    private Scanner scanner;
    private FileReader fileOfficialInput;
    private FileReader fileOfficialOutput;
    private FileWriter fileMyOutput;
    private FileReader fileMyOutput_reader;
    private File file_3;
    private PrintStream PS;
    private VirtualFriends_11503(){
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
        int casos=scanner.nextInt();
        DSU_Friends dsuf=new DSU_Friends(200001);
        while(casos-->0){
            int F=scanner.nextInt();
            dsuf.clear();
            TreeMap<String,Integer> mapa=new TreeMap<>();
            while(F-->0){
                int x=getIndx(scanner.next(), mapa);
                int y=getIndx(scanner.next(), mapa);
                int tmp_ans=dsuf.joint(x, y);
                PS.println(tmp_ans);
            }
        }
         closeFiles();
        
    }
    int getIndx(String s,TreeMap<String,Integer> mapa){
        if(mapa.containsKey(s)){
            return mapa.get(s);
        }
        int v=mapa.size();
        mapa.put(s, v);
        return v;
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
        VirtualFriends_11503 solver=new VirtualFriends_11503();
        solver.solve();       
    }
}
class DSU_Friends{
    int N;
    int parent[];
    int sz[];

    public DSU_Friends(int N) {
        this.N=N;
        parent=new int[N];
        sz=new int[N];
        for(int i=0;i<N;i++){
            parent[i]=i;
            sz[i]=1;
        }
    }
    private int getFather(int x){
        while(x!=parent[x]){
            parent[x]=parent[parent[x]];
            x=parent[x];
        }
        return x;
    }
    public int joint(int x,int y){
        int p=getFather(x);
        int q=getFather(y);
        if(q!=p){
            parent[p]=q;
            sz[q]+=sz[p];
            return sz[q];
        }
        return sz[p];
    }

    void clear() {
        for(int i=0;i<N;i++){
            parent[i]=i;
            sz[i]=1;
        }
    }
    
}
