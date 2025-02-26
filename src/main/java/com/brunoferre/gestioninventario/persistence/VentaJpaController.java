package com.brunoferre.gestioninventario.persistence;

import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import com.brunoferre.gestioninventario.logica.DetalleVenta;
import com.brunoferre.gestioninventario.logica.Venta;
import com.brunoferre.gestioninventario.persistence.exceptions.NonexistentEntityException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VentaJpaController implements Serializable {

    public VentaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public VentaJpaController() {
        emf = Persistence.createEntityManagerFactory("GestionPU");
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Venta venta) {
        if (venta.getDetalles() == null) {
            venta.setDetalles(new HashSet<DetalleVenta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Set<DetalleVenta> attachedDetalles = new HashSet<DetalleVenta>();
            for (DetalleVenta detallesDetalleVentaToAttach : venta.getDetalles()) {
                detallesDetalleVentaToAttach = em.getReference(detallesDetalleVentaToAttach.getClass(), detallesDetalleVentaToAttach.getId());
                attachedDetalles.add(detallesDetalleVentaToAttach);
            }
            venta.setDetalles(attachedDetalles);
            em.persist(venta);
            for (DetalleVenta detallesDetalleVenta : venta.getDetalles()) {
                Venta oldVentaOfDetallesDetalleVenta = detallesDetalleVenta.getVenta();
                detallesDetalleVenta.setVenta(venta);
                detallesDetalleVenta = em.merge(detallesDetalleVenta);
                if (oldVentaOfDetallesDetalleVenta != null) {
                    oldVentaOfDetallesDetalleVenta.getDetalles().remove(detallesDetalleVenta);
                    oldVentaOfDetallesDetalleVenta = em.merge(oldVentaOfDetallesDetalleVenta);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Venta venta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Venta persistentVenta = em.find(Venta.class, venta.getId());
            Set<DetalleVenta> detallesOld = persistentVenta.getDetalles();
            Set<DetalleVenta> detallesNew = venta.getDetalles();
            Set<DetalleVenta> attachedDetallesNew = new HashSet<DetalleVenta>();
            for (DetalleVenta detallesNewDetalleVentaToAttach : detallesNew) {
                detallesNewDetalleVentaToAttach = em.getReference(detallesNewDetalleVentaToAttach.getClass(), detallesNewDetalleVentaToAttach.getId());
                attachedDetallesNew.add(detallesNewDetalleVentaToAttach);
            }
            detallesNew = attachedDetallesNew;
            venta.setDetalles(detallesNew);
            venta = em.merge(venta);
            for (DetalleVenta detallesOldDetalleVenta : detallesOld) {
                if (!detallesNew.contains(detallesOldDetalleVenta)) {
                    detallesOldDetalleVenta.setVenta(null);
                    detallesOldDetalleVenta = em.merge(detallesOldDetalleVenta);
                }
            }
            for (DetalleVenta detallesNewDetalleVenta : detallesNew) {
                if (!detallesOld.contains(detallesNewDetalleVenta)) {
                    Venta oldVentaOfDetallesNewDetalleVenta = detallesNewDetalleVenta.getVenta();
                    detallesNewDetalleVenta.setVenta(venta);
                    detallesNewDetalleVenta = em.merge(detallesNewDetalleVenta);
                    if (oldVentaOfDetallesNewDetalleVenta != null && !oldVentaOfDetallesNewDetalleVenta.equals(venta)) {
                        oldVentaOfDetallesNewDetalleVenta.getDetalles().remove(detallesNewDetalleVenta);
                        oldVentaOfDetallesNewDetalleVenta = em.merge(oldVentaOfDetallesNewDetalleVenta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = venta.getId();
                if (findVenta(id) == null) {
                    throw new NonexistentEntityException("The venta with id " + id + " no longer exists.");
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
            Venta venta;
            try {
                venta = em.getReference(Venta.class, id);
                venta.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The venta with id " + id + " no longer exists.", enfe);
            }
            Set<DetalleVenta> detalles = venta.getDetalles();
            for (DetalleVenta detallesDetalleVenta : detalles) {
                detallesDetalleVenta.setVenta(null);
                detallesDetalleVenta = em.merge(detallesDetalleVenta);
            }
            em.remove(venta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Venta> findVentaEntities() {
        return findVentaEntities(true, -1, -1);
    }

    public List<Venta> findVentaEntities(int maxResults, int firstResult) {
        return findVentaEntities(false, maxResults, firstResult);
    }

    private List<Venta> findVentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Venta.class));
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

    public Venta findVenta(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Venta.class, id);
        } finally {
            em.close();
        }
    }

    public int getVentaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Venta> rt = cq.from(Venta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public Venta findVentaByNumeroVenta(String numeroVenta) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Venta> query = em.createQuery("SELECT v FROM Venta v WHERE numeroVenta = :numeroVenta", Venta.class);
            query.setParameter("numeroVenta", numeroVenta);
            return query.getSingleResult();
        } catch (NoResultException ex) {
            System.out.println("No se encontró producto con nombre: " + numeroVenta);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw e; // Relanzar la excepción para ver más detalles
        } finally {
            em.close();
        }
    }
}
