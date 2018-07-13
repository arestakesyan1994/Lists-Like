package am.listy.backend.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Data
@AllArgsConstructor
@Builder
@Document(collection = "listings")
public class Lists {
    @Id
    private String id;
    private String title;
    private Category category;
    private String description;
    private String togs;
    private Region region;
    private User user;
    private Date date;
    private String phone;
    private String website;
    private String picUrl;
    private String videoUrl;
    private String ms;
    private HashSet<User> like;
    private Rating rating;
    private Comment comment;

    public Lists(){
        like = new HashSet<User>();
    }

    public Lists(HashSet<User> id) {
    }
}
