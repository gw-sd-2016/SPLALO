package jm.audio;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.io.StreamTokenizer;
import java.util.Enumeration;
import java.util.Stack;
import java.util.Vector;
import jm.JMC;
import jm.audio.io.SampleOut;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;

public final class Audio
  implements JMC
{
  private static boolean JPF = false;
  private static int channels;
  private static int sampleRate;
  
  public Audio() {}
  
  public static void processScore(Score paramScore, Instrument[] paramArrayOfInstrument, String paramString)
  {
    Stack localStack = new Stack();
    localStack.push(paramArrayOfInstrument[0]);
    for (int i = 0; i < paramArrayOfInstrument.length; i++) {
      if ((paramArrayOfInstrument[i] != null) && (!paramArrayOfInstrument[i].getInitialised())) {
        try
        {
          if (!paramArrayOfInstrument[i].getInitialised())
          {
            paramArrayOfInstrument[i].createChain();
            paramArrayOfInstrument[i].setInitialised(true);
          }
        }
        catch (AOException localAOException)
        {
          localAOException.printStackTrace();
        }
      }
    }
    Enumeration localEnumeration1 = paramScore.getPartList().elements();
    double d1 = 60.0D / paramScore.getTempo();
    int j = 0;
    while (localEnumeration1.hasMoreElements())
    {
      Part localPart = (Part)localEnumeration1.nextElement();
      double d2 = d1;
      if (localPart.getTempo() > 0.0D) {
        d2 = 60.0D / localPart.getTempo();
      }
      if (localPart.getInstrument() != -1) {
        try
        {
          localStack.push(paramArrayOfInstrument[localPart.getInstrument()]);
        }
        catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException1)
        {
          System.out.println("jMusic Audio warning: Can't find the instrument number " + localPart.getInstrument() + " that you have specified for " + "the part named " + localPart.getTitle() + ".");
        }
      }
      System.out.println("Part " + j++ + " '" + localPart.getTitle() + "'. ");
      Enumeration localEnumeration2 = localPart.getPhraseList().elements();
      int k = 0;
      while (localEnumeration2.hasMoreElements())
      {
        Phrase localPhrase = (Phrase)localEnumeration2.nextElement();
        double d3 = d2;
        if (localPhrase.getTempo() > 0.0D)
        {
          System.out.println("A: " + d3);
          d3 = 60.0D / localPhrase.getTempo();
          System.out.println("B: " + d3);
        }
        if (localPhrase.getInstrument() != -1) {
          try
          {
            localStack.push(paramArrayOfInstrument[localPhrase.getInstrument()]);
          }
          catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException2)
          {
            System.out.println("jMusic Audio warning: Can't find the instrument number " + localPhrase.getInstrument() + " that you have specified for" + " the phrase named " + localPhrase.getTitle() + ".");
          }
        }
        double d4 = d2 * localPhrase.getStartTime();
        double d5 = 0.0D;
        Enumeration localEnumeration3 = localPhrase.getNoteList().elements();
        System.out.print("    Phrase " + k++ + " '" + localPhrase.getTitle() + "'" + " starting at beat " + localPhrase.getStartTime() + ": ");
        int m = 0;
        while (localEnumeration3.hasMoreElements())
        {
          Note localNote1 = (Note)localEnumeration3.nextElement();
          if (localNote1.getFrequency() == -2.147483648E9D)
          {
            d5 += d3 * localNote1.getRhythmValue();
          }
          else
          {
            m++;
            if (m % 10 == 0) {
              System.out.print(m);
            } else {
              System.out.print(".");
            }
            Note localNote2 = localNote1.copy();
            localNote2.setDuration(d3 * localNote1.getDuration());
            localNote2.setRhythmValue(d3 * localNote1.getRhythmValue());
            Instrument localInstrument = (Instrument)localStack.peek();
            localInstrument.setBlock(false);
            localInstrument.setFinished(true);
            localInstrument.renderNote(localNote2, d4 + d5);
            localInstrument.setFinished(false);
            localInstrument.iterateChain();
            d5 += d3 * localNote1.getRhythmValue();
          }
        }
        System.out.println();
        if (localPhrase.getInstrument() != -1) {
          localStack.pop();
        }
      }
    }
  }
  
  public static void combine(String paramString1, String paramString2, String paramString3, boolean paramBoolean1, boolean paramBoolean2)
  {
    if (paramBoolean2)
    {
      sampleRate = SampleOut.samprate;
      channels = SampleOut.numofchan;
      System.out.println("Bit Depth: 16 Sample rate: " + SampleOut.samprate + " Channels: " + SampleOut.numofchan);
      addEmUp(paramString2, paramString3, SampleOut.max);
      return;
    }
    int i = 1;
    float f1 = 0.0F;
    try
    {
      FileReader localFileReader = new FileReader(paramString1);
      StreamTokenizer localStreamTokenizer = new StreamTokenizer(localFileReader);
      RandomAccessFile localRandomAccessFile = new RandomAccessFile(paramString2, "rw");
      double d1 = System.currentTimeMillis();
      try
      {
        for (;;)
        {
          localStreamTokenizer.nextToken();
          String str = localStreamTokenizer.sval;
          if (str == null) {
            break;
          }
          localStreamTokenizer.nextToken();
          long l = (long) (localStreamTokenizer.nval * 4.0D);
          localStreamTokenizer.nextToken();
          int j = (int)localStreamTokenizer.nval;
          float f2 = getAudio(str, l, j, f1, localRandomAccessFile);
          if (f1 < f2)
          {
            f1 = f2;
            System.out.println("Max is smaller: " + f1);
          }
          if ((f2 < 0.0F) && (f1 < f2 * -1.0F))
          {
            f1 = f2 * -1.0F;
            System.out.println("MAX is bigger: " + f1);
          }
          if (i % 10 == 0) {
            System.out.print(i);
          } else {
            System.out.print(".");
          }
          i++;
          localRandomAccessFile.close();
        }
      }
      catch (EOFException localEOFException) {}catch (IOException localIOException2)
      {
        localIOException2.printStackTrace();
      }
      System.out.print("\n");
      double d2 = System.currentTimeMillis();
      System.out.println("Created tmp file in " + (d2 - d1) / 1000.0D + " seconds");
      double d3 = System.currentTimeMillis();
      addEmUp(paramString2, paramString3, f1);
      double d4 = System.currentTimeMillis();
      System.out.println("Mixed to a single file in " + (d4 - d3) / 1000.0D + " seconds");
      if (paramBoolean1)
      {
        File localFile1 = new File(paramString1);
        File localFile2 = new File(paramString2);
        localFile1.delete();
        localFile2.delete();
      }
    }
    catch (IOException localIOException1)
    {
      localIOException1.printStackTrace();
    }
  }
  
  private static float getAudio(String paramString, long paramLong, int paramInt, float paramFloat, RandomAccessFile paramRandomAccessFile)
  {
    FileInputStream localFileInputStream = null;
    BufferedInputStream localBufferedInputStream = null;
    DataInputStream localDataInputStream = null;
    try
    {
      localFileInputStream = new FileInputStream(paramString);
      localBufferedInputStream = new BufferedInputStream(localFileInputStream, 4096);
      localDataInputStream = new DataInputStream(localBufferedInputStream);
      if (localDataInputStream.readInt() != 779316836)
      {
        System.out.println("jMusic SampleIn warning: This file is NOT in the .au/.snd file format");
        return paramFloat;
      }
      int i = localDataInputStream.readInt();
      int j = localDataInputStream.readInt();
      int k = localDataInputStream.readInt();
      sampleRate = localDataInputStream.readInt();
      channels = localDataInputStream.readInt();
      localFileInputStream.skip(i - 24);
      paramLong *= channels;
      paramInt *= channels;
    }
    catch (IOException localIOException1)
    {
      localIOException1.printStackTrace();
    }
    try
    {
      for (;;)
      {
        paramRandomAccessFile.seek(paramLong);
        float f1 = localDataInputStream.readShort() / 32767.0F;
        try
        {
          float f2 = paramRandomAccessFile.readFloat();
          paramRandomAccessFile.seek(paramLong);
          paramLong += 4L;
          float f3 = f2 + f1;
          if (paramFloat < f3)
          {
            paramFloat = f3;
            System.out.println("MAX small: " + paramFloat);
          }
          if ((f3 < 0.0F) && (paramFloat < f3 * -1.0F))
          {
            paramFloat = f3 * -1.0F;
            System.out.println("MAX large: " + paramFloat);
          }
          paramRandomAccessFile.writeFloat(f3);
        }
        catch (EOFException localEOFException2)
        {
          if (paramFloat < f1) {
            paramFloat = f1;
          }
          if ((f1 < 0.0F) && (paramFloat < f1 * -1.0F)) {
            paramFloat = f1 * -1.0F;
          }
          paramRandomAccessFile.writeFloat(f1);
          paramLong += 4L;
        }
      }
    }
    catch (EOFException localEOFException1) {}catch (IOException localIOException2)
    {
      localIOException2.printStackTrace();
    }
    try
    {
      localDataInputStream.close();
      localFileInputStream.close();
    }
    catch (IOException localIOException3)
    {
      localIOException3.printStackTrace();
    }
    return paramFloat;
  }
  
  public static void addEmUp(String paramString1, String paramString2, float paramFloat)
  {
    System.out.println("MAX amplitude: " + paramFloat);
    System.out.println("Writing .au/.snd file '" + paramString2 + "' please wait...");
    try
    {
      FileOutputStream localFileOutputStream = new FileOutputStream(paramString2);
      BufferedOutputStream localBufferedOutputStream = new BufferedOutputStream(localFileOutputStream, 4096);
      DataOutputStream localDataOutputStream = new DataOutputStream(localBufferedOutputStream);
      File localFile = new File(paramString1);
      FileInputStream localFileInputStream = new FileInputStream(localFile);
      BufferedInputStream localBufferedInputStream = new BufferedInputStream(localFileInputStream, 4096);
      DataInputStream localDataInputStream = new DataInputStream(localBufferedInputStream);
      int i = 28;
      int j = 0;
      int k = 3;
      localDataOutputStream.writeInt(779316836);
      localDataOutputStream.writeInt(i);
      localDataOutputStream.writeInt(j);
      localDataOutputStream.writeInt(k);
      localDataOutputStream.writeInt(sampleRate);
      localDataOutputStream.writeInt(channels);
      localDataOutputStream.writeInt(0);
      double d1 = System.currentTimeMillis();
      try
      {
        for (;;)
        {
          float f1 = localDataInputStream.readFloat();
          float f2 = f1 / paramFloat;
          if ((f2 < -1.0F) || (f2 > 1.0F)) {
            System.out.println("Outgoing= " + f2 + "  SAMPLE: " + f1 + "  MAX: " + paramFloat + "  SampleOut.max: " + SampleOut.max);
          }
          localDataOutputStream.writeShort((short)(int)(f2 * 32767.0F));
          j += 2;
        }
      }
      catch (EOFException localEOFException)
      {
        double d2 = System.currentTimeMillis();
        System.out.println("Finished writing the audio file in " + (d2 - d1) / 1000.0D + " seconds");
        localDataOutputStream.flush();
        localFileOutputStream.flush();
        localBufferedOutputStream.flush();
        localDataOutputStream.close();
        localFileOutputStream.close();
        localBufferedOutputStream.close();
        localFileInputStream.close();
        localBufferedInputStream.close();
        localDataInputStream.close();
        localFile.delete();
        if (localFile.exists())
        {
          localRandomAccessFile = new RandomAccessFile(paramString1, "rw");
          localRandomAccessFile.setLength(0L);
          localRandomAccessFile.close();
        }
        RandomAccessFile localRandomAccessFile = new RandomAccessFile(paramString2, "rw");
        localRandomAccessFile.seek(8L);
        localRandomAccessFile.writeInt(j);
        localRandomAccessFile.close();
        return;
      }
      catch (IOException localIOException2)
      {
        localIOException2.printStackTrace();
      }
      return;
    }
    catch (IOException localIOException1)
    {
      localIOException1.printStackTrace();
      System.out.println(localIOException1);
    }
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\audio\Audio.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */