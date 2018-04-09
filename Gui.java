package com.assignment;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.awt.event.ActionListener;
import java.util.Scanner;
import java.awt.event.ActionEvent;

public class Gui extends JFrame implements ActionListener
{
	
	/*File Chooser for user to use GUI to choose a file to open*/
	private	JFileChooser Chooser=new JFileChooser();
	//
	private FileNameExtensionFilter filter;
	/*Java buttons and text area for testing and user input*/
	private JButton ChooseFile;
	private JTextArea area1;
	
	/*Scanner to read through the file and input the values*/
	private Scanner input = new Scanner(System.in);
	private String[] words;
	
	Gui(String title)
	{
		/*Edit options of the gui, layout etc*/
		super(title);
		setSize(800,400);
		setLayout(new FlowLayout());
		setLocation(100,100);
		
		/*Edit the size of the FileChooser*/
		Chooser.setPreferredSize(new Dimension(1000,500));

		/*Button to start the FileChooser and choose what file to read through*/
		ChooseFile=new JButton("Click to choose file");
		ChooseFile.addActionListener(this);
		add(ChooseFile);
		
		area1=new JTextArea(25,25);
		add(area1);
		
		setVisible(true);
	}

	/*Actions performed by user are set here*/
	public void actionPerformed(ActionEvent event) 
	{
		//Open only text files and set it to default text files
		filter = new FileNameExtensionFilter("Text Files","txt");
		Chooser.addChoosableFileFilter(filter);
		Chooser.setAcceptAllFileFilterUsed(false);
		//If user clicks the button to choose a file
		if(event.getSource()==ChooseFile)
		{
			
			//Open the text file, put in to file variable
			Chooser.showOpenDialog(null);
			File inputFile = Chooser.getSelectedFile();
			
			//Scan through the file
			try {
				input = new Scanner(inputFile);
			} catch (FileNotFoundException e) 
			{
			}
			
			//Read through it and for now put it inside the text area
			while (input.hasNextLine())
			{
					String line = input.nextLine();		
					area1.append(line+"\n");
					words=line.split(" ");
			}
			
			
			//Close input 
			input.close();
			
			for(int i=0;i<words.length;i++)
			{
				System.out.println(words[i]);
			}
		}
	}
}
