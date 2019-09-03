package proyecto.lista;


import javax.swing.JLabel;
import javax.swing.ImageIcon;

/**
 * Nodo para la clase lista
 * @author Darius
 */
public class Nodo extends JLabel {
    /** Dato guardado en el nodo */
    private int dato;
    /** Siguiente nodo en la lista */
    private Nodo liga;
    

    /**
     * Devuelve el dato que tiene guardado
     * @return El dato almacenado en el nodo
     */
    public int getDato() {
        return dato;
    }
    
    /**
     * Coloca el dato en el nodo
     * @param dato El dato que se va a colocar en el nodo 
     */
    public void setDato(int dato) {
        this.dato = dato;
    }
    
    /**
     * Devuelve el nodo al cual está enlazado
     * @return El nodo al cual enlaza este nodo
     */
    public Nodo getLiga() {
        return liga;
    }
    
    /**
     * Enlaza este nodo con otro
     * @param liga El nodo al cual deberá enlazar este nodo
     */
    public void setLiga(Nodo liga) {
        this.liga = liga;        
    }
    
    public void setImagen(boolean volteada) {
        if (volteada)
            this.setIcon(new ImageIcon(this.getClass().getResource("/img/back.png")));
        else
            this.setIcon(new ImageIcon(this.getClass().getResource("/img/" + dato + ".png")));
    }
}
