package jm.midi;

import java.io.PrintStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.Vector;
import jm.JMC;
import jm.midi.event.CChange;
import jm.midi.event.EndTrack;
import jm.midi.event.Event;
import jm.midi.event.KeySig;
import jm.midi.event.NoteOn;
import jm.midi.event.PChange;
import jm.midi.event.TempoEvent;
import jm.midi.event.TimeSig;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;

public final class MidiParser
  implements JMC
{
  private static double tickRemainder = 0.0D;
  
  public MidiParser() {}
  
  public static void SMFToScore(Score paramScore, SMF paramSMF)
  {
    System.out.println("Convert SMF to JM");
    Enumeration localEnumeration = paramSMF.getTrackList().elements();
    while (localEnumeration.hasMoreElements())
    {
      Part localPart = new Part();
      Track localTrack = (Track)localEnumeration.nextElement();
      Vector localVector1 = localTrack.getEvtList();
      Vector localVector2 = new Vector();
      sortEvents(paramScore, localVector1, localVector2, paramSMF, localPart);
      for (int i = 0; i < localVector2.size(); i++) {
        localPart.addPhrase((Phrase)localVector2.elementAt(i));
      }
      paramScore.addPart(localPart);
      paramScore.clean();
    }
  }
  
  private static void sortEvents(Score paramScore, Vector paramVector1, Vector paramVector2, SMF paramSMF, Part paramPart)
  {
    double d1 = 0.0D;
    double[] arrayOfDouble = new double[100];
    Note[] arrayOfNote = new Note[100];
    int i = 0;
    double d2 = 0.0D;
    int j = 0;
    for (int k = 0; k < paramVector1.size(); k++)
    {
      Event localEvent = (Event)paramVector1.elementAt(k);
      d1 += localEvent.getTime() / paramSMF.getPPQN();
      Object localObject;
      if (localEvent.getID() == 7)
      {
        localObject = (PChange)localEvent;
        paramPart.setInstrument(((PChange)localObject).getValue());
      }
      else if (localEvent.getID() == 16)
      {
        localObject = (TempoEvent)localEvent;
        paramScore.setTempo(((TempoEvent)localObject).getTempo());
      }
      else if (localEvent.getID() == 5)
      {
        localObject = (NoteOn)localEvent;
        paramPart.setChannel(((NoteOn)localObject).getMidiChannel());
        short s1 = ((NoteOn)localObject).getPitch();
        int m = ((NoteOn)localObject).getVelocity();
        short s2 = ((NoteOn)localObject).getMidiChannel();
        if (m > 0) {
          noteOn(j, arrayOfNote, paramSMF, k, arrayOfDouble, d1, paramVector2, s2, s1, m, paramVector1);
        }
      }
      else if ((localEvent instanceof TimeSig))
      {
        localObject = (TimeSig)localEvent;
        paramScore.setNumerator(((TimeSig)localObject).getNumerator());
        paramScore.setDenominator(((TimeSig)localObject).getDenominator());
      }
      else if ((localEvent instanceof KeySig))
      {
        localObject = (KeySig)localEvent;
        paramScore.setKeySignature(((KeySig)localObject).getKeySig());
        paramScore.setKeyQuality(((KeySig)localObject).getKeyQuality());
      }
    }
  }
  
  private static void noteOn(int paramInt1, Note[] paramArrayOfNote, SMF paramSMF, int paramInt2, double[] paramArrayOfDouble, double paramDouble, Vector paramVector1, short paramShort1, short paramShort2, int paramInt3, Vector paramVector2)
  {
    paramInt1 = -1;
    for (int i = 0; i < paramVector1.size(); i++) {
      if (paramArrayOfDouble[i] <= paramDouble + 0.08D)
      {
        paramInt1 = i;
        break;
      }
    }
    if (paramInt1 == -1)
    {
      paramInt1 = paramVector1.size();
      paramVector1.addElement(new Phrase(paramDouble));
      paramArrayOfDouble[paramInt1] = paramDouble;
    }
    if ((paramDouble > paramArrayOfDouble[paramInt1]) && (paramArrayOfNote[paramInt1] != null))
    {
      d1 = paramDouble - paramArrayOfDouble[paramInt1];
      if (d1 < 0.25D)
      {
        double d2 = paramArrayOfNote[paramInt1].getRhythmValue();
        paramArrayOfNote[paramInt1].setRhythmValue(d2 + d1);
      }
      else
      {
        localNote = new Note(Integer.MIN_VALUE, d1, 0);
        localNote.setPan(paramShort1);
        localNote.setDuration(d1);
        localNote.setOffset(0.0D);
        ((Phrase)paramVector1.elementAt(paramInt1)).addNote(localNote);
      }
      paramArrayOfDouble[paramInt1] += d1;
    }
    double d1 = MidiUtil.getEndEvt(paramShort2, paramVector2, paramInt2) / paramSMF.getPPQN();
    Note localNote = new Note(paramShort2, d1, paramInt3);
    localNote.setDuration(d1);
    paramArrayOfNote[paramInt1] = localNote;
    ((Phrase)paramVector1.elementAt(paramInt1)).addNote(paramArrayOfNote[paramInt1]);
    paramArrayOfDouble[paramInt1] += paramArrayOfNote[paramInt1].getRhythmValue();
  }
  
  public static void scoreToSMF(Score paramScore, SMF paramSMF)
  {
    System.out.println("Converting to SMF data structure...");
    double d1 = paramScore.getTempo();
    double d2 = 1.0D;
    double d3 = 1.0D;
    Track localTrack1 = new Track();
    localTrack1.addEvent(new TempoEvent(0, paramScore.getTempo()));
    localTrack1.addEvent(new TimeSig(0, paramScore.getNumerator(), paramScore.getDenominator()));
    localTrack1.addEvent(new KeySig(0, paramScore.getKeySignature()));
    localTrack1.addEvent(new EndTrack());
    paramSMF.getTrackList().addElement(localTrack1);
    int j = 0;
    Enumeration localEnumeration1 = paramScore.getPartList().elements();
    while (localEnumeration1.hasMoreElements())
    {
      Track localTrack2 = new Track();
      Part localPart = (Part)localEnumeration1.nextElement();
      System.out.print("    Part " + j + " '" + localPart.getTitle() + "' to SMF Track on Ch. " + localPart.getChannel() + ": ");
      j++;
      if (localPart.getTempo() != -1.0D) {
        d2 = d1 / localPart.getTempo();
      } else {
        d2 = 1.0D;
      }
      int i = localPart.getPhraseList().size();
      for (int k = 0; k < i; k++)
      {
        Phrase localPhrase1 = (Phrase)localPart.getPhraseList().elementAt(k);
        for (int m = 0; m < i; m++)
        {
          Phrase localPhrase2 = (Phrase)localPart.getPhraseList().elementAt(m);
          if (localPhrase2.getStartTime() > localPhrase1.getStartTime())
          {
            localPart.getPhraseList().setElementAt(localPhrase2, k);
            localPart.getPhraseList().setElementAt(localPhrase1, m);
            break;
          }
        }
      }
      LinkedList localLinkedList = new LinkedList();
      if (localPart.getInstrument() != -1) {
        localLinkedList.add(new Object()
        {
          public double time;
          public Event ev;
        });
      }
      if (localPart.getNumerator() != Integer.MIN_VALUE) {
        localLinkedList.add(new Object()
        {
          public double time;
          public Event ev;
        });
      }
      if (localPart.getKeySignature() != Integer.MIN_VALUE) {
        localLinkedList.add(new Object()
        {
          public double time;
          public Event ev;
        });
      }
      Enumeration localEnumeration2 = localPart.getPhraseList().elements();
      double d4 = 0.0D;
      double d5 = 0.0D;
      double d6 = 0.0D;
      int n = 0;
      while (localEnumeration2.hasMoreElements())
      {
        Phrase localPhrase3 = (Phrase)localEnumeration2.nextElement();
        Enumeration localEnumeration3 = localPhrase3.getNoteList().elements();
        d5 = localPhrase3.getStartTime() * d2;
        if (localPhrase3.getInstrument() != -1) {
          localLinkedList.add(new Object()
          {
            public double time;
            public Event ev;
          });
        }
        if (localPhrase3.getTempo() != -1.0D) {
          d3 = d1 / localPhrase3.getTempo();
        } else {
          d3 = d2;
        }
        int i1 = 0;
        System.out.print(" Phrase " + n++ + ":");
        double d9 = -1.0D;
        resetTicker();
        while (localEnumeration3.hasMoreElements())
        {
          Note localNote = (Note)localEnumeration3.nextElement();
          d6 = localNote.getOffset();
          if (localNote.getPan() != d9)
          {
            d9 = localNote.getPan();
            localLinkedList.add(new Object()
            {
              public double time;
              public Event ev;
            });
          }
          int i4 = 0;
          if (localNote.getPitchType() == true)
          {
            System.err.println("jMusic warning: converting note frequency to the closest MIDI pitch for SMF.");
            i4 = Note.freqToMidiPitch(localNote.getFrequency());
          }
          else
          {
            i4 = localNote.getPitch();
          }
          if (i4 != Integer.MIN_VALUE)
          {
            localLinkedList.add(new Object()
            {
              public double time;
              public Event ev;
            });
            double d10 = d5 + localNote.getDuration() * d3;
            localLinkedList.add(new Object()
            {
              public double time;
              public Event ev;
            });
          }
          d5 += tickRounder(localNote.getRhythmValue() * d3);
          System.out.print(".");
        }
      }
      Collections.sort(localLinkedList, new Comparator()
      {
        public int compare(Object paramAnonymousObject1, Object paramAnonymousObject2)
        {
          MidiParser.1EventPair local1EventPair1 = (MidiParser.1EventPair)paramAnonymousObject1;
          MidiParser.1EventPair local1EventPair2 = (MidiParser.1EventPair)paramAnonymousObject2;
          if (local1EventPair1.time - local1EventPair2.time < 0.0D) {
            return -1;
          }
          if (local1EventPair1.time - local1EventPair2.time > 0.0D) {
            return 1;
          }
          return 0;
        }
      });
      double d7 = 0.0D;
      resetTicker();
      for (int i3 = 0; i3 < localLinkedList.size(); i3++)
      {
        Object local1EventPair = (1EventPair)localLinkedList.get(i3);
        Event localEvent = local1EventPair.ev;
        double d8 = local1EventPair.time;
        int i2 = (int)((d8 - d7) * paramSMF.getPPQN() + 0.5D);
        d7 = d8;
        localEvent.setTime(i2);
        localTrack2.addEvent(localEvent);
      }
      localTrack2.addEvent(new EndTrack());
      paramSMF.getTrackList().addElement(localTrack2);
      System.out.println();
    }
  }
  
  private static boolean zeroVelEventQ(Event paramEvent)
  {
    return (paramEvent.getID() == 5) && (((NoteOn)paramEvent).getVelocity() == 0);
  }
  
  private static void resetTicker()
  {
    tickRemainder = 0.0D;
  }
  
  private static double tickRounder(double paramDouble)
  {
    int i = (int)(paramDouble * 480.0D);
    double d = i * 0.0020833333333333333D;
    tickRemainder += paramDouble - d;
    if (tickRemainder > 0.0010416666666666667D)
    {
      d += 0.0020833333333333333D;
      tickRemainder -= 0.0020833333333333333D;
    }
    return d;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\midi\MidiParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */