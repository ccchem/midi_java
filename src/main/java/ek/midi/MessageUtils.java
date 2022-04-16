package ek.midi;

public class MessageUtils
{
    public static int toInt(byte[] bb)
    {
        switch(bb.length)
        {
        case 3:
            return ((0xFF & bb[0]) << 16) | ((0xFF & bb[1]) << 8) | (0xFF & bb[2]);
        case 4:
            return ((0xFF & bb[0]) << 24) | ((0xFF & bb[1]) << 16) | ((0xFF & bb[2]) << 8) | (0xFF & bb[3]);
        }
        
        return 0;
    }
    
    
    public static String toString(byte[] data)
    {
        if(data != null && data.length != 0)
        {
            try
            {
                String name = new String(data, "UTF-8");
                return name;
            }
            catch(Exception ex)
            {
            }
        }
        
        return "";
    }

}
