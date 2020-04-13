package org.wiki.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName("wiki_admin")
@Data
public class Admin implements Serializable {
    @TableId(value = "manager_id", type = IdType.AUTO)
    private Integer id;
    @TableField(value = "manager_name")
    private String name;
    @TableField(value = "manager_password")
    private String password;
}
