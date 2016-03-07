package fullTests;

import java.util.ArrayList;

import jm.music.data.Phrase;
import jm.util.View;
import pureToneTests.Note;

public class TranscribeSheetMusic {
	public static int[] jMusicConstants = {jm.constants.Pitches.C0, jm.constants.Pitches.D0, jm.constants.Pitches.E0, jm.constants.Pitches.F0, jm.constants.Pitches.G0, jm.constants.Pitches.A0, jm.constants.Pitches.B0, jm.constants.Pitches.C1, jm.constants.Pitches.D1, jm.constants.Pitches.E1, jm.constants.Pitches.F1, jm.constants.Pitches.G1, jm.constants.Pitches.A1, jm.constants.Pitches.B1, jm.constants.Pitches.C2, jm.constants.Pitches.D2, jm.constants.Pitches.E2, jm.constants.Pitches.F2, jm.constants.Pitches.G2 , jm.constants.Pitches.A2, jm.constants.Pitches.B2, jm.constants.Pitches.C3, jm.constants.Pitches.D3, jm.constants.Pitches.E3, jm.constants.Pitches.F3, jm.constants.Pitches.G3, jm.constants.Pitches.A3, jm.constants.Pitches.B3, jm.constants.Pitches.C4, jm.constants.Pitches.D4, jm.constants.Pitches.E4, jm.constants.Pitches.F4, jm.constants.Pitches.G4 , jm.constants.Pitches.A4, jm.constants.Pitches.B4, jm.constants.Pitches.C5, jm.constants.Pitches.D5, jm.constants.Pitches.E5, jm.constants.Pitches.F5, jm.constants.Pitches.G5, jm.constants.Pitches.A5, jm.constants.Pitches.B5, jm.constants.Pitches.C6, jm.constants.Pitches.D6, jm.constants.Pitches.E6, jm.constants.Pitches.F6, jm.constants.Pitches.G6, jm.constants.Pitches.A6, jm.constants.Pitches.B6, jm.constants.Pitches.C7, jm.constants.Pitches.D7, jm.constants.Pitches.E7, jm.constants.Pitches.F7, jm.constants.Pitches.G7, jm.constants.Pitches.A7, jm.constants.Pitches.B7,};
	
	public static void write(ArrayList<Note> noteArray)
	{
		//DISPLAY AS SHEET MUSIC
		Phrase phr = new Phrase();
		
		for(int i = 0; i < noteArray.size(); i++)
		{
			//System.out.println(noteArray.get(i).getNoteValue().get(0));
			jm.music.data.Note n = new jm.music.data.Note();
			if(noteArray.get(i).getTimingValue() == "QUARTER_NOTE")
				n.setDuration(jm.constants.Durations.QUARTER_NOTE );
			

			System.out.println(jMusicConstants[noteArray.get(i).num]);
			n.setPitch(jMusicConstants[noteArray.get(i).num]);
			phr.addNote(n);
		}
		

        View.notate(phr);
	}
	
}
