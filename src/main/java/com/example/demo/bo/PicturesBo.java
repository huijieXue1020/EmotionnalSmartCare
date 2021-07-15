package com.example.demo.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
public class PicturesBo {

    private String type;
    private MultipartFile[] pictures;
    private String token;
}
