/*
 * Copyright (c) 2014, jasper
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package jarbundler;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author jasper
 */
public class Frame extends JFrame{

	public static final String newline = System.getProperty("line.separator");

	File outputDirectoryFile;
	File jarfileFile;
	File iconFile;

	public JTextField outputDirectoryField;
	public JTextField jarFileField;
	public JTextField appNameField;
	public JTextField mainClassNameField;
	public JTextField iconFileFeild;
	public JTextField versionStringField;

	public Frame(){
		super("Jar Bundler");
		setLayout(new GridLayout(6, 1));

		JPanel panel1 = new JPanel(new BorderLayout());
		panel1.setBorder(new EmptyBorder(2, 2, 2, 2));
		outputDirectoryField = new JTextField(15);
		outputDirectoryField.setEditable(false);
		JButton chooseButton = new JButton("Choose...");
		chooseButton.addActionListener((ActionEvent e) -> {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fileChooser.setFileFilter(new FileFilter() {

				@Override
				public boolean accept(File f) {
					return f.getName().endsWith(".app") || f.isDirectory();
				}

				@Override
				public String getDescription() {
					return ".app files";
				}
			});
			fileChooser.showSaveDialog(null);
			outputDirectoryFile = fileChooser.getSelectedFile();
			outputDirectoryField.setText(outputDirectoryFile.getAbsolutePath());
		});
		panel1.add(new JLabel("Save output file as: "), BorderLayout.WEST);
		panel1.add(outputDirectoryField, BorderLayout.CENTER);
		panel1.add(chooseButton, BorderLayout.EAST);
		add(panel1);

		JPanel panel3 = new JPanel(new BorderLayout());
		panel3.setBorder((new EmptyBorder(2, 2, 2, 2)));
		jarFileField = new JTextField(15);
		jarFileField.setEditable(false);
		JButton openButton = new JButton("Open...");
		openButton.addActionListener((ActionEvent e) -> {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileFilter(new FileFilter() {

				@Override
				public boolean accept(File f) {
					return f.getName().endsWith(".jar") || f.isDirectory();
				}

				@Override
				public String getDescription() {
					return ".jar files";
				}
			});
			fileChooser.showOpenDialog(Frame.this);
			jarfileFile = fileChooser.getSelectedFile();
			jarFileField.setText(jarfileFile.getAbsolutePath());
		});
		panel3.add(new JLabel("Jar file: "), BorderLayout.WEST);
		panel3.add(jarFileField, BorderLayout.CENTER);
		panel3.add(openButton, BorderLayout.EAST);
		add(panel3);

		JPanel panel4 = new JPanel(new BorderLayout());
		panel4.setBorder(new EmptyBorder(2, 2, 2, 2));
		mainClassNameField = new JTextField(15);
		panel4.add(new JLabel("Main class: "), BorderLayout.WEST);
		panel4.add(mainClassNameField, BorderLayout.CENTER);
		add(panel4);

		JPanel panel5 = new JPanel(new BorderLayout());
		panel5.setBorder((new EmptyBorder(2, 2, 2, 2)));
		iconFileFeild = new JTextField(15);
		iconFileFeild.setEditable(false);
		JButton openButton2 = new JButton("Open...");
		openButton2.addActionListener((ActionEvent e) -> {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileFilter(new FileFilter() {

				@Override
				public boolean accept(File f) {
					return f.getName().endsWith(".icns") || f.isDirectory();
				}

				@Override
				public String getDescription() {
					return ".icns files";
				}
			});
			fileChooser.showOpenDialog(Frame.this);
			iconFile = fileChooser.getSelectedFile();
			iconFileFeild.setText(iconFile.getAbsolutePath());
		});
		panel5.add(new JLabel("Icon: "), BorderLayout.WEST);
		panel5.add(iconFileFeild, BorderLayout.CENTER);
		panel5.add(openButton2, BorderLayout.EAST);
		add(panel5);

		JPanel panel6 = new JPanel(new BorderLayout());
		panel6.setBorder(new EmptyBorder(2, 2, 2, 2));
		versionStringField = new JTextField(15);
		panel6.add(new JLabel("Version String: "), BorderLayout.WEST);
		panel6.add(versionStringField, BorderLayout.CENTER);
		add(panel6);

		JPanel panel7 = new JPanel(new BorderLayout());
		JButton buildButton = new JButton("Bundle");
		buildButton.addActionListener((ActionEvent e) -> bundle());
		panel7.add(buildButton, BorderLayout.CENTER);
		add(panel7);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		pack();
		setMinimumSize(getSize());
		setLocationRelativeTo(null);
	}

	@Override
	public void dispose(){
		System.exit(0);
	}

	public void close(){
		super.dispose();
	}

	public static String xmlBuildFile(File outputDirectory, File jarFile, String mainClassName, String shortVersionString, File icon){
		String name = "";
		if(outputDirectory.getName().endsWith(".app")){
			name = outputDirectory.getName().substring(0,outputDirectory.getName().length()-4);
		}else{
			name =outputDirectory.getName();
		}

		if(shortVersionString.equals("")){
			shortVersionString = "1.0";
		}

		String returnS = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + newline +
			"<project name=\"tempBuildFile\" default=\"default\" basedir=\"" + outputDirectory.getParent() + "\">" +newline+
			"    <description>Temporary build config for ant script</description>" +newline+
			"        <taskdef name=\"bundleapp\"" +newline+
			"            classname=\"com.oracle.appbundler.AppBundlerTask\""+newline+
			"            classpath=\"/usr/share/java/ant/lib/appbundler-1.0.jar\" />"+newline+
			"        <target name=\"bundle-app\">"+newline+
			"            <bundleapp outputdirectory=\"" + outputDirectory.getParent() + "\"" +newline+
            "                name=\""+name+"\"" +newline+
            "                displayname=\""+name+"\"" +newline+
            "                identifier=\""+mainClassName+"\"" +newline+
			"                shortversion=\""+shortVersionString+"\""+newline+
			"                icon=\""+icon.getAbsolutePath()+"\""+newline+
            "                mainclassname=\""+mainClassName+"\">" +newline+
            "                <classpath file=\"" + jarFile.getAbsolutePath() + "\"/>" +newline+
			"                <option value=\"-Dapple.laf.useScreenMenuBar=true\"/>" +newline+
			"            </bundleapp>" +newline+
			"        </target>" +newline+
			"    </project>";

		return returnS;
	}

	private void bundle(){
		if(outputDirectoryFile == null || jarfileFile == null || iconFile == null){
			JOptionPane.showMessageDialog(null, "Error: Empty text fields.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Create build.xml file
		File parent = outputDirectoryFile.getParentFile();
		File buildDotXML = new File(parent, "build.xml");
		BufferedWriter writer = null;
		try {
			buildDotXML.createNewFile();
			writer = new BufferedWriter(new FileWriter(buildDotXML));
			writer.write(xmlBuildFile(outputDirectoryFile, jarfileFile, mainClassNameField.getText(), versionStringField.getText(), iconFile));
			writer.flush();

		} catch (IOException ex) {
			Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
			return;
		} finally {
			if(writer!=null){
				try {
					writer.close();
				} catch (IOException ex) {
					Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}

		// Bundle the app
		Process bundleApp;
		try {
			bundleApp = Runtime.getRuntime().exec("/usr/share/java/ant/bin/ant bundle-app", new String[]{},parent);
			bundleApp.waitFor();
		} catch (IOException | InterruptedException ex) {
			Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
		}

		// Delete build.xml
		buildDotXML.delete();

		// Notify user process was complete
		JOptionPane.showMessageDialog(null, "Bundle was complete!");

		// Show bundled app in file browser
		try {
			Desktop.getDesktop().open(parent);
		} catch (IOException ex) {
			Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

}
