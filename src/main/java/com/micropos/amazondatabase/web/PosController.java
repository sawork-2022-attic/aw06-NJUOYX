package com.micropos.amazondatabase.web;

import com.micropos.amazondatabase.model.Cart;
import com.micropos.amazondatabase.service.PosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class PosController {
    private HttpSession session;

    private PosService posService;

    private String currentCategory;

    private Cart getCart(){
        Cart cart = (Cart) session.getAttribute("cart");
        if(cart == null){
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    private void setCart(Cart cart){
        session.setAttribute("cart", cart);
    }

    private String flushModel(Model model){
        model.addAttribute("categories", posService.getCategories());
        model.addAttribute("products", posService.products(currentCategory));
        model.addAttribute("cart", getCart());
        return "index";
    }

    @Autowired
    public void setSession(HttpSession session){
        this.session = session;
    }

    @Autowired
    public void setPosService(PosService posService) {
        this.posService = posService;
    }

    @GetMapping("/")
    public String pos(Model model) {
        currentCategory = null;
        return flushModel(model);
    }

    @GetMapping("/product")
    public String pos(@RequestParam(name = "category") String category, Model model){
        currentCategory = category;
        return flushModel(model);
    }

    @GetMapping("/add")
    public String addByGet(@RequestParam(name = "pid") String pid, Model model) {
        Cart cart = posService.add(getCart(), pid, 1);
        setCart(cart);
        return flushModel(model);
    }

    @GetMapping("/cancel")
    public String cancel(Model model){
        setCart(new Cart());
        return flushModel(model);
    }

    @GetMapping("/increase")
    public String increase(@RequestParam(name = "pid") String pid, Model model){
        setCart(posService.increase(getCart(),pid, 1));
        return flushModel(model);
    }

    @GetMapping("/decrease")
    public String decrease(@RequestParam(name = "pid") String pid, Model model){
        setCart(posService.increase(getCart(), pid, -1));
        return flushModel(model);
    }

    @GetMapping("/remove")
    public String remove(@RequestParam(name = "pid") String pid , Model model){
        setCart(posService.remove(getCart(), pid));
        return flushModel(model);
    }

}
