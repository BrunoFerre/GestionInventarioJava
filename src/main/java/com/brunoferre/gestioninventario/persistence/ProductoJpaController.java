
package com.brunoferre.gestioninventario.persistence;

import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import com.brunoferre.gestioninventario.logica.DetalleVenta;
import com.brunoferre.gestioninventario.logica.Producto;
import com.brunoferre.gestioninventario.persistence.exceptions.NonexistentEntityException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductoJpaController implements Serializable {

    public ProductoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public ProductoJpaController() {
        emf= Persistence.createEntityManagerFactory("GestionPU");
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Producto producto) {
        if (producto.getDetalles() == null) {
            producto.setDetalles(new HashSet<DetalleVenta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Set<DetalleVenta> attachedDetalles = new HashSet<DetalleVenta>();
            for (DetalleVenta detallesDetalleVentaToAttach : producto.getDetalles()) {
                detallesDetalleVentaToAttach = em.getReference(detallesDetalleVentaToAttach.getClass(), detallesDetalleVentaToAttach.getId());
                attachedDetalles.add(detallesDetalleVentaToAttach);
            }
            producto.setDetalles(attachedDetalles);
            em.persist(producto);
            for (DetalleVenta detallesDetalleVenta : producto.getDetalles()) {
                Producto oldProductoOfDetallesDetalleVenta = detallesDetalleVenta.getProducto();
                detallesDetalleVenta.setProducto(producto);
                detallesDetalleVenta = em.merge(detallesDetalleVenta);
                if (oldProductoOfDetallesDetalleVenta != null) {
                    oldProductoOfDetallesDetalleVenta.getDetalles().remove(detallesDetalleVenta);
                    oldProductoOfDetallesDetalleVenta = em.merge(oldProductoOfDetallesDetalleVenta);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Producto producto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto persistentProducto = em.find(Producto.class, producto.getId());
            Set<DetalleVenta> detallesOld = persistentProducto.getDetalles();
            Set<DetalleVenta> detallesNew = producto.getDetalles();
            Set<DetalleVenta> attachedDetallesNew = new HashSet<DetalleVenta>();
            for (DetalleVenta detallesNewDetalleVentaToAttach : detallesNew) {
                detallesNewDetalleVentaToAttach = em.getReference(detallesNewDetalleVentaToAttach.getClass(), detallesNewDetalleVentaToAttach.getId());
                attachedDetallesNew.add(detallesNewDetalleVentaToAttach);
            }
            detallesNew = attachedDetallesNew;
            producto.setDetalles(detallesNew);
            producto = em.merge(producto);
            for (DetalleVenta detallesOldDetalleVenta : detallesOld) {
                if (!detallesNew.contains(detallesOldDetalleVenta)) {
                    detallesOldDetalleVenta.setProducto(null);
                    detallesOldDetalleVenta = em.merge(detallesOldDetalleVenta);
                }
            }
            for (DetalleVenta detallesNewDetalleVenta : detallesNew) {
                if (!detallesOld.contains(detallesNewDetalleVenta)) {
                    Producto oldProductoOfDetallesNewDetalleVenta = detallesNewDetalleVenta.getProducto();
                    detallesNewDetalleVenta.setProducto(producto);
                    detallesNewDetalleVenta = em.merge(detallesNewDetalleVenta);
                    if (oldProductoOfDetallesNewDetalleVenta != null && !oldProductoOfDetallesNewDetalleVenta.equals(producto)) {
                        oldProductoOfDetallesNewDetalleVenta.getDetalles().remove(detallesNewDetalleVenta);
                        oldProductoOfDetallesNewDetalleVenta = em.merge(oldProductoOfDetallesNewDetalleVenta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = producto.getId();
                if (findProducto(id) == null) {
                    throw new NonexistentEntityException("The producto with id " + id + " no longer exists.");
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
            Producto producto;
            try {
                producto = em.getReference(Producto.class, id);
                producto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The producto with id " + id + " no longer exists.", enfe);
            }
            Set<DetalleVenta> detalles = producto.getDetalles();
            for (DetalleVenta detallesDetalleVenta : detalles) {
                detallesDetalleVenta.setProducto(null);
                detallesDetalleVenta = em.merge(detallesDetalleVenta);
            }
            em.remove(producto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Producto> findProductoEntities() {
        return findProductoEntities(true, -1, -1);
    }

    public List<Producto> findProductoEntities(int maxResults, int firstResult) {
        return findProductoEntities(false, maxResults, firstResult);
    }

    private List<Producto> findProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Producto.class));
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

    public Producto findProducto(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Producto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Producto> rt = cq.from(Producto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
