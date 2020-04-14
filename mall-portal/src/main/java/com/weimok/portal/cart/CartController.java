package com.weimok.portal.cart;

import com.weimok.auth.entity.UserInfo;
import com.weimok.cart.api.CartService;
import com.weimok.cart.pojo.Cart;
import com.weimok.portal.interceptor.UserInterceptor;
import org.apache.catalina.User;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author itsNine
 * @create 2020-04-12-10:01
 */
@RestController
@RequestMapping("/cart")
public class CartController {
    @Reference(version = "1.0.0")
    private CartService cartService;

    /**
     * 登录状态下添加购物车
     * @param cart
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> addCart(@RequestBody Cart cart){
        UserInfo user = UserInterceptor.getUser();
        System.out.println("info"+user);
        System.out.println("user"+user.getId());
        cartService.addCart(cart,user);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 登录后合并购物车
     * @param cart
     * @return
     */
    @PostMapping("/loginadd")
    public ResponseEntity<Void> loginAdd(@RequestBody Cart cart){
        UserInfo user = UserInterceptor.getUser();
        cartService.addCart(cart,user);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 查询购物车
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<List<Cart>> queryCartList(){
        UserInfo user = UserInterceptor.getUser();
        return ResponseEntity.ok(cartService.queryCartList(user));
    }

    /**
     * 修改购物车数量
     * @param spuId
     * @param num
     * @return
     */
    @PutMapping
    public ResponseEntity<Void> updateNum(@RequestParam("spuId") Long spuId,
                                          @RequestParam("num")Integer num){
        UserInfo user = UserInterceptor.getUser();
        cartService.updateNum(spuId,num,user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 删除购物车中的商品
     * @param spuId
     * @return
     */
    @DeleteMapping("/{spuId}")
    public ResponseEntity<Void> deleteCart(@PathVariable("spuId")Long spuId){
        UserInfo user = UserInterceptor.getUser();
        cartService.deleteCart(spuId,user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
