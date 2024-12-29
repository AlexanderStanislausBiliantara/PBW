package com.example.WombatFm.Gallery;

import java.util.List;

public interface GalleryRepository {
    List<Gallery> getShowImages();

    int addShowImage(Gallery gallery);

    int addShowImages(List<Gallery> galleries);
}
