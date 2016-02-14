package fullTests;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.io.TarsosDSPAudioFormat;

public class RecordAudio {

	private final int sampleRate;
	private String audioFileName;
	private static TargetDataLine targetLine;
	
	public RecordAudio(int sampleRate, String fileName) throws LineUnavailableException
	{
		this.sampleRate = sampleRate;
		audioFileName = fileName;
		
		AudioFormat format = new AudioFormat(sampleRate, 16, 1, true, true);
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
		if(!AudioSystem.isLineSupported(info))
			System.err.println("Line not supported");
		
		targetLine = (TargetDataLine) AudioSystem.getLine(info);
		
	}
	
	//boolean record is true when setting to record and false when stopping
	public void record(boolean record) 
	{
		if(record)
		{
			try
			{	
				targetLine.open();
				targetLine.start();
				System.out.println("Recoding ... ");
				
				Thread thread = new Thread()
				{
					@Override public void run() 
					{
						AudioInputStream audioStream = new AudioInputStream(targetLine);
						File audioFile2 = new File("just-major-sc.wav");					
						
						//OutputStream output = new ByteArrayOutputStream();
						
						try  
						{
							AudioInputStream input = AudioSystem.getAudioInputStream(audioFile2);
							//while(targetLine.isOpen())
							{
								
								byte byteArray[] = new byte[input.available()];			//Processing performed for each second
								//audioStream.read(byteArray);
								input.read(byteArray);
								
								ProcessAudio pa = new ProcessAudio(audioFileName, sampleRate);
								double doubleArray[] = pa.ByteArrayToDoubleArray(byteArray);
								
								/*
								 *  ATTEMPT TO USE TARSOS BANDPASS FILTER
								AudioEventae = new AudioEvent();
								pa.BandPassFilter(doubleArray, ae);
								*/
								
								
								double max = 0, soundBegin = 0;
								double doubleBuffer[] = new double[sampleRate/10];
								
								long beginProcess = System.currentTimeMillis();
								
								for(int sample = 0; sample < doubleArray.length; sample += sampleRate/10)
								{
									double tempMax = 0;
									for(int s = sample; s < sample + (sampleRate/20); s++)
									{
										doubleBuffer[s-sample] = doubleArray[s];
										tempMax = (Math.abs(doubleArray[s]) > tempMax)? Math.abs(doubleArray[s]) : tempMax;
									}
									
									max = (max < tempMax)? tempMax : (max * 0.8);
									double decibelVal = pa.DoubleArraytoDecibelArray(doubleBuffer, max);
									//System.out.println(decibelVal + "dB at " + (double) sample/sampleRate/2 +" s\n");
									if(soundBegin != 0 && decibelVal < -10.0)
									{	
										//No particular reason why dividing by 2. Just fits audio files better
										System.out.println("Sound heard from " + soundBegin + " to " + (double) sample/(2*sampleRate));
										
										
										/*FREQUENCY ANALYSIS (IT'S ABOUT TO GO DOWN)*/
										//Copy segment of array into a new double buffer and run FFT on it
										
										double freqBuffer[] = new double [sample - ((int) (soundBegin*(2*sampleRate))) ];
										
										for(int pos = (int) (soundBegin*(2*sampleRate)); pos < sample; pos++)
											freqBuffer[(int) (pos - (soundBegin*(2*sampleRate)))] = doubleArray[pos];
											
									
										//Make ComplexNum array equivalent
										ComplexNum compArray [] = pa.DoubleArraytoComplexArray(freqBuffer);

										ProcessFrequency pf = new ProcessFrequency(freqBuffer, sampleRate);
										
										System.out.println("Processing ... ");
										List<Float> f = pf.findFrequency(compArray);
											System.out.println(f.toString());
										
										for(Float x: f)
											System.out.print(pf.compareFreq(x) + ", ");
										System.out.println("\n");
										
										
										
										soundBegin = 0;
									}
									
									else if(soundBegin == 0 && decibelVal > -10.0)
										soundBegin = (double) sample/sampleRate/2;
								
									
								}
								
								System.out.println("It took " + (System.currentTimeMillis() - beginProcess)/1000 + "s to process. Do better!");
							}
							
						} 
						
						catch (IOException | UnsupportedAudioFileException e) {e.printStackTrace();} 
						
						
						System.out.println("STOP!");
						
	
					}
				};
	
				thread.start();
				//Thread.sleep(recordingTime == 0? 0: (recordingTime * 1000) + 500);
			
				System.out.println("Should work");
				
			}
	
			catch(LineUnavailableException lue)
				{System.err.println("Perceived error with recording through TargetLine (still running in the background");}

		}
		
		else
		{
			targetLine.stop();
			targetLine.close();
		}
		
		
	}

}
