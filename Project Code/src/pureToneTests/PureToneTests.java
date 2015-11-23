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


class PureToneTests {

	public static double[] setTones = {16.35, 17.32, 18.35, 19.45, 20.60, 21.83, 23.12, 24.50, 25.96, 27.50, 29.14, 30.87, 32.70, 34.65, 36.71, 38.89, 41.20, 43.65, 46.25, 49, 51.91, 55, 58.27, 61.74, 65.41, 69.30, 73.42, 77.78, 82.41, 87.31, 92.50, 98, 103.83, 110, 116.54, 123.47, 130.81, 138.59, 146.83, 155.56, 164.81, 174.61, 185, 196, 207.65, 220, 233.08, 246.94, 261.63, 277.18, 293.66, 311.13, 329.63, 349.23, 369.99, 392, 415.30, 440, 466.16, 493.88, 523.25, 554.37, 587.33, 622.25, 659.25, 698.46, 739.99, 783.99, 830.61, 880, 932.33, 987.77, 1046.50, 1108.73, 1174.66, 1244.51, 1318.51, 1396.91, 1479.98, 1567.98, 1661.22, 1760, 1864.66, 1975.53, 2093, 2217.46, 2349.32, 2489.02, 2637.02, 2793.83, 2959.96, 3135.96, 3322.44, 3520, 3729.31, 3951.07, 4186.01, 4434.92, 4698.63, 4978.03, 5274.04, 5587.65, 5919.91, 6271.93, 6644.88, 7040, 7458.62, 7902.13};
	public static double[] constantTones = {16.352, 17.324, 18.354, 19.455, 20.601, 21.827, 23.124, 24.499, 25.956, 27.50, 29.135, 30.868};

	public static int testSampleRate = 11025;
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
		AudioFormat format = new AudioFormat(testSampleRate, 16, 1, true, true);
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

		byte [] buffer = new byte[wave.length*8];
		for(int i = 0; i < wave.length; i++)
		{
			byte[] bytes = new byte[8];
		    ByteBuffer.wrap(bytes).putDouble(wave[i]);
		
		    for(int j = 0; j < 8; j++)
		    	buffer[(i*8)+j] = bytes[j];
		    		
		}



		//The following step is for testing purposes
		//Write produced sound to a .wav file
		File audioFile = new File("testPureTone.wav");
		
		System.out.println("\nWriting to file ...");
		save(audioFile.toString(), wave);
		System.out.println("Written to file.");

	
		
		
		/*
		//Attempt to play test sound out loud
		try 
		{
			final SourceDataLine sourceLine = (SourceDataLine) AudioSystem.getLine(info);
			sourceLine.open(format);
			sourceLine.start();

			//System.out.println("Playing ...");
			//sourceLine.write(buffer, 0, buffer.length);
		
			
			sourceLine.stop();
			sourceLine.close();
			//System.out.println("Stopping.\n");
		} 

		catch (LineUnavailableException e) 
			{e.printStackTrace();}
		*/
		
		
		
		//Determine frequency of sound in .wav file

		double doubleValues[];
		byte byteValues[]; 
		
		try 
		{
			
			AudioInputStream input = AudioSystem.getAudioInputStream(audioFile);
			byteValues = new byte[input.available()];
			//InputStream input = new FileInputStream(audioFile);
			input.read(byteValues);
			/*
			for(int i = 0; i+8 <= byteValues.length; i+=8)
			{
				
				byte miniByteArray[] = new byte [8];
				for(int j = 0; j < 8; j++)
				{
					if(i == 80)
						System.out.print(byteValues[i+j] + ", ");
					
					miniByteArray[j] = byteValues[i+j];
				}
				doubleValues[i/8] = ByteBuffer.wrap(miniByteArray).getDouble();
				//System.out.println("i Value: " + i/8 + "Double: " + doubleValues[i/8]);
			}
			*/
			
			doubleValues = new double[byteValues.length/2];
			for(int i = 0; i < doubleValues.length; i++)
				doubleValues[i] = (short)(((byteValues[i] & 0xff) << 8) + (byteValues[i+1] & 0xff))/(short)32767;

			
			//Set to array of complex number values for processing
			int windowSize = testSampleRate/4;
			double timeStart = System.currentTimeMillis();
			List<ArrayList<Float>> freqArray = new ArrayList<ArrayList<Float>>(wave.length/windowSize); 
			for(int j = 0; j < wave.length - windowSize; j+= windowSize)
			{
				ComplexNum complexArray [] = new ComplexNum[testSampleRate/20]; 
				for(int i = 0; i < complexArray.length; i++)
				{
					ComplexNum tempor = new ComplexNum(wave[i+j], 0);
					complexArray[i] = tempor;
				}
				
				List<Float> f = findFrequency(complexArray);
				if(j == 0)
					{
						freqArray.add((ArrayList<Float>) f);
						for(Float freq: f)
							{
								compareFreq(freq);
								System.out.println();
							}
					}
				else
				{
					if( !f.containsAll(freqArray.get(freqArray.size()-1)) )
						{
							freqArray.add((ArrayList<Float>) f);
							for(Float freq: f)
							{
								compareFreq(freq);
								System.out.println();
							}
						}
				}
			}
						
			for(int i = 0; i < freqArray.size(); i++)
				System.out.println("Note/Chord " + (i+1) + " has " + freqArray.get(i).toString());
			

			double timeEnd = System.currentTimeMillis();
			System.out.println("It took " + (timeEnd - timeStart)/1000 + " seconds. Do better ...");
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
				return "C";

			case 1:
				return "C#";
				
			case 2:
				return "D";		

			case 3:
				return "D#";		

			case 4:
				return "E";

			case 5:
				return "F";

			case 6:
				return "F#";

			case 7:
				return "G";			

			case 8:
				return "G#";		

			case 9:
				return "A";		

			case 10:

				return "A#";

			case 11:

				return "B";

			default: 
				return "There is an issue.";

		}

	}

	

	public static void compareFreq(double fileFreq)
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
						String note = findNoteValue(j);
						System.out.print(note + "" + i + " ");
						break;
					}

				}

				break;

			}

		}

	}
	

	public static double[] producePureTone(double freq)
	{
		int sampleRate = testSampleRate;
		int numSamples = sampleRate * 5;
		double array[] = new double[numSamples];
		double sampleTime;
		double constantPeriod = 2*Math.PI*freq;
		
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
		
		return array;

	}

	
	
	public static List<Float> findFrequency(ComplexNum[] wave)
	{
		//The following method is an attempt to use DFT to find the frequencies of a wave
		//It accepts an array of complex numbers with discrete values from the sound
		
		//DFT loop to determine strength of frequency
		ComplexNum strength[] = new ComplexNum[wave.length];		//THis variable will be used to store the strength of a frequency in the wave
		ComplexNum temp = new ComplexNum();
		ComplexNum sum = new ComplexNum(0,0);
		//ComplexNum fraction = new ComplexNum((1/strength.length), 0);
		double mag[] = new double [wave.length];
		long timeStart = System.currentTimeMillis();
		for(int k = 0; k < wave.length; k++)
		{	
			sum.setReal(0.0);
			sum.setImaginary(0.0);
			
			for(int t = 0; t < wave.length; t++)
			{
				double realTemp = Math.cos(-2 * Math.PI * k * t /wave.length);
				double imagTemp = Math.sin(- 2 * Math.PI * k *t/wave.length);
				
				temp.setReal((realTemp*wave[t].getReal()));
				temp.setImaginary((imagTemp*wave[t].getReal()));
				
				sum = sum.add(temp);	
			}
			
			mag[k] = sum.magnitude();
			
		}
		
		double average = 0;
		for(double D: mag)
			average += D;
		
		average = average/mag.length;
		
		List<Float> found = process(mag, testSampleRate, wave.length, 1);
		List<Float> foundFrequencies = new ArrayList<Float>();
		long timeEnd = System.currentTimeMillis();
		for (float freq : found) {
//OG-CODE	      System.out.println("Found: " + freq);
	      foundFrequencies.add(freq);
	    }

//OG-CODE		System.out.println("It took " + (timeEnd - timeStart) + " milliseconds.");

	    return (foundFrequencies);
	}
	
	

	  static List<Float> process(double results[], float sampleRate, int numSamples, int sigma) {
	    double average = 0;
	    for (int i = 0; i < results.length; i++) {
	      average += results[i];
	    }
	    average = average/results.length;

	    double sums = 0;
	    for (int i = 0; i < results.length; i++) {
	      sums += (results[i]-average)*(results[i]-average);
	    }

	    double stdev = Math.sqrt(sums/(results.length-1));

	    ArrayList<Float> found = new ArrayList<Float>();
	    double max = Integer.MIN_VALUE;
	    int maxF = -1;
	    for (int f = 0; f < results.length/2; f++) {
	      if (results[f] > average+sigma*stdev) {
	        if (results[f] > max) {
	          max = results[f];
	          maxF = f;
	        }
	      } else {
	        if (maxF != -1) {
	          found.add(maxF*sampleRate/numSamples);
	          max = Integer.MIN_VALUE;
	          maxF = -1;
	        }
	      }
	    }

	    return (found);
	  }
	  
	  
	  
	  /**
	     * Saves the double array as an audio file (using .wav or .au format).
	     *
	     * @param  filename the name of the audio file
	     * @param  samples the array of samples
	     */
	  
	  
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

	    
}
