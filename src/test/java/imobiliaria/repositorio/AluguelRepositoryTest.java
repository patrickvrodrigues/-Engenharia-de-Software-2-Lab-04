package imobiliaria.repositorio;

import imobiliaria.builder.AluguelBuilder;
import imobiliaria.builder.ClienteBuilder;
import imobiliaria.builder.LocacaoBuilder;
import imobiliaria.modelo.Aluguel;
import imobiliaria.modelo.Cliente;
import imobiliaria.modelo.Locacao;
import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class AluguelRepositoryTest {
    private EntityManager manager;
    private static EntityManagerFactory emf;
    private AluguelRepository alugueis;
    private ClienteRepository clientes;
    private LocacaoRepository locacoes;

    @BeforeAll
    public static void inicio() {
        emf = Persistence.createEntityManagerFactory("Imobiliaria_test");
    }

    @BeforeEach
    public void antes() {
        manager = emf.createEntityManager();
        manager.getTransaction().begin();

        alugueis = new AluguelRepositoryImpl(manager );
        clientes = new ClienteRepositoryImpl(manager);
        locacoes = new LocacaoRepositoryImpl(manager);

    }
    @AfterEach
    public void depois() {
        manager.getTransaction().rollback();
    }

    @AfterAll
    public static void fim() {
        emf.close();
    }
    @Test
    public void deveTrazerSomenteAlugueisPagosPorNomeDoInquilino() {
        Cliente cliente1 = ClienteBuilder.umCliente().constroi();
        clientes.salva(cliente1);

        Locacao locacao1 = LocacaoBuilder.umaLocacao().paraCliente(cliente1).constroi();
        locacoes.salva(locacao1);
        Aluguel aluguel1 = AluguelBuilder.umAluguel().comLocacao(locacao1)
                .comDataVencimento(LocalDate.now().plusDays(7))
                .comDataPagamento(LocalDate.now()).comValorPago(new BigDecimal("1000"))
                .constroi();
        Aluguel aluguel2 = AluguelBuilder.umAluguel().comLocacao(locacao1)
                .comDataVencimento(LocalDate.now().plusMonths(1).plusDays(7))
                .comDataPagamento(LocalDate.now().plusMonths(1))
                .comValorPago(new BigDecimal("1000")).constroi();
        Aluguel aluguel3 = AluguelBuilder.umAluguel().comLocacao(locacao1)
                .comDataVencimento(LocalDate.now().plusMonths(2).plusDays(7))
                .comDataPagamento(LocalDate.now().plusMonths(2))
                .comValorPago(new BigDecimal("1000")).constroi();
        Aluguel aluguel4 = AluguelBuilder.umAluguel().comDataVencimento(LocalDate.now().plusMonths(3).plusDays(7))
                .comLocacao(locacao1).constroi();
        alugueis.salva(aluguel1);alugueis.salva(aluguel2);alugueis.salva(aluguel3);
        alugueis.salva(aluguel4);

        List<Aluguel> listaAlugueis = alugueis.buscaAlugueisPagosPorNomeInquilino("Patrick Viegas");

        for(Aluguel aluguel : listaAlugueis){
            Assertions.assertEquals("Patrick Viegas", aluguel.getLocacao().getCliente().getNomeCliente());
            Boolean bool;
            if (aluguel.getValorPago()!= null)
                bool = true;
            else
                bool = false;
            Assertions.assertTrue(bool);
        }
        Assertions.assertEquals(3, listaAlugueis.size());

    }
    @Test
    public void deveTrazerSomenteAlugueisAtrasadosPorNomeDoInquilino() {
        Cliente cliente1 = ClienteBuilder.umCliente().constroi();
        clientes.salva(cliente1);

        Locacao locacao1 = LocacaoBuilder.umaLocacao().paraCliente(cliente1).constroi();
        locacoes.salva(locacao1);
        Aluguel aluguel1 = AluguelBuilder.umAluguel().comLocacao(locacao1)
                .comDataVencimento(LocalDate.now().plusDays(7))
                .comDataPagamento(LocalDate.now()).comValorPago(new BigDecimal("1000"))
                .constroi();
        Aluguel aluguel2 = AluguelBuilder.umAluguel().comLocacao(locacao1)
                .comDataVencimento(LocalDate.now().plusMonths(1).minusDays(7))
                .comDataPagamento(LocalDate.now().plusMonths(1))
                .comValorPago(new BigDecimal("1000")).constroi();
        Aluguel aluguel3 = AluguelBuilder.umAluguel().comLocacao(locacao1)
                .comDataVencimento(LocalDate.now().plusMonths(2).minusDays(7))
                .comDataPagamento(LocalDate.now().plusMonths(2))
                .comValorPago(new BigDecimal("1000")).constroi();
        Aluguel aluguel4 = AluguelBuilder.umAluguel().comDataVencimento(LocalDate.now().plusMonths(3).plusDays(7))
                .comLocacao(locacao1).constroi();
        alugueis.salva(aluguel1);alugueis.salva(aluguel2);alugueis.salva(aluguel3);
        alugueis.salva(aluguel4);

        List<Aluguel> listaAlugueis = alugueis.buscaAlugueisAtrasadosPorNomeInquilino("Patrick Viegas");

        for(Aluguel aluguel : listaAlugueis){
            Assertions.assertEquals("Patrick Viegas", aluguel.getLocacao().getCliente().getNomeCliente());
            Boolean bool;
            if (aluguel.getValorPago()!= null)
                bool = true;
            else
                bool = false;
            Assertions.assertTrue(bool);
        }

        Assertions.assertEquals(2, listaAlugueis.size());
    }
}
