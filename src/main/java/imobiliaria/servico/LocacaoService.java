package imobiliaria.servico;

import imobiliaria.modelo.Aluguel;
import imobiliaria.modelo.Cliente;
import imobiliaria.modelo.Imovel;
import imobiliaria.modelo.Locacao;
import imobiliaria.repositorio.AluguelRepository;
import imobiliaria.repositorio.ImovelRepository;
import imobiliaria.repositorio.LocacaoRepository;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LocacaoService {
    private LocacaoRepository locacaoRepository;
    private SPCService spcService;
    private EmailService emailService;
    private AluguelRepository aluguelRepository;


    public Locacao alugarImovel(Cliente cliente, Imovel imovel, Integer diaVencimento, String obs) {
        if (cliente == null) {
            throw new IllegalArgumentException("Usuário não pode ser nulo");
        }

        if(locacaoRepository.imovelJaEstaAlugado(imovel)){
            throw new IllegalArgumentException("Este imóvel ja está alugado");
        }


        //if (spcService.estaNegativado(cliente) ) {
        //    throw new IllegalStateException("Não pode alugar filme para usuario com pendências no SPC");

       // }


        Locacao locacao = new Locacao();
        locacao.setImovel(imovel);
        locacao.setCliente(cliente);
        locacao.setValorAluguel(imovel.getValorAluguelSugerido());
        locacao.setPercentualMulta(new BigDecimal("0.33"));
        locacao.setDiaVencimento(diaVencimento);
        locacao.setDataIncio(LocalDate.now());

        locacao.setDataFim(LocalDate.now().plusYears(1));
        locacao.setAtivo(true);
        locacao.setObs(obs);

        int i;
        List<Aluguel> listaAlugueis = new ArrayList<>();
        for(i=0;i<locacao.getPeriodo(); i++){
            Aluguel aluguel = new Aluguel();
            aluguel.setDataVencimento(locacao.getDataIncio().plusMonths(i).withDayOfMonth(locacao.getDiaVencimento()));
            aluguel.setLocacao(locacao);
            aluguelRepository.salva(aluguel);
            listaAlugueis.add(aluguel);
        }
        locacao.setAlugueis(listaAlugueis);

        locacaoRepository.salva(locacao);

        return locacao;
    }

    public Locacao pagarAluguel(Locacao locacao, Aluguel aluguel ,BigDecimal valor, AluguelRepository aluguelRepository){
        if (aluguel == null) {
            throw new IllegalArgumentException("Aluguel não pode ser nulo");
        }
        AluguelService service = new AluguelService();
        service.setAluguelRepository(aluguelRepository);
        aluguel.setLocacao(locacao);
        Aluguel aluguel1 = service.pagarAluguel(aluguel,valor);

        locacao.getAlugueis().add(aluguel1);

        return locacao;
    }

    public void notificaUsuariosEmAtraso() {

        List<Aluguel> alugueisAtrasados=  aluguelRepository.emAtraso();

        alugueisAtrasados.forEach(aluguel ->
                emailService.notifica(aluguel.getLocacao().getCliente()
                ));

    }



}
