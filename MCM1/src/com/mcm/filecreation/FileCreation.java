package com.mcm.filecreation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.content.Context;

public class FileCreation {
	Context context;
	public FileCreation(Context context)
	{
		this.context = context;
	}
	
	private void createSubDirectoryInInternalDtorage(String fileName)
	{
		File mydir = context.getDir("MCM", Context.MODE_PRIVATE); //Creating an internal dir;
		File fileWithinMyDir = new File(mydir, "myfile"); //Getting a file within the dir.
		try {
			FileOutputStream out = new FileOutputStream(fileWithinMyDir);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
