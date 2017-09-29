package io.nuls.util.param;

import io.nuls.exception.NulsException;
import io.nuls.util.constant.ErrorCode;
import org.springframework.util.StringUtils;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

/**
 * Created by Niels on 2017/9/29.
 * nuls.io
 */
public abstract class AssertUtil {

    public static void canNotEmpty(Object val,String msg){
        boolean b = false;
        do{
            if(null==val){
                b = true;
                break;
            }
            if(val instanceof String){
                b = StringUtils.isEmpty(val+"");
                break;
            }
            if(val instanceof List){
                b = ((List)val).isEmpty();
                break;
            }
            if(val instanceof Map){
                b = ((Map)val).isEmpty();
                break;
            }
            if(val instanceof String[]){
                b = ((String[])val).length==0;
                break;
            }
            if(val instanceof byte[]){
                b = ((byte[])val).length==0;
                break;
            }
        }while (false);
        if(b){
            throw new NulsException(ErrorCode.NULL_PARAMETER.getCode(),msg);
        }
    }
}
