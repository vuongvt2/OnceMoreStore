package com.oncemore.store.controller;

import com.oncemore.store.dto.ProductDTO;
import com.oncemore.store.entity.Category;
import com.oncemore.store.entity.Product;
import com.oncemore.store.model.ProductModel;
import com.oncemore.store.service.CategoryService;
import com.oncemore.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Controller
public class ProductController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping("/admin/create-product")
    public String createProduct(Model model, @ModelAttribute("toastMessage") String toastMessage) {
        Product product = new Product();
        List<Category> categories = categoryService.findAll();
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        return "create-product";
    }

    @PostMapping("/admin/create-product")
    public String createProduct(ProductModel product, MultipartHttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            List<MultipartFile> images = request.getFiles("images");

            productService.save(product, images);
            redirectAttributes.addFlashAttribute("toastMessage", "Sản phẩm đã được tạo thành công.");

        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("toastMessage", "Đã xảy ra lỗi khi tạo sản phẩm.");
        }
        return "redirect:/admin/create-product";
    }

    @GetMapping("/admin/products")
    public String showProduct(Model model, @ModelAttribute("toastMessage") String toastMessage) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "/admin/products";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String deleteProduct(@PathVariable("id") UUID id, RedirectAttributes redirectAttributes) {
        String message = productService.delete(id);
        redirectAttributes.addFlashAttribute("toastMessage", message);

        return "redirect:/admin/products";
    }

    @GetMapping("/admin/product/update/{id}")
    public String updateProduct(@PathVariable("id") UUID id, Model model, @ModelAttribute("toastMessage") String toastMessage) {
        Product product = productService.findById(id);
        if (Objects.isNull(product)) {
            return "/error/404";
        }
        List<Category> categories = categoryService.findAll();
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        return "admin/update-product";
    }

    @PostMapping("/admin/product/update")
    public String updateProduct(@ModelAttribute("product") ProductModel product, MultipartHttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            List<MultipartFile> images = request.getFiles("images");

            productService.update(product, images);
            redirectAttributes.addFlashAttribute("toastMessage", "Cập nhật thành công.");

        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("toastMessage", "Đã xảy ra lỗi khi tạo sản phẩm.");
        }
        return "redirect:/admin/product/update/" + product.getId();
    }

    @GetMapping("/user/product/detail/{id}")
    public String viewProductDetail(@PathVariable UUID id, @ModelAttribute("toastMessage") String toastMessage, Model model) {
        ProductDTO product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "user/product-detail";
    }

    @GetMapping("/filter")
    public String filterProductsByCategoryAndPrice(@RequestParam(value = "categoryId", required = false) UUID categoryId,
                                                   @RequestParam(value = "minPrice", required = false) BigDecimal minPrice,
                                                   @RequestParam(value = "maxPrice", required = false) BigDecimal maxPrice,
                                                   Model model) {
        List<ProductDTO> products = productService.filterProductsByCategoryAndPrice(categoryId, minPrice, maxPrice);
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        return "product";
    }
}
