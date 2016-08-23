/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Tried.DataStructures;

import java.io.BufferedReader;
import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.*;
/**
 *
 * @author vchura
 */
public class EasyProblemFromRujiaLiu_11991 {

    private InputReader in;
    private PrintStream PS;
    private EasyProblemFromRujiaLiu_11991(){
            
            in=new EasyProblemFromRujiaLiu_11991.InputReader(System.in);
/*        
            try{
            in=new InputReader(new FileInputStream("D:\\CompetitiveProgramming3\\CompetitiveProgramming3\\src\\Tried\\DataStructures\\EasyProblemFromRujiaLiu_11991_input.txt"));
            }catch(Exception ex){
                System.out.println("NULL");
                in=new InputReader(System.in);
            }
*/
            PS=System.out;
    }
    public void solve(){
        
        ArrayList<ArrayList<Integer> > AL    =new ArrayList<>();
        for(int i=0;i<100001;i++){
            AL.add(new ArrayList<Integer>());
        }
        
        int[] L=new int[100001];
        int[] D=new int[1000001];
        int DSIZE=0;
        while(true){
            try{
                for(int i=0;i<DSIZE;i++)
                    L[i]=0;
                DSIZE=0;
                Arrays.fill(D, -1);
                int n=in.nextInt();
                int m=in.nextInt();
                for(int i=1;i<=n;i++){
                    int x=in.nextInt();
                    int y;
                    if(D[x]>=0){
                        y=D[x];
                        
                    }else{
                        y=DSIZE;
                        D[x]=y;
                        DSIZE++;
                    }
                    if(AL.get(y).size()>L[y])
                        AL.get(y).set(L[y], i);
                    else{
                        AL.get(y).add(i);
                    }
                    L[y]++;
                }
                while(m-->0){
                    int k=in.nextInt()-1;
                    int x=in.nextInt();
                    if(D[x]>=0 && L[D[x]]>k)
                        PS.println(AL.get(D[x]).get(k));
                    else
                        PS.println(0);
                }
                
            }catch(Exception ex){
                    break;
                    }
        }
    
         
        
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

        public int nextInt() {
            return Integer.parseInt(next());
        }

    }
    public static void main(String[] args){
        EasyProblemFromRujiaLiu_11991 solver=new EasyProblemFromRujiaLiu_11991();
        solver.solve();       
    }
}
