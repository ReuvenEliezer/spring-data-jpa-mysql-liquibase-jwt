package Tests;

import com.liquibase.entities.Case;
import com.liquibase.entities.CaseProfile;
import com.liquibase.entities.Profile;
import com.liquibase.repositories.CaseDao;
import com.liquibase.repositories.CaseProfileDao;
import com.liquibase.repositories.ProfileDao;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class CasesTest extends AbstractTest {

    @Autowired
    private CaseDao caseDao;

    @Autowired
    private ProfileDao profileDao;

    @Autowired
    private CaseProfileDao caseProfileDao;


    @Test
    public void caseProfileTest() {
        Profile profile1 = new Profile();
        profile1.setFirstName("profile 1");
        profile1.setPhoto("photo.jpg");
        profile1 = profileDao.save(profile1);
        System.out.println(profile1);

        Profile profile2 = new Profile();
        profile1.setFirstName("profile 2");
        profile1.setPhoto("photo.jpg");
        profile2 = profileDao.save(profile2);
        System.out.println(profile2);

        Case aCase = new Case();
        aCase.setName("case a");
        aCase = caseDao.save(aCase);
        System.out.println(aCase);

        Case aCaseB = new Case();
        aCaseB.setName("case b");
        aCaseB = caseDao.save(aCaseB);
        System.out.println(aCaseB);

        CaseProfile caseProfile = new CaseProfile(profile1, aCase);
        CaseProfile save = caseProfileDao.save(caseProfile);
        Profile profile = profileDao.findById(save.getProfile().getId()).get();
        System.out.println(profile.getFirstName());
        Assert.assertEquals(2, caseDao.findAll().size());
//        Assert.assertEquals(1, profile.getRatings().size());
        Case courseRatings = caseDao.findById(save.getCase().getId()).get();
        System.out.println(courseRatings.getName());
//        Assert.assertEquals(1, courseRatings.getRatings().size());

        caseProfileDao.delete(caseProfile);
        profileDao.delete(caseProfile.getPk().getProfile());
        Optional<Profile> byId = profileDao.findById(save.getProfile().getId());
        Assert.assertFalse(byId.isPresent());

        List<CaseProfile> all = caseProfileDao.findAll();
        CaseProfile caseProfile1 = caseProfileDao.getCaseProfile(aCase.getId(), profile1.getId());
    }

}
