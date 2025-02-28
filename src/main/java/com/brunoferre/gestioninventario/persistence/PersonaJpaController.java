package com.brunoferre.gestioninventario.persistence;

import com.brunoferre.gestioninventario.logica.JPAUtil;
import com.brunoferre.gestioninventario.logica.Persona;
import com.brunoferre.gestioninventario.logica.SesionPersona;
import com.brunoferre.gestioninventario.persistence.exceptions.NonexistentEntityException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class PersonaJpaController implements Serializable {

//    public PersonaJpaController(EntityManagerFactory emf) {
//        this.emf = emf;
//    }
//
//    public PersonaJpaController() {
//        emf = Persistence.createEntityManagerFactory("PersistenceMYSQL");
//    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return JPAUtil.getMysqlEntityManager();
    }

    public void create(Persona persona) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(persona);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Persona persona) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            persona = em.merge(persona);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = persona.getId();
                if (findPersona(id) == null) {
                    throw new NonexistentEntityException("The persona with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona persona;
            try {
                persona = em.getReference(Persona.class, id);
                persona.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The persona with id " + id + " no longer exists.", enfe);
            }
            em.remove(persona);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Persona> findPersonaEntities() {
        return findPersonaEntities(true, -1, -1);
    }

    public List<Persona> findPersonaEntities(int maxResults, int firstResult) {
        return findPersonaEntities(false, maxResults, firstResult);
    }

    private List<Persona> findPersonaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Persona.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Persona findPersona(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Persona.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Persona> rt = cq.from(Persona.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public Optional<Persona> findByEmailAndDni(String email, String dni) {
        try {
            TypedQuery<Persona> query = getEntityManager().createQuery(
                    "SELECT p FROM Persona p WHERE p.email = :email AND p.dni = :dni", Persona.class
            );
            query.setParameter("email", email);
            query.setParameter("dni", dni);
            List<Persona> resultados = query.getResultList(); // Evita excepciones
            if (resultados.isEmpty()) {
                System.out.println("No se encontró ninguna persona con email: " + email + " y DNI: " + dni);
                return Optional.empty();
            }

            System.out.println("Persona encontrada: " + resultados.get(0).getEmail());
            SesionPersona.setPersona(resultados.get(0));
            return Optional.of(resultados.get(0));

        } catch (Exception e) {
            e.printStackTrace(); // Captura cualquier otro error
            return Optional.empty();
        }
    }

}
