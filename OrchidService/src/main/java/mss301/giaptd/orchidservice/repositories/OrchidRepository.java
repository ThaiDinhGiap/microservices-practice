package mss301.giaptd.orchidservice.repositories;

import mss301.giaptd.orchidservice.pojos.Orchid;
import org.springframework.data.repository.CrudRepository;

public interface OrchidRepository extends CrudRepository<Orchid, Integer> {
    Orchid getByOrchidId(int orchidID);
}
