package mss301.giaptd.orchidservice.configs;

import mss301.giaptd.orchidservice.pojos.Category;
import mss301.giaptd.orchidservice.pojos.Orchid;
import mss301.giaptd.orchidservice.repositories.CategoryRepository;
import mss301.giaptd.orchidservice.repositories.OrchidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private OrchidRepository orchidRepository;

    @Override
    public void run(String... args) throws Exception {
        if (categoryRepository.count() > 0 || orchidRepository.count() > 0) {
            System.out.println("Data already exists, skipping initialization");
            return;
        }

        Category naturalCategory = new Category();
        naturalCategory.setCategoryName("Natural");
        categoryRepository.save(naturalCategory);

        Category hybridCategory = new Category();
        hybridCategory.setCategoryName("Hybrid");
        categoryRepository.save(hybridCategory);

        Orchid orchid1 = new Orchid();
        orchid1.setOrchidName("Phalaenopsis amabilis");
        orchid1.setNatural(true);
        orchid1.setPrice(1000);
        orchid1.setOrchidDescription("The Moth Orchid is a natural species from Southeast Asia");
        orchid1.setOrchidURL("phalaenopsis-amabilis.jpg");
        orchid1.setCategory(naturalCategory);
        orchidRepository.save(orchid1);

        Orchid orchid2 = new Orchid();
        orchid2.setOrchidName("Dendrobium Hybrid");
        orchid2.setNatural(false);
        orchid2.setPrice(2000);
        orchid2.setOrchidDescription("A beautiful hybrid Dendrobium variety");
        orchid2.setOrchidURL("dendrobium-hybrid.jpg");
        orchid2.setCategory(hybridCategory);
        orchidRepository.save(orchid2);

        System.out.println("Categories and Orchids initialized successfully");
    }
}