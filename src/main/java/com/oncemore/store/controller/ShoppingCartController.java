package com.oncemore.store.controller;

import com.oncemore.store.dto.CartDTO;
import com.oncemore.store.dto.OrderDTO;
import com.oncemore.store.dto.ResponseOrder;
import com.oncemore.store.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.UUID;

@Controller
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;


    @GetMapping(value = "/shoppingCart/addProduct/{productId}", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String addProductToCart(@PathVariable("productId") UUID productId, @RequestParam(required = false, defaultValue = "1") int quantity) {
        String message = "";
        try {
            message = shoppingCartService.addProductToCart(productId, quantity);
        } catch (Exception e) {
            return message;
        }
        return message;

    }

    @GetMapping("/shoppingCart/view-cart")
    public String viewCart(Model model) {
        CartDTO cartDTO = shoppingCartService.viewCart();
        model.addAttribute("cartDTO", cartDTO);
        return "/user/cart";
    }

    @GetMapping("/shoppingCart/removeProduct/{productId}")
    @ResponseBody
    public String removeProductToCart(@PathVariable("productId") UUID productId) {
        try {
            shoppingCartService.removeProductToCart(productId);
            return "00";
        } catch (Exception e) {
            return "01";
        }
    }

    @GetMapping("/shoppingCart/view-order")
    public String order(Model model) {
        CartDTO cartDTO = shoppingCartService.viewCart();
        model.addAttribute("cartDTO", cartDTO);
        return "/user/order";
    }

    @PostMapping("/process-order")
    @ResponseBody
    public ResponseOrder processOrder(@RequestBody @Valid OrderDTO orderDTO) {
        ResponseOrder responseOrder = new ResponseOrder();
        try {
            responseOrder = shoppingCartService.processOrder(orderDTO, false);
            responseOrder.setResultCode("00");
            return responseOrder;
        } catch (Exception ex) {
            responseOrder.setResultCode("01");
            return responseOrder;
        }
    }

    @GetMapping("/success")
    public String success() {
        return "/user/success";
    }

    @GetMapping("/shoppingCart/buyNow/{productId}")
    public String buyNowProduct(@PathVariable("productId") UUID productId, Model model, RedirectAttributes redirectAttributes) {
        CartDTO cartDTO;
        try {
            cartDTO = shoppingCartService.buyNowProduct(productId);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("toastMessage", e.getMessage());
            return "redirect:/home";
        }
        model.addAttribute("cartDTO", cartDTO);
        return "/user/order-now";
    }


    @PostMapping("/process-order-now")
    @ResponseBody
    public ResponseOrder processOrderNow(@RequestBody @Valid OrderDTO orderDTO) {
        ResponseOrder responseOrder = new ResponseOrder();
        try {
            responseOrder = shoppingCartService.processOrder(orderDTO, true);
            responseOrder.setResultCode("00");
            return responseOrder;
        } catch (Exception ex) {
            responseOrder.setResultCode("01");
            return responseOrder;
        }
    }


}
