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
public class AnagramsByStack_732 {

    private Scanner scanner;
    private FileReader fileOfficialInput;
    private FileReader fileOfficialOutput;
    private FileWriter fileMyOutput;
    private FileReader fileMyOutput_reader;
    private File file_3;
    private PrintStream PS;
    private AnagramsByStack_732(){
            try{
                File file_alwaysExists=new File("src/ParserToSubmit/ParserToMainJava.java");
                if(!file_alwaysExists.exists()){
                    throw new AssertionError();
                }
                
                File file_1=new File("src/Tried/DataStructures/"+this.getClass().getSimpleName()+"_input.txt");
                File file_2=new File("src/Tried/DataStructures/"+this.getClass().getSimpleName()+"_output.txt");
                     file_3=new File("src/Tried/DataStructures/"+this.getClass().getSimpleName()+"_myoutput.txt");
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
         ArrayList<String> ans=new ArrayList<>();
         while(scanner.hasNext()){
             ans.clear();
             
             String str_src=scanner.next();
             String str_target=scanner.next();
             
             char[] src=str_src.toCharArray();
             char[] target=str_target.toCharArray();
             Stack<Character> stack=new Stack<>();
             boolean[] process=new boolean[src.length*2];
             
             go(src,target,0,0,0,process,stack,ans);
             Collections.sort(ans);
             PS.println("[");
             for(String s: ans){
                 PS.println(s);
             }
             PS.println("]");
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
        AnagramsByStack_732 solver=new AnagramsByStack_732();
        solver.solve();       
    }

    private void go(char[] src, char[] target, int indxProcess,int indxSrc, int indxTarget, boolean[] process,Stack<Character> stack, ArrayList<String> ans) {
        if(indxProcess>=process.length && indxSrc>=src.length && indxTarget>=target.length){
            //respuesta apropiada
            
            StringBuilder sb=new StringBuilder();
            for(int i=0;i<process.length;i++){
                if(i>0)
                    sb.append(' ');
                sb.append(process[i]?'i':'o');
                
                        
            }
            ans.add(sb.toString());
        }
        
        if(indxSrc<src.length){        
            stack.add(src[indxSrc]);
            process[indxProcess]=true;
            go(src,target,indxProcess+1, indxSrc+1, indxTarget, process, stack, ans);
            stack.pop();
        }
        if(!stack.empty()){
            if(stack.peek()==target[indxTarget]){
                process[indxProcess]=false;
                stack.pop();
                go(src,target,indxProcess+1, indxSrc, indxTarget+1, process, stack, ans);
                stack.add(target[indxTarget]);
                
            }
        }
        
    }
}
