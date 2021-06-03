package rs.apoteka.dto;

import javax.validation.constraints.Size;
import java.util.List;

public class BusinessReport {
    private String pharmacyName;
    private RatingReport ratingReport;
    @Size(min = 12, max = 12)
    private List<Integer> monthlyMedicineSales;
    @Size(min = 12, max = 12)
    private List<Integer> monthlyExaminations;
    @Size(min = 12, max = 12)
    private List<Integer> monthlyConsultations;
    private IncomeReport incomeReport;

    public BusinessReport() {
    }

    public String getPharmacyName() {
        return pharmacyName;
    }

    public void setPharmacyName(String pharmacyName) {
        this.pharmacyName = pharmacyName;
    }

    public RatingReport getRatingReport() {
        return ratingReport;
    }

    public void setRatingReport(RatingReport ratingReport) {
        this.ratingReport = ratingReport;
    }

    public List<Integer> getMonthlyMedicineSales() {
        return monthlyMedicineSales;
    }

    public void setMonthlyMedicineSales(List<Integer> monthlyMedicineSales) {
        this.monthlyMedicineSales = monthlyMedicineSales;
    }

    public List<Integer> getMonthlyExaminations() {
        return monthlyExaminations;
    }

    public void setMonthlyExaminations(List<Integer> monthlyExaminations) {
        this.monthlyExaminations = monthlyExaminations;
    }

    public List<Integer> getMonthlyConsultations() {
        return monthlyConsultations;
    }

    public void setMonthlyConsultations(List<Integer> monthlyConsultations) {
        this.monthlyConsultations = monthlyConsultations;
    }

    public IncomeReport getIncomeReport() {
        return incomeReport;
    }

    public void setIncomeReport(IncomeReport incomeReport) {
        this.incomeReport = incomeReport;
    }
}
