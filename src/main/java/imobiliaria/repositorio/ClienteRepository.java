package imobiliaria.repositorio;


import imobiliaria.modelo.Cliente;

public interface ClienteRepository {

    void salva(Cliente cliente);

    void exclui(Cliente cliente);

    void atualiza(Cliente cliente);

}
