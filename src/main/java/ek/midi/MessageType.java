package ek.midi;

public interface MessageType
{
    public static final int META_track_name = 0x3;          // 3
    public static final int META_channel_prefix = 0x20;     // 32
    public static final int META_port_prefix = 0x21;        // 33
    public static final int META_end_of_track = 0x2F;       // 47
    public static final int META_set_tempo = 0x51;          // 81
    public static final int META_SMPTE_offset = 0x54;       // 84
    public static final int META_time_signature = 0x58;     // 88
    public static final int META_key_signature = 0x59;      // 89
}
