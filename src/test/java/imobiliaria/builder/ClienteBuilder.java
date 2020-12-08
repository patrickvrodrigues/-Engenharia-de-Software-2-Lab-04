package imobiliaria.builder;

import imobiliaria.modelo.Cliente;

import java.math.BigDecimal;
import java.time.LocalDate;


public class ClienteBuilder {
    private Cliente cliente;

    private ClienteBuilder(){}

    public static ClienteBuilder umCliente() {
        ClienteBuilder builder = new ClienteBuilder();

        builder.cliente = new Cliente();
        builder.cliente.setNomeCliente("Patrick Viegas");
        builder.cliente.setCpf(new BigDecimal("999999"));
        builder.cliente.setDataNascimento(LocalDate.of(1997,2,25));
        builder.cliente.setEmail("patrickviegas4@gmail.com");
        builder.cliente.setTelefone1(new BigDecimal("999999999"));
        builder.cliente.setTelefone2(new BigDecimal("8888888888"));

        return builder;
    }

    public ClienteBuilder comNome(String nome){
        this.cliente.setNomeCliente(nome);
        return this;
    }

    public Cliente constroi(){
        return this.cliente;
    }
}
