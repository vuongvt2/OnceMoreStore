package com.oncemore.store.controller;

import com.oncemore.store.dto.ProductDTO;
import com.oncemore.store.service.CategoryService;
import com.oncemore.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @GetMapping(value = {"/home", "/"})
    public String home(Model model, @ModelAttribute("toastMessage") String toastMessage) {
        List<ProductDTO> products = productService.getAllProducts();
        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.findAll());
        return "product";
    }

}
