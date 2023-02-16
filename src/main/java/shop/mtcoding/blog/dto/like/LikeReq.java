package shop.mtcoding.blog.dto.like;

import lombok.Getter;
import lombok.Setter;

public class LikeReq {

    @Setter
    @Getter
    public static class LikeReqDto {
        private int userId;
        private int boardId;
        private int likeNum;
    }
}
