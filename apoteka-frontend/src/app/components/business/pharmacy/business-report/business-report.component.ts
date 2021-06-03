import { Component, OnInit } from '@angular/core';
import { BusinessReport } from 'src/app/dto/business-report';
import { PharmacyService } from 'src/app/service/business/pharmacy.service';

@Component({
  selector: 'app-business-report',
  templateUrl: './business-report.component.html',
  styleUrls: ['./business-report.component.scss']
})
export class BusinessReportComponent implements OnInit {
  report = new BusinessReport();
  profitFrom = new Date();
  profitUntil = new Date();
  year = 2021;

  yearlyMedicineSales = 0;
  yearlyExaminations = 0;
  yearlyConsultations = 0;

  dermatologistDisplayedColumns: string[] = ['dermatologistName', 'dermatologistRating'];
  pharmacistDisplayedColumns: string[] = ['pharmacistName', 'pharmacistRating'];

  constructor(private pharmacyService: PharmacyService) { }

  ngOnInit() {

  }

  getReport() {
    this.pharmacyService.getBusinessReport(this.profitFrom, this.profitUntil, this.year).subscribe(
      data => {
        this.report = data;
        this.yearlyMedicineSales = 0;
        this.report.monthlyMedicineSales.forEach(s => {
          this.yearlyMedicineSales += s;
        });
        this.yearlyExaminations = 0;
        this.report.monthlyExaminations.forEach(s => {
          this.yearlyExaminations += s;
        });
        this.yearlyConsultations = 0;
        this.report.monthlyConsultations.forEach(s => {
          this.yearlyConsultations += s;
        });
      }, error => {
        console.log(error);
        window.alert(error.errorMessage);
      }
    );
  }

  getAveragePharmacistRating() {
    let sum = 0;
    this.report.ratingReport.pharmacistRatings.forEach(r => {
      sum += r.pharmacistRating;
    });
    return sum / this.report.ratingReport.pharmacistRatings.length;
  }

  getAverageDermatologistRating() {
    let sum = 0;
    this.report.ratingReport.dermatologistRatings.forEach(r => {
      sum += r.dermatologistRating;
    });
    return sum / this.report.ratingReport.dermatologistRatings.length;
  }

}
