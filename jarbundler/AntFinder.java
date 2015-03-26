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
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.io.File;
import java.util.Properties;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author jasper
 */
public class AntFinder extends JFrame{

	public AntFinder() throws HeadlessException {
		super("JarBundler");
		setLayout(new BorderLayout());
		JPanel content = new JPanel(new GridLayout(5,1));
		content.setBorder(new EmptyBorder(5, 5, 5, 5));

		JPanel message1Panel = new JPanel(new FlowLayout());
		message1Panel.add(new JLabel("You opened JarBundler for the first time, so we need to know where Ant is."));
		content.add(message1Panel);
		
		JPanel antPathPanel = new JPanel(new FlowLayout());
		antPathPanel.add(new JLabel("Path to Ant: "));
		JTextField pathFieldForAnt = new JTextField(20);
		pathFieldForAnt.setEditable(false);
		antPathPanel.add(pathFieldForAnt);
		JButton openButtonForAntPath = new JButton("Choose...");
		antPathPanel.add(openButtonForAntPath);
		content.add(antPathPanel);
		
		JPanel message2Panel = new JPanel(new FlowLayout());
		message2Panel.add(new JLabel("We also need to know where appbuilder-1.0.jar is."));
		content.add(message2Panel);
		
		JPanel appbuilderPathPanel = new JPanel(new FlowLayout());
		appbuilderPathPanel.add(new JLabel("Path to appbuilder-1.0.jar: "));
		JTextField pathFieldForAppbuilder = new JTextField(20);
		pathFieldForAppbuilder.setEditable(false);
		appbuilderPathPanel.add(pathFieldForAppbuilder);
		JButton openButtonForAppbuilderPath = new JButton("Choose...");
		appbuilderPathPanel.add(openButtonForAppbuilderPath);
		content.add(appbuilderPathPanel);
		
		JButton finishButton = new JButton("Done");
		JPanel finishButtonPanel = new JPanel(new FlowLayout());
		finishButtonPanel.add(finishButton);
		content.add(finishButtonPanel);
		
		
		add(content,BorderLayout.CENTER);
		
		openButtonForAntPath.addActionListener((e) -> {
			JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView());
			chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());
			chooser.addChoosableFileFilter(new FileFilter() {

				@Override
				public boolean accept(File f) {
					return (f.getName().equals("ant") && f.getParentFile().getName().equals("bin")) || f.isDirectory();
				}

				@Override
				public String getDescription() {
					return "Executable bash script called \"ant\"";
				}
			});

			if(chooser.showOpenDialog(null) == 0){
				pathFieldForAnt.setText(chooser.getSelectedFile().getAbsolutePath());
			}
			
		});
		
		openButtonForAppbuilderPath.addActionListener((e) -> {
			JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView());
			chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());
			chooser.addChoosableFileFilter(new FileFilter() {

				@Override
				public boolean accept(File f) {
					return f.getName().equals("appbundler-1.0.jar") || f.isDirectory();
				}

				@Override
				public String getDescription() {
					return "appbundler-1.0.jar";
				}
			});

			if(chooser.showOpenDialog(null) == 0){
				pathFieldForAppbuilder.setText(chooser.getSelectedFile().getAbsolutePath());
			}
		});
		
		finishButton.addActionListener((e) -> {
			if(pathFieldForAnt.getText().equals("")||pathFieldForAppbuilder.getText().equals("")){
				return;
			}
			Properties p = new Properties();
			p.setProperty("path_to_ant", pathFieldForAnt.getText());
			p.setProperty("path_to_appbuilder", pathFieldForAppbuilder.getText());
			p.setProperty("version_string", JarBundler.VERSION_STRING);
			JarBundler.saveSettings(p);
			dispose();
			JarBundler.findAntAndStart();
		});
		
		pack();
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setLocationRelativeTo(null);
		rootPane.setDefaultButton(finishButton);

	}
}
