package com.liquibase.services.web.convert;

import com.liquibase.client_entities.CaseThinViewModel;
import com.liquibase.client_entities.ProfileViewModel;
import com.liquibase.entities.CaseProfile;
import com.liquibase.entities.Profile;
import com.liquibase.repositories.CaseProfileDao;
import com.liquibase.repositories.ProfileDao;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProfileVmConverter extends AbstractEntityVmConverter<Profile, ProfileViewModel> {


    private final ProfileDao profileDao;
    private final CaseProfileDao caseProfileDao;


//    @Autowired
//    private CaseVmConverter caseVmConverter;


    public ProfileVmConverter(ProfileDao profileDao, CaseProfileDao caseProfileDao) {
        this.profileDao = profileDao;
        this.caseProfileDao = caseProfileDao;
    }


    @Override
    protected void setEntity(Profile profile, ProfileViewModel profileViewModel) {
        if (profileViewModel == null || profile == null)
            throw new IllegalArgumentException();
        profile.setFirstName(profileViewModel.getFirstName());
        profile.setPhoto(profileViewModel.getPhoto());
    }

    @Override
    protected Profile findById(Long id) {
        return profileDao.findById(id).get();
    }

    @Override
    protected Profile createInstance() {
        return new Profile();
    }

    @Override
    public ProfileViewModel convertToVM(Profile profile) {
        //    public ProfileViewModel convertToVM(Profile profile, boolean includeChildren) {

        if (profile == null) return null;
        ProfileViewModel profileViewModel = new ProfileViewModel();
        profileViewModel.setFirstName(profile.getFirstName());
        profileViewModel.setId(profile.getId());
        profileViewModel.setPhoto(profile.getPhoto());
//        if (includeChildren) {
//            List<CaseProfile> allByProfile = caseProfileDao.getAllByProfile(profile.getId());
//            List<CaseViewModel> caseList = allByProfile.stream()
//                    .map(CaseProfile::getCase)
//                    .map(e -> caseVmConverter.convertToVM(e, false))
//                    .collect(Collectors.toList());
//            profileViewModel.setCaseList(caseList);
//        }


        List<CaseProfile> allByProfile = caseProfileDao.getAllByProfile(profile.getId());
        List<CaseThinViewModel> caseThinViewModelList = allByProfile.stream()
                .map(caseProfile -> new CaseThinViewModel(caseProfile.getCase().getId(), caseProfile.getCase().getName()))
                .sorted(Comparator.comparing(CaseThinViewModel::getName))
                .collect(Collectors.toList());

        profileViewModel.setCaseThinViewModelList(caseThinViewModelList);

        return profileViewModel;
    }


}
