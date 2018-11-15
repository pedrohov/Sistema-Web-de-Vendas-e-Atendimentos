package modelo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.joda.time.DateTime;

@Entity
public class Venda implements Serializable {
    
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;
    @ManyToOne
    @JoinColumn(name="codVendedor")
    private Atendente vendedor;
    @ManyToOne
    @JoinColumn(name="codCliente")
    private Cliente cliente;
    @Temporal(TemporalType.DATE)
    private Date data;
    private Float total;
    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VendaProduto> itens = new LinkedList<VendaProduto>();

    public Venda() {
    
    }

    public String getMesAno() {
    	Calendar c = Calendar.getInstance();
    	c.setTime(this.data);
    	int mes = c.get(Calendar.MONTH);
    	String res = "";
    	switch(mes) {
    		case Calendar.JANUARY:
    			res = "jan";
    			break;
    		case Calendar.FEBRUARY:
    			res = "fev";
    			break;
    		case Calendar.MARCH:
    			res = "mar";
    			break;
    		case Calendar.APRIL:
    			res = "abr";
    			break;
    		case Calendar.MAY:
    			res = "may";
    			break;
    		case Calendar.JUNE:
    			res = "jun";
    			break;
    		case Calendar.JULY:
    			res = "jul";
    			break;
    		case Calendar.AUGUST:
    			res = "ago";
    			break;
    		case Calendar.SEPTEMBER:
    			res = "set";
    			break;
    		case Calendar.OCTOBER:
    			res = "out";
    			break;
    		case Calendar.NOVEMBER:
    			res = "nov";
    			break;
    		case Calendar.DECEMBER:
    			res = "dez";
    			break;
    	}
    	
    	DateTime dt = new DateTime(this.data);
    	
    	res = res + "/" + dt.getYear();
    	
    	return res;
    }
    
    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Atendente getVendedor() {
        return vendedor;
    }

    public void setVendedor(Atendente vendedor) {
        this.vendedor = vendedor;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public List<VendaProduto> getItens() {
        return itens;
    }

    public void setItens(List<VendaProduto> itens) {
        this.itens = itens;
    }
    
    public void addItem(VendaProduto item) {
        if (item != null) {
           if (itens == null) {
        	   itens = new LinkedList<VendaProduto>();          
           }
           itens.add(item);
           item.setVenda(this);
        }
     }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.codigo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Venda other = (Venda) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }
    
    
}
