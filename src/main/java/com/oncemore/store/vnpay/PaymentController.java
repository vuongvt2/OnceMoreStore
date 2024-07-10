package com.oncemore.store.vnpay;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/vn-pay")
    public RedirectView pay(HttpServletRequest request) {
        RedirectView redirectView = new RedirectView();
        PaymentDTO.VNPayResponse vnPayResponse = paymentService.createVnPayPayment(request);
        String url = vnPayResponse.paymentUrl;
        redirectView.setUrl(url);

        return redirectView;
    }

    @GetMapping("/vn-pay-callback")
    public String payCallbackHandler(HttpServletRequest request) {
        String status = request.getParameter("vnp_ResponseCode");
        if (status.equals("00")) {
            return "redirect:/success";
        } else {
            return "redirect:/home";
        }

    }
}
