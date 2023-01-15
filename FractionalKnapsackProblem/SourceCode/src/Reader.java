import java.util.*;
import java.io.*; 

/**
 * Clase encargada de analizar los parametros de entrada, separarlos
 * en sus distintas componentes y comprobar que son correctos. Si
 * el input es correcto, se lo pasa a la clase que ejecuta el algoritmo.
 * 
 * @author Isabel Manzaneque, imanzaneq3@alumno.uned.es
 */
public class Reader{
    
    
	/**
     * Dado un archivo de entrada, comprueba si es valido y extrae la informacion
     */
    public Greedy parseFile(String str) {   	
    	
    	File myFile = new File(str);  	    
    	Greedy greedy = null;
    	
        try{
            Scanner scanner = new Scanner(myFile);  
            int numeroObjetos = Integer.parseInt(scanner.nextLine());  
            int capacidad = 0;
            ArrayList<Integer> valores = new ArrayList<>();
            ArrayList<Integer> pesos = new ArrayList<>();

            while (scanner.hasNextLine()) {
                String data[] = scanner.nextLine().split("\\s+");
                if(data.length>1){
                    int peso = Integer.parseInt(data[0]);
                    int valor = Integer.parseInt(data[1]);

                    pesos.add(peso);
                    valores.add(valor);                  
                }else{
                	capacidad = Integer.parseInt(data[0]);
                }                              
            }
           scanner.close();  
           // si input es correcto, se lo pasa a clase Greedy
           if(inputValidator(numeroObjetos,capacidad,pesos,valores)){
        	   greedy = new Greedy(numeroObjetos,capacidad,pesos,valores);
           }
        }catch (Exception e){
            return null;
        }
        return greedy;
    }
    
    /**
     * Dado un String de entrada, comprueba si es valido y extrae la informacion
     */
    public Greedy parseStdin(String str) {
    	
    	String[] splited = str.split("\\s+");
    	Greedy greedy = null;
    	
    	
    	try{
	    	int numeroObjetos = Integer.parseInt(splited[0]);
	    	int capacidad = Integer.parseInt(splited[splited.length -1]);  
	    	ArrayList<Integer> valores = new ArrayList<>();
            ArrayList<Integer> pesos = new ArrayList<>();
        	if(numeroObjetos != (splited.length-2)/2) {return null;}
            
	    	for(int i = 1; i < splited.length-1; i+=2) {
	    		 pesos.add(Integer.parseInt(splited[i]));
	             valores.add(Integer.parseInt(splited[i+1]));    
	    	}
	    	// si input es correcto, se lo pasa a clase Greedy
	    	if(inputValidator(numeroObjetos,capacidad,pesos,valores)) {
	    		greedy = new Greedy(numeroObjetos,capacidad,pesos,valores);
	    	}
	        
    	}catch (Exception e){
	        return null;
	    }      
    	return greedy;    
    }
    
    /**
     * Comprueba que los parametros de la entrada sean consistentes con el enunciado
     */
    public boolean inputValidator(int numeroObjetos, int capacidad, ArrayList<Integer> pesos, ArrayList<Integer> valores) {    	
    	
    	if(numeroObjetos<1 || capacidad < 1 || pesos.size() <1 || valores.size()<1) {return false;}
    	 	    	
    	if(pesos.size() != numeroObjetos || valores.size() != numeroObjetos) {return false;}
       
    	return true;
    }
       
}