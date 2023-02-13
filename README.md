# Algorithms

## Table of contents

* [Fractional Knapsack Problem](#fractional-knapsack-problem)
* [Job Assignment Problem](#job-assignment-problem)

## Fractional Knapsack Problem

https://github.com/IsabelManzaneque/Algorithms/tree/main/FractionalKnapsackProblem

Se tiene una mochila con una capacidad máxima M y n objetos con pesos p = (p1, p2, …,pn) y beneficios b = (b1,b2, …,bn). El objetivo de este problema es encontrar
una selección de objetos cuya suma de pesos no supere la capacidad máxima de la mochila, y de forma que la suma de beneficios sea máxima. En esta versión del 
problema los objetos son fraccionables: un objeto se puede almacenar completo en la mochila aportando todo su beneficio o se puede guardar una fracción, aportando 
solamente esa fracción del beneficio.

### Entrada:

El fichero de entrada consta de:

• Una primera línea que indica el número de objetos n.

• Tantas líneas como objetos conteniendo dos datos separados por espacio: el peso de cada uno y su beneficio.

• Una última línea con el valor M que indica la capacidad máxima de la mochila

Por ejemplo, para un conjunto de 3 objetos de pesos 18, 15 y 10, beneficios de 25, 24 y 15, respectivamente, y una mochila de capacidad máxima 20, 
la entrada sería:
<pre>
3
18 25
15 24
10 15
20
</pre>

### Salida:

La salida constará de:

• Tantas líneas como objetos seleccionados, conteniendo cada línea tres datos: peso del objeto, fracción del objeto almacenada en la
  mochila y beneficio aportado por dicho objeto.
  
• Beneficio total de la mochila.

En el ejemplo anterior la salida sería:
<pre>
15 1 24
10 0.5 7.5
31.5
</pre>

## Job Assignment Problem

https://github.com/IsabelManzaneque/Algorithms/tree/main/jobAssignmentProblem


Una pastelería tiene n pasteleros capaces de realizar los m tipos de pasteles diferentes que ofrece la pastelería, aunque cada uno de ellos presenta distinta 
destreza en la realización de los diferentes pasteles. El objetivo es asignar los n próximos pedidos recibidos en la pastelería, uno a cada uno de los n 
pasteleros, de forma que se minimice el tiempo total de preparación de los pedidos. Se conoce el tiempo que cada pastelero emplea en preparar cada tipo de pastel.

### Entrada:

El fichero de entrada consta de:

• Una primera línea que indica el valor de n (número de pasteleros de la pastelería)

• Una segunda línea que indica el valor de m (número de tipos de pasteles distintos que ofrece la pastelería)

• Una tercera línea, con n valores separados por guión (-), donde cada valor indica el tipo de pastel correspondiente a cada pedido.

• n líneas más con la matriz de costes C[1..n,1..m], en la que el valor cij corresponde al coste de que el pastelero i realice el pastel j, 
y cuyos valores se presentan separados por un espacio.

Por ejemplo, para una pastelería con 5 pasteleros y una carta de 3 tipos de pasteles diferentes, que reciben 5 pedidos de tipos [11321], y 
que tiene una cierta tabla de costes, el fichero de entrada tendría el siguiente aspecto:
<pre>
5
3
1-1-3-2-1
2 5 3
5 3 2
6 4 9
6 3 8
7 5 8
</pre>

### Salida:

La salida consta de dos líneas:

• Una línea con la asignación resultante: n valores separados por guión (-),  donde el primer valor representa al pastelero asignado al primer
pedido, el segundo valor representa al pastelero encargado del segundo pedido, y así sucesivamente.

• El coste total de la asignación.

Por ejemplo, la salida para el fichero de entrada descrito anteriormente tendría el siguiente aspecto:
<pre>
1-3-2-4-5
20
</pre>
Expresando que el pastelero 1 elabora el pedido 1, el pastelero 3 elabora el pedido 2, el pastelero 2 elabora el pedido 3, el pastelero 4 elabora 
el pedido 4 y el pastelero 5 elabora el pedido 5. El coste de esta asignación de pasteleros a pedidos es 20.  

## Utilización

Los algoritmos se invocan usando la siguiente sintaxis:
<pre>
java nombreArchivoJar [-t][-h] [fichero entrada] [fichero salida]

java –jar nombreArchivoJar.jar [-t][-h] [fichero entrada] [fichero salida]
</pre>
Los argumentos son los siguientes:

• -t: traza cada paso de manera que se describa la aplicación del algoritmo utilizado.

• -h: muestra una ayuda y la sintaxis del comando.

• fichero_entrada: nombre del fichero del que se leen los datos

• fichero_salida: nombre del fichero que se creará para almacenar la salida. 

En caso de que el fichero de entrada no exista, se leerán los datos por la entrada estándar. En caso de que el fichero de salida no se indique en la 
llamada al programa, se escribirá el resultado por la salida estándar. Si se activa la traza del algoritmo, se podrá observar lo que ocurre en las 
iteraciones del algoritmo.
