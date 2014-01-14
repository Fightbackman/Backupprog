package gui;

import java.util.EnumSet;

import java.io.*;
import java.nio.file.*;
import static java.nio.file.FileVisitResult.*;
import java.nio.file.attribute.*;
import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JDialog;

import java.awt.BorderLayout;

import javax.swing.JButton;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JTextArea;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;

public class BackupMain implements ActionListener {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BackupMain window = new BackupMain();
					window.frmBackup.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	JFrame frmBackup = new JFrame();
	JLabel lblBackup = new JLabel("BACKUP");
	JButton from_button = new JButton("FROM");
	JButton to_button = new JButton("TO");
	JLabel from_label = new JLabel(
			"The path you choosed wil be displayed here.");
	JLabel to_label = new JLabel("The path you choosed wil be displayed here.");
	JTextArea status_text = new JTextArea();
	JLabel status_label = new JLabel("Status:");
	JButton start_button = new JButton("START");

	JDialog from_choose = new JDialog();
	JFileChooser from_chooser = new JFileChooser();

	JFileChooser to_chooser = new JFileChooser();

	File from_file;
	Path from_path;
	File to_file;
	Path to_path;

	/**
	 * Create the application.
	 */
	public BackupMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		// frmBackup = new JFrame();
		frmBackup.setTitle("Backup");
		frmBackup.setBounds(100, 100, 717, 446);
		frmBackup.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBackup.getContentPane().setLayout(null);

		// JLabel lblBackup = new JLabel("BACKUP");
		lblBackup.setBounds(0, 0, 701, 14);
		lblBackup.setHorizontalAlignment(SwingConstants.CENTER);
		frmBackup.getContentPane().add(lblBackup);

		// JButton from_button = new JButton("FROM");
		from_button.setBounds(118, 74, 89, 23);
		frmBackup.getContentPane().add(from_button);

		// JButton to_button = new JButton("TO");
		to_button.setBounds(118, 108, 89, 23);
		frmBackup.getContentPane().add(to_button);

		// JLabel from_label = new
		// JLabel("The path you choosed wil be displayed here.");
		from_label.setBounds(229, 78, 462, 14);
		frmBackup.getContentPane().add(from_label);

		// JLabel to_label = new
		// JLabel("The path you choosed wil be displayed here.");
		to_label.setBounds(229, 112, 472, 14);
		frmBackup.getContentPane().add(to_label);

		// JTextArea status_text = new JTextArea();
		status_text.setBounds(0, 229, 701, 167);
		frmBackup.getContentPane().add(status_text);

		// JLabel status_label = new JLabel("Status:");
		status_label.setFont(new Font("Tahoma", Font.PLAIN, 15));
		status_label.setBounds(0, 211, 63, 14);
		frmBackup.getContentPane().add(status_label);

		// JButton start_button = new JButton("START");
		start_button.setBounds(118, 142, 89, 23);
		frmBackup.getContentPane().add(start_button);

		from_button.addActionListener(this);
		to_button.addActionListener(this);
		start_button.addActionListener(this);

	}

	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == from_button) {

			int mode = 2;
			from_chooser.setFileSelectionMode(mode);
			from_chooser.showOpenDialog(null);
			from_file = from_chooser.getSelectedFile();
			from_path = from_chooser.getSelectedFile().toPath();
			from_label.setText(from_path.toString());
			System.out.println(from_path.toString());

		}
		if (event.getSource() == to_button) {
			to_chooser.setFileSelectionMode(1);
			to_chooser.showOpenDialog(null);
			to_file = to_chooser.getSelectedFile();
			to_path = to_chooser.getSelectedFile().toPath();
			to_label.setText(to_path.toString());

			System.out.println(to_path.toString());
		}
		if (event.getSource() == start_button) {
			try {
				Files.walkFileTree(from_path, new SimpleFileVisitor<Path>() {
					@Override
					public FileVisitResult visitFile(Path from_path2,
							BasicFileAttributes attributes) throws IOException {

						if (timestampdatei.lastModified() < datei2
								.lastModified()) {
							status_text.append("Copying "
									+ from_path2.getFileName() + '\n');

							Files.deleteIfExists(to_path.resolve(from_path2
									.getFileName()));

							Files.copy(from_path2,
									to_path.resolve(from_path2.getFileName()));

							status_text.append("Copying ended of"
									+ from_path2.getFileName() + '\n');
						} else {
							status_text.append("No newer Version of "
									+ from_path2.getFileName()
									+ "exists, it wasn't copyed  at all."
									+ '\n');
						}

						return FileVisitResult.CONTINUE;
					}

					// @Override
					// public FileVisitResult preVisitDirectory(Path directory,
					// BasicFileAttributes attributes) throws IOException {
					// Path targetDirectory =
					// to_path.resolve(from_path.relativize(directory));
					// try {
					// System.out.println("Copying " +
					// from_path.relativize(directory));
					// Files.copy(directory, targetDirectory);
					// } catch (FileAlreadyExistsException e) {
					// if (!Files.isDirectory(targetDirectory)) {
					// throw e;
					// }
					// }
					// return FileVisitResult.CONTINUE;
					// }
				});

			} catch (IOException e) {
				e.printStackTrace();

			}
		}
	}
}