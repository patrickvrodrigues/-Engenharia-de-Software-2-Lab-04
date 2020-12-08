package imobiliaria.repositorio;

import imobiliaria.modelo.Aluguel;
import imobiliaria.modelo.Locacao;

import java.time.LocalDate;
import java.util.List;

public interface AluguelRepository {

    List<Aluguel> todosAlugueis();
    void salva(Aluguel aluguel);

    void exclui(Aluguel aluguel);

    void atualiza(Aluguel aluguel);

    List<Aluguel> buscaAlugueisPagosPorNomeInquilino(String nome);

    List<Aluguel> buscaAlugueisAtrasadosPorNomeInquilino(String nome);

    List<Aluguel> emAtraso(Locacao locacao);
    List<Aluguel> emAtraso();




}
