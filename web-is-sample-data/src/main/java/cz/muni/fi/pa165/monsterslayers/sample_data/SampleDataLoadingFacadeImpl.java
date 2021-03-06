package cz.muni.fi.pa165.monsterslayers.sample_data;

import cz.muni.fi.pa165.monsterslayers.entities.*;
import cz.muni.fi.pa165.monsterslayers.enums.HeroStatus;
import cz.muni.fi.pa165.monsterslayers.enums.PowerElement;
import cz.muni.fi.pa165.monsterslayers.enums.RightsLevel;
import cz.muni.fi.pa165.monsterslayers.service.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Controller
@Transactional
public class SampleDataLoadingFacadeImpl implements SampleDataLoadingFacade {

    @Autowired
    private MonsterTypeService monsterTypeService;

    @Autowired
    private UserService userService;

    @Autowired
    private ClientRequestService clientRequestService;

    @Autowired
    private JobService jobService;

    @Autowired
    private HeroService heroService;

    public static final String PNG = "image/png";

    @Autowired
    private PasswordService passwordService;

    @Override
    public void loadData() {
        MonsterType zombie = monsterType("Zombie", "brains", PowerElement.HOLY);
        MonsterType dragon = monsterType("Dragon", "humans", PowerElement.WATER);

        User david = user("Dávid the Client", "david@client.com", "client", RightsLevel.CLIENT, "default.png", PNG);
        User john = user("john", "john@coolmail.com", "iamnotgeorge", RightsLevel.CLIENT, "default.png" , PNG);
        User ondra = user("Ondra the Hero", "ondra@hero.com", "hero", RightsLevel.HERO, "default.png", PNG);
        User maksym = user("Maksym the Manager", "maksym@manager.com", "manager", RightsLevel.MANAGER, "default.png", PNG);
        User tomas = user("Tomas the Hero", "tomas@hero.com", "hero", RightsLevel.HERO, "default.png", PNG);

        List<PowerElement> exHuntEles = new ArrayList<>();
        exHuntEles.add(PowerElement.FIRE);
        exHuntEles.add(PowerElement.WATER);
        exHuntEles.add(PowerElement.WIND);

        Hero exceptionHunter = hero(ondra, "Exception Hunter", exHuntEles, HeroStatus.ACTIVE);

        List<PowerElement> nullCrushEles = new ArrayList<>();
        nullCrushEles.add(PowerElement.EARTH);
        nullCrushEles.add(PowerElement.GHOST);
        nullCrushEles.add(PowerElement.POISON);
        Hero nullCrusher = hero(tomas, "Null Crusher", nullCrushEles, HeroStatus.ACTIVE);

        Map<MonsterType, Integer> justZombiesKL = new HashMap<>();
        justZombiesKL.put(zombie, 4);
        ClientRequest justZombies = clientRequest(
                "Zombies in garden",
                david,
                "It's awful.",
                "garden",
                10,
                justZombiesKL
                );

        Map<MonsterType, Integer> dragonbornKL = new HashMap<>();
        dragonbornKL.put(dragon, 10);
        ClientRequest dragonBorn = clientRequest(
                "Dovahkiin",
                john,
                "Fus ro dah!",
                "Skyrim, obviously",
                424242,
                dragonbornKL
        );

        Map<MonsterType, Integer> hellKL = new HashMap<>();
        hellKL.put(dragon, 100);
        hellKL.put(zombie, 666);
        ClientRequest hell = clientRequest(
                "Hell",
                john,
                "You will need the pick of destiny.",
                "Lee's place",
                666,
                hellKL
        );

        Map<MonsterType, Integer> easyPeasyKL = new HashMap<>();
        easyPeasyKL.put(zombie, 1);
        ClientRequest easyPeasy = clientRequest(
                "Just one zombie",
                john,
                "Crappy hero should be enough.",
                "Unicorn meadow",
                42,
                easyPeasyKL
        );
      
        Map<MonsterType, Integer> pyramidKL = new HashMap<>();
        hellKL.put(zombie, 1000000);
        ClientRequest pyramid = clientRequest(
                "Pyramid Leveling Session",
                john,
                "Get the Aura!",
                "Morroc field 4",
                100,
                pyramidKL
        );
      
        job(exceptionHunter, hell);
        job(exceptionHunter, justZombies);
        job(nullCrusher, easyPeasy);
    }

    private Hero hero(User user, String heroName, Collection<PowerElement> elements, HeroStatus heroStatus) {
        Hero hero = new Hero();
        hero.setUser(user);
        hero.setHeroName(heroName);
        hero.setElements(elements);
        hero.setStatus(heroStatus);
        return heroService.findHeroById(heroService.saveHero(hero));
    }

    private Job job(Hero hero, ClientRequest clientRequest) {
        Job job = new Job();

        job.setAssignee(hero);
        job.setClientRequest(clientRequest);

        return jobService.getJobById(jobService.saveJob(job));
    }

    private User user(String name, String email, String password, RightsLevel rightLevel, String imageFile, String mimeType) {
        User user = new User();

        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordService.createHash(password));
        user.setRightsLevel(rightLevel);
        try {
            user.setImage(readImage(imageFile));
            user.setImageMimeType(mimeType);
        } catch (IOException ex) {
        }
        return userService.findUserById(userService.saveUser(user));
    }

    private ClientRequest clientRequest(String title, User client, String description, String location, int reward, Map<MonsterType, Integer> killList) {
        ClientRequest clientRequest = new ClientRequest();

        clientRequest.setTitle(title);
        clientRequest.setClient(client);
        clientRequest.setDescription(description);
        clientRequest.setLocation(location);
        clientRequest.setReward(new BigDecimal(reward));
        clientRequest.setKillList(killList);

        return clientRequestService.findClientRequestById(clientRequestService.saveClientRequest(clientRequest));
    }

    private MonsterType monsterType(String name, String food, PowerElement weakness) {
        MonsterType monsterType = new MonsterType();

        monsterType.setName(name);
        monsterType.setFood(food);
        //TODO: create monster with multiple weaknesses is currently impossible
        monsterType.addWeakness(weakness);

        return monsterTypeService.findById(monsterTypeService.save(monsterType));
    }

    private byte[] readImage(String file) throws IOException {
        try (InputStream is = this.getClass().getResourceAsStream("/" + file)) {
            int nRead;
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
            return buffer.toByteArray();
        }
    }
}
