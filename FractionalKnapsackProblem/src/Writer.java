import java.io.*; 

/**
 * Clase que recibe los datos de salida y el tipo de salida
 * y escribe los datos en la salida correspondiente
 * 
 * @author Isabel Manzaneque, imanzaneq3@alumno.uned.es
 */
public class Writer {

    private String ficheroSalida;
    private String infoSalida;
    
    public Writer(String ficheroSalida, String infoSalida){
        this.ficheroSalida = ficheroSalida;
        this.infoSalida = infoSalida;       
    }
    
    
    /**
     * Dado un String, lo muestra por pantalla o lo guarda en un fichero
     */
    public void printSalida() {        
        
    	// Si se especifica un fichero de salida 
    	// se escriben los datos en fichero
        if (ficheroSalida!="") {
            File file = new File(ficheroSalida);
            BufferedWriter bw = null; 
            
            if(!file.isAbsolute()) {
                String userDir = System.getProperty("user.dir");
                ficheroSalida = userDir + "\\" + ficheroSalida;
            }
    
            try{
                if (file.exists()) { 
                    System.err.println("Fichero con nombre " + ficheroSalida + " existe en el directorio.");
                } else { 
                    bw = new BufferedWriter(new FileWriter(ficheroSalida));
                    bw.write(infoSalida);
                    System.out.println("Fichero guardado en " + ficheroSalida);                
                }
            }catch (IOException e) {
                System.err.println(e);
            }finally{ 
                try {
                    if (bw != null) {bw.close();}
                } catch (IOException e2) { 
                    e2.printStackTrace();
                }
            }
        // Si no se especifica fichero de salida, 
        // escribe el resultado por salida estandar
        }else{
            System.out.println(infoSalida);
        }
    }     
}