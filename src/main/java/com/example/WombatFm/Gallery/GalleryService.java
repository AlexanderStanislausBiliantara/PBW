package com.example.WombatFm.Gallery;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GalleryService {
    @Autowired
    private GalleryRepository galleryRepository;

    public List<Gallery> getShowImages() {
        return galleryRepository.getShowImages();
    };

    public int addShowImage(Gallery gallery) {
        return galleryRepository.addShowImage(gallery);
    };

    public int addShowImages(List<Gallery> galleries) {
        return galleryRepository.addShowImages(galleries);
    };
}
