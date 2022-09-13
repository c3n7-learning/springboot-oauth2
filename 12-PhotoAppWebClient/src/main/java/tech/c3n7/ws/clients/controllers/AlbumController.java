package tech.c3n7.ws.clients.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import tech.c3n7.ws.clients.response.AlbumRest;

import java.util.Arrays;
import java.util.List;

@Controller
public class AlbumController {

    @GetMapping("/albums")
    public String getAlbums(Model model) {

        AlbumRest album = new AlbumRest();
        album.setAlbumId("albumOne");
        album.setAlbumTitle("Album one title");
        album.setAlbumUrl("http://localhost:8082/albums/1");

        AlbumRest album2 = new AlbumRest();
        album2.setAlbumId("albumTwo");
        album2.setAlbumTitle("Album two title");
        album2.setAlbumUrl("http://localhost:8082/albums/2");

        List returnValue = Arrays.asList(album, album2);

        model.addAttribute("albums", returnValue);

        return "albums";
    }
}
