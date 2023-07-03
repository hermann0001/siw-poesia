package it.uniroma3.siw.poesia.siwpoesia0.controller.session;
import it.uniroma3.siw.poesia.siwpoesia0.model.Autore;
import it.uniroma3.siw.poesia.siwpoesia0.model.Credentials;
import it.uniroma3.siw.poesia.siwpoesia0.service.AutoreService;
import it.uniroma3.siw.poesia.siwpoesia0.service.CredenzialeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@Scope(value="session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionData {

    private Autore autore;
    private Credentials credentials;
    @Autowired
    private CredenzialeService credentialsService;
    @Autowired
    private AutoreService autoreService;

    public Credentials getLoggedCredentials(){
        this.update();

        return this.credentials;
    }

    public Autore getLoggedUser(){

        try{
            this.update();
        }catch(ClassCastException e){
            return null;
        }
        return this.autore;
    }

    private void update(){
        Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails loggedUserDetails = (UserDetails) object;

        this.credentials = this.credentialsService.getCredentials(loggedUserDetails.getUsername());
        this.autore = this.credentials.getAutore();
    }

/*    private void oauth2update(){
        Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomOAuth2User loggedOAuth2User = (CustomOAuth2User) object;

        try {
            this.user = userRepository.findByName(loggedOAuth2User.getLogin()).get();
        }
        catch( NoSuchElementException e ){
            this.user = userRepository.findByName(loggedOAuth2User.getFullName()).get();
        }
    }*/


}
