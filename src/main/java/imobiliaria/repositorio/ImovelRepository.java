package imobiliaria.repositorio;

import imobiliaria.modelo.Imovel;

import java.util.List;

public interface ImovelRepository {
    void salva(Imovel imovel);
    void exclui(Imovel imovel);
    void atualiza(Imovel imovel);
    List<Imovel> imoveisDisponiveisPor(String bairro);
    List<Imovel> imoveisDisponiveis();
}
