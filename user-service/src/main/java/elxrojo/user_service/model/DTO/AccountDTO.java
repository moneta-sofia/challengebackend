package elxrojo.user_service.model.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDTO {
    private Long id;
    private String name;
    private Float balance;
    private String alias;
    private String cvu;
    private Long userId;

    public AccountDTO(String name, String alias, String cvu, Long userId) {
        this.name = name;
        this.alias = alias;
        this.cvu = cvu;
        this.userId = userId;
    }

    public AccountDTO() {
    }

    public AccountDTO(Long id, String name, String alias, String cvu, Long userId) {
        this.id = id;
        this.name = name;
        this.alias = alias;
        this.cvu = cvu;
        this.userId = userId;
    }
}
