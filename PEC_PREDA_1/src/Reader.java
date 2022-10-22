import java.util.*;
import java.io.*; 

public class Reader{
    
    private int n;
    private int M;
    private ArrayList<Integer> valores;
    private ArrayList<Integer> pesos;

    public Reader(){	    	
    	n = 0;
    	M = 0;
    	valores = new ArrayList<>();
        pesos = new ArrayList<>();    
                      
    }
    
    /* Dado un archivo valido, extrae la informacion*/
    public void parseFile(String str) {   	
    	
    	File myFile = new File(str);  	    
        
        try{
            Scanner scanner = new Scanner(myFile);  
            this.n = Integer.parseInt(scanner.nextLine());    

            while (scanner.hasNextLine()) {
                String data[] = scanner.nextLine().split("\\s+");
                if(data.length>1){
                    int peso = Integer.parseInt(data[0]);
                    int valor = Integer.parseInt(data[1]);

                    this.pesos.add(peso);
                    this.valores.add(valor);                  
                }else{
                    this.M = Integer.parseInt(data[0]);
                }                              
            }
           scanner.close();            
        }
        catch (Exception e){
            System.out.println("Error en el fichero de entrada");
        }
    }
    
    /* Dada un string de datos, comprueba que sean validos */
    public boolean parseStdin(String str) {
    	
    	String[] splited = str.split("\\s+");
    	
    	// Si no hay al menos n, m y un par valor-peso, la entrada no es correcta
    	if(splited.length < 4) {return false;}
    	
    	try{
	    	this.n = Integer.parseInt(splited[0]);
	    	this.M = Integer.parseInt(splited[splited.length -1]);    
    	}catch (NumberFormatException ff){
	        return false;
	    }
    	
    	// Si el numero de pares no es igual a n, la entrada no es correcta
    	if(n != (splited.length-2)/2) {return false;}
    	    	
    	for(int i = 1; i < splited.length-1; i+=2) {
    		 this.pesos.add(Integer.parseInt(splited[i]));
             this.valores.add(Integer.parseInt(splited[i+1]));    
    	}
    	return true;    
    }

    // Getters    
    public int getN(){
        return this.n;
    }
    public int getM(){
        return this.M;
    }
    public ArrayList<Integer> getValores(){
        return this.valores;
    }
    public ArrayList<Integer> getPesos(){
        return this.pesos;
    }	
       
}