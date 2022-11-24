import java.util.*;
import java.io.*; 

public class Reader{
    
    
    /* Dado un archivo de entrada. comprueba si es valido y extrae la informacion */
    public Greedy parseFile(String str) {   	
    	
    	File myFile = new File(str);  	    
    	Greedy greedy = null;
    	
        try{
            Scanner scanner = new Scanner(myFile);  
            int n = Integer.parseInt(scanner.nextLine());  
            int M = 0;
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
                    M = Integer.parseInt(data[0]);
                }                              
            }
           scanner.close();   
           if(inputValidator(n,M,pesos,valores)){
        	   greedy = new Greedy(n,M,pesos,valores);
           }
        }catch (Exception e){
            return null;
        }
        return greedy;
    }
    
    /* Dado una String de entrada. comprueba si es valida y extrae la informacion */
    public Greedy parseStdin(String str) {
    	
    	String[] splited = str.split("\\s+");
    	Greedy greedy = null;
    	
    	
    	try{
	    	int n = Integer.parseInt(splited[0]);
	    	int M = Integer.parseInt(splited[splited.length -1]);  
	    	ArrayList<Integer> valores = new ArrayList<>();
            ArrayList<Integer> pesos = new ArrayList<>();
        	if(n != (splited.length-2)/2) {return null;}
            
	    	for(int i = 1; i < splited.length-1; i+=2) {
	    		 pesos.add(Integer.parseInt(splited[i]));
	             valores.add(Integer.parseInt(splited[i+1]));    
	    	}
	    	
	    	if(inputValidator(n,M,pesos,valores)) {
	    		greedy = new Greedy(n,M,pesos,valores);
	    	}
	        
    	}catch (Exception e){
	        return null;
	    }      
    	return greedy;    
    }
    
    public boolean inputValidator(int n, int m, ArrayList<Integer> p, ArrayList<Integer> v) {
    	
    	// Si no hay al menos n, m y un par valor-peso, la entrada no es correcta    
    	if(n<1 || m < 1 || p.size() <1 || v.size()<1) {return false;}
    	
    	// Si el numero de pares no es igual a n, la entrada no es correctay    	    	
    	if(p.size() != n || v.size() != n) {return false;}
       
    	return true;
    }
       
}