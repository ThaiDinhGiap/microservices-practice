package mss301.giaptd.orchidservice.services;

import mss301.giaptd.orchidservice.pojos.Orchid;

import java.util.List;
import java.util.Optional;

public interface OrchidService {
    List<Orchid> getAllOrchids();

    Orchid insertOrchid(Orchid orchid);

    Orchid updateOrchid(int orchidID, Orchid orchid);

    void deleteOrchid(int orchidID);

    Optional<Orchid> getOrchidById(int orchidID);

    void test();
}
