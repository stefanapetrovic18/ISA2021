import { IncomeReport } from "./income-report";
import { RatingReport } from "./rating-report";

export class BusinessReport {
  pharmacyName: string;
  ratingReport: RatingReport;
  monthlyMedicineSales: number[];
  monthlyExaminations: number[];
  monthlyConsultations: number[];
  incomeReport: IncomeReport;
}
