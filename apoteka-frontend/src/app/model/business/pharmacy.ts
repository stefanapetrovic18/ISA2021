import {User} from '../auth/user';
import {Dermatologist} from '../user/dermatologist';
import {Pharmacist} from '../user/pharmacist';
import {PharmacyAdmin} from '../user/pharmacy-admin';
import {Consultation} from './consultation';
import {Examination} from './examination';
import {Pricelist} from './pricelist';
import {Promotion} from './promotion';
import {Order} from './order';

export class Pharmacy {
  id: number;
  name: string;
  description: string;
  address: string;
  admins: PharmacyAdmin[];
  dermatologists: Dermatologist[];
  pharmacists: Pharmacist[];
  examinations: Examination[];
  consultations: Consultation[];
  orders: Order[];
  promotions: Promotion[];
  subscriptions: User[];
  pricelist: Pricelist;
  rating: number;
}
