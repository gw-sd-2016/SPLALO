package jm.audio;

import java.io.PrintStream;
import jm.JMC;
import jm.music.data.Note;

public abstract class AudioObject
  implements JMC
{
  protected AudioObject[] previous;
  protected AudioObject[] next;
  protected String name;
  protected int sampleRate;
  protected int channels;
  protected int inputs = 0;
  protected Note currentNote = null;
  protected double currentNoteStartTime;
  protected int numOfSamples = 0;
  protected Instrument inst = null;
  protected boolean finished = true;
  private int returned;
  
  protected AudioObject(AudioObject paramAudioObject, String paramString)
  {
    AudioObject[] arrayOfAudioObject = { paramAudioObject };
    this.name = paramString;
    this.previous = arrayOfAudioObject;
    this.previous[0].setNext(this);
    this.inputs = 1;
  }
  
  protected AudioObject(AudioObject[] paramArrayOfAudioObject, String paramString)
  {
    this.name = paramString;
    this.previous = paramArrayOfAudioObject;
    for (int i = 0; i < paramArrayOfAudioObject.length; i++) {
      paramArrayOfAudioObject[i].setNext(this);
    }
    this.inputs = paramArrayOfAudioObject.length;
  }
  
  protected AudioObject(Instrument paramInstrument, int paramInt, String paramString)
  {
    this.inst = paramInstrument;
    this.name = paramString;
    this.sampleRate = paramInt;
    this.inst.addPrimaryAO(this);
  }
  
  public abstract int work(float[] paramArrayOfFloat)
    throws AOException;
  
  private void setNext(AudioObject paramAudioObject)
  {
    if (this.next == null)
    {
      this.next = new AudioObject[1];
      this.next[0] = paramAudioObject;
    }
    else
    {
      AudioObject[] arrayOfAudioObject = new AudioObject[this.next.length + 1];
      for (int i = 0; i < this.next.length; i++) {
        arrayOfAudioObject[i] = this.next[i];
      }
      arrayOfAudioObject[this.next.length] = paramAudioObject;
      this.next = arrayOfAudioObject;
    }
  }
  
  public int nextWork(float[] paramArrayOfFloat)
    throws AOException
  {
    this.returned = 0;
    this.returned = work(paramArrayOfFloat);
    this.inst.setFinished(this.finished);
    return this.returned;
  }
  
  protected void buildNext(Note paramNote, double paramDouble, int paramInt)
  {
    if (this.next != null) {
      for (int i = 0; i < this.next.length; i++)
      {
        this.next[i].numOfSamples = paramInt;
        this.next[i].inst = this.inst;
        this.next[i].channels = this.channels;
        this.next[i].sampleRate = this.sampleRate;
        this.next[i].newNote(paramNote, paramDouble, paramInt);
      }
    } else {
      try
      {
        this.inst.setFinalAO(this);
      }
      catch (AOException localAOException)
      {
        System.out.println(localAOException);
        System.exit(1);
      }
    }
  }
  
  protected void build() {}
  
  public void newNote(Note paramNote, double paramDouble, int paramInt)
  {
    this.currentNote = paramNote;
    this.currentNoteStartTime = paramDouble;
    this.numOfSamples = paramInt;
    build();
    buildNext(this.currentNote, this.currentNoteStartTime, this.numOfSamples);
  }
  
  public int getSampleRate()
  {
    return this.sampleRate;
  }
  
  public int getChannels()
  {
    return this.channels;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\audio\AudioObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */