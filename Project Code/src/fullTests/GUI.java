package fullTests;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import jm.gui.sketch.SketchScore;
import jm.music.data.Score;
import pureToneTests.Note; 

public class GUI extends JFrame{

	private JTextField timeFrame;
	private JLabel label;
	private JButton recordButton;
	
	public GUI()
	{
		super("SPLALO");
		setLayout(new FlowLayout());
		
		timeFrame = new JTextField("0", 3);
		add(timeFrame);
		
		label = new JLabel("s           ");
		add(label);
		
		recordButton = new JButton("Record");
		recordButton.setEnabled(false);
		add(recordButton);
		
		
		action handler = new action();
		recordButton.addActionListener(handler);
		timeFrame.addActionListener(handler);
	}

	
	private class action implements ActionListener{
		
		public void actionPerformed(ActionEvent event)
		{

			int recordingTime = Integer.parseInt(timeFrame.getText());
			
			if(recordingTime > 0)
				recordButton.setEnabled(true);
			
			if(event.getSource() == recordButton)
			{
				
					/*ATTEMPT TO RECORD AUDIO FROM STANDARD MICROPHONE*/
				int sampleRate = 44100;
				String fileName = "testRecordGUI.wav";
				
				recordButton.setText("Recording ... ");
				add(recordButton);
				
				RecordAudio recAudio = new RecordAudio(sampleRate, recordingTime, fileName);
				recAudio.record();
				
				recordButton.setText("Record");
				
				
				
				
					/*ATTEMPT TO PROCESS AUDIO (CHANGING AUDIO FILE TO ARRAY OF DOUBLES)*/
				ProcessAudio pa = new ProcessAudio(fileName, sampleRate);
				double wave[] = pa.AudioFileToDoubleArray();
				
//				
//				int windowSize = sampleRate/10;
//				List<ArrayList<Float>> freqArray = new ArrayList<ArrayList<Float>>(wave.length/windowSize); 
//				ArrayList<Note> noteArray = new ArrayList<Note>(wave.length/windowSize); 
//				
//				for(int j = 0; j < wave.length - windowSize; j+= windowSize)
//				{
//					ComplexNum[] compWave = pa.DoubleArraytoComplexArray(wave,j);
//					List<Float> f = ProcessFrequency.findFrequency(compWave);
//				}
			}
				
		} 
		
	}
	
}
