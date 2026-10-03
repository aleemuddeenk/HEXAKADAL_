package com.hexakadal.repository;

import com.hexakadal.model.CongestionData;
import com.hexakadal.model.FreightData;
import com.hexakadal.model.PortData;
import com.hexakadal.model.VesselData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CsvDataRepository {

    private static final Logger log = LoggerFactory.getLogger(CsvDataRepository.class);

    private List<FreightData> freightDataList = new ArrayList<>();
    private List<PortData> portDataList = new ArrayList<>();
    private List<VesselData> vesselDataList = new ArrayList<>();
    private List<CongestionData> congestionDataList = new ArrayList<>();

    public CsvDataRepository() {
        loadData();
    }

    public synchronized void loadData() {
        log.info("Loading HexaKadal CSV datasets...");
        loadBalticData();
        loadPortData();
        loadVesselData();
        loadCongestionData();
        log.info("CSV Datasets loaded successfully. Freight rows: {}, Ports: {}, Vessels: {}, Congestion rows: {}",
                freightDataList.size(), portDataList.size(), vesselDataList.size(), congestionDataList.size());
    }

    private BufferedReader getReader(String filename) throws Exception {
        String[] candidatePaths = {
            "../data/" + filename,
            "data/" + filename,
            "c:/Users/ganes/OneDrive/Desktop/HexaKadal/data/" + filename
        };

        for (String path : candidatePaths) {
            File file = new File(path);
            if (file.exists()) {
                return new BufferedReader(new FileReader(file));
            }
        }

        // Fallback: search classpath
        InputStream is = getClass().getResourceAsStream("/data/" + filename);
        if (is != null) {
            return new BufferedReader(new InputStreamReader(is));
        }

        throw new java.io.FileNotFoundException("CSV file not found: " + filename);
    }

    private void loadBalticData() {
        freightDataList.clear();
        String filename = "baltic.csv";
        try (BufferedReader br = getReader(filename)) {
            String line;
            boolean isHeader = true;
            while ((line = br.readLine()) != null) {
                if (isHeader) { isHeader = false; continue; }
                String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                if (parts.length >= 2) {
                    String date = cleanQuotes(parts[0]);
                    double price = parseDouble(parts[1]);
                    double open = parts.length > 2 ? parseDouble(parts[2]) : price;
                    double high = parts.length > 3 ? parseDouble(parts[3]) : price;
                    double low = parts.length > 4 ? parseDouble(parts[4]) : price;
                    String vol = parts.length > 5 ? cleanQuotes(parts[5]) : "";
                    String change = parts.length > 6 ? cleanQuotes(parts[6]) : "0%";

                    freightDataList.add(new FreightData(date, price, open, high, low, vol, change));
                }
            }
        } catch (Exception e) {
            log.warn("Could not load {}: {}. Using fallback freight dataset.", filename, e.getMessage());
            freightDataList.add(new FreightData("04-09-2026", 3628.00, 3628.00, 3628.00, 3628.00, "", "4.01%"));
            freightDataList.add(new FreightData("03-09-2026", 3488.00, 3488.00, 3488.00, 3488.00, "", "4.71%"));
        }
    }

    private void loadPortData() {
        portDataList.clear();
        // Indian ports defaults with known max draft (m)
        portDataList.add(new PortData("India", "Paradip Port", "INPRT", 17.50, 20.26, 86.67));
        portDataList.add(new PortData("India", "Visakhapatnam Port", "INVTZ", 16.10, 17.68, 83.21));
        portDataList.add(new PortData("India", "Haldia Port", "INHAL", 8.50, 22.02, 88.06));
        portDataList.add(new PortData("India", "Dhamra Port", "INDHM", 18.00, 20.81, 86.97));
        portDataList.add(new PortData("India", "Gopalpur Port", "INGPR", 14.50, 19.31, 84.91));
        portDataList.add(new PortData("Australia", "Hay Point (Australia)", "AUHPT", 19.00, -21.27, 149.30));
        portDataList.add(new PortData("Australia", "Newcastle (Australia)", "AUNCL", 15.20, -32.92, 151.78));
        portDataList.add(new PortData("South Africa", "Saldanha Bay (South Africa)", "ZASLD", 20.50, -33.01, 17.95));
        portDataList.add(new PortData("Australia", "Port Hedland (Australia)", "AUPHE", 20.00, -20.31, 118.57));

        String filename = "ports.csv";
        try (BufferedReader br = getReader(filename)) {
            String line;
            boolean isHeader = true;
            while ((line = br.readLine()) != null) {
                if (isHeader) { isHeader = false; continue; }
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String country = cleanQuotes(parts[0]);
                    String portName = cleanQuotes(parts[1]);
                    String locode = cleanQuotes(parts[2]);
                    boolean exists = portDataList.stream().anyMatch(p -> p.getPortName().equalsIgnoreCase(portName));
                    if (!exists && !portName.isEmpty()) {
                        portDataList.add(new PortData(country, portName, locode, 14.0, 0.0, 0.0));
                    }
                }
            }
        } catch (Exception e) {
            log.warn("Could not parse extra rows from {}: {}", filename, e.getMessage());
        }
    }

    private void loadVesselData() {
        vesselDataList.clear();
        String filename = "vessels.csv";
        try (BufferedReader br = getReader(filename)) {
            String line;
            boolean isHeader = true;
            while ((line = br.readLine()) != null) {
                if (isHeader) { isHeader = false; continue; }
                String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                if (parts.length >= 7) {
                    String company = cleanQuotes(parts[0]);
                    String shipName = cleanQuotes(parts[1]);
                    int year = (int) parseDouble(parts[2]);
                    long gt = (long) parseDouble(parts[3]);
                    long dwt = (long) parseDouble(parts[4]);
                    double len = parseDouble(parts[5]);
                    double width = parseDouble(parts[6]);
                    double estimatedDraft = calculateDraftFromDwt(dwt);

                    vesselDataList.add(new VesselData(company, shipName, year, gt, dwt, len, width, estimatedDraft));
                }
            }
        } catch (Exception e) {
            log.warn("Could not load {}: {}. Adding fallback vessel fleet.", filename, e.getMessage());
        }

        if (vesselDataList.isEmpty()) {
            vesselDataList.add(new VesselData("VALEMAX", "MV ORE BRASIL (400k DWT Valemax)", 2018, 203953, 400000, 362, 65, 18.0));
            vesselDataList.add(new VesselData("CAPESIZE", "MV CAPESIZE HERO (180k DWT Capesize)", 2019, 100000, 180000, 290, 45, 16.5));
            vesselDataList.add(new VesselData("PANAMAX", "MV PANAMAX STAR (75k DWT Panamax)", 2017, 45000, 75000, 225, 32, 12.0));
            vesselDataList.add(new VesselData("SUPRAMAX", "MV SUPRAMAX OCEAN (55k DWT Supramax)", 2016, 32000, 55000, 190, 32, 9.0));
        }
    }

    private void loadCongestionData() {
        congestionDataList.clear();
        String filename = "congestion.csv";
        try (BufferedReader br = getReader(filename)) {
            String line;
            boolean isHeader = true;
            while ((line = br.readLine()) != null) {
                if (isHeader) { isHeader = false; continue; }
                String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                if (parts.length >= 6) {
                    String econ = cleanQuotes(parts[1]);
                    String market = cleanQuotes(parts[2]);
                    double age = parseDouble(parts[3]);
                    double medianTime = parseDouble(parts[5]);
                    double avgDwt = parseDouble(parts[9]);
                    String period = parts.length > 19 ? cleanQuotes(parts[19]) : "2022-S1";

                    congestionDataList.add(new CongestionData(econ, market, medianTime, age, avgDwt, period));
                }
            }
        } catch (Exception e) {
            log.warn("Could not parse {}: {}", filename, e.getMessage());
        }

        if (congestionDataList.isEmpty()) {
            congestionDataList.add(new CongestionData("India", "Dry bulk carriers", 1.5, 12.0, 58640.0, "2022-S1"));
        }
    }

    private double calculateDraftFromDwt(long dwt) {
        if (dwt >= 200000) return 18.0;
        if (dwt >= 100000) return 16.5;
        if (dwt >= 60000) return 12.0;
        return 9.0;
    }

    private String cleanQuotes(String str) {
        if (str == null) return "";
        return str.replace("\"", "").trim();
    }

    private double parseDouble(String str) {
        if (str == null) return 0.0;
        String cleaned = str.replace("\"", "").replace(",", "").trim();
        if (cleaned.isEmpty() || cleaned.equalsIgnoreCase("Not available or not separately reported")) {
            return 0.0;
        }
        try {
            return Double.parseDouble(cleaned);
        } catch (Exception e) {
            return 0.0;
        }
    }

    public List<FreightData> getFreightDataList() { return freightDataList; }
    public List<PortData> getPortDataList() { return portDataList; }
    public List<VesselData> getVesselDataList() { return vesselDataList; }
    public List<CongestionData> getCongestionDataList() { return congestionDataList; }
}
