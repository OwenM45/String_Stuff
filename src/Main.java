/* Assignment 1: string stuff
 * By: Owen Maertens
 * Started: 2/14/2018
 */


import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.*;
import javax.swing.*;

public class Main extends JFrame implements ActionListener
{

	//Declare all the GUI elements
	JPanel panel;
	
	JRadioButton radMaxRun;
	JRadioButton radBlowUp;
	JRadioButton radBoth;
	
	JLabel lblInstructions;
	JLabel lblAnswer;
	JLabel lblWarning;
	
	JTextField txtUserInput;
	
	JButton btnParse;
	JButton btnRead;
	
	//boolean that tells the program if its reading from a text file
	boolean reading = false;
	
	//Constructor
	public Main() 
	{
		// initiate the GUI
        initUI();
    }
	
	private void initUI() 
	{ 
		//Set up the GUI window
		setTitle("String Stuff (Owen M)");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        //Set up the area where the GUI is displayed
        panel = new JPanel();
        panel.setLayout(null);
        
        //Set up the max run, blow up and both radio buttons
        radMaxRun = new JRadioButton("Max Run");
        radMaxRun.setBounds(50, 30, 80, 20);
        radMaxRun.addActionListener(this);  
        panel.add(radMaxRun);
        
        radMaxRun.setSelected(true);
        
        radBlowUp = new JRadioButton("Blow Up");
        radBlowUp.setBounds(150, 30, 80, 20);
        radBlowUp.addActionListener(this);  
        panel.add(radBlowUp);
        
        radBoth = new JRadioButton("Both");
        radBoth.setBounds(250, 30, 80, 20);
        radBoth.addActionListener(this);  
        panel.add(radBoth);
        
        //sets up the label that instructs the user what to do
        lblInstructions = new JLabel("Select what you would like to do to the string:");
        lblInstructions.setBounds(50, 10, 280, 20);  
        panel.add(lblInstructions);
        
        //displays the result of the blow up or max run
        lblAnswer = new JLabel("Result:");
        lblAnswer.setBounds(50, 110, 280, 20);  
        panel.add(lblAnswer);
        
        //sets up the area where the user enters the string they want to mess with
        txtUserInput = new JTextField("Enter the string you want to parse");
        txtUserInput.setBounds(50, 80, 190, 20);  
        panel.add(txtUserInput);
        
        //sets up the button to parse from the text box
        btnParse = new JButton("Parse!");
        btnParse.setBounds(245, 80, 80,20);
        btnParse.addActionListener(this);
        panel.add(btnParse);
        
        //sets up the button to parse from the txt file
        btnRead = new JButton("Read from txt file");
        btnRead.setBounds(240, 110, 140,20);
        btnRead.addActionListener(this);
        panel.add(btnRead);
        
        //sets up label that warns user of an empty text box
        lblWarning = new JLabel("You must enter something into the text box");
        lblWarning.setBounds(50, 140, 280, 20);  
        lblWarning.setVisible(false);
        panel.add(lblWarning);
        
        //makes the gui visible when its rendered
        this.getContentPane().add(panel);
	
	}
	
	public static void main(String[] args) 
	{
		//displays the gui window
		EventQueue.invokeLater(() -> {
            Main MAIN = new Main();
            MAIN.setVisible(true);
        });
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// makes it so only one radio button can be selected at a time
		if (e.getSource() == radMaxRun )
		{
			radBlowUp.setSelected(false);
			radBoth.setSelected(false);
		}
		else if (e.getSource() == radBlowUp)
		{
			radMaxRun.setSelected(false);
			radBoth.setSelected(false);
		}
		else if (e.getSource() == radBoth)
		{		
			radBlowUp.setSelected(false);
			radMaxRun.setSelected(false);
		}
		
		if (txtUserInput.getText().length() > 1)
		{
			//calls the correct function based on the selected radio button
			if (e.getSource() == btnParse)
			{
				reading = false;
				if (radMaxRun.isSelected() == true)
				{
					maxRun(null);
				}
				else if (radBlowUp.isSelected() == true)
				{
					blowUp();
				}
				else if (radBoth.isSelected() == true)
				{
					blowUp();
				}
			}
			
			
			lblWarning.setVisible(false);
		}
		else
		{
			lblWarning.setVisible(true);
		}
		// calls the correct function based on the radio button and uses the text from the text file
		if (e.getSource() == btnRead)
		{
			reading = true;
			if (radMaxRun.isSelected() == true)
			{
				maxRun(null);
			}
			else if (radBlowUp.isSelected() == true)
			{
				blowUp();
			}
			else if (radBoth.isSelected() == true)
			{
				blowUp();
			}			
			lblWarning.setVisible(false);
		}
		 
	}
	
	public String readFile()
	{
		
		System.out.println("read file called");
		//setup local variables
		String fileName = "input.txt";
        String line = "";
		
		try {
            //reads text files
            FileReader fileReader = 
                new FileReader(fileName);

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            line = bufferedReader.readLine();

            // Closes the file
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
        	//checks if there was an error finding the file
        }
		catch(IOException ex) {
            //checks if there was a problem reading the file                  
        }	
		
		return line;
	}
	
	public void writeFile(String output) 
	{
		//sets up local variables
        String fileName = "output.txt";

        try {
            FileWriter fileWriter =
                new FileWriter(fileName);

            BufferedWriter bufferedWriter =
                new BufferedWriter(fileWriter);

            bufferedWriter.write(output);

            //Close the file
            bufferedWriter.close();
        }
        catch(IOException ex) 
        {
            //checks if the program couldn't find the file
        }
    }
	
	public void maxRun(String blownUp)
	{
		String userInput;
		
		if (radBoth.isSelected() == false && reading == false)
		{
			userInput = txtUserInput.getText();
		}
		else if (blownUp != null)
		{
			userInput = blownUp; 
		}
		else
		{
			userInput = readFile();				
		}
		
		userInput += "e";
		
		char[] arrayOfChars = userInput.toCharArray();
		
		int currentRunCount = 1;
		
		int maxRunCount = 1;
		char maxRunChar = ' ';
		
		for(int i = 0; i < arrayOfChars.length; i++)
		{
			
			try
			{
				if (arrayOfChars[i] == arrayOfChars[i+1] )
				{
					currentRunCount ++;
				}
				else if (currentRunCount > maxRunCount)
				{
					maxRunCount = currentRunCount;
					maxRunChar = arrayOfChars[i];
					currentRunCount = 1;
				}
				else
				{
					currentRunCount = 1;
				}  
			}
			catch(Exception e)
			{
				
			}
					
		}
		
		lblAnswer.setText("Result: " + String.valueOf(maxRunCount) + String.valueOf(maxRunChar));
		writeFile(String.valueOf(maxRunCount) + String.valueOf(maxRunChar));
	}
	
	public void blowUp()
	{
		String userInput;
		if (reading == false)
		{
			userInput = txtUserInput.getText();
		}
		else
		{
			userInput = readFile();
		}
		char[] arrayOfChars = userInput.toCharArray();
		
		String outputString = "";
	
		for(int i = 0; i < arrayOfChars.length; i++)
		{
			if (Character.isDigit(arrayOfChars[i]) && Character.isDigit(arrayOfChars[i+1]) == false)
			{
				String numChar = String.valueOf(arrayOfChars[i]);
				int num = Integer.valueOf(numChar);
				System.out.println();
				for(int j = -1; j < num; j++ )
				{
					outputString += arrayOfChars[i+1];
				}

			}
			try 
			{
				if (Character.isDigit(arrayOfChars[i]) == false && Character.isDigit(arrayOfChars[i+1]) == false)
				{
					outputString += arrayOfChars[i];
				}
				else if (Character.isDigit(arrayOfChars[i]) == false && Character.isDigit(arrayOfChars[i-1]) == false)
				{
					outputString += arrayOfChars[i];
				}
			}
			catch (Exception e)
			{
				
			}
			
			
		}
		
		if(radBoth.isSelected() == true)
		{		
			maxRun(outputString);
		}
		else
		{
			lblAnswer.setText("Result: " + outputString);
			writeFile(outputString);
		}
	}

}
