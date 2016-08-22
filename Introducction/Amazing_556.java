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
public class Amazing_556 {

    private Scanner scanner;
    private FileReader fileOfficialInput;
    private FileReader fileOfficialOutput;
    private FileWriter fileMyOutput;
    private FileReader fileMyOutput_reader;
    private File file_3;
    private PrintStream PS;
    private final int[] dx={0,-1,0,1};
    private final int[] dy={1,0,-1,0};
    private Amazing_556(){
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
            while(scanner.hasNext()){
                int R=scanner.nextInt();
                int C=scanner.nextInt();
                if(R==0 || C==0)
                    break;
                boolean[][] maze=new boolean[R][C];
                int[][] out=new int[R][C];
                
                for(int i=0;i<R;i++){
                    String fila=scanner.next();
                    for(int j=0;j<C;j++){
                        maze[i][j]=fila.charAt(j)=='0';
                        out[i][j]=0;
                    }
                }
                int dir=0;
                int x=R-1;
                int y=0;
                int[] ans=new int[5];
                Arrays.fill(ans,0);
                boolean hadMoved=false;
                do{
                    
                    //si tiene derecha
                    int dirDerecha=dir==0?3:dir-1;
                    int x_d=x+dx[dirDerecha];
                    int y_d=y+dy[dirDerecha];
                    if(isFree(maze,x_d,y_d)){
                        out[x][y]++;
                        
                        dir=dirDerecha;                        
                        x=x_d;
                        y=y_d;
                                                  
                        hadMoved=true;
                    }else{
                        //si puede avanzar

                        

                        int nx=x+dx[dir];
                        int ny=y+dy[dir];
                        if(isFree(maze,nx,ny)){
                                out[x][y]++;
                                x=nx;
                                y=ny;                               
                            hadMoved=true;
                        }else{
                            dir++;
                            if(dir>=4)
                                dir=0;
                            //no puede avanzar
                        }
                    }
                }while(!((x==R-1) && (y==0)) || !hadMoved);
                for(int i=0;i<R;i++){
                    for(int j=0;j<C;j++){
                        ans[out[i][j]]+=maze[i][j]?1:0;
                    }
                }
                
                PS.println(String.format("%3d%3d%3d%3d%3d",ans[0],ans[1],ans[2],ans[3],ans[4]));
                
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
    boolean isFree(boolean[][] maze,int x,int y){
        int R=maze.length;
        int C=maze[0].length;
        if(x<0 || x>=R || y<0 || y>=C)
            return false;
        return maze[x][y];
    }
    public static void main(String[] args){
        Amazing_556 solver=new Amazing_556();
        solver.solve();
    } 
}
