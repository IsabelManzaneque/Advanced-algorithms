import java.util.ArrayList;

public class Greedy{
    
    private int n;
    private int M;
    private ArrayList<Integer> pesos;   
    private ArrayList<Integer> valores;

    public Greedy(int n,int M, ArrayList<Integer> pesos, ArrayList<Integer> valores){
		this.n = n;
        this.M = M;
        this.pesos = pesos;
        this.valores = valores;
	}    
    
    
    /* Función auxiliar para intercambiar dos elementos de dos vectores */
    public void swap(int i, int j){
    	
        int tempV = valores.get(i);
        int tempP = pesos.get(i);

        valores.set(i,valores.get(j));
        pesos.set(i,pesos.get(j));
        valores.set(j, tempV);
        pesos.set(j, tempP);       	
    }
  
    /* Toma el ultimo elemento como pivote, lo coloca en la posicion correcta en el array y coloca 
     * los elementos menores que el a la izq y los mayores a la derecha*/
    public int partition(int low, int high){
  
        float pivote = (float)valores.get(high)/(float)pesos.get(high);
        int i = (low - 1);
          
        for (int j = low; j <= high - 1; j++) {  
            // Si el elemento actual es mayor que el pivote, incrementa el indice del elemento menor
            if ((float)valores.get(j)/(float)pesos.get(j) > pivote){
                i++;
                swap(i, j);
            }
        }
        swap(i + 1, high);
        return (i + 1);
    }
  
    /* Implementa quickSort, low es el indice de comienzo y high el de finalizacion */
    public void quickSort(int low, int high){
    	
        if (low < high) {              
            // Ordena por separado los elementos antes y despues del indice de particion (pi)
        	int pi = partition(low, high);
            quickSort(low, pi - 1);
            quickSort(pi + 1, high);
        }
    }

    /* Ejecuta el algoritmo voraz Mochila Fraccionable sobre los datos de entrada */
    public String mochilaFraccionable(boolean trazaOn){  

        ArrayList<Double> x = new ArrayList<>();
        ArrayList<Double> ViPi = new ArrayList<>();
        String resultado = "";
        double beneficio = 0;
        int peso = 0;        
        int i = 0;        

        String traza = "\n----------------- PARAMETROS -----------------\n\n";
        traza += "Capacidad de la mochila: " + M + "\nNumero de elementos: " + n; 
        for (int k = 0; k < n; k++) {
            ViPi.add((double)valores.get(k)/(double)pesos.get(k));
        }
        traza += "\nAntes de ordenar:    p = "+ pesos + ", v = " + valores + ", Vi/Pi = " + ViPi;

        quickSort(0, n-1);

        for (int k = 0; k < n; k++) {
            ViPi.set(k,(double)valores.get(k)/(double)pesos.get(k));
        }
        traza += "\nDespues de ordenar:  p = "+ pesos + ", v = " + valores + ", Vi/Pi = " + ViPi;       
        traza += "\n\n----------------- ITERACIONES -----------------\n\n";   

        for(int j = 0; j < n; j++){
            x.add(0.0);
        }
        
        while(peso < M){
            if(peso + pesos.get(i) <= M){
                x.set(i,1.0);
                peso += pesos.get(i);
                beneficio += valores.get(i);
                resultado += pesos.get(i) + " " + x.get(i) + " " + valores.get(i) + "\n";
            }else{
                x.set(i, ((double)(M-peso)/(double)pesos.get(i)));
                peso = M;
                beneficio += valores.get(i)*x.get(i);
                resultado += pesos.get(i) + " " + x.get(i) + " " + valores.get(i)*x.get(i) + "\n";
            }
            traza += "Iteracion numero " + (i+1) +" - Objeto seleccionado:\n";
            traza += "   - Objeto " + (i+1) + ", Fraccion: " + x.get(i) + ", Peso aportado: " + (pesos.get(i)*x.get(i)) + ", Valor aportado: " + (valores.get(i)*x.get(i)) + "\n";
            i++;
        }
        traza += "\n------------------ RESULTADO ------------------\n\n";
        traza += "Beneficio total en mochila: " + beneficio + "\nFraccion de cada objeto: " + x + "\n";       
        
        if(trazaOn){return traza;}
        return resultado + beneficio;        
    }
    
    /* Ordena los objetos en orden no creciente de vi/pi */
//  public void bubbleSort(){
//
//      int tempV;
//      int tempP;
//
//      if (n>1){
//          for (int j=0; j < n; j++){
//              for (int i=0; i < n - j - 1; i++) {
//                  if ( ((float)valores.get(i)/(float)pesos.get(i)) < ((float)valores.get(i+1)/(float)pesos.get(i+1))){
//
//                      tempV = valores.get(i);
//                      tempP = pesos.get(i);
//
//                      valores.set(i,valores.get(i+1));
//                      pesos.set(i,pesos.get(i+1));
//
//                      valores.set(i+1, tempV);
//                      pesos.set(i+1, tempP);
//                  }
//              }
//          }
//      }
//  }
    
   
    // 15 1 24        peso del objeto 15, fraccion 1, beneficio aportado 24
    // 10 0.5 7.5     peso del objeto 10, fraccion 0.5, beneficio aportado 7.5
    // 31.5           beneficio total 31.5
}
