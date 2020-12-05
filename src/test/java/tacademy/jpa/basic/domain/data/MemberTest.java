package tacademy.jpa.basic.domain.data;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberTest {

    @DisplayName("테이블에 맞춘 방식")
    @Test
    void SaveAndFindForTable() {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        try {
            final Team team = new Team();
            team.setId(1L);
            team.setName("Team A");
            em.persist(team);
            final Member member = new Member();
            member.setId(1L);
            member.setTeamId(1L);
            member.setName("Member A");
            em.persist(member);

            em.flush();
            em.clear();

            final Member findMember = em.find(Member.class, member.getId());

            assertAll(
                    () -> assertThat(findMember.getId()).isEqualTo(member.getId()),
                    () -> assertThat(findMember.getTeamId()).isEqualTo(member.getTeamId()),
                    () -> assertThat(findMember.getName()).isEqualTo(member.getName())
            );

            transaction.commit();
        } catch (final Exception e) {
            transaction.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

}
