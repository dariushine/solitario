package proyecto.lista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * Clase principal
 * @author Darius
 */
public class ProyectoLista {
    
    /**
     * Metodo principal. Aquí se inicia todo y se leen los datos hasta 
     * el fin del juego
     * @param args Argumentos recicibidos por la línea de comandos
     */
    public static void main(String[] args) {
        Solitario test = new Solitario();
        test.preparar();
        Scanner input = new Scanner(System.in);
        
        
        JFrame frame = new JFrame("Solitario");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        frame.setSize(1024,600);
        
        
        final InterfazSolitario panel = new InterfazSolitario(test);
        frame.add(panel);
        

        
        
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        
        JMenu menuAccion = new JMenu("Acción");
        menuBar.add(menuAccion);
        
        JMenuItem itemNuevo = new JMenuItem("Nuevo juego");
        itemNuevo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.reiniciar();
            }
        });
        menuAccion.add(itemNuevo);
        
        JMenuItem itemRepartir = new JMenuItem("Repartir");
        itemRepartir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.repartirMano();
            }
        });
        menuAccion.add(itemRepartir);
        
        frame.setVisible(true);

        /*
        while (true) {
            test.imprimir();
            System.out.print("Columna fuente: ");
            int col = input.nextInt();
            if (col == 999)
                return;
            else if (col == 111) {
                System.out.println("======================================");
                test.repartir();
                System.out.println("======================================");
            }
            else {
                System.out.print("Columna objetivo: ");
                int obj = input.nextInt();
                System.out.print("Cantidad de cartas: ");
                int num = input.nextInt();
                System.out.println("======================================");
                test.mover(col, obj, num);
                System.out.println("======================================");
            }
        }
        */
    }
}
