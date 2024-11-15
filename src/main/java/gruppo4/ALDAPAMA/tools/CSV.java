package gruppo4.ALDAPAMA.tools;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import gruppo4.ALDAPAMA.entities.Provincia;
import gruppo4.ALDAPAMA.exceptions.BadRequestException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CSV {

    public static List<String[]> toStringList (MultipartFile csvFile){
        try(
                CSVReader csvReader = new CSVReader(new InputStreamReader(csvFile.getInputStream()));
        ) {

            List<String[]> res = new ArrayList<>();

            // Leggo tutte le righe del csv
            List<String[]> rows = csvReader.readAll();

            // Faccio un loop per ogni riga
            for(int i = 0; i < rows.size(); i++){
                String[] row = rows.get(i);

                // evito i titoli
                if(i == 0) continue;

                // splitto per ; se il file arriva da excel
                if(row.length == 1){
                    row = row[0].split(";");
                }

                res.add(row);

            }

            return res;

        } catch (IOException | CsvException e) {
            throw new BadRequestException("Errore nella lettura del file .csv");
        }
    }

}
