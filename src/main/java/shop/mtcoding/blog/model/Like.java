package shop.mtcoding.blog.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Like {
    private int id;
    private int boardId;
    private int userId;
    private int likeNum;
}