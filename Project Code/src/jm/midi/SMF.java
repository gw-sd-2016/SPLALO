package jm.midi;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.util.Enumeration;
import java.util.Vector;
import jm.JMC;
import jm.midi.event.EndTrack;
import jm.midi.event.Event;
import jm.midi.event.VoiceEvt;

public final class SMF
  implements JMC
{
  private short fileType;
  private short numOfTracks;
  private int numOfBytes;
  private short ppqn;
  private Vector trackList;
  private boolean VERBOSE = false;
  
  public SMF()
  {
    this((short)1, (short)480);
  }
  
  public SMF(short paramShort1, short paramShort2)
  {
    this.fileType = paramShort1;
    this.ppqn = paramShort2;
    this.numOfBytes = 0;
    this.numOfTracks = 0;
    this.trackList = new Vector();
  }
  
  public void setVerbose(boolean paramBoolean)
  {
    this.VERBOSE = paramBoolean;
  }
  
  public Vector getTrackList()
  {
    return this.trackList;
  }
  
  public short getPPQN()
  {
    return this.ppqn;
  }
  
  public void clearTracks()
  {
    if (!this.trackList.isEmpty()) {
      this.trackList.removeAllElements();
    }
  }
  
  public void read(InputStream paramInputStream)
    throws IOException
  {
    byte[] arrayOfByte = new byte[paramInputStream.available()];
    paramInputStream.read(arrayOfByte);
    ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(arrayOfByte);
    DataInputStream localDataInputStream = new DataInputStream(localByteArrayInputStream);
    if (!this.trackList.isEmpty()) {
      this.trackList.removeAllElements();
    }
    if (localDataInputStream.readInt() != 1297377380) {
      throw new IOException("This is NOT a MIDI file !!!");
    }
    localDataInputStream.readInt();
    try
    {
      this.fileType = localDataInputStream.readShort();
      if (this.VERBOSE) {
        System.out.println("MIDI file type = " + this.fileType);
      }
      this.numOfTracks = localDataInputStream.readShort();
      if (this.VERBOSE) {
        System.out.println("Number of tracks = " + this.numOfTracks);
      }
      this.ppqn = localDataInputStream.readShort();
      if (this.VERBOSE) {
        System.out.println("ppqn = " + this.ppqn);
      }
    }
    catch (IOException localIOException)
    {
      System.out.println(localIOException);
      localIOException.printStackTrace();
    }
    for (int i = 0; i < this.numOfTracks; i++) {
      readTrackChunk(localDataInputStream);
    }
    paramInputStream.close();
    localDataInputStream.close();
  }
  
  public void write(OutputStream paramOutputStream)
    throws IOException
  {
    DataOutputStream localDataOutputStream = new DataOutputStream(paramOutputStream);
    this.numOfTracks = ((short)this.trackList.size());
    try
    {
      localDataOutputStream.writeInt(1297377380);
      localDataOutputStream.writeInt(6);
      localDataOutputStream.writeShort(1);
      localDataOutputStream.writeShort(this.numOfTracks);
      localDataOutputStream.writeShort(this.ppqn);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    Enumeration localEnumeration = this.trackList.elements();
    while (localEnumeration.hasMoreElements())
    {
      Track localTrack = (Track)localEnumeration.nextElement();
      writeTrackChunk(localDataOutputStream, localTrack);
    }
    paramOutputStream.flush();
    paramOutputStream.close();
    localDataOutputStream.flush();
    localDataOutputStream.close();
  }
  
  public void print()
  {
    Enumeration localEnumeration = this.trackList.elements();
    while (localEnumeration.hasMoreElements())
    {
      Track localTrack = (Track)localEnumeration.nextElement();
      localTrack.print();
    }
  }
  
  private void skipATrack(RandomAccessFile paramRandomAccessFile)
    throws IOException
  {
    if (this.VERBOSE) {
      System.out.println("Skipping the tempo track . . .");
    }
    paramRandomAccessFile.readInt();
    paramRandomAccessFile.skipBytes(paramRandomAccessFile.readInt());
  }
  
  private void readTrackChunk(DataInputStream paramDataInputStream)
    throws IOException
  {
    Track localTrack = new Track();
    this.trackList.addElement(localTrack);
    int i = 0;
    if (this.VERBOSE) {
      System.out.println("Reading Track ..........");
    }
    if (paramDataInputStream.readInt() != 1297379947) {
      throw new IOException("Track started in wrong place!!!!  ABORTING");
    }
    paramDataInputStream.readInt();
    int k = 0;
    int m = 0;
    Object localObject = null;
    for (;;)
    {
      try
      {
        i = MidiUtil.readVarLength(paramDataInputStream);
        paramDataInputStream.mark(2);
        int j = paramDataInputStream.readUnsignedByte();
        if (j < 128)
        {
          j = k;
          paramDataInputStream.reset();
        }
        int n;
        if (j >= 255)
        {
          n = paramDataInputStream.readUnsignedByte();
          m = MidiUtil.readVarLength(paramDataInputStream);
          localObject = MidiUtil.createMetaEvent(n);
        }
        else if (j >= 240)
        {
          System.out.println("SysEX---");
          m = MidiUtil.readVarLength(paramDataInputStream);
        }
        else if (j >= 128)
        {
          n = (short)(j / 16);
          short s = (short)(j - n * 16);
          VoiceEvt localVoiceEvt = (VoiceEvt)MidiUtil.createVoiceEvent(n);
          localVoiceEvt.setMidiChannel(s);
          localObject = localVoiceEvt;
          if (localObject == null) {
            throw new IOException("MIDI file read error: invalid voice event type!");
          }
        }
        k = j;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        System.exit(1);
      }
      if (localObject != null)
      {
        ((Event)localObject).setTime(i);
        ((Event)localObject).read(paramDataInputStream);
        localTrack.addEvent((Event)localObject);
        if ((localObject instanceof EndTrack)) {
          break;
        }
      }
      else
      {
        paramDataInputStream.skipBytes(m);
      }
    }
  }
  
  private void writeTrackChunk(DataOutputStream paramDataOutputStream, Track paramTrack)
    throws IOException
  {
    if (this.VERBOSE) {
      System.out.println("Writing MIDI Track");
    }
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
    int i = 1297379947;
    Enumeration localEnumeration = paramTrack.getEvtList().elements();
    localEnumeration = paramTrack.getEvtList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Event localEvent = (Event)localEnumeration.nextElement();
      localEvent.write(localDataOutputStream);
    }
    paramDataOutputStream.writeInt(i);
    paramDataOutputStream.writeInt(localByteArrayOutputStream.size());
    paramDataOutputStream.write(localByteArrayOutputStream.toByteArray(), 0, localByteArrayOutputStream.size());
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\midi\SMF.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */