package fullTests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.filters.BandPass;


public class ProcessAudio {

	private	static int testSampleRate;
	private String audioFileName;
	
	public static double normalizedRestThreshold = -12.0;		//For averaged frequencies below this threshold, a segment is considered at rest and will be notated as such
	public static double normalizedNoteThreshold;		//For average frequencies above this threshold, a segment is considered
	
	public ProcessAudio(String fileName, int sampleRate)
	{
		audioFileName = fileName;
		testSampleRate = sampleRate;
	}
	
	
	public double[] AudioFileToDoubleArray()
	{
		
		try 
		{
			File audioFile = new File(audioFileName);						
			AudioInputStream input = AudioSystem.getAudioInputStream(audioFile);
			byte byteValues[] = new byte[input.available()];
			input.read(byteValues);
			
			double doubleValues [] = new double[byteValues.length/2];
			
			for(int i = 0; i < byteValues.length; i+=2)
			{
				doubleValues[i/2] = (short) (((byteValues[i+1] & 0xff) << 8) | (byteValues[i] & 0xff));
				doubleValues[i/2] /= 32768;			
			}
			
			System.out.println("Format: " + input.getFormat());
			return doubleValues;
		} 
		
		catch (IOException | UnsupportedAudioFileException e) {e.printStackTrace();} 
		
		return null;
		
	}

	public double[] ByteArrayToDoubleArray(byte byteValues[])
	{
			double doubleValues [] = new double[byteValues.length/2];
			
			for(int i = 0; i < byteValues.length; i+=2)
			{
				doubleValues[i/2] = (short) (((byteValues[i+1] & 0xff) << 8) | (byteValues[i] & 0xff));
				doubleValues[i/2] /= 32768;			
			}
			
			return doubleValues; 
	}
	
	public ComplexNum[] DoubleArraytoComplexArray(double doubleValues[])
	{
		ComplexNum[] complexArray = new ComplexNum[doubleValues.length]; 
		for(int i = 0; i < complexArray.length; i++)
		{
			ComplexNum tempor = new ComplexNum(doubleValues[i], 0);
			complexArray[i] = tempor;
		}
		
		return complexArray;
	}
	
	public double DoubleArraytoDecibelArray(double [] doubleArray, double max)
	{
		double rootMeanSquared = 0;
		for(double x: doubleArray)
			rootMeanSquared += x*x;
		
		rootMeanSquared /= doubleArray.length;
		rootMeanSquared = Math.sqrt(rootMeanSquared);
		//System.out.println("Max = " + max);
		return (20 * Math.log10(rootMeanSquared/max));
	}
	
	
	public double[] BandPassFilter(double doubleValues[], AudioEvent ae)
	{
		BandPass filter = new BandPass(2106.75f, 4158.5f, testSampleRate);
		filter.process(ae);
		return null;
	}
	
	
	
	//Stores averages of amplitudes for a portion in some double array and returns it
	public double[] makeAverageAmpArray(double wave [])
	{
		double averageAmps[] = new double[wave.length/20];
		for(int i = 0; i < wave.length; i+= (testSampleRate/20))
			averageAmps[i/(testSampleRate/20)] = calcAverageAmp(wave, i, i + (testSampleRate/20));		//Store average amplitudes in array
		
		return averageAmps;
	}
	
	public double findMaxAmp(double wave[])
	{
		double max = Double.MIN_VALUE;
		
		for(double x: wave)
			{if(x > max) max = x;}
		
		return max;
	}
	
	//The following method receive a double array representing amplitudes in some portion of code and calculate for the average amplitude
	public double calcAverageAmp(double wave[], int start, int end)
	{
		int result = 0;
		for(int i = start; i < end; i++)			//Sum up amplitudes
			result += wave[i];
		
		result = result / (end-start);		//Divide by number of samples in portion
		
		return result;
		
		
	}
	
}
