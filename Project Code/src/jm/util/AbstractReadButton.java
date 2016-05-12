package jm.util;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Label;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import jm.midi.MidiParser;
import jm.midi.SMF;
import jm.music.data.Score;

public abstract class AbstractReadButton
  extends Button
{
  protected Frame owner = null;
  
  public AbstractReadButton() {}
  
  public Score readFile(String paramString1, String paramString2)
  {
    if ((paramString1 == null) || (paramString2 == null)) {
      return null;
    }
    Score localScore = null;
    String str1 = null;
    try
    {
      SMF localSMF = new SMF();
      localScore = new Score(paramString2);
      localFileInputStream = new FileInputStream(paramString1 + paramString2);
      localSMF.read(localFileInputStream);
      MidiParser.SMFToScore(localScore, localSMF);
    }
    catch (IOException localIOException1)
    {
      FileInputStream localFileInputStream;
      str1 = localIOException1.getMessage();
      if (str1 == null) {
        str1 = "Unknown IO Exception";
      } else if (str1.equals("Track Started in wrong place!!!!  ABORTING")) {
        str1 = "The MIDI file corrupted.  Track data started in the wrong place.";
      } else if (str1.equals("This is NOT a MIDI file !!!")) {
        try
        {
          localFileInputStream = new FileInputStream(paramString1 + paramString2);
          ObjectInputStream localObjectInputStream = new ObjectInputStream(localFileInputStream);
          localScore = (Score)localObjectInputStream.readObject();
          localObjectInputStream.close();
          localFileInputStream.close();
        }
        catch (SecurityException localSecurityException)
        {
          str1 = "Read access not allowed to " + paramString2;
        }
        catch (ClassNotFoundException localClassNotFoundException)
        {
          str1 = "The file " + paramString2 + " is neither a jm or MIDI file";
        }
        catch (ClassCastException localClassCastException)
        {
          str1 = "The file " + paramString2 + " is neither a jm or MIDI file";
        }
        catch (StreamCorruptedException localStreamCorruptedException)
        {
          str1 = "The file " + paramString2 + " is neither a jm or MIDI file";
        }
        catch (IOException localIOException2)
        {
          str1 = localIOException2.getMessage();
          if (str1 == null) {
            str1 = "Unknown Exception";
          }
        }
      }
    }
    if (str1 != null)
    {
      final String str2 = str1;
      new Dialog(this.owner, "Not a valid MIDI or jMusic File", true) {}.show();
    }
    return localScore;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\util\AbstractReadButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */