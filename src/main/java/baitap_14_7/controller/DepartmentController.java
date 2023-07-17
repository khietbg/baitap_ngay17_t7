package baitap_14_7.controller;

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

@Controller
@RequestMapping("/department")
public class DepartmentController {
    private final DepartmentService departmentService;
    private final EmployeeService employeeService;

    public DepartmentController(DepartmentService departmentService, EmployeeService employeeService) {
        this.departmentService = departmentService;
        this.employeeService = employeeService;
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(name = "textSearch", required = false, defaultValue = "") String textSearch, Pageable pageable) {
        Page<DepartmentDTO> page = departmentService.findAllByNameContainingIgnoreCase(textSearch, pageable);
        ModelAndView modelAndView = new ModelAndView("department/index");
        modelAndView.addObject("page", page);
        return modelAndView;
    }

    @GetMapping("/add")
    public ModelAndView showAdd() {
        ModelAndView modelAndView = new ModelAndView("department/add");
        modelAndView.addObject("department", new DepartmentDTO());
        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView doAdd(@ModelAttribute("department") DepartmentDTO departmentDTO) {
        departmentService.save(departmentDTO);
        ModelAndView modelAndView = new ModelAndView("redirect:/department/index");
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEdit(@PathVariable Long id) {
        DepartmentDTO departmentDTO = departmentService.findOne(id).get();
        ModelAndView modelAndView = new ModelAndView("department/edit");
        modelAndView.addObject("department", departmentDTO);
        return modelAndView;
    }

    @PostMapping("/edit")
    public ModelAndView doEdit(@ModelAttribute("department") DepartmentDTO departmentDTO) {
        departmentService.save(departmentDTO);
        ModelAndView modelAndView = new ModelAndView("redirect:/department/index");
        return modelAndView;
    }

    @GetMapping("/detail/{id}")
    public ModelAndView showDetail(@PathVariable Long id) {
        DepartmentDTO departmentDTO = departmentService.findOne(id).get();
        ModelAndView modelAndView = new ModelAndView("/department/detail");
        modelAndView.addObject("department", departmentDTO);
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView doDelete(@PathVariable("id") Long id) {
        List<EmployeeDTO> dtoList = employeeService.findAll();
        for (EmployeeDTO e : dtoList) {
            if (e.getDepartmentId().equals(id)) {
                ModelAndView modelAndView = new ModelAndView("department/index");
                modelAndView.addObject("message1", "can not delete this department!");
                return modelAndView;
            }
        }
        departmentService.delete(id);
        ModelAndView modelAndView = new ModelAndView("redirect:/department/index");
        return modelAndView;
    }

    @GetMapping("/back")
    public ModelAndView back() {
        ModelAndView modelAndView = new ModelAndView("redirect:/department/index");
        return modelAndView;
    }

    @GetMapping("/layout")
    public ModelAndView layout() {
        return new ModelAndView("layout/layout");
    }

}
