package shop.mtcoding.blog.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Board {
    private int id;
    private String title;
    private String content;
    private int userId;
}
