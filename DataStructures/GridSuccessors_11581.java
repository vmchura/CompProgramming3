package Tried.DataStructures;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



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
public class GridSuccessors_11581 {

    private Scanner scanner;
    private FileReader fileOfficialInput;
    private FileReader fileOfficialOutput;
    private FileWriter fileMyOutput;
    private FileReader fileMyOutput_reader;
    private File file_3;
    private PrintStream PS;
    private GridSuccessors_11581(){
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
         int n=scanner.nextInt();
         BitSet bitSet=new BitSet(1<<10);
         int[] V=new int[1<<10];
         while(n-->0){
             boolean[][] g=new boolean[3][3];
             for(int i=0;i<3;i++){
                 String s=scanner.next();
                 for(int j=0;j<3;j++){
                     g[i][j]=s.charAt(j)=='1';
                 }
             }
             bitSet.clear();
             
             V[0]=toInt(g);
             bitSet.set(V[0]);
             int ans=0;
             for(int i=1;i<(1<<10);i++){
                 boolean[][] gn=f(g);
                 int val=toInt(gn);
                 if(bitSet.get(val)){
                     if(V[ans]==val){
                         ans--;
                     }
                     break;
                 }
                 V[i]=val;
                 bitSet.set(val);
                 ans++;
                 g=gn;
             }
             PS.println(ans);
         }
         closeFiles();
        
    }
    public int toInt(boolean[][] v){
        int ans=0;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                ans=(ans<<1)+(v[i][j]?1:0);
            }
        }
        return ans;
    }
    int dx[]=new int[]{1,-1,0,0};
    int dy[]=new int[]{0,0,1,-1};
    public boolean[][] f(boolean[][] g){
        boolean[][] ans=new boolean[3][3];
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                ans[i][j]=false;
            }
        }
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                for(int k=0;k<4;k++){
                    int ii=i+dx[k];
                    int jj=j+dy[k];
                    if(ii>=0 && ii<3&& jj>=0 && jj<3){
                        ans[i][j]^=g[ii][jj];
                    }
                }
            }
        }
        return ans;
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
        GridSuccessors_11581 solver=new GridSuccessors_11581();
        solver.solve();       
    }
}
