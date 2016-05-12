package jm.gui.cpn;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiDevice.Info;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Transmitter;

public class JmMidiPlayer
  extends OutputStream
{
  private Sequencer sequencer = MidiSystem.getSequencer();
  private Transmitter transmitter = this.sequencer.getTransmitter();
  private Receiver receiver = this.synthesizer.getReceiver();
  private MidiDevice synthesizer = getSynthesizer();
  ByteArrayOutputStream os;
  
  private static MidiDevice getSynthesizer()
    throws MidiUnavailableException
  {
    MidiDevice.Info[] arrayOfInfo = MidiSystem.getMidiDeviceInfo();
    MidiDevice localMidiDevice = null;
    int i = 0;
    for (int j = 0; j < arrayOfInfo.length; j++) {
      if (i == 0) {
        try
        {
          MidiDevice.Info localInfo = arrayOfInfo[(j - 1)];
          System.out.print(localInfo.toString());
          System.out.print(" Getting Info ");
          localMidiDevice = MidiSystem.getMidiDevice(localInfo);
          if (!(localMidiDevice instanceof Sequencer))
          {
            System.out.print(" Opening ");
            i = 1;
            System.out.println(" Opened");
          }
          else
          {
            System.out.println(" Not a Sequencer");
          }
        }
        catch (Throwable localThrowable)
        {
          System.out.println(" Exception " + localThrowable.getMessage());
        }
      }
    }
    if (i != 0) {
      return localMidiDevice;
    }
    System.out.println("No Synthesizer Device Found");
    throw new MidiUnavailableException("No Synthesizer Device Found");
  }
  
  public JmMidiPlayer()
    throws MidiUnavailableException
  {
    this.transmitter.setReceiver(this.receiver);
    this.sequencer.open();
    if (this.sequencer.isOpen()) {}
    this.os = new ByteArrayOutputStream();
    MidiDevice.Info localInfo1 = this.synthesizer.getDeviceInfo();
    MidiDevice.Info localInfo2 = this.sequencer.getDeviceInfo();
    MidiDevice.Info[] arrayOfInfo = MidiSystem.getMidiDeviceInfo();
    for (int i = 0; i < arrayOfInfo.length; i++)
    {
      MidiDevice.Info localInfo3 = arrayOfInfo[i];
      System.out.print(localInfo3.toString());
      try
      {
        MidiDevice localMidiDevice = MidiSystem.getMidiDevice(localInfo3);
      }
      catch (MidiUnavailableException localMidiUnavailableException)
      {
        System.out.println(" Unavailable");
      }
    }
    if (this.sequencer.isOpen()) {}
  }
  
  public void write(int paramInt)
    throws IOException
  {
    byte[] arrayOfByte = new byte[1];
    arrayOfByte[0] = new Integer(paramInt).byteValue();
    write(arrayOfByte);
  }
  
  public void write(byte[] paramArrayOfByte)
    throws IOException
  {
    this.os.write(paramArrayOfByte);
  }
  
  public void play()
  {
    try
    {
      ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(this.os.toByteArray());
      Sequence localSequence = MidiSystem.getSequence(localByteArrayInputStream);
      if (!this.sequencer.isOpen()) {
        this.sequencer.open();
      }
      this.sequencer.setSequence(localSequence);
      this.sequencer.start();
      while (this.sequencer.isRunning()) {
        try
        {
          Thread.sleep(100L);
        }
        catch (InterruptedException localInterruptedException) {}
      }
      this.sequencer.stop();
    }
    catch (InvalidMidiDataException localInvalidMidiDataException)
    {
      System.out.println("Bad Midi Data " + localInvalidMidiDataException.getMessage());
    }
    catch (MidiUnavailableException localMidiUnavailableException)
    {
      System.out.println("Unable to Re-Open Sequencer " + localMidiUnavailableException.getMessage());
    }
    catch (IOException localIOException)
    {
      System.out.println("IO Exception in Midi " + localIOException.getMessage());
    }
  }
  
  public void close()
  {
    this.sequencer.close();
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\gui\cpn\JmMidiPlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */