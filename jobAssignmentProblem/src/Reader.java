import java.util.*;
import java.io.*; 

/**
 * Clase encargada de analizar los parametros de entrada, separarlos
 * en sus distintas componentes y comprobar que son correctos. Si
 * el input es correcto, se lo pasa a la clase que ejecuta el algoritmo.
 * 
 * @author Isabel Manzaneque, imanzaneq3@alumno.uned.es
 */
public class Reader {
    
	/**
     * Dado un archivo de entrada, comprueba si es valido y extrae la informacion
     */
    public BranchBound parseFile(String str) {   	
    	
    	File myFile = new File(str);  	
    	BranchBound bb = null;
        
        try{        	
            Scanner scanner = new Scanner(myFile);  
            int numeroPasteleros = Integer.parseInt(scanner.nextLine());    
            int tiposPastel = Integer.parseInt(scanner.nextLine()); 
            int costes[][] = new int[numeroPasteleros][tiposPastel];
            int pedidos[] = Arrays.stream(scanner.nextLine().split("-")).mapToInt(Integer::parseInt).toArray();           
            
            int i = 0;
            while (scanner.hasNextLine()) {
            	            	
                String data[] = scanner.nextLine().split("\\s+");
                costes[i] = Arrays.stream(data).mapToInt(Integer::parseInt).toArray();
                i++;                        
            }
            scanner.close(); 
            
            // si input es correcto, se lo pasa a clase BranchBound
            if(!inputValidator(numeroPasteleros,tiposPastel,pedidos,costes)) {return null;}
            bb = new BranchBound(numeroPasteleros,tiposPastel,pedidos,costes);            
           
        }catch (Exception e){
            return null;
        }            
        return bb;    
    }
    
    
    /**
     * Dado un String de entrada, comprueba si es valido y extrae la informacion
     */
    public BranchBound parseStdin(String str) {
    	
    	BranchBound bb = null;
    	String[] splited = str.split("\\s+");   	
    	    	
    	try{
	    	int numeroPasteleros = Integer.parseInt(splited[0]);
	    	int tiposPastel = Integer.parseInt(splited[1]); 
	        int costes[][] = new int[numeroPasteleros][tiposPastel];
	    	int pedidos[] = Arrays.stream(splited[2].split("-")).mapToInt(Integer::parseInt).toArray();  
	    		
		    int aux = 3;
		    for(int i = 0; i < numeroPasteleros; i++) {
		    	for(int j = 0; j < tiposPastel; j++) {
		    		costes[i][j] = Integer.parseInt(splited[aux]);
		    		aux ++;
		    	}
		    }
	        // si input es correcto, se lo pasa a clase BranchBound
		    if(!inputValidator(numeroPasteleros,tiposPastel,pedidos,costes)) {return null;}
	    	bb = new BranchBound(numeroPasteleros,tiposPastel,pedidos,costes);   
	    	
		}catch (Exception e){
	        return null;
	    }     	  	
    	return bb;    
    }  
    
    /**
     * Comprueba que los parametros de la entrada sean consistentes con el enunciado
     */
    public boolean inputValidator(int numeroPasteleros, int tiposPastel, int pedidos[], int costes[][]) {
    	
    	if( numeroPasteleros != pedidos.length || numeroPasteleros != costes.length) {return false;}
        
    	for(int pedido : pedidos) 
    		if(pedido<1 || pedido>tiposPastel) {return false;}   		
    	   	
    	for(int i = 0; i < costes.length; i++) 
    		if(costes[i].length != tiposPastel) {return false;}    	
    	   	
    	return true;
    }
	
}
