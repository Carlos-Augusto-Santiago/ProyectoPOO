/*
  Nombre completo: Santiago Pérez Carlos Augusto

  Proyecto Album de fotos digital

  Fecha de entrega: Viernes 18 de junio del 2021
  
  Grupo: 2CM13

  Materia: Programacion Orientada a Objetos
*/
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        
        Mosaico ventana = new Mosaico();
        ventana.setBounds(0, 0, 915, 600);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
        ventana.setResizable(false);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Establecer Loon & Feel del sistema
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
}
