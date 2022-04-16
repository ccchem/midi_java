package ek.midi;

import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;
import javax.sound.midi.Track;

public class EventPrinter
{
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
        
        switch(type)
        {
        case MessageType.META_track_name:
            System.out.println("Track name: " + MessageUtils.toString(msg.getData()));
            break;
        case MessageType.META_end_of_track:
            System.out.println("End of track");
            break;
        case MessageType.META_channel_prefix:
            System.out.println("Channel prefix: " + msg.getData()[0]);
            break;
        case MessageType.META_port_prefix:
            System.out.println("Port prefix: " + msg.getData()[0]);
            break;
        case MessageType.META_set_tempo:
            // tempo = Microseconds per quarter note (beat)
            // There are 60,000,000 microseconds per minute
            // quarter note (beats) per minute = 60,000,000 / tempo
            float tempo = MessageUtils.toInt(msg.getData());
            int bpm = Math.round(60_000_000.0f / tempo);
            System.out.println("Set tempo: " + bpm + " bpm");
            break;
        case MessageType.META_SMPTE_offset:
            byte[] data = msg.getData();
            System.out.format("SMPTE offset (hh:mm:ss.ff.sf): %02d:%02d:%02d.%d.%d\n", 
                    data[0], data[1], data[2], data[3], data[4]);
            break;
        case MessageType.META_time_signature:
            System.out.println("Time signature: ");
            break;
        case MessageType.META_key_signature:
            System.out.println("Key signature: ");
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
    

}
