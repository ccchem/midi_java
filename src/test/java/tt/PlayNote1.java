package tt;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;


public class PlayNote1
{

    public static void main(String[] args) throws Exception
    {
        Synthesizer midiSynth = MidiSystem.getSynthesizer(); 
        midiSynth.open();
        
        Instrument[] insts = midiSynth.getDefaultSoundbank().getInstruments();
        midiSynth.loadInstrument(insts[0]);
        
        Thread.sleep(1000);
        
        MidiChannel[] channels = midiSynth.getChannels();
        play(channels[0]);
        
        Thread.sleep(2000);
        
        midiSynth.close();
    }

    
    private static void play(MidiChannel ch) throws Exception
    {
        for(int note = 60; note < 75; note += 2)
        {
            ch.noteOn(note, 100);
            Thread.sleep(500);
            ch.noteOff(note);
        }        
    }
    
    
    private static void printInstruments(Instrument[] insts)
    {
        for(int i = 0; i < insts.length; i++)
        {
            Instrument inst = insts[i];
            System.out.println(i + " - " + inst.getName());
        }
    }
}
