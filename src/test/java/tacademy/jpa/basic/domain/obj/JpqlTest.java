package tacademy.jpa.basic.domain.obj;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Transactional
class JpqlTest {

    @DisplayName("JQPL 테스트")
    @Test
    void jpqlTest() {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        try {
            final Member memberA = new Member(1L, null, "Member A");
            em.persist(memberA); // 영속 상태
            final Member memberB = new Member(2L, null, "Member B");
            em.persist(memberB);
            final Member memberC = new Member(3L, null, "C");
            em.persist(memberC);

            em.flush();
            em.clear();

            final String jpql = "SELECT m FROM Member m WHERE m.name LIKE '%Member%'";
            final List<Member> members = em.createQuery(jpql, Member.class)
                    .getResultList();

            assertThat(members).hasSize(2);

            transaction.commit();
        } catch (final Exception e) {
            System.out.println(e.getMessage());
            transaction.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    @DisplayName("페이징 테스트")
    @Test
    void pagingTest() {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        try {

            for (long i = 0; i < 40; i++) {
                em.persist(new Member(i, null, "Member " + i));
            }

            em.flush();
            em.clear();

            final String jpql = "SELECT m FROM Member m ORDER BY m.name DESC";
            final List<Member> members = em.createQuery(jpql, Member.class)
                    .setFirstResult(0)
                    .setMaxResults(20)
                    .getResultList();

            assertThat(members).hasSize(20);

            transaction.commit();
        } catch (final Exception e) {
            System.out.println(e.getMessage());
            transaction.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    @DisplayName("페치조인 테스트")
    @Test
    void fetchJoinTest() {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        try {
            final Team teamA = new Team(1L, "TEAM A");
            final Team teamB = new Team(2L, "TEAM B");
            em.persist(teamA);
            em.persist(teamB);
            final Member memberA = new Member(1L, teamA, "MEMBER A");
            final Member memberB = new Member(2L, teamB, "MEMBER B");

            em.persist(memberA);
            em.persist(memberB);

            em.flush();
            em.clear();

            final String jpql = "SELECT m FROM Member m JOIN FETCH m.team";
            final List<Member> members = em.createQuery(jpql, Member.class)
                    .getResultList();

            assertThat(members).hasSize(2);

            transaction.commit();
        } catch (final Exception e) {
            System.out.println(e.getMessage());
            transaction.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    @DisplayName("NamedQuery 테스트")
    @Test
    void namedQueryTest() {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        try {
            final Team teamA = new Team(1L, "TEAM A");
            final Team teamB = new Team(2L, "TEAM B");
            em.persist(teamA);
            em.persist(teamB);
            final Member memberA = new Member(1L, teamA, "MEMBER A");
            final Member memberB = new Member(2L, teamB, "MEMBER B");

            em.persist(memberA);
            em.persist(memberB);

            em.flush();
            em.clear();

            final Member findMember = em.createNamedQuery("Member.findByName", Member.class)
                    .setParameter("name", "MEMBER A")
                    .getSingleResult();

            assertThat(findMember.getName()).isEqualTo("MEMBER A");

            transaction.commit();
        } catch (final Exception e) {
            System.out.println(e.getMessage());
            transaction.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

}
