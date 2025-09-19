package mss301.giaptd.orchidservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import mss301.giaptd.orchidservice.pojos.Orchid;
import mss301.giaptd.orchidservice.services.OrchidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/orchids")
@Tag(name = "Orchid", description = "Orchid management APIs")
public class OrchidController {

    @Autowired
    private OrchidService orchidService;

    @Operation(summary = "Get all orchids")
    @GetMapping
    public List<Orchid> getAllOrchids() {
        return orchidService.getAllOrchids();
    }

    @Operation(summary = "Create a new orchid")
    @ApiResponse(responseCode = "201", description = "Orchid created successfully")
    @PostMapping
    public Orchid insertOrchid(@RequestBody Orchid orchid) {
        return orchidService.insertOrchid(orchid);
    }

    @Operation(summary = "Update an orchid by ID")
    @PutMapping("/{id}")
    public Orchid updateOrchid(
            @Parameter(description = "Orchid ID") @PathVariable int id,
            @RequestBody Orchid orchid) {
        return orchidService.updateOrchid(id, orchid);
    }

    @Operation(summary = "Delete an orchid by ID")
    @DeleteMapping("/{id}")
    public void deleteOrchid(
            @Parameter(description = "Orchid ID") @PathVariable int id) {
        orchidService.deleteOrchid(id);
    }

    @Operation(summary = "Get an orchid by ID")
    @GetMapping("/{id}")
    public Orchid getOrchidById(
            @Parameter(description = "Orchid ID") @PathVariable int id) {
        return orchidService.getOrchidById(id).orElse(null);
    }
}
