package cz.muni.fi.pa165.monsterslayers.facade;

import cz.muni.fi.pa165.monsterslayers.dto.hero.CreateHeroDTO;
import cz.muni.fi.pa165.monsterslayers.dto.hero.HeroDTO;
import cz.muni.fi.pa165.monsterslayers.dto.hero.ModifyHeroDTO;
import cz.muni.fi.pa165.monsterslayers.enums.HeroStatus;

import java.util.Collection;

/**
 * Facade layer interface for hero
 *
 * @author Tomáš Richter
 */
public interface HeroFacade {
    /**
     * Gets hero with specific id as DTO
     *
     * @param heroId specific id
     * @return single DTO
     */
    HeroDTO getHeroById(Long heroId);

    /**
     * Gets hero with specific name as DTO
     *
     * @param heroName specific hero name
     * @return single DTO
     */
    HeroDTO getHeroByName(String heroName);

    /**
     * Gets all heroes as collection of DTOs
     *
     * @return collection of DTOs
     */
    Collection<HeroDTO> getAllHeroes();

    /**
     * Removes hero according to DTO
     *
     * @param heroDTO DTO of hero that will be removed
     */
    void removeHero(HeroDTO heroDTO);

    /**
     * Edits hero according to special DTOs
     *
     * @param modifyHeroDTO DTO for modification of hero
     */
    void editHero(ModifyHeroDTO modifyHeroDTO);

    /**
     * Creates new hero according to special DTO
     *
     * @param heroDTO DTO for creating new hero
     *
     * @return ID of newly created hero
     */
    Long createHero(CreateHeroDTO heroDTO);

    /**
     * Changes user status to given status
     *
     * @param userId id of user
     * @param userStatus new user status
     */
    void changeStatus(Long userId, HeroStatus userStatus);

    /**
     * Finds hero by user ID
     * @param userId if of user
     * @return hero assigned to given user
     */
    HeroDTO getHeroByUserId(Long userId);
}
