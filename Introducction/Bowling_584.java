/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Solved.Introducction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.*;
/**
 *
 * @author vchura
 */
public class Bowling_584 {
    private BufferedReader  in;
    private int next(int cant, int i, int[][] nocked, FrameState[] frameStates) {
        int ans=nocked[i][0];
        if(cant==2){
            if(frameStates[i]==FrameState.Strike){
                ans+=next(1,i+1,nocked,frameStates);
            }else{
                
                ans+=nocked[i][1];
            }
            
        }
        return ans;
    }
    enum FrameState{
        Normal,Strike,Spare
    }
    private Bowling_584(){
         in=new BufferedReader(new InputStreamReader(System.in));
    }
    public void solve(){
        FrameState[] frameStates=new FrameState[12];
        int[] frameResults=new int[12];
        int[][] nocked=new int[12][2];
        char[] results=new char[12*4*2];
        while(true){
            
            String linea;
                try{
                    String s=in.readLine();
                    if(s==null)
                        break;
                    linea=s;
                }catch(Exception ex){
                    linea="Game Over";
                }
            
            if(linea.equals("Game Over"))
                break;
            int cantResults=linea.length()/2+1;
            for(int i=0; i<cantResults;i++)
                results[i]=linea.charAt(i*2);
            
            
            
            for(int i=0;i<12;i++)
                for(int j=0;j<2;j++)
                    nocked[i][j]=0;
            Arrays.fill(frameStates, FrameState.Normal);
            Arrays.fill(frameResults,0);
            int currentFrame=0;
            boolean firstRoll=true;
            for(int i=0;i<cantResults && currentFrame<12;i++){
                if(results[i]=='X'){
                    frameStates[currentFrame]=FrameState.Strike;
                    nocked[currentFrame][0]=10;
                    firstRoll=true;
                    currentFrame++;
                }else{
                    if(results[i]=='/'){
                        if(firstRoll)
                            throw new AssertionError("en su primer tiro spare?");
                        frameStates[currentFrame]=FrameState.Spare;
                        nocked[currentFrame][1]=10-nocked[currentFrame][0];
                        firstRoll=true;
                        currentFrame++;
                    }else{
                        int nk=results[i]-'0';
                        nocked[currentFrame][firstRoll?0:1]+=nk;
                        if(firstRoll){
                            firstRoll=false;
                            
                        }else{
                            firstRoll=true;
                            currentFrame++;
                        }
                        
                    }
                }
            }
            int ans=0;
            /*
            for(int i=0;i<12;i++){
                
                PS.print(String.format("(%2d,%2d)",nocked[i][0],nocked[i][1]));
            }
                    */
            //PS.println("");
            for(int i=0;i<10;i++){
            
                
                int whatItSums=0;
                if(null!=frameStates[i])switch (frameStates[i]) {
                    case Strike:
                        whatItSums=10+next(2,i+1,nocked,frameStates);
                        break;
                    case Spare:
                        whatItSums=10+next(1,i+1,nocked,frameStates);
                        break;
                    default:
                        whatItSums=nocked[i][0]+nocked[i][1];
                        break;
                }
                    //PS.print(String.format("%3d",whatItSums));
                    ans+=whatItSums;
                            
            }
            //PS.println("");
            System.out.println(ans);
            
        }
       
        
    }
    public static void main(String[] args){
        Bowling_584 solver=new Bowling_584();
        solver.solve();       
    }
}
