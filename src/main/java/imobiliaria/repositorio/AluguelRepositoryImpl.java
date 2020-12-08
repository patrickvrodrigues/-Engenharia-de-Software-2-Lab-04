package imobiliaria.repositorio;

import imobiliaria.modelo.Aluguel;
import imobiliaria.modelo.Locacao;
import net.bytebuddy.implementation.bytecode.Throw;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class AluguelRepositoryImpl implements AluguelRepository {
    private EntityManager manager;

    public AluguelRepositoryImpl(EntityManager manager){
        this.manager = manager;
    }

    @Override
    public void salva(Aluguel aluguel){
        manager.persist(aluguel );
    }
    @Override
    public void atualiza(Aluguel aluguel){
        manager.merge(aluguel);
    }
    @Override
    public void exclui(Aluguel aluguel){
        manager.remove(aluguel);
    }

    @Override
    public List<Aluguel> todosAlugueis(){
        String jpql = "from Aluguel";

        List<Aluguel> aluguel = manager
                .createQuery(jpql, Aluguel.class)
                .getResultList();
        return aluguel;
    }

    public Aluguel buscaPor(Locacao locacao, LocalDate dataVencimento){
        String jpql = "from Aluguel a "
                + "where a.locacao = :locacao and a.dataVencimento = :dataVencimento";

        Aluguel aluguel = manager
                .createQuery(jpql, Aluguel.class)
                .getSingleResult();
        return aluguel;
    }

    @Override
    public List<Aluguel> emAtraso(Locacao locacao){
        LocalDate data = LocalDate.now();
        String jpql = "from Aluguel a "
                + "where a.locacao = :locacao ";

        List<Aluguel> alugueis = manager
                .createQuery(jpql, Aluguel.class)
                .setParameter("locacao", locacao)
                .getResultList();
        return alugueis;
    }
    @Override
    public List<Aluguel> emAtraso(){
        LocalDate data = LocalDate.now();
        String jpql = "from Aluguel a "
                + "where a.dataVencimento < :data ";

        List<Aluguel> alugueis = manager
                .createQuery(jpql, Aluguel.class)
                .setParameter("data", data)
                .getResultList();
        return alugueis;
    }

    public List<Aluguel> pagos(){
        String jpql = "from Aluguel a "
                + "where valorPago != null";
        List<Aluguel> alugueis = manager
                .createQuery(jpql, Aluguel.class)
                .getResultList();
        return alugueis;
    }
    @Override
    public List<Aluguel> buscaAlugueisPagosPorNomeInquilino(String nome){
        String jpql = "from Aluguel a "
                + "where a.valorPago != null and a.locacao.cliente.nomeCliente = :nome";
        List<Aluguel> alugueis = manager
                .createQuery(jpql, Aluguel.class)
                .setParameter("nome",nome)
                .getResultList();

        return alugueis;
    }
    @Override
    public List<Aluguel> buscaAlugueisAtrasadosPorNomeInquilino(String nome){
        String jpql = "from Aluguel a "
                + "where a.valorPago != null and a.locacao.cliente.nomeCliente = :nome "+
                "and a.dataPagamento > a.dataVencimento";
        List<Aluguel> alugueis = manager
                .createQuery(jpql, Aluguel.class)
                .setParameter("nome",nome)
                .getResultList();

        return alugueis;
    }
}
