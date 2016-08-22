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
public class ChessboardInFEN_10284 {

    private Scanner scanner;
    private FileReader fileOfficialInput;
    private FileReader fileOfficialOutput;
    private FileWriter fileMyOutput;
    private FileReader fileMyOutput_reader;
    private File file_3;
    private PrintStream PS;
    private ChessboardInFEN_10284(){
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
    boolean[][] occupied=new boolean[8][8];
    boolean[][] attacked=new boolean[8][8];
    int[] dx=new int[]{-2,-2,-1,-1,1,1,2,2};
    int[] dy=new int[]{1,-1,-2,2,-2,2,1,-1};
    public void solve(){
        while(scanner.hasNext()){
            String line=scanner.nextLine();
            String[] rows=line.split("/");
            for(int i=0;i<8;i++){
                for(int j=0;j<8;j++){
                    occupied[i][j]=false;
                    attacked[i][j]=false;
                }
            }
            for(int i=0;i<8;i++){
                int j=0;
                for(int k=0;k<rows[i].length();k++){
                        if(rows[i].charAt(k)>='0' && rows[i].charAt(k)<='9'){
                            j+=rows[i].charAt(k)-'0';
                        }else{
                            occupied[i][j]=true;
                            j++;
                        }
                    }
            }
            for(int i=0;i<8;i++){
                int j=0;
                
            
                for(int k=0;k<rows[i].length();k++){
                    
                    if(rows[i].charAt(k)>='0' && rows[i].charAt(k)<='9'){
                        j+=rows[i].charAt(k)-'0';
                    }else{
                        switch(rows[i].charAt(k)){
                            case 'p': 
                                peonNegro(i, j);                                 
                                break;
                            case 'P': 
                                peonBlanco(i,j);                    
                                break;
                            case 'N':
                            case 'n':
                                caballo(i, j);                                 
                                break;
                            case 'B': 
                            case 'b':
                                alfil(i,j);                                 
                                break;
                            case 'R':
                            case 'r':
                                torre(i,j);                                
                                break;
                            case 'Q':
                            case 'q':
                                reina(i,j);
                                break;
                            case 'K':
                            case 'k':
                                rey(i,j);
                                break;
                        }
                        j++;
                    }
                }
            }
            int ans=0;
            for(int i=0;i<8;i++){
                for(int j=0;j<8;j++){
                    if(occupied[i][j]){
                      //  PS.print('o');
                    }else{
                        if(attacked[i][j]){
                        //    PS.print('.');
                            //ans++;
                        }else{
                          //  PS.print('*');
                            ans++;
                        }
                    }
                }
                //PS.println();
            }
            PS.println(ans);
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
    public void rayo(int x,int y,final int dx,final int dy,int L){
        /*
        if(occupied[x][y]){
            PS.println("occupied");
            return;
        }else{
            PS.println("wtf");
        }
        */
        if(L>0 && canAttack(x+dx, y+dy)){
            rayo(x+dx,y+dy,dx,dy,L-1);
        }
        
    }
    public void alfil(int x,int y){
        alfil(x,y,10);
    }
    public void alfil(int x,int y,int L){
        rayo(x,y,1,1,L);
        rayo(x,y,1,-1,L);
        rayo(x,y,-1,1,L);
        rayo(x,y,-1,-1,L);
    }
    public void torre(int x,int y){
        torre(x,y,10);
    }
    public void torre(int x,int y,int L){
        rayo(x,y,1,0,L);
        rayo(x,y,-1,0,L);
        rayo(x,y,0,1,L);
        rayo(x,y,0,-1,L);
    }
    public void reina(int x,int y,int L){
        alfil(x,y,L);
        torre(x,y,L);
    }
    public void reina(int x,int y){
        reina(x,y,10);
    }
    public void rey(int x,int y){
        reina(x,y,1);
    }
    public void peonNegro(int x,int y){
        rayo(x,y,1,-1,1);
        rayo(x,y,1,1,1);
    }
    public void peonBlanco(int x,int y){
        rayo(x,y,-1,-1,1);
        rayo(x,y,-1,1,1);
    }
    public void caballo(int x,int y){
        for(int k=0;k<8;k++){
            canAttack(x+dx[k],y+dy[k]);
        }
    }
    public boolean canAttack(int x,int y){
        
        if(x>=0 && x<8 && y>=0 && y<8){
            if(occupied[x][y])
                return false;
            attacked[x][y]=true;
            return true;
        }
        return false;
    }
    
    public static void main(String[] args){
        ChessboardInFEN_10284 solver=new ChessboardInFEN_10284();
        solver.solve();       
    }
}
