/*
  Nombre completo: Santiago Pérez Carlos Augusto

  Proyecto Album de fotos digital

  Fecha de entrega: Viernes 18 de junio del 2021
  
  Grupo: 2CM13

  Materia: Programacion Orientada a Objetos
*/
import java.awt.Color;
import java.awt.Image;
import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class MarcoDig extends JFrame implements ActionListener, Runnable, ChangeListener, DocumentListener {

	ImageIcon iconos[] = new ImageIcon[10];
	String comentarios[] = new String[10];
	JLabel img, dur;
	JButton prev, next, diap;
	JComboBox<Integer> comboDuracion;
	JSlider slider;
	JTextArea comentario;
	int posicion = 0;
	Thread hilo;
	boolean diapRun = false;

	public MarcoDig(int imagen) {
		super("Marco Digital");
		setLayout(null);
		arregloImagenes();

		// Definir el field de comentario
		comentario = new JTextArea();
		comentario.setBounds(200, 530, 400, 100);
		comentario.setBorder(BorderFactory.createLineBorder(Color.black));
		comentario.setLineWrap(true);
		add(comentario);
		comentario.getDocument().addDocumentListener(this);

		// Definir el button de previous
		prev = new JButton("<");
		prev.setBounds(10, 295, 100, 30);
		add(prev);
		prev.addActionListener(this);

		//agregamos los iconos
		img = new JLabel(iconos[posicion]);
		img.setBounds(200, 80, 400, 300);
		add(img);

		//Definimos el button de next
		next = new JButton(">");
		next.setBounds(680, 295, 100, 30);
		add(next);
		next.addActionListener(this);

		//Definimos el button de diapositiva
		diap = new JButton("Modo diapositiva");
		diap.setBounds(200, 480, 150, 30);
		add(diap);
		diap.addActionListener(this);

		//Duracion en el combo box
		comboDuracion = new JComboBox();
		for (int i = 1; i <= 10; i++) {
			comboDuracion.addItem(i);
		}
		comboDuracion.setBounds(520, 480, 80, 30);
		add(comboDuracion);

		//Label de la duracion en segundos
		dur = new JLabel("Duracion (segundos): ");
		dur.setBounds(360, 480, 150, 30);
		add(dur);

		//Agregar slider
		slider = new JSlider(50, 150, 100);
		slider.setMajorTickSpacing(25);
		slider.setMinorTickSpacing(5);
		slider.setPaintTicks(true);
		slider.setBounds(300, 420, 200, 30);
		slider.addChangeListener(this);
		add(slider);

		// Crear hilo
		hilo = new Thread(this);

		this.posicion = imagen;
		leerArchivo();
		actualizarImagen();
	}

	private void actualizarImagen() {
		//Se actualiza la imagen de acuerdo a su posicion y el tamaño
		int porcentaje = slider.getValue();

		if (posicion < 0) {
			posicion = iconos.length - 1;
		} else if (posicion >= iconos.length) {
			posicion = 0;
		}

		// Cambiar el tamaño de la imagen
		Image imagen = iconos[posicion].getImage().getScaledInstance(400 * porcentaje / 100, 300 * porcentaje / 100, java.awt.Image.SCALE_SMOOTH);

		// Establecer el ícono
		img.setIcon(new ImageIcon(imagen));
		comentario.setText(comentarios[posicion]);
	}

	private void escribirComentario() {
		comentarios[posicion] = comentario.getText();
		escribirArchivo();
	}

	private void leerArchivo() {
		// Leer de archivo
		try {
			ArrayList<String> list = new ArrayList();
			//Se leen los comentarios del archivo
			Scanner scan = new Scanner(new File("comentarios.txt"));
			scan.useDelimiter(Pattern.compile("(\\n)|;"));

			while (scan.hasNext()) {
				list.add(scan.next());
			}

			comentarios = list.toArray(comentarios);
		} catch (FileNotFoundException ex) {
			//Se crea el archivo en dado caso que no exista
			JOptionPane.showMessageDialog(this, "Archivo no encontrado! Creando...");
			comentarios = new String[]{
				" ", " ", " ", " ", " ", " ", " ", " ", " ", " "
			};
			escribirArchivo();
		}
	}

	private void escribirArchivo() {
		// Escribir a archivo
		try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("comentarios.txt")))) {
			for (String com : comentarios) {
				com = com.trim();
				out.println(com);
			}
		} catch (IOException ex) {
			Logger.getLogger(MarcoDig.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void arregloImagenes() {
		try {
			//Se definen los iconos por su posicion
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

	private void setEnabledBotones(boolean estado) {
		//Se crea para deshabilitar los botones y el texto mientras se esta en modo presentacion
		prev.setEnabled(estado);
		next.setEnabled(estado);
		comboDuracion.setEnabled(estado);
		comentario.setEnabled(estado);
	}

	// Overrides
	@Override
	public void run() {
		int i = posicion;
		while (true) {
			//Se coloca un ciclo para el hilo para mostrar todas las imagenes y que sea repetitivo
			posicion = i++ % iconos.length;
			actualizarImagen();
			try {
				Thread.sleep((int) comboDuracion.getSelectedItem() * 1000);
			} catch (InterruptedException ex) {
				System.out.println("Error en el hilo");
				ex.printStackTrace();
			}
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		actualizarImagen();
	}

	@Override
	public void actionPerformed(ActionEvent e) {		
		if (e.getSource() == diap) {
			//si el boton fue modo diapositiva 
			if (!diapRun) {
				//mientras se este ejecuntando la diapositiva
				diap.setText("Detener");
				if (hilo.isAlive()) {
					hilo.resume();
				} else {
					hilo.start();
				}
			} else {
				//Se detiene o no se inicia el modo diapositiva
				diap.setText("Modo Diapositiva");
				hilo.suspend();
			}
			//Deshabilitamos los botones
			setEnabledBotones(diapRun);
			diapRun = !diapRun;
		} else {
			//definimos una posicion para que si se llega al final regrese
			//al principio y viceversa
			if (e.getSource() == next) {				
				posicion++;
			} else if (e.getSource() == prev) {
				posicion--;
			}
			actualizarImagen();
		}
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		escribirComentario();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		escribirComentario();
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		escribirComentario();
	}

}
