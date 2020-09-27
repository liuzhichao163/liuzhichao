package com.primeton.liuzhichao.demo.utils;

import org.springframework.beans.BeanUtils;

/**
 * @ClassName MyBeanUtils
 * @Description TODO
 * @Author liuzhichao
 * @Date 2020/8/7 17:07
 * @Version 1.0
 */
public class MyBeanUtils {


    public static <Vo> Vo doToVo(Object doEntity, Class<Vo> voClass) {
        // 判断do是否为空!
        if (doEntity == null) {
            return null;
        }
        // 判断Dlass 是否为空
        if (voClass == null) {
            return null;
        }
        try {
            Vo newInstance = voClass.newInstance();
            BeanUtils.copyProperties(doEntity, newInstance);
            // Dto转换Do
            return newInstance;
        } catch (Exception e) {
            return null;
        }
    }


    public static <Dto> Dto voToDto(Object voEntity, Class<Dto> dtoClass) {
        // 判断VoSF是否为空!
        if (voEntity == null) {
            return null;
        }
        // 判断DtoClass 是否为空
        if (dtoClass == null) {
            return null;
        }
        try {
            Dto newInstance = dtoClass.newInstance();
            BeanUtils.copyProperties(voEntity, newInstance);
            // Dto转换Do
            return newInstance;
        } catch (Exception e) {
            return null;
        }
    }
}
