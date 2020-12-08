package imobiliaria.builder;

import imobiliaria.modelo.Aluguel;
import imobiliaria.modelo.Cliente;
import imobiliaria.modelo.Imovel;
import imobiliaria.modelo.Locacao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class LocacaoBuilder {
    private Locacao locacao;


    private LocacaoBuilder() { }

    public static LocacaoBuilder umaLocacao() {
        LocacaoBuilder builder = new LocacaoBuilder();
        Imovel imovel = ImovelBuilder.umImovel().constroi();
        Cliente cliente = ClienteBuilder.umCliente().constroi();
        builder.locacao = new Locacao();
        builder.locacao.setImovel(imovel);
        builder.locacao.setCliente(cliente);
        builder.locacao.setValorAluguel(imovel.getValorAluguelSugerido());
        builder.locacao.setPercentualMulta(new BigDecimal("0.33"));
        builder.locacao.setDiaVencimento(7);
        builder.locacao.setDataIncio(LocalDate.now());
        builder.locacao.setDataFim(LocalDate.now().plusYears(1));
        builder.locacao.setAtivo(true);
        builder.locacao.setObs("Sem Observação");

        return builder;
    }

    public LocacaoBuilder paraCliente(Cliente cliente){
        locacao.setCliente(cliente);
        return this;
    }

    public LocacaoBuilder comImovel(Imovel imovel){
        locacao.setImovel(imovel);
        return this;
    }
    public LocacaoBuilder comValorDeAluguel(BigDecimal valor){
        locacao.setValorAluguel(valor);
        return this;
    }

    public LocacaoBuilder comDiaVencimento(Integer dia){
        locacao.setDiaVencimento(dia);
        return this;
    }

    public LocacaoBuilder comAtivo(boolean valor){
        locacao.setAtivo(valor);
        return this;
    }

    public LocacaoBuilder comListaDeAlugueis(List<Aluguel> alugueis){
        locacao.setAlugueis(alugueis);
        return this;
    }
    public LocacaoBuilder comUmAluguelEmAtraso(){
        locacao.getAlugueis().get(0).setDataVencimento(locacao.getAlugueis().get(0).getDataVencimento().minusDays(20));
        return this;
    }

    public Locacao constroi() {
        return this.locacao;
    }
}
