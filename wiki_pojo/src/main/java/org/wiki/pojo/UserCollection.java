package org.wiki.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName("wiki_collection")
@Data
public class UserCollection implements Serializable {
//    动漫Id
    private Integer animationId;
//    用户id
    private Integer userId;
//    动漫名称
    private String animationName;
//    动漫图片
    private String animationImg;

    public UserCollection(Integer animationId, Integer userId, String animationName, String animationImg) {
        this.animationId = animationId;
        this.userId = userId;
        this.animationName = animationName;
        this.animationImg = animationImg;
    }
}
