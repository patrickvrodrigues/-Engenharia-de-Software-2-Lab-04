package imobiliaria.repositorio;

import imobiliaria.modelo.Imovel;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class ImovelRepositoryImpl implements  ImovelRepository{
    private EntityManager manager;

    public ImovelRepositoryImpl(EntityManager manager){
        this.manager = manager;
    }

    @Override
    public void salva(Imovel imovel){
        manager.persist(imovel );
    }
    @Override
    public void atualiza(Imovel imovel){
        manager.merge(imovel);
    }
    @Override
    public void exclui(Imovel imovel){
        manager.remove(imovel);
    }
    @Override
    public List<Imovel> imoveisDisponiveisPor(String bairro){
        LocacaoRepository locacaoRepository = new LocacaoRepositoryImpl(this.manager);
        List<Imovel> imoveisDisponiveis = new ArrayList<>();
        String jpql = "from Imovel i "
                + "where i.bairro == :bairro";
        List<Imovel> imoveis = manager
                .createQuery(jpql, Imovel.class)
                .setParameter("bairro", bairro )
                .getResultList();


        for (Imovel imovel : imoveis) {
            if(locacaoRepository.imovelJaEstaAlugado(imovel)){
                imoveisDisponiveis.add(imovel);
            }
        }

        return imoveisDisponiveis;
    }
    @Override
    public List<Imovel> imoveisDisponiveis(){
        LocacaoRepository locacaoRepository = new LocacaoRepositoryImpl(this.manager);
        String jpql = "from Imovel i ";
        List<Imovel> imoveis = manager
                .createQuery(jpql, Imovel.class)
                .getResultList();
        return imoveis;
    }



}
