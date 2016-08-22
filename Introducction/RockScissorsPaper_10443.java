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
public class RockScissorsPaper_10443 {

    private Scanner scanner;
    private FileReader fileOfficialInput;
    private FileReader fileOfficialOutput;
    private FileWriter fileMyOutput;
    private FileReader fileMyOutput_reader;
    private File file_3;
    private PrintStream PS;
    private RockScissorsPaper_10443(){
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
    enum Weapon{
        Piedra,Papel,Tijera
    }
    int[] dx=new int[]{1,-1,0,0};
    int[] dy=new int[]{0,0,1,-1};
    public void solve(){
        int T=scanner.nextInt();
        Weapon[][][] mapa;
        for(int caso=0;caso<T;caso++){
            int R=scanner.nextInt();
            int C=scanner.nextInt();
            int n=scanner.nextInt();
            int current=0;
            mapa=new Weapon[2][R][C];
            for(int i=0;i<R;i++){
                String str=scanner.next();
                for(int j=0;j<C;j++){
                    switch(str.charAt(j)){
                        case 'R':
                            mapa[current][i][j]=Weapon.Piedra; break;
                        case 'S':
                            mapa[current][i][j]=Weapon.Tijera; break;
                        case 'P':
                            mapa[current][i][j]=Weapon.Papel; break;
                    }
                }
            }
            int next=1-current;
            while(n-->0){
                for(int i=0;i<R;i++){
                    
                    for(int j=0;j<C;j++){
                        Weapon pierde=null;
                        switch(mapa[current][i][j]){
                            case Piedra:
                                pierde=Weapon.Papel; break;
                            case Tijera:
                                pierde=Weapon.Piedra; break;
                            case Papel:
                                pierde=Weapon.Tijera; break;
                        }
                        
                        mapa[next][i][j]=mapa[current][i][j];
                        for(int k=0;k<4;k++){
                            int nx=i+dx[k];
                            int ny=j+dy[k];
                            if(nx>=0 && nx<R && ny>=0 && ny<C){
                                if(pierde==mapa[current][nx][ny]){
                                    mapa[next][i][j]=pierde;
                                }
                            }
                        }
                        
                    }
                }
                current=1-current;
                next=1-next;
            }
            if(caso>0)
                PS.println();
            for(int i=0;i<R;i++){
                    
                    for(int j=0;j<C;j++){
                 
                        switch(mapa[current][i][j]){
                            case Piedra:
                                PS.print('R'); break;
                            case Tijera:
                                PS.print('S'); break;
                            case Papel:
                                PS.print('P'); break;
                        }
                        
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
        RockScissorsPaper_10443 solver=new RockScissorsPaper_10443();
        solver.solve();       
    }
}
