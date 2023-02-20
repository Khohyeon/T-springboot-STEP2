package shop.mtcoding.blog.dto.board;

import lombok.Getter;
import lombok.Setter;

public class BoardResp {

    @Setter
    @Getter
    public static class BoardMainRespDto {
        private int id;
        private String title;
        private String thumbnail;
        private String username;
        private int likeNum;

    }

    @Setter
    @Getter
    public static class BoardDetailRespDto {
        private int id;
        private String username;
        private String title;
        private int userId;
        private String content;
        private int likeNum;

    }

    @Setter
    @Getter
    public static class BoardLikeCountRespDto {
        private int id;
        private String title;
        private String thumbnail;
        private String username;
        private int likeCount;
    }

}
