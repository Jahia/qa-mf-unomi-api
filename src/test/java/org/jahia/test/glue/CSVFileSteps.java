package org.jahia.test.glue;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.jahia.test.data.TestRtVariables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.opencsv.CSVReader;

import cucumber.api.java.en.Then;

public class CSVFileSteps
{
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Then("^The downloaded csv file contains a line with records \"([^\"]*)\"$")
	public void the_downloaded_csv_file_contains_a_line_with_records(List<String> expectedRecords)
			throws Throwable
	{
		boolean found = false;
		Reader reader = Files
				.newBufferedReader(Paths.get(TestRtVariables.lastDownloadedFile.getAbsolutePath()));
		CSVReader csvReader = new CSVReader(reader);
		List<String[]> csvLines = csvReader.readAll();
		for (int i = 0; i < csvLines.size(); i++)
		{
			found = false;
			if (csvLines.get(i)[0].equals(expectedRecords.get(0)))
			{
				found = true;
				for (int j = 1; j < expectedRecords.size(); j++)
				{
					found = found && (csvLines.get(i)[j].equals(expectedRecords.get(j)));
				}
			}
			if (found)
				break;
		}
		csvReader.close();
		Assert.assertTrue(found);

	}
}
