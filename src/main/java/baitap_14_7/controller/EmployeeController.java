package baitap_14_7.controller;

import baitap_14_7.domain.Role;
import baitap_14_7.repository.EmployeeRepository;
import baitap_14_7.repository.RoleRepository;
import baitap_14_7.service.DepartmentService;
import baitap_14_7.service.EmployeeService;
import baitap_14_7.service.dto.DepartmentDTO;
import baitap_14_7.service.dto.EmployeeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/apiEmployee")
public class EmployeeController {
    private final EmployeeRepository employeeRepository;
    private final EmployeeService employeeService;
    private final DepartmentService departmentService;
    private final RoleRepository roleRepository;

    public EmployeeController(EmployeeRepository employeeRepository, EmployeeService employeeService, DepartmentService departmentService, RoleRepository roleRepository) {
        this.employeeRepository = employeeRepository;
        this.employeeService = employeeService;
        this.departmentService = departmentService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/")
    public ModelAndView findAll(@RequestParam(name = "textSearch", required = false, defaultValue = "") String textSearch, Pageable pageable) {
        Page<EmployeeDTO> page = employeeService.findAllByNameContainingIgnoreCase(textSearch, pageable);
        ModelAndView modelAndView = new ModelAndView("employee/employeeList");
        modelAndView.addObject("page", page);
        return modelAndView;
    }

    @GetMapping("/formCreate")
    public ModelAndView formCreate() {
        ModelAndView modelAndView = new ModelAndView("employee/create");
        modelAndView.addObject("employee", new EmployeeDTO());
        List<DepartmentDTO> dtoList = departmentService.getAll();
        modelAndView.addObject("departments", dtoList);
        List<Role> roles = roleRepository.findAll();
        modelAndView.addObject("roles", roles);
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView createDepartment(@ModelAttribute("department") EmployeeDTO employeeDTO) {
        if (employeeService.existsByEmail(employeeDTO.getEmail())) {
            ModelAndView modelAndView = new ModelAndView("employee/create");
            modelAndView.addObject("employee", new EmployeeDTO());
            List<DepartmentDTO> dtoList = departmentService.getAll();
            modelAndView.addObject("departments", dtoList);
            List<Role> roles = roleRepository.findAll();
            modelAndView.addObject("roles", roles);
            modelAndView.addObject("message1", "email existed, please try again!");
            return modelAndView;
        }
        employeeService.save(employeeDTO);
        ModelAndView modelAndView = new ModelAndView("redirect:/apiEmployee/");
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable Long id) {
        EmployeeDTO employeeDTO = employeeService.findOne(id).get();
        ModelAndView modelAndView = new ModelAndView("/employee/update");
        modelAndView.addObject("employee", employeeDTO);
        List<DepartmentDTO> dtoList = departmentService.getAll();
        modelAndView.addObject("departments", dtoList);
        List<Role> roles = roleRepository.findAll();
        modelAndView.addObject("roles", roles);
        return modelAndView;
    }

    @PostMapping("/update")
    public ModelAndView update(@ModelAttribute("employee") EmployeeDTO employeeDTO) {
        Optional<EmployeeDTO> employeeByEmail = employeeService.findByEmail(employeeDTO.getEmail());
        if (employeeByEmail.isPresent() && employeeByEmail.get().getId() != employeeDTO.getId()) {
            ModelAndView modelAndView = new ModelAndView("/employee/update");
            modelAndView.addObject("employee", employeeDTO);
            List<DepartmentDTO> dtoList = departmentService.getAll();
            modelAndView.addObject("departments", dtoList);
            List<Role> roles = roleRepository.findAll();
            modelAndView.addObject("roles", roles);
            modelAndView.addObject("message1", "email existed, please try again!");
        }
        employeeService.save(employeeDTO);
        ModelAndView modelAndView = new ModelAndView("redirect:/apiEmployee/");
        return modelAndView;
    }

    @GetMapping("/detail/{id}")
    public ModelAndView findById(@PathVariable Long id) {
        EmployeeDTO employeeDTO = employeeService.findOne(id).get();
        ModelAndView modelAndView = new ModelAndView("/employee/detail");
        modelAndView.addObject("employee", employeeDTO);
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteDepartment(@PathVariable("id") Long id) {
        employeeService.delete(id);
        ModelAndView modelAndView = new ModelAndView("redirect:/apiEmployee/");
        return modelAndView;
    }

    @GetMapping("/back")
    public ModelAndView back() {
        ModelAndView modelAndView = new ModelAndView("redirect:/apiEmployee/");
        return modelAndView;
    }
}
