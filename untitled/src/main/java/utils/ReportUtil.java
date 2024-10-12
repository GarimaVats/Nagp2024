package utils;

import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import logger.Log;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class ReportUtil {

    public boolean embedScreenshotsIntoWord(String wordFilePath, String scenarioName) {

        boolean status = false;
        InputStream pic = null;
        File file = null;
        File imageFolder = null;
        FileOutputStream out = null;
        XWPFDocument docx = null;
        try {
            docx = new XWPFDocument();
            XWPFParagraph para = docx.createParagraph();
            XWPFRun run = para.createRun();
            run.setText(scenarioName);
            para.setBorderLeft(Borders.BASIC_THIN_LINES);
            para.setBorderRight(Borders.BASIC_THIN_LINES);
            run.setBold(true);
            run.setFontSize(15);
            run.setUnderline(UnderlinePatterns.SINGLE);
            run.addBreak();
            para.setAlignment(ParagraphAlignment.CENTER);
            out = new FileOutputStream(wordFilePath + File.separator + scenarioName + ".docx");
            imageFolder = new File(wordFilePath);
            System.out.println("Last Modified Time :");
            File[] filArr = imageFolder.listFiles();
            filArr = Arrays.stream(filArr).sorted(Comparator.comparingLong(f -> f.lastModified())).toArray(File[]::new);
            for (int i = 0; i < filArr.length; i++) {
                String screenshot_name = System.currentTimeMillis() + ".png";
                if (filArr[i].getName().toString().endsWith(".png")) {
                    Image image = ImageIO.read(filArr[i]);
                    BufferedImage buffered = (BufferedImage) image;
                    file = new File(wordFilePath + File.separator + screenshot_name);
                    ImageIO.write(buffered, "png", file);
                    pic = new FileInputStream(file);
                    run.addBreak();
                    run.addPicture(pic, XWPFDocument.PICTURE_TYPE_PNG, screenshot_name, Units.toEMU(350),
                            Units.toEMU(500));
                    run.addBreak();
                    run.addBreak();
                    run.setText(scenarioName + " - " + i + ".png");
                    TimeUnit.SECONDS.sleep(1);
                }
            }
            docx.write(out);
            for (File delFile : new File(wordFilePath).listFiles()) {
                if (delFile.getName().endsWith(".png"))
                    delFile.delete();
            }
            status = true;
        } catch (Exception e) {
            Log.info(e.getMessage());
        } finally {
            try {
                pic.close();
                out.flush();
                out.close();
                docx.close();
            } catch (IOException e) {
                Log.info(e.getMessage());
            }
        }
        return status;
    }

    public void addAllureAttachment(String name, Object file) {

        FileUtils fileUtils = new FileUtils();

        try {
            if (file instanceof File[]) {
                for (File fi : (File[]) file) {
                    switch (FilenameUtils.getExtension(fi.getName())) {
                        case "json":
                            Allure.getLifecycle().addAttachment(name, "application/json", "json",
                                    fileUtils.convertFileToByteArray(fi));
                            break;
                        case "jpeg":
                            Allure.getLifecycle().addAttachment(name, "application/jpeg", "jpeg",
                                    fileUtils.convertFileToByteArray(fi));
                            break;
                        case "png":
                            Allure.getLifecycle().addAttachment(name, "application/png", "png",
                                    fileUtils.convertFileToByteArray(fi));
                            break;
                        case "html":
                            Allure.getLifecycle().addAttachment(name, "application/html", "html",
                                    fileUtils.convertFileToByteArray(fi));
                            break;
                        default:
                    }
                }
            } else if (file instanceof String) {
                Allure.getLifecycle().addAttachment(name, "application/json", "json", ((String) file).getBytes("UTF-8"));

            }
        } catch (Exception e) {
            Log.warn(e.getMessage());
        }
    }

    public void generateInProgressHtmlReport(Scenario scenario, String screenShotPath) {
        try {

            // Generate JSON Report for Scenario Data
            String jsonFilePath = generateJsonReport(scenario);

            // Creating Map for HTML Report
            Map<String, Object> map = new HashMap<>();
            map.put("scenarioName", scenario.getName().split("\\|")[0]);
            map.put("scenarioStatus", scenario.getStatus().toString());
            map.put("screenshotLink", screenShotPath);
            map.put("total", countStatusOccurences("TOTAL", jsonFilePath));
            map.put("passed", countStatusOccurences("PASSED", jsonFilePath));
            map.put("failed", countStatusOccurences("FAILED", jsonFilePath));
            map.put("skipped", countStatusOccurences("SKIPPED", jsonFilePath));
            map.put("passedPercentage", calculatePercentage(Float.parseFloat(map.get("passed").toString()), Float.parseFloat(map.get("total").toString())));
            map.put("failedPercentage", calculatePercentage(Float.parseFloat(map.get("failed").toString()), Float.parseFloat(map.get("total").toString())));

            // Adding Failure Scenarios to Detailed Report
            addFailedScenarioDetails(map);
        } catch (Exception e) {
            Log.warn(e.getMessage());
        }

    }

    private String calculatePercentage(float x, float total) {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        return df.format(x / total * 100);
    }

    private String getHtml(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader(filePath));
            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);
            }
            in.close();
        } catch (IOException e) {
            Log.warn(e.getMessage());
        }
        return contentBuilder.toString();

    }

    public void addFailedScenarioDetails(Map<String, Object> map) {
        try {
            //Checking File Exists & Create New File
            String filePath;
            File jsonFile = new File("" + "progress.html");
            if (jsonFile.exists()) {
                filePath = getHtml(jsonFile.getPath());
            } else {
                filePath = getHtml("" + "progress.html");
            }
            Document doc = Jsoup.parse(filePath, "UTF-8");
            doc.getElementsByTag("tr").get(1).getElementsByTag("td").get(0).text(map.get("total").toString());
            doc.getElementsByTag("tr").get(1).getElementsByTag("td").get(1).text(map.get("passed").toString());
            doc.getElementsByTag("tr").get(1).getElementsByTag("td").get(2).text(map.get("failed").toString());
            doc.getElementsByTag("tr").get(1).getElementsByTag("td").get(3).text(map.get("skipped").toString());
            doc.getElementsByTag("tr").get(1).getElementsByTag("td").get(4).text(map.get("passedPercentage").toString());
            doc.getElementsByTag("tr").get(1).getElementsByTag("td").get(5).text(map.get("failedPercentage").toString());
            if (map.get("scenarioStatus").equals("FAILED")) {
                Elements table = doc.getElementsByTag("table");
                Elements tbody = table.last().getElementsByTag("tbody");
                Element td = tbody.get(tbody.size() - 1);
                td.append("<tr style=\"font-family: Lato-Regular;font-size: 20px;color: white;line-height: 1.4;background-color: #222;line-height: 1.4;text-align:center;\"> <td style=text-align:left;>" + map.get("scenarioName") + "</td> <td>" + map.get("scenarioStatus") + "</td> <td><a style=\"color: white;\" href=" + map.get("screenshotLink") + " target=\"_blank\">Click here</a></td></tr>");
            }
            Files.write(Paths.get(jsonFile.getPath()), doc.html().getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            Log.warn(e.getMessage());
        }
    }

    private String generateJsonReport(Scenario scenario) {
        File jsonFile = null;
        try {
            //Checking File Exists & Create New File
            jsonFile = new File("" + "progress.json");
            if (!jsonFile.exists())
                jsonFile.createNewFile();
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            UUID uniqueKey = UUID.randomUUID();
            if (jsonFile.length() > 0) {
                Object obj = jsonParser.parse(new FileReader(jsonFile));
                jsonArray = (JSONArray) obj;
            }
            jsonObject.put("scenarioId", String.valueOf(uniqueKey));
            jsonObject.put("name", scenario.getName());
            jsonObject.put("status", scenario.getStatus().toString());
            jsonArray.add(jsonObject);
            Files.write(Paths.get(jsonFile.getPath()), jsonArray.toString().getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            Log.warn(e.getMessage());
        }
        return jsonFile.getPath();
    }

    private int countStatusOccurences(String status, String jsonFilePath) {
        int count = 0;
        try {
            JSONParser jsonParser = new JSONParser();
            Object obje2 = jsonParser.parse(new FileReader(jsonFilePath));
            org.json.JSONArray jsonArray = new org.json.JSONArray(obje2.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                org.json.JSONObject element = jsonArray.getJSONObject(i);
                if (status.equals("TOTAL")) {
                    count = jsonArray.length();
                    break;
                } else if (status.equals(element.getString("status"))) {
                    count++;
                }
            }
        } catch (Exception e) {
            Log.warn(e.getMessage());
        }
        return count;
    }

}
