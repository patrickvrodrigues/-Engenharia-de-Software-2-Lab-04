package imobiliaria.repositorio;

import imobiliaria.builder.ImovelBuilder;
import imobiliaria.builder.LocacaoBuilder;
import imobiliaria.modelo.Imovel;
import imobiliaria.modelo.Locacao;
import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LocacaoRepositoryTest {
    private EntityManager manager;
    private static EntityManagerFactory emf;
    private LocacaoRepository locacoes;
    private ImovelRepository imoveis;

    @BeforeAll
    public static void inicio() {
        emf = Persistence.createEntityManagerFactory("Imobiliaria_test");
    }

    @BeforeEach
    public void antes() {
        manager = emf.createEntityManager();
        manager.getTransaction().begin();

        locacoes = new LocacaoRepositoryImpl(manager );
        imoveis = new ImovelRepositoryImpl(manager);

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
    public void deveTrazerSomenteApartamentoDisponiveisPorDeterminadoBairro() {
        Imovel imovel1 = ImovelBuilder.umImovel().constroi();
        imoveis.salva(imovel1);
        Imovel imovel2 = ImovelBuilder.umImovel().constroi();
        imoveis.salva(imovel2);
        Imovel imovel3 = ImovelBuilder.umImovel().comBairro("Anjo da Guarda").constroi();
        imoveis.salva(imovel3);
        Locacao locacao1 = LocacaoBuilder.umaLocacao().comImovel(imovel1).comAtivo(false).constroi();
        Locacao locacao2 = LocacaoBuilder.umaLocacao().comImovel(imovel2).comAtivo(false).constroi();
        Locacao locacao3 = LocacaoBuilder.umaLocacao().comImovel(imovel3).comAtivo(false).constroi();
        locacoes.salva(locacao1);
        locacoes.salva(locacao2);

        List<Locacao> lista_locacoes = locacoes.imoveisDisponiveisPor("São Francisco");
        for(Locacao locacao : lista_locacoes){
            Assertions.assertEquals("São Francisco", locacao.getImovel().getBairro() );
            Assertions.assertFalse(locacao.getAtivo());
        }
    }
    @Test
    public void deveTrazerSomenteImoveisComPrecoMenorOuIgualAoInformado(){
        Imovel imovel1 = ImovelBuilder.umImovel().comValorSugerido(new BigDecimal("1000")).constroi();
        Imovel imovel2 = ImovelBuilder.umImovel().comValorSugerido(new BigDecimal(900)).constroi();
        Imovel imovel3 = ImovelBuilder.umImovel().comValorSugerido(new BigDecimal(800)).constroi();
        Imovel imovel4 = ImovelBuilder.umImovel().comValorSugerido(new BigDecimal(1200)).constroi();
        Imovel imovel5 = ImovelBuilder.umImovel().comValorSugerido(new BigDecimal(1500)).constroi();
        Locacao locacao1 = LocacaoBuilder.umaLocacao().comImovel(imovel1).comAtivo(false).constroi();
        Locacao locacao2 = LocacaoBuilder.umaLocacao().comImovel(imovel2).comAtivo(false).constroi();
        Locacao locacao3 = LocacaoBuilder.umaLocacao().comImovel(imovel3).comAtivo(false).constroi();
        Locacao locacao4 = LocacaoBuilder.umaLocacao().comImovel(imovel4).comAtivo(false).constroi();
        Locacao locacao5 = LocacaoBuilder.umaLocacao().comImovel(imovel5).comAtivo(false).constroi();

        imoveis.salva(imovel1);imoveis.salva(imovel2);imoveis.salva(imovel3);imoveis.salva(imovel4);imoveis.salva(imovel5);
        locacoes.salva(locacao1);locacoes.salva(locacao2);locacoes.salva(locacao3);locacoes.salva(locacao4);locacoes.salva(locacao5);

        List<Locacao> listaLocacoes = locacoes.imoveisDisponiveisPorLimiteDe(new BigDecimal("1000"));
        for(Locacao locacao : listaLocacoes){
            boolean bool;
            if(locacao.getImovel().getValorAluguelSugerido().compareTo(new BigDecimal("1000"))<=1000){
                bool = true;
            }
            else{
                bool = false;
            }
        Assertions.assertTrue(bool);
        }
    }

}
