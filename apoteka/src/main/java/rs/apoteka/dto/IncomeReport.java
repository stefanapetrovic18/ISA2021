package rs.apoteka.dto;

import java.time.LocalDate;

public class IncomeReport {
    private LocalDate reportFrom;
    private LocalDate reportUntil;
    private Double income;

    public IncomeReport() {
    }

    public LocalDate getReportFrom() {
        return reportFrom;
    }

    public void setReportFrom(LocalDate reportFrom) {
        this.reportFrom = reportFrom;
    }

    public LocalDate getReportUntil() {
        return reportUntil;
    }

    public void setReportUntil(LocalDate reportUntil) {
        this.reportUntil = reportUntil;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }
}
