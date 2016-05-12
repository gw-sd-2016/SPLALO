package jm.gui.show;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.PrintStream;

public class NoteGraphic
  extends Component
  implements MouseListener
{
  NoteGraphic()
  {
    addMouseListener(this);
  }
  
  public void mousePressed(MouseEvent paramMouseEvent)
  {
    System.out.println("X is: " + paramMouseEvent.getX());
  }
  
  public void mouseClicked(MouseEvent paramMouseEvent) {}
  
  public void mouseEntered(MouseEvent paramMouseEvent) {}
  
  public void mouseExited(MouseEvent paramMouseEvent) {}
  
  public void mouseReleased(MouseEvent paramMouseEvent) {}
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\gui\show\NoteGraphic.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */