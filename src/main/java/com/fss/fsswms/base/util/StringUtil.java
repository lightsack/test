package com.fss.fsswms.base.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import com.google.common.base.CaseFormat;

public class StringUtil {

	private StringUtil() {
	}

	public static String toString(Object o) {
		if (o == null) {
			return null;
		}
		return o.toString();
	}

	public static boolean isEmpty(String value) {
		return (value == null || value.length() == 0);
	}

	public static boolean isNotEmpty(String value) {
		return !isEmpty(value);
	}

	public static String trim(String value) {
		if(value == null) {
			return null;
		}
		return value.trim();
	}

	public static String getOnlyNum(String value) {
		String trimmedValue = StringUtil.trim(value);
		if(StringUtil.isEmpty(trimmedValue)) {
			return value;
		}
		return value.replaceAll("[^0-9]", "");
	}

	public static String getAmountNum(String value) {
		String trimmedValue = StringUtil.trim(value);
		if(StringUtil.isEmpty(trimmedValue)) {
			return value;
		}
		return value.replaceAll("[^0-9\\.\\-]", "");
	}

	/**
	 * 문자열이 len 사이즈보다 작을 경우 pad 문자열을 len 길이만큼 왼쪽에 채움
	 * @param value 원본문자열
	 * @param len 문자열길이
	 * @param pad 채울문자
	 * @return len 만큼 pad로 채워진 문자열
	 */
	public static String lpad(String value, int len, String pad) {
		if(StringUtil.isEmpty(value)) {
			return value;
		}
		int lenStr = value.length();
		int lenPad = pad.length();
		if (lenStr < len && lenPad > 0) {
			StringBuffer sb = new StringBuffer(len);
			int i = lenStr + lenPad;
			for (; i < len; i += lenPad) {
				sb.append(pad);
			}
			sb.append(pad.substring(0, (len - i + lenPad)));
			sb.append(value);
			return sb.toString();

		} else {
			return value;
		}
	}
	
	public static String replaceAll(String src, String from, String to) {
		if (isEmpty(src)) {
			return src;
		}
		if (isEmpty(from)) {
			return src;
		}
		if (to == null) {
			to = "";
		}
		StringBuffer buf = new StringBuffer();
		for (int pos;(pos = src.indexOf(from)) >= 0;) {
			buf.append(src.substring(0, pos));
			buf.append(to);
			src = src.substring(pos + from.length());
		}
		buf.append(src);
		return buf.toString();
		// return src.replaceAll(regex, replacement);
	}

    /**
     * 주어진 문자열에서 지정한 문자열값을 지정한 문자열로 치환후 그결과 문자열을 리턴함.
     *
     * @param src
     * @param off
     * @param from
     * @param to
     * @return 문자열
     */
    public static String replace(String src, int off, String from, String to) {
    	if (isEmpty(src)) {
			return src;
		}
		if (isEmpty(from)) {
			return src;
		}
        off = src.indexOf(from, off);
        if(off==-1) {
        	return src;
        }
        if (to == null) {
			to = "";
		}
        StringBuffer buff = new StringBuffer(src);
        buff.replace(off, off+from.length(), to);
        return buff.toString();
    }
    
    public static String nullToString(String value) {
        return nullToString(value, "");
    }
    
    public static String nullToString(String value, String defaultValue) {
    	if(isEmpty(value)) {
    		return defaultValue;
    	}
    	return value;
    }
    
    public static String nvl(String value) {
        return nvl(value, "");
    }
    
    public static String nvl(String value, String defaultValue) {
    	if(isEmpty(value) || "null".equals(value)) {
    		return defaultValue;
    	}
    	return value;
    }
    
    public static String nvl(Object valueObj) {
    	return nvl(valueObj, "");
    }

    public static String nvl(Object valueObj, String defaultValue) {
    	String value = toString(valueObj);
    	if(isEmpty(value) || "null".equals(value)) {
    		return defaultValue;
    	}
    	return value;
    }

	/**
	 * 입력된 값에 대한 Injection 보안처리를 한다.
	 *
	 * @param str 입력
	 * @param replace 대체 값
	 * @return 문자
	 */
	public static String injection (String str) {
		if (str == null) {
			return str;
		} else {
			str = replaceAll(str, "<", "");
			str = replaceAll(str, ">", "");
			str = replaceAll(str, "'", "''");
			return str;
		}
	}

	/**
	 * Null 또는 공백이면 다른 값으로 대체한다.
	 * @param str 입력
	 * @param replace 대체 값
	 * @return 문
	 */
	public static String checkEmpty(String str, String replace) {
		if (str == null || str.equals("")) {
			return replace;
		} else {
			return str;
		}
	}

	/**
	 * 프로퍼티 내의 컬럼 값을 변경한다.
	 * @param source 컬럼 값
	 * @return 변경된 컬럼 값
	 */
	public static String convertColumnIntoProp(String source) {
		if (source == null || source.indexOf("_") == -1) {
			if (source.equals(source.toUpperCase())) {
				//DESCRIPTION --> description
				return source.toLowerCase();
			} else {
				//subMeta01 --> subMeta01
				return source;
			}
		}

		StringBuffer buffer = new StringBuffer();

		source = source.toLowerCase();
		String[] tempArr = source.split("_", -1);

		for (int i = 0; i < tempArr.length; i++) {
			if (i == 0) {
				buffer.append(tempArr[i]);
			} else {
				buffer.append(capitalize(tempArr[i]));
			}
		}

		return buffer.toString();
	}

	/**
	 * 컬럼내의 프로퍼티 값을 변경한다.
	 * @param source 프로퍼티 값
	 * @return 변경된 프로퍼티 값
	 */
	public static String convertPropIntoColumn(String source) {
		if (source == null || source.length() == 0) {
			return source;
		}

		StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < source.length(); i++) {
			if (Character.isUpperCase(source.charAt(i))) {
				buffer.append("_");
			}

			buffer.append(Character.toUpperCase(source.charAt(i)));
		}

		return buffer.toString();
	}

	/**
	 * 문자를 합친다.
	 * @param str 문자
	 * @return 합쳐진 문자
	 */
	public static String capitalize(String str) {
		int strLen;

		if (str == null || (strLen = str.length()) == 0) {
			return str;
		}

		return new StringBuffer(strLen).append(Character.toTitleCase(str.charAt(0))).append(str.substring(1)).toString();
	}

	/**
	 * Exception 정보를 String으로 변환한다.
	 * @param e Exception
	 * @return String 변환된 Exception
	 */
	public static String getErrorTrace(Exception e){
		if(e == null){
			return "";
		}

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);

		String errTrace = sw.toString();

		return errTrace;
	}

	/**
	 * String 을 delimeter 기준으로 배열로 생성한다.
	 * @param str String 값
	 * @param delimeter 기준
	 * @return String[] 생성된 배열
	 */
	public static synchronized String[] tokenWork(String str, String delimeter) {
		String[] returnedArray = null;
		StringTokenizer tempStr = new StringTokenizer(str, delimeter);
		int FirstTokenCount = tempStr.countTokens();
		returnedArray = new String[FirstTokenCount];

		for (int n = 0; n < FirstTokenCount; n++) {
			returnedArray[n] = tempStr.nextToken();
		}

		return returnedArray;
	}

    /**
     * 특정한 문자를 제외한 나머지 문자를 리턴한다.
     * @param String    문자
     * @param char      뺄 문자
     * @return String
     */
    public static String hyphenToblank(String str, char delimeter) {
        int len = str.length();
        String rtValue = "";

        for( int i =0 ; i < len ; i++ ) {
            if( str.charAt(i) != delimeter ) {
                rtValue += str.charAt(i);
            }
        }
        return rtValue;
    }

    /**
     * 특정한 문자를 제외한 나머지 문자를 리턴한다.
     * @param String    문자
     * @param char      뺄 문자
     * @return String
     */
    public static String stringToDateType( String str ) {
        int len = str.length();
        String rtValue = "";

        if ( len == 0 ) {
            return rtValue;
        } else {
            rtValue = str.substring(0,4) + "-" + str.substring(4,6) + "-" +str.substring(6,8);
        }

        return rtValue;
    }

    /**
     * '\n'을 ""로 번환
     * @param String
     * @return String
     */
    public static String toSPACE(String str) {
        int len = str.length();
        int lineNum=0, i=0;
        StringBuffer new_str = null;

        try {
            for(i=0; i < len; i++) {
                if(str.charAt(i) == '\n') {
                    lineNum++;
                }
            }

            new_str = new StringBuffer(len + lineNum*3);

            for(i=0; i < len; i++){
                if(str.charAt(i) == '\n') {
                    new_str.append(" ");
                } else {
                    new_str.append(str.charAt(i));
                }
            }
        } catch(Exception e) {
        }

        return(new_str.toString());
    }

    /**
     * IP를 십진수로 변환
     * @param String IP
     * @return long 변환된 십진수
     */
    public static long getRealIP(String ip) throws Exception {
        InetAddress address = null;

        try {
            address = InetAddress.getByName(ip) ;
        } catch (UnknownHostException e) {
            return 0;
        }

        long realIP = address.hashCode();
        if (realIP < 0) {
            realIP ^= 0xFFFFFFFF00000000L;
        }

        return realIP;
    }

    public static String ipConvert( String sValue ) {
        String arrIp = "";
        String str = "";
        String str1 = "";

        for ( int i = 0; i < sValue.length(); i++ ) {
            char ch = sValue.charAt(i);

            if ( ch == '*' ) {
                str = str + "0";
                str1 = str1 + "255";
            } else {
                str = str + ch;
                str1 = str1 + ch;
            }
        }

        arrIp = str + "," + str1;

        return arrIp;
    }

    /**
     * IP 대역폭 을 비교해서 값을 리턴해준다.
     */
    public static boolean isIPTrue( long preLong, long lastLong, long comLong ) {
        try {
            if ( ( preLong <= comLong ) && ( lastLong >= comLong ) ) {
                return true;
            } else {
                return false;
            }
        } catch(Exception e) {
            return false;
        }
    }

    public static String toConvertStringType( String str ) {
        try {
            if ( str.length() > 0 ) {
                str = "'" + str + "'";
            } else {
                str = "''";
            }
        } catch ( Exception e ) {
            return "''";
        }

        return str;
    }

    /**
     * val의 오른쪽으로 pad를 len 만큼 채워준다.
     * @param String    문자
     * @param len
     * @param pad
     * @return String
     */
    public static String getRPad(String val, int len, char pad) {
        String result = val;
        int templen = len - result.getBytes().length;

        for (int i=0; i<templen; i++) {
            result = result + pad;
        }

        return result;
    }

    /**
     * val의 왼쪽으로 pad를 len 만큼 채워준다.
     * @param String    문자
     * @param len
     * @param pad
     * @return String
     */
    public static String getLPad(String val, int len, char pad) {
        String result = val;
        int templen = len - result.getBytes().length;

        for (int i=0; i<templen; i++) {
            result = pad + result;
        }

        return result;
    }

    /**
     * int형을 String형으로 변경
     * @return String
     */
    public static String int2str(int val) {
        return (new Integer(val).toString());
    }

    /**
     * String형을 int형으로 변경
     * @return String
     */
    public static int str2int(String val) {
        if (isEmptyString(val)) {
            return 0;
        }

        return (Integer.valueOf(val).intValue());
    }

    /**
     * String형을 double형으로 변경
     * @return String
     */
	public static double str2dbl(String val) {
		if (isEmptyString(val)) {
			return 0;
		}
		return (Double.parseDouble(val));
	}

	public static boolean isEmptyString(String val) {
	    if (val == null || val.equalsIgnoreCase("") || val.length() == 0) {
	        return true;
	    } else {
	        return false;
	    }
	}

    /**
     * 특수 문자 값으로 바꾸어 주는 메소드
     * @return String
     */
    public static String convertHtmlString(String strText) {
        if (strText == null) {
            strText = "";
        } else {
            strText = strText.trim();

            strText = StringUtil.replaceAll( strText, "&","&amp;");
            strText = StringUtil.replaceAll( strText,  "#","&#35;");
            strText = StringUtil.replaceAll( strText, "<","");
            strText = StringUtil.replaceAll( strText, ">","");
            strText = StringUtil.replaceAll( strText, "%","&#37;");
            strText = StringUtil.replaceAll( strText, "\"","&quot;");
            strText = StringUtil.replaceAll( strText, "'","&#39;");
        }

        return strText;
    }

    /**
     * 특수 문자 값을 원래값으로 바꾸어 주는 메소드
     * @return String
     */
    public static String reverseHtmlString(String strText) {
        if (strText == null) {
            strText = "";
        } else {
            strText = strText.trim();

	        strText = StringUtil.replaceAll( strText, "&amp;", "&");
	        strText = StringUtil.replaceAll( strText, "&#35;", "#");
	        strText = StringUtil.replaceAll( strText, "&lt;", "");
	        strText = StringUtil.replaceAll( strText, "&gt;", "");
	        strText = StringUtil.replaceAll( strText, "&#37;", (char)37 + "");
	        strText = StringUtil.replaceAll( strText, "&quot;", (char)34 + "");
	        strText = StringUtil.replaceAll( strText, "&#39;", (char)39 + "");
	        strText = StringUtil.replaceAll( strText, "\n", "<br>");
        }

        return strText;
    }

    /**
     * 특수 문자 값으로 바꾸어 주는 메소드
     * @return String
     */
    public static String convertXmlString(String strText) {
        if (strText == null) {
            strText = "";
        } else {
            strText = strText.trim();

            strText = StringUtil.replaceAll( strText, "&","&amp;");
            strText = StringUtil.replaceAll( strText, "<","&lt;");
            strText = StringUtil.replaceAll( strText, ">","&gt;");
            strText = StringUtil.replaceAll( strText, "'","&apos;");
            strText = StringUtil.replaceAll( strText, "\"","&quot;");

        }

        return strText;
    }

    /**
     * 특수 문자 값을 원래값으로 바꾸어 주는 메소드
     * @return String
     */
    public static String reverseXmlString(String strText) {
        if (strText == null) {
            strText = "";
        } else {
            strText = strText.trim();

            strText = StringUtil.replaceAll( strText, "&amp;","&");
            strText = StringUtil.replaceAll( strText, "&lt;","<");
            strText = StringUtil.replaceAll( strText, "&gt;",">");
            strText = StringUtil.replaceAll( strText, "&apos;","'");
            strText = StringUtil.replaceAll( strText, "&quot;","\"");
        }

        return strText;
    }

    /**
     * String으로 입력된 값의 특수문자 여부 확인 및 변환
     * XML Document 생성시 오류 보정(Tag 등 확인)
     * @param String  변환할 문자열
     * @return String 일정길이로 만들어진 문자열
     */
    public static String normalize(String sValue) {
        StringBuffer str = new StringBuffer();

        int len = (sValue != null) ? sValue.length() : 0;
        for ( int i = 0; i < len; i++ ) {
            char ch = sValue.charAt(i);
            switch ( ch ) {
                case '<':
                {
                    str.append("");
                    break;
                }
                case '>':
                {
                    str.append("");
                    break;
                }
                case '(':
                {
                	str.append("&#40");
                	break;
                }
                case ')':
                {
                	str.append("&#41");
                	break;
                }
                case '&':
                {
                    str.append("&amp;");
                    break;
                }
                case ';':
                {
                    str.append("");
                    break;
                }
                case '#':
                {
                    str.append("&#35;");
                    break;
                }
                case ' ':
                {
                    str.append("");
                    break;
                }
                case '\\':
                {
                    str.append("\\\\");
                    break;
                }
                case '"':
                {
                    str.append("&quot;");
                    break;
                }
                case '\'':
                {
                    str.append("\"");
                    break;
                }
                default:
                {
                    str.append(ch);
                }
            }
        }

        return(str.toString());
    }

    /**
     * String으로 입력된 값의 특수문자 여부 확인 및 변환
     * XML Document 생성시 오류 보정(Tag 등 확인)
     * @param String 변환할 문자열
     * @param aiByteSize  반환받을 Byte Size
     * @return String 일정길이로 만들어진 문자열
     */
	public static String convertFileName(String sValue) {
        StringBuffer str = new StringBuffer();

        int len = (sValue != null) ? sValue.length() : 0;
        for ( int i = 0; i < len; i++ ) {
            char ch = sValue.charAt(i);

            switch ( ch ) {
                case '<':
                {
                    str.append("");
                    break;
                }
                case '>':
                {
                    str.append("");
                    break;
                }
                case ';':
                {
                    str.append("");
                    break;
                }
                case ' ':
                {
                    str.append("");
                    break;
                }
                case '\\':
                {
                    str.append("\\\\");
                    break;
                }
                case '\'':
                {
                    str.append("\"");
                    break;
                }
                default:
                {
                    str.append(ch);
                }
            }
        }

        return(str.toString());
    }

    /**
     * 특수 문자 값을 원래값으로 바꾸어 주는 메소드
     * @return String
     */
    public static String convertEnter(String strText) {
        if (strText == null) {
            strText = "";
        } else {
	        strText = StringUtil.replaceAll( strText, "\n", "");
	        strText = StringUtil.replaceAll( strText, "\r", "");
	        strText = StringUtil.replaceAll( strText, "<","");
            strText = StringUtil.replaceAll( strText, ">","");
            strText = StringUtil.replaceAll( strText, "\"","");
            strText = StringUtil.replaceAll( strText, "'","");
        }

        return strText;
    }

    /**
     * 특수 문자 값을 원래값으로 바꾸어 주는 메소드
     * @return String
     */
    public static String reverseEnter(String strText) {
        if (strText == null) {
            strText = "";
        } else {
	        strText = StringUtil.replaceAll( strText, "&#35;", "\n");
	        strText = StringUtil.replaceAll( strText, "<br>", "\n");
        }

        return strText;
    }




    /**
     * 입력값에 HTML 엔티티 인코딩이 포함되어 있는지 확인하여 변경
     *
     * <pre>
     *   StoredXSSPatch.replaceXSS("<", 0) => ＜
     * </pre>
     *
     * @param str 검사할 String, null일 수 있음
     * @param type 모두(0), HTML 엔티티 인코딩(1), 차단 할 태그 리스트(2)
     * @return 변경된 문자열 리턴
     */
    public static String replaceXSS(String str, int type)
    {
    	String rtn = null;
    	if( str == null )
		{
    		rtn =  "";
		}else {
			rtn = "";

			if( type == 0 ) {
				rtn = replaceHTML(str);
				rtn = replaceTag(rtn);
			} else if( type == 1 ) {
				rtn = replaceHTML(str);
			} else if( type == 2 ) {
				rtn = replaceTag(str);
			}
		}

      return rtn;
    }

    /**
     * 입력값에 HTML 엔티티 인코딩이 포함되어 있는지 확인하여 변경
     *
     * <pre>
     *   StoredXSSPatch.replaceHTML("<") => ＜
     * </pre>
     *
     * @param str 검사할 String, null일 수 있음
     * @return 변경된 문자열 리턴
     */
    public static String replaceHTML(String str)
    {
    	String rtn = str;

    	if( str == null ) {
    		rtn =  "";
		}else {

			rtn = rtn.replaceAll("<","＜");
			rtn = rtn.replaceAll(">","＞");
			rtn = rtn.replaceAll("\\(","（");
			rtn = rtn.replaceAll("\\)","）");
			rtn = rtn.replaceAll("#","＃");
			rtn = rtn.replaceAll("\"","¨");
		}

      return rtn;
    }

    public static String replaceTag(String str)
    {
    	String rtn = str;

    	if( str == null ) {
    		rtn =  "";
		}else {

			rtn = rtn.replaceAll("(?i)alert","_alert_");
			rtn = rtn.replaceAll("(?i)background","_background_");
			rtn = rtn.replaceAll("(?i)cmd","_cmd_");
			rtn = rtn.replaceAll("(?i)cookie","_cookie_");
			rtn = rtn.replaceAll("(?i)document","_document_");
			rtn = rtn.replaceAll("(?i)exec","_exec_");
			rtn = rtn.replaceAll("(?i)iframe","_iframe_");
			rtn = rtn.replaceAll("(?i)applet","_applet_");
			rtn = rtn.replaceAll("(?i)meta","_meta_");
			rtn = rtn.replaceAll("(?i)object","_object_");
			rtn = rtn.replaceAll("(?i)onload","_onload_");
			rtn = rtn.replaceAll("(?i)script","_script_");
			rtn = rtn.replaceAll("(?i)style","_style_");
			rtn = rtn.replaceAll("(?i)write","_write_");
			rtn = rtn.replaceAll("(?i)javascript","_javascript_");
			rtn = rtn.replaceAll("(?i)embed","_embed_");
			rtn = rtn.replaceAll("(?i)onmouseover","_onmouseover_");
			rtn = rtn.replaceAll("(?i)&#83;","_X_"); //대문자 S
			rtn = rtn.replaceAll("(?i)&#115;","_x_"); //소문자 s
			rtn = rtn.replaceAll("(?i)&#","_"); //2009.11.15
			rtn = rtn.replaceAll("(?i)&＃","_"); //2009.11.15
		}
      return rtn;
    }


  	 //2010.01.21
    public static String replaceXssTag(String str)
    {
   	 String rtn = str;

   	 rtn = rtn.replaceAll( "-", "");
   	 rtn = rtn.replaceAll( "&", "");
   	 rtn = rtn.replaceAll( "<", "");
   	 rtn = rtn.replaceAll( ">", "");
   	 rtn = rtn.replaceAll( "'", "");
   	 rtn = rtn.replaceAll( "\\(", "");
   	 rtn = rtn.replaceAll( "\\)", "");
  		 return rtn;
    }


    public static String evl(String s, String s1)
    {
    	return s != null && !s.equals("") ? s : s1;
    }

    public static String cutStr(String str, int limit)
    {
   	 String rtn = "";
		if (str.length() <= limit)
		{
			rtn =  str;
		}else
		{
			rtn = (str.substring(0, limit) + "...");
		}
		return rtn;
    }

    public static String toHttp(HttpServletRequest request){
    	String siteName = request.getServerName();
    	int sitePort = request.getServerPort();

    	if(request.getRequestURL().indexOf("https://")>-1){
			// 찾으면
			if(sitePort==443){
				sitePort = 80;
			}
			else if (sitePort==443){
				sitePort = 81;
			}
		}

    	return "http://" + siteName + ":" + sitePort;
    }

    /**
     *<pre>
     * 인자로 받은 String이 null일 경우 &quot;0&quot;로 리턴한다.
     * &#064;param src null값일 가능성이 있는 String 값.
     * &#064;return 만약 String이 null 값일 경우 &quot;0&quot;로 바꾼 String 값.
     *</pre>
     */
    public static int zeroConvert(Object src) {
		if (src == null || src.equals("null") || src.equals("") ) {
		    return 0;
		} else {
		    return Integer.parseInt(((String)src).trim());
		}
    }

    /**
     *<pre>
     * 인자로 받은 String이 null일 경우 &quot;&quot;로 리턴한다.
     * &#064;param src null값일 가능성이 있는 String 값.
     * &#064;return 만약 String이 null 값일 경우 &quot;&quot;로 바꾼 String 값.
     *</pre>
     */
    public static int zeroConvert(String src) {
		if (src == null || src.equals("null") || "".equals(src) || " ".equals(src)) {
		    return 0;
		} else {
		    return Integer.parseInt(src.trim());
		}
    }


    /**
     * XSS 방지 처리.
     *
     * @param data
     * @return
     */
    public static String unscript(String data) {
        if (data == null || data.trim().equals("")) {
            return "";
        }

        String ret = data;

        ret = ret.replaceAll("<(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)", "&lt;script");
        ret = ret.replaceAll("</(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)", "&lt;/script");

        ret = ret.replaceAll("<(O|o)(B|b)(J|j)(E|e)(C|c)(T|t)", "&lt;object");
        ret = ret.replaceAll("</(O|o)(B|b)(J|j)(E|e)(C|c)(T|t)", "&lt;/object");

        ret = ret.replaceAll("<(A|a)(P|p)(P|p)(L|l)(E|e)(T|t)", "&lt;applet");
        ret = ret.replaceAll("</(A|a)(P|p)(P|p)(L|l)(E|e)(T|t)", "&lt;/applet");

        ret = ret.replaceAll("<(E|e)(M|m)(B|b)(E|e)(D|d)", "&lt;embed");
        ret = ret.replaceAll("</(E|e)(M|m)(B|b)(E|e)(D|d)", "&lt;embed");

        ret = ret.replaceAll("<(F|f)(O|o)(R|r)(M|m)", "&lt;form");
        ret = ret.replaceAll("</(F|f)(O|o)(R|r)(M|m)", "&lt;form");

        ret = ret.replaceAll("<(F|f)(O|o)(R|r)(M|m)", "&lt;iframe");
        ret = ret.replaceAll("</(F|f)(O|o)(R|r)(M|m)", "&lt;iframe");

        return ret;
    }

    /**
     * XSS 방지 처리. (Map일경우)
     *
     * @param data
     * @return
     */
    public static Map<String, Object> unscript(Map<String, Object> dataMap) {
    	if (null == dataMap || dataMap.isEmpty() || "".equals(dataMap)) {
        	return dataMap;
        }

    	Iterator<String> iter = dataMap.keySet().iterator();
    	while(iter.hasNext()){
        	String key = (String)iter.next();
        	dataMap.put(key, replaceTag(replaceXssTag(StringUtil.nvl(dataMap.get(key)))));
//        	System.out.println("!!!!!!!!!key:"+ key);
//        	System.out.println("!!!!!!!!!data:"+ StringUtil.nvl(dataMap.get(key)));
        }

        return dataMap;
    }

    /* 유효한 숫자인지 체크
     *
     */
    public static boolean isNumber(String str) {
    	boolean result = false;
    	try {
    		Double.parseDouble(str);
    		result = true;
    	} catch(Exception e){
    	}

    	return result;
    }
    
    public static String camelToLower(String name) {
    	return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, name);
    }
    
    public static String camelToUpper(String name) {
    	return CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, name);
    }

	/**
	 * input String 값을 split 을 이용해서 자르고, trim 하여 배열로 변환.
	 * 
	 * @param input
	 * @param splitDelemeter
	 * @return
	 */
	public static String[] splitStringTrim(String input, String splitDelemeter) {
		if ( input == null ) {
			return null;
		}
		String[] arr = tokenWork(input, splitDelemeter);
		for ( int i=0; i<arr.length; i++) {
			arr[i] = trim(arr[i]);
		}
		return arr;
	}
	
	/**
	 * 1byte 문자외는 2byte 문자로 계산해서 문자열 자르기.
	 * utf-8 하에서 동작한다.
	 * 
	 * @param str
	 * @param byteLength
	 * @return
	 */
	public static String subStringKO2Bytes(String str, int byteLength) {
		// String 을 byte 길이 만큼 자르기.

		int retLength = 0;
		int tempSize = 0;
		int asc;
		if (str == null || "".equals(str) || "null".equals(str)) {
			str = "";
		}

		int length = str.length();

		for (int i = 1; i <= length; i++) {
			asc = (int) str.charAt(i - 1);
			if (asc > 127) {
				if (byteLength >= tempSize + 2) {
					tempSize += 2;
					retLength++;
				} else {
					return str.substring(0, retLength) ;
				}
			} else {
				if (byteLength > tempSize) {
					tempSize++;
					retLength++;
				}
			}
		}

		return str.substring(0, retLength);
	}
	
	public static int getByteLen(String value) {
		int byteLen = 0;
		if(StringUtil.isEmpty(value)) {
			return byteLen;
		}
		int asc;
		int length = value.length();
		for(int i=0; i<length; i++) {
			asc = (int)value.charAt(i);
			if(asc > 127) {
				byteLen += 2;
			} else {
				byteLen++;
			}
		}
		return byteLen;
	}
	
	public static boolean isMinByte(String value, int minByte) {
		if(StringUtil.isEmpty(value)) {
			return (minByte<1);
		}
		boolean isValid = false;
		int byteLen = 0;
		int asc;
		int length = value.length();
		for(int i=0; i<length; i++) {
			asc = (int)value.charAt(i);
			if(asc > 127) {
				byteLen += 2;
			} else {
				byteLen++;
			}
			if(byteLen >= minByte) {
				isValid = true;
				break;
			}
		}
		return isValid;
	}
	
	public static boolean isMaxByte(String value, int maxByte) {
		if(StringUtil.isEmpty(value)) {
			return (maxByte<1);
		}
		boolean isValid = true;
		int byteLen = 0;
		int asc;
		int length = value.length();
		for(int i=0; i<length; i++) {
			asc = (int)value.charAt(i);
			if(asc > 127) {
				byteLen += 2;
			} else {
				byteLen++;
			}
			if(byteLen > maxByte) {
				isValid = false;
				break;
			}
		}
		return isValid;
	}

}