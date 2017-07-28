package in.brewcode.api.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/upload")
public class FileUploadController {

	private static final String UPLOAD_DIRECTORY = "/resources/uploads";

	@RequestMapping(value = "", method = RequestMethod.POST, consumes = "multipart/form-data")
	@ResponseBody
	public ResponseEntity<String> uploadMedia(
			@RequestParam(name = "file", required = true) MultipartFile mf,
			@RequestParam(name = "target", defaultValue = "") String targetType,
			@RequestParam(name = "id") String targetFolderNumber)
			throws IllegalStateException, IOException {
		final String fileName = mf.getOriginalFilename();
		final String appPath = ContextLoader.getCurrentWebApplicationContext()
				.getServletContext().getRealPath("/");

		String directoryPath = appPath + UPLOAD_DIRECTORY;
		switch (targetType.toUpperCase()) {
		case "ARTICLE":
			directoryPath = directoryPath + "/articles/" + targetFolderNumber;
			break;
		case "CONTENT":
			directoryPath = directoryPath + "/articles/" + targetFolderNumber
					+ "/contents";
			break;
		case "PROFILE":
			directoryPath = directoryPath + "/profiles/" + targetFolderNumber;
			break;
		case "CLIENT":
			directoryPath = directoryPath + "/clients/" + targetFolderNumber;
			break;
		default:
			directoryPath = "/";
			break;
		}
		Path path = Paths.get(directoryPath);

		if (!Files.exists(path)) {
			Files.createDirectories(path);
		}
		final String dest = directoryPath + "/" + fileName;
		Files.copy(mf.getInputStream(), Paths.get(dest),
				StandardCopyOption.REPLACE_EXISTING);
		return new ResponseEntity<String>(dest, new HttpHeaders(),
				HttpStatus.OK);

	}

}
