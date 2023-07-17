package baitap_14_7.controller;

import baitap_14_7.service.DepartmentService;
import baitap_14_7.service.dto.DepartmentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/department")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/index")
    public ModelAndView index(Pageable pageable) {
        Page<DepartmentDTO> page = departmentService.findAll(pageable);
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
        departmentService.delete(id);
        ModelAndView modelAndView = new ModelAndView("redirect:/department/index");
        return modelAndView;
    }

    @GetMapping("/back")
    public ModelAndView back() {
        ModelAndView modelAndView = new ModelAndView("redirect:/department/index");
        return modelAndView;
    }

}
