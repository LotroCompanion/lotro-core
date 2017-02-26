package delta.games.lotro.plugins;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import delta.common.utils.NumericTools;
import delta.common.utils.io.FileIO;
import delta.common.utils.misc.IntegerHolder;
import delta.common.utils.text.EncodingNames;

/**
 * LUA data structure parser.
 * @author DAM
 */
public class LuaParser
{
  private String _body;
  private int _index;

  /**
   * Read data from the given string.
   * @param body String to read.
   * @return A map of data.
   */
  public Map<String,Object> read(String body)
  {
    if (body.startsWith("return")) body=body.substring(6);
    _body=body;
    _index=0;
    Map<String,Object> value=readPropertiesMap();
    return value;
  }

  /**
   * Read data from the given file.
   * @param file File to read.
   * @return A map of data.
   * @throws Exception if an error occurs.
   */
  public Map<String,Object> read(File file) throws Exception
  {
    byte[] b=FileIO.readFile(file);
    String body=new String(b,EncodingNames.UTF_8);
    return read(body);
  }

  private void skipBlanks()
  {
    while(true)
    {
      char c=readNextChar();
      if ((c!=' ') && (c!='\r') && (c!='\n') && (c!='\t'))
      {
        pushBackChar();
        break;
      }
    }
  }
  private void pushBackChar()
  {
    _index--;
  }

  private char readNextChar()
  {
    char c=_body.charAt(_index);
    _index++;
    return c;
  }

  private Map<String,Object> readPropertiesMap()
  {
    Map<String,Object> ret=new HashMap<String,Object>();
    skipBlanks();
    char c=readNextChar();
    if (c!='{') throwException("'{' expected");
    skipBlanks();
    c=readNextChar();
    if (c=='}')
    {
      return ret;
    }
    pushBackChar();
    Object[] property=readProperty();
    ret.put((String)property[0],property[1]);
    while (true)
    {
      skipBlanks();
      c=readNextChar();
      if (c=='}')
      {
        break;
      }
      if (c==',')
      {
        property=readProperty();
        ret.put((String)property[0],property[1]);
      }
      else
      {
        throwException("Unexpected char '" + c + '\'');
      }
    }
    return ret;
  }

  private Object[] readProperty()
  {
    String name=readPropertyName();
    skipBlanks();
    char c=readNextChar();
    if (c!='=') throwException("'=' expected");
    Object value=readPropertyValue();
    Object[] ret=new Object[2];
    ret[0]=name;
    ret[1]=value;
    return ret;
  }

  private Object readPropertyValue()
  {
    Object ret=null;
    skipBlanks();
    char c=readNextChar();
    if (c=='{')
    {
      pushBackChar();
      ret=readPropertiesMap();
    }
    else if (c=='\"')
    {
      pushBackChar();
      ret=readString();
    }
    else if ((c=='t') || (c=='f'))
    {
      pushBackChar();
      ret=readBoolean();
    }
    //System.out.println("Read value: "+ret);
    return ret;
  }

  private String readPropertyName()
  {
    skipBlanks();
    char c=readNextChar();
    if (c!='[') throwException("'[' expected");
    skipBlanks();
    String name=readString();
    skipBlanks();
    c=readNextChar();
    if (c!=']') throwException("'[' expected");
    //System.out.println("Read: "+name);
    return name;
  }

  private String readString()
  {
    char c=readNextChar();
    if (c!='\"') throwException("'\"' expected");
    StringBuilder sb=new StringBuilder();
    while (true)
    {
      c=readNextChar();
      if (c!='\"')
      {
        sb.append(c);
      }
      else
      {
        break;
      }
    }
    String ret=sb.toString();
    ret=resolveEscapes(ret);
    return ret;
  }

  private String resolveEscapes(String s)
  {
    StringBuilder sb=new StringBuilder();
    int length=s.length();
    int index=0;
    while(index<length)
    {
      char c=s.charAt(index);
      if (c!='#')
      {
        sb.append(c);
        index++;
      }
      else
      {
        IntegerHolder holder=new IntegerHolder(index);
        try
        {
          byte[] escaped=readEscapes(s,holder);
          sb.append(new String(escaped,"UTF-8"));
        }
        catch(Exception e)
        {
          throw new IllegalArgumentException("Bad escaped value", e);
        }
        index=holder.getInt();
      }
    }
    return sb.toString();
  }

  private byte[] readEscapes(String s, IntegerHolder indexHolder) throws IOException
  {
    ByteArrayOutputStream bos=new ByteArrayOutputStream();
    int index=indexHolder.getInt();
    while(true)
    {
      int index2=s.indexOf('#',index+1);
      if (index2==index+1)
      {
        bos.write("#".getBytes());
        indexHolder.setInt(index2+1);
        index=index2+1;
        break;
      }
      if (index2!=-1)
      {
        Integer escapedValue=NumericTools.parseInteger(s.substring(index+1,index2));
        if (escapedValue!=null)
        {
          bos.write(escapedValue.byteValue());
        }
        index=index2+1;
        indexHolder.setInt(index);
      }
      else
      {
        bos.write(s.substring(index).getBytes());
        indexHolder.setInt(s.length());
        break;
      }
      if (s.charAt(index)!='#')
      {
        break;
      }
    }
    byte[] byteBuffer=bos.toByteArray();
    return byteBuffer;
  }

  private Boolean readBoolean()
  {
    StringBuilder sb=new StringBuilder();
    char c=readNextChar();
    int nbChars=0;
    if (c=='t') nbChars=4;
    if (c=='f') nbChars=5;
    pushBackChar();
    for(int i=0;i<nbChars;i++)
    {
      sb.append(readNextChar());
    }
    String value=sb.toString();
    if ("true".equals(value)) return Boolean.TRUE;
    if ("false".equals(value)) return Boolean.FALSE;
    throwException("Expected a boolean value. Got: "+value);
    return null;
  }

  private void throwException(String msg)
  {
    throw new IllegalStateException(msg);
  }
}
