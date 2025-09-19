package mss301.giaptd.orchidservice.services;

import mss301.giaptd.orchidservice.pojos.Orchid;
import mss301.giaptd.orchidservice.repositories.OrchidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrchidServiceImpl implements OrchidService {

    @Autowired
    private OrchidRepository iOrchidRepository;

    @Override
    public List<Orchid> getAllOrchids() {
        return (List<Orchid>) iOrchidRepository.findAll();
    }

    @Override
    public Orchid insertOrchid(Orchid orchid) {
        return iOrchidRepository.save(orchid);
    }

    @Override
    public Orchid updateOrchid(int orchidID, Orchid newOrchid) {
        Orchid orchid = iOrchidRepository.getByOrchidId(orchidID);
        if (orchid != null) {
            orchid.setOrchidName(newOrchid.getOrchidName());
            orchid.setNatural(newOrchid.isNatural());
            return iOrchidRepository.save(orchid);
        }
        return orchid;
    }

    @Override
    public void deleteOrchid(int orchidID) {
        iOrchidRepository.deleteById(orchidID);
    }

    @Override
    public Optional<Orchid> getOrchidById(int orchidID) {
        return iOrchidRepository.findById(orchidID);
    }

    @Override
    public void test() { }
}
