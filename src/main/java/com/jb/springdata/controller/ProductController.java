package com.jb.springdata.controller;

import com.jb.springdata.entity.Product;
import com.jb.springdata.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping("/")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;



    @GetMapping
    public String findAll(
            @RequestParam Map<String, Object> params,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            Model model
    ) {

        String searchTerm = (String) params.get("search");

        Page<Product> productPage;
        if (searchTerm != null) {
            productPage = productRepository.findProductByNameContains(searchTerm, PageRequest.of(page, size));
        } else {
            productPage = productRepository.findAll(PageRequest.of(page, size));
        }



        model.addAttribute("pages", new int[productPage.getTotalPages()]);
        model.addAttribute("list", productPage.getContent());
        model.addAttribute("current", page );
        model.addAttribute("next", page + 1);
        model.addAttribute("previous", page -1);
        model.addAttribute("last", (productPage.getTotalPages() -1) );

        model.addAttribute("newProducts", new Product());

        return "product";
    }


    @GetMapping(value = "/create")
    public String add(Model model) {
        model.addAttribute("product", new Product());
        return "modify";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute Product product, Errors error,
                       @RequestParam("file") MultipartFile image,
                       RedirectAttributes redirectAttributes) {


        if (error.hasErrors()) {
            return "modify";
        }
        if (!image.isEmpty()) {
            //Path directorioImagenes = Paths.get("src//main//resources//static/image");
           // String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
            String rutaAbsoluta =  "//Users//jborras15//Documents/recurso";

            try {
                byte[] bytesImg = image.getBytes();
                Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + image.getOriginalFilename());
                Files.write(rutaCompleta, bytesImg);

                product.setImage(image.getOriginalFilename());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        redirectAttributes
                .addFlashAttribute("mensaje", "El producto se guardo exitosamente ")
                .addFlashAttribute("clase", "success");
        productRepository.save(product);
        return "redirect:/";
    }

    @GetMapping("detalle/{idProduct}")
    public String detalleProduct(@PathVariable Long idProduct, Model model) {
        model.addAttribute("product", productRepository.findById(idProduct));
        return "detalleProduct";
    }

    @GetMapping("/edit/{idProduct}")
    public String edit(@PathVariable Long idProduct, Model model) {
        model.addAttribute("product", productRepository.findById(idProduct));
        return "modify";
    }


    @GetMapping("/delete")
    public String delete(Product product) {
        productRepository.delete(product);
        return "redirect:/";
    }

    @PostMapping("actualizar/{idProduct}")
    public String actualizar(
            @PathVariable Long idProduct,
            @ModelAttribute("product") Product newProduct
    ) {
        Optional<Product> result = productRepository.findById(idProduct);

        if (result.isPresent()) {
            Product product = result.get();
            product.setQuantity(newProduct.getQuantity());
            productRepository.save(product);
        }

        return "redirect:/";
    }

}

