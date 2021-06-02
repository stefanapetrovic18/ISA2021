import { Patient } from "../user/patient";

export class Complaint {
  id: number;
  text: string;
  patient: Patient;
  resolved: boolean;
}
