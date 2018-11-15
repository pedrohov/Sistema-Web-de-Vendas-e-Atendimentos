package security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import modelo.Usuario;
import service.UsuarioService;

public class AppUserDetailsService implements UserDetailsService {

		@Override
		public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
			UsuarioService usuarios = new UsuarioService();
			Usuario usuario = usuarios.buscarPorLogin(login);
			
			UsuarioSistema user = null;
			
			if (usuario != null) {
				user = new UsuarioSistema(usuario, getGrupos(usuario));
			}
			
			return user;
		}

		private Collection<? extends GrantedAuthority> getGrupos(Usuario usuario) {
			List<SimpleGrantedAuthority> authorities = new ArrayList<>();
			
			for (String grupo : usuario.getPermissao()) {
				authorities.add(new SimpleGrantedAuthority(grupo.toUpperCase()));
			}
			
			return authorities;
	}	
	
}
