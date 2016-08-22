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
public class Anagram_195 {

   private Scanner scanner;
    private FileReader fileOfficialInput;
    private FileReader fileOfficialOutput;
    private FileWriter fileMyOutput;
    private FileReader fileMyOutput_reader;
    private File file_3;
    private PrintStream PS;
    private Anagram_195(){
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

    private Character[] toCharacter(char[] toCharArray) {
        Character[] ans=new Character[toCharArray.length];
        for(int i=0;i<toCharArray.length;i++){
            ans[i]=new Character(toCharArray[i]);
        }
        return ans;
    }
    private class CharacterComparator implements Comparator<Character>{

        @Override
        public int compare(Character o1, Character o2) {
            int x=o1.charValue()>='a' && o1.charValue()<='z'?o1.charValue()-'a':o1.charValue()-'A';
            int y=o2.charValue()>='a' && o2.charValue()<='z'?o2.charValue()-'a':o2.charValue()-'A';
            if(x!=y)
                return x<y?-1:1;
            return o1.compareTo(o2);
        }
        
    }
    public void solve(){
       int n=scanner.nextInt();
       while(n-->0){
           String s=scanner.next();
           Character[] word=toCharacter(s.toCharArray());
           Arrays.sort(word, new CharacterComparator());
           int length=word.length;
           do{
               StringBuilder sb=new StringBuilder();
               for(int i=0;i<word.length;i++){
                   sb.append(word[i].charValue());
               }
           PS.println(sb.toString());
           }while(nextPermutation(word,0, new CharacterComparator()));
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
    public boolean nextPermutation(Character[] word, int i,Comparator<Character> cmp){//inclusive
        
        final int length=word.length;
        if(i==length-1)
            return false;
        if(nextPermutation(word,i+1,cmp)){
            return true;
        }else{
           //significa que desde i+1 hasta el fin esta completamente desordenado
            //aacd...xyz
            //azyx...dca
            //aacd...xyz
            //aacd...xyz
            
            flip(word,i+1);
            
            int j=i;
            while(j<length && cmp.compare(word[j],word[i])<=0)
                j++;
            if(j>=length){
                
                flip(word,i+1);
                return false;
            }
            //PS.println("affter fipping:"+Arrays.toString(word));
            //System.err.println(i+":"+j);
            Character x=word[i];
            word[i]=word[j]; 
            word[j]=x;
            return true;
            
        }
        /*
        abc
        acb
        bac
        */
    }
    public void flip(Character[] word,int i){
        int j=word.length-1;
        while(i<j){
            Character x=word[i];
            word[i]=word[j];
            word[j]=x;
            i++;
            j--;
        }
    }
    public static void main(String[] args){
        Anagram_195 solver=new Anagram_195();
        solver.solve();
    } 
}
