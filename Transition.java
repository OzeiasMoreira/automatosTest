
package automatosTest;

public class Transition {
    private int from;
    private String read; // Símbolo lido durante a transição
    private int to;
    
    public Transition(int from,String read,int to){
        this.from = from;
         // Inicializa o símbolo lido; se for nulo, define como "None"; caso contrário, converte para minúsculas
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
    
    // Sobrescreve o método toString para fornecer uma representação em string da transição
    @Override
    public String toString(){
        return String.format("from: %d, read: '%s', to: %d ",from, read, to);
    }
}
