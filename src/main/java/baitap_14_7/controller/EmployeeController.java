package baitap_14_7.controller;

import baitap_14_7.repository.RoleRepository;
import baitap_14_7.service.DepartmentService;
import baitap_14_7.service.EmployeeService;
import baitap_14_7.service.dto.EmployeeDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final DepartmentService departmentService;
    private final RoleRepository roleRepository;

    public EmployeeController(EmployeeService employeeService, DepartmentService departmentService, RoleRepository roleRepository) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/index")
    public ModelAndView findAll(@RequestParam(name = "textSearch", required = false, defaultValue = "") String textSearch, Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("employee/index");
        modelAndView.addObject("page", employeeService.findAllByNameContainingIgnoreCase(textSearch, pageable));
        return modelAndView;
    }

    @GetMapping("/add")
    public ModelAndView showAdd() {
        ModelAndView modelAndView = new ModelAndView("employee/add");
        modelAndView.addObject("employee", new EmployeeDTO());
        modelAndView.addObject("departments", departmentService.getAll());
        modelAndView.addObject("roles", roleRepository.findAll());
        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView doAdd(@Valid @ModelAttribute("employee") EmployeeDTO employeeDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("employee/add", bindingResult.getModel());
            modelAndView.addObject("employee", new EmployeeDTO());
            modelAndView.addObject("departments", departmentService.getAll());
            modelAndView.addObject("roles", roleRepository.findAll());
            return modelAndView;
        }
        if (employeeService.existsByEmail(employeeDTO.getEmail())) {
            ModelAndView modelAndView = new ModelAndView("employee/add");
            modelAndView.addObject("employee", new EmployeeDTO());
            modelAndView.addObject("departments", departmentService.getAll());
            modelAndView.addObject("roles", roleRepository.findAll());
            modelAndView.addObject("message1", "email existed, please try again!");
            return modelAndView;
        }
        employeeService.save(employeeDTO);
        ModelAndView modelAndView = new ModelAndView("redirect:/employee/index");
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEdit(@PathVariable Long id) {
        EmployeeDTO employeeDTO = employeeService.findOne(id).get();
        ModelAndView modelAndView = new ModelAndView("employee/edit");
        modelAndView.addObject("employee", employeeDTO);
        modelAndView.addObject("departments", departmentService.getAll());
        modelAndView.addObject("roles", roleRepository.findAll());
        return modelAndView;
    }

    @PostMapping("/edit")
    public ModelAndView doEdit(@Valid @ModelAttribute("employee") EmployeeDTO employeeDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("employee/edit");
            modelAndView.addObject("employee", new EmployeeDTO());
            modelAndView.addObject("departments", departmentService.getAll());
            modelAndView.addObject("roles", roleRepository.findAll());
            return modelAndView;
        }
        Optional<EmployeeDTO> employeeByEmail = employeeService.findByEmail(employeeDTO.getEmail());
        if (employeeByEmail.isPresent() && employeeByEmail.get().getId() != employeeDTO.getId()) {
            ModelAndView modelAndView = new ModelAndView("employee/edit");
            modelAndView.addObject("employee", employeeDTO);
            modelAndView.addObject("departments", departmentService.getAll());
            modelAndView.addObject("roles", roleRepository.findAll());
            modelAndView.addObject("message1", "email existed, please try again!");
        }
        employeeService.save(employeeDTO);
        ModelAndView modelAndView = new ModelAndView("redirect:/apiEmployee/index");
        return modelAndView;
    }

    @GetMapping("/detail/{id}")
    public ModelAndView findById(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("/employee/detail");
        modelAndView.addObject("employee", employeeService.findOne(id).get());
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView doDelete(@PathVariable("id") Long id) {
        employeeService.delete(id);
        ModelAndView modelAndView = new ModelAndView("redirect:/employee/index");
        return modelAndView;
    }

    @GetMapping("/back")
    public ModelAndView back() {
        ModelAndView modelAndView = new ModelAndView("redirect:/employee/index");
        return modelAndView;
    }
}
