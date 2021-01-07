import { Dermatologist } from "../user/dermatologist";
import { Patient } from "../user/patient";
import { Pharmacy } from "./pharmacy";

export class Examination {
  id: number;
  examinationDate: Date;
  dermatologist: Dermatologist;
  patient: Patient;
  pharmacy: Pharmacy;
  duration: number;
  price: number;
}
