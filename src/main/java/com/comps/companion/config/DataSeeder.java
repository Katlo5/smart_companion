package com.comps.companion.config;

import com.comps.companion.entity.DiabetesPatient;
import com.comps.companion.entity.HypertensionPatient;
import com.comps.companion.repository.DiabetesRepository;
import com.comps.companion.repository.HypertensionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * DataSeeder — runs once on startup and populates the H2 in-memory database
 * with realistic demo patients so the dashboard has data to display immediately.
 *
 * Only seeds if the tables are empty, so restarting won't create duplicates
 * within the same JVM session (though H2 resets between full restarts anyway).
 */
@Component
public class DataSeeder implements ApplicationRunner {

    @Autowired
    private DiabetesRepository diabetesRepository;

    @Autowired
    private HypertensionRepository hypertensionRepository;

    @Override
    public void run(ApplicationArguments args) {
        seedDiabetesPatients();
        seedHypertensionPatients();
        System.out.println(">>> DataSeeder: Demo database populated successfully.");
    }

    // ── Diabetes Patients ────────────────────────────────────────────────
    private void seedDiabetesPatients() {
        if (diabetesRepository.count() > 0) return;

        Object[][] data = {
            // name,                glucoseLevel, hba1c,  hoursAgo
            { "Amara Osei",          182.4,        8.1,    1  },  // High — diabetic
            { "Kofi Mensah",          94.2,        5.4,    2  },  // Normal
            { "Abena Darko",         134.7,        7.2,    3  },  // High
            { "Kwame Tetteh",         78.5,        4.9,    4  },  // Normal
            { "Esi Boateng",         221.3,        9.6,    5  },  // Critical
            { "Yaw Asante",          108.0,        6.1,    6  },  // Borderline
            { "Adwoa Kumi",           88.9,        5.2,    7  },  // Normal
            { "Nana Agyei",          156.2,        7.8,    8  },  // High
            { "Akosua Frimpong",      99.1,        5.7,   10  },  // Borderline
            { "Kweku Ankrah",        193.5,        8.9,   12  },  // Critical
            { "Efua Oppong",          72.4,        4.7,   14  },  // Normal
            { "Kojo Amoah",          119.6,        6.5,   18  },  // Borderline
            { "Ama Nyarko",          248.0,       10.2,   22  },  // Critical — highest risk
            { "Fiifi Owusu",          85.3,        5.0,   26  },  // Normal
            { "Akua Bonsu",          141.8,        7.4,   30  },  // High
        };

        for (Object[] row : data) {
            DiabetesPatient p = new DiabetesPatient();
            p.setPatientName((String) row[0]);
            p.setGlucoseLevel((Double) row[1]);
            p.setHba1c((Double) row[2]);
            p.setMeasuredAt(LocalDateTime.now().minusHours((int) row[3]));

            // Pre-classify risk so the dashboard shows insight immediately
            double g = (Double) row[1];
            if (g > 180)       p.setRiskAssessment("Critical — immediate clinical review required");
            else if (g > 126)  p.setRiskAssessment("High — glucose above diabetic threshold");
            else if (g > 100)  p.setRiskAssessment("Borderline — pre-diabetic range, monitor closely");
            else               p.setRiskAssessment("Normal — glucose within healthy range");

            diabetesRepository.save(p);
        }

        System.out.println(">>> DataSeeder: Seeded " + data.length + " diabetes patients.");
    }

    // ── Hypertension Patients ────────────────────────────────────────────
    private void seedHypertensionPatients() {
        if (hypertensionRepository.count() > 0) return;

        Object[][] data = {
            // name,                  systolic, diastolic, hoursAgo
            { "Grace Nkrumah",         178,       112,       1  },  // Stage 2
            { "Emmanuel Quaye",        122,        78,       2  },  // Normal
            { "Abigail Sarpong",       145,        94,       3  },  // Stage 2
            { "Daniel Ofori",          118,        76,       4  },  // Normal
            { "Patience Acheampong",   196,       124,       5  },  // Crisis
            { "Samuel Baidoo",         132,        84,       6  },  // Stage 1
            { "Rebecca Gyasi",         115,        72,       8  },  // Normal
            { "Joshua Asumadu",        155,        98,      10  },  // Stage 2
            { "Cecilia Yeboah",        128,        82,      12  },  // Stage 1
            { "Frank Dompreh",         183,       116,      16  },  // Crisis
            { "Elizabeth Twum",        110,        68,      20  },  // Normal
            { "Michael Aidoo",         138,        88,      24  },  // Stage 1
        };

        for (Object[] row : data) {
            HypertensionPatient p = new HypertensionPatient();
            p.setPatientName((String) row[0]);
            p.setSystolic((Integer) row[1]);
            p.setDiastolic((Integer) row[2]);
            p.setMeasuredAt(LocalDateTime.now().minusHours((int) row[3]));
            hypertensionRepository.save(p);
        }

        System.out.println(">>> DataSeeder: Seeded " + data.length + " hypertension patients.");
    }
}
