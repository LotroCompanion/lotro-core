package delta.games.lotro.plugins.lotrocompanion;

import java.io.ByteArrayInputStream;

import org.apache.log4j.Logger;

import delta.common.utils.text.EncodingNames;

/**
 * Utility methods on buffers.
 * @author DAM
 */
public class BufferUtils
{
  private static final Logger LOGGER=Logger.getLogger(BufferUtils.class);

  /**
   * Read a 4 bytes value as a long.
   * @param buffer Buffer to read.
   * @param offset Offset of data in this buffer.
   * @return read value.
   */
  public static long getDoubleWordAtAsLong(byte[] buffer, int offset)
  {
    long high=((buffer[offset+3]&0xff)<<8)+(buffer[offset+2]&0xff);
    long low=((buffer[offset+1]&0xff)<<8)+(buffer[offset]&0xff);
    return (high<<16)+low;
  }

  /**
   * Read a 2 bytes value.
   * @param buffer Buffer to read.
   * @param offset Offset of data in this buffer.
   * @return read value.
   */
  public static int getWordAt(byte[] buffer, int offset)
  {
    return ((buffer[offset+1]&0xff)<<8)+(buffer[offset]&0xff);
  }

  /**
   * Read a 4 bytes value.
   * @param buffer Buffer to read.
   * @param offset Offset of data in this buffer.
   * @return read value.
   */
  public static int getDoubleWordAt(byte[] buffer, int offset)
  {
    return ((buffer[offset+3]&0xff)<<24)+((buffer[offset+2]&0xff)<<16)+((buffer[offset+1]&0xff)<<8)+(buffer[offset]&0xff);
  }

  /**
   * Read 'TSize'.
   * @param is Input stream.
   * @return An integer.
   */
  public static int readTSize(ByteArrayInputStream is)
  {
    /*int padding=*/is.read();
    return readVle(is);
  }

  /**
   * Read a boolean value.
   * @param is Input stream.
   * @return A boolean value.
   */
  public static boolean readBoolean(ByteArrayInputStream is)
  {
    int value=BufferUtils.readUInt8(is);
    if (value==0) return false;
    if (value==1) return true;
    throw new IllegalArgumentException("Invalid boolean value: "+value);
  }

  /**
   * Read a float value.
   * @param is Input stream.
   * @return A float value.
   */
  public static float readFloat(ByteArrayInputStream is)
  {
    int ch1 = is.read();
    int ch2 = is.read();
    int ch3 = is.read();
    int ch4 = is.read();
    if ((ch1 | ch2 | ch3 | ch4) < 0)
    {
        throw new IllegalArgumentException("EOF reached");
    }
    int value=((ch4 << 24) + (ch3 << 16) + (ch2 << 8) + (ch1 << 0));
    return Float.intBitsToFloat(value);
  }

  /**
   * Read a signed 64 bits value.
   * @param is Input stream.
   * @return A long.
   */
  public static long readLong64(ByteArrayInputStream is)
  {
    byte[] buffer=new byte[8];
    try
    {
      is.read(buffer);
    }
    catch(Exception e)
    {
      LOGGER.warn("Could not read 8 bytes!",e);
    }
    return (((long)buffer[7] << 56) +
            ((long)(buffer[6] & 0xff) << 48) +
            ((long)(buffer[5] & 0xff) << 40) +
            ((long)(buffer[4] & 0xff) << 32) +
            ((long)(buffer[3] & 0xff) << 24) +
            ((buffer[2] & 0xff) << 16) +
            ((buffer[1] & 0xff) <<  8) +
            ((buffer[0] & 0xff) <<  0));
  }

  /**
   * Read an unsigned 32 bits value.
   * @param is Input stream.
   * @return An integer.
   */
  public static int readUInt32(ByteArrayInputStream is)
  {
    int b4=is.read()&0xff;
    int b3=is.read()&0xff;
    int b2=is.read()&0xff;
    int b1=is.read()&0xff;
    return (((((b1<<8)+b2)<<8)+b3)<<8)+b4;
  }

  /**
   * Read an unsigned 24 bits value.
   * @param is Input stream.
   * @return An integer.
   */
  public static int readUInt24(ByteArrayInputStream is)
  {
    int b3=is.read()&0xff;
    int b2=is.read()&0xff;
    int b1=is.read()&0xff;
    return (((b1<<8)+b2)<<8)+b3;
  }

  /**
   * Read an unsigned 16 bits value.
   * @param is Input stream.
   * @return An integer.
   */
  public static int readUInt16(ByteArrayInputStream is)
  {
    int low=is.read()&0xff;
    int high=is.read()&0xff;
    return (high<<8)+low;
  }

  /**
   * Read an unsigned 8 bits value.
   * @param is Input stream.
   * @return An integer.
   */
  public static int readUInt8(ByteArrayInputStream is)
  {
    int low=is.read()&0xff;
    return low;
  }

  /**
   * Skip some bytes.
   * @param is Input stream.
   * @param size Number of bytes to skip.
   */
  public static void skip(ByteArrayInputStream is, int size)
  {
    for(int i=0;i<size;i++) is.read();
  }

  /**
   * Read 'VLE' (variable length).
   * @param is Input stream.
   * @return An integer.
   */
  public static int readVle(ByteArrayInputStream is)
  {
    int result = is.read()&0xff;
    if ((result & 0x80) != 0)
    {
      int b=is.read()&0xff;
      result = (((result & 0x7f) << 8) | b);
    }
    if (result == 0x4000)
    {
      return readUInt16(is);
    }
    return result;
  }

  /**
   * Read a 'ASCII' string.
   * @param is Input stream.
   * @return A string or <code>null</code> if decoding failed.
   */
  public static String readAsciiString(ByteArrayInputStream is)
  {
    try
    {
      int length=readUInt8(is);
      byte[] buffer=new byte[length];
      is.read(buffer);
      return new String(buffer,"ASCII");
    }
    catch(Exception e)
    {
      LOGGER.warn("Could not read an ASCII string!",e);
    }
    return null;
  }

  /**
   * Read a 'Pascal' string.
   * @param is Input stream.
   * @return A string or <code>null</code> if decoding failed.
   */
  public static String readPascalString(ByteArrayInputStream is)
  {
    try
    {
      int length=readVle(is);
      byte[] buffer=new byte[length];
      is.read(buffer);
      return new String(buffer,EncodingNames.LATIN_1);
    }
    catch(Exception e)
    {
      LOGGER.warn("Could not read a Pascal string!",e);
    }
    return null;
  }

  /**
   * Read a 'prefixed UTF-16' string.
   * @param is Input stream.
   * @return A string or <code>null</code> if decoding failed.
   */
  public static String readPrefixedUtf16String(ByteArrayInputStream is)
  {
    String line=null;
    int length=BufferUtils.readVle(is);
    if (length>20000)
    {
      LOGGER.warn("Bad string length: "+length);
      throw new IllegalArgumentException("Bad string length!");
    }
    byte[] buffer=new byte[length*2];
    try
    {
      is.read(buffer);
      // Swap all words
      for(int i=0;i<length;i++)
      {
        byte tmp=buffer[i*2+1];
        buffer[i*2+1]=buffer[i*2];
        buffer[i*2]=tmp;
      }
      line=new String(buffer,"UTF16");
      LOGGER.debug("Read: "+line);
    }
    catch(Exception e)
    {
      LOGGER.warn("Could not read a prefixed UTF-16 string!",e);
      line=null;
    }
    return line;
  }
}
