package org.example.application.repositories;

import lombok.RequiredArgsConstructor;
import org.example.application.exceptions.IncorrectDataException;
import org.example.application.model.Project;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectsRepository {

    private final SessionFactory sessionFactory;

    public List<Project> findAll() {
        try (Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            List<Project> positions = session.createQuery("FROM Project", Project.class).getResultList();
            transaction.commit();
            return positions;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Optional<Project> findByName(String name) {
        Optional<Project> optionalEmployee = Optional.empty();
        try (Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            Query query = (Query) session.createQuery("FROM Project WHERE name = :name", Project.class);
            query.setParameter("name", name);
            Project project = (Project) query.getSingleResult();
            transaction.commit();
            optionalEmployee = Optional.of(project);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return optionalEmployee;
    }

    public boolean save(Project project) throws IncorrectDataException {
        try (Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            session.persist(project);
            transaction.commit();
            return true;
        } catch (Exception e) {
            throw new IncorrectDataException("position with name " + project.getName() + " is already exists");
        }
    }

    public boolean update(String lastName, String newName) {
        try (Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            Query query = (Query) session.createQuery("FROM Project WHERE name = :name", Project.class);
            query.setParameter("name", lastName);
            Project project = (Project) query.getSingleResult();
            project.setName(newName);
            session.persist(project);
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Project project) {
        try (Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            session.remove(project);
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
