package org.launchcode.javawebdevtechjobspersistent.controllers;

import org.launchcode.javawebdevtechjobspersistent.models.Employer;
import org.launchcode.javawebdevtechjobspersistent.models.Job;
import org.launchcode.javawebdevtechjobspersistent.models.Skill;
import org.launchcode.javawebdevtechjobspersistent.models.data.EmployerRepository;
import org.launchcode.javawebdevtechjobspersistent.models.data.JobRepository;
import org.launchcode.javawebdevtechjobspersistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Created by LaunchCode
 */
@Controller
public class HomeController {
    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private SkillRepository skillsRepository;

    @Autowired
    private JobRepository jobRepository;

    @RequestMapping("")
    public String index(Model model) {

        model.addAttribute("title", "My Jobs");
        model.addAttribute("jobs", jobRepository.findAll());

        return "index";
    }

    @GetMapping("add")
    public String displayAddJobForm(Model model) {
        model.addAttribute("employers", employerRepository.findAll());
        model.addAttribute("skills", skillsRepository.findAll());
        model.addAttribute("title", "Add Job");
        model.addAttribute(new Job());
        return "add";
    }

    @PostMapping("add")
    public String processAddJobForm(@ModelAttribute @Valid Job newJob,
                                    Errors errors, Model model, @RequestParam int employerId, @RequestParam List<Integer> skills) {
        //New jobs are not being saved to the db yet because i haven't added the many-to-many relationship
        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Job");
            return "redirect:";
        }
        Optional<Employer> optEmployer = employerRepository.findById(employerId);
        if(optEmployer.isEmpty()) { //this check may not be necessary since you can only select from the list of available ones.
            return "redirect:";
        }
        Employer selectedEmployer = optEmployer.get();
        List<Skill> skillObjs = (List<Skill>) skillsRepository.findAllById(skills);
        newJob.setSkills(skillObjs);
        newJob.setEmployer(selectedEmployer);
        jobRepository.save(newJob);
        model.addAttribute("jobs", jobRepository.findAll());
        return "redirect:";
    }

    @GetMapping("view/{jobId}")
    public String displayViewJob(Model model, @PathVariable int jobId) {
        Optional<Job> optionalJob = jobRepository.findById(jobId);
        Job job = optionalJob.get();
        model.addAttribute("job", job);
        return "view";
    }
}