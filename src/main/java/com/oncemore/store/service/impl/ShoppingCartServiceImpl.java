package com.oncemore.store.service.impl;

import com.oncemore.store.dto.*;
import com.oncemore.store.entity.*;
import com.oncemore.store.model.UserCartInfo;
import com.oncemore.store.repository.*;
import com.oncemore.store.service.ShoppingCartService;
import com.oncemore.store.util.VNPayUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ProductRepository productRepository;

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    private final UserCartInfo userCartInfo;

    private final OrderRepository orderRepository;

    private final OrderDetailRepository orderDetailRepository;

    private final ProductImageRepository productImageRepository;

    @Override
    @Transactional
    public String addProductToCart(UUID productId, int quantity) {
        try {
            Product product = productRepository.getById(productId);
            int maxQuantity = product.getQuantity();
            if (maxQuantity < quantity) {
                return "Số lượng sản phẩm không đủ";
            }
            CartItem cartItem = cartItemRepository.findByCartIdAndProductId(userCartInfo.getCartId(), product.getId());
            if (Objects.isNull(cartItem)) {
                cartItem = new CartItem();
                cartItem.setQuantity(quantity);
                cartItem.setCartId(userCartInfo.getCartId());
                cartItem.setProductId(productId);
            } else {
                int oldQuantity = cartItem.getQuantity();
                int sum = Math.addExact(oldQuantity, quantity);
                if (maxQuantity < sum) {
                    return "Số lượng sản phẩm không đủ";
                }
                cartItem.setQuantity(sum);
            }
            cartItemRepository.saveAndFlush(cartItem);
            return "Đã thêm vào giỏ hàng!";
        } catch (Exception ex) {
            return "Có lỗi xảy ra!";
        }
    }

    @Override
    public CartDTO viewCart() {
        List<CartItemDTO> cartItemDTOList = cartItemRepository.findAllByCartId(userCartInfo.getCartId());
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartItemDTOList(cartItemDTOList);
        cartDTO.setCartId(userCartInfo.getCartId());
        return cartDTO;
    }

    @Transactional
    @Override
    public void removeProductToCart(UUID productId) {
        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(userCartInfo.getCartId(), productId);
        if (Objects.nonNull(cartItem)) {
            cartItemRepository.delete(cartItem);
        }
    }

    @Override
    @Transactional
    public ResponseOrder processOrder(OrderDTO orderDTO, boolean orderNow) {
        List<OrderDetailDTO> orderDetailList = orderDTO.getOrderDetailDTOList();
        ResponseOrder responseOrder = new ResponseOrder();
        if (!CollectionUtils.isEmpty(orderDetailList)) {
            Order order = new Order();
            order.setAddress(orderDTO.getAddress());
            order.setPhone(orderDTO.getPhone());
            order.setReceiverName(orderDTO.getReceiverName());
            if ("false".equals(orderDTO.getIsPayment())) {
                order.setPayment(false);
            } else {
                order.setPayment(true);
            }
            String orderCode = VNPayUtil.getRandomNumber(8);
            order.setOrderCode(orderCode);
            order.setAmount(orderDTO.getAmount());
            responseOrder.setOrderCode(orderCode);
            responseOrder.setAmount(orderDTO.getAmount());
            if (order.isPayment()) {
                responseOrder.setPayment(true);
                order.setPaymentMethod("VNPAY");
//                order.setStatus(EOrderStatus.PENDING);
                order.setStatus(EOrderStatus.SUCCESS);
            } else {
                responseOrder.setPayment(false);
                order.setPaymentMethod("CASH");
                order.setStatus(EOrderStatus.SUCCESS);
            }
            order.setNotes(order.getNotes());
            order.setUserId(userCartInfo.getUserId());
            order = orderRepository.save(order);
            List<OrderDetail> orderDetails = new ArrayList<>();
            List<Product> products = new ArrayList<>();
            BigDecimal amount = BigDecimal.ZERO;
            for (OrderDetailDTO orderDetailDTO : orderDetailList) {
                Product product = productRepository.getById(orderDetailDTO.getProductId());

                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setProductId(orderDetailDTO.getProductId());
                orderDetail.setOrderId(order.getId());
                int quantity = orderDetailDTO.getQuantity();
                int maxQuantity = product.getQuantity();
                if (quantity > maxQuantity) {
                    break;
                }
                BigDecimal priceProduct = product.getPrice();
                orderDetail.setQuantity(quantity);
                product.setQuantity(maxQuantity - quantity);
                products.add(product);

                amount.add(priceProduct.multiply(BigDecimal.valueOf(quantity)));
                orderDetail.setPrice(priceProduct);
                orderDetails.add(orderDetail);
            }
            productRepository.saveAll(products);
            orderDetailRepository.saveAll(orderDetails);
//            if (!orderNow && !order.isPayment()) {
            if (!orderNow) {
                cartItemRepository.deleteAllByCartId(userCartInfo.getCartId());
            }
        }

        return responseOrder;
    }

    @Override
    public CartDTO buyNowProduct(UUID productId) throws Exception {
        Product product = productRepository.getById(productId);
        if (product.getQuantity() < 1) {
            throw new Exception("Sản phẩm đã hết hàng");
        }

        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setProductId(productId);
        cartItemDTO.setQuantity(1);
        cartItemDTO.setDescription(product.getDescription());
        cartItemDTO.setNameProduct(product.getName());
        cartItemDTO.setPrice(product.getPrice());
        cartItemDTO.setMaxQuantity(product.getQuantity());
        String url = productImageRepository.findUrlByProductId(productId);
        cartItemDTO.setUrl(url);
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartId(userCartInfo.getCartId());
        cartDTO.setCartItemDTOList(List.of(cartItemDTO));
        return cartDTO;
    }

    @GetMapping("/success")
    private String success() {
        return "user/success";
    }


}
