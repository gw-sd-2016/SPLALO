import jm.music.data.*;
import jm.JMC;
import jm.util.*;
 
public class Dot01 implements JMC {
    public static void main(String[] args) { 
        jm.music.data.Note n;
        n = new jm.music.data.Note(C5, QUARTER_NOTE);
        jm.music.data.Note n1 = new jm.music.data.Note(E4, QUARTER_NOTE);
        Phrase phr = new Phrase();
        int[] array = {440, 880};
        //phr.addChord(array, QUARTER_NOTE);
        phr.addNote(n1);
        phr.addNote(n);
        
        View.notate(phr);
    }
}