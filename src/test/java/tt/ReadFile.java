package tt;

import java.io.File;

import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import ek.midi.MessageUtils;


public class ReadFile
{
    public static final int NOTE_ON = 0x90;
    public static final int NOTE_OFF = 0x80;
    
    public static final String[] NOTE_NAMES = { 
            "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"
    };
    
    public static void main(String[] args) throws Exception
    {
        Sequence seq = MidiSystem.getSequence(new File("/tmp/test.mid"));
        
        Track tr = seq.getTracks()[0];
        
        for(int i=0; i < tr.size(); i++) 
        { 
            MidiEvent ev = tr.get(i);
            System.out.print(ev.getTick() + " ");
            processMidiMsg(ev.getMessage());
        }
    }

    
    
    private static void print(ShortMessage msg)
    {
        System.out.print("Channel: " + msg.getChannel() + " ");

        if(msg.getCommand() == NOTE_ON) 
        {
            int key = msg.getData1();
            int octave = (key / 12)-1;
            int note = key % 12;
            String noteName = NOTE_NAMES[note];
            int velocity = msg.getData2();
            
            String onOff = velocity == 0 ? "Note off" : "Note on";
            System.out.println(onOff + ", " + noteName + octave + " key=" + key + " velocity: " + velocity);
        } 
        else if (msg.getCommand() == NOTE_OFF) 
        {
            int key = msg.getData1();
            int octave = (key / 12)-1;
            int note = key % 12;
            String noteName = NOTE_NAMES[note];
            int velocity = msg.getData2();

            System.out.println("Note off, " + noteName + octave + " key=" + key + " velocity: " + velocity);
        } 
        else 
        {
            System.out.println("Command:" + msg.getCommand());
        }
    }
    
    
    private static void processMidiMsg(MidiMessage msg)
    {
        if(msg instanceof ShortMessage) 
        {
            print((ShortMessage)msg);
        } 
        else if(msg instanceof MetaMessage)
        {
            //print((MetaMessage)msg);
        }
        else
        {
            System.out.println("Other message: " + msg.getClass());
        }        
    }
}
