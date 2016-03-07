package fullTests;

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
	
	public int num = 0;
	
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
	
	public void setStartTime(double start)
	{startTime = start;}
	
	public void setFrequency(List<Float> freq)
	{frequency = freq;}
	
	public void setTimingValue(String timeVal)
	{timingValue = timeVal;}
	
	
				/*ACCESSOR METHODS*/
	public List<String> getNoteValues()
	{return noteValue;}
	
	public double getStartTime()
	{return startTime;}
	
	public List<Float> getFrequency()
	{return frequency;}

	public String getTimingValue()
	{return timingValue;}
	
	
}
