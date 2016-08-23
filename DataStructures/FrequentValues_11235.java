/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Tried.DataStructures;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.*;
/**
 *
 * @author vchura
 */
public class FrequentValues_11235 {

    private InputReader in;
    private PrintStream PS;
    final int maxLength=100001;
    private FrequentValues_11235(){
                  in=new FrequentValues_11235.InputReader(System.in);
       /*
            try{
            in=new InputReader(new FileInputStream("D:\\CompetitiveProgramming3\\CompetitiveProgramming3\\src\\Tried\\DataStructures\\FrequentValues_11235_input.txt"));
            }catch(Exception ex){
                System.out.println("NULL");
                in=new InputReader(System.in);
            }
*/
            PS=System.out;
    }
    
    public void solve(){
        int A[]=new int[maxLength];
        int B[]=new int[maxLength];
        int mapLeft[]=new int[maxLength];
        int mapRight[]=new int[maxLength];
        int mapB[]=new int[maxLength];
        ST_MaxValue st_maxValue=new ST_MaxValue(B);
        
        while(true){
            try{
             int N=in.nextInt();
             if(N==0)
                 break;
             int Q=in.nextInt();
             int prev=Integer.MIN_VALUE;
             
             int currentIndxB=-1;
             Arrays.fill(mapRight,-1);
             Arrays.fill(mapLeft,-1);
             for(int i=0;i<N;i++){
                 A[i]=in.nextInt();
                 if(A[i]!=prev){
                     
                     currentIndxB++;
                     B[currentIndxB]=1;
                     
                     mapLeft[i]=i-1;
                     if(i>0){
                        mapRight[i-1]=i;
                     }
                 }else{
                     B[currentIndxB]++;
                     mapLeft[i]=mapLeft[i-1];
                 }
                 mapB[i]=currentIndxB;
                 prev=A[i];
             }
             st_maxValue.reset(currentIndxB+1);
             mapRight[N-1]=N;
             for(int i=N-1;i>=0;i--){
                 if(mapRight[i]==-1)
                     mapRight[i]=mapRight[i+1];
             }
             while(Q-->0){
                 int x=in.nextInt()-1;
                 int y=in.nextInt()-1;
                 int left_ToRight=0;
                 int right_ToLeft=0;
                 
                 int middle=0;
                 if(mapB[x]==mapB[y]){
                     middle=y-x+1;
                 }else{
                     left_ToRight=mapRight[x]-x;
                     right_ToLeft=y-mapLeft[y];
                     middle=st_maxValue.query(mapB[x]+1, mapB[y]);
                 }
                 
                 PS.println(Math.max(Math.max(left_ToRight,right_ToLeft),middle));
             }
            }catch(Exception ex){
                break;
            }
         }
    
        
    }
    
    public static void main(String[] args){
        FrequentValues_11235 solver=new FrequentValues_11235();
        solver.solve();       
    }
          static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }
        public int nextChar(){
            try{
                int ch=reader.read();
               
                return ch;
                
            }catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        public int nextInt(){
            return Integer.parseInt(next());
        }

}

class ST_MaxValue{
    int L;
    final int A[];
    final int ST[];
    public ST_MaxValue(int[] A) {

        
        this.A = A;
        this.ST =new int[100001*4];
        
    }

    private void init(int nod,int left,int right) {
        //included excluded
        //System.out.println("init "+nod+" L "+left+" R "+right);
        if(left+1>=right){
            ST[nod]=A[left];
            return;
        }
        int m=(left+right)>>1;
        init(nod*2+1,left,m);
        init(nod*2+2,m,right);
        ST[nod]=Math.max(ST[nod*2+1], ST[nod*2+2]);
    }
    public int query(int left,int right){
        if(left>=right)
            return 0;
        return query(0,0,L,left,right);
    }
    public void reset(int L){
        this.L=L;
        init(0,0,L);
    }

    private int query(int nod, int leftmostNode, int rightMostNode, int left, int right) {
        if(left>right){
            return Integer.MIN_VALUE;
        }
        if(leftmostNode==left && rightMostNode==right){
            //todo un rango
            return ST[nod];
        }
        int m=(leftmostNode+rightMostNode)>>1;
        if(right <= m){
            return query(nod*2+1,leftmostNode,m,left,right);
        }else{
            if(left>=m){
                return query(nod*2+2,m,rightMostNode,left,right);
            }else{
                int ansLeft=query(nod*2+1,leftmostNode,m,left,m);
                int ansRight=query(nod*2+2,m,rightMostNode,m,right);
                return Math.max(ansLeft,ansRight);
            }
        }
        
    }
    
    }
}