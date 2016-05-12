package jm.midi;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.List;
import java.awt.ScrollPane;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.PrintStream;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiDevice.Info;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;
import javax.sound.midi.Transmitter;

public abstract class MidiCommunication
  implements Receiver
{
  private Receiver midiReceiver;
  private boolean waitingToSetup = true;
  
  public MidiCommunication()
  {
    setupMidiInput();
    while (this.waitingToSetup) {
      try
      {
        Thread.sleep(100L);
      }
      catch (Exception localException) {}
    }
  }
  
  public MidiCommunication(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      setupMidiInput();
      while (this.waitingToSetup) {
        try
        {
          Thread.sleep(100L);
        }
        catch (Exception localException) {}
      }
    }
  }
  
  public MidiCommunication(int paramInt1, int paramInt2)
  {
    MidiDevice.Info[] arrayOfInfo = MidiSystem.getMidiDeviceInfo();
    setMidiInputSelection(paramInt1, arrayOfInfo);
    setMidiOutputSelection(paramInt2, arrayOfInfo);
  }
  
  public void midiSetup()
  {
    setupMidiInput();
  }
  
  public abstract void handleMidiInput(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  public void sendMidiOutput(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    try
    {
      ShortMessage localShortMessage = new ShortMessage();
      localShortMessage.setMessage(paramInt1, paramInt2, paramInt3, paramInt4);
      this.midiReceiver.send(localShortMessage, -1L);
    }
    catch (InvalidMidiDataException localInvalidMidiDataException) {}
  }
  
  public void send(MidiMessage paramMidiMessage, long paramLong)
  {
    byte[] arrayOfByte = paramMidiMessage.getMessage();
    int i = paramMidiMessage.getStatus();
    int j;
    if ((paramMidiMessage instanceof ShortMessage))
    {
      j = (arrayOfByte[0] & 0xFF) >> 4;
      int k = arrayOfByte[0] & 0xF;
      int m = arrayOfByte[1];
      int n = -1;
      if (arrayOfByte.length > 2) {
        n = arrayOfByte[2];
      }
      if (j != 15) {
        handleMidiInput(i - k, k, m, n);
      } else if (i == 248) {
        System.out.print("MIDI Clock message");
      } else if (i == 254) {
        System.out.print("MIDI Active sensing message");
      } else {
        System.out.print("A non-identified MIDI system message " + i);
      }
    }
    else if ((paramMidiMessage instanceof SysexMessage))
    {
      System.out.println();
      System.out.print("Sysex MIDI message <<");
      for (j = 0; j < arrayOfByte.length; j++) {
        System.out.print(" " + arrayOfByte[j]);
      }
      System.out.println(">>");
    }
    else if ((paramMidiMessage instanceof MetaMessage))
    {
      System.out.println();
      System.out.print("Meta MIDI Message {");
      for (j = 0; j < arrayOfByte.length; j++) {
        System.out.print(" " + arrayOfByte[j]);
      }
      System.out.println("}");
    }
    else
    {
      System.out.println("Unknown MIDI message [");
      for (j = 0; j < arrayOfByte.length; j++) {
        System.out.print(" " + arrayOfByte[j]);
      }
      System.out.println("]");
    }
  }
  
  public void close() {}
  
  private void setupMidiInput()
  {
    try
    {
      final Frame localFrame = new Frame("MIDI Input port: Double-click to select.");
      final MidiDevice.Info[] arrayOfInfo = MidiSystem.getMidiDeviceInfo();
      final List localList = new List();
      fillFrame(localFrame, localList, arrayOfInfo);
      localFrame.setVisible(true);
      MouseAdapter local1 = new MouseAdapter()
      {
        public void mouseClicked(MouseEvent paramAnonymousMouseEvent)
        {
          if (paramAnonymousMouseEvent.getClickCount() == 2)
          {
            int i = localList.getSelectedIndex();
            MidiCommunication.this.setMidiInputSelection(i, arrayOfInfo);
            localFrame.setVisible(false);
            MidiCommunication.this.setupMidiOutput();
          }
        }
      };
      localList.addMouseListener(local1);
    }
    catch (Exception localException)
    {
      System.out.println(localException);
      System.exit(0);
    }
  }
  
  private void setupMidiOutput()
  {
    try
    {
      final MidiDevice.Info[] arrayOfInfo = MidiSystem.getMidiDeviceInfo();
      final Frame localFrame = new Frame("MIDI Output port: Double-click to select.");
      final List localList = new List();
      fillFrame(localFrame, localList, arrayOfInfo);
      MouseAdapter local2 = new MouseAdapter()
      {
        public void mouseClicked(MouseEvent paramAnonymousMouseEvent)
        {
          if (paramAnonymousMouseEvent.getClickCount() == 2)
          {
            int i = localList.getSelectedIndex();
            MidiCommunication.this.setMidiOutputSelection(i, arrayOfInfo);
            localFrame.setVisible(false);
            MidiCommunication.this.waitingToSetup = false;
          }
        }
      };
      localList.addMouseListener(local2);
      localFrame.setVisible(true);
    }
    catch (Exception localException)
    {
      System.out.println(localException);
      System.exit(0);
    }
  }
  
  private void fillFrame(Frame paramFrame, List paramList, MidiDevice.Info[] paramArrayOfInfo)
  {
    try
    {
      paramFrame.setSize(340, 200);
      paramFrame.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 170, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 100);
      String[] arrayOfString = new String[paramArrayOfInfo.length];
      arrayOfString[0] = ("" + paramArrayOfInfo[0]);
      arrayOfString[1] = ("" + paramArrayOfInfo[1]);
      for (int i = 2; i < paramArrayOfInfo.length; i++) {
        arrayOfString[i] = MidiSystem.getMidiDevice(paramArrayOfInfo[i]).toString();
      }
      for (i = 0; i < paramArrayOfInfo.length; i++) {
        paramList.add(arrayOfString[i]);
      }
      ScrollPane localScrollPane = new ScrollPane();
      localScrollPane.add(paramList);
      paramFrame.add(localScrollPane);
    }
    catch (Exception localException)
    {
      System.out.println(localException);
      System.exit(0);
    }
  }
  
  private void setMidiInputSelection(int paramInt, MidiDevice.Info[] paramArrayOfInfo)
  {
    try
    {
      MidiDevice localMidiDevice = MidiSystem.getMidiDevice(paramArrayOfInfo[paramInt]);
      localMidiDevice.open();
      Transmitter localTransmitter = localMidiDevice.getTransmitter();
      localTransmitter.setReceiver(this);
    }
    catch (Exception localException)
    {
      System.out.println("Exception in PlumStone main ()");
      System.out.println(localException);
      System.exit(0);
    }
  }
  
  private void setMidiOutputSelection(int paramInt, MidiDevice.Info[] paramArrayOfInfo)
  {
    try
    {
      MidiDevice localMidiDevice = MidiSystem.getMidiDevice(paramArrayOfInfo[paramInt]);
      localMidiDevice.open();
      this.midiReceiver = localMidiDevice.getReceiver();
    }
    catch (Exception localException)
    {
      System.out.println("Exception in PlumStone main ()");
      System.out.println(localException);
      System.exit(0);
    }
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\midi\MidiCommunication.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */