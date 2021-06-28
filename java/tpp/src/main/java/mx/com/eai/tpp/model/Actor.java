
/*
 * Copyright (C) 2017 Ashley Heyeral Ruiz Fernandez
 *
 * The SOFTWARE PRODUCT includes entitlement to limited technical support services  
 * support services
 * SOFTWARE PRODUCT will perform substantially in accordance with 
 * the accompanying written materials.
 */
package mx.com.eai.tpp.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/* *****************************************************************************
 * @author grc  13-jul-2013 12:37:42  Description: 
 * Actor.java  
 * *****************************************************************************
 */
public class Actor implements UserDetails
{

    private List<GrantedAuthority> roles;
    private Dusuario dUsuario;

    public Actor()
    {
        this.roles = new ArrayList<GrantedAuthority>();
    }

    public Collection<GrantedAuthority> getAuthorities()
    {
        return getRoles();
    }

    public String getPassword()
    {
        return this.dUsuario.getContrasenia();
    }

    public String getUsername()
    {
        return this.dUsuario.getUsuario();
    }

    public boolean isAccountNonExpired()
    {
        int indexAccountNonExpired = this.dUsuario == null ? 0 : this.dUsuario.getCuentaexpirada();
//        return indexAccountNonExpired <= 0;
        return true;
    }

    public boolean isAccountNonLocked()
    {
        int indexAccountNonLocked = this.dUsuario == null ? 0 : this.dUsuario.getCuentabloqueada();
        return indexAccountNonLocked <= 0;
    }

    public boolean isCredentialsNonExpired()
    {
        int indexCredentialsNonExpired = this.dUsuario == null ? 0 : this.dUsuario.getCredencialexpirada();
//        return indexCredentialsNonExpired <= 0;
        return true;
    }

    public boolean isEnabled()
    {
        int indexEnabled = this.dUsuario == null ? 0 : this.dUsuario.getActivo();
        return indexEnabled <= 0;
    }

    public List<GrantedAuthority> getRoles()
    {
        return roles;
    }

    public void setRoles(List<GrantedAuthority> roles)
    {
        this.roles = roles;
    }

    /**
     * @return the dUsuario
     */
    public Dusuario getdUsuario()
    {
        return dUsuario;
    }

    /**
     * @param dUsuario the dUsuario to set
     */
    public void setdUsuario(Dusuario dUsuario)
    {
        this.dUsuario = dUsuario;
    }
}
