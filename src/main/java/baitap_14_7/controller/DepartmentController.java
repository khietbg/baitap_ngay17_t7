package baitap_14_7.controller;

import baitap_14_7.repository.DepartmentRepository;
import baitap_14_7.service.DepartmentService;
import baitap_14_7.service.dto.DepartmentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/apiDepartment")
public class DepartmentController {
    private final DepartmentService departmentService;
    private final DepartmentRepository departmentRepository;

    public DepartmentController(DepartmentService departmentService, DepartmentRepository departmentRepository) {
        this.departmentService = departmentService;
        this.departmentRepository = departmentRepository;
    }

    @GetMapping("/")
    public ModelAndView findAll(Pageable pageable) {
        Page<DepartmentDTO> page = departmentService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("department/departmentList");
        modelAndView.addObject("page",page);
        return modelAndView;
    }
    @GetMapping("/formCreate")
    public ModelAndView formCreate(){
       ModelAndView modelAndView = new ModelAndView("department/create");
       modelAndView.addObject("department",new DepartmentDTO());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView createDepartment(@ModelAttribute("department") DepartmentDTO departmentDTO) {
        departmentService.save(departmentDTO);
        ModelAndView modelAndView = new ModelAndView("redirect:/apiDepartment/");
        return modelAndView;
    }
    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable Long id){
        DepartmentDTO departmentDTO = departmentService.findOne(id).get();
        ModelAndView modelAndView = new ModelAndView("/department/update");
        modelAndView.addObject("department",departmentDTO);
        return modelAndView;
    }
    @PostMapping("/update")
    public ModelAndView update(@ModelAttribute("department") DepartmentDTO departmentDTO) {
        departmentService.save(departmentDTO);
        ModelAndView modelAndView = new ModelAndView("redirect:/apiDepartment/");
        return modelAndView;
    }
    @GetMapping("/detail/{id}")
    public ModelAndView findById(@PathVariable Long id){
        DepartmentDTO departmentDTO = departmentService.findOne(id).get();
        ModelAndView modelAndView = new ModelAndView("/department/detail");
        modelAndView.addObject("department",departmentDTO);
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteDepartment(@PathVariable("id") Long id) {
        departmentService.delete(id);
        ModelAndView modelAndView = new ModelAndView("redirect:/apiDepartment/");
        return modelAndView;
    }
    @GetMapping("/back")
    public ModelAndView back(){
        ModelAndView modelAndView = new ModelAndView("redirect:/apiDepartment/");
        return modelAndView;
    }

}
