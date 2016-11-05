package function;

import java.util.List;


/**
 * Created by vanthi on 10/28/2016.
 */
/* this String is: $abc#cde#cdc$
    This class use for processing type string predefined by GANT
*/
public class StringHanding {

    private static String listIgnoreChar = "#$'";
    //convert from $abc#cde#cdc$ to array String
    public static String[] getArrayStr(String str) {
        if (str.equals("")) return null;
        int m = 1;
        int p = 1;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '#') p++;
        }
        String[] buf = new String[p];
        int[] temp = new int[p + 1];
        temp[0] = 0;
        temp[p] = str.length() - 1;
        if (str.startsWith("$") && str.endsWith("$")) {
            for (int i = 1; i < str.length() - 1; i++) {
                if (str.charAt(i) == '#') {
                    temp[m] = i;
                    m++;
                }
            }
            for (int i = 0; i < p; i++) {
                if (temp[i] + 1 == temp[i + 1]) buf[i] = null;
                else buf[i] = str.substring(temp[i] + 1, temp[i + 1]);
            }
            return buf;
        } else return null;
    }

    //convert from array string to $abc#cde#cdc$
    public static String getStr(String[] list) {
        if (list == null) return "";
        String temp = "$";
        for (String val : list) {
            if (val != null) temp = temp + val + "#";
            else temp += "#";
        }
        String p = temp.substring(0, temp.length() - 1);
        p += "$";
        return p;
    }

    //convert from list string to $abc#cde#cdc$
    public static String getStr(List<String> list) {
        if (list.isEmpty()) return "";
        String temp = "$";
        for (String val : list) {
            if (val != null) temp = temp + val + "#";
            else temp += "#";
        }
        String p = temp.substring(0, temp.length() - 1);
        p += "$";
        return p;
    }

    //convert from array field and array value to array type $field#value$
    public static String[] getArrayListStr(List<String[]> data) {
        if (data.isEmpty()) return null;
        String[] temp = new String[data.size()];
        int i = 0;
        for (String[] var : data) {
            temp[i] = getStr(var);
            i++;
        }
        return temp;
    }
    /*public static String[] setStrProcess(String[] field,String[] value){
        if (field.length != value.length) return null;
        String[] temp = new String[field.length];
        String[] p = new String[2];
        for (int i=0;i<field.length;i++){
            p[0] = field[i];
            p[1] = value[i];
            temp[i] = setStrProcess(p);
        }
        return temp;
    }
    public static String[] setStrProcess(List<String> field,List<String> value){
        if (field.size() != value.size()) return null;
        String[] temp = new String[field.size()];
        String[] p = new String[2];
        for (int i=0;i<field.size();i++){
            p[0] = field.get(i);
            p[1] = value.get(i);
            temp[i] = setStrProcess(p);
        }
        return temp;
    }*/

    //check String method

    //check is valid string: it's String that hasn't special character ($,#,...)
    public static Boolean isValidString(String s){
        for (int i=0;i<s.length();i++) {
            for (int j=0;j<listIgnoreChar.length();j++)
                if (s.charAt(i) == listIgnoreChar.charAt(j) || s.charAt(i) == 34 || s.charAt(i) == 92) return false;
        }
        return true;
    }
    //check is valid phone number: phone number must more than 9 char (can start 0 or +(area number))
    public static Boolean isValidPhone(String phoneNum){
        if (phoneNum.length() <= 9 ) return false;
        if (!(phoneNum.startsWith("+") || phoneNum.startsWith("0"))){
            return false;
        }
        for (int i=1;i<phoneNum.length();i++){
            if (phoneNum.charAt(i) < '0' || phoneNum.charAt(i) > '9') return false;
        }
        return true;
    }
    //auto fix unvalid string to valid string
    //exam string is "cddvdv#$" -> "cddvdv"
    public static String getValidString(String s){
        if (isValidString(s)) return s;
        else{
            for (int i=0;i<s.length();i++) {
                for (int j=0;j<listIgnoreChar.length();j++)
                    if (s.charAt(i) == listIgnoreChar.charAt(j) || s.charAt(i) == 34 || s.charAt(i) == 92) {
                        s = s.substring(0,i) + s.substring(i+1);
                        break;
                    }
            }
            return s;
        }
    }
}