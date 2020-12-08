package imobiliaria.servico;

import imobiliaria.modelo.Aluguel;
import imobiliaria.repositorio.AluguelRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class AluguelService {
    private AluguelRepository aluguelRepository;

    public Aluguel pagarAluguel(Aluguel aluguel, BigDecimal valor){
        BigDecimal valorAluguel = calculaValorAluguel(aluguel);
        if(valor.doubleValue()<valorAluguel.doubleValue()){
            throw new IllegalArgumentException("O valor é menor que o valor a pagar");
        }
        aluguel.setValorPago(valorAluguel);
        aluguelRepository.atualiza(aluguel);
        return aluguel;
    }

    public BigDecimal calculaValorAluguel(Aluguel aluguel){
        BigDecimal valorAluguel = aluguel.getLocacao().getValorAluguel();
        if ((aluguel.getDataVencimento().isBefore(LocalDate.now())) == true) {
            BigDecimal diasAtrasados = new BigDecimal(aluguel.getDataVencimento().until(LocalDate.now(), ChronoUnit.DAYS));

            valorAluguel = valorAluguel.add(aluguel.getLocacao().getValorAluguel().
                    multiply(diasAtrasados.multiply(aluguel.getLocacao().getPercentualMulta()).divide(new BigDecimal("100"))));
        }
        return valorAluguel;
    }

    public void setAluguelRepository(AluguelRepository aluguelRepository) {
        this.aluguelRepository = aluguelRepository;
    }
}
