package imobiliaria.repositorio;

import imobiliaria.modelo.Cliente;
import javax.persistence.EntityManager;

public class ClienteRepositoryImpl implements  ClienteRepository{
    private EntityManager manager;

    public ClienteRepositoryImpl(EntityManager manager){
        this.manager = manager;
    }

    @Override
    public void salva(Cliente cliente){
        manager.persist(cliente );
    }
    @Override
    public void atualiza(Cliente cliente){
        manager.merge(cliente);
    }
    @Override
    public void exclui(Cliente cliente){
        manager.remove(cliente);
    }

}
