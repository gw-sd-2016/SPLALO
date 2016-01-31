

import java.util.ArrayList;
import java.util.List;

public class Note {

	private List<Float> frequency;
	private List<String> noteValue;
	private List<Integer> noteVal;
	private double startTime;
	private double endTime;
	private String timingValue;
	private double timingValConstant; 		//Based of JMusic notation library constants
	
	public Note()
	{
		frequency = new ArrayList<Float>();
		noteValue = new ArrayList<String>();
		noteVal = new ArrayList<Integer>();
		startTime = 0;
		timingValue = "";
	}
	
	
				/*MUTATOR METHODS*/
	public void setNoteValue(String nv)
		{noteValue.add(nv);}
	
	public void setNoteValue(int noteval) 
	{noteVal.add(noteval);}
	
	public void setStartTime(double start)
	{startTime = start;}
	
	public void setEndTime(double end)
	{endTime = end;}
	
	public void setFrequency(List<Float> freq)
	{frequency = freq;}
	
	public void setTimingValue(String timeVal)
	{timingValue = timeVal;}
	
	public void setTimingValue(double jMusicConstant) 
	{timingValConstant = jMusicConstant;}
	
	
				/*ACCESSOR METHODS*/
	public List<String> getNoteValues()
	{return noteValue;}
	
	public List<Integer> getNoteValue()
	{return noteVal;}
	
	public double getStartTime()
	{return startTime;}
	
	public double getEndTime()
	{return endTime;}
	
	public List<Float> getFrequency()
	{return frequency;}

	public String getTimingValue()
	{return timingValue;}
	
	
	
	public double calculateTimeFrame()
	{return (endTime - startTime);}




}
