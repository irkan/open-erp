package az.sufilter.bpm.util;

public class Util {
    public static Object check(Object object){
        try{
            if(object!=null)
                return object;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
