package shop.mtcoding.blog.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import shop.mtcoding.blog.dto.like.LikeReq.LikeReqDto;

@Mapper
public interface LikeRepository {

    public List<Like> findAll();

    public int insert(LikeReqDto likeReqDto);

    public int findLike();
}
