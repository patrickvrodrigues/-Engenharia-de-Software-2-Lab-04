package imobiliaria.servico;

import imobiliaria.builder.AluguelBuilder;
import imobiliaria.builder.LocacaoBuilder;
import imobiliaria.modelo.Aluguel;
import imobiliaria.modelo.Locacao;
import imobiliaria.repositorio.AluguelRepository;
import imobiliaria.repositorio.LocacaoRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


public class AluguelServiceTestWithMock {
    @InjectMocks
    private AluguelService aluguelService;

    @Mock
    private AluguelRepository aluguelRepository;
    @Mock
    private LocacaoRepository locacaoRepository;

    @Before
    public void setup() {
        aluguelService = new AluguelService();

        locacaoRepository = Mockito.mock(LocacaoRepository.class);
        aluguelRepository = Mockito.mock(AluguelRepository.class);

        aluguelService.setAluguelRepository(aluguelRepository );

        MockitoAnnotations.initMocks(this);

    }


    /*GeradorDePagamento: Uma exceção deverá ser lançada, caso o
valor_pago seja menor que o valor do aluguel.*/

    @Test(expected=IllegalArgumentException.class)
    public void testaUmPagamentoComValorMenor() {

        Locacao locacao1 = LocacaoBuilder.umaLocacao().comValorDeAluguel(new BigDecimal("900")).constroi();

        locacaoRepository.salva(locacao1);
        Aluguel aluguel1 = AluguelBuilder.umAluguel().comLocacao(locacao1)
                .comDataVencimento(LocalDate.now().plusDays(7)).constroi();
        aluguelRepository.salva(aluguel1);

        aluguelService.pagarAluguel(aluguel1,new BigDecimal("800"));

        verify(aluguelRepository, times(1)).atualiza(aluguel1);
    }
    @Test
    public void testaUmPagamentoComAtraso() {

        Locacao locacao1 = LocacaoBuilder.umaLocacao().comValorDeAluguel(new BigDecimal("900")).constroi();

        locacaoRepository.salva(locacao1);
        Aluguel aluguel1 = AluguelBuilder.umAluguel().comLocacao(locacao1)
                .comDataVencimento(LocalDate.now().minusDays(7)).constroi();
        aluguelRepository.salva(aluguel1);

        aluguel1 = aluguelService.pagarAluguel(aluguel1,new BigDecimal("920.79"));
        Assert.assertEquals(920.79,aluguel1.getValorPago().doubleValue(),0.00001);
        
    }

    /*Valor a ser pago com multa: Dado a data de vencimento e a
data de pagamento, calcule o valor a ser pago, incluindo a
multa. Se o pagamento estiver dentro do prazo, deverá ser
retornado o valor do aluguel sem acréscimo de multas.*/
    @Test
    public void testaValoresDePagamento(){

        //Cenário
        Locacao locacao1 = LocacaoBuilder.umaLocacao().comValorDeAluguel(new BigDecimal("1200")).constroi();
        Aluguel aluguel1 = AluguelBuilder.umAluguel().comLocacao(locacao1).constroi();

        Locacao locacao2 = LocacaoBuilder.umaLocacao().comValorDeAluguel(new BigDecimal("900")).constroi();
        Aluguel aluguel2 = AluguelBuilder.umAluguel().comLocacao(locacao2)
                .comDataVencimento(LocalDate.now().minusDays(7)).constroi();


        //Ações
        //Teste1: Paga aluguel com valor sem atraso
        aluguel1 = aluguelService.pagarAluguel(aluguel1, new BigDecimal("1200"));
        //Teste2: Paga aluguel com valor com atraso
        aluguel2 = aluguelService.pagarAluguel(aluguel2,new BigDecimal("920.79"));


        //Verificações
        //Verificação Teste1: Verifica se foi pago com o valor original sem juros
        Assert.assertEquals(1200,aluguel1.getValorPago().doubleValue(),0.00001);

        //Verificação Teste2: Verifica se foi pago com o valor com juros devido ao atraso
        Assert.assertEquals(920.79,aluguel2.getValorPago().doubleValue(),0.00001);


        //Verificação Teste1: Verifica se o metódo de atualizar pagamento foi chamado só 1 vez
        verify(aluguelRepository, times(1)).atualiza(aluguel1);
        //Verificação Teste2: Verifica se o metódo de atualizar pagamento foi chamado só 1 vez
        verify(aluguelRepository, times(1)).atualiza(aluguel2);
    }

}
