package modelo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.joda.time.DateTime;

@Entity
public class Atendimento implements Serializable {
    
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    private String comentarios;
    @ManyToOne
    @JoinColumn(name="codServico")
    private Servico servico;
    @ManyToOne
    @JoinColumn(name="codCliente")
    private Cliente cliente;
    @ManyToOne
    @JoinColumn(name="codAtendente")
    private Atendente atendente;
    
    public Atendimento() {

    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Servico getAtendimento() {
        return servico;
    }

    public void setAtendimento(Servico servico) {
        this.servico = servico;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public Atendente getAtendente() {
        return atendente;
    }

    public void setAtendente(Atendente atendente) {
        this.atendente = atendente;
    }
    
    public String getDia() {
    	Calendar c = Calendar.getInstance();
    	c.setTime(this.data);
    	String res = Integer.toString(c.get(Calendar.DAY_OF_MONTH));
    	if(res.length() == 1)
    		res = "0" + res;
    	
    	return res;
    }
    
    public String getDiaSemana() {
    	Calendar c = Calendar.getInstance();
    	c.setTime(this.data);
    	int semana = c.get(Calendar.DAY_OF_WEEK);
    	String res = "";
    	switch(semana) {
    		case Calendar.MONDAY:
    			res = "Segunda";
    			break;
    		case Calendar.TUESDAY:
    			res = "Terça";
    			break;
    		case Calendar.WEDNESDAY:
    			res = "Quarta";
    			break;
    		case Calendar.THURSDAY:
    			res = "Quinta";
    			break;
    		case Calendar.FRIDAY:
    			res = "Sexta";
    			break;
    		case Calendar.SATURDAY:
    			res = "Sábado";
    			break;
    		case Calendar.SUNDAY:
    			res = "Domingo";
    			break;
    	}
    	return res;
    }
    
    public String getMes() {
    	Calendar c = Calendar.getInstance();
    	c.setTime(this.data);
    	int mes = c.get(Calendar.MONTH);
    	String res = "";
    	switch(mes) {
    		case Calendar.JANUARY:
    			res = "JAN";
    			break;
    		case Calendar.FEBRUARY:
    			res = "FEV";
    			break;
    		case Calendar.MARCH:
    			res = "MAR";
    			break;
    		case Calendar.APRIL:
    			res = "ABR";
    			break;
    		case Calendar.MAY:
    			res = "MAY";
    			break;
    		case Calendar.JUNE:
    			res = "JUN";
    			break;
    		case Calendar.JULY:
    			res = "JUL";
    			break;
    		case Calendar.AUGUST:
    			res = "AGO";
    			break;
    		case Calendar.SEPTEMBER:
    			res = "SET";
    			break;
    		case Calendar.OCTOBER:
    			res = "OUT";
    			break;
    		case Calendar.NOVEMBER:
    			res = "NOV";
    			break;
    		case Calendar.DECEMBER:
    			res = "DEZ";
    			break;
    	}
    	
    	return res;
    }
    
    public String getInicio() {
    	DateTime dt = new DateTime(this.data);
    	String hour = Integer.toString(dt.getHourOfDay());
    	String minute = Integer.toString(dt.getMinuteOfHour());
    	if(hour.length() == 1)
    		hour = "0" + hour;
    	if(minute.length() == 1)
    		minute = "0" + minute;
    	
    	return hour + ":" + minute;
    }
    
    public String getFim() {
    	DateTime dt = new DateTime(this.data);
    	dt = dt.plusMinutes(servico.getDuracao());
    	
    	String hour = Integer.toString(dt.getHourOfDay());
    	String minute = Integer.toString(dt.getMinuteOfHour());
    	if(hour.length() == 1)
    		hour = "0" + hour;
    	if(minute.length() == 1)
    		minute = "0" + minute;
    	
    	return hour + ":" + minute;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.codigo);
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
        final Atendimento other = (Atendimento) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }
}
