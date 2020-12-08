package imobiliaria.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Imovel {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String tipoImovel;
    private String endereco;
    private String bairro;
    private BigDecimal cep;
    private BigDecimal metragem;
    private Integer dormitorios;
    private Integer banheiros;
    private Integer suites;
    private Integer vagasGaragem;
    private BigDecimal valorAluguelSugerido;
    private String obs;

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getTipoImovel() { return tipoImovel; }

    public void setTipoImovel(String tipoImovel) { this.tipoImovel = tipoImovel; }

    public String getEndereco() { return endereco; }

    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getBairro() { return bairro; }

    public void setBairro(String bairro) { this.bairro = bairro; }

    public BigDecimal getCep() { return cep; }

    public void setCep(BigDecimal cep) { this.cep = cep; }

    public BigDecimal getMetragem() { return metragem; }

    public void setMetragem(BigDecimal metragem) { this.metragem = metragem; }

    public Integer getDormitorios() { return dormitorios; }

    public void setDormitorios(Integer dormitorios) { this.dormitorios = dormitorios; }

    public Integer getBanheiros() { return banheiros; }

    public void setBanheiros(Integer banheiros) { this.banheiros = banheiros; }

    public Integer getSuites() { return suites; }

    public void setSuites(Integer suites) { this.suites = suites; }

    public Integer getVagasGaragem() { return vagasGaragem; }

    public void setVagasGaragem(Integer vagasGaragem) { this.vagasGaragem = vagasGaragem; }

    public BigDecimal getValorAluguelSugerido() { return valorAluguelSugerido; }

    public void setValorAluguelSugerido(BigDecimal valorAluguelSugerido) {this.valorAluguelSugerido = valorAluguelSugerido; }

    public String getObs() { return obs; }

    public void setObs(String obs) { this.obs = obs; }
}
