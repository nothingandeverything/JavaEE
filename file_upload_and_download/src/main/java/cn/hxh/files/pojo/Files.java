package cn.hxh.files.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

/**
 * (Files)实体类
 *
 * @author makejava
 * @since 2020年12月13日22:29:26
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Files implements Serializable {

    private Integer id;
    
    private String oldfilename;
    
    private String newfilename;
    
    private String ext;
    
    private String path;
    
    private Long size;
    
    private String type;
    
    private String isimg;
    
    private Integer downcounts;
    
    private Date uploadTime;
    
    private Integer userId;

	


}