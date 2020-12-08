package imobiliaria.modelo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Locacao {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade= {CascadeType.MERGE, CascadeType.PERSIST})
    private Imovel imovel;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    private Cliente cliente;

    @OneToMany
    private List<Aluguel> alugueis;

    private BigDecimal valorAluguel;
    private BigDecimal percentualMulta;
    private int diaVencimento;
    private LocalDate dataIncio;
    private LocalDate dataFim;
    private Boolean ativo;
    private String obs;

    public Locacao(){
        alugueis = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Imovel getImovel() {
        return imovel;
    }

    public void setImovel(Imovel imovel) {
        this.imovel = imovel;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public BigDecimal getValorAluguel() {
        return valorAluguel;
    }

    public void setValorAluguel(BigDecimal valorAluguel) {
        this.valorAluguel = valorAluguel;
    }

    public BigDecimal getPercentualMulta() {
        return percentualMulta;
    }

    public void setPercentualMulta(BigDecimal percentualMulta) {
        this.percentualMulta = percentualMulta;
    }

    public int getDiaVencimento() {
        return diaVencimento;
    }

    public void setDiaVencimento(int diaVencimento) {
        this.diaVencimento = diaVencimento;
    }

    public LocalDate getDataIncio() {
        return dataIncio;
    }

    public void setDataIncio(LocalDate dataIncio) {
        this.dataIncio = dataIncio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public void setAlugueis(List<Aluguel> aluguel){
        this.alugueis = aluguel;
    }

    public List<Aluguel> getAlugueis(){
        return this.alugueis;
    }

    public Long getPeriodo(){
        long diferenca = ChronoUnit.MONTHS.between(dataIncio,dataFim);
        return diferenca;
    }
}
