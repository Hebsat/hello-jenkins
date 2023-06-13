package org.example.application.repositories;

import lombok.RequiredArgsConstructor;
import org.example.application.exceptions.IncorrectDataException;
import org.example.application.model.Position;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PositionsRepository {

    private final SessionFactory sessionFactory;

    public List<Position> findAll() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            List<Position> positions = session.createQuery("FROM Position", Position.class).getResultList();
            transaction.commit();
            return positions;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Optional<Position> findByName(String name) {
        Transaction transaction = null;
        Optional<Position> optionalEmployee = Optional.empty();
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            Query query = (Query) session.createQuery("FROM Position WHERE name = :name", Position.class);
            query.setParameter("name", name);
            Position position = (Position) query.getSingleResult();
            transaction.commit();
            optionalEmployee = Optional.of(position);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return optionalEmployee;
    }

    public boolean save(Position position) throws IncorrectDataException {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.persist(position);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new IncorrectDataException("position with name " + position.getName() + " is already exists");
        }
    }

    public boolean update(String lastName, String newName) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            Query query = (Query) session.createQuery("FROM Position WHERE name = :name", Position.class);
            query.setParameter("name", lastName);
            Position position = (Position) query.getSingleResult();
            position.setName(newName);
            session.persist(position);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Position position) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.remove(position);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }
}
