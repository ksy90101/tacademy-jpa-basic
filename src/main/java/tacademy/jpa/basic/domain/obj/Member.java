package tacademy.jpa.basic.domain.obj;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Member {
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    private String name;

    public Member() {
    }

    public Member(final Long id, final Team team, final String name) {
        this.id = id;
        this.team = team;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public Team getTeam() {
        return team;
    }

    public String getName() {
        return name;
    }
}
