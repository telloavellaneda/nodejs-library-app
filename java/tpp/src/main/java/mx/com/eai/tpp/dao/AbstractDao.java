package mx.com.eai.tpp.dao;

import java.io.Serializable;
import java.util.List;
import org.hibernate.criterion.Criterion;

public interface AbstractDao<E, I extends Serializable>
{

    E findById(I id);

    void saveOrUpdate(E e);
    
    void saveOrUpdate(List<E> es);

    void delete(E e);
    
    List<E> findByCriteria(Criterion criterion);

    public List<E> getAll();
    
    public List<E> getAllOrderByDescripcion();
    
    public List<E> getAllInactive();
    
    public List<E> getAllInactiveOrderByDescripcion();
   
}
