package com.primeton.liuzhichao.demo.vo;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.primeton.liuzhichao.demo.entity.Role;
import com.primeton.liuzhichao.demo.entity.User;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName UserVo
 * @Description TODO
 * @Author liuzhichao
 * @Date 2020/8/7 16:34
 * @Version 1.0
 */
@Data
public class UserVo implements Serializable {

    private static final long serialVersionUID = -6438122922334303265L;

//    @JsonIgnore
    private Integer id;
    private String userId;
    private String name;
    @JsonIgnore
    private String password;
    private String job;
    private String mgrId;
    private String orgId;
    @JsonIgnore
    private String newPassword;
    @JsonIgnore
    private String oldPassword;
    @JsonIgnore
    private String userFace;
    private String httpUserFace;
    @JsonIgnore
    private List<Role> roles;
}
