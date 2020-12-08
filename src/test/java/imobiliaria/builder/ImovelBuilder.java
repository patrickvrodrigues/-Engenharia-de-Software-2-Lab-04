package imobiliaria.builder;

import imobiliaria.modelo.Imovel;

import java.math.BigDecimal;

public class ImovelBuilder {
    private Imovel imovel;

    private ImovelBuilder(){}

    public static ImovelBuilder umImovel() {
        ImovelBuilder builder = new ImovelBuilder();
        builder.imovel = new Imovel();
        builder.imovel.setTipoImovel("Apartamento");
        builder.imovel.setEndereco("Rua da estrela, 243");
        builder.imovel.setBairro("São Francisco");
        builder.imovel.setCep(new BigDecimal("65000000"));
        builder.imovel.setMetragem(new BigDecimal("54.5"));
        builder.imovel.setDormitorios(3);
        builder.imovel.setSuites(2);
        builder.imovel.setBanheiros(1);
        builder.imovel.setVagasGaragem(1);
        builder.imovel.setValorAluguelSugerido(new BigDecimal("1200"));
        builder.imovel.setObs("");


        return builder;
    }
    public ImovelBuilder comBairro(String bairro){
        this.imovel.setBairro(bairro);
        return this;
    }
    public ImovelBuilder comValorSugerido(BigDecimal valor){
        this.imovel.setValorAluguelSugerido(valor);
        return this;
    }

    public Imovel constroi(){
        return this.imovel;
    }
}
