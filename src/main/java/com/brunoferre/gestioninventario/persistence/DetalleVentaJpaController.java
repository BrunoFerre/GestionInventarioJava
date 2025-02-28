package com.brunoferre.gestioninventario.persistence;

import com.brunoferre.gestioninventario.logica.DetalleVenta;
import com.brunoferre.gestioninventario.logica.JPAUtil;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import com.brunoferre.gestioninventario.logica.Producto;
import com.brunoferre.gestioninventario.logica.Venta;
import com.brunoferre.gestioninventario.persistence.exceptions.NonexistentEntityException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class DetalleVentaJpaController implements Serializable {

//    public DetalleVentaJpaController(EntityManagerFactory emf) {
//        this.emf = emf;
//    }
//
//    public DetalleVentaJpaController() {
//        emf = Persistence.createEntityManagerFactory("PersistencePOST");
//    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return JPAUtil.getPostgresEntityManager();
    }

    public void create(DetalleVenta detalleVenta) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto producto = detalleVenta.getProducto();
            if (producto != null) {
                producto = em.getReference(producto.getClass(), producto.getId());
                detalleVenta.setProducto(producto);
            }
            Venta venta = detalleVenta.getVenta();
            if (venta != null) {
                venta = em.getReference(venta.getClass(), venta.getId());
                detalleVenta.setVenta(venta);
            }
            em.persist(detalleVenta);
            if (producto != null) {
                producto.getDetalles().add(detalleVenta);
                producto = em.merge(producto);
            }
            if (venta != null) {
                venta.getDetalles().add(detalleVenta);
                venta = em.merge(venta);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DetalleVenta detalleVenta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetalleVenta persistentDetalleVenta = em.find(DetalleVenta.class, detalleVenta.getId());
            Producto productoOld = persistentDetalleVenta.getProducto();
            Producto productoNew = detalleVenta.getProducto();
            Venta ventaOld = persistentDetalleVenta.getVenta();
            Venta ventaNew = detalleVenta.getVenta();
            if (productoNew != null) {
                productoNew = em.getReference(productoNew.getClass(), productoNew.getId());
                detalleVenta.setProducto(productoNew);
            }
            if (ventaNew != null) {
                ventaNew = em.getReference(ventaNew.getClass(), ventaNew.getId());
                detalleVenta.setVenta(ventaNew);
            }
            detalleVenta = em.merge(detalleVenta);
            if (productoOld != null && !productoOld.equals(productoNew)) {
                productoOld.getDetalles().remove(detalleVenta);
                productoOld = em.merge(productoOld);
            }
            if (productoNew != null && !productoNew.equals(productoOld)) {
                productoNew.getDetalles().add(detalleVenta);
                productoNew = em.merge(productoNew);
            }
            if (ventaOld != null && !ventaOld.equals(ventaNew)) {
                ventaOld.getDetalles().remove(detalleVenta);
                ventaOld = em.merge(ventaOld);
            }
            if (ventaNew != null && !ventaNew.equals(ventaOld)) {
                ventaNew.getDetalles().add(detalleVenta);
                ventaNew = em.merge(ventaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = detalleVenta.getId();
                if (findDetalleVenta(id) == null) {
                    throw new NonexistentEntityException("The detalleVenta with id " + id + " no longer exists.");
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
            DetalleVenta detalleVenta;
            try {
                detalleVenta = em.getReference(DetalleVenta.class, id);
                detalleVenta.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detalleVenta with id " + id + " no longer exists.", enfe);
            }
            Producto producto = detalleVenta.getProducto();
            if (producto != null) {
                producto.getDetalles().remove(detalleVenta);
                producto = em.merge(producto);
            }
            Venta venta = detalleVenta.getVenta();
            if (venta != null) {
                venta.getDetalles().remove(detalleVenta);
                venta = em.merge(venta);
            }
            em.remove(detalleVenta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DetalleVenta> findDetalleVentaEntities() {
        return findDetalleVentaEntities(true, -1, -1);
    }

    public List<DetalleVenta> findDetalleVentaEntities(int maxResults, int firstResult) {
        return findDetalleVentaEntities(false, maxResults, firstResult);
    }

    private List<DetalleVenta> findDetalleVentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DetalleVenta.class));
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

    public DetalleVenta findDetalleVenta(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DetalleVenta.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetalleVentaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DetalleVenta> rt = cq.from(DetalleVenta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<DetalleVenta> findAllByVenta(Venta venta) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<DetalleVenta> query = em.createQuery("SELECT dv FROM DetalleVenta dv WHERE dv.venta = :venta", DetalleVenta.class);
            query.setParameter("venta", venta);
            return query.getResultList();
        } catch (NoResultException ex) {
            System.out.println(ex.getMessage());
            return null;
        }catch(Exception e){
            System.out.println("ERROR PORQUE CELE NO ME HACE CASO");
            throw e;
        }finally{
            em.close();
        }
    }

}
