package pureToneTests;

//COMPLETE LIVE MIC RECORDING TO .WAV FILE AND PRINTING OF .WAV FILE DISCRETE VALUES AS BYTE OR DOUBLE


import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.sound.sampled.AudioFileFormat.Type;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.Mixer.Info;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;


public class RecordingAttempt {

	public static Mixer mixer;
	final static int sampleRate = 44100;

	public static void main(String[] args)
	{
		System.out.println("Mic Check ...");
		Mixer.Info infos[] = AudioSystem.getMixerInfo();
				for(int i = 0; i < infos.length; i++)
					System.out.println(infos[i].getName() + " --- " + infos[i].getDescription());

		System.out.println();

		try
		{	
			AudioFormat format = new AudioFormat(sampleRate, 16, 1, true, true);
			//AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
			DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

			if(!AudioSystem.isLineSupported(info))
				System.err.println("Line not supported");

			final TargetDataLine targetLine = (TargetDataLine) AudioSystem.getLine(info);
			targetLine.open();

			System.out.println("Recoding ... ");

			targetLine.start();

			Thread thread = new Thread()
			{
				byte[] byteBuffer;
				@Override public void run()
				{
					AudioInputStream audioStream = new AudioInputStream(targetLine);
					File audioFile = new File("testRecord.wav");					

					try  
					{
						int size = AudioSystem.write(audioStream, Type.WAVE, audioFile);
						System.out.println("Size = " + size);
						InputStream fileStream = new FileInputStream(audioFile);
						byteBuffer = new byte[(int)audioFile.length()];
						fileStream.read(byteBuffer, 0, byteBuffer.length);
						
						fileStream.close();

					} 
					
					catch (IOException e) {e.printStackTrace();}

					
					System.out.println("STOP!");
					
					//int [] intBuffer = new int [byteBuffer.length];
					//Integer [] integerBuffer = new Integer [byteBuffer.length];
					//byte [] binaryArray = new byte [8];
					for(int index = 0; index < byteBuffer.length; index += 8)
						{
		//					intBuffer[index] = (int) byteBuffer[index] & 0xFF;
	//						System.out.println("Integer = " + intBuffer[index] + " Byte = " + byteBuffer[index]);
//							System.out.println("Binary = " + Integer.toBinaryString(intBuffer[index]) + "\n");
						
						
						for(int i = 0; i < byteBuffer.length; i+=2)
							{
								//binaryArray[i] = byteBuffer[index+i];
								//System.out.println(byteBuffer[i] + ", " + byteBuffer[i+1] + ", " + (short)(((byteBuffer[i] & 0xff) << 8) + (byteBuffer[i+1] & 0xff))/(short)32768);
								
								//binaryArray[(index*8) + i] = Integer.toBinaryString(intBuffer[index]).charAt(i);
							}
							
							//System.out.println("Float value: " + ByteBuffer.wrap(binaryArray).getDouble	());
							//.getDouble());
						}
					//System.out.println(binaryArray.toString());
					System.out.printf("The byte array is %d values long", byteBuffer.length);
					System.out.printf("The double array will be %d values long", byteBuffer.length/8);

					double[] doubleBuffer = new double[byteBuffer.length/8];
					int[] miniArray = new int [8];
					
					for(int i = 0; i < doubleBuffer.length; i++)
					{
						//for(int j = 0; j < 8; j++)
						//{miniArray}
						//doubleBuffer[i] = ByteArray
									//(double) (byteBuffer[i+1] << 8 | byteBuffer[i] & 0xFF)/32767;
							//System.out.println(doubleBuffer[i]);
					}	

				}
			};

	
			thread.start();
			Thread.sleep(4500);
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
