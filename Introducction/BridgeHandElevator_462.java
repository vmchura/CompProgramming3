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
public class BridgeHandElevator_462 {

    private static final String naturalOrder="SHDC";
    private Scanner scanner;
    private FileReader fileOfficialInput;
    private FileReader fileOfficialOutput;
    private FileWriter fileMyOutput;
    private FileReader fileMyOutput_reader;
    private File file_3;
    private PrintStream PS;
    private BridgeHandElevator_462(){
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
        
        
        while(scanner.hasNext()){
            Suit S=new Suit('S');
            Suit H=new Suit('H');
            Suit D=new Suit('D');
            Suit C=new Suit('C');
            Suit[] deck=new Suit[4];
            deck[0]=S;
            deck[1]=H;
            deck[2]=D;
            deck[3]=C;
            String[] mano=(scanner.nextLine()).split(" ");
            
            if(mano.length==13){
                for(String carta: mano){
                   switch(carta.charAt(1)){
                       case 'S': 
                           S.add(carta.charAt(0)); 
                           break;
                       case 'H': 
                           H.add(carta.charAt(0)); 
                           break;
                       case 'D': 
                           D.add(carta.charAt(0)); 
                           break;
                       case 'C': 
                           C.add(carta.charAt(0)); 
                           break;
                   } 
                }
                
                /*primero es con noTrump*/
                int points14=0;
                boolean todosStopped=true;
                for(int i=0;i<4;i++){
                    points14+=deck[i].getPoints14();
                    todosStopped&=deck[i].isStopped();
                }
                if(points14>=16 && todosStopped){
                    PS.println("BID NO-TRUMP");
                }else{
                    int points17=0;
                    for(int i=0;i<4;i++){
                        points17+=deck[i].getPoints17();
                    }
                    if(points17>=14){
                        Arrays.sort(deck);
                        PS.println("BID "+deck[0].name);
                    }else{
                        PS.println("PASS");
                    }
                }
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
    private class Suit implements Comparable<Suit>{
        public int points14=0;
        
        int countCards=0;
        boolean A=false,K=false,Q=false,J=false;
        char name;
        Suit(char name){
            this.name=name;
        }
        public void add(char v){
            countCards++;
            switch(v){
                case 'A': 
                    A=true; 
                    points14+=4;
                    break;
                case 'K': 
                    K=true; 
                    points14+=3;
                    break;
                case 'Q': 
                    Q=true; 
                    points14+=2;
                    break;
                case 'J': 
                    J=true;
                    points14+=1;
                    break;
                default: break;
            }
        }
        public boolean isStopped(){
            if(A )
                return true;
            if(K && countCards-1>=1)
                return true;
            if(Q && countCards-1>=2)
                return true;
            return false;
        }
        public int getPoints14(){
            int restar=0;
            if(K && countCards-1<=0)
                restar++;
            if(Q && countCards-1<=1)
                restar++;
            if(J && countCards-1<=2)
                restar++;
            return points14-restar;
            
        }
        public int getPoints17(){
            int sumar=0;
            if(countCards==2)
                sumar+=1;
            if(countCards==1)
                sumar+=2;
            if(countCards==0)
                sumar+=2;
            return getPoints14()+sumar;
        }

        @Override
        public int compareTo(Suit o) {
            if(countCards!=o.countCards)
                return countCards<o.countCards?1:-1;
            int id=naturalOrder.indexOf(name);
            int ido=naturalOrder.indexOf(o.name);
            return id<ido?-1:1;
        }
    }
    public static void main(String[] args){
        BridgeHandElevator_462 solver=new BridgeHandElevator_462();
        solver.solve();
    }
}
