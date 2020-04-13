package org.wiki.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName("wiki_user")
@Data
public class User implements Serializable {
    @TableId(value = "user_id",type = IdType.AUTO)
    private Integer id;
    @TableField(value = "user_name")
    private String name;
    @TableField(value = "user_password")
    private String password;
    @TableField(value = "user_age")
    private Integer age;
    @TableField(value = "user_sex")
    private String sex;
    @TableField(value = "user_email")
    private String email;
    @TableField(value = "user_status")
    private Integer status;
    @TableField(value = "user_img")
    private String img;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User(Integer id, Integer status) {
        this.id = id;
        this.status = status;
    }

    public User(Integer id, String name, String password, Integer age, String sex, String email, Integer status, String img) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.age = age;
        this.sex = sex;
        this.email = email;
        this.status = status;
        this.img = img;
    }

}
