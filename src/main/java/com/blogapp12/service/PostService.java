package com.blogapp12.service;

import com.blogapp12.payload.ListPostDto;
import com.blogapp12.payload.PostDto;

public interface PostService {
    public PostDto createPost(PostDto postdto);

    void deletePost(long id);

    ListPostDto fetchAllPost(int pageNo, int pageSize, String sortBy, String sortDir);


    public PostDto getPostById(long id);
}
