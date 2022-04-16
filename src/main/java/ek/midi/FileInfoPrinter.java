package ek.midi;

import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;


public class FileInfoPrinter
{
    public static void printTrackInfo(Sequence seq)
    {
        Track[] tracks = seq.getTracks();
        System.out.println("Tracks: " + tracks.length);
        
        for(int i = 0; i < tracks.length; i++)
        {
            Track tr = tracks[i];
            System.out.format("%02d: %s (%d) \n", i, getTrackName(tr), tr.size());
        }
    }
    
    
    private static String getTrackName(Track tr)
    {
        for(int i=0; i < tr.size(); i++) 
        {
            MidiEvent ev = tr.get(i);

            MidiMessage msg = ev.getMessage();
            if(msg instanceof MetaMessage)
            {
                MetaMessage mmsg = (MetaMessage)msg;
                if(mmsg.getType() == MessageType.META_track_name)
                {
                    return MessageUtils.toString(mmsg.getData()).trim();
                }
            }
        }
        
        return "";
    }
}
