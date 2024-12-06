package com.blogapp12.entity;

import javax.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String description;
    private String content;

    @OneToMany(mappedBy = "post",orphanRemoval = true,cascade = CascadeType.ALL)
    private List<Comment> comments=new ArrayList<>();




}
