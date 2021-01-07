import { Patient } from "../user/patient";
import { Medicine } from "./medicine";
import { Pharmacy } from "./pharmacy";

export class Prescription {
  id: number;
  code: string;
  issueDate: Date;
  pharmacy: Pharmacy;
  patient: Patient;
  medicines: Medicine[];
}
