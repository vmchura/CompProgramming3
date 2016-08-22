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
public class ConsanguineCalculations_1061 {

    static enum ABOAllei{
            A,B,O
    }
    
    private Scanner scanner;
    private FileReader fileOfficialInput;
    private FileReader fileOfficialOutput;
    private FileWriter fileMyOutput;
    private FileReader fileMyOutput_reader;
    private File file_3;
    private PrintStream PS;
    private ConsanguineCalculations_1061(){
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
        int caso=0;
        while(scanner.hasNext()){
            String bf=scanner.next();
            String bm=scanner.next();
            String bs=scanner.next();
            if(bf.equals("E"))
                break;
            Person father=null,mother=null,son=null;
            if(!bf.equals("?"))
                father=new Person(bf);
            if(!bm.equals("?"))
                mother=new Person(bm);
            if(!bs.equals("?"))
                son=new Person(bs);
            if(bf.equals("?")){
                father=new Person(mother,son,0);
            }
            if(bm.equals("?")){
                mother=new Person(father,son,0);
            }
            if(bs.equals("?")){
                son=new Person(father,mother);
            }
            
            PS.println(String.format("Case %d: %s %s %s",++caso,father.toString(),mother.toString(),son.toString()));
            
            /*
            Person father1=new Person("O+");
        Person mother1=new Person("AB+");
        Person son1=new Person(father1,mother1);
        PS.println(son1);
                    */
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
    private class Person{
        
        LinkedList<BloodType> bloodTypes=new LinkedList<>();
        
        Person(Person father,Person mother){
            if(father.bloodTypes.size()!=1 || mother.bloodTypes.size()!=1){
                   throw new AssertionError("alguno de los padres tiene mas de un tipo de sangre");
            }
            BloodType btFather=father.bloodTypes.getFirst();
            BloodType btMother=mother.bloodTypes.getFirst();
            init(btFather,btMother);
            
        }
        Person(BloodType father,BloodType mother){
            init(father,mother);
        }
        Person(Person father,BloodType mother){
            init(father.bloodTypes.getFirst(),mother);
        }
                
        private void init(BloodType btFather,BloodType btMother){
            for(int i=0;i<2;i++){
                ABOAllei x=btFather.get(i);
                boolean fatherGivesPos=false;
                for(int ip=0;ip<2;ip++){
                    if(btFather.isPositive()){
                        fatherGivesPos=ip>0;
                    }
                    for(int j=0;j<2;j++){
                        ABOAllei y=btMother.get(j);
                        boolean motherGivesPositive=false;
                        for(int jp=0;jp<2;jp++){
                            if(btMother.isPositive()){
                                motherGivesPositive=jp>0;
                            }
                            BloodType bloodType=new BloodType(fatherGivesPos || motherGivesPositive, x, y);
                            
                            if(!bloodTypes.contains(bloodType))
                                bloodTypes.add(bloodType);
                        }
                        
                    }
                }
            }
        }
        Person(Person partner,Person son,int z){
            for(ABOAllei x: ABOAllei.values()){
                for(ABOAllei y: ABOAllei.values()){
                    for(int i=0;i<2;i++){
                        BloodType bloodType=new BloodType(i>0,x,y);
                        Person daughter=new Person(partner, bloodType);
                        for(BloodType bloodTypeDaughter:daughter.bloodTypes){
                            if(son.bloodTypes.getFirst().equals(bloodTypeDaughter)){
                                if(!bloodTypes.contains(bloodType)){
                                    bloodTypes.add(bloodType);
                                }
                            }
                        }
                    }
                }
            }
        }
        Person(String str){
            bloodTypes.add(new BloodType(str));
        }
        @Override public String toString(){
            
            if(bloodTypes.size()==0){
                return "IMPOSSIBLE";
            }
            if(bloodTypes.size()==1)
                return bloodTypes.getFirst().toString();
            StringBuilder stringBuilder=new StringBuilder();
            stringBuilder.append("{");
            boolean wroteSomething=false;
            for(BloodType bloodType:bloodTypes){
                if(wroteSomething)
                    stringBuilder.append(", ");
                wroteSomething=true;
                stringBuilder.append(bloodType.toString());
            }
            stringBuilder.append("}");
            return stringBuilder.toString();
        }
    }
    
    
    private class BloodType {
        
        boolean positive;
        ABOAllei x,y;
        String str;
        BloodType(boolean pos,ABOAllei x,ABOAllei y){
            positive=pos;
            this.x=x;
            this.y=y;
            if(x==ABOAllei.O )
                str=y.toString();
            else{
                if(y==ABOAllei.O){
                    str=x.toString();
                }else{
                    if(x==y){
                        str=x.toString();
                    }else{
                        str =ABOAllei.A.toString()+ABOAllei.B.toString();
                    }
                }
            }
            
            str=str+(pos?"+":"-");
        }
        BloodType(String str){
            this.str=str;
            int length=str.length();
            positive=str.charAt(length-1)=='+';
            if(length==3){
                x=ABOAllei.A;
                y=ABOAllei.B;
            }else{
                if(str.charAt(0)=='O'){
                   x=ABOAllei.O; 
                   y=ABOAllei.O;
                }else{
                    y=ABOAllei.O;
                    if(str.charAt(0)=='A'){
                        x=ABOAllei.A;
                    }else{
                        x=ABOAllei.B;
                    }
                }
            }
        }
        public boolean isPositive(){
            return positive;
        }
        public ABOAllei get(int pos){
            if(pos==0)
                return x;
            return y;
        }
        @Override public String toString(){
            return str;
        }
        @Override public boolean equals(Object b){
            if(b instanceof BloodType){
                BloodType o=(BloodType)b;
                return str.equals(o.str);
            }
            return false;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 97 * hash + Objects.hashCode(this.str);
            return hash;
        }
    }
    public static void main(String[] args){
        ConsanguineCalculations_1061 solver=new ConsanguineCalculations_1061();
        solver.solve();       
        
    }
}
