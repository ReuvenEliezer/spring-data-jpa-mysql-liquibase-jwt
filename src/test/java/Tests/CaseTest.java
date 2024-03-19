package Tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.liquibase.client_entities.CaseViewModel;
import com.liquibase.client_entities.ProfileViewModel;
import com.liquibase.entities.Case;
import com.liquibase.entities.CaseProfile;
import com.liquibase.entities.Profile;
import com.liquibase.repositories.CaseDao;
import com.liquibase.repositories.CaseProfileDao;
import com.liquibase.repositories.ProfileDao;
import com.liquibase.utils.WsAddressConstants;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class CaseTest extends AbstractTest {

    @Autowired
    private CaseDao caseDao;

    @Autowired
    private ProfileDao profileDao;

    @Autowired
    private CaseProfileDao caseProfileDao;


    @Before
    public void before() {
        super.setUp();
        caseProfileDao.deleteAll();
        profileDao.deleteAll();
        caseDao.deleteAll();
    }

    @Test
    public void restTest() throws JsonProcessingException {
        CaseViewModel caseA = new CaseViewModel();
        caseA.setName("case a");
        Case aCase = restTemplate.postForObject(localhost + serverPort + WsAddressConstants.caseLogicUrl, caseA, Case.class);
        Assert.assertNotNull(aCase);
        Assert.assertNotNull(aCase.getId());

        String s = objectMapper.writeValueAsString(caseA);
        CaseViewModel caseViewModel = objectMapper.readValue(s, CaseViewModel.class);
        Assert.assertNotNull(caseViewModel);
    }

    @Test
    public void casesVmWithProfilesVmTest() {
        CaseViewModel case3 = new CaseViewModel();
        case3.setName("case 3");
        ProfileViewModel profile1 = createProfileViewModel("profile 1", case3);
        ProfileViewModel profile2 = createProfileViewModel("profile 2", case3);
        List<ProfileViewModel> profiles = List.of(profile1, profile2);
        CaseViewModel case1 = createCaseViewModel("case 1", profiles);
        CaseViewModel case2 = createCaseViewModel("case 2", profiles);
        assertThat(case1.getProfileList()).hasSize(2);
        assertThat(case2.getProfileList()).hasSize(2);
    }

    private ProfileViewModel createProfileViewModel(String profileName, CaseViewModel case3) {
        ProfileViewModel profileViewModel = new ProfileViewModel();
        profileViewModel.setName(profileName);
        profileViewModel.setCaseList(List.of(case3));
        return restTemplate.postForObject(localhost + serverPort + WsAddressConstants.profileLogicUrl, profileViewModel, ProfileViewModel.class);
    }

    private CaseViewModel createCaseViewModel(String caseName, List<ProfileViewModel> profiles) {
        CaseViewModel caseViewModel = new CaseViewModel();
        caseViewModel.setName(caseName);
        caseViewModel.setProfileList(profiles);
        return restTemplate.postForObject(localhost + serverPort + WsAddressConstants.caseLogicUrl, caseViewModel, CaseViewModel.class);
    }

    @Test
    public void auditTest() throws InterruptedException {
        Case caseA = new Case();
        caseA.setName("case a");
        caseA = caseDao.save(caseA);
        Assert.assertEquals(caseA.getCreatedAt(), caseA.getModifiedAt());
        Thread.sleep(2000);
        caseA.setName("case a");
        caseA = caseDao.save(caseA);
        Assert.assertNotEquals(caseA.getCreatedAt(), caseA.getModifiedAt());
        logger.info("caseA: {}", caseA);
    }


    @Test
    public void caseProfileTest() {

        Case caseA = new Case();
        caseA.setName("case a");
        caseA = caseDao.save(caseA);
        logger.info(caseA);

        Case aCaseB = new Case();
        aCaseB.setName("case b");
        aCaseB = caseDao.save(aCaseB);
        logger.info(aCaseB);


        Profile profile1 = new Profile();
        profile1.setName("profile 1");
        profile1.setPhoto("photo.jpg");
        profile1 = profileDao.save(profile1);
        logger.info(profile1);

        Profile profile2 = new Profile();
        profile2.setName("profile 2");
        profile2.setPhoto("photo.jpg");
        profile2 = profileDao.save(profile2);
        logger.info(profile2);


        CaseProfile caseProfile = new CaseProfile(profile1, caseA);
        CaseProfile save = caseProfileDao.save(caseProfile);
        Profile profile = profileDao.findById(save.getProfile().getId()).get();
        logger.info(profile.getName());
        Assert.assertEquals(2, caseDao.findAll().size());
//        Assert.assertEquals(1, profile.getRatings().size());
        Case courseRatings = caseDao.findById(save.getCase().getId()).get();
        logger.info(courseRatings.getName());
        Assert.assertEquals(1, caseProfileDao.findAll().size());

//        caseProfileDao.delete(caseProfile);
//        profileDao.delete(caseProfile.getPk().getProfile());
//        Optional<Profile> byId = profileDao.findById(save.getProfile().getId());
//        Assert.assertFalse(byId.isPresent());

        List<CaseProfile> all = caseProfileDao.findAll();
        CaseProfile caseProfile1 = caseProfileDao.getCaseProfile(caseA.getId(), profile1.getId());
    }

}
