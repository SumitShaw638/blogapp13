package com.blogapp12.service.impl;



import com.blogapp12.entity.Post;
import com.blogapp12.exception.ResourceNotFound;
import com.blogapp12.payload.ListPostDto;
import com.blogapp12.payload.PostDto;
import com.blogapp12.repository.PostRepository;
import com.blogapp12.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;

    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository,ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper=modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postdto) {
    Post post=mapToEntity(postdto);

       Post savedPost=postRepository.save(post);
       PostDto dto=mapToDto(savedPost);


        return dto;
    }

    @Override
    public void deletePost(long id) {
        postRepository.deleteById(id);
    }

    public PostDto getPostById(long id){
        Post post = postRepository.findById(id).orElseThrow(
                ()->new ResourceNotFound("Post Not Found with Id:"+id)
        );
        return mapToDto(post);

    }

    @Override
    public ListPostDto fetchAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort= sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();

        Pageable pageable=PageRequest.of(pageNo,pageSize, sort);
        Page<Post> all = postRepository.findAll(pageable);

        List<Post> post=all.getContent();
        List<PostDto> postDtos=post.stream().map(e->mapToDto(e)).collect(Collectors.toList());
        ListPostDto listPostDto=new ListPostDto();
        listPostDto.setPostDto(postDtos);
        listPostDto.setTotalPages(all.getTotalPages());
        listPostDto.setTotalElemets((int)all.getTotalElements());
        listPostDto.setFirstPage(all.isFirst());
        listPostDto.setLastPage(all.isLast());
        listPostDto.setPageNumber(all.getNumber());
        return listPostDto;
    }

    Post mapToEntity(PostDto postdto){
       Post post= modelMapper.map(postdto,Post.class);
        return  post;
    }

    PostDto mapToDto(Post post){

        PostDto dto=modelMapper.map(post,PostDto.class);
        return dto;
    }
}
