package org.bcbs.microservice.organization.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.bcbs.microservice.constant.DataView;
import org.bcbs.microservice.controller.GenericController;
import org.bcbs.microservice.controller.GenericResponse;
import org.bcbs.microservice.exception.DataNotFoundException;
import org.bcbs.microservice.organization.dal.model.*;
import org.bcbs.microservice.organization.service.def.InstitutionService;
import org.bcbs.microservice.organization.service.def.PresidentService;
import org.bcbs.microservice.organization.service.def.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/institution")
@ResponseBody
class InstitutionController extends GenericController<Institution, Integer, InstitutionService> {

    private final PresidentService presidentService;
    private final SchoolService schoolService;

    @Autowired
    public InstitutionController(InstitutionService institutionService, PresidentService presidentService,
                                 SchoolService schoolService) {
        super(institutionService);

        this.presidentService = presidentService;
        this.schoolService = schoolService;
    }

    // Begin of president api.
    @GetMapping(path = "/{id}/president")
    @JsonView(value = {DataView.BasicView.class})
    public GenericResponse getPresident(@PathVariable final Integer id) throws DataNotFoundException {
        return GenericResponse.success(this.service.findById(id).getPresidents());
    }

    @PostMapping(path = "/{id}/president")
    @JsonView(value = {DataView.TypicalView.class})
    public GenericResponse newPresident(@PathVariable final Integer id,
                                        @JsonView(value = {DataView.ExtensiveView.class})
                                        @Validated @RequestBody President president)
            throws DataNotFoundException {
        president.setInstitution(this.service.findById(id));
        return GenericResponse.success(this.presidentService.save(president));
    }

    @PutMapping(path = "/{id}/president/{pid}")
    @JsonView(value = {DataView.TypicalView.class})
    public GenericResponse addPresident(@PathVariable final Integer id, @PathVariable final Integer pid)
            throws DataNotFoundException {
        President president = this.presidentService.findById(pid);
        president.setInstitution(this.service.findById(id));
        return GenericResponse.success(this.presidentService.save(president));
    }

    @DeleteMapping(path = "/{id}/president/{pid}")
    @JsonView(value = {DataView.TypicalView.class})
    public GenericResponse removePresident(@PathVariable final Integer id, @PathVariable final Integer pid)
            throws DataNotFoundException {
        President president = this.presidentService.findOne((root, cq, cb) ->
                cb.and(cb.equal(root.get(School_.id), pid),
                        cb.equal(root.join(President_.institution).get(Institution_.id), id)));
        president.setInstitution(null);
        return GenericResponse.success(this.presidentService.save(president));
    }

    // Begin of school api.
    @GetMapping(path = "/{id}/school")
    @JsonView(value = {DataView.BasicView.class})
    public GenericResponse getSchool(@PathVariable final Integer id) throws DataNotFoundException {
        return GenericResponse.success(this.service.findById(id).getSchools());
    }

    @PostMapping(path = "/{id}/school")
    public GenericResponse newSchool(@PathVariable final Integer id,
                                     @JsonView(value = {DataView.ExtensiveView.class})
                                     @Validated @RequestBody School school)
            throws DataNotFoundException {
        school.setInstitution(this.service.findById(id));
        return GenericResponse.success(this.schoolService.save(school));
    }

    @PutMapping(path = "/{id}/school/{sid}")
    @JsonView(value = {DataView.TypicalView.class})
    public GenericResponse addSchool(@PathVariable final Integer id, @PathVariable final Integer sid)
            throws DataNotFoundException {
        School school = this.schoolService.findById(sid);
        school.setInstitution(this.service.findById(id));
        return GenericResponse.success(this.schoolService.save(school));
    }

    @DeleteMapping(path = "/{id}/school/{sid}")
    @JsonView(value = {DataView.TypicalView.class})
    public GenericResponse removeSchool(@PathVariable final Integer id, @PathVariable final Integer sid)
            throws DataNotFoundException {
        School school = this.schoolService.findOne((root, cq, cb) ->
                cb.and(cb.equal(root.get(School_.id), sid),
                        cb.equal(root.join(School_.institution).get(Institution_.id), id)));
        school.setInstitution(null);
        return GenericResponse.success(this.schoolService.save(school));
    }
}
