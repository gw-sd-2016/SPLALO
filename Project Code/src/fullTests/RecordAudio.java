package fullTests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class RecordAudio {

	private final int sampleRate;
	private int recordingTime;
	private String audioFileName;
	
	public RecordAudio(int sampleRate, int recordingTime, String fileName)
	{
		this.sampleRate = sampleRate;
		this.recordingTime = recordingTime;
		audioFileName = fileName;
	}
	
	public void record()
	{
		try
		{	
			AudioFormat format = new AudioFormat(sampleRate, 16, 1, true, true);
			DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

			if(!AudioSystem.isLineSupported(info))
				System.err.println("Line not supported");

			final TargetDataLine targetLine = (TargetDataLine) AudioSystem.getLine(info);
			targetLine.open();

			System.out.println("Recoding ... ");

			targetLine.start();

			Thread thread = new Thread()
			{
				@Override public void run()
				{
					AudioInputStream audioStream = new AudioInputStream(targetLine);
					File audioFile = new File(audioFileName);					
 
					try  
					{
						int size = AudioSystem.write(audioStream, javax.sound.sampled.AudioFileFormat.Type.WAVE, audioFile);
						System.out.println("Size = " + size);
						InputStream fileStream = new FileInputStream(audioFile);
						
						fileStream.close();

					} 
					
					catch (IOException e) {e.printStackTrace();}

					
					System.out.println("STOP!");
					

				}
			};

	
			thread.start();
			Thread.sleep(recordingTime == 0? 0: (recordingTime * 1000) + 500);
			targetLine.stop();
			targetLine.close();

			System.out.println("Should work");
			
		}


		catch(LineUnavailableException lue)
			{lue.printStackTrace();}

		catch(InterruptedException ie)
			{ie.printStackTrace();}
		
	}

}
