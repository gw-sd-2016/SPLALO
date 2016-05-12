package fullTests;

import java.util.ArrayList;

import jm.music.data.Phrase;
import jm.util.View;

import jm.JMC;
import jm.music.data.CPhrase;
//import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.Play;
import jm.util.Write;


//import pureToneTests.Note;

public class TranscribeSheetMusic {
	public static int[] jMusicConstants = {jm.constants.Pitches.C0, jm.constants.Pitches.D0, jm.constants.Pitches.E0, jm.constants.Pitches.F0, jm.constants.Pitches.G0, jm.constants.Pitches.A0, jm.constants.Pitches.B0, jm.constants.Pitches.C1, jm.constants.Pitches.D1, jm.constants.Pitches.E1, jm.constants.Pitches.F1, jm.constants.Pitches.G1, jm.constants.Pitches.A1, jm.constants.Pitches.B1, jm.constants.Pitches.C2, jm.constants.Pitches.D2, jm.constants.Pitches.E2, jm.constants.Pitches.F2, jm.constants.Pitches.G2 , jm.constants.Pitches.A2, jm.constants.Pitches.B2, jm.constants.Pitches.C3, jm.constants.Pitches.D3, jm.constants.Pitches.E3, jm.constants.Pitches.F3, jm.constants.Pitches.G3, jm.constants.Pitches.A3, jm.constants.Pitches.B3, jm.constants.Pitches.C4, jm.constants.Pitches.D4, jm.constants.Pitches.E4, jm.constants.Pitches.F4, jm.constants.Pitches.G4 , jm.constants.Pitches.A4, jm.constants.Pitches.B4, jm.constants.Pitches.C5, jm.constants.Pitches.D5, jm.constants.Pitches.E5, jm.constants.Pitches.F5, jm.constants.Pitches.G5, jm.constants.Pitches.A5, jm.constants.Pitches.B5, jm.constants.Pitches.C6, jm.constants.Pitches.D6, jm.constants.Pitches.E6, jm.constants.Pitches.F6, jm.constants.Pitches.G6, jm.constants.Pitches.A6, jm.constants.Pitches.B6, jm.constants.Pitches.C7, jm.constants.Pitches.D7, jm.constants.Pitches.E7, jm.constants.Pitches.F7, jm.constants.Pitches.G7, jm.constants.Pitches.A7, jm.constants.Pitches.B7,};
	
	public static void write(ArrayList<fullTests.Note> noteArray)
	{
		//DISPLAY AS SHEET MUSIC
		CPhrase phr = new CPhrase();
		
		for(int i = 0; i < noteArray.size(); i++)
		{
			//System.out.println(noteArray.get(i).getNoteValue().get(0));
			jm.music.data.Note n[] = new jm.music.data.Note[noteArray.get(i).getNoteValue().size()];
			System.out.println("n.length = " + n.length);
			
			//NOTE VALUE
			for(int x = 0; x < n.length; x++)
			{
				System.out.println("FORM " + jMusicConstants[noteArray.get(i).getNoteValue().get(x)]);
				jm.music.data.Note temp = new jm.music.data.Note();
				temp.setPitch(jMusicConstants[noteArray.get(i).getNoteValue().get(x)]);
				n[x] = temp;
			}
			
			
			
			
			//One-ThirtySecond Note 
			if(noteArray.get(i).getTimingValue() == "DEMISEMIQUAVER")
				for(jm.music.data.Note n1 : n)
					n1.setRhythmValue(jm.constants.Durations.DEMI_SEMI_QUAVER);
				
					
			//Dotted One-ThirtySecond Note
			//else if(noteArray.get(i).getTimingValue() == "DOTTED DEMISEMIQUAVER")
				//n.setRhythmValue(jm.constants.Durations.);
			
			//One-Sixteenth Note
			else if(noteArray.get(i).getTimingValue() == "SEMIQUAVER")
				for(jm.music.data.Note n1 : n)
					n1.setRhythmValue(jm.constants.Durations.SEMI_QUAVER);
			
			//Dotted One-Sixteenth Note 
			else if(noteArray.get(i).getTimingValue() == "DOTTED SEMIQUAVER")
				for(jm.music.data.Note n1 : n)
					n1.setRhythmValue(jm.constants.Durations.DOTTED_SEMI_QUAVER);
			
			//One-Eighth Note
			else if(noteArray.get(i).getTimingValue() == "QUAVER")
				for(jm.music.data.Note n1 : n)
					n1.setRhythmValue(jm.constants.Durations.QUAVER);
			
			//Dotted One-Eighth Note
			else if(noteArray.get(i).getTimingValue() == "DOTTED QUAVER")
				for(jm.music.data.Note n1 : n)
					n1.setRhythmValue(jm.constants.Durations.DOTTED_QUAVER);
			
			//Quarter Note
			else if(noteArray.get(i).getTimingValue() == "CROTCHET")
				for(jm.music.data.Note n1 : n)
					n1.setRhythmValue(jm.constants.Durations.CROTCHET);
			
			//Dotted Quarter Note
			else if(noteArray.get(i).getTimingValue() == "DOTTED CROTCHET")
				for(jm.music.data.Note n1 : n)
					n1.setRhythmValue(jm.constants.Durations.DOTTED_CROTCHET);
			
			//Half Note
			else if(noteArray.get(i).getTimingValue() == "MINIM")
				for(jm.music.data.Note n1 : n)
					n1.setRhythmValue(jm.constants.Durations.MINIM);
			
			//Dotted Half Note
			else if(noteArray.get(i).getTimingValue() == "DOTTED MINIM")
				for(jm.music.data.Note n1 : n)
					n1.setRhythmValue(jm.constants.Durations.DOTTED_MINIM);
			
			//Whole Note
			else if(noteArray.get(i).getTimingValue() == "SEMIBREVE")
				for(jm.music.data.Note n1 : n)
					n1.setRhythmValue(jm.constants.Durations.SEMIBREVE);
			
			//Dotted Whole Note
			//else if(noteArray.get(i).getTimingValue() == "DOTTED SEMIBREVE")
				//n.setRhythmValue(jm.constants.Durations.DOTTED);
			
			
			
			if(n.length > 0)
				phr.addChord(n);
			
			
			//int[] ar = {jm.constants.Pitches.C4, jm.constants.Pitches.A4};
			//phr.addChord(ar, jm.constants.Durations.M);
			
		}
		

        View.notation(phr);
	}
	
}
