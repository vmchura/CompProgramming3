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
public class Minesweeper_10189 {

    private Scanner scanner;
    private FileReader fileOfficialInput;
    private FileReader fileOfficialOutput;
    private FileWriter fileMyOutput;
    private FileReader fileMyOutput_reader;
    private File file_3;
    private PrintStream PS;
    private Minesweeper_10189(){
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
        int[] dx=new int[]{-1,-1,-1,1,1,1,0,0};
        int[] dy=new int[]{1,-1,0,1,-1,0,-1,1};
        int field=0;
        while(scanner.hasNext()){
            int n=scanner.nextInt();
            int m=scanner.nextInt();
            if(n+m==0)
                break;
            int[][] countBomb=new int[n][m];
            
            for(int i=0;i<n;i++)
                for(int j=0;j<m;j++)
                    countBomb[i][j]=0;
            for(int i=0;i<n;i++){
                String str=scanner.next();
                for(int j=0;j<m;j++){
                    boolean bomba=str.charAt(j)=='*';
                    if(bomba){
                        countBomb[i][j]=-100;
                        for(int k=0;k<8;k++){
                            int nx=i+dx[k];
                            int ny=j+dy[k];
                            if(nx>=0 && nx<n && ny>=0 && ny<m){
                                countBomb[nx][ny]++;
                            }
                        }
                    }
                }
            }
            if(field>0)
                PS.println();
            PS.println(String.format("Field #%d:", ++field));
            for(int i=0;i<n;i++){
              
                for(int j=0;j<m;j++){
                    if(countBomb[i][j]<0)
                        PS.print('*');
                    else
                        PS.print((char)('0'+countBomb[i][j]));
                }
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
        Minesweeper_10189 solver=new Minesweeper_10189();
        solver.solve();       
    }
}
