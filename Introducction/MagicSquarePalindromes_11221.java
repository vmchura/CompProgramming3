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
public class MagicSquarePalindromes_11221 {

    char[][] SquarePalindrome;
    private Scanner scanner;
    private FileReader fileOfficialInput;
    private FileReader fileOfficialOutput;
    private FileWriter fileMyOutput;
    private FileReader fileMyOutput_reader;
    private File file_3;
    private PrintStream PS;
    private MagicSquarePalindromes_11221(){
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
        int T=Integer.parseInt(scanner.nextLine());
        for(int caso=1;caso<=T;caso++){
            
            String L=scanner.nextLine();
            L=L.toLowerCase();
            int K2=0;
            for(int i=0;i<L.length();i++){
                if(L.charAt(i)>='a' && L.charAt(i)<='z'){
                        K2++;
                }
            }
            int K=(int)Math.sqrt(K2+0.01);
            if(K*K==K2){
                SquarePalindrome=new char[K][K];
                int currentRow=0;
                int currentCol=0;
                for(int i=0;i<L.length();i++){
                    if(L.charAt(i)>='a' && L.charAt(i)<='z'){
                            SquarePalindrome[currentRow][currentCol]=L.charAt(i);
                            currentCol++;
                            if(currentCol>=K){
                                currentRow++;
                                currentCol=0;
                            }
                    }
                }
                //(x,y) (u,v)
                //(i,0) (0,i) ,  //x>=u
                //(K-1,i) (i,K-1) ,
                
                //(i,K-1) (0,i)
                //(K-1,i) (i,0)
                boolean isSqPal=true;
                for(int i=0;i<K;i++){
                    isSqPal&=isPal(i,0, 0, i);
                    isSqPal&=isPal(K-1,i, i, K-1);
                    isSqPal&=isPal(i,K-1, 0, K-1-i);
                    isSqPal&=isPal(K-1,i, K-1-i, 0);
                }
                if(isSqPal){
                    PS.println(String.format("Case #%d:\n%d",caso,K));
                }else{
                    PS.println(String.format("Case #%d:\nNo magic :(",caso));
                }
                
            }else{
                PS.println(String.format("Case #%d:\nNo magic :(",caso));
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
    
    public boolean isPal(int x,int y,int u,int v){
        //PS.println(String.format("Compares: (%d,%d)=%c : (%d,%d)=%c",x,y,SquarePalindrome[x][y],
          //      u,v,SquarePalindrome[u][v]));
        if(x<=u)
            return true;
        if(SquarePalindrome[x][y]==SquarePalindrome[u][v]){
            if(v>y)
                return isPal(x-1,y+1,u+1,v-1);
            else
                return isPal(x-1,y-1,u+1,v+1);
        }
        return false;
    }
    public static void main(String[] args){
        MagicSquarePalindromes_11221 solver=new MagicSquarePalindromes_11221();
        solver.solve();       
    }
}
