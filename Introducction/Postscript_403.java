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
public class Postscript_403 {
private static final String[] C5=
{
".***.."+
"*...*."+
"*****."+
"*...*."+
"*...*.",
    
"****.."+
"*...*."+
"****.."+
"*...*."+
"****..",
".****."+
"*...*."+
"*....."+
"*....."+
".****.",
"****.."+
"*...*."+
"*...*."+
"*...*."+
"****..",
"*****."+
"*....."+
"***..."+
"*....."+
"*****.",
"*****."+
"*....."+
"***..."+
"*....."+
"*.....",
".****."+
"*....."+
"*..**."+
"*...*."+
".***..",
"*...*."+
"*...*."+
"*****."+
"*...*."+
"*...*.",
"*****."+
"..*..."+
"..*..."+
"..*..."+
"*****.",
"..***."+
"...*.."+
"...*.."+
"*..*.."+
".**...",
"*...*."+
"*..*.."+
"***..."+
"*..*.."+
"*...*.",
"*....."+
"*....."+
"*....."+
"*....."+
"*****.",
"*...*."+
"**.**."+
"*.*.*."+
"*...*."+
"*...*.",
"*...*."+
"**..*."+
"*.*.*."+
"*..**."+
"*...*.",
".***.."+
"*...*."+
"*...*."+
"*...*."+
".***..",
"****.."+
"*...*."+
"****.."+
"*....."+
"*.....",
".***.."+
"*...*."+
"*...*."+
"*..**."+
".****.",
"****.."+
"*...*."+
"****.."+
"*..*.."+
"*...*.",
".****."+
"*....."+
".***.."+
"....*."+
"****..",
"*****."+
"*.*.*."+
"..*..."+
"..*..."+
".***..",
"*...*."+
"*...*."+
"*...*."+
"*...*."+
".***..",
"*...*."+
"*...*."+
".*.*.."+
".*.*.."+
"..*...",
"*...*."+
"*...*."+
"*.*.*."+
"**.**."+
"*...*.",
"*...*."+
".*.*.."+
"..*..."+
".*.*.."+
"*...*.",
"*...*."+
".*.*.."+
"..*..."+
"..*..."+
"..*...",
"*****."+
"...*.."+
"..*..."+
".*...."+
"*****.",
"......"+
"......"+
"......"+
"......"+
"......"};
    private final char[][] paper=new char[60][60];
    private final int L=60;
    private Scanner scanner;
    private FileReader fileOfficialInput;
    private FileReader fileOfficialOutput;
    private FileWriter fileMyOutput;
    private FileReader fileMyOutput_reader;
    private File file_3;
    private PrintStream PS;
    private Postscript_403(){
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
        cleanPaper();
        while(scanner.hasNext()){
           String cmd=scanner.next();
           
           if(cmd.equals(".EOP")){
               for(int i=0;i<60;i++){
                   for(int j=0;j<60;j++)
                       PS.print(paper[i][j]);
                   PS.println();
               }
               PS.println();
               for(int i=0;i<60;i++)
                   PS.print("-");
               PS.println();
               PS.println();
               cleanPaper();
           }else{
 
           
               String font=scanner.next();
               int sz=font.charAt(1)-'0';
               int x=scanner.nextInt()-1;
               int y=0;
               String s;
               int length;
               if(cmd.equals(".P")){
         
                  y=scanner.nextInt()-1;
                  
               
               
                }
               s=scanner.nextLine().trim();
               length=s.length()-2;
               
               if(cmd.equals(".C")){
                    if(sz==1){
                        y=30-length/2;
                    }else{
                        y=30-length*3;
                    }
                }else if(cmd.equals(".L")){
                      y=0;
                }else if(cmd.equals(".R")){
                      y=60-length*(sz==5?6:sz);  
                }
               
               if(sz==1)
                  printC1(s.substring(1,length+1),x,y);
               else
                   printC5(s.substring(1,length+1),x,y);
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
    public void cleanPaper(){
        for(int i=0;i<60;i++){
            for(int j=0;j<60;j++){
                paper[i][j]='.';
            }
        }
    }
    public void printC5(char c,int x,int y){
        
        if((c>='A' && c<='Z') || c==' '){
            int index;
            if(c==' '){
                index=(int)('Z'-'A')+1;
            }else{
                index=(int)(c-'A');
            }
            String toPrint=C5[index];
            int indexToPrint=0;
            for(int i=0;i<5;i++){
                for(int j=0;j<6;j++){
                    if(toPrint.charAt(indexToPrint)!='.'){
                        if(i+x>=0 && i+x < L && j+y < L && j+y>=0)
                            paper[i+x][j+y]=toPrint.charAt(indexToPrint);
                    }
                    indexToPrint++;
                }
            }
        }else{
            throw new AssertionError("print c is nos acceptable");
        }
    }
    public void printC5(String s,int x,int y){
        
        for(int i=0;i<s.length();i++){
            printC5(s.charAt(i),x,y+i*6);
        }
    }
    public void printC1(String s,int x,int y){
        for(int i=0;i<s.length();i++){
            printC1(s.charAt(i),x,y+i);
        }
    }
    public void printC1(char c,int x,int y){
        if(c==' ')
            return;
        if(x<L && y<L)
            paper[x][y]=c;
    }
    public static void main(String[] args){
        Postscript_403 solver=new Postscript_403();
        solver.solve();
    }
}
