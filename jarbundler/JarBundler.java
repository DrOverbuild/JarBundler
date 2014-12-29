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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author jasper
 */
public class JarBundler {
	
	public static final String SETTINGS_FILE_PATH = System.getProperty("user.home") + System.getProperty("file.separator") + ".jarbundler";

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		JarBundler.findAntAndStart();
	
	}

	public static void findAntAndStart(){
		try{
			Properties p = getSettingsFromDisk();
			
			new Frame(p);
		}catch(FileNotFoundException e){
			new AntFinder();
		}catch(IOException e){
			
		}
	}
	
	public static void saveSettings(Properties properties)throws IOException{
		properties.store(new FileOutputStream(new File(SETTINGS_FILE_PATH)), "JarBundler Properties");
	}
	
	public static Properties getSettingsFromDisk() throws IOException{
		Properties p = new Properties();
		p.load(new FileInputStream(new File(SETTINGS_FILE_PATH)));
		return p;
	}
	
}
