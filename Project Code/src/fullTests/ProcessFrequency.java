package fullTests;

import java.util.ArrayList;
import java.util.List;

import be.tarsos.dsp.util.fft.FFT;

public class ProcessFrequency {

	private static double[] setTones = {16.35, 17.32, 18.35, 19.45, 20.60, 21.83, 23.12, 24.50, 25.96, 27.50, 29.14, 30.87, 32.70, 34.65, 36.71, 38.89, 41.20, 43.65, 46.25, 49, 51.91, 55, 58.27, 61.74, 65.41, 69.30, 73.42, 77.78, 82.41, 87.31, 92.50, 98, 103.83, 110, 116.54, 123.47, 130.81, 138.59, 146.83, 155.56, 164.81, 174.61, 185, 196, 207.65, 220, 233.08, 246.94, 261.63, 277.18, 293.66, 311.13, 329.63, 349.23, 369.99, 392, 415.30, 440, 466.16, 493.88, 523.25, 554.37, 587.33, 622.25, 659.25, 698.46, 739.99, 783.99, 830.61, 880, 932.33, 987.77, 1046.50, 1108.73, 1174.66, 1244.51, 1318.51, 1396.91, 1479.98, 1567.98, 1661.22, 1760, 1864.66, 1975.53, 2093, 2217.46, 2349.32, 2489.02, 2637.02, 2793.83, 2959.96, 3135.96, 3322.44, 3520, 3729.31, 3951.07, 4186.01, 4434.92, 4698.63, 4978.03, 5274.04, 5587.65, 5919.91, 6271.93, 6644.88, 7040, 7458.62, 7902.13};
	private static double[] constantTones = {16.352, 17.324, 18.354, 19.455, 20.601, 21.827, 23.124, 24.499, 25.956, 27.50, 29.135, 30.868};
	private double soundArray[];
	private	static int testSampleRate;
	
	public ProcessFrequency(double sound[], int sampleFreq)
	{
		soundArray = sound;
		testSampleRate = sampleFreq;
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
						String note = findNoteValue(j);
						String fullNote = note + i;
						return fullNote;
					}

				}

			}

		}

		return "Invalid";
	}

	public static List<Float> findFrequency(ComplexNum[] wave)
	{
		//The following method is an attempt to use DFT to find the frequencies of a wave
		//It accepts an array of complex numbers with discrete values from the sound
		
		ComplexNum temp = new ComplexNum();
		ComplexNum sum = new ComplexNum(0,0);
		
		double mag[] = new double [wave.length];
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
		
		for (float freq : found) 
			foundFrequencies.add(freq);

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
	    for (int f = 0; f < freqDomain.length/2; f++) 
	    {
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

}
