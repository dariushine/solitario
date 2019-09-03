package proyecto.lista;
import java.util.Random;
import javax.swing.JLabel;

/**
 * Lista simple que contiene nodos enlazados entre si de manera lineal
 * @author Darius
 */
public class Lista {
    private Nodo p;
    
    /**
     * Agrega un elemento al principio de la lista
     * @param d Elemento a agregar
     */
    public void agregarPrincipio (int d) {
        Nodo n = new Nodo();
        n.setDato(d);
        n.setLiga(p);
        p = n;
    }
    
    /**
     * Agrega un elemento al final de la lista
     * @param d El elemento a agregar
     */
    public void agregarFin (int d) {
        if (p==null) {
            agregarPrincipio(d);
        }
        else {
            Nodo n = new Nodo();
            Nodo q = p;
            while(q.getLiga() != null) {
                q = q.getLiga();
            }
            q.setLiga(n);
            n.setDato(d);
        }
    }
    
    /**
     * Devuelve la lista en forma de cadena
     * @return La lista en forma de cadena
     */
    @Override
    public String toString() {
        String texto = "";
        if (p != null) {
            Nodo q = p;
            while (q.getLiga() != null) {
                texto += q.getDato() + ", ";
                q = q.getLiga();
            }
            texto += q.getDato();
        }
        return texto;
    }
    
    /**
     * Cuenta la cantidad de elementos en una lista
     * @return El numero de elementos en la lista
     */
    public int count() {
        if  (p==null) {
            return 0;
        }
        int cont=1;
        Nodo nodo = p;
        while (nodo.getLiga() != null) {
            cont++;
            nodo=nodo.getLiga();
        }
        return cont;
    }
    
    /**
     * Agrega un elemento en la posición indicada, empujando los demás 
     * elementos hacía delante.
     * @param pos La posición donde se agregará el elemento
     * @param d El elemento a agregar
     * @return Verdadero si no hay errores, de lo contrario es falso
     */
    public boolean agregarEn(int pos, int d) {
        if (pos > count() - 1 || pos < 0) {
            return false;
        }
        if (pos == 0) {
            agregarPrincipio(d);
        }
        else {
            Nodo nodo = p;
            for (int i = 0; i < pos - 1; i++) {
                nodo = nodo.getLiga();
            }
            
            Nodo nuevoNodo = new Nodo();
            nuevoNodo.setDato(d);
            nuevoNodo.setLiga(nodo.getLiga());
            
            nodo.setLiga(nuevoNodo);
        }
        return true;
    }
    
    /**
     * Busca y devuelve la primera posicion numerica de un dato en la lista
     * @param dato El dato a buscar
     * @return La primera posicion numerica del dato si existe dentro de 
     * la lista
     */
    public int buscarPrimero(int dato) {
        if (p == null) {
            return -1;
        }
        
        Nodo nodo = p;
        int cont = 0;
        while (nodo != null) {
            if (nodo.getDato() == dato) {
                return cont;
            }
            cont++;
            nodo = nodo.getLiga();
        }
        return -1;
    }
    
    /**
     * Busca y devuelve la última posicion numerica de un dato en la lista
     * @param dato El dato a buscar
     * @return La ultima posicion numerica del dato si existe dentro de 
     * la lista
     */
    public int buscarUltimo(int dato) {
        if (p == null) {
            return -1;
        }        
        Nodo nodo = p;
        int cont = 0;
        int encontrado = -1;
        while (nodo != null) {
            if (nodo.getDato() == dato) {
                encontrado = cont;
            }
            cont++;
            nodo = nodo.getLiga();
        }
        return encontrado;
    }
    
    /**
     * Elimina el primer elemento de la lista.
     * @return Verdadero si fue posible realizarlo, de lo contrario falso
     */
    public boolean eliminarPrimero() {
        if (p != null) {
            p = p.getLiga();
            return true;
        }
        return false;
    }
    
    /**
     * Devuelve el dato del primer elemento en la lista.
     * @return El dato del primer elemento de la lista. 0 si la lista esta vacía
     */
    public int getPrimero() {
        if (p != null) {
            return p.getDato();
        }
        return 0;
    }
    
    /**
     * Devuelve el dato del ultimo elemento en la lista.
     * @return El dato del ultimo elemento de la lista. 0 si la lista esta vacía
     */
    public int getUltimo() {
        if (p!=null){
            Nodo nodo = p;
            while (nodo.getLiga()!=null){
                nodo = nodo.getLiga();
            }
            return nodo.getDato();
        }
        return 0; 
    }
    
    /**
     * Devuelve el dato del elemento en una posición indicada
     * @param pos La posición en la lista del dato que se quiere obtener
     * @return El dato del elemento situado en esa posición
     */
    public int getEn(int pos) {
        Nodo nodo = p;
        for (int i = 0; i < pos; i++) 
            nodo = nodo.getLiga();
        return nodo.getDato();
    }
    
    /**
     * Elimina el ultimo elemento de la lista
     * @return Verdadero si se pudo realizar la operacion, si no, falso
     */
    public boolean eliminarUltimo() {
        if (p!=null) {
            if (p.getLiga() == null){
                p = p.getLiga();
            }
            else {
                Nodo nodo = p;
                // Mientras el nodo siguiente al actual no sea el último...
                while (nodo.getLiga().getLiga() != null)
                    nodo = nodo.getLiga();
                // Cuando el siguiente nodo sea el último, apuntar el nodo
                // actual a null.
                nodo.setLiga(null);
                return true;
            }
        }
        return false;
    }
    
    public boolean eliminarEn(int pos) {
        if (pos > count() - 1 || pos < 0) {
            return false;
        }
        if (pos == 0) {
            eliminarPrimero();
        }
        else{
            Nodo nodo = p;
            // Recorrer la lista desde el principio hasta que el nodo "p"
            // sea el nodo anterior al que buscamos
            for (int i = 0; i < pos - 1; i++) {
                nodo = nodo.getLiga();
            }
            // Remover el nodo que buscamos
            nodo.setLiga(nodo.getLiga().getLiga());
        }
        return true;
    }
    
    /**
     * Elimina todos los nodos empezando desde el nodo señalado
     * @param pos Posición desde la cual se borraran los nodos
     * @return Verdadero si todo salió bien
     */
    public boolean eliminarDesde(int pos) {
        // Tiene que haber al menos un nodo en esa posicion, y pos tiene que
        // ser mayor a cero, sino la re bardeaste
        if (pos > count() - 1 || pos < 0) {
            return false;
        }
        // Si pos es cero solo quieres vaciar la lista (debería añadir ese
        // metodo)
        if (pos == 0) {
            p = null;
        }
        // Sino pues procedemos a eliminar
        else {
            Nodo nodo = p;
            // Recorrer la lista desde el principio hasta que el nodo "p"
            // sea el nodo anterior a la posicion que buscamos
            for (int i = 0; i < pos - 1; i++) 
                nodo = nodo.getLiga();
            // Elimina todos los nodos siguientes a ese
            nodo.setLiga(null);
        }
        return true;
    }
    
    /**
     * Desordena la lista. Simple
     */
    public void desordenar() {
        // Iniciamos el aleatorizador
        Random aleatorio = new Random();
        
        // Se empieza aleatorizando con todos los números y se van sacando de 
        // la "funcion" los que ya fueron movidos
        for (int i = count(); i >= 0; i--) {
            // Tomamos un numero aleatorio desde 0 hasta i
            int pos = (int) Math.floor((aleatorio.nextDouble() * i));
            // Lo ponemos al final
            agregarFin(getEn(pos));
            // Lo quitamos de la posicion en que estaba antes por que si no
            // estamos duplicándolo
            eliminarEn(pos);
        }
    }
    
    /**
     * Comprueba si la lista está vacía o contiene elementos
     * @return Falso si la lista no contiene elementos, sino, lo contrario
     */
    public boolean vacia() {
        return p == null;
    }
    
    public void setLocation(int pos, int x, int y) {
        Nodo nodo = p;
        for (int i = 0; i < pos; i++) 
            nodo = nodo.getLiga();
        nodo.setBounds(x,y,90,125);
    }
    
    public JLabel getLabel(int pos) {
        Nodo nodo = p;
        for (int i = 0; i < pos; i++) 
            nodo = nodo.getLiga();
        return nodo;
    }
    
    public void setImagen(int pos, boolean volteada) {
        Nodo nodo = p;
        for (int i = 0; i < pos; i++) 
            nodo = nodo.getLiga();
        nodo.setImagen(volteada);
    }
    
    public Nodo sacarNodo(int pos) {
        Nodo nodo = p;
        for (int i = 0; i < pos; i++) 
            nodo = nodo.getLiga();
        eliminarDesde(pos);
        return nodo;
    }
    
    public void agregarNodo(Nodo nodo) {
        if (p==null) {
            p = nodo;
        }
        else {
            Nodo q = p;
            while(q.getLiga() != null) {
                q = q.getLiga();
            }
            q.setLiga(nodo);
        }
    }
}
