package tacademy.jpa.basic.domain.obj;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(
        name = "Member.findByName",
        query = "SELECT m FROM Member m WHERE m.name = :name"
)
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

    public void setTeam(final Team team) {
        this.team = team;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
