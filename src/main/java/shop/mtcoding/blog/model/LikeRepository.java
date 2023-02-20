package shop.mtcoding.blog.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import shop.mtcoding.blog.dto.like.LikeReq.LikeReqDto;

@Mapper
public interface LikeRepository {

    public List<Like> findAll();

    public int insert(LikeReqDto likeReqDto);

    public int findLike();

    public Like findByUserIdAndBoardId(@Param("userId") int userId, @Param("boardId") int boardId);

    public Like findByLikeNum(int id);

    public int deleteById(int id);
}
