package controller;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.joda.time.DateTime;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import modelo.Cliente;
import modelo.Venda;
import service.ClienteService;
import service.VendaService;
import util.NegocioException;

@ManagedBean(name="graficos")
@SessionScoped
public class GraficoViewBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private BarChartModel modeloVendas;
	private VendaService service = new VendaService();
	private List<Venda> vendas;
	private List<Venda> topVendas;
	private Venda venda = new Venda();
	private Cliente cliente = new Cliente();
	private Float max;
	private Integer ano;
	private ClienteService cService = new ClienteService();
	private List<Cliente> clientes;
	
	public GraficoViewBean() {
		setVendas(service.buscarTodos());
		setClientes(cService.buscarTodos());
		setTopVendas(service.maiorLucro(5));
		max = 0f;
		ano = new DateTime().getYear();
		createModeloVendas();
	}
	
	public void preRender(ComponentSystemEvent e) {
		setVendas(service.buscarTodos());
		setClientes(cService.buscarTodos());
		setTopVendas(service.maiorLucro(5));
	}
	
	public void createModeloVendas() {
		modeloVendas = initModeloVendas();
 
		modeloVendas.setTitle("");
		modeloVendas.setLegendPosition("ne");
 
        Axis xAxis = modeloVendas.getAxis(AxisType.X);
        xAxis.setLabel("Mês/Ano");
 
        Axis yAxis = modeloVendas.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(max);
    }
	
	public BarChartModel initModeloVendas() {
        BarChartModel model = new BarChartModel();
 
        ChartSeries lVendas = new ChartSeries();
        lVendas.setLabel("Mês/Ano");
        setVendas(service.buscarTodos());
        
        Map<String, Float> meses = new LinkedHashMap<String, Float>();
        DateTime atual = new DateTime();
        int ano = atual.getYear();
        
        // 
        meses.put("jan/" + ano, 0f);
        meses.put("feb/" + ano, 0f);
        meses.put("mar/" + ano, 0f);
        meses.put("abr/" + ano, 0f);
        meses.put("mai/" + ano, 0f);
        meses.put("jun/" + ano, 0f);
        meses.put("jul/" + ano, 0f);
        meses.put("ago/" + ano, 0f);
        meses.put("set/" + ano, 0f);
        meses.put("out/" + ano, 0f);
        meses.put("nov/" + ano, 0f);
        meses.put("dez/" + ano, 0f);
        
        //
        for(int i = 0; i < vendas.size(); i++) {
        	Float novoValor = vendas.get(i).getTotal();
        	String key  = vendas.get(i).getMesAno();
        	Float valor = meses.get(key);
        	
        	if(valor != null) {
        		if(novoValor + valor > max)
        			max = novoValor + valor;
        		
        		meses.put(key, novoValor + valor);
        	}
        }
        
        for (Map.Entry<String, Float> entry : meses.entrySet())
        	lVendas.set(entry.getKey(), entry.getValue());
        	
        model.addSeries(lVendas);
 
        return model;
	}
	
	public StreamedContent getFoto() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getRenderResponse()) {
            // So, we're rendering the view. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        }
        else {
            // So, browser is requesting the image. Get ID value from actual request param.
            String id = context.getExternalContext().getRequestParameterMap().get("id");
			
			try {
				Cliente c = cService.buscarPorCodigo(Integer.valueOf(id));
				if((c == null) || (c.getFoto() == null))
					return new DefaultStreamedContent();
				return new DefaultStreamedContent(new ByteArrayInputStream(c.getFoto()));
			} catch (NumberFormatException e) {
				return new DefaultStreamedContent();
			} catch (NegocioException e) {
				return new DefaultStreamedContent();
			}
			
        }
    }

	public BarChartModel getModeloVendas() {
		return modeloVendas;
	}

	public void setModeloVendas(BarChartModel modeloVendas) {
		this.modeloVendas = modeloVendas;
	}

	public List<Venda> getVendas() {
		return vendas;
	}

	public void setVendas(List<Venda> vendas) {
		this.vendas = vendas;
	}

	public Float getMax() {
		return max;
	}

	public void setMax(Float max) {
		this.max = max;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public List<Venda> getTopVendas() {
		return topVendas;
	}

	public void setTopVendas(List<Venda> topVendas) {
		this.topVendas = topVendas;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

}