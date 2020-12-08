package imobiliaria.servico;

import imobiliaria.modelo.Cliente;
import imobiliaria.repositorio.LocacaoRepository;

public class SPCServiceImpl implements SPCService{
    private LocacaoRepository locacaoRepository;

    @Override
    public boolean estaNegativado(Cliente cliente){
        return true;
    }
}
