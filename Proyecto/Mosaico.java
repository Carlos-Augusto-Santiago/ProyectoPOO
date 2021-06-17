/*
  Nombre completo: Santiago Pérez Carlos Augusto

  Proyecto Album de fotos digital

  Fecha de entrega: Viernes 18 de junio del 2021
  
  Grupo: 2CM13

  Materia: Programacion Orientada a Objetos
*/
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;

public class Mosaico extends JFrame implements ActionListener {

	JButton[][] botones = new JButton[3][4];
	int[][] indexImagenes = new int[][]{
		{
			0, 1, 2, 3
		}, {
			4, 5, 6, 7
		}, {
			-1, 8, 9, -1
		}};

	ImageIcon iconos[] = new ImageIcon[10];
	JLabel img, dur;
	JButton prev, next, diap;
	JComboBox<Integer> comboDuracion;
	JSlider slider;
	int posicion = 0;
	Thread hilo;
	boolean diapRun = false;

	int bWidth = 200;
	int bHeight = 150;
	int bSeparacion = 20;

	public Mosaico() {
		super("Mosaico");
		setLayout(null);
		arregloImagenes();

		// Creando botones del mosaico
		for (int y = 0; y < 3; ++y) {
			for (int x = 0; x < 4; ++x) {
				if (indexImagenes[y][x] != -1) {
					botones[y][x] = new JButton();

					// Cambiar el tamaño de la imagen
					Image img = iconos[indexImagenes[y][x]].getImage().getScaledInstance(bWidth, bHeight, java.awt.Image.SCALE_SMOOTH);

					// Establecer el ícono
					botones[y][x].setIcon(new ImageIcon(img));

					// Establecer comando
					botones[y][x].setActionCommand("" + indexImagenes[y][x]);

					// Establecer accion
					botones[y][x].addActionListener(this);

					// Establecer dimensiones
					botones[y][x].setBounds(
							bSeparacion + x * (bWidth + bSeparacion),
							bSeparacion + y * (bHeight + bSeparacion),
							bWidth, bHeight);

					// Agregarlo
					this.add(botones[y][x]);
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int index = Integer.parseInt(e.getActionCommand());

		// Iniciar el album en la foto seleccionada
		MarcoDig ventana = new MarcoDig(index);
		ventana.setBounds(0, 0, 800, 700);
		ventana.setLocationRelativeTo(null);
		ventana.setResizable(false);
		ventana.setVisible(true);
		ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	private void arregloImagenes() {
		try {
			iconos[0] = new ImageIcon(ImageIO.read(this.getClass().getResource("conejo1.jpg")));
			iconos[1] = new ImageIcon(ImageIO.read(this.getClass().getResource("conejo2.jpg")));
			iconos[2] = new ImageIcon(ImageIO.read(this.getClass().getResource("conejo3.jpg")));
			iconos[3] = new ImageIcon(ImageIO.read(this.getClass().getResource("conejo4.jpg")));
			iconos[4] = new ImageIcon(ImageIO.read(this.getClass().getResource("conejo5.jpg")));
			iconos[5] = new ImageIcon(ImageIO.read(this.getClass().getResource("conejo6.jpg")));
			iconos[6] = new ImageIcon(ImageIO.read(this.getClass().getResource("conejo7.jpg")));
			iconos[7] = new ImageIcon(ImageIO.read(this.getClass().getResource("conejo8.jpg")));
			iconos[8] = new ImageIcon(ImageIO.read(this.getClass().getResource("conejo9.jpg")));
			iconos[9] = new ImageIcon(ImageIO.read(this.getClass().getResource("conejo10.jpg")));
		} catch (IOException ex) {
			System.out.println("Error al cargar imagenes");
			ex.printStackTrace();
		}
	}
}
