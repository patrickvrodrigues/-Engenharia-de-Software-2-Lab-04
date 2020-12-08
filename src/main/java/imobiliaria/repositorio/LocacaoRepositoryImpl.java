package imobiliaria.repositorio;

import imobiliaria.modelo.Aluguel;
import imobiliaria.modelo.Imovel;
import imobiliaria.modelo.Locacao;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class LocacaoRepositoryImpl implements  LocacaoRepository{
    private EntityManager manager;
    private AluguelRepository aluguelRepository = new AluguelRepositoryImpl(manager);

    public LocacaoRepositoryImpl(EntityManager manager){
        this.manager = manager;
    }

    @Override
    public void salva(Locacao locacao){
        manager.persist(locacao);
    }
    @Override
    public void atualiza(Locacao locacao){
        manager.merge(locacao);
    }
    @Override
    public void exclui(Locacao locacao){
        manager.remove(locacao);
    }

    public boolean imovelJaEstaAlugado(Imovel imovel){
        String jpql = "from Locacao l "
                + "where l.imovel = :imovel "
            + "and l.ativo = true";

        List<Locacao> locacoes = manager
                .createQuery(jpql, Locacao.class)
                .setParameter("imovel", imovel)
                .getResultList();
        if (locacoes.isEmpty()){
            return false;
        }else{
            return true;
        }
    }
    @Override
    public List<Locacao> imoveisDisponiveis(){
        String jpql = "from Locacao l "
                + "where l.ativo = false ";

        List<Locacao> locacoes = manager
                .createQuery(jpql, Locacao.class)
                .getResultList();
        return  locacoes;
    }
    @Override
    public List<Locacao> imoveisDisponiveisPor(String bairro){
        String jpql = "from Locacao l "
                + "where l.imovel.bairro = :bairro and l.ativo = false";

        List<Locacao> locacoes = manager
                .createQuery(jpql, Locacao.class)
                .setParameter("bairro", bairro)
                .getResultList();
        return  locacoes;
    }
    @Override
    public List<Locacao> imoveisDisponiveisPorLimiteDe(BigDecimal preco){
        String jpql = "from Locacao l "
                + "where l.imovel.valorAluguelSugerido <= :preco and l.ativo = false";

        List<Locacao> locacoes = manager
                .createQuery(jpql, Locacao.class)
                .setParameter("preco", preco)
                .getResultList();
        return  locacoes;
    }
    @Override
    public List<Aluguel> alugueisEmAtrasoPor(Locacao locacao){
        return aluguelRepository.emAtraso(locacao);
    }

}
