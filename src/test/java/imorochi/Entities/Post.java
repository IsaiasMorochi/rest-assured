package imorochi.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post implements Serializable {
    private Integer userId;
    private Integer id;
    private String title;
    private String body;
}
