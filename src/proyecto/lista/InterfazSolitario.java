/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto.lista;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Darius
 */
public class InterfazSolitario extends JPanel {
    Solitario juego;
    boolean click;
    int x_pos;
    int y_pos;
    int barajaSel;
    int cartaSel;
    
    /**
     * Constructor para esta clase
     * @param juegoSolitario Un objeto de clase Solitario
     */
    public InterfazSolitario(Solitario juegoSolitario) {
        // Guardar la referencia del solitario pasado por parámetros
        juego = juegoSolitario;
        // Quitar el posicionamiento automatico de los objetos 
        // en el JPanel
        this.setLayout(null);
        // Mostrar todas las cartas en pantalla
        crearCartas();
        // Ponerle un fondo verde para que se vea mejor
        this.setBackground(new Color(0,80,0));
    }
    
    /**
     * Esta funcion pone todas las cartas en la pantalla
     */
    private void crearCartas() {
        // Guardamos estos dos arrays para accederlos mas facilmente
        final Lista[] barajas = juego.barajas;
        final int[] invisible = juego.invisible;
        
        // Y procesamos cada una de las cartas en cada una de las barajas
        for (int i = 0; i < barajas.length; i++)
            for (int j = 0; j < barajas[i].count(); j++) {
                // CartaX = posicion X de la carta en el JPanel
                int cartaX = i * 90 + i * 10 + 5;
                // CartaY = posicion Y de la carta en el JPanel
                int cartaY = calcularY(j, invisible[i]);
                
                // Creamos una variable que haga referencia para
                // acceso más fácil
                final JLabel carta = barajas[i].getLabel(j);
                
                // La añadimos al JPanel para que se muestre en pantalla
                this.add(barajas[i].getLabel(j),0);
                
                // La ponemos en su sitio
                barajas[i].setLocation(j, cartaX, cartaY);
                
                // Le ponemos la imagen que le corresponda
                barajas[i].setImagen(j, j < invisible[i]);
                
                // Y le añadimos las funciones del mouse
                ponerListener(carta, barajas, invisible);
        }
    }
    
    /**
     * Añade las funciones del mouse a una carta en pantalla
     * @param carta La carta a la que se le va a añadir las funciones
     * @param barajas Las barajas de cartas que hay actualmente en el juego
     * @param invisible La cantidad de cartas invisibles que hay en cada una
     */
    private void ponerListener(final JLabel carta, final Lista[] barajas, final int[] invisible) {
        
        // Añadimos los listeners para el click y el soltar click
        carta.addMouseListener(new MouseAdapter() {
            
                    /**
                     * Esta es la funcion que responde al click en la carta
                     * @param e El evento del click
                     */
                    @Override
                    public void mousePressed(MouseEvent e) {
                        
                        // Aquí buscamos y guardamos en dos variables
                        // el numero de la baraja donde se encuentra la carta,
                        // y la posición dentro de esa baraja que tiene
                        for(int i = 0; i < barajas.length; i++) {
                            for(int j = 0; j < barajas[i].count(); j++) {
                                if(carta.getX()== barajas[i].getLabel(j).getX() && carta.getY() == barajas[i].getLabel(j).getY()) {
                                    barajaSel = i;
                                    cartaSel = j;
                                }
                            }
                        }
                        
                        // Si es una carta visible...
                        if(cartaSel >= invisible[barajaSel]) {
                            for(int i = cartaSel; i < barajas[barajaSel].count() - 1; i++)
                                if(barajas[barajaSel].getEn(i) != barajas[barajaSel].getEn(i+1) + 1)
                                    return;
                            // Entonces permitir que se mueva
                            click = true;
                            
                            // Y guardamos el punto donde se tocó la carta,
                            // para que se vea bien cuando se mueva
                            x_pos = e.getX();
                            y_pos = e.getY();
                        }
                        
                        // Si es una carta boca abajo, voltearla
                        else {
                            if(cartaSel == barajas[barajaSel].count() - 1 && invisible[barajaSel] == barajas[barajaSel].count()) {
                                invisible[barajaSel]--;
                                barajas[barajaSel].setImagen(cartaSel, false);
                            }
                        }
                    }
                    
                    /**
                     * Esta es la funcion que responde cuando el usuario
                     * suelta el click
                     * @param e Evento de soltar el click
                     */
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        // Primero revisamos que el usuario este arrastrando
                        // una carta
                        if(click) {
                            
                            // De ser así, ya no la está arrastrando porque
                            // soltó el botón del mouse
                            click = false;
                            
                            // Vemos en cual baraja soltó la carta y la
                            // guardamos en una variable
                            int numBar = (carta.getX() + 10) / 95;
                            
                            // Si la soltó muy lejos a la derecha, solo
                            // seguiremos como si la soltó en la última
                            // baraja
                            if (numBar > 9) 
                                numBar = 9;
                               
                            // En esta variable guardamos la ultima carta
                            // de la baraja destino
                            int valAnt = barajas[numBar].getUltimo();
                            
                            // Y en esta variable guardamos la primera carta
                            // de las que deseamos mover
                            int valSig = barajas[barajaSel].getEn(cartaSel);
                            
                            /* Se hacen varias comprobaciones:
                                -Que no se esté moviendo hacia la misma baraja
                                -Que no se esté moviendo hacia donde haya una
                            invi*ible al frente
                                -Que las cartas coincidan o al menos que
                            se esté moviendo adonde no haya ninguna carta
                            */
                            if(barajaSel != numBar && ((barajas[numBar].count() > invisible[numBar] && valAnt == valSig + 1) || (barajas[numBar].vacia()))) {
                                // Si se cumplen estas condiciones, procedemos
                                // a guardar la baraja fuente en una variable
                                // para mejor accesibilidad
                                Lista barajaFuente = barajas[barajaSel];
                                
                                // Y empezamos a mover todas las cartas
                                for(int i = cartaSel; i < barajaFuente.count(); i++) {
                                    // Guardar la carta actual para
                                    // accesibilidad
                                    JLabel carta = barajaFuente.getLabel(i);
                                    
                                    // Lo mismo que en la funcion crearcarta
                                    int cartaX = numBar * 90 + numBar * 10 + 5;
                                    int cartaY = calcularY(barajas[numBar].count(), invisible[numBar]) + ((i-cartaSel) * 25);
                                    carta.setLocation(cartaX, cartaY);
                                }
                                
                                // Movemos el nodo de la carta de una lista a
                                // otra
                                Nodo nodo = barajaFuente.sacarNodo(cartaSel);
                                barajas[numBar].agregarNodo(nodo);
                                
                                // Revisar si contiene las cartas del 1 al 13
                                int lenObj = barajas[numBar].count();
                                if (lenObj - invisible[numBar] >= 13) {
                                    for (int i = 1; i < 13; i++) {
                                        int actual = barajas[numBar].getEn(lenObj - i);
                                        int anterior = barajas[numBar].getEn(lenObj - i - 1);
                                        if (actual != anterior - 1) {
                                            return;
                                        }
                                    }
                                    // De ser así, eliminarlas
                                    treceCartas(barajas[numBar]);
                                }
                            }
                            
                            // Regresar las cartas a su sitio si es imposible
                            // realizar ese movimiento
                            else {
                                Lista barajaFuente = barajas[barajaSel];
                                int num = barajas[barajaSel].count() - cartaSel;
                                
                                // Regresa todas las cartas a su localización
                                for(int i = cartaSel; i < barajaFuente.count(); i++) {
                                    JLabel carta = barajaFuente.getLabel(i);

                                    int cartaX = barajaSel * 90 + barajaSel * 10 + 5;
                                    int cartaY = calcularY(barajas[barajaSel].count() - num, invisible[barajaSel]) + ((i-cartaSel) * 25);
                                    carta.setLocation(cartaX, cartaY);
                                }
                            }
                        }
                    }
                });
        
                // Agregamos el listener para el movimiento del mouse
                carta.addMouseMotionListener(new MouseMotionAdapter(){
                    /**
                     * Funcion para el movimiento del mouse
                     * @param e Evento de movimiento del mouse
                     */
                    @Override
                    public void mouseDragged(MouseEvent e){
                        // Si se están arrastrando cartas, ir moviéndolas
                        if(click) {
                            Lista baraja = barajas[barajaSel];
                            for(int i = cartaSel; i < baraja.count(); i++) {
                                JLabel carta = baraja.getLabel(i);
                                // Primero las ponemos al frente de todas las
                                // otras cartas
                                setComponentZOrder(carta,0);
                                
                                // Guardamos la posicion en pantalla del panel
                                Point panelPoint = getLocationOnScreen();
                                
                                // Calculamos donde la carta debe estar segun
                                // la posición del mouse
                                int cartaX = e.getXOnScreen() - x_pos;
                                int cartaY = e.getYOnScreen() - y_pos + ((i - cartaSel) * 25);
                                
                                // Creamos un punto de plano cartesiano para
                                // pasarselo como posición a la carta en la
                                // pantalla
                                Point cartaPoint = new Point(cartaX, cartaY);
                                
                                // Y corregimos el punto para que en vez de
                                // posición en la pantalla, sea posición en
                                // el panel que la contiene
                                cartaPoint.x -= panelPoint.x;
                                cartaPoint.y -= panelPoint.y;
                                
                                // Finalmente actualizamos la posición de la
                                // carta
                                carta.setLocation(cartaPoint);
                            }
                        }
                    }
                });
    }
    
    /**
     * Calcula la posición Y en el panel para una carta
     * @param pos Posición de la carta en la baraja
     * @param invisible Numero de cartas invisibles en esa baraja
     * @return La posición Y donde debe ir la carta
     */
    private int calcularY(int pos, int invisible) {
        // Se calcula todo de la siguiente manera:
        // La distancia desde las cartas invisibles es de 15px
        // La distancia desde las cartas reveladas es de 25px
        if (pos <= invisible)
            return (pos * 15);
        else
            return (invisible * 15) + ((pos - invisible) * 25);
    }
    
    /**
     * Se reinicia todo el juego
     */
    public void reiniciar() {
        // Quitamos todas las cartas de la pantalla
        removeAll();
        
        // Creamos un nuevo solitario y lo preparamos
        juego = new Solitario();
        juego.preparar();
        
        // Ponemos las cartas en la pantalla
        crearCartas();
        
        // Redibujamos todo para que se vea
        this.repaint();
    }
    
    /** 
     * Pasa una mano de cartas
     */
    public void repartirMano() {
        // Variables para accesibilidad
        Lista cartas = juego.cartas;
        Lista[] barajas = juego.barajas;
        int[] invisible = juego.invisible;
        
        // Comprobamos que no hayamos gastado todas las manos de cartas
        if (cartas.count() > 0) {
            // Si todavía hay, agregamos las diez cartas
            for (int i = 0; i < 10 ; i++) {
                // Agregamos una carta al final de la baraja [i]
                barajas[i].agregarFin(cartas.getPrimero());
                
                // Y removemos la carta de la lista de cartas para repartir
                cartas.eliminarPrimero();
                
                // Y todo lo demas es lo que se hace cuando se añade una carta
                // a la pantalla (ver crearcartas)
                int pos = barajas[i].count() - 1;
                
                int cartaX = i * 90 + i * 10 + 5;
                int cartaY = calcularY(pos, invisible[i]);
                
                final JLabel carta = barajas[i].getLabel(pos);
                
                this.add(barajas[i].getLabel(pos),0);
                
                barajas[i].setLocation(pos, cartaX, cartaY);
                barajas[i].setImagen(pos, false);
                ponerListener(carta, barajas, invisible);
            }
        }
    }
    
    /**
     * Elimina trece cartas en una baraja
     * @param baraja La baraja donde se van a eliminar
     */
    private void treceCartas(Lista baraja) {
        for(int i = baraja.count() - 13; i < baraja.count(); i++) {
            // Simplemente las quitamos del panel
            remove(baraja.getLabel(i));
        }
        // Y las quitamos de la lista de la baraja
        baraja.eliminarDesde(baraja.count() - 13);
        // Y redibujamos
        repaint();
    }
}

