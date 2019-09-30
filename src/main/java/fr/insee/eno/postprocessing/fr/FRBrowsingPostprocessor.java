package fr.insee.eno.postprocessing.fr;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.insee.eno.Constants;
import fr.insee.eno.parameters.PostProcessing;
import fr.insee.eno.postprocessing.Postprocessor;
import fr.insee.eno.transform.xsl.XslTransformation;

@Service
public class FRBrowsingPostprocessor implements Postprocessor {

	private static final Logger logger = LoggerFactory.getLogger(FRBrowsingPostprocessor.class);

	@Autowired
	private XslTransformation saxonService;

	@Override
	public File process(File input, byte[] parameters, String survey) throws Exception {

		File outputForFRFile = new File(input.getParent(),
				Constants.BASE_NAME_FORM_FILE +
				Constants.BROWSING_FR_EXTENSION);
		
		logger.debug("Output folder for basic-form : " + outputForFRFile.getAbsolutePath());

		InputStream FO_XSL = Constants.getInputStreamFromPath(Constants.UTIL_FR_BROWSING_XSL);

		InputStream inputStream = FileUtils.openInputStream(input);
		OutputStream outputStream = FileUtils.openOutputStream(outputForFRFile);

		saxonService.transformBrowsingFr(inputStream, outputStream, FO_XSL);

		inputStream.close();
		outputStream.close();
		FO_XSL.close();
		logger.info("End of Browsing post-processing " + outputForFRFile.getAbsolutePath());

		return outputForFRFile;
	}
	
	@Override
	public String toString() {
		return PostProcessing.FR_BROWSING.name();
	}


}
