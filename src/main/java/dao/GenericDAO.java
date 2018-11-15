/*
 * Desenvolvimento Java Avancado
 * 1o sem/2018
 * 0002346 - Pedro Veloso
 */

package dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO<T, ID extends Serializable> {
   
    public T buscarPorCodigo(ID id);
    public void salvar(T entidade);
    public boolean remover(ID id);
    //public void remover(T entidade);
    public List<T> buscarTodos();
}
