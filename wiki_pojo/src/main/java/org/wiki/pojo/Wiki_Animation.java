package org.wiki.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName("wiki_animation")
@Data
public class Wiki_Animation implements Serializable {
    @TableId(value = "animation_id",type = IdType.AUTO)
    private Integer animationId;
    private String animationName;
    private String animationLabel;
    private Date animationTime;
    private String animationImg;
    private String animationLink;
    private String animationVoiceactor;
    private Integer animationStatus;
    private String animationIntroduce;
    private String animationStaff;
}
