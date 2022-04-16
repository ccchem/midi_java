package tt;

import java.io.File;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;

import ek.midi.EventPrinter;
import ek.midi.FileInfoPrinter;


public class TestFileInfo
{

    public static void main(String[] args) throws Exception
    {
        Sequence seq = MidiSystem.getSequence(new File("/ws4/Music/MIDI/WhatsGoinOn.mid"));

        //FileInfoPrinter.printTrackInfo(seq);
        
        EventPrinter ep = new EventPrinter();
        ep.print(seq.getTracks()[0]);
    }

}
