package com.rabbit.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @author gyc
 * @date 2019/11/2
 */
@Data
public class RabbitLog implements Serializable {

  @TableId(type = IdType.AUTO)
  private Long id;
  private String type;
  private String entityClass;
  private String entity;
  private String entityId;

  private String status;

}
