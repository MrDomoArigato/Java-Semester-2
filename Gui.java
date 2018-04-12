/* Java Assignment 2018
 * Dominik Dobrowolski C16347703
 * Language analyser checks for slang words and percentage of slang in the file
 * Allows to add new words to slang dictionary 
 */
package com.assignment;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
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
	//Variables to store directory as string
	private File directory;
	//Array lists used to store file input
	private static ArrayList<String> sentence = new ArrayList<String>();
	private static ArrayList<String> dictionary = new ArrayList<String>();
	
	/*File Chooser for user to use GUI to choose a file to open*/
	private	JFileChooser Chooser=new JFileChooser();
	private FileNameExtensionFilter filter;
	
	/*Java buttons and text area for testing and user input*/
	private JButton ChooseFile,ChooseDictionary,Compare,Edit;
	private JTextArea area1;
	private int counter;
	
	/*Scanner to read through the file and input the values*/
	private Scanner input = new Scanner(System.in);
	
	//Double to store percentage of file that is slang
	private double percentage;
	
	//Format the output to 2 decimal places, don't want huge floating point values
	NumberFormat format = new DecimalFormat("#0.00");
	
	//Store users word input
	private String word;
	
	Gui(String title)
	{
		
		/*Edit options of the gui layout etc*/
		super(title);
		setSize(800,400);
		setLayout(new FlowLayout());
		setLocation(100,100);
		
		/*Edit the size of the FileChooser*/
		Chooser.setPreferredSize(new Dimension(1000,500));

		/*Button to start the FileChooser and choose what file to read through*/
		ChooseFile=new JButton("Click to choose text file to check");
		ChooseFile.addActionListener(this);
		add(ChooseFile);
		
		ChooseDictionary = new JButton("Click to choose dictionary");
		ChooseDictionary.addActionListener(this);
		add(ChooseDictionary);
		
		Compare = new JButton("COMPARE!");
		Compare.addActionListener(this);
		add(Compare);
		
		Edit = new JButton("Click this to edit dictionary in use");
		Edit.addActionListener(this);
		add(Edit);
		

		area1=new JTextArea(15,15);
		add(area1);

		setVisible(true);
		
	}
	
	/*Actions performed by user are set here*/
	public void actionPerformed(ActionEvent event) 
	{
		//Open only text files and set it to default text files
		filter = new FileNameExtensionFilter("Text Files","txt");
		//Set default as text files only
		Chooser.addChoosableFileFilter(filter);
		Chooser.setAcceptAllFileFilterUsed(false);
		
		//BUTTON 1 -------------------------------------
		if(event.getSource()==ChooseFile)
		{
			sentence.clear();
			//Open the text file, put in to file variable
			Chooser.showOpenDialog(null);
			File inputSentence = Chooser.getSelectedFile();
			
			//Scan through the file
			try{
				input = new Scanner(inputSentence);
				}
				catch (FileNotFoundException e) 
				{
				}
			
			//Read through it and for now put it inside the text area
			while (input.hasNext())
			{	
				sentence.add(input.next());
			}	
			System.out.println(sentence);
			input.close();
		}
		//BUTTON 2 -------------------------
		if (event.getSource()==ChooseDictionary)
		{
			//Clear arraylist and input new values 
			dictionary.clear();
			Chooser.showOpenDialog(null);
			File inputDict = Chooser.getSelectedFile();	
			
			try 
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
		//BUTTON 3 -------------------------
		if(event.getSource()==Compare)
		{
			//Clear text area 
			area1.setText("");
			
			//Error check if user didn't choose dictionary or has word list
			if(dictionary.isEmpty() || sentence.isEmpty())
			{
				JOptionPane.showMessageDialog(this,"Please choose a dictionary and a file to compare");
				return;
			}
			counter=0;//Reset counter
			//Loop to iterate and  compare both array lists
			for(int i =0; i < dictionary.size();i++)
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
			
			//Get percentage of file that contains slang
			percentage = ((float)counter/sentence.size())*100;
			
			//Print to user the amount of slang words, and percentage of text file that is slang
			area1.append("\n\nThere are: "+counter+" Slang words");
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
				//User input for the new word in dictionary
				word = JOptionPane.showInputDialog("Please enter the word you want to input");
				try {
					
					FileWriter out = new FileWriter(directory, true);
					out.write("\n");
					out.write(word);
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				//Choose dictionary again
				JOptionPane.showMessageDialog(this,"Dictionary updated, please choose your dictionary again");
			}
		}
	}
}