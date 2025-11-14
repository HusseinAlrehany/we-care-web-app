package clinicmangement.com.WeCare.controller.doctor;

import clinicmangement.com.WeCare.service.doctor.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ImageController {

    private final DoctorService doctorService;

    @GetMapping("/doctors/{id}/photo")
    public ResponseEntity<byte[]> getDoctorPhoto(@PathVariable Integer id){

         byte[] image = doctorService.getDoctorPhotoById(id);
         MediaType mediaType = getMediaType(image);

        return ResponseEntity.ok().contentType(mediaType)
                        .cacheControl(CacheControl.maxAge(1, TimeUnit.DAYS))
                        .body(image);
    }

    @GetMapping("/doctors/{id}/medical-card")
    public ResponseEntity<byte[]> getMedicalCardImage(@PathVariable Integer id){

        byte[] image = doctorService.getMedicalCardById(id);
        MediaType mediaType = getMediaType(image);

        return ResponseEntity.ok().contentType(mediaType).body(image);
    }

    private MediaType getMediaType(byte[] imageBytes){
        try (InputStream is = new ByteArrayInputStream(imageBytes)) {
            String mimeType = URLConnection.guessContentTypeFromStream(is);
            return MediaType.parseMediaType(mimeType != null ? mimeType : MediaType.APPLICATION_OCTET_STREAM_VALUE);
        } catch (IOException e) {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}
