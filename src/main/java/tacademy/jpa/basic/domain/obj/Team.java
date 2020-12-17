package tacademy.jpa.basic.domain.obj;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Team {
    @OneToMany(mappedBy = "team")
    private final List<Member> members = new ArrayList<>();
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

    public void updateMember(final Member member) {
        member.setTeam(this);
        members.add(member);
    }

    public List<Member> getMembers() {
        return members;
    }
}
