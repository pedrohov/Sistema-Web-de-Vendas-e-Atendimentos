package modelo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ATENDENTE")
public class Atendente extends Usuario {

	private static final long serialVersionUID = 1L;

    public Atendente() {
        super();
    }
    
}
