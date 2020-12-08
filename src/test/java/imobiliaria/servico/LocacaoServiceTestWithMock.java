package imobiliaria.servico;

import imobiliaria.builder.AluguelBuilder;
import imobiliaria.builder.ClienteBuilder;
import imobiliaria.builder.ImovelBuilder;
import imobiliaria.builder.LocacaoBuilder;
import imobiliaria.modelo.Aluguel;
import imobiliaria.modelo.Cliente;
import imobiliaria.modelo.Imovel;
import imobiliaria.modelo.Locacao;
import imobiliaria.repositorio.AluguelRepository;
import imobiliaria.repositorio.ClienteRepository;
import imobiliaria.repositorio.ImovelRepository;
import imobiliaria.repositorio.LocacaoRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;


public class LocacaoServiceTestWithMock {
    @InjectMocks
    private LocacaoService locacaoService;


    @Mock
    private AluguelRepository aluguelRepository;
    @Mock
    private LocacaoRepository locacaoRepository;
    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private ImovelRepository imovelRepository;
    @Mock
    private EmailService emailService;

    @Before
    public void setup() {
        locacaoService = new LocacaoService();

        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void testaUmaLocacao() {
        // cenário
        Cliente cliente1 = ClienteBuilder.umCliente().constroi();
        Imovel imovel1 = ImovelBuilder.umImovel().constroi();

        // ação
        Locacao locacao = locacaoService.alugarImovel(cliente1, imovel1, 7, "");

        // verificação
        assertThat(locacao.getAtivo(), is(equalTo(true)));
        assertThat(locacao.getValorAluguel(), is(equalTo(new BigDecimal("1200"))));
        assertThat(locacao.getDataIncio(), is(equalTo(LocalDate.now())));
        assertThat(locacao.getDiaVencimento(), is(equalTo(7)));

        verify(locacaoRepository, times(1)).salva(locacao);

    }

    /* EnviadorDeEmail: deverá ter, no mínimo a funcionalidade de
enviar email a todos para todos os clientes que não
pagaram o aluguel na data prevista. */
    @Test
    public void deveEnviarEmailParaClientesComAluguelEmAtraso() {
        // cenário
        Cliente cliente1 = ClienteBuilder.umCliente().comNome("Fernando").constroi();
        Cliente cliente2 = ClienteBuilder.umCliente().comNome("Tereza").constroi();
        Cliente cliente3 = ClienteBuilder.umCliente().constroi();

        Locacao locacao1 = LocacaoBuilder.umaLocacao().paraCliente(cliente1).constroi();
        Locacao locacao2 = LocacaoBuilder.umaLocacao().paraCliente(cliente2).constroi();
        Locacao locacao3 = LocacaoBuilder.umaLocacao().paraCliente(cliente3).constroi();

        List<Locacao> locacoes = new ArrayList<>();

        List<Aluguel> alugueisEmAtraso = new ArrayList<>();
        List<Aluguel> alugueisEmAtraso2 = new ArrayList<>();
        alugueisEmAtraso.add(AluguelBuilder.umAluguel().comLocacao(locacao1).emAtraso().constroi());
        alugueisEmAtraso2.add(AluguelBuilder.umAluguel().comLocacao(locacao2).emAtraso().constroi());
        alugueisEmAtraso2.add(AluguelBuilder.umAluguel().comLocacao(locacao2).emAtraso().constroi());
        locacao1.setAlugueis(alugueisEmAtraso);
        locacao2.setAlugueis(alugueisEmAtraso2);

        locacoes.add(locacao1);
        locacoes.add(locacao2);
        locacoes.add(locacao3);


        // ação
        for(Locacao locacao : locacoes){

            locacaoService.notificaUsuariosEmAtraso();
            when(aluguelRepository.emAtraso()).thenReturn(locacao.getAlugueis());
        }

        // verificação
        verify(emailService, times(1)).notifica(cliente1);
        verify(emailService, times(2)).notifica(cliente2);
        verify(emailService, never() ).notifica(cliente3);

    }


    /*Tratando Exceção: Usando Mock Objects crie um teste, que
verifique se uma exceção foi lançada, O EnviadorDeEmail
continuará funcionando da mesma forma, caso um email seja
rejeitado. Os outros emails deverão ser enviados.*/
    @Test
    public void deveGerarExcecaoAoEnviarEmailParaClientesComAluguelEmAtraso() {
        // cenário
        Cliente cliente1 = ClienteBuilder.umCliente().comNome("Fernando").constroi();
        Cliente cliente2 = ClienteBuilder.umCliente().comNome("Tereza").constroi();
        Cliente cliente3 = ClienteBuilder.umCliente().constroi();

        Locacao locacao1 = LocacaoBuilder.umaLocacao().paraCliente(cliente1).constroi();
        Locacao locacao2 = LocacaoBuilder.umaLocacao().paraCliente(cliente2).constroi();
        Locacao locacao3 = LocacaoBuilder.umaLocacao().paraCliente(cliente3).constroi();

        List<Locacao> locacoes = new ArrayList<>();

        List<Aluguel> alugueisEmAtraso = new ArrayList<>();
        List<Aluguel> alugueisEmAtraso2 = new ArrayList<>();
        List<Aluguel> alugueisEmAtraso3 = new ArrayList<>();
        alugueisEmAtraso.add(AluguelBuilder.umAluguel().comLocacao(locacao1).emAtraso().constroi());
        alugueisEmAtraso2.add(AluguelBuilder.umAluguel().comLocacao(locacao2).emAtraso().constroi());
        alugueisEmAtraso2.add(AluguelBuilder.umAluguel().comLocacao(locacao2).emAtraso().constroi());
        alugueisEmAtraso3.add(AluguelBuilder.umAluguel().comLocacao(locacao3).emAtraso().constroi());
        locacao1.setAlugueis(alugueisEmAtraso);
        locacao2.setAlugueis(alugueisEmAtraso2);
        locacao3.setAlugueis(alugueisEmAtraso3);

        locacoes.add(locacao1);
        locacoes.add(locacao2);
        locacoes.add(locacao3);


        // ação
        for(Locacao locacao : locacoes){
            try {
                when(aluguelRepository.emAtraso()).thenReturn(locacao.getAlugueis());
                Mockito.doThrow(new IllegalArgumentException("Email não enviado para "+cliente1.getNomeCliente())).when(emailService).notifica(cliente1);
                locacaoService.notificaUsuariosEmAtraso();

            }catch (IllegalArgumentException e){
                System.out.println(e);
            }
        }

        // verificação
        verify(emailService, times(1)).notifica(cliente1);
        verify(emailService, times(2)).notifica(cliente2);
        verify(emailService, times(1)).notifica(cliente3);


    }

}
