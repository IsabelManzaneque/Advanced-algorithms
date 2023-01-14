import java.util.*;
import java.io.*;  


/**
 * Clase principal que contiene el main del programa. Recibe el input, lo
 * pre procesa e invoca a las clases encargadas de leerlo, realizar la
 * ejecucion del algoritmo sobre los datos y devolver el output con los 
 * resultados de la ejecucion
 * 
 * @author Isabel Manzaneque, imanzaneq3@alumno.uned.es
 */
public class Pasteleria {	
	    
        
	/**
     * Metodo main que filtra los argumentos de entrada y los pasa a launcher 
     * si son correctos en orden y forma
     */
    public static void main(String[] args){
        
    	ArrayList<String> myFiles = new ArrayList<>();
    	String stdin = "";
    	boolean correctInput = false;
    	boolean showTraza = false;
    	
        switch(args.length){
            case 1: 
            	// input correcto = ( -h ) o ( fichero )
            	correctInput = (args[0].equals("-h") || !args[0].startsWith("-"));
                break;
            case 2: 
            	// input correcto = (-t  fichero) o (-h  fichero) o (fichero  fichero)
            	correctInput = ((args[0].equals("-t") || args[0].equals("-h")) && !args[1].startsWith("-"))          				
            				 || (!args[0].startsWith("-") && !args[1].startsWith("-"));                      
                break;
            case 3: 
            	// input correcto = (-t  fichero  fichero) o (-h  fichero  fichero) o (-t  -h  fichero)
            	correctInput = ((args[0].equals("-t") || args[0].equals("-h")) && !args[1].startsWith("-") && !args[2].startsWith("-")) 			                
			                 || (args[0].equals("-t") && args[1].startsWith("-h") && !args[2].startsWith("-"));                        
                break;
            case 4: 
            	// input correcto = -t  -h  fichero  fichero
            	correctInput = (args[0].equals("-t") && args[1].equals("-h") && !args[2].startsWith("-") && !args[3].startsWith("-")); 
                break;
            default: 
                System.out.println("Introduzca una entrada correcta (-h para ver ayuda)");            
                break;
        }
        
        // Si input es correcto se lo pasa a funcion launcher
        if(!correctInput && args.length!=0) {System.out.println("El input no es correcto");}
        else {
	        for (String a : args) { 
	        	if(a.equals("-t")) {showTraza = true;}
	            if(a.equals("-h")) {helper();}
	            if(a.endsWith(".txt")) {myFiles.add(a);}
	            if(!a.endsWith(".txt") && !a.startsWith("-")) {stdin = a;}	    	           
	        }
        }
        launcher(showTraza, myFiles, stdin);                           
    }
        
    
    /**
     * Muestra la ayuda y la sintaxis del comando
     */
    public static void helper(){
        System.out.println("SINTAXIS: pasteleria [-t][-h] [fichero entrada]\n" +
        "-t                  Traza el algoritmo\n" +
        "-h                  Muestra esta ayuda\n" +
        "[fichero entrada]   Nombre del fichero de entrada\n" +
        "[fichero salida]    Nombre del fichero de salida\n");
    }  
        
    
    /**
     * Arranca el algoritmo con los parametros pre-procesados
     */
    public static void launcher(boolean showTraza, ArrayList<String> myFiles, String stdin){    			
    	
    	Reader reader = new Reader();    
    	BranchBound bb = null;
    	String input = "";
    	String output = "";       	
       	
    	// Si stdin no esta vacio y myFiles contiene un 
    	// fichero, solo puede ser el de salida    	
    	if(stdin != "") {
    		input = stdin;
    		if(!myFiles.isEmpty()) {output = myFiles.get(0);}
    	// Si stdin esta vacio, myFiles puede contener ficheros 
    	// de entrada y salida, solo fichero de entrada o nada  	
    	}else{
    		if(!myFiles.isEmpty()){
    			input = myFiles.get(0);
    			if(myFiles.size() == 2){output = myFiles.get(1);}
    		}
    	}
    	
    	if(input != "") {
    		
    		// el input puede ser un archivo o entrada estandar
    		if (new File(input).exists()) {
    			if(reader.parseFile(input) == null) {
    				System.out.println("Error en el fichero de entrada");
    				return;
    			}else{
    				bb = reader.parseFile(input);  
    			}
    		}else {
    			if(input.endsWith(".txt")) {
    				System.out.println("No existe el fichero de entrada");  
    				return;
    			}else if(reader.parseStdin(input) == null) {
    				System.out.println("Error en la entrada estandar");    	
    				return;
    			}else {
    				bb = reader.parseStdin(input);
    			}
    		}   		
    		Writer writer = new Writer(output, bb.pasteleria(showTraza));  //
    	    writer.printSalida();    		
    	}
    	
	}

}
