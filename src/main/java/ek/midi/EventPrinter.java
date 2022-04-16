package ek.midi;

import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;
import javax.sound.midi.Track;


public class EventPrinter
{
    private static final String[] majorNotes =
    {
        "Cb Db Eb Fb Gb Ab Bb", 
        "Gb Ab Bb Cb Db Eb F",
        "Db Eb F Gb Ab Bb C",
        "Ab Bb C Db Eb F G",
        "Eb F G Ab Bb C D",
        "Bb C D Eb F G A",
        "F G A Bb C D E",
        "C D E F G A B",
        "G A B C D E F#",
        "D E F# G A B C#",
        "A B C# D E F# G#",
        "E F# G# A B C# D#",
        "B C# D# E F# G# A#",
        "F# G# A# B C# D# E#",
        "C# D# E# F# G# A# B#"
    };

    private static final String[] majorRoots =
    {
        "Cb", "Gb", "Db", "Ab", "Eb", "Bb", "F", "C", "G", "D", "A", "E", "B", "F#", "C#"
    };

    private static final String[] minorNotes =
    {
        "Ab Bb Cb Db Eb Fb Gb",
        "Eb F Gb Ab Bb Cb Db",
        "Bb C Db Eb F Gb Ab",
        "F G Ab Bb C Db Eb",
        "C D Eb F G Ab Bb",
        "G A Bb C D Eb F",
        "D E F G A Bb C",
        "A B C D E F G",
        "E F# G A B C D",
        "B C# D E F# G A",
        "F# G# A B C# D E",
        "C# D# E F# G# A B",
        "G# A# B C# D# E F#",
        "D# E# F# G# A# B C#",
        "A# B# C# D# E# F# G#"
    };

    private static final String[] minorRoots =
    {
        "Ab", "Eb", "Bb", "F", "C", "G", "D", "A", "E", "B", "F#", "C#", "G#", "D#", "A#"    
    };
    
    
    public void print(Track tr)
    {
        for(int i=0; i < tr.size(); i++) 
        {
            MidiEvent ev = tr.get(i);
            //System.out.print(ev.getTick() + " ");

            MidiMessage msg = ev.getMessage();
            if(msg instanceof ShortMessage)
            {
                printShortMessage((ShortMessage)msg);
            }
            if(msg instanceof MetaMessage)
            {
                printMetaMessage((MetaMessage)msg);
            }
            if(msg instanceof SysexMessage)
            {
                printSysexMessage((SysexMessage)msg);
            }
        }
    }
    
    
    private void printMetaMessage(MetaMessage msg)
    {
        int type = msg.getType();
        byte[] data = msg.getData();
        
        switch(type)
        {
        case MessageType.META_track_name:
            System.out.println("Track name: " + MessageUtils.toString(data));
            break;
        case MessageType.META_end_of_track:
            System.out.println("End of track");
            break;
        case MessageType.META_channel_prefix:
            System.out.println("Channel prefix: " + data[0]);
            break;
        case MessageType.META_port_prefix:
            System.out.println("Port prefix: " + data[0]);
            break;
        case MessageType.META_set_tempo:
            // tempo = Microseconds per quarter note (beat)
            // There are 60,000,000 microseconds per minute
            // quarter note (beats) per minute = 60,000,000 / tempo
            float tempo = MessageUtils.toInt(data);
            int bpm = Math.round(60_000_000.0f / tempo);
            System.out.println("Set tempo: " + bpm + " bpm");
            break;
        case MessageType.META_SMPTE_offset:
            System.out.format("SMPTE offset (hh:mm:ss.ff.sf): %02d:%02d:%02d.%d.%d\n", 
                    data[0], data[1], data[2], data[3], data[4]);
            break;
        case MessageType.META_time_signature:
            System.out.println("Time signature: ");
            break;
        case MessageType.META_key_signature:
            System.out.println("Key signature: " + getKeySignature(data));
            break;
        default:
            System.out.println("Meta: " + type);
            break;
        }

    }

    private void printShortMessage(ShortMessage msg)
    {
        
    }

    private void printSysexMessage(SysexMessage msg)
    {
        
    }
    

    private String getKeySignature(byte[] data)
    {
        boolean isMajor = data[1] == 0;
        
        String scale = isMajor ? " major" : " minor";
        int keyIndex = data[0] + 7;

        String root = isMajor ? majorRoots[keyIndex] : minorRoots[keyIndex];
        String notes = isMajor ? majorNotes[keyIndex] : minorNotes[keyIndex];

        String flats = "";
        if(data[0] < 0) flats = " (" + -data[0] + " flats)";
        else if(data[0] > 0) flats = " (" + data[0] + " sharps)";
        
        return root + scale + flats + ": " + notes;
    }

}
