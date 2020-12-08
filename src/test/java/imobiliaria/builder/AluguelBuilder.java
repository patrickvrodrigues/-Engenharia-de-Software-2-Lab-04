package imobiliaria.builder;

import imobiliaria.modelo.Aluguel;
import imobiliaria.modelo.Locacao;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AluguelBuilder {
    private Aluguel aluguel;

    private AluguelBuilder(){}

    public static AluguelBuilder umAluguel(){
        AluguelBuilder builder = new AluguelBuilder();

        builder.aluguel = new Aluguel();
        builder.aluguel.setLocacao(LocacaoBuilder.umaLocacao().constroi());
        builder.aluguel.setDataVencimento(LocalDate.now().
                withDayOfMonth(builder.aluguel.getLocacao().getDiaVencimento()).plusDays(7));
        builder.aluguel.setObs("");

        return builder;
    }

    public AluguelBuilder emAtraso(){
        this.aluguel.setDataVencimento(LocalDate.now().minusDays(5));
        this.aluguel.setDataPagamento(LocalDate.now());
        return this;
    }
    public AluguelBuilder comDataPagamento(LocalDate dataPagamento){
        this.aluguel.setDataPagamento(dataPagamento);
        return this;
    }
    public AluguelBuilder comDataVencimento(LocalDate dataVencimento){
        this.aluguel.setDataVencimento(dataVencimento);
        return this;
    }
    public AluguelBuilder comValorPago(BigDecimal valorPago){
        this.aluguel.setValorPago(valorPago);
        return this;
    }

    public AluguelBuilder comLocacao(Locacao locacao){
        this.aluguel.setLocacao(locacao);
        return this;
    }


    public Aluguel constroi(){
        return this.aluguel;
    }
}
