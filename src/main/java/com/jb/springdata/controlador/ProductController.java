package com.jb.springdata.controlador;

import com.jb.springdata.modelo.Product;
import com.jb.springdata.servicio.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@Slf4j
@RequestMapping("/")
public class ProductController {

   @Autowired
    private ProductService productService;

    @GetMapping
    public String  findAll(@RequestParam Map<String, Object> params, Model model, @ModelAttribute Product product){
        int page = params.get("page") != null ? (Integer.valueOf(params.get("page").toString())-1) : 0;

        PageRequest pageRequest = PageRequest.of(page, 9);
        Page<Product> productPage = productService.findAll(pageRequest);

        int totalPage = productPage.getTotalPages();
        if(totalPage >0 ) {
            List<Integer> pages = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
            model.addAttribute("pages", pages);
        }

        model.addAttribute("list", productPage.getContent());
        model.addAttribute("current", page + 1);
        model.addAttribute("next", page + 2);
        model.addAttribute("previous", page );
        model.addAttribute("last", totalPage );

        return "index";
    }


    @GetMapping(value = "/create")
    public String add(Model   model){
        model.addAttribute("product", new Product());
        return "modify";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute Product product, Errors error,  @RequestParam("file")MultipartFile image){


        if(error.hasErrors()){
            return "modify";
        }
        if(!image.isEmpty()){
            //Path directorioImagenes = Paths.get("src//main//resources//static/image");
            //String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
            String rutaAbsoluta= "//home//jorge//Escritorio//proyectofinal//recursos";

            try {
                byte[] bytesImg =image.getBytes();
                Path rutaCompleta = Paths.get(rutaAbsoluta + "//"+ image.getOriginalFilename());
                Files.write(rutaCompleta, bytesImg);

                product.setImage(image.getOriginalFilename());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        productService.save(product);
        return "redirect:/";
    }

    @GetMapping ("/detalle/{idProduct}")
    public String detalleProduct(@PathVariable Long idProduct, Model model ){
        model.addAttribute("product",productService.findproduct(idProduct));
        return "detalleProduct";
    }

    @GetMapping("/edit/{idProduct}")
    public String edit(@PathVariable Long idProduct,Model model){
        model.addAttribute("product", productService.findproduct(idProduct));
        return "modify";
    }



    @GetMapping("/delete")
    public String delete(Product product){
        productService.delete(product);
        return "redirect:/";
    }

    @PostMapping("actualizar/{idProduct}")
    public  String actualizar(@PathVariable Long idProduct, @ModelAttribute("product") Product product){
        Product products = productService.findproduct(idProduct);
        products.setIdProduct(idProduct);
        products.setQuantity(product.getQuantity());

        productService.actualizar(products);
        return "redirect:/";
    }

    @PostMapping("/search")
    public String searchProduct(@RequestParam String nombre, Model model) {
        log.info("Nombre del producto: {}", nombre);
        List<Product> products =productService.listProducts().stream().filter(p -> p.getName().contains(nombre)).collect(Collectors.toList());
        model.addAttribute("list", products);

        return "redirect:/";
    }



}

