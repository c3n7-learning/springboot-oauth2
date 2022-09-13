package tech.c3n7.ws.clients.controllers;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import tech.c3n7.ws.clients.response.AlbumRest;

import java.util.List;

@Controller
public class AlbumController {

    @Autowired
    OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

//    @Autowired
//    RestTemplate restTemplate;

    @Autowired
    WebClient webClient;

    @GetMapping("/albums")
    public String getAlbums(Model model) {

        String url = "http://localhost:8082/albums";

        List<AlbumRest> albums = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<AlbumRest>>() {})
                .block();

        model.addAttribute("albums", albums);

        return "albums";
    }

    @GetMapping("/albums-alt")
    public String getAlbumsAlt(Model model, @AuthenticationPrincipal OidcUser principal
           // , Authentication authentication
    ) {

        // Get the authentication object details
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Since it is an oauth token, upcast it
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;

        // use the servce to load the oauth2client object
        OAuth2AuthorizedClient oauth2Client = oAuth2AuthorizedClientService.loadAuthorizedClient(oauthToken.getAuthorizedClientRegistrationId(),
                oauthToken.getName());

        String jwtAccessToken = oauth2Client.getAccessToken().getTokenValue();
        System.out.println("jwtAccessToken: " + jwtAccessToken);

        System.out.println("Principal: " + principal);

        OidcIdToken idToken = principal.getIdToken();
        String idTokenValue = idToken.getTokenValue();

        System.out.println("idTokenValue: " + idTokenValue);

        String url = "http://localhost:8082/albums";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwtAccessToken);
        HttpEntity<List<AlbumRest>> entity = new HttpEntity<>(httpHeaders);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<AlbumRest>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity,
                new ParameterizedTypeReference<List<AlbumRest>>() {});

        List<AlbumRest> albums = responseEntity.getBody();

//        AlbumRest album = new AlbumRest();
//        album.setAlbumId("albumOne");
//        album.setAlbumTitle("Album one title");
//        album.setAlbumUrl("http://localhost:8082/albums/1");
//
//        AlbumRest album2 = new AlbumRest();
//        album2.setAlbumId("albumTwo");
//        album2.setAlbumTitle("Album two title");
//        album2.setAlbumUrl("http://localhost:8082/albums/2");

//        List returnValue = Arrays.asList(album, album2);

        model.addAttribute("albums", albums);

        return "albums";
    }
}
