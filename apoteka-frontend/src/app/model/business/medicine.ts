import { Rating } from "./rating";

export class Medicine {
  id: number;
  name: string;
  manufacturer: string;
  code: string;
  type: string;
  form: string;
  prescriptionNecessary: boolean;
  sideEffects: string;
  ingredients: string[];
  recommendedDose: string;
  substitutes: Medicine[];
  ratings: Rating[];
}
