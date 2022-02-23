package codegym.controllers;


import codegym.model.Product;
import codegym.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Value("${file-upload}")
    private String fileUpload;

    @Value("${view}")
    private String view;

    @Autowired
    private IProductService productService;

    @GetMapping
    public ModelAndView showProducts() {
        ModelAndView modelAndView = new ModelAndView("list");
        ArrayList<Product> products = productService.getAllProducts();
        if (products.isEmpty()) {
            modelAndView.addObject("message", "No products !");
            modelAndView.addObject("color", "red");
        }
        modelAndView.addObject("file", "//localhost:8080/images/");
        modelAndView.addObject("products", products);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createProduct(Product product) {
        ModelAndView modelAndView = new ModelAndView("create");
        modelAndView.addObject("product", new Product());
        return modelAndView;
    }

    @PostMapping
    public ModelAndView saveProduct(@ModelAttribute Product product) {
        ModelAndView modelAndView = new ModelAndView("create");
        MultipartFile multipartFile = product.getImageFile();
        String fileName = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(product.getImageFile().getBytes(), new File(fileUpload + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        product.setImageUrl(fileName);
        Product productCreate = productService.save(product);
        if (productCreate != null) {
            modelAndView.addObject("message", "Create Product Successfully !!!");
        }
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable int id) {
        ModelAndView modelAndView = new ModelAndView("edit");
        Product product = productService.findById(id);
        modelAndView.addObject("file", "//localhost:8080/images/");
        modelAndView.addObject("product", product);
        return modelAndView;
    }

    @PostMapping("/{id}")
    public ModelAndView updateProduct(@PathVariable int id, @ModelAttribute Product product) {
        ModelAndView modelAndView = new ModelAndView("edit");
        product.setId(id);
        if (product.getImageFile().getSize() != 0) {
            MultipartFile multipartFile = product.getImageFile();
            String fileName = multipartFile.getOriginalFilename();
            try {
                FileCopyUtils.copy(product.getImageFile().getBytes(), new File(fileUpload + fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
            product.setImageUrl(fileName);
        } else {
            product.setImageUrl(productService.findById(id).getImageUrl());
        }
        Product productEdit = productService.save(product);
        if (productEdit != null) {
            modelAndView.addObject("message", "Update Product Successfully !!!");
        }
        return modelAndView;
    }

    @GetMapping("/view/{id}")
    public ModelAndView showDetail(@PathVariable int id) {
        ModelAndView modelAndView = new ModelAndView("detail");
        Product product = productService.findById(id);
        modelAndView.addObject("file", "//localhost:8080/images/");
        modelAndView.addObject("product", product);
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteProduct(@PathVariable int id) {
        ModelAndView modelAndView = new ModelAndView("redirect:/product");
        productService.delete(id);
//        ArrayList<Product> products = productService.getAllProducts();
//        modelAndView.addObject("products", products);
        return modelAndView;
    }

    @PostMapping("/search")
    public ModelAndView search(@RequestParam("search") String name) {
        ModelAndView modelAndView = new ModelAndView("list");
        ArrayList<Product> products = productService.findAllByName(name);
        if (products.isEmpty()) {
            modelAndView.addObject("message", "Don't search products !!!");
        }
        modelAndView.addObject("file", "//localhost:8080/images/");
        modelAndView.addObject("products", products);
        modelAndView.addObject("search", name);
        return modelAndView;
    }
}
