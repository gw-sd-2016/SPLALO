package fullTests;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
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
import pureToneTests.Note;

public class RecordAudio {

	private final int sampleRate;
	private String audioFileName;
	private static TargetDataLine targetLine;
	public static double normalizedRestThreshold = -12.0;		//For averaged frequencies below this threshold, a segment is considered at rest and will be notated as such
	
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
								
								//Variables to emulate windows and timing values & peak amplitudes within them.
								double max = 0, soundBegin = 0;
								int windowSize = sampleRate/16;
								double doubleBuffer[] = new double[windowSize];
								
								//Tracking program efficiency
								long beginProcess = System.currentTimeMillis();
								
								
								//ArrayList of notes played
								List<ArrayList<Float>> freqArray = new ArrayList<ArrayList<Float>>(doubleArray.length/windowSize); 
								ArrayList<Note> noteArray = new ArrayList<Note>(doubleArray.length/windowSize);
								
								
								for(int sample = 0; sample < doubleArray.length; sample += windowSize)
								{
									double tempMax = 0;
									int limit = doubleArray.length < sample + (windowSize/2)? doubleArray.length : sample + (windowSize/2); 
									for(int s = sample; s < limit; s++)
									{
										doubleBuffer[s-sample] = doubleArray[s];
										tempMax = (Math.abs(doubleArray[s]) > tempMax)? Math.abs(doubleArray[s]) : tempMax;
									}
									
									max = (max < tempMax)? tempMax : (max * 0.8);
									double decibelVal = pa.DoubleArraytoDecibelArray(doubleBuffer, max);
									//System.out.println(decibelVal + "dB at " + (double) sample/sampleRate/2 +" s\n");
									
									if(soundBegin != 0 && decibelVal < normalizedRestThreshold)
									{	
										//No particular reason why dividing by 2. Just fits audio files better
										System.out.println("Sound heard from " + soundBegin + " to " + (double) sample/(2*sampleRate));
										
										
										
										/*FREQUENCY ANALYSIS (IT'S ABOUT TO GO DOWN)*/
										//Copy segment of array into a new double buffer and run FFT on it
										int length = 2;
										while(length <= ( sample - ((int) (soundBegin*2*sampleRate)) )  )
											{length *= 2;}
										
										double freqBuffer[] = new double [length/2];
										
										for(int pos = (int) (soundBegin*(2*sampleRate)); pos < ((int) (soundBegin*(2*sampleRate)) + freqBuffer.length); pos++)
										{
											//System.out.println(pos + " < " + (pos+freqBuffer.length));
											freqBuffer[(int) (pos - (soundBegin*(2*sampleRate)))] = doubleArray[pos];
										}	
									
										
										//Make ComplexNum array equivalent
										ComplexNum compArray [] = pa.DoubleArraytoComplexArray(freqBuffer);

										ProcessFrequency pf = new ProcessFrequency(freqBuffer, sampleRate);
										
										System.out.println("Processing ... ");
										List<Float> f = pf.FFT(compArray);
											System.out.println(f.toString());
										
										for(Float x: f)
											System.out.print(pf.compareFreq(x) + ", ");
										System.out.println("\n");
										
										

										Note newNote = new Note();
										
										freqArray.add((ArrayList<Float>) f);
										for(Float freq: f)
											newNote.setNoteValue(pf.compareFreq(freq));
											
										newNote.setStartTime(soundBegin);
										newNote.setEndTime((double) sample/(2*sampleRate));
										newNote.setFrequency(f);
										newNote.num = pf.num;
										noteArray.add(newNote);
										
										
										
										
										soundBegin = 0;
									}
									
									else if(soundBegin == 0 && decibelVal > normalizedRestThreshold)
										soundBegin = (double) sample/sampleRate/2;
								
									
								}
								
								
								/*TRANSCRIBE TO SHEET MUSIC*/
								TranscribeSheetMusic tsm = new TranscribeSheetMusic();
								tsm.write(noteArray);
								
								System.out.println("It took " + (double)(System.currentTimeMillis() - beginProcess)/1000 + "s to process. Do better!");
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
