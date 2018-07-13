package am.listy.backend.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "rating")
public class Rating {
    @Id
    private String id;
    private int count;
    private User user;
    private Lists lists;
    private double account;

    public double getAccount() {
        return account;
    }
}
