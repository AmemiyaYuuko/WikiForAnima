package org.wiki.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName("wiki_report")
@Data
public class Wiki_Report implements Serializable {
    private Integer userId;
    private Integer animationId;
    private String reportReason;
    private Integer status;
}
