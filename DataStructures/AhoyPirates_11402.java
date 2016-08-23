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
public class AhoyPirates_11402 {

    private Scanner scanner;
    private FileReader fileOfficialInput;
    private FileReader fileOfficialOutput;
    private FileWriter fileMyOutput;
    private FileReader fileMyOutput_reader;
    private File file_3;
    private PrintStream PS;
    public enum State{
        Trues,Falses,Invertir,Normal
    }
    private AhoyPirates_11402(){
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
        int T_casos=scanner.nextInt();
        final BitSet bitSet=new BitSet(1024002);
        
        ST_LazyPirates stlp=new ST_LazyPirates(bitSet);
        for(int caso=1;caso<=T_casos;caso++){
            PS.println(String.format("Case %d:",caso));
            bitSet.clear();
            int M=scanner.nextInt();
            int current=0;
            for(int pares=0;pares<M;pares++){
                int T_concat=scanner.nextInt();
                String s=scanner.next();
                int L=s.length();
                for(int i=0;i<L;i++){

                    if(s.charAt(i)=='1'){
                        for(int j=0;j<T_concat;j++){
                            bitSet.set(current+j*L+i);
                        }
                    }
                }
                current+=T_concat*L;
            }
            stlp.reset(current);
            int currentQ=0;
            int Q=scanner.nextInt();
            while(Q-->0){
                String s=scanner.next();
                int x=scanner.nextInt();
                int y=scanner.nextInt();
                if(s.equals("F")){//trues
                    stlp.update(x, y+1, State.Trues);
                }else{
                    if(s.equals("E")){//falses{
                        stlp.update(x, y+1, State.Falses);
                    }else
                       if(s.equals("I")){
                         stlp.update(x, y+1, State.Invertir);
                       }else{
                           //S query
          
                           PS.println(String.format("Q%d: %d",++currentQ,stlp.query(x, y+1)));
                       }
                }
                //System.out.println("After "+s);
                //stlp.desc(0, 0, current);
                
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
        AhoyPirates_11402 solver=new AhoyPirates_11402();
        solver.solve();       
    }
}
class ST_LazyPirates{
    int L;
    final BitSet A;
    final int Trues[];
    final int Cant[];
    final AhoyPirates_11402.State State[];
    public ST_LazyPirates(BitSet A) {

        
        this.A = A;
        this.Trues =new int[1024000*4];
        this.Cant=new int[1024000*4];
        this.State=new AhoyPirates_11402.State[1024000*4];
    }

    private void init(int nod,int left,int right) {
        //included excluded
        //System.out.println("init "+nod+" L "+left+" R "+right);
        if(left+1>=right){
            if(A.get(left)){
                Trues[nod]=1;
                Cant[nod]=1;
                
            }else{
                Trues[nod]=0;
                Cant[nod]=1;
                
            }
            State[nod]=AhoyPirates_11402.State.Normal;
            
            return;
        }
        int m=(left+right)>>1;
        init(nod*2+1,left,m);
        init(nod*2+2,m,right);
        Trues[nod]=Trues[nod*2+1]+ Trues[nod*2+2];
        Cant[nod]=Cant[nod*2+1]+ Cant[nod*2+2];
        State[nod]=AhoyPirates_11402.State.Normal;
    }
    public void desc(int nod,int left,int right) {
        //included excluded
        //System.out.println("init "+nod+" L "+left+" R "+right);
        if(left+1>=right){
            System.out.println("nod: "+nod+" S: "+State[nod]+" T: "+countTrues(nod));
            
            return;
        }
        int m=(left+right)>>1;
        desc(nod*2+1,left,m);
        desc(nod*2+2,m,right);
        System.out.println("nod: "+nod+" S: "+State[nod]+" T: "+countTrues(nod));
    }
    public int query(int left,int right){
        if(left>right)
            return 0;
        return query(0,0,L,left,right);
    }
    public void reset(int L){
        this.L=L;
        init(0,0,L);
    }

    private int query(int nod, int leftmostNode, int rightMostNode, int left, int right) {
        if(left>=right){
            return 0;
         }
        if(left<leftmostNode || right>rightMostNode)
             return 0;
        
        if(leftmostNode==left && rightMostNode==right){
            //todo un rango
            /*
            if(left+1<right){
                push(nod);
                pull(nod);
            }
            */
          //  System.out.println("Got whole ans for "+left+" "+right);
            return countTrues(nod);
            
        }
        
        push(nod);
        
        
        int m=(leftmostNode+rightMostNode)>>1;
        int ansLeft=query(nod*2+1,leftmostNode,m,left,Math.min(m,right));        
        int ansRight=query(nod*2+2,m,rightMostNode,Math.max(m,left),right);        
        pull(nod);
        //System.out.println("Got ans for "+left+" "+right);
        return ansLeft+ansRight;
    }
    private void update(int nod,int leftmostNode,int rightmostNode,int left,int right,AhoyPirates_11402.State newState){
         if(left>=right){
            return;
         }
         if(left<leftmostNode || right>rightmostNode)
             return;
        
        
        
        if(leftmostNode==left && rightmostNode==right){
        //    System.out.println("UPGRADING "+left+" <-> "+right+" con "+newState);
            updateNewState(nod,newState);
            return;
        }
        
        push(nod);
        
        
        int m=(leftmostNode+rightmostNode)>>1;
        update(nod*2+1,leftmostNode, m             , left            , Math.min(m,right), newState);
        update(nod*2+2,m           , rightmostNode , Math.max(m,left), right            , newState);
        
        pull(nod);
        
    }
    private void updateNewState(int nod,AhoyPirates_11402.State newState){
        if(newState==AhoyPirates_11402.State.Invertir)
            invert(nod);
        else
            State[nod]=newState;
    }

    private AhoyPirates_11402.State invertState(AhoyPirates_11402.State st){
        switch(st){
            case Falses:
                return AhoyPirates_11402.State.Trues;
            case Trues:
                return AhoyPirates_11402.State.Falses;
            case Invertir:
                return AhoyPirates_11402.State.Normal;
            case Normal:
                return AhoyPirates_11402.State.Invertir;
        }
        return null;
    }
    private void push(int nod) {
        //System.out.println("PUSHING NOD "+nod+" CURRENT STATE "+State[nod]);
        if(State[nod]!=AhoyPirates_11402.State.Normal){
            if(State[nod]==AhoyPirates_11402.State.Invertir){
                State[nod*2+1]=invertState(State[nod*2+1]);
                State[nod*2+2]=invertState(State[nod*2+2]);
            }else{
                State[nod*2+1]=State[nod];
                State[nod*2+2]=State[nod];
                
            }
            State[nod]=AhoyPirates_11402.State.Normal;     
        }
    }

    private void pull(int nod) {
       //System.out.println("PULLING NOD "+nod);                  
        Trues[nod]=countTrues(nod*2+1)+countTrues(nod*2+2);
        
    }
    private int countTrues(int nod){
            if(State[nod]==AhoyPirates_11402.State.Normal){
                return Trues[nod];
            }else{
                if(State[nod]==AhoyPirates_11402.State.Invertir){
                    //invertir
                    return Cant[nod]-Trues[nod];
                }else{
                    if(State[nod]==AhoyPirates_11402.State.Falses){
                        //todo a Falses
                        return 0;
                    }else{
                        return Cant[nod];
                    }
                }
            }
    }

    private void invert(int nod) {
        
        if(State[nod]==AhoyPirates_11402.State.Normal){
            State[nod]=AhoyPirates_11402.State.Invertir;
        }else{
            if(State[nod]==AhoyPirates_11402.State.Invertir){
                State[nod]=AhoyPirates_11402.State.Normal;
            }else{
                if(State[nod]==AhoyPirates_11402.State.Trues){
                    State[nod]=AhoyPirates_11402.State.Falses;
                }else{
                    
                    State[nod]=AhoyPirates_11402.State.Trues;
                }
            }
        }
    }
    public void update(int x,int y,AhoyPirates_11402.State state){
        
        update(0,0,L,x,y,state);
        
    }

    private Exception Exception(String asd) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
