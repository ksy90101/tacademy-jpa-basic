package tacademy.jpa.basic.domain.obj;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberTest {

    @DisplayName("객체에 맞춘 방식")
    @Test
    void SaveAndFindForObj() {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        try {
            final Team team = new Team(1L, "Team A");
            em.persist(team);
            final Member member = new Member(1L, team, "Member A");
            em.persist(member);

            em.flush();
            em.clear();

            final Member findMember = em.find(Member.class, member.getId());

            assertAll(
                    () -> assertThat(findMember.getId()).isEqualTo(member.getId()),
                    () -> assertThat(findMember.getTeam().getId()).isEqualTo(member.getTeam().getId()),
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

    @DisplayName("동등성 체크")
    @Test
    void identityTest() {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        try {
            final Team team = new Team(1L, "Team A");
            em.persist(team);
            final Member memberA = new Member(1L, team, "Member A");
            em.persist(memberA);

            final Member findMemberA = em.find(Member.class, memberA.getId());

            assertThat(memberA).isEqualTo(findMemberA);

            transaction.commit();
        } catch (final Exception e) {
            transaction.rollback();
        } finally {
            em.close();
        }

        emf.close();

    }

    @DisplayName("업데이트 테스트")
    @Test
    void updateTest() {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        try {
            final Team team = new Team(1L, "Team A");
            em.persist(team);
            final Member memberA = new Member(1L, team, "Member A");
            em.persist(memberA);

            em.flush();
            em.clear();

            final Member findMember = em.find(Member.class, memberA.getId());
            findMember.setName("Member B");

            em.flush();
            em.clear();

            final Member findMember2 = em.find(Member.class, memberA.getId());

            assertThat(findMember2.getName()).isEqualTo("Member B");

            transaction.commit();
        } catch (final Exception e) {
            transaction.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    @DisplayName("양방향 테스트")
    @Test
    void bidirectionalTest() {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        try {
            final Team team = new Team(1L, "Team A");
            em.persist(team);
            final Member memberA = new Member(1L, team, "Member A");
            em.persist(memberA);
            final Member memberB = new Member(1L, team, "Member B");
            em.persist(memberB);

            em.flush();
            em.clear();

            final Team findTeam = em.find(Team.class, team.getId());

            assertThat(findTeam.getMembers()).hasSize(2);

            transaction.commit();
        } catch (final Exception e) {
            transaction.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    @DisplayName("양방향 실수 테스트")
    @Test
    void bidirectionalNotActionTest() {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        try {
            final Team team = new Team(1L, "Team A");
            em.persist(team);
            final Member memberA = new Member(1L, null, "Member A");
            em.persist(memberA);

            team.getMembers().add(memberA);

            em.flush();
            em.clear();
            final Member findMember = em.find(Member.class, memberA.getId());

            assertThat(findMember.getTeam()).isNull();
            transaction.commit();
        } catch (final Exception e) {
            transaction.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    @DisplayName("양방향 실수 해결 테스트")
    @Test
    void bidirectionalNotActionSolutionTest() {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        try {
            final Team team = new Team(1L, "Team A");
            em.persist(team);
            final Member memberA = new Member(1L, null, "Member A");
            em.persist(memberA);

            team.updateMember(memberA);
            em.flush();
            em.clear();
            final Member findMember = em.find(Member.class, memberA.getId());

            assertThat(findMember.getTeam()).isNotNull();
            transaction.commit();
        } catch (final Exception e) {
            transaction.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
