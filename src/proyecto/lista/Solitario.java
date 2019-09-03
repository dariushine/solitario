package proyecto.lista;

/**
 * Juego del solitario
 * @author Darius
 */
public class Solitario {
    /** Listas que conforman las 10 barajas/manos/eso */
    public Lista[] barajas = new Lista[10];
    /** Vector que indica cuantas cartas estan ocultas en cada baraja*/
    public int[] invisible;
    /** Lista que contiene todas las cartas que no estan en el tablero */
    public Lista cartas = new Lista();
    
    /**
     * Prepara el juego para poder empezar a jugarlo (obligatorio)
     */
    public void preparar() {
        // Agregar todas las cartas (de momento solo de picas)
        for (int j=0;j<8;j++) {
            for (int i=1;i<=13;i++) {
                cartas.agregarFin(i);
            }
        }    
        
        // Desordenar esas cartas (esto simula barajearlas)
        cartas.desordenar();
        
        // Aquí indicamos cuantas de esas cartas estarán ocultas por columna
        // No preguntar por el nombre
        int[] amigosInvisibles = {5,5,5,5,4,4,4,4,4,4};
        invisible = amigosInvisibles;
        
        // Inicializar listas
        for (int i = 0; i<barajas.length;i++)
            barajas[i] = new Lista();
        
        // Finalmente pasar las cartas a las columnas
        int col = 0;
        for (int i = 0; i < 54; i++) {
            barajas[col].agregarFin(cartas.getPrimero());
            cartas.eliminarPrimero();
            col++;
            if (col == 10)
                    col = 0;
        }
        
    }
    
    /**
     * Realiza el movimiento de las cartas y aplica la lógica del juego
     * @param posFuente La posición de donde se estan moviendo las cartas
     * @param posObj La posición adonde se moverán las cartas
     * @param num El número de cartas a mover
     */
    public void mover(int posFuente, int posObj, int num) {
        
        /////////////////////////////////////////////
        //   Comprobaciones básicas (salir...)  //
        /////////////////////////////////////////////
        
        // ...si la fuente es un número incorrecto
        if (posFuente < 0 || posFuente > 9) {
            System.out.println("No existe esa fuente");
            return;
        }
        
        // ...si el objetivo es un número incorrecto
        if (posObj < 0 || posObj > 9) {
            System.out.println("No existe ese objetivo");
            return;
        }
        
        // ...si la fuente es igual al objetivo
        if (posObj == posFuente) {
            System.out.println("Eso no haria nada");
            return;
        }
        
        // ...si trata de mover cartas a la inversa (?)
        if (num <= 0) {
            System.out.println("¿Qué tratas de hacer?");
            return;
        }
        
        // A partir de aqui el usuario no está tan mal
        // así que podemos progresar
        Lista fuente = barajas[posFuente];
        Lista obj = barajas[posObj];
        
        // ...si el usuario intenta poner mas cartas de las que hay
        if (num > fuente.count()) {
            System.out.println("No hay tantas cartas ahi.");
            return;
        }
        
        // Revisar si las cartas estan reveladas
        if (fuente.count() - num < invisible[posFuente]) {
            System.out.println("¡No puedes mover cartas ocultas!");
            return;
        }
        
        // Revisar si las cartas estan ordenadas
        int ult = fuente.count() - 1;
        for (int i = 0; i < num - 1; i++) {
            if ((fuente.getEn(ult - i - 1)) != (fuente.getEn(ult - i) + 1)) {
                System.out.println("No estan ordenados");
                return;
            }            
        }
        
        // Revisar si el objetivo es un espacio en blanco o...
        if (!obj.vacia()) {
            /// Revisar si el movimiento es válido
            if (fuente.getEn(fuente.count() - num) != (obj.getUltimo() - 1)) {
                System.out.println("La fuente no coincide con el objetivo");
                return;
            }
        }
        
        // Realizar el movimiento
        for (int i = fuente.count() - num; i < fuente.count(); i++)
            obj.agregarFin(fuente.getEn(i));
        fuente.eliminarDesde(fuente.count() - num);
            
        // Revelar las cartas ocultas en la fuente si se puede
        if (invisible[posFuente] > 0) {
            if (fuente.count() == invisible[posFuente]) {
                invisible[posFuente]--;
            }
        }
        
        // Verificar si estan todos los numeros del 1 al 13
        int lenObj = obj.count();
        if (lenObj - invisible[posObj] >= 13){
            for (int i = 1; i < 13; i++) {
                int actual = obj.getEn(lenObj - i);
                int anterior = obj.getEn(lenObj - i - 1);
                if (actual != anterior - 1) {
                    return;
                }
            }
            
            // Verificar si ganó
            boolean gana = true;
            for (int i = 0; i < barajas.length; i++)
                if (!barajas[i].vacia())
                    gana = false;
            
            // Cuando gana hace esto
            if (gana) {
                System.out.println("Felicidades, ha ganado");
                System.exit(0);
            }
            
            // Si no gana...
            // Quitarlos de la lista y ver si se puede revelar algo
            obj.eliminarDesde(lenObj - 13);
            if (invisible[posObj] > 0) {
                if (obj.count() == invisible[posObj]) {
                    invisible[posObj]--;
                }
            }
        }
    }
    
    /**
     * Imprime el tablero en la consola
     */
    public void imprimir() {
        System.out.println(" 0   1   2   3   4   5   6   7   8   9");
        System.out.println("--------------------------------------");
        int mayor = 0;
        for (int i = 0; i < barajas.length; i++)
            if (mayor < barajas[i].count()) 
                    mayor = barajas[i].count();
        for (int i = 0; i < mayor; i++) {
            String texto = "";
            for (int j = 0; j < barajas.length; j++) {
                if (barajas[j].count() > i) {
                    if(i+1 <= invisible[j]) {
                        texto = texto + "**";
                    }
                    else {
                        int num = barajas[j].getEn(i);
                        if (num < 10)
                            texto = texto + " " + num ;
                        else
                            texto = texto + num;
                    }
                }
                else
                    texto = texto + "  ";
                texto = texto + "  ";
            }
            System.out.println(texto);
        }
        System.out.println();
        System.out.println("Manos restantes: " + cartas.count()/10);
        System.out.println();
        
    }
    
    /**
     * Reparte una carta a cada posición en el tablero
     */
    public void repartir() {
        if (cartas.count() > 0) {
            for (int i = 0; i < 10 ; i++) {
                barajas[i].agregarFin(cartas.getPrimero());
                cartas.eliminarPrimero();
            }
            System.out.println("Cartas repartidas maquinola");
        }
        else {
            System.out.println("Ya desperdiciaste todas las cartas");
        }
    }
    
    
}
