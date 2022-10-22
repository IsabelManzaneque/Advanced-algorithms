import java.util.*;
import java.io.*;  

public class mochila_voraz{
	
	
    public static void helper(){
        System.out.println("SINTAXIS: mochila-voraz [-t][-h] [fichero entrada]\n" +
        "-t                  Traza el algoritmo\n" +
        "-h                  Muestra esta ayuda\n" +
        "[fichero entrada]   Nombre del fichero de entrada\n" +
        "[fichero salida]    Nombre del fichero de salida\n");
    }       

    public static void launcher(boolean showTraza, ArrayList<String> myFiles, String stdin){    			
    	
    	Reader reader = new Reader();    
    	Greedy greedy;
    	String input = "";
    	String output = "";       	
       	
    	// Si stdin no esta vacio y myFiles contiene un fichero, solo puede ser el de salida    	
    	if(stdin != "") {
    		input = stdin;
    		if(!myFiles.isEmpty()) {
    			output = myFiles.get(0);
    		}
    	// Si stdin esta vacio, myFiles puede contener input y output, input o nada  	
    	}else{
    		if(!myFiles.isEmpty()){
    			input = myFiles.get(0);
    			if(myFiles.size() == 2){
    				output = myFiles.get(1);
    			}
    		}
    	}
    	
    	if(input != "") {
    		
    		// el input puede ser un archivo o entrada estandar
    		if (new File(input).exists()) {
    			reader.parseFile(input);    			
    		}else {
    			if(input.endsWith(".txt")) {
    				System.out.println("No existe el fichero de entrada");  
    				return;
    			}else if(!reader.parseStdin(input)) {
    				System.out.println("Entrada estandar incorrecta");    	
    				return;
    			}     			
    		}  
    		
    		greedy = new Greedy(reader.getN(), reader.getM(), reader.getPesos(), reader.getValores());
    		Writer writer = new Writer(output, greedy.mochilaFraccionable(showTraza));
    	    writer.printSalida();    		
    	}
    	
	}

    public static void main(String[] args){
        
    	ArrayList<String> myFiles = new ArrayList<>();
    	String stdin = "";
    	boolean correctInput = false;
    	boolean showTraza = false;

        switch(args.length){
            case 1: 
            	correctInput = (args[0].equals("-h") || !args[0].startsWith("-"));
                break;
            case 2: 
            	correctInput = ((args[0].equals("-t") || args[0].equals("-h")) && !args[1].startsWith("-")) //-t o -h y fichero de entrada            				
            				 || (!args[0].startsWith("-") && !args[1].startsWith("-"));                      //los dos ficheros: entrada y salida
                break;
            case 3: 
            	correctInput = ((args[0].equals("-t") || args[0].equals("-h")) && !args[1].startsWith("-") && !args[2].startsWith("-")) // -t o -h y los ficheros de entrada y salida			                
			                 || (args[0].equals("-t") && args[1].startsWith("-h") && !args[2].startsWith("-"));                         // -t, -h y un fichero
                break;
            case 4: 
            	correctInput = (args[0].equals("-t") && args[1].equals("-h") && !args[2].startsWith("-") && !args[3].startsWith("-")); // todos los parametros en orden
                break;
            default: 
                System.out.println("Introduzca una entrada correcta (-h para ver ayuda)");            
                break;
        }
        
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
}