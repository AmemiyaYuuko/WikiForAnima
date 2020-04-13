package org.wiki.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

//对应历史版本的表，主要给后台管理员回滚进行使用
@Data
@TableName("wiki_history")
public class AnimationHistory implements Serializable {
    @TableId(value = "history_id",type = IdType.AUTO)
    private Integer historyId;
    private Date changeTime;
    private Integer userId;
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


    public AnimationHistory(Integer animationId, String animationName, String animationLabel,
                            Date animationTime, String animationImg, String animationLink,
                            String animationVoiceactor, String animationIntroduce,
                            String animationStaff, Integer userId, Date date) {
        this.historyId=0;
        this.animationId=animationId;
        this.animationName=animationName;
        this.animationLabel=animationLabel;
        this.animationTime=animationTime;
        this.animationImg=animationImg;
        this.animationLink=animationLink;
        this.animationVoiceactor=animationVoiceactor;
        this.animationIntroduce=animationIntroduce;
        this.animationStaff=animationStaff;
        this.userId=userId;
        this.changeTime=date;
    }
}
