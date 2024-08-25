package com.example.controller;

import com.example.primary.model.Item;
import com.example.service.FileService;
import com.example.primary.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/images/in")
public class ImageController {

    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);
    private static final String UPLOAD_DIR = "uploads/images";

    @Autowired
    private ImageService imageService;

    @Autowired
    private FileService fileService;

    @GetMapping
    public String viewHomePage(Model model) {
        model.addAttribute("images", imageService.getAllItems());
        return "index";
    }

    @PostMapping("/add")
    public String addFile(@RequestParam("file") MultipartFile file,
                          @RequestParam("description") String description,
                          @RequestParam("name") String name) throws IOException {
        if (file.isEmpty()) {
            logger.error("File is empty");
            return "redirect:/images/in?error=emptyfile";
        }

        String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR, filename);

        // Создание директории для изображения, если она не создана
        if (!Files.exists(Paths.get(UPLOAD_DIR))) {
            Files.createDirectories(Paths.get(UPLOAD_DIR));
        }

        // Запись файла в файловую систему
        Files.write(filePath, file.getBytes());
        logger.info("Файл {} сохранен в {}", filename, UPLOAD_DIR);

        // Создание ссылки на изображение
        String link = "/images/" + filename;

        // Создание нового элемента Item и сохранение в базу данных
        Item item = new Item();
        item.setName(name);
        item.setDescription(description);
        item.setLink(link);
        imageService.saveItem(item);
        logger.info("Item {} сохранен в базу данных", item.getName());

        return "redirect:/images/in";
    }

//    @GetMapping("/images/{filename:.+}")
//    @ResponseBody
//    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
//        try {
//            Path filePath = Paths.get(UPLOAD_DIR).resolve(filename);
//            Resource resource = new UrlResource(filePath.toUri());
//
//            if (resource.exists() || resource.isReadable()) {
//                return ResponseEntity.ok().body(resource);
//            } else {
//                logger.error("Невозможно прочитать файл : {}", filename);
//                return ResponseEntity.notFound().build();
//            }
//        } catch (MalformedURLException e) {
//            logger.error("Ошибка чтения файла : {}", filename, e);
//            return ResponseEntity.notFound().build();
//        }
//    }

    @GetMapping("/edit/{id}")
    public String editItem(@PathVariable("id") Long id, Model model) {
        Item item = imageService.getAllItems().stream()
                .filter(i -> i.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Недействительный item ID : " + id));

        model.addAttribute("item", item);
        return "edit-item";
    }

    @PostMapping("/edit/{id}")
    public String updateItem(@PathVariable Long id, Item item) {
        imageService.saveItem(item);
        return "redirect:/images/in";
    }

    @PostMapping("/delete/{id}")
    public String deleteItem(@PathVariable("id") Long id) {
        // Получаем элемент из базы данных
        Item item = imageService.getAllItems().stream()
                .filter(i -> i.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Недействительный item ID : " + id));

        // Извлечение имени файла из ссылки на изображение
        String filename = item.getLink().substring(item.getLink().lastIndexOf("/") + 1);

        // Удаление файла с использованием FileService
        boolean fileDeleted = fileService.deleteFile(filename);
        if (!fileDeleted) {
            return "redirect:/images/in?error=deletefile";
        }

        // Удаление записи из базы данных
        imageService.deleteItem(id);
        return "redirect:/images/in";
    }
}


//
//package com.example.controller;
//
//import com.example.primary.model.Item;
//import com.example.service.FileService;
//import com.example.primary.service.ImageService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.UUID;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//
//@Controller
//@RequestMapping("/images/in")
//public class ImageController {
//
//    @Autowired
//    private ImageService imageService;
//
//    @Autowired
//    private FileService fileService;
//
//    // Путь к директории изображений
////    private static final String UPLOAD_DIR = "src/main/resources/static/images";
//    private static final String UPLOAD_DIR = "uploads/images";
//
//    @GetMapping
//    public String viewHomePage(Model model) {
//        model.addAttribute("images", imageService.getAllItems());
//        return "index";
//    }
//
//    @PostMapping("/add")
//    public String addFile(@RequestParam("file") MultipartFile file,
//                          @RequestParam("description") String description,
//                          @RequestParam("name") String name) throws IOException {
//        Logger logger = LoggerFactory.getLogger(ImageController.class);
//        if (file.isEmpty()) {
//            logger.error("File is empty");
//            return "redirect:/images/in?error=emptyfile";
//        }
//
//        String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
//        Path filePath = Paths.get(UPLOAD_DIR, filename);
//
//        // Создание директории для изображения, если она не создана
//        if (!Files.exists(Paths.get(UPLOAD_DIR))) {
//            Files.createDirectories(Paths.get(UPLOAD_DIR));
//        }
//
//        // Запись файла в файловую систему
//        Files.write(filePath, file.getBytes());
//        logger.info("File {} saved to {}", filename, UPLOAD_DIR);
//        // Создание ссылки на изображение
//        String link = "/images/" + filename;
//
//        // Создание нового элемента Item и сохранение в базу данных
//        Item item = new Item();
//        item.setName(name);
//        item.setDescription(description);
//        item.setLink(link);
//        imageService.saveItem(item);
//        logger.info("Item {} saved to database", item.getName());
//        return "redirect:/images/in";
//    }
//
//    @GetMapping("/edit/{id}")
//    public String editItem(@PathVariable("id") Long id, Model model) {
//        Item item = imageService.getAllItems().stream()
//                .filter(i -> i.getId().equals(id))
//                .findFirst()
//                .orElseThrow(() -> new IllegalArgumentException("Invalid item ID: " + id));
//
//        model.addAttribute("item", item);
//        return "edit-item";
//    }
//
//    @PostMapping("/edit/{id}")
//    public String updateItem(@PathVariable Long id, Item item) {
//        imageService.saveItem(item);
//        return "redirect:/images/in";
//    }
//
//    @PostMapping("/delete/{id}")
//    public String deleteItem(@PathVariable("id") Long id) {
//        // Получаем элемент из базы данных
//        Item item = imageService.getAllItems().stream()
//                .filter(i -> i.getId().equals(id))
//                .findFirst()
//                .orElseThrow(() -> new IllegalArgumentException("Invalid item ID: " + id));
//        // Извлечение имени файла из ссылки на изображение
//        String filename = item.getLink().substring(item.getLink().lastIndexOf("/") + 1);
//        // Удаление файла с использованием FileService
//        boolean fileDeleted = fileService.deleteFile(filename);
//        if (!fileDeleted) {
//            return "redirect:/images/in?error=deletefile";
//        }
//        // Удаление записи из базы данных
//        imageService.deleteItem(id);
//        return "redirect:/images/in";
//    }
//
//
//}
//
//
