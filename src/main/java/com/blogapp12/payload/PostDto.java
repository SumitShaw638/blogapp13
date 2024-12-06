package com.blogapp12.payload;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDto {
    private long id;
    @NotEmpty
    @Size(min=3,message = "Title should be minimum 3 characters")
    private String title;
    @NotEmpty
    @Size(min=3,message = "Description should be minimum 3 characters")
    private String description;
    private String content;



}
