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
public class WakingUpBrain_10507 {

    private Scanner scanner;
    private FileReader fileOfficialInput;
    private FileReader fileOfficialOutput;
    private FileWriter fileMyOutput;
    private FileReader fileMyOutput_reader;
    private File file_3;
    private PrintStream PS;
    private WakingUpBrain_10507(){
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
            DSU_Brain dsub=new DSU_Brain(N+1); //1 based
            int M=scanner.nextInt();
            TreeMap<Character,Integer> mapa=new TreeMap<>();
            String s=scanner.next();
            for(int i=0;i<3;i++){
                dsub.wakeUp(getIndx(s.charAt(i),mapa));
                
            }
            
            boolean[][] matrixAdj=new boolean[N+1][N+1];
            for(int i=0;i<=N;i++)
                Arrays.fill(matrixAdj[i], false);
            for(int i=0;i<M;i++){
                    s=scanner.next();
                int x=getIndx(s.charAt(0),mapa);
                int y=getIndx(s.charAt(1),mapa);
                matrixAdj[x][y]=true;
                matrixAdj[y][x]=true;
            }
            boolean itExistsHope=true;
            BitSet bitSet=new BitSet(28);
            int ans=0;
            while(itExistsHope && !dsub.allAreWokedUp()){
                ans++;
                itExistsHope=false;
                bitSet.clear();
                for(int x=1;x<=N;x++){
                    if(dsub.isWokedUp(x)){
                        continue;
                    }
                    int cont=0;
                    for(int y=0;y<=N;y++){
                        if(matrixAdj[x][y] && dsub.isWokedUp(y)){
                            cont++;
                        }
                    }
                    if(cont>=3){
                        bitSet.set(x);
                    }
                }
                for(int x=1;x<=N;x++){
                    if(bitSet.get(x)){
                        dsub.wakeUp(x);
                        itExistsHope=true;
                    }
                }
                
            }
            if(dsub.allAreWokedUp()){
                PS.println(String.format("WAKE UP IN, %d, YEARS", ans));
            }else{
                PS.println("THIS BRAIN NEVER WAKES UP");
            }
        }
         closeFiles();
        
    }
    private int getIndx(char c,TreeMap<Character,Integer> mapa){
        if(mapa.containsKey(c)){
            return mapa.get(c);
        }
        int x=mapa.size()+1;
        mapa.put(c, x);
        return x;
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
        WakingUpBrain_10507 solver=new WakingUpBrain_10507();
        solver.solve();       
    }
}
class DSU_Brain{
    int N;
    int parent[];
    int cntWokenUp=1;
    public DSU_Brain(int N) {
        this.N=N;
        parent=new int[N];
        for(int i=0;i<N;i++)
            parent[i]=i;
    }
    private int getFather(int x){
        while(x!=parent[x]){
            parent[x]=parent[parent[x]];
            x=parent[x];
        }
        return x;
    }
    public void wakeUp(int x){
        int p=getFather(x);
        int q=getFather(0);
        if(p!=q)
            cntWokenUp++;
        parent[p]=q;
    }
    public boolean isWokedUp(int x){
        return getFather(x)==getFather(0);
    }
    public boolean allAreWokedUp(){
        return cntWokenUp>=N;
    }
    
}