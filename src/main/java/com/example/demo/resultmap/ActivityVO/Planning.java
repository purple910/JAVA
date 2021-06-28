package com.example.demo.resultmap.ActivityVO;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Planning {

    private String content;

    private Integer planningIndex;


    private List<String> thumbs;

    private Integer thumbsNumber;

    private Date startTime;

    private Date endTime;

    private Integer commentNumber;

    private List<Resource> resourceList;

}
