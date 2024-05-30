/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package automatosTest;

/**
 *
 * @author Ozeias
 */
public class Transition {
    private int from;
    private String read;
    private int to;
    
    public Transition(int from,String read,int to){
        this.from = from;
        this.read = read == null ? "None" : read.toLowerCase();
        this.to = to;
    }
    
    public int getFrom(){
        return from;
    }
    
    public String getRead(){
        return read;
    }
    
    public int getTo(){
        return to;
    }
    
    @Override
    public String toString(){
        return String.format("from: %d, read: '%s', to: %d ",from, read, to);
    }
}