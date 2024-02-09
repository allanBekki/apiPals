package com.example.demo.controller;

import com.example.demo.beans.Pals;
import com.example.demo.beans.Skills;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/pals")
public class PalsController
{
    private final MongoTemplate mongoTemplate;

    public PalsController(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @GetMapping
    public List<Pals> getPals() {
        return mongoTemplate.findAll(Pals.class);
    }

    @PostMapping
    public void savePals(@RequestBody Pals pals) {
        mongoTemplate.save(pals);
    }

    @GetMapping("/{id}")
    public Pals getId(@PathVariable int id)
    {
        return mongoTemplate.findById(id, Pals.class);
    }

    @GetMapping("getName/{name}")
    public Pals getName(@PathVariable String name)
    {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        return mongoTemplate.findOne(query, Pals.class);
    }

    @GetMapping("getType/{type}")
    public List<Pals> getType(@PathVariable String type)
    {
        Query query = new Query();
        query.addCriteria(Criteria.where("types").is(type));
        return mongoTemplate.find(query, Pals.class);
    }

    @PostMapping("/save/pal")
    public ResponseEntity<String> savePal(@RequestBody Pals pals) {
        try {
            mongoTemplate.save(pals);
            return ResponseEntity.ok("Pals saved successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save Pal: " + e.getMessage());
        }
    }

    @GetMapping("/{id}/skills")
    public ResponseEntity<List<Skills>> getPalSkillsById(@PathVariable int id) {
        Pals pal = mongoTemplate.findById(id, Pals.class);
        if (pal == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pal.getSkills());
    }

    @PostMapping("/{id}/skills/add")
    public ResponseEntity<String> addSkillToPalById(@PathVariable int id, @RequestBody Skills newSkill) {
        Pals pal = mongoTemplate.findById(id, Pals.class);
        if (pal == null) {
            return ResponseEntity.notFound().build();
        }
        pal.getSkills().add(newSkill);
        mongoTemplate.save(pal);
        return ResponseEntity.ok("Skill added to Pal successfully!");
    }

    @PutMapping("/{id}/skills/change/{skillName}")
    public void addSkill(@PathVariable int id, @PathVariable String skillName, @RequestBody Skills updatedSkill) {
        Pals pal = mongoTemplate.findById(id, Pals.class);
        if (pal == null) {
            // Gérer le cas où le pal n'est pas trouvé
            return;
        }

        Skills skillToUpdate = null;
        for (Skills skill : pal.getSkills()) {
            if (skill.getName().equals(skillName)) {
                skillToUpdate = skill;
                break;
            }
        }

        // Modifier le skill si trouvé
        if (skillToUpdate != null) {
            // Mettre à jour les propriétés du skill avec les nouvelles valeurs
            skillToUpdate.setName(updatedSkill.getName());
            skillToUpdate.setType(updatedSkill.getType());
            skillToUpdate.setCooldown(updatedSkill.getCooldown());
            skillToUpdate.setPower(updatedSkill.getPower());
            skillToUpdate.setDescription(updatedSkill.getDescription());
            // Autres propriétés à mettre à jour si nécessaire

            // Enregistrer les modifications dans la base de données
            mongoTemplate.save(pal);
        }
    }

    @GetMapping("/{id}/types")
    public ResponseEntity<List<String>> getTypesOfPalById(@PathVariable int id) {
        Pals pal = mongoTemplate.findById(id, Pals.class);
        if (pal == null) {
            return ResponseEntity.notFound().build();
        }
        List<String> types = pal.getTypes();
        return ResponseEntity.ok(types);
    }

    @PostMapping("/{id}/types/add/{type}")
    public ResponseEntity<String> addTypeToPalById(@PathVariable int id, @PathVariable String type) {
        Pals pal = mongoTemplate.findById(id, Pals.class);
        if (pal == null) {
            return ResponseEntity.notFound().build();
        }
        if (!pal.getTypes().contains(type)) {
            pal.getTypes().add(type);
            mongoTemplate.save(pal);
            return ResponseEntity.ok("Type added successfully!");
        } else {
            return ResponseEntity.badRequest().body("Type already exists for this Pal.");
        }
    }

    @DeleteMapping("/{id}/types/delete/{type}")
    public ResponseEntity<String> removeTypeFromPalById(@PathVariable int id, @PathVariable String type) {
        Pals pal = mongoTemplate.findById(id, Pals.class);
        if (pal == null) {
            return ResponseEntity.notFound().build();
        }
        List<String> types = pal.getTypes();
        if (types.contains(type)) {
            types.remove(type);
            pal.setTypes(new ArrayList<>(types)); // Mise à jour de la liste entière
            mongoTemplate.save(pal);
            return ResponseEntity.ok("Type removed successfully!");
        } else {
            return ResponseEntity.badRequest().body("Type does not exist for this Pal.");
        }
    }
    @GetMapping("/get/{rarity}")
    public List<Pals> getPalsByRarity(@PathVariable int rarity) {
        return mongoTemplate.findAll(Pals.class).stream()
                .filter(pal -> pal.getRarity() == rarity)
                .toList();
    }

    @GetMapping("/get/{price}")
    public List<Pals> getPalsByPrice(@PathVariable String price) {
        return mongoTemplate.findAll(Pals.class).stream()
                .filter(pal -> Objects.equals(pal.getPrice(), price))
                .toList();
    }

    @GetMapping("/filterByRarity")
    public List<Pals> filterByRarity() {
        Query query = new Query();
        List<Pals> palList = mongoTemplate.find(query, Pals.class);
        palList.sort(Comparator.comparing(Pals::getRarity));
        return palList;
    }

    @GetMapping("/filterByPrice")
    public List<Pals> filterByPrice() {
        Query query = new Query();
        List<Pals> palList = mongoTemplate.find(query, Pals.class);
        palList.sort(Comparator.comparing(Pals::getPrice));
        return palList;
    }
}
