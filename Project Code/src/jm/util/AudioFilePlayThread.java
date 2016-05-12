package jm.util;

import java.io.PrintStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.SourceDataLine;

class AudioFilePlayThread
  extends Thread
{
  byte[] tempBuffer = new byte['Ѐ'];
  private AudioInputStream audioInputStream;
  
  public AudioFilePlayThread(AudioInputStream paramAudioInputStream)
  {
    this.audioInputStream = paramAudioInputStream;
  }
  
  public void run()
  {
    try
    {
      AudioFormat localAudioFormat = this.audioInputStream.getFormat();
      DataLine.Info localInfo = new DataLine.Info(SourceDataLine.class, localAudioFormat);
      SourceDataLine localSourceDataLine = (SourceDataLine)AudioSystem.getLine(localInfo);
      localSourceDataLine.open(localAudioFormat);
      localSourceDataLine.start();
      int i;
      while ((i = this.audioInputStream.read(this.tempBuffer, 0, this.tempBuffer.length)) != -1) {
        if (i > 0) {
          localSourceDataLine.write(this.tempBuffer, 0, i);
        }
      }
      localSourceDataLine.drain();
      localSourceDataLine.stop();
      localSourceDataLine.close();
      localSourceDataLine.close();
      this.audioInputStream.close();
    }
    catch (Exception localException)
    {
      System.out.println("jMusic AudioFilePlayThread error");
      localException.printStackTrace();
    }
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\util\AudioFilePlayThread.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */