package jm;

import jm.constants.Alignments;
import jm.constants.DrumMap;
import jm.constants.Dynamics;
import jm.constants.Frequencies;
import jm.constants.Noises;
import jm.constants.Panning;
import jm.constants.Pitches;
import jm.constants.ProgramChanges;
import jm.constants.RhythmValues;
import jm.constants.Scales;
import jm.constants.Tunings;
import jm.constants.Waveforms;

public abstract interface JMC
  extends RhythmValues, Pitches, Frequencies, Tunings, Dynamics, Panning, ProgramChanges, DrumMap, Scales, Waveforms, Noises, Alignments
{
  public static final boolean DEBUG = false;
  public static final boolean VERBOSE = true;
  public static final int EIGHT_BIT = 127;
  public static final int SIXTEEN_BIT = 32767;
  public static final int THIRTY_TWO_BIT = 214748647;
  public static final int PROG_EVT = 748394;
  public static final int TEMP_EVT = 748395;
  public static final int KEY_SIG_EVT = 748396;
  public static final int TIME_SIG_EVT = 748397;
  public static final int NO_KEY_SIGNATURE = Integer.MIN_VALUE;
  public static final int NO_KEY_QUALITY = Integer.MIN_VALUE;
  public static final int NO_NUMERATOR = Integer.MIN_VALUE;
  public static final int NO_DENOMINATOR = Integer.MIN_VALUE;
  public static final int NO_INSTRUMENT = -1;
  public static final int AMPLITUDE = 0;
  public static final int FREQUENCY = 1;
  public static final int MONO = 1;
  public static final int STEREO = 2;
  public static final int QUADRAPHONIC = 4;
  public static final int OCTAPHONIC = 8;
  public static final int PITCH = 0;
  public static final int RHYTHM = 1;
  public static final int DYNAMIC = 2;
  public static final int PAN = 3;
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\JMC.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */