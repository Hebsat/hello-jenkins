package org.example.application.repositories;

import lombok.RequiredArgsConstructor;
import org.example.application.model.Employee;
import org.example.application.model.Position;
import org.example.application.model.Project;
import org.example.application.model.links.Employee2Project;
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
public class EmployersRepository {

    private final SessionFactory sessionFactory;

    public List<Employee> findAll() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            List<Employee> employees = session.createQuery("FROM Employee", Employee.class).getResultList();
            transaction.commit();
            return employees;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Optional<Employee> findById(int id) {
        Transaction transaction = null;
        Optional<Employee> optionalEmployee = Optional.empty();
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            Query query = (Query) session.createQuery("FROM Employee WHERE id = :id", Employee.class);
            query.setParameter("id", id);
            Employee employee = (Employee) query.getSingleResult();
            transaction.commit();
            optionalEmployee = Optional.of(employee);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return optionalEmployee;
    }

    public boolean save(Employee employee) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.persist(employee);
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

    public boolean update(int id, String firstName, String lastName, Position position, List<Project> projects) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            Query query = (Query) session.createQuery("FROM Employee WHERE id = :id", Employee.class);
            query.setParameter("id", id);
            Employee employee = (Employee) query.getSingleResult();
            if (firstName != null) {
                employee.setFirstName(firstName);
            }
            if (lastName != null) {
                employee.setLastName(lastName);
            }
            if (position != null) {
                employee.setPosition(position);
            }
            if (projects != null) {
                employee.setProjects(projects);
            }
            session.persist(employee);
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

    public boolean delete(Employee employee) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.remove(employee);
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

    public boolean deleteAssigningToProject(Employee employee, Project project) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            Query query = (Query) session.createQuery("FROM Employee2Project WHERE employeeId = :employeeId AND projectId = :projectId", Employee2Project.class);
            query.setParameter("employeeId", employee.getId());
            query.setParameter("projectId", project.getId());
            Employee2Project employee2Project = (Employee2Project) query.getSingleResult();
            session.remove(employee2Project);
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
