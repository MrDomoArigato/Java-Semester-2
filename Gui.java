/* Java Assignment 2018 - Due date 16th April
 * Dominik Dobrowolski C16347703
 * Language analyser checks for slang words and percentage of slang in the file
 * Allows to add new words to slang dictionary 
 */
package com.assignment;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.event.ActionEvent;

public class Gui extends JFrame implements ActionListener
{
	private File directory;													//Variables to store directory as string
	private static ArrayList<String> sentence = new ArrayList<String>();	//Array lists used to store file input
	private static ArrayList<String> dictionary = new ArrayList<String>();
	
	private	JFileChooser Chooser=new JFileChooser();						//File Chooser for user to use GUI to choose a file to open
	private FileNameExtensionFilter filter;
	
	private JButton ChooseFile,ChooseDictionary,Compare,Edit,Exit;				//Java buttons and text area for testing and user input
	private JTextArea area1;
	private int counter;
	
	private Scanner input = new Scanner(System.in);							//Scanner to read through the file and input the values
	
	private double percentage;												//Double to store percentage of file that is slang
	
	NumberFormat format = new DecimalFormat("#0.00");						//Format the output to 2 decimal places, don't want huge floating point values
	
	private String word;													//Store users word input
	
	Gui(String title)
	{
		super(title);														//Edit options of the gui layout etc
		setSize(800,400);
		setLayout(new FlowLayout());
		setLocation(100,100);
		

		Chooser.setPreferredSize(new Dimension(1000,500));					//Edit the size of the FileChooser menu
		getContentPane().setBackground(Color.black);
		ChooseFile=new JButton("Click to choose text file to check");		//Button to start the FileChooser and choose what file to read through
		ChooseFile.addActionListener(this);
		ChooseFile.setBackground(Color.CYAN);
		add(ChooseFile);

		ChooseDictionary = new JButton("Click to choose dictionary"); 		//Choose dictionary button
		ChooseDictionary.addActionListener(this);
		ChooseDictionary.setBackground(Color.CYAN);
		add(ChooseDictionary);

		Compare = new JButton("COMPARE!"); 									//Compare button
		Compare.addActionListener(this);
		Compare.setBackground(Color.CYAN);
		add(Compare);

		Edit = new JButton("Click this to edit dictionary in use");
		Edit.addActionListener(this);
		Edit.setBackground(Color.CYAN);
		add(Edit);
		
		area1=new JTextArea(15,70);											//Area of text field
		add(area1);

		Exit= new JButton("Close");
		Exit.addActionListener(this);
		add(Exit);
		
		setVisible(true);
		Exit.setBackground(Color.RED);
	}
	
	/*--------Actions performed by user are set here--------*/
	public void actionPerformed(ActionEvent event) 
	{
		filter = new FileNameExtensionFilter("Text Files","txt");			//Open only text files and set it to default text files
		Chooser.addChoosableFileFilter(filter);
		Chooser.setAcceptAllFileFilterUsed(false);
		
		//-------------------------BUTTON 1-------------------------
		if(event.getSource()==ChooseFile)
		{
			sentence.clear();												//Clear the array list

			Chooser.showOpenDialog(null);
			File inputSentence = Chooser.getSelectedFile();					//Open the text file, put directory into file variable
																			
			try{															//Scan through the file
				input = new Scanner(inputSentence);
				}
				catch (FileNotFoundException e) 
				{
				}
			
			while (input.hasNext())											//Read through it and put into console for testing purposes
			{	
				sentence.add(input.next());
			}	
			System.out.println(sentence);
			input.close();
		}
		//-------------------------BUTTON 2 -------------------------
		if (event.getSource()==ChooseDictionary)
		{
			dictionary.clear();												//Clear arraylist
			Chooser.showOpenDialog(null);
			File inputDict = Chooser.getSelectedFile();	
																			//Same as above, open the file, scan it, put into array list, store directory in string format
			try 															//to allow editing the text file
			{ 
				input = new Scanner(inputDict);
			} catch (FileNotFoundException e) 
			{}
			
			while (input.hasNext())
			{	
				dictionary.add(input.next());
			}
			directory=inputDict;
			input.close();
			System.out.println(dictionary);
		}
		//-------------------------BUTTON 3-------------------------
		if(event.getSource()==Compare)
		{
			area1.setText("");												//Clear text area 
			
			if(dictionary.isEmpty() || sentence.isEmpty())					//Error check if user didn't choose dictionary or sentence to check
			{
				JOptionPane.showMessageDialog(this,"Please choose a dictionary and a file to compare");
				return;														//Exit if null
			}
			counter=0;														//Reset slang counter
			
			for(int i =0; i < dictionary.size();i++)						//Nested Loop to iterate and  compare both array lists
			{
				for(int j=0; j < sentence.size();j++)
				{
					if(sentence.get(j).contains(dictionary.get(i)))
					{
						counter=counter+1;
						area1.append("\nSlang word found: "+dictionary.get(i));
					}
				}
			}
			
			percentage = ((float)counter/sentence.size())*100;				//Get percentage of file that contains slang
			
			area1.append("\n\nThere are: "+counter+" Slang words");			//Print to user the amount of slang words, and percentage of text file that is slang
			area1.append("\nThe sentence is: "+format.format(percentage)+"% Slang");
			
			if(counter!=0)
			{
				JOptionPane.showMessageDialog(this,"The sentence is slang, it is not formal");
			}else
			{
				JOptionPane.showMessageDialog(this,"This is a formal sentence");
			}
		}
		if(event.getSource()==Edit)
		{
			//Button to edit dictionary, error check if file chosen
			if(dictionary.isEmpty())
			{
				JOptionPane.showMessageDialog(this,"Please choose a dictionary first to edit");
				return;
			}
			else
			{
				word = JOptionPane.showInputDialog("Please enter the word you want to input");		//User input for the new word in dictionary
				try {
					FileWriter out = new FileWriter(directory, true);								//Write to file a new line, the word and close
					out.write("\n");
					out.write(word);
					out.close();
					
				} catch (IOException e)
				{
					e.printStackTrace();
				}
				JOptionPane.showMessageDialog(this,"Dictionary updated, please choose your dictionary again");			//Choose dictionary file again to compare
			}
		}
		if(event.getSource()==Exit)
		{
			System.exit(0);
		}
	}
}