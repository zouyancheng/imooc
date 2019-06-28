package com.imooc.utils;

import com.imooc.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;

import java.text.SimpleDateFormat;

@Slf4j
public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();
    static {
        //对象所有字段都列入
        objectMapper.setSerializationInclusion(Inclusion.ALWAYS);
        //取消默认的timestamp转换
        objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS,false);
        //忽略空beans转换json出错
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS,false);
        //所有日期统一转换成以下格式
        objectMapper.setDateFormat(new SimpleDateFormat(DateTimeUtil.STANDARD_FORMAT));
        //忽略在json字符串中存在但是在java对象中不存在的错误
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,false);
    }

    public  static <T> String obj2String(T obj){
        if(obj == null){
            return  null;
        }
        try{
            return  obj instanceof  String ? (String)obj : objectMapper.writeValueAsString(obj);
        }catch (Exception e){
            log.warn("parse object to string error",e);
            return  null;
        }
    }

    public static <T> String obj2StringPretty(T obj){
        if(obj ==null){
            return null;
        }
        try{
            return  obj instanceof  String ? (String)obj : objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        }catch (Exception e){
            log.warn("parse object to string error",e);
            return  null;
        }
    }

    public static <T> T string2Obj(String str,Class<T> clazz){
        if(StringUtils.isEmpty(str) || clazz == null){
            return null;
        }

        try {
            return clazz.equals(String.class)? (T)str : objectMapper.readValue(str,clazz);
        } catch (Exception e) {
            log.warn("Parse String to Object error",e);
            return null;
        }
    }

    public static <T> T string2Obj(String str, TypeReference<T> typeReference){
        if(StringUtils.isEmpty(str) || typeReference == null){
            return null;
        }
        try {
            return (T)(typeReference.getType().equals(String.class)? str : objectMapper.readValue(str,typeReference));
        } catch (Exception e) {
            log.warn("Parse String to Object error",e);
            return null;
        }
    }


    public static <T> T string2Obj(String str,Class<?> collectionClass,Class<?>... elementClasses){
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass,elementClasses);
        try {
            return objectMapper.readValue(str,javaType);
        } catch (Exception e) {
            log.warn("Parse String to Object error",e);
            return null;
        }
    }

    public static void main(String[] args) {
        User u1 = new User();
        u1.setId(1);
        u1.setEmail("9999999");
        String userJson = JsonUtil.obj2String(u1);
        String userJsonPretty = JsonUtil.obj2StringPretty(u1);
        log.info("userJson:{}",userJson);
        log.info("userJsonPretty:{}",userJsonPretty);
        //List<User> userListObj1 = JsonUtil.string2Obj(userListStr, new TypeReference<List<User>>() {
//        });
//
//
//        List<User> userListObj2 = JsonUtil.string2Obj(userListStr,List.class,User.class);
    }

}
