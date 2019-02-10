package run.client.files;

import java.util.stream.Collectors;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import run.service.storage.StorageService;

@Controller
public class ImagesController extends FileController {

    private final String folder = "images/";

    public ImagesController(StorageService storageService){
        super(storageService);
    }

    @GetMapping("/" + folder + "{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(folder + filename);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
                .body(file);
    }

    @PostMapping("/" + folder + "upload")
    @ResponseBody
    public UploadResponse handleFileUpload(@RequestParam("file") MultipartFile file) {
        System.out.println("hello");
        String newPath = storageService.store(file, folder);
        return new UploadResponse("/" + folder + newPath);
    }

    @GetMapping("/getAllFiles")
    public ResponseEntity<?> getAllFiles() {
        return ResponseEntity.ok(storageService.loadAll().collect(Collectors.toList()));
    }
}
