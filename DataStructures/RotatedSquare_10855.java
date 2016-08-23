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
public class RotatedSquare_10855 {

    private Scanner scanner;
    private FileReader fileOfficialInput;
    private FileReader fileOfficialOutput;
    private FileWriter fileMyOutput;
    private FileReader fileMyOutput_reader;
    private File file_3;
    private PrintStream PS;
    private RotatedSquare_10855(){
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
            int N=scanner.nextInt();
            int n=scanner.nextInt();
            if(N==0)
                break;
            char[][] R=new char[N][N];
            char[][] r=new char[n][n];
            for(int i=0;i<N;i++){
                R[i]=scanner.next().toCharArray();
            }
            for(int j=0;j<n;j++){
                r[j]=scanner.next().toCharArray();
            }
            /*
            for(int i=0;i<N;i++)
                System.out.println(Arrays.toString(R[i]));
            for(int i=0;i<n;i++)
                System.out.println(Arrays.toString(r[i]));
            */
            int x=countRepetitions(R,r);
            PS.print(x);
            for(int i=0;i<3;i++){
                R=rotate(R);
                /*
                for(int asd=0;asd<N;asd++){
                     System.out.println(Arrays.toString(R[asd]));
                }
                System.out.println("---");
                */            
                x=countRepetitions(R,r);
                PS.print(" "+x);
            }
            PS.println("");
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
        RotatedSquare_10855 solver=new RotatedSquare_10855();
        solver.solve();       
    }

    private int countRepetitions(char[][] R, char[][] r) {
        int ans=0;
        int N=R.length;
        int n=r.length;
        
        for(int i=0;i<=N-n;i++){
            for(int j=0;j<=N-n;j++){
                boolean  ok=true;
                for(int ii=0;ii<n && ok;ii++){
                    for(int jj=0;jj<n && ok;jj++){
                        ok=R[i+ii][j+jj]==r[ii][jj];
                    }
                }
              
                ans+=ok?1:0;
            }
        }
        return ans;
    }

    private char[][] rotate(char[][] R) {
       int N=R.length;
       char[][] ans=new char[N][N];
       for(int i=0;i<N;i++){
           for(int j=0;j<N;j++){
               ans[i][j]=R[j][N-1-i];
           }
       }
       return ans;
    }
}
