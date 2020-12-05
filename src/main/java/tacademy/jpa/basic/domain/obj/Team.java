package tacademy.jpa.basic.domain.obj;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Team {
    @Id
    private Long id;

    private String name;

    public Team() {
    }

    public Team(final Long id, final String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
