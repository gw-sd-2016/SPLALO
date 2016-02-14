package pureToneTests;
//PLAY USER INPUT FREQUENCY, WRITE TO .WAV FILE AND WORKING ON PROCESSING FOR FREQUENCY

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.sound.sampled.*;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;
import be.tarsos.dsp.pitch.PitchProcessor.PitchEstimationAlgorithm;

import jm.music.data.Phrase;
import jm.util.View;
import jm.JMC;
import jm.constants.*;

public class PureToneTests implements JMC{

	public static double[] setTones = {16.35, 17.32, 18.35, 19.45, 20.60, 21.83, 23.12, 24.50, 25.96, 27.50, 29.14, 30.87, 32.70, 34.65, 36.71, 38.89, 41.20, 43.65, 46.25, 49, 51.91, 55, 58.27, 61.74, 65.41, 69.30, 73.42, 77.78, 82.41, 87.31, 92.50, 98, 103.83, 110, 116.54, 123.47, 130.81, 138.59, 146.83, 155.56, 164.81, 174.61, 185, 196, 207.65, 220, 233.08, 246.94, 261.63, 277.18, 293.66, 311.13, 329.63, 349.23, 369.99, 392, 415.30, 440, 466.16, 493.88, 523.25, 554.37, 587.33, 622.25, 659.25, 698.46, 739.99, 783.99, 830.61, 880, 932.33, 987.77, 1046.50, 1108.73, 1174.66, 1244.51, 1318.51, 1396.91, 1479.98, 1567.98, 1661.22, 1760, 1864.66, 1975.53, 2093, 2217.46, 2349.32, 2489.02, 2637.02, 2793.83, 2959.96, 3135.96, 3322.44, 3520, 3729.31, 3951.07, 4186.01, 4434.92, 4698.63, 4978.03, 5274.04, 5587.65, 5919.91, 6271.93, 6644.88, 7040, 7458.62, 7902.13};
	public static double[] constantTones = {16.352, 17.324, 18.354, 19.455, 20.601, 21.827, 23.124, 24.499, 25.956, 27.50, 29.135, 30.868};
	public static int[] jMusicConstants = {jm.constants.Pitches.C0, jm.constants.Pitches.D0, jm.constants.Pitches.E0, jm.constants.Pitches.F0, jm.constants.Pitches.G0, jm.constants.Pitches.A0, jm.constants.Pitches.B0, jm.constants.Pitches.C1, jm.constants.Pitches.D1, jm.constants.Pitches.E1, jm.constants.Pitches.F1, jm.constants.Pitches.G1, jm.constants.Pitches.A1, jm.constants.Pitches.B1, jm.constants.Pitches.C2, jm.constants.Pitches.D2, jm.constants.Pitches.E2, jm.constants.Pitches.F2, jm.constants.Pitches.G2 , jm.constants.Pitches.A2, jm.constants.Pitches.B2, jm.constants.Pitches.C3, jm.constants.Pitches.D3, jm.constants.Pitches.E3, jm.constants.Pitches.F3, jm.constants.Pitches.G3, jm.constants.Pitches.A3, jm.constants.Pitches.B3, jm.constants.Pitches.C4, jm.constants.Pitches.D4, jm.constants.Pitches.E4, jm.constants.Pitches.F4, jm.constants.Pitches.G4 , jm.constants.Pitches.A4, jm.constants.Pitches.B4, jm.constants.Pitches.C5, jm.constants.Pitches.D5, jm.constants.Pitches.E5, jm.constants.Pitches.F5, jm.constants.Pitches.G5, jm.constants.Pitches.A5, jm.constants.Pitches.B5, };
	
	
	public static final int testSampleRate = 11025;
	public static int num;
	public static void main(String[] args) 
	{
		Scanner keyboard = new Scanner(System.in);
		
		//Get length of array
		System.out.printf("There are %d notes in the array\n", setTones.length);
		System.out.printf("The values for C  from the setTones array are: \n");

		for(int i = 0; i < setTones.length; i+=12)
			System.out.printf("%.2f \t", setTones[i]);

		System.out.printf("\nThe values for C from the constant Tones array are: \n");
		for(int i = 0; i <= 8; i++)
			System.out.printf("%.2f \t", (constantTones[0] * Math.pow(2, i)));

		
		//Request and generate pure tone	
		System.out.println("\n\nWhat is the frequency you want to play?: ");
		double frequency = keyboard.nextFloat();		
		double wave[] = producePureTone(frequency);

		
		//Play pure tone
		AudioFormat format = new AudioFormat(testSampleRate, 16, 1, true, false);
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);


		//The following step is for testing purposes
		//Write produced sound to a .wav file
		File audioFile = new File("testPureTone.wav");
		
		System.out.println("\nWriting to file ...");
		save(audioFile.toString(), wave);
		System.out.println("Written to file.");

		
		//Determine frequency of sound in .wav file
		double doubleValues[];
		byte byteValues[]; 
		
		try 
		{
			File audioFile2 = new File("just-major-sc.wav");						
			AudioInputStream input = AudioSystem.getAudioInputStream(audioFile);
			byteValues = new byte[input.available()];
			input.read(byteValues);
			
			doubleValues = new double[byteValues.length/2];
			for(int i = 0; i < byteValues.length; i+=2)
			{
				doubleValues[i/2] = (short) (((byteValues[i+1] & 0xff) << 8) | (byteValues[i] & 0xff));
				doubleValues[i/2] /= 32768;			
			}
			
			System.out.println("Format: " + input.getFormat());
			System.out.println(byteValues.length);
			
			
			//Set to array of complex number values for processing
			int windowSize = testSampleRate/10;
			double timeStart = System.currentTimeMillis();
			List<ArrayList<Float>> freqArray = new ArrayList<ArrayList<Float>>(wave.length/windowSize); 
			ArrayList<Note> noteArray = new ArrayList<Note>(wave.length/windowSize); 
			
			for(int j = 0; j < wave.length - windowSize; j+= windowSize)
			{
				ComplexNum complexArray [] = new ComplexNum[testSampleRate/20]; 
				for(int i = 0; i < complexArray.length; i++)
				{
					ComplexNum tempor = new ComplexNum(doubleValues[i+j], 0);
					complexArray[i] = tempor;
				}
				
				

				
				List<Float> f = findFrequency(complexArray);
				Note newNote = new Note();
				
				if(j == 0)
				{
					freqArray.add((ArrayList<Float>) f);
					for(Float freq: f)
						newNote.setNoteValue(compareFreq(freq));
						
					newNote.setStartTime((double) j/(double) testSampleRate);
					newNote.setFrequency(f);
					newNote.num = num;
					noteArray.add(newNote);
					
				}
				else
				{
					if( !f.containsAll(freqArray.get(freqArray.size()-1)) )
						{
							newNote.setStartTime((double) j/(double) testSampleRate);
							//System.out.println(j + ", " + j/windowSize + ", " + noteArray.size());
							noteArray.get(noteArray.size()-1).setEndTime((double) j/(double) testSampleRate);
							freqArray.add((ArrayList<Float>) f);
							for(Float freq: f)
								newNote.setNoteValue(compareFreq(freq));
							
							
							newNote.setFrequency(f);
							newNote.num = num;
							noteArray.add(newNote);
						}
				}
			}
						noteArray.get(noteArray.size()-1).setEndTime(wave.length/testSampleRate);
			for(int i = 0; i < noteArray.size(); i++)
				System.out.println("Note/Chord/Rest " + (i+1) + " has " + noteArray.get(i).getNoteValues().toString() + " at time " + noteArray.get(i).getStartTime());
			

			double timeEnd = System.currentTimeMillis();
			System.out.println("It took " + (timeEnd - timeStart)/1000 + " seconds. Do better ...");
			
			
			//Tempo attempt and timing values
			double tempo = simulateTempoAnalysis(noteArray, wave.length/testSampleRate);
			
			
			//DISPLAY AS SHEET MUSIC
			Phrase phr = new Phrase();
			
			for(int i = 0; i < noteArray.size(); i++)
			{
				//System.out.println(noteArray.get(i).getNoteValue().get(0));
				jm.music.data.Note n = new jm.music.data.Note();
				if(noteArray.get(i).getTimingValue() == "QUARTER_NOTE")
					n.setDuration(jm.constants.Durations.QUARTER_NOTE );
				

				System.out.println(jMusicConstants[noteArray.get(i).num]);
				//n = new jm.music.data.Note(jm.constants.Pitches.A4, jm.constants.Durations.QUARTER_NOTE );
				n.setPitch(jMusicConstants[noteArray.get(i).num]);
				phr.addNote(n);
			}
			

	        View.notate(phr);
			
		} 
		
		catch (FileNotFoundException e) {e.printStackTrace();} 
		catch (IOException e) {e.printStackTrace();}
		catch (UnsupportedAudioFileException e)	{e.printStackTrace();}
		

	}

	public static String findNoteValue(int val)
	{
		//This method accepts some number that represents a note value in the array of frequencies and returns a string.
		//For example, a frequency that has an index of 0 mod 12 represents a C in some octave. 1 mod 12 represents C#, and so on.

		switch(val)
		{
			case 0:
			{
				num = 0;
				return "C";
			}
			
			case 1:
			{	
				num = 0;
				return "C_SHARP";
			}	
			case 2:
			{
				num = 1;
				return "D";
			}
			case 3:
			{
				num = 1;
				return "D_SHARP";		
			}
			
			case 4:
			{
				num = 2;
				return "E";
			}
			
			case 5:
			{
				num = 3;
				return "F";
			}
			
			case 6:
			{	
				num = 3;
				return "F_SHARP";
			}
			
			case 7:
			{	
				num = 4;
				return "G";			
			}
			
			case 8:
			{
				num = 4;
				return "G_SHARP";	
			}
			
			case 9:
			{
				num = 5;
				return "A";	
			}
			
			case 10:
			{
				num = 5;
				return "A_SHARP";	
			}
			
			case 11:
			{
				num = 6;
				return "B";	
			}
			
			default: 
				return "There is an issue.";

		}

	}

	

	public static String compareFreq(double fileFreq)
	{
		//A for-loop runs from 0 to 8. These values represent the octave the pure note can be found in
		for(int i = 0; i <= 8; i++)
		{
			if(fileFreq <= 1.025 * constantTones[11] * (Math.pow(2,i)) )
			{
				//If the note appears to be in this octave, the program attempts to determine the note
				for(int j = 0; j < constantTones.length; j++)
				{
					if(fileFreq <= (1.025 * constantTones[j] * (Math.pow(2,i)) )  )
					{
						num = 0;
						String note = findNoteValue(j);
						num = (i*7) + num;

						System.out.println(num + ", Octave = " + i);
						String fullNote = note + i;
						return fullNote;
						
						
					}

				}

			}

		}

		return "Invalid";
	}
	

	public static double[] producePureTone(double freq)
	{
		int sampleRate = testSampleRate;
		int numSamples = sampleRate * 5;
		double array[] = new double[numSamples];
		double sampleTime;
		double constantPeriod = 2*Math.PI*freq;
		
		/*
		for(int i = 0; i < array.length; i++)
		{
			sampleTime = ((double)i)/sampleRate;
			//array[i] = 0.5* ( Math.sin(constantPeriod * (sampleTime)) + 0.02*Math.sin(2*Math.PI*440*(sampleTime)) + 0.1*Math.sin(2*Math.PI*110*(sampleTime)) + 0.02*Math.sin(2*Math.PI*220*(sampleTime)));	//Equivalent to y= sin(2Pi* frequency of note * t)
			array[i] = 0.5* ( Math.sin(constantPeriod * (sampleTime)) + Math.sin(2*Math.PI*440*(sampleTime)) );	//Equivalent to y= sin(2Pi* frequency of note * t)
			//array[i] = Math.sin(constantPeriod * (sampleTime));
		}
		
		//Test on changing note of halved frequency
		for(int i = array.length/2; i < array.length; i++)
		{
			sampleTime = ((double)i)/sampleRate;
			array[i] = Math.sin(Math.PI*freq * (sampleTime));
		}
		*/
		
		
		
		//Series of pure tones for testing
		//C4
		for(int x = 0; x < array.length/5; x++)
			array[x] = Math.sin(2 * Math.PI * 261.63 * ((double)x)/sampleRate);
		
		//E4
		for(int x = array.length/5; x < 2 * array.length/5; x++)
			array[x] = Math.sin(2 * Math.PI * 329.63 * ((double)x)/sampleRate);
		
		//G4
				for(int x = 2 * array.length/5; x < 3 * array.length/5; x++)
					array[x] = Math.sin(2 * Math.PI * 392.00 * ((double)x)/sampleRate);
		

		//E4
		for(int x = 3 * array.length/5; x < 4 * array.length/5; x++)
			array[x] = Math.sin(2 * Math.PI * 329.63 * ((double)x)/sampleRate);
		

		//C4
		for(int x = 4 * array.length/5; x < array.length; x++)
			array[x] = Math.sin(2 * Math.PI * 261.63 * ((double)x)/sampleRate);
		
		
		
		return array;
		
	}

	
	
	public static List<Float> findFrequency(ComplexNum[] wave)
	{
		//The following method is an attempt to use DFT to find the frequencies of a wave
		//It accepts an array of complex numbers with discrete values from the sound
		
		ComplexNum temp = new ComplexNum();
		ComplexNum sum = new ComplexNum(0,0);
		
		double mag[] = new double [wave.length];
		long timeStart = System.currentTimeMillis();
		for(int k = 0; k < wave.length; k++)
		{	
			sum.setReal(0.0);
			sum.setImaginary(0.0);
			
			for(int t = 0; t < wave.length; t++)
			{
				double realTemp = Math.cos(-2 * Math.PI * k * t /wave.length);
				double imagTemp = Math.sin(- 2 * Math.PI * k * t /wave.length);
				
				temp.setReal((realTemp*wave[t].getReal()));
				temp.setImaginary((imagTemp*wave[t].getReal()));
				
				sum = sum.add(temp);	
			}
			
			mag[k] = sum.magnitude();
			
		}
		
		List<Float> found = process(mag, testSampleRate, wave.length, 0.5);
		List<Float> foundFrequencies = new ArrayList<Float>();
		long timeEnd = System.currentTimeMillis();
		for (float freq : found) 
		{
			if(freq > 20 && freq < 20000)
				foundFrequencies.add(freq);
		}

	    return (foundFrequencies);
	}
	
	

	  public static List<Float> process(double freqDomain[], float sampleRate, int numSamples, double sigma) 
	  {
		//Calculate standard deviation
		  //Find Average
	    double average = 0;
	    for (double x: freqDomain) 
	    	average += x;
	    average = average/freqDomain.length;

	    //Calculate variance
	    double variance = 0;
	    for (double x: freqDomain)
	      variance += Math.pow((x - average), 2);
	    
	    variance /= freqDomain.length;
	    double stdev = Math.sqrt(variance);
	    

	    ArrayList<Float> found = new ArrayList<Float>();
	    double max = Integer.MIN_VALUE;
	    int maxF = -1;
	    for (int f = 0; f < freqDomain.length/2; f++) {
	      if (freqDomain[f] > average+sigma*stdev) 
	      {
	        if (freqDomain[f] > max) 
	        {
	          max = freqDomain[f];
	          maxF = f;
	        }
	      } 
	      
	      else 
	      {
	        if (maxF != -1) 
	        {
	          found.add(maxF * sampleRate/numSamples);
	          max = Integer.MIN_VALUE;
	          maxF = -1;
	        }
	      }
	      
	    }

	    return (found);
	  }
	  
	  
	  
	    public static void save(String filename, double[] samples) {

	        // assumes 44,100 samples per second
	        // use 16-bit audio, mono, signed PCM, big Endian
	        AudioFormat format = new AudioFormat(testSampleRate, 16, 1, true, true);
	        byte[] data = new byte[2 * samples.length];
	        for (int i = 0; i < samples.length; i++) {
	            int temp = (short) (samples[i] * 32767);
	            data[2*i + 1] = (byte) temp;
	            data[2*i + 0] = (byte) (temp >> 8);
	        }

	        // now save the file
	        try {
	            ByteArrayInputStream bais = new ByteArrayInputStream(data);
	            AudioInputStream ais = new AudioInputStream(bais, format, samples.length);
	            if (filename.endsWith(".wav") || filename.endsWith(".WAV")) {
	                AudioSystem.write(ais, AudioFileFormat.Type.WAVE, new File(filename));
	            }
	        
	            else {
	                throw new RuntimeException("File format not supported: " + filename);
	            }
	        }
	        catch (IOException e) {
	            System.out.println(e);
	            System.exit(1);
	        }
	    }

	    
	    public static double simulateTempoAnalysis(ArrayList<Note> noteArray, double songLength)
	    {
	    	double tempo = 0, tempTimeFrame;
	    	double shortest = Double.MAX_VALUE, longest = 0;
	    	for(int i = 0; i < noteArray.size(); i++)
	    	{
	    		tempTimeFrame = noteArray.get(i).calculateTimeFrame();
	    		
	    		if(shortest > tempTimeFrame)
	    			shortest = tempTimeFrame;
	 
	    		if(longest < tempTimeFrame)
	    			longest = tempTimeFrame;

	    	}
	    	longest = Double.parseDouble(String.format("%.2f", longest));
	    	System.out.println(longest);
	    	shortest = Double.parseDouble(String.format("%.2f", shortest));
	    	System.out.println(shortest);
	    	
	    	
	    	//If all note values are the same, a simple calculation will do
	    	if(longest == shortest)
	    	{
	    		System.out.println("Tempo is " + 60/(songLength/noteArray.size()) + " BPM");
	    		for(int i = 0; i < noteArray.size(); i++)
	    			noteArray.get(i).setTimingValue(jm.constants.Durations.QUARTER_NOTE);
	    	}
	    	
	    	
	    	//If they differ, comparative timing analysis must be done.
	    	else
	    	{
	    		
	    	}
	    	
	    	//Determine individual timing values
	    	
	    	
	    	return tempo;
	    }
	    
	    
	    
}
